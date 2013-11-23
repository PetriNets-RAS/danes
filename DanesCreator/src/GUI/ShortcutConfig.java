/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author MISO
 */
public class ShortcutConfig extends javax.swing.JDialog {
    private ShortcutsManager shortcutsManager;
    private int placeCode;
    private int resCode;
    private int transCode;
    private int arcCode;
    /**
     * Creates new form ShortcutConfig
     */
    public ShortcutConfig(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        if(parent instanceof View){
            this.shortcutsManager = ((View)parent).getShortcutsManager();
        }
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        arcKey = new javax.swing.JTextField();
        arcKeyLabel = new javax.swing.JLabel();
        transKeyLabel = new javax.swing.JLabel();
        transKey = new javax.swing.JTextField();
        resKey = new javax.swing.JTextField();
        resKeyLabel = new javax.swing.JLabel();
        placeKeyLabel = new javax.swing.JLabel();
        placeKey = new javax.swing.JTextField();
        save = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        arcKey.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                arcKeyKeyReleased(evt);
            }
        });

        arcKeyLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        arcKeyLabel.setText("Arc");

        transKeyLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        transKeyLabel.setText("Transition");

        transKey.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                transKeyKeyReleased(evt);
            }
        });

        resKey.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                resKeyKeyReleased(evt);
            }
        });

        resKeyLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        resKeyLabel.setText("Resource");

        placeKeyLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        placeKeyLabel.setText("Place");

        placeKey.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                placeKeyKeyReleased(evt);
            }
        });

        save.setLabel("Save");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(placeKeyLabel)
                    .addComponent(resKeyLabel)
                    .addComponent(transKeyLabel)
                    .addComponent(arcKeyLabel))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(arcKey, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(transKey, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(placeKey)
                        .addComponent(resKey, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(save)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(placeKeyLabel)
                    .addComponent(placeKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resKeyLabel)
                    .addComponent(resKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(transKeyLabel)
                    .addComponent(transKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(arcKeyLabel)
                    .addComponent(arcKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(save)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        this.shortcutsManager.setAddArcKey(this.arcCode);
        this.shortcutsManager.setAddPlaceKey(this.placeCode);
        this.shortcutsManager.setAddResourceKey(this.resCode);
        this.shortcutsManager.setAddTransitionKey(this.transCode);
        this.shortcutsManager.writeKeysToConfig();
    }//GEN-LAST:event_saveActionPerformed

    private void placeKeyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_placeKeyKeyReleased
        this.placeCode = evt.getKeyCode();
        this.placeKey.setText(this.placeKey.getText().toUpperCase());
    }//GEN-LAST:event_placeKeyKeyReleased

    private void resKeyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_resKeyKeyReleased
        this.resCode = evt.getKeyCode();
        this.resKey.setText(this.resKey.getText().toUpperCase());
    }//GEN-LAST:event_resKeyKeyReleased

    private void transKeyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_transKeyKeyReleased
        this.transCode = evt.getKeyCode();
        this.transKey.setText(this.transKey.getText().toUpperCase());
    }//GEN-LAST:event_transKeyKeyReleased

    private void arcKeyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_arcKeyKeyReleased
        this.arcCode = evt.getKeyCode();
        this.arcKey.setText(this.arcKey.getText().toUpperCase());
    }//GEN-LAST:event_arcKeyKeyReleased

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        System.out.print(this.shortcutsManager.toString());
        this.arcKey.setText(""+(char)this.shortcutsManager.getAddArcKey());
        this.resKey.setText(""+(char)this.shortcutsManager.getAddResourceKey());
        this.transKey.setText(""+(char)this.shortcutsManager.getAddTransitionKey());
        this.placeKey.setText(""+(char)this.shortcutsManager.getAddPlaceKey());
        this.arcCode = this.arcKey.getText().codePointAt(0);
        this.placeCode = this.placeKey.getText().codePointAt(0);
        this.transCode = this.transKey.getText().codePointAt(0);
        this.resCode = this.resKey.getText().codePointAt(0);
    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ShortcutConfig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ShortcutConfig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ShortcutConfig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ShortcutConfig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ShortcutConfig dialog = new ShortcutConfig(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField arcKey;
    private javax.swing.JLabel arcKeyLabel;
    private javax.swing.JTextField placeKey;
    private javax.swing.JLabel placeKeyLabel;
    private javax.swing.JTextField resKey;
    private javax.swing.JLabel resKeyLabel;
    private javax.swing.JButton save;
    private javax.swing.JTextField transKey;
    private javax.swing.JLabel transKeyLabel;
    // End of variables declaration//GEN-END:variables
}