/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;

/**
 *
 * @author Mohamed
 */
public class Auction extends Model<Auction> {

    private int _id;
    private int _userID;
    private int _itemID;
    private int _itemQuantity;
    private Date _startDate;
    private Date _terminationDate;
    private double _initialPrice;
    private double _bidRate;

    protected Auction() {
    }

    public Auction(int userID, int itemID, int itemQuantity, Date terminationDate, double initialPrice, double bidRate) {
        this._userID = userID;
        this._itemID = itemID;
        this._itemQuantity = itemQuantity;
        this._terminationDate = terminationDate;
        this._initialPrice = initialPrice;
        this._bidRate = bidRate;
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

}
