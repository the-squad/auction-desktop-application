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
public class UserType extends Model<UserType> {

    private int _id;
    private String _type;

    protected UserType() {
    }

    public UserType(String type) {
        this._type = type;
    }

    public int getId() {
        return _id;
    }

    public String getType() {
        return _type;
    }
}
