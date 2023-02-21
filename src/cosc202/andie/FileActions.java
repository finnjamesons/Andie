package cosc202.andie;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * <p>
 * Actions provided by the File menu.
 * </p>
 * 
 * <p>
 * The File menu is very common across applications,
 * and there are several items that the user will expect to find here.
 * Opening and saving files is an obvious one, but also exiting the program.
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
public class FileActions {

    /** A list of actions for the File menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of File menu actions.
     * </p>
     */
    public FileActions() {
        actions = new ArrayList<Action>();
        actions.add(new FileOpenAction("Open", "open", "Open", Integer.valueOf(KeyEvent.VK_O)));
        actions.add(new FileSaveAction("Save", "save", "Save", Integer.valueOf(KeyEvent.VK_S)));
        actions.add(new FileSaveAsAction("Save As", "saveas", "Save as", Integer.valueOf(KeyEvent.VK_A)));
        actions.add(
                new ImageExportAction("Export Image", "export", "Exports The Image", Integer.valueOf(KeyEvent.VK_E)));
        actions.add(new FileExitAction("Exit", "exit", "Exit ANDIE", Integer.valueOf(KeyEvent.VK_ESCAPE)));

    }

    /**
     * <p>
     * Create a menu contianing the list of File actions.
     * </p>
     * 
     * @return The File menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("File");

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * A method that returns the most common actions used for the toolbar.
     * 
     * @return An arraylist of common file actions for the toolbar
     */
    public ArrayList<Action> getTools() {

        ArrayList<Action> fileTools = new ArrayList<Action>();

        fileTools.add(actions.get(1));
        return fileTools;
    }

    /**
     * <p>
     * Action to open an image from file.
     * </p>
     * 
     * @see EditableImage#open(String)
     */
    public class FileOpenAction extends ImageAction {

        /**
         * <p>
         * Create a new file-open action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param iconName An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileOpenAction(String name, String iconName, String desc, Integer mnemonic) {
            super(name, iconName, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-open action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileOpenAction is triggered.
         * It prompts the user to select a file and opens it as an image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            if (saveCheck()) {
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            // Used for filtering available file formats to those supported by ANDIE.
            // Currently hardcoded
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Supported Picture Files", "jpg", "jpeg",
                    "png", "bmp", "gif", "tif", "tiff", "wbmp");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(target.getParent());

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImage().open(imageFilepath);
                } catch (Exception ex) {
                    JLabel errorLabel = new JLabel(ex.toString());
                    JOptionPane.showMessageDialog(target.getParent(), errorLabel, "Error",
                            JOptionPane.OK_OPTION, null);

                }
            }

            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to save an image to its current file location.
     * </p>
     * 
     * @see EditableImage#save()
     */
    public class FileSaveAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param iconName An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileSaveAction(String name, String iconName, String desc, Integer mnemonic) {
            super(name, iconName, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-save action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAction is triggered.
         * It saves the image to its original filepath.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (!hasImage()) {
                return;
            }
            try {
                target.getImage().save();
            } catch (Exception ex) {
                JLabel erroLabel = new JLabel(ex.toString());
                JOptionPane.showMessageDialog(target.getParent(), erroLabel, "Error",
                        JOptionPane.OK_OPTION, null);
            }
        }

    }

    /**
     * <p>
     * Action to save an image to a new file location.
     * </p>
     * 
     * @see EditableImage#saveAs(String)
     */
    public class FileSaveAsAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save-as action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param iconName An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileSaveAsAction(String name, String iconName, String desc, Integer mnemonic) {
            super(name, iconName, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-save-as action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAsAction is triggered.
         * It prompts the user to select a file and saves the image to it.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (!hasImage()) {
                return;
            }
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(target.getParent());

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    Path stringPath = Paths.get(imageFilepath);
                    // Checking whether the file name already exists in the directory
                    if (stringPath.toFile().isFile()) {
                        // Creating an option pane if it does
                        JLabel saveFileMessage = new JLabel(
                                "A file of the same name already exists. Would you like to continue?");
                        int option2 = JOptionPane.showOptionDialog(target.getParent(),
                                saveFileMessage,
                                "Overwrite file?",
                                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
                                null, null);
                        // Check the return value from the dialog box.
                        if (option2 != JOptionPane.OK_OPTION) {
                            return;
                        }
                    }
                    target.getImage().saveAs(imageFilepath);
                } catch (Exception ex) {
                    JLabel erroLabel = new JLabel(ex.toString());
                    JOptionPane.showMessageDialog(target.getParent(), erroLabel, "Error",
                            JOptionPane.OK_OPTION, null);
                }
            }
        }

    }

    /**
     * An action to export the current file as an image
     * 
     */
    public class ImageExportAction extends ImageAction {

        ImageExportAction(String name, String iconName, String desc, Integer mnemonic) {
            super(name, iconName, desc, mnemonic);
        }

        /**
         * A method that returns an alphabetised array of available string formats
         * 
         * Method from Java Docs save image tutorial, released under licence by Oracle
         * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
         * 
         * @return A list of available export formats
         */
        public String[] getFormats() {
            String[] formats = ImageIO.getWriterFormatNames();
            TreeSet<String> formatSet = new TreeSet<String>();
            for (String s : formats) {
                formatSet.add(s.toLowerCase());
            }
            return formatSet.toArray(new String[0]);
        }

        /**
         * Callback for when the image export is triggered
         * 
         * The method is preformed everytime the ImageExport action is called and is
         * used to export the imaget o a variety of file types
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // A gaurd clause that returns early if no image present to export
            if (!hasImage()) {
                return;
            }
            // Makes a combo box with available image formats
            JComboBox<String> formatOptions = new JComboBox<>(getFormats());
            // Opens an option pain with the available file formats to be chosen
            int option = JOptionPane.showOptionDialog(target.getParent(), formatOptions, "Select Image Format",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, (Icon) getValue(Action.LARGE_ICON_KEY),
                    null,
                    null);

            // Returns early if option pain is canceled
            if (option != JOptionPane.OK_OPTION) {
                return;
            }

            // Returns a string representation of the selected format
            String format = formatOptions.getSelectedItem().toString();
            // Creates a local reference to the current image
            BufferedImage output = target.getImage().getCurrentImage();
            // Ininitalises a new file chooser
            JFileChooser fileChooser = new JFileChooser();
            // Creates a default output file with the same name as the file being edited and
            // its new output format
            File outputfile = new File(target.getImage().getImageFileName() + "." + format);

            // Sets the selected file as the current in the fileChooser
            fileChooser.setSelectedFile(outputfile);
            // Shows the save dialog and returns the result of the users action
            int result = fileChooser.showSaveDialog(target.getParent());

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    // Sets the outputfile equal to the selected file. Different if the user decided
                    // to rename the file
                    outputfile = fileChooser.getSelectedFile();
                    // Checking whether the file name already exists in the directory
                    if (outputfile.exists()) {
                        // Creating an option pane if it does
                        JLabel saveFileMessage = new JLabel(
                                "A file of the same name already exists. Would you like to continue?");
                        int option2 = JOptionPane.showOptionDialog(target.getParent(), saveFileMessage,
                                "Overwrite file?",
                                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
                                null, null);

                        // Check the return value from the dialog box.
                        if (option2 != JOptionPane.OK_OPTION) {
                            return;
                        }
                    }
                    // Writes the image to the output file in the correct format
                    ImageIO.write(output, format, outputfile);

                } catch (Exception el) {
                    JLabel erroLabel = new JLabel(el.toString());
                    JOptionPane.showMessageDialog(target.getParent(), erroLabel, "Error",
                            JOptionPane.OK_OPTION, null);

                }
            }

        }

    }

    /**
     * <p>
     * Action to quit the ANDIE application.
     * </p>
     */
    public class FileExitAction extends AbstractAction {

        /**
         * <p>
         * Create a new file-exit action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param iconName An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileExitAction(String name, String iconName, String desc, Integer mnemonic) {
            super(name);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);

            try {

                URL smallIconAddress = ClassLoader.getSystemResource("icons/" + iconName + "Small.png");

                URL largeIconAddress = ClassLoader.getSystemResource("icons/" + iconName + "Large.png");

                ImageIcon smallIcon = new ImageIcon(smallIconAddress, name);

                ImageIcon largeIcon = new ImageIcon(largeIconAddress, name);

                this.putValue(SMALL_ICON, smallIcon);
                this.putValue(LARGE_ICON_KEY, largeIcon);
            } catch (Exception e) {

            }
        }

        /**
         * <p>
         * Callback for when the file-exit action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileExitAction is triggered.
         * It quits the program.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            System.exit(0);
        }

    }

}
