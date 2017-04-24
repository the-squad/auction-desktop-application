package models;


/**
 *
 * @author Mohamed
 */
public class SellerReport extends Model<SellerReport>{

    private int _id;
    private int _userID;
    private int _sellerID;
    private String _message;

    protected SellerReport() {
    }

    public SellerReport(int userID, int sellerID, String message) {
        this._userID = userID;
        this._sellerID = sellerID;
        this._message = message;
    }

    public int getId() {
        return _id;
    }

    public int getUserID() {
        return _userID;
    }

    public SellerReport setUserID(int userID) {
        this._userID = userID;
        return this;
    }

    public int getSellerID() {
        return _sellerID;
    }

    public SellerReport setSellerID(int sellerID) {
        this._sellerID = sellerID;
        return this;
    }

    public String getMessage() {
        return _message;
    }

    public SellerReport setMessage(String message) {
        this._message = message;
        return this;
    }
}
