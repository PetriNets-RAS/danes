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
        /* Inicializacia */
        Trie _znakovyStrom=new Trie();
        PetriNet _net=petriNet;
        State _stav;
        State intialState=new State(_net.getState(), 0, null);
        
        _stav=new State(_net.getState(), 0, null);
        _znakovyStrom.insert(_stav.toString(), _stav);
        
        /* Vypocet */        
        while(_stav!=null)
        {    
            /* Pridaj k sucasnemu stavu jeho childy, ak ma aktivovane prechody */
            for(Transition t : _net.getListOfTransitions())
            {
                /* Nejaky prechod sa da vykonat v stave parenta*/
                _net.setState(_stav);
                if (t.isActive())
                {
                    /* Zisti do akeho stavu sa mozem dostat */
                    t.executeTransition();
                    /* Ak je child stav, v ktorom som uz bol, doslo by k cyklu */
                    /* Pridaj do doprednej hviezdy a do stromu len take, ktore este v strome nie su */
                    State _childStav=new State(_net.getState(),0, _stav);
                    if (_znakovyStrom.search(_childStav.toString())==null)
                    {
                        //System.out.println("Vlozil som child stav: "+_childStav.toString());                                               
                        /* Unikatny stav pridaj k detom, a pridaj aj do stromu */
                        _znakovyStrom.insert(_childStav.toString(), _childStav);
                        _stav.addChild(new StateItem(_childStav,t));                
                    }
                }
            }

            /* Mozem vykonat nejaky child prechod? */
            if (_stav.getLastMarkedItem()<_stav.getChilds().size())
            {           
                State _novyStav=_stav.getChilds().get(_stav.getLastMarkedItem()).getState();            
                _stav.increaseLastMarkedItem();
                _stav=_novyStav;
            }
            /* Nemam kam ist, idem k parentovi */
            else
            {
                _stav=_stav.getParent();
            }
        // Koniec while (_stav!=null)
        }
        
        /* Vypis , vrat siet do povodneho znacenia*/
        _znakovyStrom.levelOrder();                        
        _net.setState(intialState);
    }
    
}
