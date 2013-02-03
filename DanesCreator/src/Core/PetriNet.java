/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

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
    private Logic log;

    /**
     * @Class constructor.
     */
    public PetriNet(String paName) {
        this.name = paName;
        this.listOfPlaces = new ArrayList<>();
        this.listOfArcs = new ArrayList<>();
        this.listOfTransitions = new ArrayList<>();
        log=new Logic();
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
        for (Place actPlace : listOfPlaces) {
            if (actPlace.getName().equals(paName)) {

                for (Arc actArc : actPlace.getListOfInArcs()) {
                    Transition temp = (Transition) actArc.getOutElement();
                    temp.getListOfOutArcs().remove(actArc);
                    temp.getListOfOutPlaces().remove(actPlace);
                    listOfArcs.remove(actArc);
                }

                for (Arc actArc : actPlace.getListOfOutArcs()) {
                    Transition temp = (Transition) actArc.getInElement();
                    temp.getListOfInArcs().remove(actArc);
                    temp.getListOfInPlaces().remove(actPlace);
                    listOfArcs.remove(actArc);
                }

                listOfPlaces.remove(actPlace);
                return true;
            }
        }
        return false;
    }

    /**
     * @Add a transition to the Petri net
     */
    public boolean addTransition(Transition paTransition) {
        for (Transition actPlace : listOfTransitions) {
            if (actPlace.getName().equals(paTransition.getName())) {
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

                for (Arc actArc : actTransition.getListOfInArcs()) {
                    Place temp = (Place) actArc.getOutElement();
                    temp.getListOfOutArcs().remove(actArc);
                    temp.getListOfOutTransitions().remove(actTransition);
                    listOfArcs.remove(actArc);
                }

                for (Arc actArc : actTransition.getListOfOutArcs()) {
                    Place temp = (Place) actArc.getInElement();
                    temp.getListOfInArcs().remove(actArc);
                    temp.getListOfInTransitions().remove(actTransition);
                    listOfArcs.remove(actArc);
                }

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
        if(!log.checkArc(paArc)) {
            return false;
        }
        
        
        for (Arc actArc : listOfArcs) {
            if (actArc.getName().equals(paArc.getName())) {
                return false;
            }
        }
        listOfArcs.add(paArc);

        if (paArc.getOutElement() instanceof Transition) {
            Transition tempTran = (Transition) paArc.getOutElement();
            Place tempPla = (Place) paArc.getInElement();

            tempTran.getListOfOutArcs().add(paArc);
            tempTran.getListOfOutPlaces().add(tempPla);

            tempPla.getListOfInArcs().add(paArc);
            tempPla.getListOfInTransitions().add(tempTran);
            return true;
        }

        if (paArc.getInElement() instanceof Transition) {
            Transition tempTran = (Transition) paArc.getInElement();
            Place tempPla = (Place) paArc.getOutElement();

            tempTran.getListOfInArcs().add(paArc);
            tempTran.getListOfInPlaces().add(tempPla);

            tempPla.getListOfOutArcs().add(paArc);
            tempPla.getListOfOutTransitions().add(tempTran);
        }

        return true;
    }

    /**
     * @Delete an arc from Petri Net
     */
    public boolean deleteArc(String paName) {
        for (Arc actArc : listOfArcs) {
            if (actArc.getName().equals(paName)) {
                if (actArc.getOutElement() instanceof Transition) {
                    Transition tempTr = (Transition) actArc.getOutElement();
                    Place tempPl = (Place) actArc.getInElement();

                    tempTr.getListOfOutArcs().remove(actArc);
                    tempTr.getListOfOutPlaces().remove(tempPl);

                    tempPl.getListOfInArcs().remove(actArc);
                    tempPl.getListOfInTransitions().remove(tempTr);
                }
                if (actArc.getInElement() instanceof Transition) {
                    Transition tempTr = (Transition) actArc.getInElement();
                    Place tempPl = (Place) actArc.getOutElement();

                    tempTr.getListOfInArcs().remove(actArc);
                    tempTr.getListOfInPlaces().remove(tempPl);

                    tempPl.getListOfOutArcs().remove(actArc);
                    tempPl.getListOfOutTransitions().remove(tempTr);
                }

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
    
    public boolean isLocationEmpty(int x,int y)
    {
        for (Element e:listOfPlaces)
        {
            if (e.getDiagramElement().getX()==x &&
                e.getDiagramElement().getY()==y  )
                return false;
        }
        for (Element e:listOfTransitions)
        {
            if (e.getDiagramElement().getX()==x &&
                e.getDiagramElement().getY()==y  )
                return false;
        }    
        
        return true;        
    }
}
