package cosc202.andie;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.Action;
import cosc202.andie.DrawTool.DrawRectangle;

/**
 * A class used to add drawing tools to the toolbar.
 * 
 */
public class DrawActions {

    /** A list of actions for the Draw menu. */
    protected ArrayList<Action> actions;

    /**
     * A constructor that adds the actions to the actions arraylist.
     */
    public DrawActions() {

        actions = new ArrayList<Action>();
        actions.add(new MoveAction("Move", "move", "desc", null));
        actions.add(new drawAction("Draw", "draw", "desc", null));
    }

    /**
     * A method used to create and return a list of the tools needed for the toolbar
     * 
     * @return An arraylist to be added to the toolbar
     */
    public ArrayList<Action> getTools() {

        ArrayList<Action> drawTools = new ArrayList<Action>();

        drawTools.add(actions.get(0));
        drawTools.add(actions.get(1));

        return drawTools;
    }

    /**
     * A class used to add the Draw Rectangle tool to the toolbar
     */
    public class drawAction extends ImageAction {

        public drawAction(String name, String iconName, String desc, Integer mnemonic) {
            super(name, iconName, desc, mnemonic);

        }

        public void actionPerformed(ActionEvent e) {

            if (!hasImage()) {
                return;
            }

            target.setDrawTool(new DrawRectangle());

        }

    }

    /**
     * A class used to set the default action of the mouse back moveing the image
     * when the move tool is clicked.
     */
    public class MoveAction extends ImageAction {

        public MoveAction(String name, String iconName, String desc, Integer mnemonic) {
            super(name, iconName, desc, mnemonic);

        }

        public void actionPerformed(ActionEvent e) {

            if (!hasImage()) {
                return;
            }

            target.setDrawTool(null);

        }

    }

}
