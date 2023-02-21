package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * <p>
 * 
 * Actions provided by the Macro menu.
 * </p>
 * 
 * <p>
 * The Macro menu contains actions that record and store macros which can be
 * applied later.
 * </p>
 * 
 * @author Matt Tyler & Dominic Tekanene
 */
public class MacrosActions {
    protected ArrayList<Action> actions;
    public static String iconName;

    public MacrosActions() {
        actions = new ArrayList<Action>();

        actions.add(new recordMacroAction("Record", "startRecord", "Start and Stop recording macros",
                null));
        actions.add(new openMacroAction("Open File", "open", "Open file to find a macro to apply on image",
                null));
    }

    /**
     * <p>
     * Create a menu contianing the list of Macro actions.
     * </p>
     * 
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        JMenu macroMenu = new JMenu("Macro");
        for (Action action : actions) {
            macroMenu.add(new JMenuItem(action));
        }

        return macroMenu;
    }

    public ArrayList<Action> getTools() {
        ArrayList<Action> macroTools = new ArrayList<Action>();

        macroTools.add(actions.get(0));
        return macroTools;
    }

    /**
     * <p>
     * Action to open a file chooser for macros.
     * </p>
     */
    public class openMacroAction extends ImageAction {

        /**
         * <p>
         * Create a new open macro action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param iconName An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        openMacroAction(String name, String iconName, String desc, Integer mnemonic) {
            super(name, iconName, desc, mnemonic);

        }

        /**
         * <p>
         * Callback for when the open-macro action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the openMacroAction is
         * triggered.
         * It searches for macros in a file to apply on an image
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
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Macros", "macro");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(target.getParent());

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String macroFilePath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImage().openMacro(macroFilePath);
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
     * Action to record image operations that were added to an image.
     * </p>
     */
    public class recordMacroAction extends ImageAction {
        boolean recording;

        /**
         * <p>
         * Create a new record macro action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param iconName An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        recordMacroAction(String name, String iconName, String desc, Integer mnemonic) {
            super(name, iconName, desc, mnemonic);
        }

        recordMacroAction(String name, String iconName, String desc, Integer mnemonic, boolean recording) {
            super(name, iconName, desc, mnemonic);
            this.recording = recording;
        }

        /**
         * <p>
         * Callback for when the record-macro action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the recordMacroAction is
         * triggered.
         * It starts recording when triggered a first time and stops recording a second
         * time it is pressed.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (!hasImage()) {
                return;
            }

            if (target.getImage().getRecording()) {
                setIcons("startRecord", "start recording");

                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showSaveDialog(target.getParent());

                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        String macroFilePath = fileChooser.getSelectedFile().getCanonicalPath();
                        try {
                            target.getImage().saveMacro(macroFilePath);
                        } catch (Exception e1) {

                            e1.printStackTrace();
                        }
                    } catch (Exception ex) {
                        JLabel erroLabel = new JLabel(ex.toString());
                        JOptionPane.showMessageDialog(target.getParent(), erroLabel, "Error",
                                JOptionPane.OK_OPTION, null);
                    }
                } else {
                    target.getImage().setRecording(false);
                }
            } else if (!target.getImage().getRecording()) {
                setIcons("stopRecord", "stop recording");
                target.getImage().setRecording(true);
            }

        }

    }
}
