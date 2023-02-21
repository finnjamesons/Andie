package cosc202.andie;

import java.awt.image.*;

public class GaussianFilter implements ImageOperation, java.io.Serializable {

    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a
     * 5x5 filter, and so forth.
     */
    private int radius;

    /**
     * <p>
     * Construct a Gaussian filter with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius' of the convolution kernel used.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * Larger filters give a stronger blurring effect.
     * </p>
     * 
     * @param radius The radius of the newly constructed GaussianFilter
     */
    GaussianFilter(int radius) {
        this.radius = radius;
    }

    /**
     * <p>
     * Construct a Gaussian filter with the default size.
     * </p
     * >
     * <p>
     * By default, a Gaussian filter has radius 1.
     * </p>
     * 
     * @see GaussianFilter(int)
     */
    GaussianFilter() {
        this(1);
    }

    /**
     * <p>
     * Apply a Mean filter to an image.
     * </p>
     * 
     * <p>
     * As with many filters, the Mean filter is implemented via convolution.
     * The size of the convolution kernel is specified by the {@link radius}.
     * Larger radii lead to stronger blurring.
     * </p>
     * 
     * @param input The image to apply the Mean filter to.
     * @return The resulting (blurred)) image.
     */
    public BufferedImage apply(BufferedImage input) {

        // Sets the size of the kernel
        int size = (2 * radius + 1) * (2 * radius + 1);
        // Creates a new array for the kernel
        float[] array = new float[size];
        // Adds the values from the guassian equation to the kernel array
        kernelWeights(array);
        // Normalises the values in the kernel array
        normaliseArray(array);
        // Creates a kernel object from the kernel array
        Kernel kernel = new Kernel(2 * radius + 1, 2 * radius + 1, array);
        // Creates a new convolution operation with the kernel
        ConvolutionOperation convOp = new ConvolutionOperation(kernel);
        // Creates a copy of the input image to apply the filter to
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null),
                input.isAlphaPremultiplied(), null);
        // Applies the filter
        convOp.filter(input, output);

        return output;
    }

    /**
     * A method that takes an array of floats and returns a non-normalised 1
     * diomensional gaussian array
     * 
     * The size of the array should be (2*raidus + 1)^2
     * 
     * @param array an array of floats
     * @return A non-normalised 1 dimensional guassian array
     */
    protected float[] kernelWeights(float[] array) {

        // The diameter of the array
        int diameter = (2 * radius + 1);

        // Loops over every element in the array as if it were a 2d array
        for (int i = 0; i < diameter; i++) {
            for (int j = 0; j < diameter; j++) {

                // Fills the array with the coresponding values from the 2D Gaussian Function.
                // Negative values represent left and positive right of
                // the middle of the array
                array[i * diameter + j] = guassianFunction(i - diameter / 2, j - diameter / 2);

            }

        }

        return array;
    }

    /**
     * A method that returns the result of a 2D Gaussian function
     * 
     * The values for x & Y are the distance from the centre of the kernel. So at
     * the centre of the kernel x = 0 & y = 0
     * 
     * The variance is set to 1/3rd of the size of the radius
     * 
     * @param x The x coordianate of the kernel array, starting from the
     *          centre.
     * @param y The y coordinate of the kernel array, starting from the
     *          centre
     * @return The result of a 2D Guassian function
     */
    protected float guassianFunction(int x, int y) {

        // The varriance, taken as 1/3rd the radius
        double variance = (radius / 3.0);
        // A very java representation of a 2D Gaussian Equation
        double output = (1 / (2 * Math.PI * Math.pow(variance, 2))
                * Math.exp(-(Math.pow(x, 2) + Math.pow(y, 2)) / (2 * Math.pow(variance, 2))));

        // Casts the result to a float
        return (float) (output);
    }

    /**
     * A method that normalises the values in an array of floats
     * 
     * 
     * @param array ann array of floats
     * @return a normalised array of floats
     */
    protected float[] normaliseArray(float[] array) {

        // Used to track the total sum of the array
        float sum = 0;

        // loops over the array to take a total sum of the array
        for (int i = 0; i < array.length; i++) {

            sum += array[i];

        }
        // Devides each value in the array by the sum of the array
        for (int i = 0; i < array.length; i++) {

            array[i] /= sum;

        }

        return array;
    }

}
