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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import javafx.scene.image.Image;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;

public class ImageUtils {

    public static BufferedImage fxImageToBufferedImage(javafx.scene.image.Image image) {
        return SwingFXUtils.fromFXImage(image, null);
    }

    public static Image bufferedImageToFXImage(BufferedImage BImage, int width, int height) {
        return new Image(new ByteArrayInputStream(bufferedImageToByteArray(BImage)), width, height, true, true);
    }

    public static byte[] bufferedImageToByteArray(BufferedImage BImage) {
        try {
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ImageIO.write(BImage, "jpg", bs);
            return bs.toByteArray();
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public static BufferedImage byteArrayToBufferedImage(byte[] image) {
        try {
            return ImageIO.read(new ByteArrayInputStream(image));
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public static BufferedImage cropImage(BufferedImage Image, int width, int height) {
        int length = Image.getHeight() > Image.getWidth() ? Image.getWidth() : Image.getHeight();
        int newWidth = Image.getWidth() == length ? length : Image.getHeight() * width / height;
        int newHeight = Image.getHeight() == length ? length : Image.getWidth() * height / width;
        if (newHeight > Image.getHeight()) {
            newHeight = Image.getHeight();
            newWidth = newHeight * width / height;
        } else if (newWidth > Image.getWidth()) {
            newWidth = Image.getWidth();
            newHeight = newWidth * height / width;
        }
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_3BYTE_BGR);
        int x = Image.getWidth() - newWidth;
        x = x > 0 ? x / 2 : 0;
        int y = Image.getHeight() - newHeight;
        y = y > 0 ? y / 2 : 0;
        newImage.getGraphics().drawImage(Image, 0, 0, newWidth, newHeight, x, y, x + newWidth, y + newHeight, null);
        return newImage;
    }

    public static Image cropAndConvertImage(BufferedImage Image, int width, int height) {
        return bufferedImageToFXImage(cropImage(Image, width, height), width, height);
    }

    public static BufferedImage scale(BufferedImage sbi, int dWidth, int dHeight, double fWidth, double fHeight) {
        BufferedImage dbi = null;
        if (sbi != null) {
            dbi = new BufferedImage(dWidth, dHeight, BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D g = dbi.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
            g.drawRenderedImage(sbi, at);
        }
        return dbi;
    }
}
