package models;

/**
 *
 * @author Mohamed
 */
public class Inventory extends Model<Inventory>{

    private int _id;
    private int _sellerID;

    protected Inventory() {
    }

    public Inventory(int sellerID) {
        this._sellerID = sellerID;
    }

    public int getId() {
        return _id;
    }

    public int getSellerID() {
        return _sellerID;
    }

    public void setSellerID(int sellerID) {
        this._sellerID = sellerID;
    }

}
