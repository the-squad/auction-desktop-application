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
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Image extends Model<Image> {

    private int _id;
    private int _itemID;
    private byte[] _image;

    protected Image() {
    }

    public Image(int itemID) {
        this._itemID = itemID;
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

    public BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new ByteArrayInputStream(_image));
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return image;
    }

    public void setImage(byte[] image) {
        this._image = image;
    }

    public Image setImage(BufferedImage photo) {
        /*photo = ImageUtils.cropImage(photo, 750, 500);
        photo = ImageUtils.scale(photo, 1125, 750, 1125f / photo.getWidth(), 750f / photo.getHeight());*/
        this._image = ImageUtils.bufferedImageToByteArray(photo);
        return this;
    }
}
