/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author mohamedkamal
 */
public class Notification extends Model<Notification>{
    private int _id;
    private int _auctionId;
    private int _itemId;
    private int _status;
    public Notification(){}
    
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
    
}
