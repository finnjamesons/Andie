package cosc202.andie;

import java.awt.image.*;

/**
 * 29/03/2022
 * Finn O'Neill
 * 
 * Flips an image horizonally or vertically in ANDIE
 * 
 * @param input     This is the image
 * @param direction direction of flip (either horizontal or vertical)
 */

public class ImageFlip implements ImageOperation, java.io.Serializable {

    public static final int FLIP_VERTICAL = 1;// Two different variables for horizontal, vertical
    public static final int FLIP_HORIZONTAL = -1;
    private int direction;

    /**
     * A default constructor sets the flip to vertical
     */
    ImageFlip() {
        this.direction = FLIP_VERTICAL;
    }

    /**
     * A constructor that takes an int argument defining the direction of the flip
     * 
     * The directon to flip the image in can be set by a static call to the image
     * flip class
     * 
     * Where ImageFlip.FLIP_VERTICAL is used for vertical
     * And ImageFlip.FLIP_HORIZONTAL is used for horizontal
     * 
     * @param direction
     */
    ImageFlip(int direction) {
        this.direction = direction;
    }

    /**
     * A method that flips the image.
     * 
     * The image can be flipped either vertically or horizontally. This is set by
     * the direction field and is done with the constructor.
     * 
     * Parts have been adapted from code by Zoran Davidović
     * www.youtube.com/watch?v=HJXl2hmapdo
     * 
     * @param input The image to be flipped
     * @return The flipped image
     */
    public BufferedImage apply(BufferedImage input) {

        int width = input.getWidth(); // width variable measures the image with using a accessor function
        int height = input.getHeight(); // height var measures the width of the image using a getHeight accessor
                                        // function
        BufferedImage flipped = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) { // nested for loop with goes through all pixels of the image and switches
            for (int x = 0; x < width; x++) {
                switch (direction) {
                    case FLIP_HORIZONTAL: // given the case horizonal then flip horizontally
                        flipped.setRGB((width - 1) - x, y, input.getRGB(x, y));
                        break;
                    case FLIP_VERTICAL:// given the case of a vertical then flip vertically
                        flipped.setRGB(x, (height - 1) - y, input.getRGB(x, y));
                        break;
                }
            }
        }
        return flipped; // return the fliped Buffered image
    }

}
