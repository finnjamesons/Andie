package cosc202.andie;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * <p>
 * Actions provided by the Filter menu.
 * </p>
 * 
 * <p>
 * The Filter menu contains actions that update each pixel in an image based on
 * some small local neighbourhood.
 * This includes a mean filter (a simple blur) in the sample code, but more
 * operations will need to be added.
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
public class FilterActions {

    /** A list of actions for the Filter menu. */
    protected ArrayList<Action> actions;
    protected ArrayList<Action> sobelActions;

    /**
     * <p>
     * Create a set of Filter menu actions.
     * </p>
     */
    public FilterActions() {
        actions = new ArrayList<Action>();
        actions.add(
                new MeanFilterAction("Mean Blur", "blur", "Apply a mean blur filter", Integer.valueOf(KeyEvent.VK_M)));
        actions.add(new SharpenFilterAction("Sharpen Filter", "sharpen", "Apply a sharpen filter",
                Integer.valueOf(KeyEvent.VK_V))); // added a new action
        actions.add(
                new MedianFilterAction("Median Blur", "blur", "Apply a median filter", Integer.valueOf(KeyEvent.VK_B)));
        actions.add(
                new GaussianFilterAction("Gaussian Blur", "blur", "Apply a guassian filter",
                        Integer.valueOf(KeyEvent.VK_U)));
        actions.add(
                new EmbossFilterAction("Emboss Filter", "emboss", "Apply an emboss filter",
                        null));

        sobelActions = new ArrayList<Action>();

        sobelActions.add(
                new SobelFilterAction("Vertical Sobel", "emboss", "Apply a vertical sobel filter", null));
        sobelActions.add(
                new SobelFilterAction("Horizontal Sobel", "emboss", "Apply a horizontal sobel filter", null));

    }

    /**
     * <p>
     * Create a menu contianing the list of Filter actions.
     * </p>
     * 
     * @return The filter menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Filter");

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        JMenu sobelSubMenu = new JMenu("Sobel Filter");
        sobelSubMenu.setIcon((Icon) sobelActions.get(0).getValue(Action.SMALL_ICON));

        for (Action sobelAction : sobelActions) {
            sobelSubMenu.add(new JMenuItem(sobelAction));
        }

        fileMenu.add(sobelSubMenu);

        return fileMenu;
    }

    /**
     * <p>
     * Action to blur an image with a mean filter.
     * </p>
     * 
     * @see MeanFilter
     */
    public class MeanFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new mean-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MeanFilterAction(String name, String iconName, String desc, Integer mnemonic) {
            super(name, iconName, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MeanFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link MeanFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (!hasImage()) {
                return;
            }
            // Determine the radius - ask the user.
            int radius = 1;

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(target.getParent(), radiusSpinner, "Enter filter radius",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, (Icon) getValue(Action.LARGE_ICON_KEY),
                    null, null);

            // Returns early if the user cancels the operation.
            if (option != JOptionPane.OK_OPTION) {
                return;
            }
            radius = radiusModel.getNumber().intValue();

            // Create and apply the filter
            target.getImage().apply(new MeanFilter(radius));
            // target.getImage().apply(new SharpenFilter(radius));

            target.repaint();
            target.getParent().revalidate();
        }

    }

    public class SharpenFilterAction extends ImageAction {
        /**
         * <p>
         * Create a new mean-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param iconName An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SharpenFilterAction(String name, String iconName, String desc, Integer mnemonic) {
            super(name, iconName, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            if (!hasImage()) {
                return;
            }

            // Pop-up dialog box to confirm user wishes to apply filter.
            JLabel text = new JLabel("Apply Sharpen Filter?");
            int option = JOptionPane.showOptionDialog(target.getParent(), text, "Apply Sharpen Filter?",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, (Icon) getValue(Action.LARGE_ICON_KEY),
                    null, null);

            // Check the return value from the dialog box.
            if (option != JOptionPane.OK_OPTION) {
                return;
            }
            // Create and apply the filter
            target.getImage().apply(new SharpenFilter());

            target.repaint();
            target.getParent().revalidate();
        }
    }

    public class MedianFilterAction extends ImageAction {

        /**
         * 
         * Create a new Median filter action.
         * 
         * 
         * @param name     The name of the action (ignored if null).
         * @param iconName An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MedianFilterAction(String name, String iconName, String desc, Integer mnemonic) {
            super(name, iconName, desc, mnemonic);

        }

        /**
         * 
         * Callback for when the median filter action is triggered.
         * 
         * 
         * 
         * This method is called whenever the MedianFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link MedianFilter}.
         * 
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Returns early if no image present
            if (!hasImage()) {
                return;
            }
            // Determine the radius - ask the user.
            int radius = 1;

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(target.getParent(), radiusSpinner, "Enter filter radius",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, (Icon) getValue(Action.LARGE_ICON_KEY),
                    null, null);

            // Returns early if the user cancels the operation.
            if (option != JOptionPane.OK_OPTION) {
                return;
            }

            radius = radiusModel.getNumber().intValue();

            // Create and apply the filter
            target.getImage().apply(new MedianFilter(radius));
            target.repaint();
            target.getParent().revalidate();

        }

    }

    public class GaussianFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new mean-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        GaussianFilterAction(String name, String iconName, String desc, Integer mnemonic) {
            super(name, iconName, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MeanFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link MeanFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (!hasImage()) {
                return;
            }
            // Determine the radius - ask the user.
            int radius = 1;

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(target.getParent(), radiusSpinner, "Enter filter radius",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, (Icon) getValue(Action.LARGE_ICON_KEY),
                    null, null);

            // Returns early if the user cancels the operation.
            if (option != JOptionPane.OK_OPTION) {
                return;
            }
            radius = radiusModel.getNumber().intValue();

            // Create and apply the filter
            target.getImage().apply(new GaussianFilter(radius));

            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * A class used to create an action for the SobelFilter class
     * The action takes a boolean defined by the SobelFilter class representing the
     * direction to apply the filter.
     * 
     */
    public class SobelFilterAction extends ImageAction {

        private boolean direction = true;

        /**
         * A default constructor that satisfies the ImageAction interface
         * 
         * @param name     The name of the action
         * @param iconName The icon for the action, usually null
         * @param desc     A description of the action preformed
         * @param mnemonic The shortcut key used in the editor
         */
        SobelFilterAction(String name, String iconName, String desc, Integer mnemonic) {
            super(name, iconName, desc, mnemonic);
        }

        /**
         * A constructor that takes the params defining the action
         * 
         * The directon to apply the SobelFilter to the image.
         * 
         * Where true is used for vertical
         * And false is used for horizontal
         * 
         * @param name      The name of the action
         * @param iconName  The root name of the icon, used to set the small and large
         * @param desc      A description of the action preformed
         * @param mnemonic  The shorcut key
         * @param direction The direction, a constant defined by the ImageFlip class
         */
        SobelFilterAction(String name, String iconName, String desc, Integer mnemonic, boolean direction) {
            super(name, iconName, desc, mnemonic);
            this.direction = direction;
        }

        public void actionPerformed(ActionEvent e) {
            if (!hasImage()) {
                return;
            }
            // Create and apply the filter
            target.getImage().apply(new SobelFilter(direction));
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * A class used to create an action for the EmbossFilter class
     * The action takes an int defined by the EmbossFilter class representing the
     * direction to emboss the image
     * 
     */
    public class EmbossFilterAction extends ImageAction {

        private int direction = 0;

        private boolean executedButton = false;

        JDialog frame;

        static JButton northWestButton = new JButton("North West");
        static JButton northButton = new JButton("North");
        static JButton northEastButton = new JButton("North East");
        static JButton eastButton = new JButton("East");
        static JButton southEastButton = new JButton("South East");
        static JButton southButton = new JButton("South");
        static JButton southWestButton = new JButton("South West");
        static JButton westButton = new JButton("West");

        static JButton[] buttons = {
                northWestButton,
                northButton,
                northEastButton,
                eastButton,
                southEastButton,
                southButton,
                southWestButton,
                westButton
        };

        /**
         * A default constructor that satisfies the ImageAction interface
         * 
         * @param name     The name of the action
         * @param iconName The icon for the action, usually null
         * @param desc     A description of the action preformed
         * @param mnemonic The shortcut key used in the editor
         */
        EmbossFilterAction(String name, String iconName, String desc, Integer mnemonic) {
            super(name, iconName, desc, mnemonic);
            frame = new JDialog(frame, "Emboss Filter", true);
            frame.setLocationRelativeTo(target.getParent());

            try {

                URL icon = ClassLoader.getSystemResource("icons/icon.png");
                Image image = ImageIO.read(icon);
                frame.setIconImage(image);
            } catch (Exception e) {
                JLabel erroLabel = new JLabel(e.toString());
                JOptionPane.showMessageDialog(frame, erroLabel, "Error",
                        JOptionPane.OK_OPTION, null);
            }
        }

        /**
         * A constructor that takes the params defining the action
         * 
         * The directon to emboss the image in can be set by a static call to the
         * EmbossFilter class
         * 
         * 
         * @param name      The name of the action
         * @param iconName  The root name of the icon, used to set the small and large
         * @param desc      A description of the action preformed
         * @param mnemonic  The shorcut key
         * @param direction The direction, a constant defined by the EmbossFilter class
         */
        EmbossFilterAction(String name, String iconName, String desc, Integer mnemonic, int direction) {
            super(name, iconName, desc, mnemonic);
            this.direction = direction;

        }

        public void actionPerformed(ActionEvent e) {
            if (!hasImage()) {
                return;
            }
            // Checking whether the ActionEvent is from the menu or from the buttons.
            if (e.getSource().getClass() == JMenuItem.class) {
                executedButton = false;
            }
            // Checking which button produced the event
            for (int i = 0; i < buttons.length; i++) {
                if (buttons[i] == e.getSource() && !executedButton) {
                    // Setting the direction of the emboss filter.
                    direction = i;
                    target.getImage().apply(new EmbossFilter(direction));
                    target.repaint();
                    executedButton = true;
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                    return;
                }
            }
            // If the ActionEvent was produced by the JMenu
            if (!executedButton) {
                // Add listeners to the buttons
                for (JButton b : buttons) {
                    b.addActionListener(this);
                    b.setActionCommand(b.getName());
                }
                target.getParent().revalidate();
                frame.setLocationRelativeTo(target.getParent());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.revalidate();
                frame.repaint();
                // Set up the content pane.
                addComponentsToPane(frame.getContentPane());

                // Display the window.
                frame.pack();
                frame.setVisible(true);

            }

        }

        public static void addComponentsToPane(Container pane) {

            JButton button;
            pane.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            c.fill = GridBagConstraints.HORIZONTAL;
            button = buttons[0];
            c.weightx = 0.5;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 0;
            pane.add(button, c);

            button = buttons[1];
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;
            c.gridx = 1;
            c.gridy = 0;
            pane.add(button, c);

            button = buttons[2];
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;
            c.gridx = 2;
            c.gridy = 0;
            pane.add(button, c);

            button = buttons[7];
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.0;
            c.gridx = 0;
            c.gridy = 1;
            pane.add(button, c);

            // button = new JButton();
            // c.fill = GridBagConstraints.HORIZONTAL;
            // c.weightx = 0.0;
            // c.gridx = 1;
            // c.gridy = 1;
            // pane.add(button, c);

            button = buttons[3];
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.0;
            c.gridx = 2;
            c.gridy = 1;
            pane.add(button, c);

            button = buttons[6];
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.0;
            c.gridx = 0;
            c.gridy = 2;
            pane.add(button, c);

            button = buttons[5];
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.0;
            c.gridx = 1;
            c.gridy = 2;
            pane.add(button, c);

            button = buttons[4];
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.0;
            c.gridx = 2;
            c.gridy = 2;
            pane.add(button, c);

        }

    }

}