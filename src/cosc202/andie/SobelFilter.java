package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a Vertical or Horizontal Sobel filter.
 * </p>
 * 
 * <p>
 * A Sobel filter
 * </p>
 * 
 * @see ConvolutionOperation.java
 * @author Angus Henderson and Finn O'Neill
 * @version 1.0
 */
public class SobelFilter implements ImageOperation, java.io.Serializable {

    /**
     * The direction of the filter to apply. True is vertical, false is horizontal.
     */
    private boolean direction;
    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;
    private static final float[][] KERNELARRAYS = {
            { -1 / 2, -1, -1 / 2, 0, 0, 0, 1 / 2, 1, 1 / 2 },
            { -1 / 2, 0, 1 / 2, -1, 0, 1, -1 / 2, 0, 1 / 2 }
    };

    /**
     * <p>
     * Construct a Sobel filter with the given direction.
     * </p>
     * 
     * @param direction The radius of the newly constructed MeanFilter
     */
    SobelFilter(boolean direction) {
        this.direction = direction;
    }

    /**
     * <p>
     * Construct a Sobel filter with the default direction (vertical).
     * </p>
     * 
     * @see MeanFilter(int)
     */
    SobelFilter() {
        this.direction = VERTICAL;
    }

    /**
     * <p>
     * Apply a Sobel filter to an image.
     * </p>
     * 
     * <p>
     * As with many filters, the Sobel filter is implemented via convolution.
     * The kernels for the connvolutions are stored in the KERNELARRAYS data field.
     * </p>
     * 
     * @param input The image to apply the Sobel filter to.
     * @return The resulting (filtered) image.
     */
    public BufferedImage apply(BufferedImage input) {
        float[] array = new float[9];
        // obtaining the kernel for the convolution
        if (direction == VERTICAL) {
            array = KERNELARRAYS[0];
        }
        if (direction == HORIZONTAL) {
            array = KERNELARRAYS[1];
        }
        // Applying the convolution op
        Kernel kernel = new Kernel(3, 3, array);
        ConvolutionOperation convOp = new ConvolutionOperation(kernel);
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null),
                input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);

        return output;
    }

}
