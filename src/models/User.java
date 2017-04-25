/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Mohamed
 */
public class User extends Model<User> {

    private int _id;
    private int _userTypeID;
    private String _name;
    private String _email;
    private String _phone;
    private String _address;
    private byte[] _photo;

    public User(int userTypeID, String email) {
        this._userTypeID = userTypeID;
        this._email = email;
    }

    protected User() {
    }

    public Integer getId() {
        return _id;
    }

    public int getUserTypeID() {
        return _userTypeID;
    }

    public String getName() {
        return _name;
    }

    public User setName(String _name) {
        this._name = _name;
        return this;
    }

    public String getEmail() {
        return _email;
    }

    public User setEmail(String _email) {
        this._email = _email;
        return this;
    }

    public String getPhone() {
        return _phone;
    }

    public User setPhone(String _phone) {
        this._phone = _phone;
        return this;
    }

    public String getAddress() {
        return _address;
    }

    public User setAddress(String _address) {
        this._address = _address;
        return this;
    }

    public BufferedImage getPhoto() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new ByteArrayInputStream(_photo));
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return image;
    }

    public User setPhoto(byte[] _photo) {
        this._photo = _photo;
        return this;
    }

    @Override
    public String toString() {
        return "User{" + "_id=" + _id + ", _userTypeID=" + _userTypeID + ", _name=" + _name + ", _email=" + _email + ", _phone=" + _phone + ", _address=" + _address + '}';
    }

    public User login(String email, String password) {
        // TODO
        return null;
    }

    public User signUp(String name, String email, String password , int userType) {
        return null;
        // TODO
    }

    public User getUserData() {
        return this;
    }

    public Boolean deleteAccount(String password) {
        // TODO
        return false;
    }

    public int checkEmail(String email) {
        return 0;
    }
}
