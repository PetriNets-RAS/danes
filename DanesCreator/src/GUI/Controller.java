/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Core.Element;
import GUI.View;
import Core.PetriNet;
import Core.Place;
import Core.Transition;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author marek
 */
public class Controller {
    private PetriNet        petriNet;
    //private View        mainFrame;

    public Controller(PetriNet pa_petriNet) {
        this.petriNet    = pa_petriNet;
    }
    
    public void addPlace(String name, int x, int y) {        
        
        // Check if coordinates place is empty  
        if (!petriNet.isLocationEmpty(x, y))
            return;
             
        Place place;        
        
        if(name.equals("Place"))
        {            
            
            // Generate text name until it is unique
            do
            {
                place=new Place("Place"+Math.random());
                place.setDiagramElement(new DiagramElement(x, y));                
            }
            while      
               (!this.petriNet.addPlace(place));

        }
        else // Normal add
        {
                place=new Place(name);
                place.setDiagramElement(new DiagramElement(x, y));  
        }
    }
    public void addTransition(String name, int x, int y) {    
        
        // Check if coordinates place is empty  
        if (!petriNet.isLocationEmpty(x, y))
            return;
        
        Transition transition;        
        
        if (name.equals("Transition"))
        {   
            // Generate text name until it is unique
            do
            {
                transition=new Transition("Transition"+Math.random());
                transition.setDiagramElement(new DiagramElement(x, y));                
            }
            while      
               (!this.petriNet.addTransition(transition));
        }
        else // Normal add
        {
            transition=new Transition(name);
            transition.setDiagramElement(new DiagramElement(x, y));             
        }   
    }

}
