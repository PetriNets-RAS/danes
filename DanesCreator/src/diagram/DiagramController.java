/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diagram;

import danescreator.MainFrame;
import danescreator.PetrihoSiet;

/**
 *
 * @author marek
 */
public class DiagramController {
    private PetrihoSiet petrihoSiet;
    private MainFrame   mainFrame;

    public DiagramController(PetrihoSiet pa_petrihoSiet, MainFrame pa_mainFrame) {
        this.petrihoSiet    = pa_petrihoSiet;
        this.mainFrame      = pa_mainFrame;
        
        this.mainFrame.setVisible(true);
    }
/*
    public PetrihoSiet getPetrihoSiet() {
        return petrihoSiet;
    }
   */
    
    
}
