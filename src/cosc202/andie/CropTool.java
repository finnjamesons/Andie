package cosc202.andie;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JComponent;

/**
 * A class that extends the draw tool, allowing the user to crop a region of the
 * image based on a selection drawn by the user
 * 
 * @author Matthew Tyler 1049833
 * 
 */
public class CropTool extends DrawTool {

    /**
     * An override of the super draw method, used to draw the selction box
     * 
     * @return a shape object representing the selection
     */
    @Override
    public Shape draw() {

        // Used to define the stroke object
        float dash1[] = { 10.0f };
        // A stroke used to give the selection dashed lines
        Stroke dashed = new BasicStroke(1.0f,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                10.0f, dash1, 0.0f);
        // A rectangle selection
        Shape rect = new Rectangle(x, y, width, height);
        // The shape with the stroke applied
        rect = dashed.createStrokedShape(rect);
        // Sets the default colour to white
        setColour(Color.WHITE);
        // Sets shape to just the outline
        setFill(false);

        return rect;
    }

    /**
     * An overide of the super apply method that instead returns an instance of the
     * CropOperation
     * 
     * @return a CropOperation
     */
    @Override
    public ImageOperation apply(Shape preview) {

        ImageOperation op = new CropOperation(preview, getScale(), getXOffset(), yOffset);

        return op;
    }

    /**
     * A method that returns a null options bar
     * 
     * @return an empty options bar
     * 
     */
    @Override
    public ArrayList<JComponent> getOptions() {

        return null;
    }
}
