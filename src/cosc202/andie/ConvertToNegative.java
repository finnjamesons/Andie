package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to convert an image from colour to negative.
 * </p>
 * 
 * @author Dominic Tekanene
 * @version 1.0
 */
public class ConvertToNegative implements ImageOperation, java.io.Serializable {

    /**
     * <p>
     * Create a new CovertToNegative operation.
     * </p>
     */
    ConvertToNegative() {

    }

    /**
     * <p>
     * ImageOperation to invert the colour of an image.
     * </p>
     * 
     * <p>
     * This operation inverts the colours in an image by taking 255 as a reference
     * to the maximum
     * amount of colour within a channel and minusing the value of that colour from
     * that reference.
     * </p>
     * 
     * @param input The image to have it's brightness and contrast altered
     * @return The resulting image.
     * 
     * @author Dominic Tekanene
     * @version 1.0
     */
    public BufferedImage apply(BufferedImage input) {
        // Iterating over every pixel in the buffered image
        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {

                // extracting the ARGB values from each pixel
                int argb = input.getRGB(x, y);
                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                // minusing the value of the colour from 255
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;

                // re-initialising and setting the new pixel value
                argb = (a << 24) | (r << 16) | (g << 8) | b;
                input.setRGB(x, y, argb);
            }
        }
        return input;
    }
}