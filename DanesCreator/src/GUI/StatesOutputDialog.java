/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import StateSpace.State;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;
import org.w3c.dom.Document;

/**
 *
 * @author Michal
 */
public class StatesOutputDialog extends javax.swing.JDialog {

    /**
     * Creates new form StatesOutputDialog
     */
    private StyledDocument doc;
    private Style style;
    
    
    public StatesOutputDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        doc = statesPane.getStyledDocument();
        setLocation(getParent().getLocation().x+20, getParent().getLocation().y+20);
        style = statesPane.addStyle("I'm a Style", null);
        this.setTitle("Set of states");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        statesPane = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        statesPane.setEditable(false);
        jScrollPane1.setViewportView(statesPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 607, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    public void setStates(ArrayList<State> listOfStates){
        try {
            
            String msg = listOfStates.size()+" states found:\n";
            doc.insertString(doc.getLength(), msg, style);
            int i=1;
            for(State state:listOfStates){
                doc.insertString(doc.getLength(), i+") "+state.toString()+"\n", style);
                i++;
            }            
        } catch (BadLocationException ex) {
            Logger.getLogger(StatesOutputDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane statesPane;
    // End of variables declaration//GEN-END:variables
}