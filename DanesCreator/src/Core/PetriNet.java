/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import StateSpace.State;
import java.util.ArrayList;

/**
 *
 * @author Michal Skovajsa
 */
public class PetriNet extends Graph implements Cloneable{

    private ArrayList<Place> listOfPlaces;
    private ArrayList<Transition> listOfTransitions;
    private ArrayList<Arc> listOfArcs;
    private ArrayList<Resource> listOfResources;    
    private String name;

    /**
     * @Class constructor.
     */
    public PetriNet(String paName) {
        this.name = paName;
        this.listOfPlaces = new ArrayList<Place>();
        this.listOfArcs = new ArrayList<Arc>();
        this.listOfTransitions = new ArrayList<Transition>();
        this.listOfResources = new ArrayList<Resource>();
    }
    /*
    public PetriNet(PetriNet net)
    {                 
            this(new String(net.getName()));
            listOfPlaces.addAll(net.getListOfPlaces());
            listOfArcs.addAll(net.getListOfArcs());
            listOfTransitions.addAll(net.getListOfTransitions());
            listOfResources.addAll(net.getListOfResources());
            /*
            this.listOfPlaces = new ArrayList<Place>(net.getListOfPlaces());
            this.listOfArcs = new ArrayList<Arc>(net.getListOfArcs());
            this.listOfTransitions = new ArrayList<Transition>(net.getListOfTransitions());
            this.listOfResources = new ArrayList<Resource>(net.getListOfResources());            
            
            Collections.copy(listOfPlaces,net.listOfPlaces);
            Collections.copy(listOfArcs,net.listOfArcs);
            Collections.copy(listOfTransitions,net.listOfTransitions);
            Collections.copy(listOfResources,net.listOfResources);
            */
/*            this.listOfPlaces = new ArrayList<Place>(
            this.listOfArcs = new ArrayList<Arc>(net.getListOfArcs());
            this.listOfTransitions = new ArrayList<Transition>(net.getListOfTransitions());
            this.listOfResources = new ArrayList<Resource>(net.getListOfResources());            

    }/*
    /**
     * @Add a place to the Petri Net
     */
    public boolean addPlace(Place paPlace) {
        for (Place actResource : listOfPlaces) {
            if (actResource.getName().equals(paPlace.getName())) {
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
     * @Add a place to the Petri Net
     */
    public boolean addResource(Resource paResource) {
        for (Resource actResource : listOfResources) {
            if (actResource.getName().equals(paResource.getName())) {
                return false;
            }
        }
        listOfResources.add(paResource);
        return true;
    }

    /**
     * @Delete a place from Petri Net
     */
    public boolean deleteResource(String paName) {
        for (Resource actResource : listOfResources) {
            if (actResource.getName().equals(paName)) {
                for (Arc actArc : actResource.getListOfInArcs()) {
                    Transition temp = (Transition) actArc.getOutElement();
                    temp.getListOfOutArcs().remove(actArc);
                    temp.getListOfOutPlaces().remove(actResource);
                    listOfArcs.remove(actArc);
                }

                for (Arc actArc : actResource.getListOfOutArcs()) {
                    Transition temp = (Transition) actArc.getInElement();
                    temp.getListOfInArcs().remove(actArc);
                    temp.getListOfInPlaces().remove(actResource);
                    listOfArcs.remove(actArc);
                }
                listOfResources.remove(actResource);
                return true;
            }
        }
        return false;
    }

    /**
     * @Add a transition to the Petri net
     */
    public boolean addTransition(Transition paTransition) {
        for (Transition actResource : listOfTransitions) {
            if (actResource.getName().equals(paTransition.getName())) {
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
                    AbsPlace temp = (AbsPlace) actArc.getOutElement();
                    temp.getListOfOutArcs().remove(actArc);
                    temp.getListOfOutTransitions().remove(actTransition);
                    listOfArcs.remove(actArc);
                }

                for (Arc actArc : actTransition.getListOfOutArcs()) {
                    AbsPlace temp = (AbsPlace) actArc.getInElement();
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

        for (Arc actArc : listOfArcs) {
            if (actArc.getName().equals(paArc.getName())) {
                return false;
            }
        }
        listOfArcs.add(paArc);
        if (paArc.getOutElement() instanceof Transition) {
            Transition tempTran = (Transition) paArc.getOutElement();
            AbsPlace tempPla = (AbsPlace) paArc.getInElement();

            tempTran.getListOfOutArcs().add(paArc);
            tempTran.getListOfOutPlaces().add(tempPla);

            tempPla.getListOfInArcs().add(paArc);
            tempPla.getListOfInTransitions().add(tempTran);
            return true;
        } else {
            Transition tempTran = (Transition) paArc.getInElement();
            AbsPlace tempPla = (AbsPlace) paArc.getOutElement();

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
                    AbsPlace tempPl = (AbsPlace) actArc.getInElement();

                    tempTr.getListOfOutArcs().remove(actArc);
                    tempTr.getListOfOutPlaces().remove(tempPl);

                    tempPl.getListOfInArcs().remove(actArc);
                    tempPl.getListOfInTransitions().remove(tempTr);
                } else {
                    Transition tempTr = (Transition) actArc.getInElement();
                    AbsPlace tempPl = (AbsPlace) actArc.getOutElement();

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

    public ArrayList<Resource> getListOfResources() {
        return listOfResources;
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

    public Transition getTransition(int x, int y) {
        for (Transition t : listOfTransitions) {
            //if ((t.getDiagramElement().getX() == x) && (t.getDiagramElement().getY() == y)) {
            if ((t.getX() == x) && (t.getY() == y)) {
                return t;
            }
        }
        return null;
    }

    public Resource getResource(int x, int y) {
        for (Resource r : listOfResources) {
            //if ((r.getDiagramElement().getX() == x) && (r.getDiagramElement().getY() == y)) {
            if ((r.getX() == x) && (r.getY() == y)) {                
                return r;
            }
        }
        return null;
    }

    public Place getPlace(int x, int y) {
        for (Place p : listOfPlaces) {
            //if ((p.getDiagramElement().getX() == x) && (p.getDiagramElement().getY() == y)) {
            if ((p.getX() == x) && (p.getY() == y)) {
                return p;
            }
        }
        return null;
    }

    /**
     * @param listOfPlaces the listOfPlaces to set
     */
    public void setListOfPlaces(ArrayList<Place> listOfPlaces) {
        this.listOfPlaces = listOfPlaces;
    }

    /**
     * @param listOfTransitions the listOfTransitions to set
     */
    public void setListOfTransitions(ArrayList<Transition> listOfTransitions) {
        this.listOfTransitions = listOfTransitions;
    }

    /**
     * @param listOfArcs the listOfArcs to set
     */
    public void setListOfArcs(ArrayList<Arc> listOfArcs) {
        this.listOfArcs = listOfArcs;
    }

    /**
     * @param listOfResources the listOfResources to set
     */
    public void setListOfResources(ArrayList<Resource> listOfResources) {
        this.listOfResources = listOfResources;
    }
    
    public ArrayList<ArrayList<Integer>> getState(){
        ArrayList<ArrayList<Integer>> stateMarkings = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> resourceVector = new ArrayList<Integer>();
        for(int i=0;i<listOfPlaces.size();i++)
        {
            ArrayList<Integer> vector = new ArrayList<Integer>();
            for (int j = 0; j < listOfPlaces.get(i).getMarkings().getMarkings().size(); j++) {
                vector.add(new Integer(listOfPlaces.get(i).getMarkings().getMarkings().get(j)));
            }
            stateMarkings.add(vector);
        }
        for(int i=0;i<listOfResources.size();i++){
            resourceVector.add(new Integer(listOfResources.get(i).getMarking()));
        }
        stateMarkings.add(resourceVector);
        return stateMarkings;
    }
    
    public void setState(State state){
        for (int i = 0; i < (listOfPlaces.size()); i++) {
            listOfPlaces.get(i).getMarkings().setMarkings(state.getPlaceMarkings().get(i));
        }
        
        int lastIndex = (state.getPlaceMarkings().size()-1);
        for (int i = 0; i < listOfResources.size(); i++) {
            listOfResources.get(i).setMarking(state.getPlaceMarkings().get(lastIndex).get(i));
        }
    }
       
}
