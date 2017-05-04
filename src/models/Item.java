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

public class Item extends Model<Item>{


    private int _id;
    private int _inventoryID;
    private int _categoryID;
    private String _name;
    private int _quantity;
    private String _description;
    private ArrayList<Image> images;

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

<<<<<<< HEAD
    public Item setDescription(String _description) {
        this._description = _description;
        return this;
    }

    public String getDescription() {
        return _description;
    }
    
=======
>>>>>>> 2ae5bedfbb29852291bdaea209a377ce98a7fd78
    public void delteItem(int itemID)
    {
        Model.delete(Item.class, itemID);
    }
    
    public ArrayList<Image> getIamgesItem()
    {
        this.images = new ArrayList<>(Model.find(Image.class, "itemID = ?", this._id));
        return this.images;
    }
    

}
