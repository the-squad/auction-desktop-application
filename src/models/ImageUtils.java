package models;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;

/**
 *
 * @author Mohamed
 */
public class ImageUtils {

    public static BufferedImage fxImageToBufferedImage(javafx.scene.image.Image image) {
        return SwingFXUtils.fromFXImage(image, null);
    }

    public static javafx.scene.image.Image bufferedImageToFXImage(BufferedImage BImage) {
        return new javafx.scene.image.Image(new ByteArrayInputStream(bufferedImageToByteArray(BImage)));
    }

    public static byte[] bufferedImageToByteArray(BufferedImage BImage) {
        try {
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ImageIO.write(BImage, "PNG", bs);
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

    public static BufferedImage squareImage(BufferedImage Image) {
        int length = Image.getHeight() > Image.getWidth() ? Image.getWidth() : Image.getHeight();
        BufferedImage newImage = new BufferedImage(length, length, BufferedImage.TYPE_4BYTE_ABGR);
        newImage.getGraphics().drawImage(Image, 0, 0, null);
        return newImage;
    }
    
    public static void main(String[] args) throws IOException {
        BufferedImage b = ImageIO.read(new File("D:/Drive/Desktop/1.PNG"));
        b = squareImage(b);
        ImageIO.write(b, "PNG", new File("D:/Drive/Desktop/1.PNG"));
    }
}
