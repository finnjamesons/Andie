package cosc202.andie;

import javax.swing.Action;
import javax.swing.JToolBar;

/**
 * A class that creates a toolbar and populates it with common tools
 * 
 */
public class Toolbar extends JToolBar {

    // A default constructor that returns an empty Toolbar
    Toolbar() {
    }

    /**
     * A constructor that takes variaus references to actions used to populate the
     * toolbar
     * 
     * @param viewActions   Actions related to the image view
     * @param editActions   Actions related to editing the image
     * @param fileActions   Actions related file tasks
     * @param macrosActions Actions related file tasks
     */
    Toolbar(DrawActions drawActions, ViewActions viewActions, EditActions editActions, FileActions fileActions,
            MacrosActions macrosActions) {

        // Sets the orientation to vertcal
        this.setOrientation(JToolBar.VERTICAL);
        // Adds a seperator at the top of the bar for aesthetic reasons
        this.addSeparator();

        for (Action action : drawActions.getTools()) {
            // Adds the action to the toolbar
            // Sets button equal to the JButton returned by the add method
            var button = this.add(action);
            // Removes the square that appears around the icon when button is focused
            button.setFocusPainted(false);
            // Removes the border that's default shown around the button
            button.setBorderPainted(false);
        }

        this.addSeparator();
        // Loops over the list of tools returned by the viewActions getTools method
        for (Action action : viewActions.getTools()) {
            // Adds the action to the toolbar
            // Sets button equal to the JButton returned by the add method
            var button = this.add(action);
            // Removes the square that appears around the icon when button is focused
            button.setFocusPainted(false);
            // Removes the border that's default shown around the button
            button.setBorderPainted(false);
        }
        // Adds a seperator at the top of the bar for aesthetic reasons
        this.addSeparator();
        // Follows the same process as above for the editActions
        for (Action action : editActions.getTools()) {
            var button = this.add(action);
            button.setFocusPainted(false);
            button.setBorderPainted(false);

        }

        this.addSeparator();
        // Follows the same process as above for the fileActions
        for (Action action : macrosActions.getTools()) {
            var button = this.add(action);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
        }

        // Adds a seperator at the top of the bar for aesthetic reasons
        this.addSeparator();
        // Follows the same process as above for the fileActions
        for (Action action : fileActions.getTools()) {
            var button = this.add(action);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
        }

        // Sets the tooltips to appear on rollover
        this.setRollover(true);
        // Disables the floatable feature of the toolbar
        this.setFloatable(false);

    }

}
