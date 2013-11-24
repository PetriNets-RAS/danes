/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DiagramAdapters;

import Core.Element;
import GUI.Controller;
import GUI.View.DiagramPanel;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author SYSTEM
 */
public class DiagramMouseAdapter extends MouseAdapter{
    private DiagramPanel diagramPanel;
    private JScrollPane diagramScrollPane;
    private Controller controller;
    private int x;
    private int y;
    
    /**
     *
     * @param pDiagramPanel
     */
    public DiagramMouseAdapter(DiagramPanel pDiagramPanel, JScrollPane pDiagramScrollPane, Controller pController) {
        this.diagramPanel = pDiagramPanel;
        this.diagramScrollPane = pDiagramScrollPane;
        this.controller = pController;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        this.diagramPanel.requestFocusInWindow();
        
        // Save current            
        this.x = e.getX();
        this.y = e.getY();
        // Check for click button
        if (SwingUtilities.isLeftMouseButton(e)) {
            this.diagramPanel.mouseLeftClick(getX(), getY());
        }
        if (SwingUtilities.isRightMouseButton(e)) {
            this.diagramPanel.mouseRightClick(getX(), getY());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.diagramPanel.setMoveHandCursor();
        if (SwingUtilities.isLeftMouseButton(e)&&e.isControlDown()) {
            int scrollPositionX = this.diagramScrollPane.getViewport().getViewPosition().x;
            int scrollPositionY = this.diagramScrollPane.getViewport().getViewPosition().y;
            int offsetX = (getX() - e.getX()+scrollPositionX);
            int offsetY = (getY() - e.getY()+scrollPositionY);
            if(offsetY > 0 && offsetX > 0){
            this.diagramScrollPane.getViewport().setViewPosition(new java.awt.Point(offsetX,offsetY));
            }
        }
        // Left
        else if (SwingUtilities.isLeftMouseButton(e)) {
            this.diagramPanel.mouseLeftDragged(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Old location is different from current
        if (true) {
            // Left
            if (SwingUtilities.isLeftMouseButton(e)) {
                this.diagramPanel.mouseLeftReleased(getX(), getY(), e.getX(), e.getY());
            }
            if (SwingUtilities.isRightMouseButton(e)) {
                this.diagramPanel.mouseRightClick(e.getX(), e.getY());
                this.diagramPanel.getPopUpMenu().show(e.getComponent(), e.getX(), e.getY());
            }
        }
        this.diagramPanel.setDiagramCursor();
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        if (this.diagramPanel.getTimer() != null) {
            this.diagramPanel.getTimer().cancel();
        }

        Point p = e.getPoint();
        final Object o = this.controller.getLocationElement((int)(p.x/diagramPanel.getScaleRatio()[0]), (int)(p.y/diagramPanel.getScaleRatio()[0]));


        if (o != null && !(o instanceof Point)) {
            this.diagramPanel.setTimer(new Timer());
            this.diagramPanel.getTimer().schedule(new TimerTask() {
                @Override
                public void run() {
                    diagramPanel.setBubbleElement((Element) o);
                    diagramPanel.repaint();
                }
            }, 750);

        } else {

            this.diagramPanel.setBubbleElement(null);
            this.diagramPanel.repaint();
        }
    }
    
    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

}
