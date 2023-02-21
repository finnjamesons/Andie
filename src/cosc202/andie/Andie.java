package cosc202.andie;

import java.net.URL;
import java.awt.*;
import javax.swing.*;
import javax.imageio.*;

/**
 * <p>
 * Main class for A Non-Destructive Image Editor (ANDIE).
 * Dom
 * </p>
 * 
 * <p>
 * This class is the entry point for the program.
 * It creates a Graphical User Interface (GUI) that provides access to various
 * image editing and processing operations.
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
public class Andie {

    /**
     * <p>
     * Launches the main GUI for the ANDIE program.
     * </p>
     * 
     * <p>
     * This method sets up an interface consisting of an active image (an
     * {@code ImagePanel})
     * and various menus which can be used to trigger operations to load, save,
     * edit, etc.
     * These operations are implemented {@link ImageOperation}s and triggerd via
     * {@code ImageAction}s grouped by their general purpose into menus.
     * </p>
     * 
     * @see ImagePanel
     * @see ImageAction
     * @see ImageOperation
     * @see FileActions
     * @see EditActions
     * @see ViewActions
     * @see FilterActions
     * @see ColourActions
     * @see TransformActions
     * 
     * @throws Exception if something goes wrong.
     */
    private static void createAndShowGUI() throws Exception {
        // Set up the main GUI frame
        JFrame frame = new JFrame("ANDIE");

        // Noticed bug where file path can't resolve on some versions of window. Put in
        // try catch statement for now.
        // Working solutions include "andie/src/icon.png" on Angus' machine, "/icon.png"
        // on Matt machine. No change required on Dom's machine.
        try {

            URL icon = ClassLoader.getSystemResource("icons/icon.png");
            Image image = ImageIO.read(icon);
            frame.setIconImage(image);
        } catch (Exception e) {
            JLabel erroLabel = new JLabel(e.toString());
            JOptionPane.showMessageDialog(frame, erroLabel, "Error",
                    JOptionPane.OK_OPTION, null);
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The main content area is an ImagePanel
        ImagePanel imagePanel = new ImagePanel();
        ImageAction.setTarget(imagePanel);
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Add in menus for various types of action the user may perform.
        JMenuBar menuBar = new JMenuBar();

        // File menus are pretty standard, so things that usually go in File menus go
        // here.
        FileActions fileActions = new FileActions();
        menuBar.add(fileActions.createMenu());

        // Likewise Edit menus are very common, so should be clear what might go here.
        EditActions editActions = new EditActions();
        menuBar.add(editActions.createMenu());

        // View actions control how the image is displayed, but do not alter its actual
        // content
        ViewActions viewActions = new ViewActions();
        menuBar.add(viewActions.createMenu());

        // Filters apply a per-pixel operation to the image, generally based on a local
        // window
        FilterActions filterActions = new FilterActions();
        menuBar.add(filterActions.createMenu());

        // Actions that affect the representation of colour in the image
        ColourActions colourActions = new ColourActions();
        menuBar.add(colourActions.createMenu());

        DrawActions drawActions = new DrawActions();

        RightSideMenu rightSideMenu = new RightSideMenu();

        frame.add(rightSideMenu, BorderLayout.EAST);

        OptionsBar optionsBar = new OptionsBar();

        frame.add(optionsBar, BorderLayout.NORTH);
        imagePanel.setOptionsBar(optionsBar);

        // Changes that affect size of image
        TransformActions transformActions = new TransformActions();
        menuBar.add(transformActions.createMenu());

        // Macro Actions
        MacrosActions macroActions = new MacrosActions();
        menuBar.add(macroActions.createMenu());

        // Creates a toolbar
        Toolbar toolbar = new Toolbar(drawActions, viewActions, editActions, fileActions, macroActions);
        // Adds the toolbar to the frame on the left
        frame.add(toolbar, BorderLayout.WEST);

        frame.setJMenuBar(menuBar);

        frame.setPreferredSize(new Dimension(800, 800));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * <p>
     * Main entry point to the ANDIE program.
     * </p>
     * 
     * <p>
     * Creates and launches the main GUI in a separate thread.
     * As a result, this is essentially a wrapper around {@code createAndShowGUI()}.
     * </p>
     * 
     * @param args Command line arguments, not currently used
     * @throws Exception If something goes awry
     * @see #createAndShowGUI()
     */
    public static void main(String[] args) throws Exception {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (Exception ex) {
                    System.exit(1);
                }
            }
        });
    }
}
