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
    public boolean checkArc(Arc paArc){
    
        Element p1=paArc.getOutElement();
        Element p2=paArc.getInElement();
        if( ((p1 instanceof Place)&&(p2 instanceof Transition)) | ((p2 instanceof Place)&&(p1 instanceof Transition)) ) {
            return true;
        }    
        return false;
    }
    
}
