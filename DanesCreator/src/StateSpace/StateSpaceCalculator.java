/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StateSpace;

import Core.PetriNet;
import Core.Transition;
import StateSpace.Trie.Trie;

/**
 *
 * @author marek
 */
public class StateSpaceCalculator {
    PetriNet petriNet;

    public StateSpaceCalculator(PetriNet petriNet) {
        this.petriNet = petriNet;
    }
    
    /* Calculate whole stateSpace */
    public void calculateStateSpace()
    {
        /* Initialization */
        PetriNet _net=petriNet;
        State _intialState=new State(_net.getState(), 0, null);
        State _currentState;
        Trie _stateSpace=new Trie();
        
        _currentState=new State(_net.getState(), 0, null);
        _stateSpace.insert(_currentState.toString(), _currentState);
        
        /* Calculation */        
        while(_currentState!=null)
        {    
            /* Add child states if transitions is activate */
            for(Transition t : _net.getListOfTransitions())
            {
                _net.setState(_currentState);
                if (t.isActive())
                {
                    /* Simulate execution of transition */
                    t.executeTransition();
                    /* If child state already exists in stateSpace , do nothing */
                    /* else add to parent's childs & add to stateSpace */
                    State _childState=new State(_net.getState(),0, _currentState);
                    if (_stateSpace.search(_childState.toString())==null)
                    {                                           
                        /* Unique state add to childs and stateSpaces */
                        _stateSpace.insert(_childState.toString(), _childState);
                        _currentState.addChild(new StateItem(_childState,t));                
                    }
                }
            }

            /* Can I execute any child? */
            if (_currentState.getLastMarkedItem()<_currentState.getChilds().size())
            {           
                State _newState=_currentState.getChilds().get(_currentState.getLastMarkedItem()).getState();            
                _currentState.increaseLastMarkedItem();
                _currentState=_newState;
            }
            /* No child, go to parent */
            else
            {
                _currentState=_currentState.getParent();
            }
        // End while (_currentState!=null)
        }
        
        /* Write results and revert back original net markings */
        _stateSpace.levelOrder();                        
        _net.setState(_intialState);
    }
    
}
