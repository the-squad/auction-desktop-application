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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Buyer extends User implements IAuctionInterface {

    public void reportSeller(int sellerId, String complaint) {
        new SellerReport(this.getId(), sellerId, complaint).create();
    }

    public void reportAuction(int auctionId, String complaint) {
        new AuctionReport(this.getId(), auctionId, complaint).create();
    }

    public ArrayList<Item> getItems() {
        return null;
        // TODO
    }

    public boolean makeBid(Auction auction, double money) {
        return auction.bidAuction(money, this.getId());
    }

    public boolean followSeller(User seller) {
        return new SubscribeSeller(this.getId(), seller.getId()).create();
    }

    public void unFollowSeller(int sellerID) {
        PreparedStatement statement = Model.generateQuery("DELETE FROM subscribe_sellers WHERE SubscriberID = ? AND SelleID = ?", this.getId(), sellerID);
        try {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void subscribeAuction(int auctionID) {
        SubscribeAuction SubscribeAuctionObject = new SubscribeAuction(auctionID, this.getId());
        SubscribeAuctionObject.create();
    }

    public ArrayList<Auction> getFeed() {
        List<Auction> auctions = new ArrayList<>();
        PreparedStatement[] statement = new PreparedStatement[2];

        statement[0] = Model.generateQuery("SELECT auctions.* from auctions JOIN subscribe_auctions ON auctions.ID = subscribe_auctions.AuctionID WHERE subscribe_auctions.SubscriberID = ?", this.getId());
        statement[1] = Model.generateQuery("SELECT auctions.* from auctions JOIN subscribe_sellers ON auctions.UserID = subscribe_sellers.SelleID WHERE subscribe_sellers.SubscriberID = ?", this.getId());

        List<Integer> auctionIDs = new ArrayList<>();
        for (int counter = 0; counter < 2; counter++) {
            try {
                statement[counter].execute();
                ResultSet resultSet = statement[counter].getResultSet();
                while (resultSet.next()) {
                    if (!auctionIDs.contains(resultSet.getInt("ID"))) {
                        auctionIDs.add(resultSet.getInt("ID"));
                        Auction auctionObject = new Auction();
                        auctionObject.setUserID(resultSet.getInt("UserID"));
                        auctionObject.setItemID(resultSet.getInt("ItemID"));
                        auctionObject.setItemQuantity(resultSet.getInt("ItemQuantity"));
                        auctionObject.setStartDate(resultSet.getDate("StartDate"));
                        auctionObject.setTerminationDate(resultSet.getDate("TerminationDate"));
                        auctionObject.setInitialPrice(resultSet.getDouble("InitialPrice"));
                        auctionObject.setBidRate(resultSet.getDouble("BidRate"));
                        auctions.add(auctionObject);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return (ArrayList<Auction>) auctions;
    }

    public ArrayList<Auction> exploreAuctions(Category category) {
        if (category.getName().equals("All"))
            return new ArrayList<>(Model.find(Auction.class));

        List<Item> items = Model.find(Item.class, "CategoryID = ?", category.getId());
        if (items.size() == 0) return null;
        String keys = "`ItemID` in (";
        keys = items.stream().map((item) -> "?,").reduce(keys, String::concat);
        return new ArrayList<>(Model.find(Auction.class, keys.replaceFirst(",$", ")"), items.stream().map(i -> (Object) i.getId()).toArray()));
    }

    public ArrayList<Auction> search(double price, int status, int numberOfBidders) {
        List<Auction> auctions = new ArrayList<>();
        PreparedStatement statement = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date today = new Date();
        if (price != -1 && status != -1 && numberOfBidders != -1) // search with all options
        {
            if (status == 1) {
                statement = Model.generateQuery("SELECT * from auctions JOIN bids on auctions.ID = bids.AuctionID where InitialPrice <= ? AND ? BETWEEN timestamp(StartDate) AND timestamp(TerminationDate) GROUP BY bids.UserID having COUNT(DISTINCT bids.UserID) <= ?", price, dateFormat.format(today), numberOfBidders);
            } else {
                statement = Model.generateQuery("SELECT * from auctions JOIN bids on auctions.ID = bids.AuctionID where InitialPrice <= ? AND ? NOT BETWEEN timestamp(StartDate) AND timestamp(TerminationDate) GROUP BY bids.UserID having COUNT(DISTINCT bids.UserID) <= ?", price, dateFormat.format(today), numberOfBidders);
            }

        } else if (price != -1 && status == -1 && numberOfBidders == -1) // search with price only
        {
            auctions = Model.find(Auction.class, "InitialPrice <= ?", price);
        } else if (price != -1 && status != -1 && numberOfBidders == -1) // search with price , status
        {
            if (status == 1) {
                auctions = Model.find(Auction.class, "InitialPrice <= ? AND ? BETWEEN timestamp(StartDate) AND timestamp(TerminationDate)", price, dateFormat.format(today));
            } else {
                auctions = Model.find(Auction.class, "InitialPrice <= ? AND ? NOT BETWEEN timestamp(StartDate) AND timestamp(TerminationDate)", price, dateFormat.format(today));
            }
        } else if (price != -1 && status == -1 && numberOfBidders != -1) // search with price , bidders
        {
            if (status == 1) {
                statement = Model.generateQuery("SELECT * from auctions JOIN bids on auctions.ID = bids.AuctionID where ? BETWEEN timestamp(StartDate) AND timestamp(TerminationDate) GROUP BY bids.UserID having COUNT(DISTINCT bids.UserID) <= ?", dateFormat.format(today), numberOfBidders);
            } else {
                statement = Model.generateQuery("SELECT * from auctions JOIN bids on auctions.ID = bids.AuctionID where ? NOT BETWEEN timestamp(StartDate) AND timestamp(TerminationDate) GROUP BY bids.UserID having COUNT(DISTINCT bids.UserID) <= ?", dateFormat.format(today), numberOfBidders);
            }

        } else if (price == -1 && status != -1 && numberOfBidders == -1) // search with status only
        {
            if (status == 1) {
                auctions = Model.find(Auction.class, "? BETWEEN timestamp(StartDate) AND timestamp(TerminationDate)", dateFormat.format(today));
            } else {
                auctions = Model.find(Auction.class, "? NOT BETWEEN timestamp(StartDate) AND timestamp(TerminationDate)", dateFormat.format(today));
            }
        } else if (price == -1 && status == -1 && numberOfBidders != -1) // search with bidders only
        {
            statement = Model.generateQuery("SELECT * from auctions JOIN bids on auctions.ID = bids.AuctionID GROUP BY bids.UserID having COUNT(DISTINCT bids.UserID) = ?", numberOfBidders);
        }
        if (statement != null) {
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
                    auctionObject.setBidRate(resultSet.getDouble("BidRate"));
                    auctions.add(auctionObject);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return (ArrayList<Auction>) auctions;
    }

    @Override
    public ArrayList<Auction> search(String query) {
        // TODO
        return null;
    }

    @Override
    public ArrayList<Auction> getAuction() {
        return null;
        // TODO
    }

}
