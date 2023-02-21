package cosc202.andie;

import java.awt.image.BufferedImage;

/**
 * <p>
 * Interface for operations to be applied to images.
 * </p>
 * 
 * <p>
 * Classes implementing ImageOperation represent operations that can be applied
 * to images.
 * Each operation takes an image as input and provides an updated image as
 * output.
 * </p>
 * 
 * <p>
 * A distinction should be made between an ImageOperation and an
 * {@link ImageAction}.
 * An ImageOperation is applied to an image in order to change it in some way.
 * An ImageAction provides support for the user doing something to an image in
 * the user interface.
 * ImageActions may apply an ImageOperation, but do not have to do so.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public interface ImageOperation {

    /**
     * Apply the operation to an image.
     * 
     * ImageOperations may be either done in-place (that is, modifying the input
     * image), or
     * create a new BufferedImage to store the result.
     * This decision is left to the implementer of specific operations.
     * 
     * @param input The image to apply the operation to
     * @return The image resulting from the operation
     */
    public BufferedImage apply(BufferedImage input);
}
