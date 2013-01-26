/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package danescreator;

import GUI.View;
import Core.PetriNet;
import GUI.Controller;
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
        PetriNet     model       =   new PetriNet("Empty");
        Controller   controller  =   new Controller(model);        
        View         view        =   new View(model,controller);
        
        
    }
}
