package cosc202.andie;

import java.awt.image.*;
import java.awt.*;

/**
 * A class that preforms a convolution with a given kernel from the input image
 * to the output image
 * 
 * @Author Matthew Tyler 1049833
 */
public class ConvolutionOperation {

    private Kernel kernel;
    private float[] kernelarray;

    /**
     * Construct a ConvolutionOperation with a given kernel
     * 
     * @param kernel
     */
    public ConvolutionOperation(Kernel kernel) {
        // The kernel used in convolution
        this.kernel = kernel;
        // Determines the size of the kernel data
        kernelarray = new float[kernel.getHeight() * kernel.getWidth()];
        // Copies the kernel
        kernel.getKernelData(kernelarray);

    }

    /**
     * A method that determines the best way to convolve an image based off of the
     * supplied kernel
     * 
     * If the sum of the input kernel totals 1, the image is convolved using java's
     * inbuilt convolution operation but with the edge case accounted for
     * 
     * If the sum of the input kernel totals 0, the image is convolved with the
     * values offset by 128
     * 
     * All other values default to the first method.
     * 
     * This is done to ensure the fastest convolution opperation is used where
     * possible
     * 
     * @param input  The image to be convolved
     * @param output The buffered image used to store the result
     * @return The convolved image
     */
    public BufferedImage filter(BufferedImage input, BufferedImage output) {

        // Used to track the sum of the kernel
        float sum = 0;
        // Loops over the kernel summing the elements
        for (int i = 0; i < kernelarray.length; i++) {
            sum += kernelarray[i];
        }
        // Rounds the total to the nearest integer
        switch (Math.round(sum)) {
            // If summed to 0, the image is convoled with the values offset
            case 0:
                output = filterResultOffset(input, output);
                break;
            // If summed to 1, the nonoffset method is used
            case 1:
                output = filterRepeatEdge(input, output);
                break;
            // All other values use no offset
            default:
                output = filterRepeatEdge(input, output);
                break;
        }

        return output;
    }

    /**
     * A method that applies a convolution with the edge pixels repeated
     * 
     * The method works by first enlarging the image and then drawing the original
     * image back overtop
     * 
     * @param input  The image to be convolved
     * @param output The buffered image used to store the result
     * @return The convolved image
     */
    private BufferedImage filterRepeatEdge(BufferedImage input, BufferedImage output) {

        // A new bufferedimage enlarged by the width and height of the kernel
        BufferedImage edgeRepeated = new BufferedImage(input.getWidth() + (kernel.getWidth() - 1),
                input.getHeight() + (kernel.getHeight() - 1), input.getType());
        // A version of the input image scaled by the width and height of the kernel
        Image scaledImage = input.getScaledInstance(input.getWidth() + (kernel.getWidth() - 1),
                input.getHeight() + (kernel.getHeight() - 1),
                Image.SCALE_DEFAULT);
        // Uses the bufferedimage's graphics compoonent to draw the scaled image in
        edgeRepeated.getGraphics().drawImage(scaledImage, 0, 0, null);
        // Draws the input image over the scaled image offset by the kernel's diamaeter
        edgeRepeated.getGraphics().drawImage(input, (kernel.getWidth() - 1) / 2, (kernel.getHeight() - 1) / 2, null);
        // Creates a new convolutionop and applies it to the image
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        op.filter(edgeRepeated, output);

        return output;
    }

    /**
     * A method that applies a convolution where the values are offset to allow for
     * negative results.
     * 
     * @param input  The image to be convolved
     * @param output The buffered image used to store the result
     * @return The convolved image
     */
    private BufferedImage filterResultOffset(BufferedImage input, BufferedImage output) {
        // Checks if the input image is null, throws an exception if so.
        if (input == null) {
            throw new NullPointerException("input image is null");
        }
        // Checks if the input image is the same as the output
        if (input == output) {
            throw new IllegalArgumentException("input image cannot be the " +
                    "same as the output image");
        }

        // Sets the height, width and size.
        int height = input.getHeight();
        int width = input.getWidth();

        // Determines the radius by the width of the kernel
        int radius = (kernel.getWidth() - 1) / 2;

        // The outer two loops visit each pixel in the input image
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {

                // A float representing each channel in the image with our applied offset
                float alpha = 128;
                float red = 128;
                float green = 128;
                float blue = 128;

                // Used to keep track of the position in the kernel
                int kernelindex = 0;

                // The inner two loops visit each pixel in the window of the kernel
                for (int dy = -radius; dy <= radius; ++dy) {
                    for (int dx = -radius; dx <= radius; ++dx) {

                        // Checks that the current coordinate of the kernel is in the bounds of the
                        // input image. Where the kernel is not inbounds, it repeats the edge pixel.
                        int ydy = Math.min(Math.max(0, y + dy), height - 1);
                        int xdx = Math.min(Math.max(0, x + dx), width - 1);

                        // The pixel of the input image for the operation to applied to
                        int pixel = input.getRGB(xdx, ydy);
                        // The value of the kernel to be applied
                        float kernelValue = kernelarray[kernelindex];
                        kernelindex++;
                        // Bitshifts the individual channels from the pixel int
                        int pixelAlpha = (pixel & 0xFF000000) >> 24;
                        int pixelRed = (pixel & 0x00FF0000) >> 16;
                        int pixelGreen = (pixel & 0x0000FF00) >> 8;
                        int pixelBlue = (pixel & 0x000000FF);
                        // Applies the kernel value to the channel
                        float a = (pixelAlpha * kernelValue);
                        float r = (pixelRed * kernelValue);
                        float g = (pixelGreen * kernelValue);
                        float b = (pixelBlue * kernelValue);
                        // Sums each channel together
                        alpha += a;
                        red += r;
                        green += g;
                        blue += b;
                    }
                }

                // Truncates and casts the float back to an int
                int alphaInt = truncate((int) alpha);
                int redInt = truncate((int) red);
                int greenInt = truncate((int) green);
                int blueInt = truncate((int) blue);

                // Bitshifts the indidivudal channels back into a single pixel
                int outputPixel = (alphaInt << 24) | (redInt << 16) | (greenInt << 8) | blueInt;
                // Places the filtered pixel in the output image
                output.setRGB(x, y, outputPixel);
            }
        }
        return output;
    }

    /**
     * A method that bounds an integer between 0 and 255
     * 
     * Values less than 0 are returned as 0, values greater than 255 are returned as
     * 255
     * 
     * @param input an integer to be bound
     * @return 0 if input less than 0, 255 if input greater than 255
     */
    private int truncate(int input) {
        if (input < 0) {
            return 0;
        } else if (input > 255) {
            return 255;
        } else {
            return input;
        }

    }

}
