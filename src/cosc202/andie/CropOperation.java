package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import java.awt.*;

/**
 * An ImageOperation allowing the user to crop an image defined by given shape.
 * 
 * @Author Matthew Tyler 1049833
 * 
 */
public class CropOperation implements ImageOperation, java.io.Serializable {

    // A shape object of the users selection
    Shape selection;
    // The current scale in the userspace
    double scale;
    // The current x,y offset in the userspace
    int xOffset;
    int yOffset;

    /**
     * A constructor that takes reference to the selection and current scaling and
     * translation in the user space
     * 
     * @param selection Used to define the crop region
     * @param scale     The current scaling factor in the userspace
     * @param xOffset   The current xOffset in the userspace
     * @param yOffset   The current yOFfset in the userspace
     */
    public CropOperation(Shape selection, double scale, int xOffset, int yOffset) {
        this.selection = selection;
        this.scale = scale;
        this.xOffset = xOffset;
        this.yOffset = yOffset;

    }

    /**
     * An overide of the super apply method that takes a BufferedImage as an input,
     * and applies a crop to it using a Shape as the selection.
     * 
     * @param input An image to be cropped
     * @return A cropped version of the image
     */
    @Override
    public BufferedImage apply(BufferedImage input) {

        // Gets a Graphics2D object scaled by the current user zoom and offsets
        Graphics2D inputGraphics = getScaledGraphics(input);
        // Transforms the user selection based off of the current transform
        Shape transformedSelection = inputGraphics.getTransform().createTransformedShape(selection);

        // Checks the selection is within the bounds of the image
        if (!input.getData().getBounds().contains(transformedSelection.getBounds())) {
            // The best option I had is to just return the input image.
            // This does add a blank operation to the the history, but avoids changing the
            // ImageOperation interface
            inputGraphics.dispose();
            return input;
        }

        // Creates a subimage
        BufferedImage sub = input.getSubimage(transformedSelection.getBounds().x, transformedSelection.getBounds().y,
                transformedSelection.getBounds().width,
                transformedSelection.getBounds().height);

        // Coppies the raster of that subimage into its own buffered image
        BufferedImage output = new BufferedImage(sub.getWidth(), sub.getHeight(), sub.getType());
        // Creates a graphics component for the output buffer
        Graphics2D outputGraphics = output.createGraphics();
        // draws the sub image over the output buffer
        outputGraphics.drawImage(sub, 0, 0, null);

        // Disposes of the graphics objects
        inputGraphics.dispose();
        outputGraphics.dispose();

        return output;
    }

    /**
     * A method that returns a Graphics2D object offset and scaled by the current
     * userspace settings
     * 
     * @param input The current image
     * @return A scaled Graphics2D object
     */
    private Graphics2D getScaledGraphics(BufferedImage input) {

        // Creates a Graphics2D object from the input image
        Graphics2D g2d = input.createGraphics();

        // Translates and scales it
        g2d.translate((((input.getWidth()) / 2)),
                (((input.getHeight()) / 2)));
        g2d.scale((1 / scale), (1 / scale));
        g2d.translate(((-input.getWidth()) / 2),
                ((-input.getHeight()) / 2));
        g2d.translate(-xOffset, -yOffset);

        return g2d;
    }

}
