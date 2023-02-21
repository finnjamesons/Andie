package cosc202.andie;

import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * ImageOperation to apply a Median (Another blur) filter.
 * 
 * 
 */
public class MedianFilter implements ImageOperation, java.io.Serializable {

    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a
     * 5x5 filter, and so forth.
     */
    private int radius;

    /**
     * <p>
     * Construct a Median filter with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius' of the local neighbourhood used.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * Larger filters give a stronger blurring effect.
     * </p>
     * 
     * @param radius The radius of the newly constructed MedianFilter
     */
    MedianFilter(int radius) {
        this.radius = radius;
    }

    /**
     * <p>
     * Construct a Median filter with the default size.
     * </p
     * >
     * <p>
     * By default, a Median filter has radius 1.
     * </p>
     * 
     * @see MedianFilter(int)
     */
    MedianFilter() {
        this.radius = 1;
    }

    /**
     * Apply a median blur to an image
     * 
     * The size of the blur depends on the {@link radius}
     * Larger radii lead to stronger blurring.
     * 
     * @param input The image to be blurred
     * @return The blurred image
     */
    public BufferedImage apply(BufferedImage input) {

        // Initialises a copy of the input BufferedImage to apply changes to
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null),
                input.isAlphaPremultiplied(), null);

        // Sets the height, width and size.
        int height = input.getHeight();
        int width = input.getWidth();

        // The total number of elements visible in a window of a given radius
        int windowSize = (2 * radius + 1) * (2 * radius + 1);

        // The outer two loops visit each pixel in the input image
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {

                // Intiialises an array to store each value in the window visible at x,y
                int[] window = new int[windowSize];
                // Used to track the current position in the window array
                int position = 0;

                // The inner two loops visit each pixel in a window of a given radius
                for (int dy = -radius; dy <= radius; ++dy) {
                    for (int dx = -radius; dx <= radius; ++dx) {

                        // Checks that the current coordinate of the window is in the bounds of the
                        // input image. Where the window is not inbounds, it repeats the edge pixel.
                        int ydy = Math.min(Math.max(0, y + dy), height - 1);
                        int xdx = Math.min(Math.max(0, x + dx), width - 1);

                        // Adds all of the values of the current window to the window array to be sorted
                        window[position] = input.getRGB(xdx, ydy);
                        position++;

                    }
                }

                // Intitalises the medianPixel of the window
                int medianPixel = getMedianPixel(window);
                // Sets the value of the output image at x,y to that of the median pixel of the
                // window
                output.setRGB(x, y, medianPixel);

            }
        }

        return output;
    }

    /**
     * Gets the median aRGB pixel value of a given array of aRGB pixels
     * 
     * This method works by finding the median value for each colour channel and
     * returning them as a single aRGB int
     * 
     * @param rgbArray
     * @return median aRGB int value
     */
    private int getMedianPixel(int[] rgbArray) {

        // Sets the size equal to that of the input array
        int size = rgbArray.length;
        // Creates an array for each colour channel
        int[] alpha = new int[size];
        int[] red = new int[size];
        int[] green = new int[size];
        int[] blue = new int[size];

        // Loops over every number in the input array
        for (int i = 0; i < size; i++) {

            int pixel = rgbArray[i];
            // Splits each arRGB int into their a,r,g,b values and adds them to their
            // respective channel's array
            alpha[i] = (pixel & 0xFF000000) >> 24;
            red[i] = (pixel & 0x00FF0000) >> 16;
            green[i] = (pixel & 0x0000FF00) >> 8;
            blue[i] = (pixel & 0x000000FF);

        }
        // Takes the median value for each of the colour channels
        int a = medianOfArray(alpha);
        int r = medianOfArray(red);
        int g = medianOfArray(green);
        int b = medianOfArray(blue);
        // Combines those values back into a single aRGB pixel value
        int medianPixel = (a << 24) | (r << 16) | (g << 8) | b;

        return medianPixel;
    }

    /**
     * Find the Median of an int array
     * 
     * This method returns the median (middle) value of a given int array
     * If the array is odd, it returns the mean (average) value of the middle two
     * values.
     * 
     * @param array
     * @return The median value of array
     */
    private int medianOfArray(int[] array) {

        // First sorts the array
        Arrays.sort(array);

        // Checks if the array is odd
        if (array.length % 2 != 0) {
            // Returns the middle value
            return array[(array.length / 2 + 1)];
        }

        // Else returns the mean of the middle two values
        return (array[(array.length / 2 + 1)] + array[(array.length / 2 - 1)] / 2);

    }

}
