/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    private static ArrayList<Notification> getnewBids(int userId) {
        try {
            String Query = "SELECT `notifications`.`ID`, `notifications`.`auctionId`,(SELECT users.Name FROM users WHERE users.ID = bids.UserID) as Name ,bids.Price , items.Name  as itemName , notifications.status FROM items,notifications JOIN auctions ON notifications.auctionId = auctions.ID JOIN bids ON bids.AuctionID = auctions.ID JOIN users ON users.ID = auctions.UserID WHERE users.ID = ? AND items.ID = auctions.ItemID AND notifications.status = '1'\n" +
"UNION\n" +
"SELECT `notifications`.`ID`, `notifications`.`auctionId` , users.Name , bids.Price , items.Name as itemName , notifications.status FROM items, notifications JOIN auctions ON notifications.auctionId = auctions.ID JOIN bids ON bids.AuctionID = auctions.ID JOIN users ON users.ID = bids.UserID WHERE (SELECT COUNT(*) FROM bids WHERE bids.UserID = ? AND bids.AuctionID = auctions.ID) > 0 AND items.ID = auctions.ItemID AND notifications.status = '1'";
            PreparedStatement excutequery = Model.generateQuery(Query, userId , userId);
            
            ResultSet result = excutequery.executeQuery();
            ArrayList<Notification> resultNotification = new ArrayList<>();
            while (result.next()) {
                resultNotification.add(new Notification(result.getInt("ID"),result.getInt("auctionId"),0, result.getInt("status"), ""));
            }
            return resultNotification;
        } catch (SQLException ex) {
            Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static ArrayList<Notification> getWinners(int userId) {
        try {
            String Query = "SELECT notifications.ID ,items.Name ,notifications.status ,items.ID as itemId  FROM `notifications` JOIN `items` on notifications.itemId = `items`.`ID` JOIN `inventories` ON `inventories`.`ID` = `items`.`InventoryID` WHERE `inventories`.`SellerID` = ? AND `notifications`.`status` = '0'";
            PreparedStatement excutequery = Model.generateQuery(Query, userId);

            ResultSet result = excutequery.executeQuery();
            ArrayList<Notification> resultNotification = new ArrayList<>();
            while (result.next()) {
                resultNotification.add(new Notification(result.getInt("ID"), 0, result.getInt("itemId"), result.getInt("status"), ""));
            }
            return resultNotification;
        } catch (SQLException ex) {
            Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private static ArrayList<Notification> getStartedAuction(int userId) {
        
        return null;
    }
    public ArrayList<Notification> getUserNotifications(int userId) {
        Model.find(Notification.class);
        return null;
    }

    public static void main(String[] args) {
        System.out.println(Notification.getnewBids(1));
    }
    //SELECT `notifications`.`ID`, `notifications`.`auctionId` FROM notifications JOIN auctions ON notifications.auctionId = auctions.ID JOIN bids ON bids.AuctionID = auctions.ID JOIN users ON users.ID = bids.UserID WHERE (SELECT COUNT(*) FROM bids WHERE bids.UserID = 3 AND bids.AuctionID = auctions.ID) > 0

    //SELECT `notifications`.`ID`, `notifications`.`auctionId`,(SELECT users.Name FROM users WHERE users.ID = bids.UserID) as name ,bids.Price , items.Name as itemName FROM items,notifications JOIN auctions ON notifications.auctionId = auctions.ID JOIN bids ON bids.AuctionID = auctions.ID JOIN users ON users.ID = auctions.UserID WHERE users.ID = 1 AND items.ID = auctions.ItemID
    /*SELECT `notifications`.`ID`, `notifications`.`auctionId`,(SELECT users.Name FROM users WHERE users.ID = bids.UserID) as Name ,bids.Price , items.Name as itemName FROM items,notifications JOIN auctions ON notifications.auctionId = auctions.ID JOIN bids ON bids.AuctionID = auctions.ID JOIN users ON users.ID = auctions.UserID WHERE users.ID = 1 AND items.ID = auctions.ItemID
UNION
SELECT `notifications`.`ID`, `notifications`.`auctionId` , users.Name , bids.Price , items.Name as itemName FROM items, notifications JOIN auctions ON notifications.auctionId = auctions.ID JOIN bids ON bids.AuctionID = auctions.ID JOIN users ON users.ID = bids.UserID WHERE (SELECT COUNT(*) FROM bids WHERE bids.UserID = 3 AND bids.AuctionID = auctions.ID) > 0 AND items.ID = auctions.ItemID*/
}
