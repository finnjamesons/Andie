package cosc202.andie;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.event.MouseInputListener;

/**
 * <p>
 * UI display element for {@link EditableImage}s.
 * </p>
 * 
 * <p>
 * This class extends {@link JPanel} to allow for rendering of an image, as well
 * as zooming
 * in and out.
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
public class ImagePanel extends JPanel implements MouseInputListener, MouseWheelListener {

    /**
     * The image to display in the ImagePanel.
     */
    private EditableImage image;

    // The used to preview shapes on the image
    private Shape preview;
    // A buffer the shape of the current image
    private BufferedImage buffer;

    // The tool used to define the shape and how its applied
    private DrawTool tool;

    // The start and end points where the user has pressed and realeased
    private Point startPoint;
    private Point endPoint;

    // The starting x,y where the user pressed
    private int x;
    private int y;

    // The width and height of the bounds the user drew
    private int width;
    private int height;

    // The x,y offset of the image
    private int xOffset = 0;
    private int yOffset = 0;

    // The previous x,y offset, used to track change
    private int previousXOffset;
    private int previousYOffset;

    // Used to track whether a modifier key is pressed
    private boolean ctrl = false;
    // A refernece to the options bar, used to set tool options
    private OptionsBar optionsBar;

    /**
     * <p>
     * The zoom-level of the current view.
     * A scale of 1.0 represents actual size; 0.5 is zoomed out to half size; 1.5 is
     * zoomed in to one-and-a-half size; and so forth.
     * </p>
     * 
     * <p>
     * Note that the scale is internally represented as a multiplier, but externally
     * as a percentage.
     * </p>
     */
    private double scale;

    /**
     * <p>
     * Create a new ImagePanel.
     * </p>
     * 
     * <p>
     * Newly created ImagePanels have a default zoom level of 100%
     * </p>
     */
    public ImagePanel() {

        image = new EditableImage();
        scale = 1.0;

        // The this as its mouse listener
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);

        // Adds a key map. Slightly better preformance than using ctrl on the mouse
        // listener. Maps "M" to overide the current draw tool and move the image
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_M, 0, false),
                "pressM");
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_M, 0, true),
                "releaseM");

        // An anon class mapped to the m key
        Action pressM = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ctrl = true;
            }
        };

        // An anon class mapped to the m key
        Action releaseM = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ctrl = false;

            }
        };

        // Adds the actions to the action map
        this.getActionMap().put("pressM", pressM);
        this.getActionMap().put("releaseM", releaseM);

    }

    /**
     * A getter method that returns a reference to the current draw tool
     * 
     * @return the current enabled draw tool
     */
    public DrawTool getDrawTool() {
        return tool;
    }

    /**
     * A setter method for setting the current draw tool.
     * 
     * @param tool the draw tool to be set
     */
    public void setDrawTool(DrawTool tool) {

        // Sets the current tool
        this.tool = tool;

        // If the previous tool was null, clears the options bar.
        if (tool == null) {
            optionsBar.clearOptions();
            return;
        }

        // Adds the tools options to the options bar
        optionsBar.setOptions(tool.getOptions());

    }

    /**
     * <p>
     * Get the currently displayed image
     * </p>
     *
     * @return the image currently displayed.
     */
    public EditableImage getImage() {
        return image;
    }

    /**
     * <p>
     * Get the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the
     * original size, 50% is half-size, etc.
     * </p>
     * 
     * @return The current zoom level as a percentage.
     */
    public double getZoom() {
        return 100 * scale;
    }

    /**
     * <p>
     * Set the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the
     * original size, 50% is half-size, etc.
     * The zoom level is restricted to the range [50, 200].
     * </p>
     * 
     * @param zoomPercent The new zoom level as a percentage.
     */
    public void setZoom(double zoomPercent) {

        if (zoomPercent < 50) {
            zoomPercent = 50;
        }
        if (zoomPercent > 200) {
            zoomPercent = 200;
        }
        scale = zoomPercent / 100;

    }

    /**
     * <p>
     * Gets the preferred size of this component for UI layout.
     * </p>
     * 
     * <p>
     * The preferred size is the size of the image (scaled by zoom level), or a
     * default size if no image is present.
     * </p>
     * 
     * @return The preferred size of this component.
     */
    @Override
    public Dimension getPreferredSize() {
        if (image.hasImage()) {
            return new Dimension((int) Math.round(image.getCurrentImage().getWidth() * scale),
                    (int) Math.round(image.getCurrentImage().getHeight() * scale));
        } else {
            return new Dimension(450, 450);
        }
    }

    /**
     * <p>
     * (Re)draw the component in the GUI.
     * </p>
     * 
     * @param g The Graphics component to draw the image on.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image.hasImage()) {
            Graphics2D g2 = (Graphics2D) g.create();

            // Translates the graphics context by the x,y Offset
            g2.translate(xOffset, yOffset);

            // Translates the image by half the width and height
            g2.translate((((image.getCurrentImage().getWidth()) / 2)),
                    (((image.getCurrentImage().getHeight()) / 2)));
            // Applies the scale
            g2.scale(scale, scale);
            // Translates it back by the width and height. This is done to keep the zoom
            // centered in the middle of the image
            g2.translate(((-image.getCurrentImage().getWidth()) / 2),
                    ((-image.getCurrentImage().getHeight()) / 2));
            // Draws the image onto the image panel with the current transforms applied
            g2.drawImage(image.getCurrentImage(), null, 0, 0);

            // If the preview isn't null
            if (preview != null) {

                // Creates a graphics2d object from the image buffer
                Graphics2D g2d = buffer.createGraphics();

                // Sets antialiasing on to smooth out rendered shapes
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                // Sets the background colour to completely opaque
                g2d.setBackground(new Color(0, 0, 0, 0));
                // Clears the buffer with an empty rectangle
                g2d.clearRect(0, 0, buffer.getWidth(), buffer.getHeight());

                // Translates the image and scales the buffer to the same relative size as the
                // current image
                g2d.translate((((image.getCurrentImage().getWidth()) / 2)),
                        (((image.getCurrentImage().getHeight()) / 2)));
                g2d.scale((1 / scale), (1 / scale));
                g2d.translate(((-image.getCurrentImage().getWidth()) / 2),
                        ((-image.getCurrentImage().getHeight()) / 2));
                // Offsets in the inverse
                g2d.translate(-xOffset, -yOffset);

                // Sets the colour to draw with
                g2d.setColor(tool.getColour());
                // Either fills or outlines based on the setting
                if (tool.getFill()) {
                    g2d.fill(preview);
                } else {
                    g2d.draw(preview);
                }
                // Draws the buffer to the screen.
                g2.drawImage(buffer, null, 0, 0);
                // disposes of the buffers graphics context
                g2d.dispose();

            }

            // Disposes of the current images graphics
            g2.dispose();

        }
    }

    /**
     * A method needed to satisfy the mouse listener interface
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Method called when the mouse enters the current image panel
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // Gaurd clause that checks if an image is loaded
        if (!image.hasImage()) {
            return;
        }

        // Sets the cursor to a move cursor if the tool is null
        if (this.tool == null) {
            this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
            return;
        }

        // Otherwise sets the cursor back to the default
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    /**
     * Method called when the mouse exits the current image panel
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // Sets the cursor back to the default cursor
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

    }

    /**
     * Method called when the user presses either the left or right mouse button.
     */
    @Override
    public void mousePressed(MouseEvent e) {

        // Gaurd clause that checks if an image is loaded
        if (!image.hasImage()) {
            return;
        }

        // Sets the current start point
        startPoint = e.getPoint();

        // If the tool is null, returns
        if (tool == null) {
            return;
        }

        // Passes the start point to the tool.
        tool.setStart(this.startPoint);
        // Creates a new buffer. Not entirely needed every time the mouse is pressed,
        // but an easy way to deal with new images being opened
        buffer = new BufferedImage(image.getCurrentImage().getWidth(), image.getCurrentImage().getHeight(),
                BufferedImage.TYPE_INT_ARGB);

    }

    /**
     * The method is applied when the mouse is dragged along the image panel.
     * 
     */
    @Override
    public void mouseDragged(MouseEvent e) {

        // Gaurd clause that checks if an image is loaded
        if (!image.hasImage()) {
            return;
        }

        // Sets teh end point
        endPoint = e.getPoint();

        // Calcualtes the x,y width and height
        setDimensions();

        // Sets some offsets based on whetehr the tool is null or the oveide key is
        // pressed
        if (tool == null || ctrl) {

            // Determines new offsets
            yOffset = (previousYOffset + (int) (endPoint.getY() - startPoint.getY()));
            xOffset = (previousXOffset + (int) (endPoint.getX() - startPoint.getX()));
            this.repaint();
            return;
        }

        // sets the preview shape equal to the shape returned by the current tool
        preview = tool.draw();
        this.repaint();
    }

    /**
     * The method called when the mouse press is released
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // Gaurd clause that checks if an image is loaded
        if (!image.hasImage()) {
            return;
        }

        // tracks previous x,y offset for determining new ones
        previousXOffset = xOffset;
        previousYOffset = yOffset;

        // If the shape is not null
        if (preview != null) {

            // Adds the current tools image operation to the stack
            image.apply(tool.apply(preview));
        }

        // Sets the preview shape back to null
        preview = null;

        this.repaint();

    }

    /**
     * Called when the mouse is moved within the image panel.
     * Currently just here to satisfy the interface
     */
    @Override
    public void mouseMoved(MouseEvent e) {
    }

    /**
     * A method used to set the dimensions of the the users selection
     */
    public void setDimensions() {

        // The x,y defined by the minimum x and y selection
        x = Math.min((int) startPoint.getX(), (int) endPoint.getX());
        y = Math.min((int) startPoint.getY(), (int) endPoint.getY());

        // Width and height determined by the largest minus the x,y
        width = Math.max((int) startPoint.getX(), (int) endPoint.getX()) - x;
        height = Math.max((int) startPoint.getY(), (int) endPoint.getY()) - y;

        // Early return if null
        if (tool == null) {
            return;
        }

        // Passes the values to the currently set tool
        tool.setEnd(this.endPoint);

        tool.setHeight(this.height);
        tool.setWidth(this.width);

        tool.setX(this.x);
        tool.setY(this.y);

        tool.setXOffset(this.xOffset);
        tool.setYOffset(this.yOffset);
        tool.setScale(this.scale);

    }

    /**
     * Method called when the mouse wheel is moved
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent w) {
        // Gaurd clause that checks if an image is loaded
        if (!image.hasImage()) {
            return;
        }

        // Adjusts the zoom based off of the wheel
        if (tool == null || ctrl) {
            if (getZoom() == 200 || getZoom() == 50) {
                this.setZoom(this.getZoom() - (w.getWheelRotation() * 5));
                return;
            }
            this.setZoom(this.getZoom() - (w.getWheelRotation() * 5));
            this.repaint();
        }
    }

    /**
     * Used to set reference to the current options bar
     * 
     * @param optionsBar the options bar at the top of the panel
     */
    public void setOptionsBar(OptionsBar optionsBar) {
        this.optionsBar = optionsBar;
    }

}
