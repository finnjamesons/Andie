package cosc202.andie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.image.BufferedImage;

/**
 * A class used to draw shapes on an image
 */
public class DrawingOperation implements ImageOperation, java.io.Serializable {

    // Reference to the shape drawn by the draw tool
    Shape preview;

    // The scale currently set in the user space
    double scale;

    // the current x,y offset
    int xOffset;
    int yOffset;

    // The current colour set
    Color colour;
    // Whether the shape is filled or not
    boolean fill;

    public DrawingOperation(Shape preview, double scale, int xOffset, int yOffset, Color colour, boolean fill) {
        this.preview = preview;
        this.scale = scale;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.colour = colour;
        this.fill = fill;

    }

    /**
     * Applies a drawing operation to the current image
     */
    @Override
    public BufferedImage apply(BufferedImage input) {

        // Creates a graphics component fromt he input image
        Graphics2D g2d = input.createGraphics();

        // Sets the rendering to smooth the images
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Translates, scales and offsets the input image
        g2d.translate((((input.getWidth()) / 2)),
                (((input.getHeight()) / 2)));
        g2d.scale((1 / scale), (1 / scale));
        g2d.translate(((-input.getWidth()) / 2),
                ((-input.getHeight()) / 2));
        g2d.translate(-xOffset, -yOffset);

        // Sets the colour to draw with
        g2d.setColor(colour);

        // Fills or draws depending on what's set
        if (fill) {
            g2d.fill(preview);
        } else {
            g2d.draw(preview);
        }

        g2d.dispose();

        return input;

    }

}
