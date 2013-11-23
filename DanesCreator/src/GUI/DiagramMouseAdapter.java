/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.View.DiagramPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author SYSTEM
 */
public class DiagramMouseAdapter extends MouseAdapter{
    private DiagramPanel diagramPanel;
    private JScrollPane diagramScrollPane;
    private int x;
    private int y;
    
    /**
     *
     * @param pDiagramPanel
     */
    public DiagramMouseAdapter(DiagramPanel pDiagramPanel, JScrollPane pDiagramScrollPane) {
        this.diagramPanel = pDiagramPanel;
        this.diagramScrollPane = pDiagramScrollPane;
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
        this.diagramPanel.setHandCursor();
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
        /*
        if(cursorButton.isSelected()){
            diagramPanel.setCursor(handCursor);
        }
        */ 
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
