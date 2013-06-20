/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StateSpace;

import Core.PetriNet;
import Core.Transition;
import StateSpace.Trie.Trie;
import java.util.ArrayList;

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
    public void calculateStateSpace() {
        /* Initialization */
        PetriNet _net = petriNet;
        //State _intialState=new State(_net.getState(), 0, null);
        Trie _stateSpace = new Trie();
        State _currentState;

        _currentState = new State(_net.getState(), 0, null);
        _stateSpace.insert(_currentState.toString(), _currentState);

        /* Calculation */
//        System.out.println("***********************************************");
//        System.out.println("***********************************************");
//        System.out.println(_currentState);
        while (_currentState != null) {
            /* Add child states if transitions is activate */
            for (Transition t : _net.getListOfTransitions()) {
                _net.setState(_currentState);
                ArrayList<Integer> candidates = t.isActive();
                if (candidates != null) // return array of markings
                {
                    /* Simulate execution of transition */
                    State temp = new State(_net.getState(), 0, null);
                    for (int i = 0; i < candidates.size(); i++) {

                        t.executeTransition(i);
                        _currentState=new State(_net.getState(), _currentState.getLastMarkedItem(), _currentState.getParent());
                        /* If child state already exists in stateSpace , do nothing */
                        /* else add to parent's childs & add to stateSpace */
                        State _childState = new State(_net.getState(), 0, _currentState);
                        /* Unique state add to childs and stateSpaces */
                        
                        if (_stateSpace.insert(_childState.toString(), _childState)) {
                            _currentState.addChild(new StateItem(_childState, t));                           
                            _net.setState(temp);
                        }

                    }
                }
            }

            /* Can I execute any child? */
            if (_currentState.getLastMarkedItem() < _currentState.getChilds().size()) {
                //System.out.println("dalsi");
                State _newState = _currentState.getChilds().get(_currentState.getLastMarkedItem()).getState();
                _currentState.increaseLastMarkedItem();
                _currentState = _newState;
                //_currentState = new State(_newState.getPlaceMarkings(), 0, null);
            } /* No child, go to parent */ else {
                //System.out.println("otec");
                _currentState = _currentState.getParent();
            }
            // End while (_currentState!=null)
        }
//        System.out.println("***********************************************");
//        System.out.println("***********************************************");
        /* Write results and revert back original net markings */
        _stateSpace.levelOrder();
        //_net.setState(_intialState);
    }
}
