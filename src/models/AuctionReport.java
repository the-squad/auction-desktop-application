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
public class AuctionReport extends Model<AuctionReport>{

    private int _id;
    private int _userID;
    private int _auctionID;
    private String _message;

    protected AuctionReport() {
    }

    public AuctionReport(int userID, int auctionID, String message) {
        this._userID = userID;
        this._auctionID = auctionID;
        this._message = message;
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

    public String getMessage() {
        return _message;
    }
    
}
