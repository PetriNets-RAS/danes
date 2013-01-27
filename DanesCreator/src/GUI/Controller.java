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

    public void moveElement(int x_old_location, int y_old_location, int x_new_location, int y_new_location) {
        // Source location is not empty
        if (petriNet.isLocationEmpty(x_old_location, y_old_location))
            return;
        // Destination location is empty
        if (!petriNet.isLocationEmpty(x_new_location, y_new_location))
            return;
        
        Element e = getLocationElement(x_old_location, y_old_location);
        e.setDiagramElement(new DiagramElement(x_new_location, y_new_location));            

    }

    public Element getLocationElement(int x, int y) {
        // Place
        for(Element e:petriNet.getListOfPlaces())
        {
            if (    e.getDiagramElement().getX()==x &&
                    e.getDiagramElement().getY()==y )
            {
                return e;
            }
        }
        // Transition
        for(Element e:petriNet.getListOfTransitions())
        {
            if (    e.getDiagramElement().getX()==x &&
                    e.getDiagramElement().getY()==y )
            {
                return e;
            }
        }  
        
        // Nothing found
        return null;
        
    }

    public void deleteElement(int x, int y) {
        Element element=getLocationElement(x, y);
        if (element==null)
        {
            return;
        }
        // Place
        if (element instanceof Place)
        {
            petriNet.deletePlace(((Place)element).getName());
        }
        // Place
        if (element instanceof Transition)
        {
            petriNet.deleteTransition(((Transition)element).getName());
        }
    }            

    public void setModel(PetriNet petriNet) {
        this.petriNet=petriNet;
    }

}
