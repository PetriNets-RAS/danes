/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RASChecker;

import Core.Place;
import Core.Transition;

/**
 *
 * @author Michal
 */
public class OwnCyclePair {
    
    private Place place;
    private Transition transition;
    
    public OwnCyclePair(Place p, Transition t){
        this.place = p;
        this.transition = t;
    }

    /**
     * @return the place
     */
    public Place getPlace() {
        return place;
    }

    /**
     * @param place the place to set
     */
    public void setPlace(Place place) {
        this.place = place;
    }

    /**
     * @return the transition
     */
    public Transition getTransition() {
        return transition;
    }

    /**
     * @param transition the transition to set
     */
    public void setTransition(Transition transition) {
        this.transition = transition;
    }
    
    
}
