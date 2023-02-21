package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * Class for rotating an image.
 * </p>
 * 
 * <p>
 * This class rotates an image one of three ways:
 * - 180 degrees,
 * - 90 degrees to the right,
 * - 90 degrees to the left.
 * </p>
 * 
 * @author Angus Henderson
 * @version 1.0
 */

public class RotateImage implements ImageOperation, java.io.Serializable {

    // Three different variables for rotating 180 degrees, 90 degrees left and
    // right.
    public static final int ROTATE_90 = 0;
    public static final int ROTATE_180 = 1;
    public static final int ROTATE_270 = 2;
    private int rotation;

    /**
     * <p>
     * Construct a default Image Rotation.
     * </p
     * >
     * <p>
     * By default, a RotateImage has a 180 degree rotation.
     * </p>
     * 
     * @see RotateImage(int)
     */
    RotateImage() {
        this.rotation = ROTATE_90;
    }

    /**
     * <p>
     * Construct an Image Rotation with the given direction.
     * </p>
     * 
     * <p>
     * There are three rotations:
     * - 180 degrees (an input of 2),
     * - 90 degrees to the right (an input of 1),
     * - 90 degrees to the left (an input of -1).
     * </p>
     * 
     * @param rotation The rotation of the newly constructed RotateImage
     */
    RotateImage(int rotation) {
        this.rotation = rotation;
    }

    /**
     * <p>
     * Apply a Rotation to an image.
     * </p>
     * 
     * <p>
     * This apply method simply exchanges pixels to produce the required rotated
     * image.
     * </p>
     * 
     * @param input The image to apply the rotation to.
     * @return The resulting (rotated) image.
     */
    public BufferedImage apply(BufferedImage input) {

        int width = input.getWidth();
        int height = input.getHeight();

        BufferedImage rotated;

        // If the rotation is 180 degrees, keep original dimensions.
        // Otherwise, exchange the height and width of the output BufferedImage.
        if (rotation == ROTATE_180) {
            rotated = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        } else {
            rotated = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
        }

        for (int y = 0; y < height; y++) { // nested for loop with goes through all pixels of the image and switches
            for (int x = 0; x < width; x++) {
                switch (rotation) {
                    case ROTATE_90:
                        rotated.setRGB(height - 1 - y, x, input.getRGB(x, y));
                        break;
                    case ROTATE_270:
                        rotated.setRGB(y, width - 1 - x, input.getRGB(x, y));
                        break;
                    case ROTATE_180:
                        rotated.setRGB(width - 1 - x, height - 1 - y, input.getRGB(x, y));
                        break;
                }
            }
        }
        return rotated; // return the rotated Buffered image
    }

}
