package models;


/**
 *
 * @author Mohamed
 */
public class SubscribeAuction extends Model<SubscribeAuction>{

    private int _id;
    private int _auctionID;
    private int _subscriberID;

    protected SubscribeAuction() {
    }

    public SubscribeAuction(int auctionID, int subscriberID) {
        this._auctionID = auctionID;
        this._subscriberID = subscriberID;
    }

    public Integer getId() {
        return _id;
    }

    public int getAuctionID() {
        return _auctionID;
    }

    public void setAuctionID(int auctionID) {
        this._auctionID = auctionID;
    }

    public int getSubscriberID() {
        return _subscriberID;
    }

    public void setSubscriberID(int subscriberID) {
        this._subscriberID = subscriberID;
    }
}
