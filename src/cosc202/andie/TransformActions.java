package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * 
 * Actions provided by the Transform menu.
 * </p>
 * 
 * <p>
 * The Transform menu contains actions that alter the an image.
 * This includes actions such as resize, rotate, and flip
 * </p>
 * 
 * @author Dominic Tekanene, Angus Henderson, Matt Tyler, Finn O'Neill
 */
public class TransformActions {

    /** A list of actions for the Transform menu. */
    protected ArrayList<Action> actions;
    protected ArrayList<Action> rotateActions;
    protected ArrayList<Action> flipActions;

    /**
     * <p>
     * Create a set of Transform menu actions.
     * </p>
     */
    public TransformActions() {

        actions = new ArrayList<Action>();
        rotateActions = new ArrayList<Action>();
        actions.add(new ResizeAction("Resize", "resize", "Apply Resize", Integer.valueOf(KeyEvent.VK_M)));
        actions.add(new ImageCropAction("Crop Image", "crop", "Crop An Image", Integer.valueOf(KeyEvent.VK_C)));

        rotateActions.add(new RotateImageAction("Rotate 90°", "rotate", "Rotate Image 90°",
                Integer.valueOf(KeyEvent.VK_9), 0));
        rotateActions.add(new RotateImageAction("Rotate 180°", "rotate", "Rotate Image 180°",
                Integer.valueOf(KeyEvent.VK_8), 1));
        rotateActions.add(new RotateImageAction("Rotate 270°", "rotate", "Rotate Image 270°",
                Integer.valueOf(KeyEvent.VK_7), 2));

        flipActions = new ArrayList<Action>();

        flipActions.add(new ImageFlipAction("Flip Horizontal", "flipHorizontal", "Flip an image horizontally",
                Integer.valueOf(KeyEvent.VK_RIGHT),
                ImageFlip.FLIP_HORIZONTAL));
        flipActions.add(
                new ImageFlipAction("Flip Vertical", "flipVertical", "Flip an image vertically",
                        Integer.valueOf(KeyEvent.VK_DOWN),
                        ImageFlip.FLIP_VERTICAL));

    }

    /**
     * <p>
     * Create a menu contianing the list of Transform actions.
     * </p>
     * 
     * @return The transform menu UI element.
     */
    public JMenu createMenu() {

        JMenu transformMenu = new JMenu("Transform");
        JMenu rotateMenu = new JMenu("Rotate");
        JMenu flipSubMenu = new JMenu("Flip");

        // Sets the icon for the submenus
        rotateMenu.setIcon((Icon) rotateActions.get(0).getValue(Action.SMALL_ICON));
        flipSubMenu.setIcon((Icon) flipActions.get(0).getValue(Action.SMALL_ICON));

        for (Action action : actions) {
            transformMenu.add(new JMenuItem(action));
        }

        for (Action action : rotateActions) {
            rotateMenu.add(new JMenuItem(action));
        }

        for (Action action : flipActions) {
            flipSubMenu.add(action);

        }

        transformMenu.add(rotateMenu);
        transformMenu.add(flipSubMenu);

        return transformMenu;
    }

    /**
     * <p>
     * Action to resize an image.
     * </p>
     * 
     * @see ResizeIamge
     */
    public class ResizeAction extends ImageAction {

        public int resizedHeight;
        public int resizedWidth;
        public double resizedScale;

        /**
         * <p>
         * Create a new resize action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param iconName An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ResizeAction(String name, String iconName, String desc, Integer mnemonic) {
            super(name, iconName, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the resize action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ResizeAction is triggered.
         * It prompts the user for a resized height, resized width, and a resized scale,
         * then applys an appropriately sized
         * {@link ResizeImage}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (!hasImage()) {
                return;
            }

            resizedHeight = target.getImage().getCurrentImage().getHeight();
            resizedWidth = target.getImage().getCurrentImage().getWidth();
            resizedScale = 1.0;

            // Pop-up dialog box to ask for the resized height, resized width and resized
            // scale values.
            SpinnerNumberModel heightSpinner = new SpinnerNumberModel(resizedHeight, 0, 5000, 1);
            SpinnerNumberModel widthSpinner = new SpinnerNumberModel(resizedWidth, 0, 5000, 1);
            SpinnerNumberModel scaleSpinner = new SpinnerNumberModel(resizedScale * 100, 0, 1000, 1);

            JSpinner height = new JSpinner(heightSpinner);
            JSpinner width = new JSpinner(widthSpinner);
            JSpinner scale = new JSpinner(scaleSpinner);

            Object[] Spinner = {
                    "Height (%):", height,
                    "Width (%):", width,
                    "Scale (%)", scale
            };

            int option = JOptionPane.showOptionDialog(target.getParent(), Spinner, "Resize",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, (Icon) getValue(Action.LARGE_ICON_KEY),
                    null, null);

            // Check the return value from the dialog boxes.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                updateValues(heightSpinner.getNumber().intValue(), widthSpinner.getNumber().intValue(),
                        scaleSpinner.getNumber().doubleValue() / 100);
            }

            // Create and apply the filter
            target.getImage().apply(new ResizeImage(resizedHeight, resizedWidth, resizedScale));
            target.repaint();
            target.getParent().revalidate();
        }

        /**
         * <p>
         * Updates the values so when changed, it updates the previos value to the new
         * values and
         * is scales the changes to the other values
         * </p>
         * 
         * @param outputHeight The updated height value.
         * @param outputWidth  The updated width value.
         * @param outputScale  The updated scale value.
         */
        public void updateValues(int outputHeight, int outputWidth, double outputScale) {
            if (outputHeight != resizedHeight) {
                resizedScale = ((double) outputHeight) / ((double) resizedHeight);
                resizedWidth = (int) (resizedScale * resizedWidth);
                resizedHeight = outputHeight;
            } else if (outputWidth != resizedWidth) {
                resizedScale = ((double) outputWidth) / ((double) resizedWidth);
                resizedHeight = (int) (resizedScale * resizedHeight);
                resizedWidth = outputWidth;
            } else if (outputScale != resizedScale) {
                resizedScale = outputScale;
                resizedWidth = (int) (resizedScale * resizedWidth);
                resizedHeight = (int) (resizedScale * resizedHeight);
            }
        }
    }

    /**
     * <p>
     * Action to rotate an image.
     * </p>
     * 
     * @see RotateImage
     */
    public class RotateImageAction extends ImageAction {

        int direction;

        /**
         * <p>
         * Create a new rotate-image action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param iconName An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        RotateImageAction(String name, String iconName, String desc, Integer mnemonic) {
            super(name, iconName, desc, mnemonic);

        }

        /**
         * <p>
         * Create a new rotate-image action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param iconName An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        RotateImageAction(String name, String iconName, String desc, Integer mnemonic, int direction) {
            super(name, iconName, desc, mnemonic);
            this.direction = direction;

        }

        /**
         * <p>
         * Callback for when the rotate-image action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RotateImageAction is triggered.
         * It rotates the image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (!hasImage()) {
                return;
            }
            target.getImage().apply(new RotateImage(direction));
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * A class used to create an action for the ImageFlip transform class
     * The action takes an int defined by the ImageFlip class representing the
     * direction to flip the image
     * 
     */
    public class ImageFlipAction extends ImageAction {

        private int direction;

        /**
         * A default constructor that satisfies the ImageAction interface
         * 
         * @param name     The name of the action
         * @param iconName The icon for the action, usually null
         * @param desc     A description of the action preformed
         * @param mnemonic The shortcut key used in the editor
         */
        ImageFlipAction(String name, String iconName, String desc, Integer mnemonic) {
            super(name, iconName, desc, mnemonic);
        }

        /**
         * A constructor that takes the params defining the action
         * 
         * The directon to flip the image in can be set by a static call to the image
         * flip class
         * 
         * Where ImageFlip.FLIP_VERTICAL is used for vertical
         * And ImageFlip.FLIP_HORIZONTAL is used for horizontal
         * 
         * @param name      The name of the action
         * @param iconName  The root name of the icon, used to set the small and large
         * @param desc      A description of the action preformed
         * @param mnemonic  The shorcut key
         * @param direction The direction, a constant defined by the ImageFlip class
         */
        ImageFlipAction(String name, String iconName, String desc, Integer mnemonic, int direction) {
            super(name, iconName, desc, mnemonic);
            this.direction = direction;
        }

        public void actionPerformed(ActionEvent e) {
            if (!hasImage()) {
                return;
            }
            // Create and apply the filter
            target.getImage().apply(new ImageFlip(direction));

            target.repaint();
            target.getParent().revalidate();
        }
    }

    public class ImageCropAction extends ImageAction {

        /**
         * A default constructor that satisfies the ImageAction interface
         * 
         * @param name     The name of the action
         * @param iconName The icon for the action, usually null
         * @param desc     A description of the action preformed
         * @param mnemonic The shortcut key used in the editor
         */
        ImageCropAction(String name, String iconName, String desc, Integer mnemonic) {
            super(name, iconName, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            if (!hasImage()) {
                return;
            }

            target.setDrawTool(new CropTool());

        }
    }

}
