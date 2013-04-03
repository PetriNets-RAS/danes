/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StateSpace;

import Core.Transition;

/**
 *
 * @author Miso
 */
public class StateItem {
    private State state;
    private Transition transition;
    
    public StateItem(State pState, Transition pTransition){
        this.state = pState;
        this.transition = pTransition;
    }

    /**
     * @return the state
     */
    public State getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(State state) {
        this.state = state;
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
    
    @Override
    public String toString()
    {
        return "State: "+ this.state +",Transition: "+ this.transition.getName(); 
    }
    
}