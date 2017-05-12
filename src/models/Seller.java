/*
 * The MIT License
 *
 * Copyright 2017 Contributors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package models;

import app.Validation;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Seller extends User implements IAuctionInterface {

    private ArrayList<Auction> auctions;

    public Auction createAuction(Item item, int ItemQuantity, String StartDate, String StartTime,
            String TerminationDate, String TerminationTime, double InitialPrice, double BidRate) {
        if (item.getQuantity() < ItemQuantity) {
            return null;
        }
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            Date startDate = df.parse(StartDate + " " + Validation.convertTimeTo24Hour(StartTime));
            Date terminationDate = df.parse(TerminationDate + " " + Validation.convertTimeTo24Hour(TerminationTime));
            if (startDate.compareTo(terminationDate) >= 0) {
                return null;
            }
            item.setQuantity(item.getQuantity() - ItemQuantity).save();
            Auction auction = new Auction(this.getId(), item.getId(), ItemQuantity, terminationDate, InitialPrice, BidRate).setStartDate(startDate);
            if (auction.create()) {
                this.auctions.add(auction);
                return auction;
            }
        } catch (ParseException e) {
            Logger.getGlobal().log(Level.WARNING, e.getMessage(), e);
        }
        return null;
    }

    public boolean deleteAuction(int id) {
        try {
            Auction auction = auctions.stream().filter(a -> a.getId() == id).findFirst().get();
            Model.delete(Auction.class, auction.getId());
            auctions.remove(auction);
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    public static void updateAuction(int auctionID , String StartDate, String StartTime,
        String TerminationDate, String TerminationTime, double InitialPrice, double BidRate) {

        Auction auc = Model.find(Auction.class, auctionID);
        
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            Date start_Date = df.parse(StartDate + " " + Validation.convertTimeTo24Hour(StartTime));
            Date terminate_Date = df.parse(TerminationDate + " " + Validation.convertTimeTo24Hour(TerminationTime));
            auc.setStartDate(start_Date).setTerminationDate(terminate_Date).setInitialPrice(InitialPrice).setBiddingRate(BidRate);

            auc.save();
        } catch (ParseException e) {
             Logger.getGlobal().log(Level.WARNING, e.getMessage(), e);
        }

    }

    public Item addItemToInventory(Inventory inventory , String name, int quantity, String category, String description) {
        return inventory.createItem(name, quantity, Model.find(Category.class, "Name=?", category).get(0), description);
    }

    public void deleteItemFromInventory(Inventory inventory , int itemID) {
        inventory.deleteItem(itemID);
    }

    public void updateItemInInventory() {
        // TODO
    }

    public int getFollowersNumber() {
        return Model.count(SubscribeSeller.class, "SelleID = ?", this.getId());
    }

    public static Seller getSellerData(int sellerId) {
        return Model.find(Seller.class, sellerId);
    }

    @Override
    public ArrayList<Auction> search(String itemName) {
        List<Auction> auctions = new ArrayList<>();
//        statement = Model.generateQuery("select auctions.* FROM auctions JOIN items ON items.ID = auctions.ItemID where auctions.UserID = ? AND items.Name LIKE '?%'",this.getId(),itemName);
        PreparedStatement statement = Model.generateQuery("select auctions.* FROM auctions JOIN items ON items.ID = auctions.ItemID where auctions.UserID = ? AND items.Name = ?", this.getId(), itemName);
        try {
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                Auction auctionObject = new Auction();
                auctionObject.setUserID(resultSet.getInt("UserID"));
                auctionObject.setItemID(resultSet.getInt("ItemID"));
                auctionObject.setItemQuantity(resultSet.getInt("ItemQuantity"));
                auctionObject.setStartDate(resultSet.getDate("StartDate"));
                auctionObject.setTerminationDate(resultSet.getDate("TerminationDate"));
                auctionObject.setInitialPrice(resultSet.getDouble("InitialPrice"));
                auctionObject.setBiddingRate(resultSet.getDouble("BidRate"));
                auctions.add(auctionObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (ArrayList<Auction>) auctions;
    }

    @Override
    public ArrayList<Auction> getAuctions() {
        if (auctions == null) {
            auctions = new ArrayList<>(Model.find(Auction.class, "UserID = ?", this.getId()));
        }
        return auctions;
    }
    
    public ArrayList<Item> getItems(Inventory inventory)
    {
        return inventory.getItems();
    }
    
    public boolean checkFollow(int userId)
    {
        return Model.find(SubscribeSeller.class , "SelleID = ? and SubscriberID = ?" ,this.getId() , userId).size() == 1;
    }
}
