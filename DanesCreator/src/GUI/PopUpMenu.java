/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.View.DiagramPanel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

/**
 *
 * @author MISO
 */
public class PopUpMenu extends JPopupMenu {
    JMenuItem copyItem,pasteItem,deleteItem,saveItem,saveAsItem,loadItem;
    DiagramPanel diagramPanel;
    public PopUpMenu(DiagramPanel pPanel){
        this.diagramPanel = pPanel;
        saveItem = new JMenuItem("Save");
        saveItem.setAccelerator(KeyStroke.getKeyStroke('S', KeyEvent.CTRL_DOWN_MASK));
        this.add(saveItem);
        saveItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveClicked(evt);
            }
        });
        saveAsItem = new JMenuItem("Save as...");
        this.add(saveAsItem);
        saveAsItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsClicked(evt);
            }
        });
        loadItem = new JMenuItem("Load");
        loadItem.setAccelerator(KeyStroke.getKeyStroke('L', KeyEvent.CTRL_DOWN_MASK));
        this.add(loadItem);
        loadItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadClicked(evt);
            }
        });
        this.addSeparator();
        copyItem = new JMenuItem("Copy");
        copyItem.setAccelerator(KeyStroke.getKeyStroke('C', KeyEvent.CTRL_DOWN_MASK));
        this.add(copyItem);
        copyItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyClicked(evt);
            }
        });    
        pasteItem = new JMenuItem("Paste");
        this.add(pasteItem);
        pasteItem.setAccelerator(KeyStroke.getKeyStroke('V', KeyEvent.CTRL_DOWN_MASK));
        pasteItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteClicked(evt);
            }
        });  
        deleteItem = new JMenuItem("Delete");
        this.add(deleteItem);
        deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0));
        deleteItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteClicked(evt);
            }
        }); 
    }
    private void copyClicked(ActionEvent evt) {
        diagramPanel.copySelectedElements();
    }
    private void pasteClicked(ActionEvent evt) {
        diagramPanel.pasteSelectedElements(0,0);
    }
    private void deleteClicked(ActionEvent evt) {
        diagramPanel.deleteSelectedElements();
    }
    private void saveClicked(ActionEvent evt) {
        diagramPanel.saveGraph();
    }
    private void loadClicked(ActionEvent evt) {
        diagramPanel.loadGraph();
        diagramPanel.repaint();
    }
    private void saveAsClicked(ActionEvent evt) {
        diagramPanel.saveGraphAs();
    }
}
