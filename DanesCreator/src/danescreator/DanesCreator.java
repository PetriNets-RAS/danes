/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package danescreator;

import GUI.View;
import Core.PetriNet;
import GUI.Controller;
import javax.swing.JFrame;
import javax.swing.UIManager;
import sun.applet.Main;



/**
 *
 * @author marek
 */
public class DanesCreator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // prisposobit zobrazenie pre prostredie OS
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        /*
        CalcModel      model      = new CalcModel();
        CalcView       view       = new CalcView(model);
        CalcController controller = new CalcController(model, view);
        */
        
        // MVC
        PetriNet     model       =   new PetriNet("Empty");
        Controller   controller  =   new Controller(model);        
        View         view        =   new View(model,controller);
        
        
    }
}
