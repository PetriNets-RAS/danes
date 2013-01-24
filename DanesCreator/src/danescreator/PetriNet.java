/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package danescreator;

import java.util.ArrayList;

/**
 *
 * @author Michal Skovajsa
 */
public class PetriNet {

    private ArrayList<Place> listOfPlaces;
    private ArrayList<Transition> listOfTransitions;
    private ArrayList<Arc> listOfArcs;
    private String name;

     /**
     * @Class constructor.
     */
    public PetriNet(String paName) {
        this.name = paName;
        this.listOfPlaces = new ArrayList<>();
        this.listOfArcs = new ArrayList<>();
        this.listOfTransitions = new ArrayList<>();
    }

    /**
     * @Add a place to the Petri Net
     */
    public boolean addPlace(Place paPlace) {
        for (Place actPlace : listOfPlaces) {
            if (actPlace.getName().equals(paPlace.getName())) {
                return false;
            }
        }
        listOfPlaces.add(paPlace);
        return true;
    }

    /**
     * @Delete a place from Petri Net
     */
    public boolean deletePlace(String paName) {
        for (Place prechMiesto : listOfPlaces) {
            if (prechMiesto.getName().equals(paName)) {
                listOfPlaces.remove(prechMiesto);
                return true;
            }
        }
        return false;
    }

    /**
     * @Add a transition to the Petri net
     */
    public boolean addTransition(Transition paTransition) {
        for (Transition prechPrechod : listOfTransitions) {
            if (prechPrechod.getName().equals(paTransition.getName())) {
                return false;
            }
        }
        listOfTransitions.add(paTransition);
        return true;
    }

    /**
     * @Delete a transition from Petri Net
     */
    public boolean deleteTransition(String paName) {
        for (Transition actTransition : listOfTransitions) {
            if (actTransition.getName().equals(paName)) {
                listOfTransitions.remove(actTransition);
                return true;
            }
        }
        return false;
    }

        
     /**
     * @Add an arc to the Petri net
     */
    public boolean addArc(Arc paArc) {
        for (Arc prechHrana : getListOfArcs()) {
            if (prechHrana.getName().equals(paArc.getName())) {
                return false;
            }
        }
        listOfArcs.add(paArc);
        return true;
    }
    
     /**
     * @Delete an arc from Petri Net
     */
    public boolean deleteArc(String paName) {
        for (Arc actArc : listOfArcs) {
            if (actArc.getName().equals(paName)) {
                listOfArcs.remove(actArc);
                return true;
            }
        }
        return false;
    }

    /**
     * @return the listOfPlaces
     */
    public ArrayList<Place> getListOfPlaces() {
        return listOfPlaces;
    }

    /**
     * @return the listOfTransitions
     */
    public ArrayList<Transition> getListOfTransitions() {
        return listOfTransitions;
    }

    /**
     * @return the listOfArcs
     */
    public ArrayList<Arc> getListOfArcs() {
        return listOfArcs;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
