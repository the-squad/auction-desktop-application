package models;

/**
 *
 * @author Mohamed
 */
public class SubscribeSeller extends Model<SubscribeSeller>{

    private int _id;
    private int _subscriberID;
    private int _selleID;

    protected SubscribeSeller() {
    }

    public SubscribeSeller(int subscriberID, int selleID) {
        this._subscriberID = subscriberID;
        this._selleID = selleID;
    }

    public int getId() {
        return _id;
    }

    public int getSubscriberID() {
        return _subscriberID;
    }

    public void setSubscriberID(int subscriberID) {
        this._subscriberID = subscriberID;
    }

    public int getSelleID() {
        return _selleID;
    }

    public void setSelleID(int selleID) {
        this._selleID = selleID;
    }
}
