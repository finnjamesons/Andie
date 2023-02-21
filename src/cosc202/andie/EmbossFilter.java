package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply an Emboss filter.
 * </p>
 * 
 * @author Angus Henderson & Finn O'Neill
 * @version 1.0
 */
public class EmbossFilter implements ImageOperation, java.io.Serializable {

    /**
     * The direction of the emboss filter.
     */
    private int direction;

    public static final int NORTHWEST = 0;
    public static final int NORTH = 1;
    public static final int NORTHEAST = 2;
    public static final int EAST = 3;
    public static final int SOUTHEAST = 4;
    public static final int SOUTH = 5;
    public static final int SOUTHWEST = 6;
    public static final int WEST = 7;

    // Kernels corresponding to the directions.
    private static final float[][] KERNELARRAYS = {
            { 1, 0, 0, 0, 0, 0, 0, 0, -1 },
            { 0, 1, 0, 0, 0, 0, 0, -1, 0 },
            { 0, 0, 1, 0, 0, 0, -1, 0, 0 },
            { 0, 0, 0, -1, 0, 1, 0, 0, 0 },
            { -1, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 0, -1, 0, 0, 0, 0, 0, 1, 0 },
            { 0, 0, -1, 0, 0, 0, 1, 0, 0 },
            { 0, 0, 0, 1, 0, -1, 0, 0, 0 }
    };

    /**
     * <p>
     * Construct an Emboss filter with the given direction.
     * </p>
     * 
     * <p>
     * The direction of the filter corresponds to the rotaion of the convolution
     * kernel used.
     * </p>
     * 
     * @param direction The direction of the newly constructed EmbossFilter
     */
    EmbossFilter(int direction) {
        this.direction = direction;
    }

    /**
     * <p>
     * Construct an Emboss filter with the default direction.
     * </p
     * >
     * <p>
     * By default, an Emboss filter has direction of North.
     * </p>
     * 
     * @see EmbossFilter(int)
     */
    EmbossFilter() {
        this(1);
    }

    /**
     * <p>
     * Apply an Emboss filter to an image.
     * </p>
     * 
     * <p>
     * As with many filters, the Emboss filter is implemented via convolution.
     * The direction of the convolution kernel is specified by the
     * {@link direction}.
     * </p>
     * 
     * @param input The image to apply the Emboss filter to.
     * @return The resulting image.
     */
    public BufferedImage apply(BufferedImage input) {
        float[] array = new float[9];
        // Setting the kernel for the corresponding direction
        switch (direction) {
            case NORTHWEST:
                array = KERNELARRAYS[0];
                break;
            case NORTH:
                array = KERNELARRAYS[1];
                break;
            case NORTHEAST:
                array = KERNELARRAYS[2];
                break;
            case EAST:
                array = KERNELARRAYS[3];
                break;
            case SOUTHEAST:
                array = KERNELARRAYS[4];
                break;
            case SOUTH:
                array = KERNELARRAYS[5];
                break;
            case SOUTHWEST:
                array = KERNELARRAYS[6];
                break;
            case WEST:
                array = KERNELARRAYS[7];
                break;
        }
        // applying the kernel with the convolution operation
        Kernel kernel = new Kernel(3, 3, array);
        ConvolutionOperation convOp = new ConvolutionOperation(kernel);
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null),
                input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);

        return output;
    }

}
