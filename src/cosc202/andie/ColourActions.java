package cosc202.andie;

import java.util.*;
//import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import javax.swing.event.*;

/**
 * <p>
 * Actions provided by the Colour menu.
 * </p>
 * 
 * <p>
 * The Colour menu contains actions that affect the colour of each pixel
 * directly
 * without reference to the rest of the image.
 * This includes conversion to greyscale in the sample code, but more operations
 * will need to be added.
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
public class ColourActions {

    /** A list of actions for the Colour menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Colour menu actions.
     * </p>
     */
    public ColourActions() {
        actions = new ArrayList<Action>();
        actions.add(new ConvertToGreyAction("Greyscale", "greyscale", "Convert to greyscale",
                Integer.valueOf(KeyEvent.VK_G)));
        actions.add(
                new BrightnessContrastAdjustmentAction("Brightness/Contrast", "brightnessContrast", "Adjust Brightness",
                        Integer.valueOf(KeyEvent.VK_SLASH)));
        actions.add(
                new PosterisationAction("Posterisation", "posterisation", "Limits the image colours",
                        Integer.valueOf(KeyEvent.VK_P)));
        actions.add(new ConvertToInvertAction("Invert", "invertColour", "Invert Image",
                Integer.valueOf(KeyEvent.VK_I)));

    }

    /**
     * <p>
     * Create a menu contianing the list of Colour actions.
     * </p>
     * 
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Colour");

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to convert an image to greyscale.
     * </p>
     * 
     * @see ConvertToGrey
     */
    public class ConvertToGreyAction extends ImageAction {

        /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param iconName An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ConvertToGreyAction(String name, String iconName, String desc, Integer mnemonic) {
            super(name, iconName, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ConvertToGreyAction is triggered.
         * It changes the image to greyscale.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (!hasImage()) {
                return;
            }
            target.getImage().apply(new ConvertToGrey());
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to change the brightness and contrast of an image.
     * </p>
     * 
     * @see BrightnessContrast
     */
    public class BrightnessContrastAdjustmentAction extends ImageAction {

        /**
         * <p>
         * Create a new brightness-contrast-adjustment action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param iconName An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        BrightnessContrastAdjustmentAction(String name, String iconName, String desc, Integer mnemonic) {
            super(name, iconName, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the brightness-contrast-adjustment action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the BrightnessContrastAdjustmentAction is
         * triggered.
         * It changes the image to based on input brightness and contrast values.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (!hasImage()) {
                return;
            }
            int brightnessChange = 0;
            int contrastChange = 0;

            // Initialising the box and the sliders to go in it.
            JComponent box = new JComponent() {
            };
            JSlider brightnessModel = new JSlider(-100, 100, 0);
            JSlider contrastModel = new JSlider(-100, 100, 0);

            brightnessModel.setMajorTickSpacing(25);
            brightnessModel.setPaintTicks(true);
            brightnessModel.setPaintLabels(true);
            contrastModel.setMajorTickSpacing(25);
            contrastModel.setPaintTicks(true);
            contrastModel.setPaintLabels(true);

            box.add(brightnessModel);
            box.add(contrastModel);

            Object[] Spinner = {
                    "Enter brightness (%):", brightnessModel,
                    "Enter contrast (%):", contrastModel
            };

            int option = JOptionPane.showOptionDialog(target.getParent(), Spinner, "BrightnessContrast",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, (Icon) getValue(Action.LARGE_ICON_KEY),
                    null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                brightnessChange = brightnessModel.getValue();
                contrastChange = contrastModel.getValue();
            }

            target.getImage().apply(new BrightnessContrast(brightnessChange, contrastChange));
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * Action to Posterise an image.
     * </p>
     * 
     * @see Posterisation
     */
    public class PosterisationAction extends ImageAction {

        /**
         * <p>
         * Create a new Posterisation action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param iconName An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        PosterisationAction(String name, String iconName, String desc, Integer mnemonic) {
            super(name, iconName, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the Posterisation action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the PosterisationAction is triggered.
         * It changes the image colours to the nearest Posterised value, based on the
         * number of bands input.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (!hasImage()) {
                return;
            }

            // Pop-up dialog box to ask for the number of bands.
            SpinnerNumberModel posteriseModel = new SpinnerNumberModel(1, 1, 255, 1);

            JSpinner posteriseSpinner = new JSpinner(posteriseModel);

            Object[] Spinner = {
                    "Enter posterisation value:", posteriseSpinner
            };

            int option = JOptionPane.showOptionDialog(target.getParent(), Spinner,
                    "Enter posterisation values",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, (Icon) getValue(Action.LARGE_ICON_KEY),
                    null, null);

            // Returns early if the user cancels the operation.
            if (option != JOptionPane.OK_OPTION) {
                return;
            }

            int numBands = posteriseModel.getNumber().intValue();

            target.getImage().apply(new Posterisation(numBands));

            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to invert the colours of an image.
     * </p>
     * 
     * @see ConvertToNegative
     */

    public class ConvertToInvertAction extends ImageAction {

        /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param iconName An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ConvertToInvertAction(String name, String iconName, String desc, Integer mnemonic) {
            super(name, iconName, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ConvertToGreyAction is triggered.
         * It changes the image to greyscale.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (!hasImage()) {
                return;
            }
            target.getImage().apply(new ConvertToNegative());
            target.repaint();
            target.getParent().revalidate();
        }

    }

}
