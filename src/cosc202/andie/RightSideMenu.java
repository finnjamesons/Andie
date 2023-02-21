package cosc202.andie;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The menu on the right. Currently used for the colour picker. Lots of space to
 * add more.
 *
 */
public class RightSideMenu extends JPanel implements ChangeListener {

    // The colour selected by the colour picker
    public static Color colour;

    // The current colour chooser
    private JColorChooser colourChooser;

    // Constructor creates the menu
    public RightSideMenu() {

        // Creates a new JColorChooser
        colourChooser = new JColorChooser();

        // Gets an array of their components
        Component[] components = colourChooser.getComponents();

        // Iterates over the componetns and sets their visibility to false
        for (Component comp : components) {
            comp.setVisible(false);
        }

        // gets an array of the colour chooser panels.
        AbstractColorChooserPanel[] pannels = colourChooser.getChooserPanels();
        // Creates a new array
        AbstractColorChooserPanel[] pannel = new AbstractColorChooserPanel[1];

        // Sets only the HSV pannel as a member of that array
        pannel[0] = pannels[1];
        // Sets the sliders to hide
        pannel[0].getComponent(0).setVisible(false);
        // Adds the panel back to the menu
        colourChooser.setChooserPanels(pannel);
        // Adds this class as the change listener
        colourChooser.getSelectionModel().addChangeListener(this);
        // Puts it on a JPanel for future layout reasons
        JPanel colourChooserHolder = new JPanel();
        // Adds it to the panel
        colourChooserHolder.add(colourChooser);
        // Adds that panel to this one
        this.add(colourChooserHolder);
        // Sets the colour variable equal to the colourchooser default
        colour = colourChooser.getColor();

    }

    /**
     * A method for the change listener interface. Called whenever the state of the
     * colour chooser panel is changed.
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        // Updates the colour variable to the new one.
        colour = colourChooser.getColor();
    }

}
