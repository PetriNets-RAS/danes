/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StateSpace;

import Core.PetriNet;
import Core.Transition;
import java.util.ArrayList;

/**
 *
 * @author marek
 */
public class StateSpaceCalculator {

    PetriNet petriNet;
    private int idCount;
    private ArrayList<State> result;

    public StateSpaceCalculator(PetriNet petriNet) {
        this.petriNet = petriNet;
        idCount=1;
        result=new ArrayList<State>();
    }

    /* Calculate whole stateSpace */
    public void calculateStateSpace() {
        /* Initialization */
        PetriNet _net = petriNet;
        //State _intialState=new State(_net.getState(), 0, null);
        Trie _stateSpace = new Trie();
        State _currentState;
        State firstState;

        firstState = new State(_net.getState(), 0, null, _net);
        _currentState = new State(_net.getState(), 0, null, _net);
        _stateSpace.insert(_currentState.getKey(), _currentState);

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
                    State temp = new State(_net.getState(), 0, _currentState.getParent(), _net);
                    //System.out.println("TRAN "+t.getName());
                    
                    for (int i = 0; i < candidates.size(); i++) {
                        //System.out.println("CAND NUMB " + candidates.get(i));
                        t.executeTransition(i);
                        //System.out.println("CURR NUMB " + _currentState);
                        //_currentState = new State(temp.getPlaceMarkings(), temp.getLastMarkedItem(), temp.getParent(), _net);
                        //System.out.println("ACT "+_net.getState());
                        
                        //System.out.println("TEMP "+temp);
                        /* If child state already exists in stateSpace , do nothing */
                        /* else add to parent's childs & add to stateSpace */
                        State _childState = new State(_net.getState(), 0, _currentState, _net);
                        /* Unique state add to childs and stateSpaces */

                        if (_stateSpace.insert(_childState.getKey(), _childState)) {
                            _currentState.addChild(new StateItem(_childState, t));
                            //_stateSpace.levelOrder();
                            
                            //System.out.println("VKLADAM: " + _childState);
                            _net.setState(_currentState);
                        }
                        _net.setState(_currentState);
                        //System.out.println("STAV "+temp);
                    }
                }
            }

            /* Can I execute any child? */
            if (_currentState.getLastMarkedItem() < _currentState.getChilds().size()) {
                //System.out.println("dalsi " + _currentState.getLastMarkedItem());
                State _newState = _currentState.getChilds().get(_currentState.getLastMarkedItem()).getState();
                _currentState.increaseLastMarkedItem();
                _currentState = _newState;
                //System.out.println("CRUR po pridani "+_currentState);
                //_currentState = new State(_newState.getPlaceMarkings(), 0, null);
            } /* No child, go to parent */ else {
                //System.out.println("otec");
                _currentState = _currentState.getParent();
                //System.out.println("NAVRAT HORE");
            }
            // End while (_currentState!=null)
        }
//        System.out.println("***********************************************");
//        System.out.println("***********************************************");
        /* Write results and revert back original net markings */
        result=_stateSpace.levelOrder();
        _net.setState(firstState);
        
        //_net.setState(_intialState);
    }

    /**
     * @return the result
     */
    public ArrayList<State> getResult() {
        return result;
    }
}
