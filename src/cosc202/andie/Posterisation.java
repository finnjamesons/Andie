package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to Posterise an image.
 * </p>
 * 
 * <p>
 * This operation changes the colours of an image to the nearest posterised
 * value, based on the number of bands specified.
 * </p>
 * 
 * @author Finn O'Neill & Angus Henderson
 * @version 1.0
 */
public class Posterisation implements ImageOperation, java.io.Serializable {

    private int numBands;
    private int[] bandValues;

    public Posterisation() {
        numBands = 1;
        setBandValues();
    }

    /**
     * <p>
     * Create a new Posterisation operation.
     * </p>
     * 
     * <p>
     * Sets the data field to be the specified number of colour bands.
     * </p>
     * 
     * @param numBands The number of colour bands
     */
    public Posterisation(int numBands) {
        this.numBands = numBands;
        setBandValues();
    }

    /**
     * <p>
     * Apply posterisation to an image.
     * </p>
     * 
     * <p>
     * Applies the posterisation change to each of the colours in every pixel. For
     * each pixel, the rgb values are obtained, then set to the nearest colour band
     * using the posterise value method.
     * </p>
     * 
     * @param input The image to be posterised.
     * @return The resulting image.
     */
    public BufferedImage apply(BufferedImage input) {
        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {

                // extracting the ARGB values from each pixel
                int argb = input.getRGB(x, y);
                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                // applying the change in brightness and contrast to the individual colours
                r = posteriseValue(r);
                g = posteriseValue(g);
                b = posteriseValue(b);

                // re-initialising and setting the new pixel value
                argb = (a << 24) | (r << 16) | (g << 8) | b;
                input.setRGB(x, y, argb);
            }
        }
        return input;
    }

    /**
     * <p>
     * Returns the closest integer band value for a given input, based on the number
     * of bands.
     * </p>
     * 
     * <p>
     * Creates an int array of the colour bands, from 0 to 255.
     * It then finds the closest value in the bandValues array for the given input
     * value.
     * </p>
     * 
     * @param input The integer value to be posterised.
     * @return The nearest integer value colour band.
     */
    public int posteriseValue(int input) {
        int lower = 0;
        int higher = 255;
        // Finding the nearest two band values
        for (int i : bandValues) {
            if (i <= input) {
                lower = i;
            } else if (i >= input) {
                higher = i;
                break;
            }
        }
        // Setting the output to be the closest band value
        if (input - lower < higher - input) {
            return lower;
        } else {
            return higher;
        }
    }

    /**
     * <p>
     * Sets the bandValues array to the pixel values based on the number of bands.
     * </p>
     */
    public void setBandValues() {
        bandValues = new int[numBands + 1];
        bandValues[0] = 0;
        bandValues[numBands] = 255;

        // Initialising band values
        for (int i = 1; i < numBands; i++) {
            bandValues[i] = i * (255 / (numBands + 1));
        }
    }

}
