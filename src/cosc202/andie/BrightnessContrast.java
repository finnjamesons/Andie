package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to change the brightness and/or contrast of an image.
 * </p>
 * 
 * <p>
 * This operation changes the brightness and/or contrast of an image by taking
 * two percentage changes for each aspect and adjusts them using a formula.
 * </p>
 * 
 * @author Dominic Tekanene & Angus Henderson
 * @version 1.0
 */
public class BrightnessContrast implements ImageOperation, java.io.Serializable {

    private double brightnessChange;
    private double contrastChange;

    /**
     * <p>
     * Create a new BrightnessContrast operation.
     * </p>
     * 
     * <p>
     * The brightnessChange is the percentage change in brightness to be applied
     * to a new image.
     * The same applies for contrastChange.
     * </p>
     * 
     * @param brightnessChange The new change in brightness
     * @param contrastChange   The new change in contrast
     */
    BrightnessContrast(int brightnessChange, int contrastChange) {
        this.brightnessChange = brightnessChange;
        this.contrastChange = contrastChange;
    }

    /**
     * <p>
     * Apply brightness and contrast conversion to an image.
     * </p>
     * 
     * <p>
     * The change of brightness and contrast uses the formula in the
     * changeBrightnessContrast method below. It is limited by the range of pixel
     * values ([0,255]) by the truncate method.
     * </p>
     * 
     * @param input The image to have it's brightness and contrast altered
     * @return The resulting image.
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

                // applying the change in brightness and contrast to the individual colours
                r = truncate(changeBrightnessContrast(r));
                g = truncate(changeBrightnessContrast(g));
                b = truncate(changeBrightnessContrast(b));

                // re-initialising and setting the new pixel value
                argb = (a << 24) | (r << 16) | (g << 8) | b;
                input.setRGB(x, y, argb);
            }
        }
        return input;
    }

    /**
     * <p>
     * Ensure an Integer value remains in [0,255].
     * </p>
     * 
     * <p>
     * If the input value is less than 0, it returns 0.
     * If the input value is greater than 255, it returns 255.
     * Otherwise, the input value is returned
     * </p>
     * 
     * @param input The integer value to be truncated.
     * @return The integer value in [0,255].
     */
    public int truncate(int input) {
        if (input < 0) {
            return 0;
        } else if (input > 255) {
            return 255;
        } else {
            return input;
        }

    }

    /**
     * <p>
     * Apply a brightness and contrast change to a pixel channel value.
     * </p>
     * 
     * <p>
     * This method takes the channel value (RGB) from a pixel and applies the
     * brightness and
     * contrast changes using the standard formula.
     * </p>
     * 
     * @param v The pixel channel value.
     * @return The new pixel channel value with the brightness and contrast changes.
     */
    public int changeBrightnessContrast(int v) {
        double output;
        output = (1 + (contrastChange / 100)) * (v - 127.5) + 127.5 * (1 + (brightnessChange / 100));
        return (int) Math.round(output);
    }

}
