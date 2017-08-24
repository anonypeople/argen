package unalcol.agents.simulate.gui;

import javax.swing.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>
 * <p>Company: Universidad Nacional de Colombia</p>
 *
 * @author Jonatan Gómez
 * @version 1.0
 */
public class DrawPanel extends JPanel {
    /**
     * Serialization purposes
     */
    private static final long serialVersionUID = 1218559364392825948L;

    public Drawer drawer = null;

    public DrawPanel() {
        this(null);
    }

    public DrawPanel(Drawer drawer) {
        this.drawer = drawer;
        setBackground(new Color(255, 255, 255));
    }

    public Drawer getDrawer() {
        return drawer;
    }

    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }

    /**
     * Paints the graphic component
     *
     * @param g Graphic component
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (drawer != null) {
            Dimension d = this.getSize();
            drawer.setDimension(d.width, d.height);
            drawer.paint(g);
        }
    }
}
