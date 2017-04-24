package models;


/**
 *
 * @author Mohamed
 */

public class Item extends Model<Item>{


    private int _id;
    private int _inventoryID;
    private int _categoryID;
    private String _name;
    private int _quantity;

    protected Item() {
    }

    public Item(int inventoryID, int categoryID, String name, int quantity) {
        this._inventoryID = inventoryID;
        this._categoryID = categoryID;
        this._name = name;
        this._quantity = quantity;
    }

    public int getId() {
        return _id;
    }

    public int getInventoryID() {
        return _inventoryID;
    }

    public Item setInventoryID(int inventoryID) {
        this._inventoryID = inventoryID;
        return this;
    }

    public int getCategoryID() {
        return _categoryID;
    }

    public Item setCategoryID(int categoryID) {
        this._categoryID = categoryID;
        return this;
    }

    public String getName() {
        return _name;
    }

    public Item setName(String name) {
        this._name = name;
        return this;
    }

    public int getQuantity() {
        return _quantity;
    }

    public Item setQuantity(int quantity) {
        this._quantity = quantity;
        return this;
    }
    
}
