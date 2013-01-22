/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package danescreator;

import diagram.DiagramController;
import javax.swing.JFrame;

/**
 *
 * @author marek
 */
public class DanesCreator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
        CalcModel      model      = new CalcModel();
        CalcView       view       = new CalcView(model);
        CalcController controller = new CalcController(model, view);
        */
        
        // MVC
        PetrihoSiet         model       =   new PetrihoSiet("Nasa siet");
        MainFrame           view        =   new MainFrame(model);
        DiagramController   controller  =   new DiagramController(model,view);
        
    }
}
