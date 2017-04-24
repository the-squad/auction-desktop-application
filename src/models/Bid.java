/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Mohamed
 */
public class Bid extends Model<Bid>{
    private int _id;
    private int _userID;
    private int _auctionID;
    private double _price;

    protected Bid() {
    }

    public Bid(int userID, int auctionID, double price) {
        this._userID = userID;
        this._auctionID = auctionID;
        this._price = price;
    }

    public int getId() {
        return _id;
    }

    public int getUserID() {
        return _userID;
    }

    public int getAuctionID() {
        return _auctionID;
    }

    public double getPrice() {
        return _price;
    }
    
}
