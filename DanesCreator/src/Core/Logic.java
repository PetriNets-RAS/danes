/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import Core.Transition;
import Core.Place;
import Core.Element;
import Core.Arc;

/**
 *
 * @author Michal Skovajsa
 */
public class Logic {
        
     /**
     * @Class constructor.
     */
    public Logic(){
        
    }
    
    /**
     * @method, that checks arc validity
     */
    public boolean checkArc(Arc paArc,Graph graph){
    
        Element p1=paArc.getOutElement();
        Element p2=paArc.getInElement();
        // Petri Net - Place / Transition
        if (graph instanceof PetriNet)
        if( ((p1 instanceof Place)&&(p2 instanceof Transition)) | ((p2 instanceof Place)&&(p1 instanceof Transition)) ) 
        {
            return true;
        }
        // Precedence Graph - Node / Node
        if (graph instanceof PrecedenceGraph)
        if(((p1 instanceof Node)&&(p2 instanceof Node))&& !p1.equals(p2))
        {
            return true;            
        }    
        return false;
    }
    
}
