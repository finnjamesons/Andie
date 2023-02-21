package cosc202.andie;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class OptionsBar extends JPanel {

    public OptionsBar() {

        this.setLayout(new FlowLayout(FlowLayout.LEADING, 50, 10));

    }

    public void setOptions(ArrayList<JComponent> options) {

        clearOptions();

        if (options == null) {
            return;
        }

        for (JComponent op : options) {
            this.add(op);
        }
        this.revalidate();
    }

    public void clearOptions() {

        this.removeAll();
        this.revalidate();
        this.repaint();

    }

}