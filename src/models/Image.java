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
public class Image extends Model<Image>{

    private int _id;
    private int _itemID;
    private byte[] _image;

    protected Image() {
    }

    public Image(int itemID, byte[] image) {
        this._itemID = itemID;
        this._image = image;
    }

    public int getId() {
        return _id;
    }

    public int getItemID() {
        return _itemID;
    }

    public void setItemID(int itemID) {
        this._itemID = itemID;
    }

    public byte[] getImage() {
        return _image;
    }

    public void setImage(byte[] image) {
        this._image = image;
    }    
}
