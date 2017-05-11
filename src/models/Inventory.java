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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Inventory extends Model<Inventory> implements IReadOnlyInventory{

    private int _id;
    private int _sellerID;
    ArrayList<Item> items;

    public Inventory() {
    }

    public Inventory(int sellerID) {
        this._sellerID = sellerID;
        items=new ArrayList<>();
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
    
    public void deleteItem(int itemID)
    {
        Item item =items.stream().filter(a->a.getId()==itemID).findFirst().get();
        items.remove(item);
        
        item.delteItem(itemID);
        
    }
    public ArrayList<Item> getItems() {
        if (items == null) {
            items = new ArrayList(Model.find(Item.class, "InventoryID = ?", this.getId()));
        }
        return items;
    }
}
