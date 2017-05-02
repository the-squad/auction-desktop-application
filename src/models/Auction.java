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

import java.util.ArrayList;
import java.util.Date;

public class Auction extends Model<Auction> {

    private int _id;
    private int _userID;
    private int _itemID;
    private int _itemQuantity;
    private Date _startDate;
    private Date _terminationDate;
    private double _initialPrice;
    private double _bidRate;
    private ArrayList<Bid> bids;

    protected Auction() {
    }

    public Auction(int userID, int itemID, int itemQuantity, Date terminationDate, double initialPrice, double bidRate) {
        this._userID = userID;
        this._itemID = itemID;
        this._itemQuantity = itemQuantity;
        this._terminationDate = terminationDate;
        this._initialPrice = initialPrice;
        this._bidRate = bidRate;
        bids = new ArrayList<>();
    }

    public int getId() {
        return _id;
    }

    public int getUserID() {
        return _userID;
    }

    public int getItemID() {
        return _itemID;
    }

    public Auction setItemID(int itemID) {
        this._itemID = itemID;
        return this;
    }

    public int getItemQuantity() {
        return _itemQuantity;
    }

    public Auction setItemQuantity(int itemQuantity) {
        this._itemQuantity = itemQuantity;
        return this;
    }

    public Date getStartDate() {
        return _startDate;
    }

    public Auction setStartDate(Date startDate) {
        this._startDate = startDate;
        return this;
    }

    public Date getTerminationDate() {
        return _terminationDate;
    }

    public Auction setTerminationDate(Date terminationDate) {
        this._terminationDate = terminationDate;
        return this;
    }

    public double getInitialPrice() {
        return _initialPrice;
    }

    public Auction setInitialPrice(double initialPrice) {
        this._initialPrice = initialPrice;
        return this;
    }

    public double getBidRate() {
        return _bidRate;
    }

    public Auction setBidRate(double bidRate) {
        this._bidRate = bidRate;
        return this;
    }
    
    public ArrayList<Bid> getBids() {
        if(bids == null)
        {
            bids = new ArrayList(Model.find(Bid.class, "AuctionID = ? ORDER BY Price DESC", this._id));
        }
        return bids;
    }
    
    public boolean bidAuction(double money, int userId) {
        if (bids == null) {
            bids = this.getBids();
        }
        if (money > this._bidRate && bids.get(0).getPrice() < money || bids.isEmpty()) {
            Bid bid = new Bid(userId, this._id, money);
            if (bid.create()) {
                bids.add(bid);
                return true;
            }
            else
                return false;
        } else {
            return false;
        }
    }
}
