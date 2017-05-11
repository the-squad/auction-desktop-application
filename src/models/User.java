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

import java.awt.image.BufferedImage;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;

public class User extends Model<User> {

    private int _id;
    private int _userTypeID;
    private String _name;
    private String _email;
    private String _phone;
    private String _address;
    private String _password;
    private byte[] _photo;

    private static BufferedImage defaultImage = ImageUtils.fxImageToBufferedImage(new Image(User.class.getResourceAsStream("/assets/default-user.jpg")));

    public User(int userTypeID, String email) {
        this();
        this._userTypeID = userTypeID;
        this._email = email;
    }

    public User() {}

    public Integer getId() {
        return _id;
    }

    public int getUserTypeID() {
        return _userTypeID;
    }

    public String getPassword() {
        return _password;
    }

    public User setPassword(String _password) {
        this._password = _password;
        return this;
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
        if (_photo == null)
            return defaultImage;
        return ImageUtils.byteArrayToBufferedImage(_photo);
    }

    public User setPhoto(BufferedImage photo) {
        photo = ImageUtils.cropImage(photo,300, 300);
        photo = ImageUtils.scale(photo, 300, 300, 300f/photo.getHeight(), 300f/photo.getHeight());
        this._photo = ImageUtils.bufferedImageToByteArray(photo);
        return this;
    }

    public User setPhoto(javafx.scene.image.Image photo) {
        return setPhoto(ImageUtils.fxImageToBufferedImage(photo));
    }

    public User setPhoto(byte[] _photo) {
        this._photo = _photo;
        return this;
    }

    public static User login(String email, String password) {
        List<User> users = new ArrayList<>();
        users = Model.find(User.class, "email = ? AND password = ?", email, password);
        if (users.size() == 1) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public static User signUp(String name, String email, String password, int userType) {
        User user = new User(userType, email);
        user.setName(name);
        user.setPassword(password);
        if (user.create()) {
            return user;
        } else {
            return null;
        }
    }

    public User getUserData() {
        return this;
    }

    public Boolean deleteAccount(String password) {
        // TODO
        return false;
    }

    public static int checkEmail(String email) {
        List<User> users = Model.find(User.class, "Email = ?", email);
        if (users.size() > 0) {
            if (users.get(0).getUserTypeID() == 1) {
                return 2;
            } else {
                return 0;
            }
        } else {
            return 1;
        }
    }
}
