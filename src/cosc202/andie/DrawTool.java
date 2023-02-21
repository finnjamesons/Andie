package cosc202.andie;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A class that provides all the basic variables and methods used to draw shapes
 * to the screen.
 * 
 * This class can be overidden to be used to create different shapes, or
 * implement other mouse based features such as copping.
 * 
 * There three methods that can be overidden to create the desired effect,
 * "draw", "apply", "getOptions"
 * 
 * 
 * Draw is the shape that is displayed on the screen when the mouse is dragged
 * and can be overidden to return a custom shape object that responds to the
 * current bounds drawn by the user.
 * 
 * Apply is called when the user releases the mouse. This method returns an
 * ImageOperation to be applied to the stack. By defaul, the super
 * implementation returns a DrawingOperation, which will apply whatever apperas
 * during the "draw" method to the image. This can be overidden to apply any
 * operation.
 * 
 * GetOptions is a list of JComponents that will be added to the options bar at
 * the top of the frame. This can be overidden to return a list of options
 * relevant to the tool.
 * 
 * @Author Matthew Tyler 1049833
 * 
 */
public class DrawTool {

    // The current scale set in the user space.
    double scale = 1.0;

    // The current x,y Offset in the user space.
    int xOffset;
    int yOffset;

    // The most recent start and endpoints selected by the user
    Point startPoint;
    Point endPoint;

    // The most recent x and y points selected by the user
    int x;
    int y;

    // The width and height of the most recent user selection
    int width;
    int height;

    // The current fill property of this tool
    boolean fill = true;

    // The current thickness of this tool
    int thickness = 1;

    // The current colour. Defaults to colour picker.
    Color colour;

    /**
     * Returns a shape object to be drawn on the screen.
     * 
     * This method is applied whenever the user presses with their mouse while the
     * tool is enabled, and will continue to be applied whenever the mouse is
     * dragged.
     * 
     * This method can be overidden to return any Shape object. By default it
     * returns a filled rectangle.
     * 
     * By default the colour is determined by the user's selection on the colour
     * picker. This can be overidden by using the setColour() method.
     * 
     * @return A shape to be drawn on the screen
     */

    public Shape draw() {

        // The default action of the tool is to draw a simple rectangle
        Shape rect = new Rectangle(x, y, width, height);

        return rect;
    }

    /**
     * This method is called following a draw method, is applied on when the user
     * releases the corresponding mouse press.
     * 
     * By default this method will draw the most recent Shape instance returned from
     * the draw method. The colour will default to the user's selection on the
     * colour picker, unless otherwise overidde with the setColour() method.
     * 
     * @param preview The most recent Shape instance currently previewed on the
     *                user's screen
     * @return An image operation to be added to the stack.
     */
    public ImageOperation apply(Shape preview) {

        // The default operation draws preview onto the current image.
        ImageOperation op = new DrawingOperation(preview, scale, xOffset, yOffset, getColour(), getFill());

        return op;
    }

    /**
     * A simple getter method for the colour variable
     * 
     * if no colour is set, will default to the colour chosen by the user in the
     * colour picker
     * 
     * @return the current colour that's set.
     */
    public Color getColour() {
        // if the colour variable has been set returns
        if (colour != null) {
            return colour;
        }
        // Else returns the current option in the colour picker
        return RightSideMenu.colour;
    }

    /**
     * A setter method for the colour variable
     */
    public void setColour(Color colour) {
        this.colour = colour;
    }

    /**
     * A setter method for the fill of the current shape
     * 
     * @param fill True for fill, otherwise false.
     */
    public void setFill(boolean fill) {
        this.fill = fill;
    }

    /**
     * A simple getter method for the fill variable
     * 
     * @return True for fill, otherwise false.
     */
    public boolean getFill() {
        return fill;
    }

    /**
     * A setter method for the thickness of some drawing operations
     * 
     * @param thickness
     */
    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    /**
     * A getter method for the thickness of this tool
     * 
     * @return The current defined thickness
     */
    public int getThickness() {
        return thickness;
    }

    /**
     * A setter method for the start point
     * 
     * @param start the most recent point pressed by the user
     */
    public void setStart(Point start) {
        this.startPoint = start;
    }

    /**
     * A setter method for the most recent end point.
     * 
     * @param end The most recent point where the user released the mouse press
     */
    public void setEnd(Point end) {
        this.endPoint = end;
    }

    /**
     * Sets the width of the most recent selection
     * 
     * @param width The width of the most recent selection
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Sets the height of the most recent selection
     * 
     * @param height the height of the most recent selection
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Sets the most recent x location selected by the user
     * 
     * @param x the most recent x Location selected by the user
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the most recent y location selected by the user
     * 
     * @param y the most recent y locatin selected by the user
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets the current scale used by the user
     * 
     * @return the current scale
     */
    public double getScale() {
        return scale;
    }

    /**
     * Sets the current scale used by the user
     * 
     * @param scale Current scale
     */
    public void setScale(double scale) {
        this.scale = scale;
    }

    /**
     * Gets the current xOffset
     * 
     * @return
     */
    public int getXOffset() {
        return xOffset;
    }

    /**
     * Sets the current xoffset
     * 
     * @param xOffset
     */
    public void setXOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    /**
     * Gets the current yOffset
     * 
     * @return
     */
    public int getYOffset() {
        return yOffset;
    }

    /**
     * Gets the current yOffset
     * 
     * @param yOffset
     */
    public void setYOffset(int yOffset) {
        this.yOffset = yOffset;
    }

    /**
     * A default toString, used for displaying on the options bar.
     */
    public String toString() {
        return "Draw";
    }

    /**
     * A default list of options that includes the selectedable list of other draw
     * tools
     * 
     * @return
     */
    public ArrayList<JComponent> getOptions() {

        ArrayList<JComponent> options = new ArrayList<>();
        JLabel choice = new JLabel(this.toString());

        options.add(new ShapeSelector());

        options.add(choice);

        return options;
    }

    /**
     * An inner class that extends the combobox to work as a way of selecting
     * different draw tools.
     */
    public class ShapeSelector extends JComboBox<String> {

        // Constructs an instance
        public ShapeSelector() {
            // The list of current draw tools
            String[] shapeOptions = { "Shapes", "Rectangle", "Ellipse", "Line" };

            // Loops over the list and adds it to the combobox
            for (String shape : shapeOptions) {
                this.addItem(shape);
            }

            // Creates an anonymous inner type of ImageAction and sets it as the action
            // listener of the combobox
            this.addActionListener(new ImageAction() {

                /**
                 * The method that's called when the selected shape is changed
                 */
                @Override
                public void actionPerformed(ActionEvent e) {

                    // Surpressed as it should always by of type combobox.
                    // Gets the source of the action, casts it to a combobox, then gets the selected
                    // items string value.
                    @SuppressWarnings("unchecked")
                    String tool = ((JComboBox<String>) e.getSource()).getSelectedItem().toString();

                    // Switch statement determines which tool to set.
                    switch (tool) {

                        case "Draw":
                            target.setDrawTool(new DrawTool());
                            break;

                        case "Rectangle":
                            target.setDrawTool(new DrawRectangle());
                            break;

                        case "Ellipse":
                            target.setDrawTool(new DrawEllipse());
                            break;

                        case "Line":
                            target.setDrawTool(new DrawLine());
                            break;
                    }

                }

            });

        }

    }

    /**
     * An inner class used to extend the drawtool to create a rectangle.
     * 
     */
    public static class DrawRectangle extends DrawTool {

        @Override
        public Shape draw() {

            Shape rect = new Rectangle(x, y, width, height);

            return rect;
        }

        @Override
        public String toString() {

            return "Rectangle";
        }

        @Override
        public ArrayList<JComponent> getOptions() {

            ArrayList<JComponent> options = super.getOptions();

            JRadioButton fillSelector = new JRadioButton("Fill");

            fillSelector.setSelected(true);

            fillSelector.addActionListener(new ImageAction() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    JRadioButton source = (JRadioButton) e.getSource();

                    if (source.isSelected()) {
                        target.getDrawTool().setFill(true);

                    } else if (!source.isSelected()) {
                        target.getDrawTool().setFill(false);
                    }

                }

            });

            options.add(fillSelector);

            return options;
        }
    }

    /**
     * An inner class used to extend the DrawTool to draw an ellipse
     */
    public static class DrawEllipse extends DrawTool {

        @Override
        public Shape draw() {

            Shape ellipse2D = new Ellipse2D.Double(x, y, width, height);

            return ellipse2D;
        }

        @Override
        public String toString() {

            return "Ellipse";
        }

        @Override
        public ArrayList<JComponent> getOptions() {

            ArrayList<JComponent> options = super.getOptions();

            JRadioButton fillSelector = new JRadioButton("Fill");

            fillSelector.setSelected(true);

            fillSelector.addActionListener(new ImageAction() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    JRadioButton source = (JRadioButton) e.getSource();

                    if (source.isSelected()) {
                        target.getDrawTool().setFill(true);

                    } else if (!source.isSelected()) {
                        target.getDrawTool().setFill(false);
                    }

                }

            });

            options.add(fillSelector);

            return options;
        }
    }

    /**
     * An inner class used to extend the drawtool to draw a line
     */
    public static class DrawLine extends DrawTool {

        @Override
        public Shape draw() {

            Shape line2D = new Line2D.Double(startPoint, endPoint);
            setFill(true);

            Stroke thickness = new BasicStroke(getThickness());

            line2D = thickness.createStrokedShape(line2D);

            return line2D;
        }

        @Override
        public String toString() {
            return "Line";
        }

        @Override
        public ArrayList<JComponent> getOptions() {

            ArrayList<JComponent> options = super.getOptions();

            JSlider thiCness = new JSlider();

            thiCness.setMinimum(1);
            thiCness.setMaximum(50);
            thiCness.setValue(1);

            thiCness.addChangeListener(new ChangeListen());

            options.add(thiCness);

            return options;
        }
    }

    /**
     * A custom change listener used to set the value of the stroke using a JSlider
     */
    public class ChangeListen extends ImageAction implements ChangeListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }

        @Override
        public void stateChanged(ChangeEvent c) {

            JSlider source = (JSlider) c.getSource();

            setStrokeValue(source.getValue());

        }

        public void setStrokeValue(int value) {
            target.getDrawTool().setThickness(value);

        }

    }
}
