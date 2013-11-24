/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DiagramAdapters;

import ConfigManagers.ShortcutsManager;
import GUI.View.DiagramPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class DiagramKeyAdapter extends KeyAdapter {//implements KeyListener{
    private DiagramPanel diagramPanel;
    private ShortcutsManager shortcutsManager;
    public DiagramKeyAdapter(DiagramPanel pDiagramPanel, ShortcutsManager pShortcutsManager){
        this.diagramPanel = pDiagramPanel;
        this.shortcutsManager = pShortcutsManager;
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
        //diagramPanel.isCTRLdown=ke.isControlDown();            
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        this.diagramPanel.setIsCTRLdown(ke.isControlDown());            
        if(ke.isControlDown() && ke.getKeyCode() == KeyEvent.VK_V){
            this.diagramPanel.pasteSelectedElements(0,0);
        }
        if(ke.isControlDown() && ke.getKeyCode() == KeyEvent.VK_C){
            this.diagramPanel.copySelectedElements();
        }
        if(ke.getKeyCode() == KeyEvent.VK_DELETE){
            this.diagramPanel.deleteSelectedElements();
        }

        if(ke.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.diagramPanel.resetAllButtons();
        }

        if(ke.getKeyCode() == shortcutsManager.getAddArcKey()){
            this.diagramPanel.setArcButtonSelected(true);
        }
        if(ke.getKeyCode() == shortcutsManager.getAddPlaceKey()){
            this.diagramPanel.setEllipseButtonSelected(true);
        }
        if(ke.getKeyCode() == shortcutsManager.getAddResourceKey()){
            this.diagramPanel.setResourceButtonSelected(true);
        }
        if(ke.getKeyCode() == shortcutsManager.getAddTransitionKey()){
            this.diagramPanel.setTransitionButtonSelected(true);
        }

        this.diagramPanel.repaint();
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        diagramPanel.setIsCTRLdown(false);
    }

}
