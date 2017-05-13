/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mohamedkamal
 */
public class Notification extends Model<Notification> {

    private int _id;
    private int _auctionId;
    private int _itemId;
    private int _status;
    private String notificationString;

    @Override
    public String toString() {
        return super.toString() + "\n" + this.notificationString;
    }

    public Notification(int _id, int _auctionId, int _itemId, int _status, String notificationString) {
        this._id = _id;
        this._auctionId = _auctionId;
        this._itemId = _itemId;
        this._status = _status;
        this.notificationString = notificationString;
    }

    public Notification() {
    }

    public int getId() {
        return _id;
    }

    public int getAuctionId() {
        return _auctionId;
    }

    public int getItemId() {
        return _itemId;
    }

    public int getStatus() {
        return _status;
    }

    public static ArrayList<Notification> getNewBids(int userId) {
        try {
            String Query = "SELECT * FROM (SELECT `notifications`.`ID`, `notifications`.`auctionId`,\n"
                    + " (SELECT `users`.`Name` FROM `users` JOIN `bids` on `bids`.`UserID` = `users`.`ID` "
                    + "WHERE `auctions`.`ID` = `bids`.`AuctionID` ORDER BY `bids`.`Price` DESC LIMIT 1) AS `Name`,\n"
                    + "    (SELECT MAX(`bids`.`Price`) FROM `bids` WHERE `bids`.`AuctionID` = `auctions`.`ID`) AS `Price`,\n"
                    + "    `items`.`Name` as `itemName` , `notifications`.`status`\n"
                    + "FROM `notifications`\n"
                    + "JOIN `auctions` ON `auctions`.`ID` = `notifications`.`auctionId`\n"
                    + "JOIN `items` ON `items`.`ID` = `auctions`.`ItemID`\n"
                    + "WHERE `auctions`.`UserID` = ?\n"
                    + "UNION\n"
                    + "SELECT `notifications`.`ID`, `notifications`.`auctionId`,\n"
                    + "(SELECT `users`.`Name` FROM `users` JOIN `bids` on `bids`.`UserID` = `users`.`ID` "
                    + "WHERE `auctions`.`ID` = `bids`.`AuctionID` ORDER BY `bids`.`Price` DESC LIMIT 1) AS `Name`,\n"
                    + "(SELECT MAX(`bids`.`Price`) FROM `bids` WHERE `bids`.`AuctionID` = `auctions`.`ID`) AS `Price`,\n"
                    + "items.Name as itemName , notifications.status\n"
                    + "FROM `notifications`\n"
                    + "JOIN `auctions` ON `notifications`.`auctionId` = `auctions`.`ID`\n"
                    + "JOIN `items` ON `items`.`ID` = `auctions`.`ItemID`\n"
                    + "WHERE `auctions`.`ID` in (SELECT DISTINCT `auctions`.`ID` FROM `auctions` JOIN `bids` on "
                    + "`auctions`.`ID` = `bids`.`AuctionID` WHERE `bids`.`UserID` = ?)\n"
                    + "UNION\n"
                    + "SELECT\n"
                    + "	`notifications`.`ID`, `notifications`.`auctionId`,\n"
                    + "    (SELECT `users`.`Name`\n"
                    + "         FROM `users` \n"
                    + "         JOIN `bids` ON bids.UserID = `users`.`ID` \n"
                    + "         WHERE `bids`.`AuctionID`= `auctions`.`ID`\n"
                    + "         ORDER BY `bids`.`Price`  LIMIT 1) as `Name`,\n"
                    + "	(SELECT MAX(`bids`.`Price`) FROM `bids` WHERE `bids`.`AuctionID` = `auctions`.`ID`) AS `Price`,\n"
                    + "    `items`.`Name` as `itemName`, `notifications`.`status`\n"
                    + "FROM `notifications`\n"
                    + "JOIN `subscribe_auctions` ON `notifications`.`auctionId` = `subscribe_auctions`.`AuctionID`\n"
                    + "JOIN `auctions` ON `auctions`.`ID` = `notifications`.`auctionId`\n"
                    + "JOIN `items` ON `items`.`ID` = `auctions`.`ItemID`\n"
                    + "WHERE `subscribe_auctions`.`SubscriberID` = ?)\n"
                    + "AS `R` WHERE `R`.`status` = '1'";
            PreparedStatement excutequery = Model.generateQuery(Query, userId, userId, userId);

            ResultSet result = excutequery.executeQuery();
            ArrayList<Notification> resultNotification = new ArrayList<>();
            DecimalFormat df = new DecimalFormat("#");
            df.setMaximumFractionDigits(2);
            while (result.next()) {
                String price = df.format(result.getDouble("Price"));
                String itemName = result.getString("itemName");
                String Name = result.getString("Name");
                String msg = Name + " made a bid of " + price + " on " + itemName;
                resultNotification.add(new Notification(result.getInt("ID"), result.getInt("auctionId"), 0, result.getInt("status"), msg));
            }
            return resultNotification;
        } catch (SQLException ex) {
            Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static ArrayList<Notification> getWinners(int userId) {
        try {
            String Query = "SELECT notifications.ID ,items.Name ,notifications.status ,"
                    + "items.ID as itemId  FROM `notifications` "
                    + "JOIN `items` on notifications.itemId = `items`.`ID` "
                    + "JOIN `inventories` ON `inventories`.`ID` = `items`.`InventoryID` "
                    + "WHERE `inventories`.`SellerID` = ? AND `notifications`.`status` = '0'";
            PreparedStatement excutequery = Model.generateQuery(Query, userId);

            ResultSet result = excutequery.executeQuery();
            ArrayList<Notification> resultNotification = new ArrayList<>();
            while (result.next()) {
                resultNotification.add(new Notification(result.getInt("ID"), 0, result.getInt("itemId"), result.getInt("status"), "Congratulations, you have won " + result.getString("Name")));
            }
            return resultNotification;
        } catch (SQLException ex) {
            Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static ArrayList<Notification> getStartedAuction(int userId) {
        try {
            String Query = "SELECT `notifications`.`ID`, `notifications`.`auctionId`,"
                    + " `notifications`.`status`, `items`.`Name`\n"
                    + "FROM `notifications`\n"
                    + "JOIN `auctions` on `auctions`.`ID` = `notifications`.`auctionId`\n"
                    + "JOIN `items` ON `items`.`ID` = `auctions`.`ItemID`\n"
                    + "JOIN `subscribe_auctions` ON `auctions`.`ID` = `subscribe_auctions`.`AuctionID`\n"
                    + "WHERE `notifications`.`status` = '2' AND `subscribe_auctions`.`SubscriberID` = ?";
            PreparedStatement excutequery = Model.generateQuery(Query, userId);

            ResultSet result = excutequery.executeQuery();
            ArrayList<Notification> resultNotification = new ArrayList<>();
            while (result.next()) {
                resultNotification.add(new Notification(result.getInt("ID"), result.getInt("auctionId"), 0, result.getInt("status"), "Auction '" + result.getString("Name") + "' Started."));
            }
            return resultNotification;
        } catch (SQLException ex) {
            Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ArrayList<Notification> getUserNotifications(int userId) {
        ArrayList<Notification> notifications = new ArrayList<>(getNewBids(userId));
        notifications.addAll(getWinners(userId));
        notifications.addAll(getStartedAuction(userId));
        return notifications;
    }

}


/*
SELECT `notifications`.`ID`, `notifications`.`auctionId` , 
	(SELECT `users`.`Name` FROM `users` JOIN `bids` on `bids`.`UserID` = `users`.`ID` WHERE `auctions`.`ID` = `bids`.`AuctionID` ORDER BY `bids`.`Price` DESC LIMIT 1) AS `Name`,
    (SELECT MAX(`bids`.`Price`) FROM `bids` WHERE `bids`.`AuctionID` = `auctions`.`ID`) AS `Price`,
    `items`.`Name` as `itemName` , `notifications`.`status`
FROM `notifications`
JOIN `auctions` ON `auctions`.`ID` = `notifications`.`auctionId`
JOIN `items` ON `items`.`ID` = `auctions`.`ItemID`
WHERE `auctions`.`UserID` = ?
UNION
SELECT `notifications`.`ID`, `notifications`.`auctionId`,
(SELECT `users`.`Name` FROM `users` JOIN `bids` on `bids`.`UserID` = `users`.`ID` WHERE `auctions`.`ID` = `bids`.`AuctionID` ORDER BY `bids`.`Price` DESC LIMIT 1) AS `Name`,
(SELECT MAX(`bids`.`Price`) FROM `bids` WHERE `bids`.`AuctionID` = `auctions`.`ID`) AS `Price`,
items.Name as itemName , notifications.status
FROM `notifications`
JOIN `auctions` ON `notifications`.`auctionId` = `auctions`.`ID`
JOIN `items` ON `items`.`ID` = `auctions`.`ItemID`
WHERE `auctions`.`ID` in (SELECT DISTINCT `auctions`.`ID` FROM `auctions` JOIN `bids` on `auctions`.`ID` = `bids`.`AuctionID` WHERE `bids`.`UserID` = ?)
UNION
SELECT
	`notifications`.`ID`, `notifications`.`auctionId`,
    (SELECT `users`.`Name`
         FROM `users` 
         JOIN `bids` ON bids.UserID = `users`.`ID` 
         WHERE `bids`.`AuctionID`= `auctions`.`ID`
         ORDER BY `bids`.`Price`  LIMIT 1) as `Name`,
	(SELECT MAX(`bids`.`Price`) FROM `bids` WHERE `bids`.`AuctionID` = `auctions`.`ID`) AS `Price`,
    `items`.`Name` as `itemName`, `notifications`.`status`
FROM `notifications`
JOIN `subscribe_auctions` ON `notifications`.`auctionId` = `subscribe_auctions`.`AuctionID`
JOIN `auctions` ON `auctions`.`ID` = `notifications`.`auctionId`
JOIN `items` ON `items`.`ID` = `auctions`.`ItemID`
WHERE `subscribe_auctions`.`SubscriberID` = ?


 */
