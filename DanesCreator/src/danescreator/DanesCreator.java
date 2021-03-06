/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package danescreator;

import Core.PetriNet;
import GUI.Controller;
import GUI.View;
import javax.swing.UIManager;
//import sun.applet.Main;



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
        }
        catch(Exception e)
        {
            System.out.println("Error while loading graphics: "+e);
        }
            /*
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }*/
        
        
        // MVC
        PetriNet     model       =   new PetriNet("Empty");
        Controller   controller  =   new Controller(model);        
        View         view        =   new View(model,controller);
        
        
    }
}
