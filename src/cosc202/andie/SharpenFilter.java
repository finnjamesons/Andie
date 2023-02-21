/**
 * Writing a test note for git commands 
 * Commit this to a repository 
 */

package cosc202.andie;

import java.awt.image.*;

/**
 * Applies a sharpen filter to the image
 * 
 */

public class SharpenFilter implements ImageOperation, java.io.Serializable {

    /**
     * <p>
     * Construct a sharpen filter.
     * </p>
     *
     */
    SharpenFilter() {
    }

    /**
     * <p>
     * Apply a Sharpen filter to an image.
     * </p>
     * 
     * @param input The image to apply the Sharpen filter to.
     * @return The resulting sharpened image.
     */
    public BufferedImage apply(BufferedImage input) {

        // The kernel matrix to be applied
        float[] array = { 0, -1 / 2.0f, 0,
                -1 / 2.0f, 3, -1 / 2.0f,
                0, -1 / 2.0f, 0 };
        // Creates a kernel from the matrix
        Kernel kernel = new Kernel(3, 3, array);
        // Passes the kernel to a convolution operation
        ConvolutionOperation convOp = new ConvolutionOperation(kernel);
        // Creates a copy of the buffered image
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null),
                input.isAlphaPremultiplied(), null);
        // Applies the convolution to the input, outputting the result to the output
        // image.
        convOp.filter(input, output);

        return output;
    }

}
