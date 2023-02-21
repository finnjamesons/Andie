package cosc202.andie;

import java.awt.*;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to resize an image.
 * </p>
 * 
 * <p>
 * This class performs operations on a BufferedImage object to resize it
 * to different dimensions using a Graphics2D object.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Angus Henderson
 * @version 1.0
 */

public class ResizeImage implements ImageOperation, java.io.Serializable {
    public int resizedHeight;
    public int resizedWidth;
    public double resizedScale;

    /**
     * 
     * <p>
     * 
     * Create a new Resize operation.
     * 
     * </p>
     * 
     */

    public ResizeImage(int resizedHeight, int resizedWidth, double resizedScale) {
        this.resizedHeight = resizedHeight;
        this.resizedWidth = resizedWidth;
        this.resizedScale = resizedScale;

    }

    /**
     * 
     * <p>
     * 
     * Resize an image.
     * 
     * </p>
     *
     * 
     * 
     * <p>
     * 
     * FILL THIS OUT
     * 
     * </p>
     *
     * 
     * 
     * @param input The image to be resized
     * 
     * @return The resulting resized image.
     * 
     */

    public BufferedImage apply(BufferedImage inputImage) {

        Image resultingImage = inputImage.getScaledInstance(resizedWidth, resizedHeight, Image.SCALE_DEFAULT);

        BufferedImage outputImage = new BufferedImage(resizedWidth, resizedHeight, inputImage.getType());

        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);

        return outputImage;

    }

}
