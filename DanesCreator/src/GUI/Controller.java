/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Core.Arc;
import Core.Element;
import Core.PetriNet;
import Core.Place;
import Core.Transition;

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

    public void addArc(String name, int x1, int y1, int x2, int y2) 
    {
        Element out     =getLocationElement(x2, y2);
        Element in      =getLocationElement(x1, y1);
        
        if (in == null || out ==  null)
            return;
        
        Arc arc;


        if (name.equals("Arc"))
        {   
            // Generate text name until it is unique
            do
            {
                arc=new Arc("Arc"+Math.random(),out,in);
                //transition.setDiagramElement(new DiagramElement(x, y));                
            }
            while      
               (!this.petriNet.addArc(arc));
        }
        else // Normal add
        {
            arc=new Arc(name,out,in);
            //transition.setDiagramElement(new DiagramElement(x, y));             
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

    public void deleteArc(int x1,int y1,int x2,int y2)
    {
        Element out     =getLocationElement(x2, y2);
        Element in      =getLocationElement(x1, y1);
        
        if (out==null || in==null)
            return;
        for(Arc a : petriNet.getListOfArcs())
        {
            if (a.getInElement()==in && a.getOutElement()==out)
            {
                petriNet.deleteArc(a.getName());
                break;
            }
        }        
    }
    
    public void setModel(PetriNet petriNet) {
        this.petriNet=petriNet;
    }

}
