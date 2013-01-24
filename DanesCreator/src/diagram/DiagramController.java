/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diagram;

import danescreator.MainFrame;
import Core.PetriNet;

/**
 *
 * @author marek
 */
public class DiagramController {
    private PetriNet petrihoSiet;
    private MainFrame   mainFrame;

    public DiagramController(PetriNet pa_petrihoSiet, MainFrame pa_mainFrame) {
        this.petrihoSiet    = pa_petrihoSiet;
        this.mainFrame      = pa_mainFrame;
        
        this.mainFrame.setVisible(true);
    }
/*
    public PetriNet getPetrihoSiet() {
        return petrihoSiet;
    }
   */
    
    
}
