/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import AVL_Tree.StringKey;
import AVL_Tree.Tree;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Michal Skovajsa
 */
public class PetriNet extends Graph{

    //private ArrayList<Place> listOfPlaces;
    //private ArrayList<Transition> listOfTransitions;
    //private ArrayList<Arc> listOfArcs;
    private AVL_Tree.Tree treeOfPlaces;
    private AVL_Tree.Tree treeOfTransitions;
    private AVL_Tree.Tree treeOfArcs;
    private AVL_Tree.Tree treeOfResources;
    private String name;

    /**
     * @Class constructor.
     */
    public PetriNet(String paName) {
        this.name = paName;
        //this.listOfPlaces = new ArrayList<>();
        //this.listOfArcs = new ArrayList<>();
        //this.listOfTransitions = new ArrayList<>();

        this.treeOfPlaces = new Tree(null);
        this.treeOfArcs = new Tree(null);
        this.treeOfTransitions = new Tree(null);
        this.treeOfResources = new Tree(null);
    }

    /**
     * @Add a place to the Petri Net
     */
    public boolean addPlace(Place paPlace) {
//        for (Place actResource : listOfPlaces) {
//            if (actResource.getName().equals(paPlace.getName())) {
//                return false;
//            }
//        }
        //listOfPlaces.add(paPlace);
        //return true;
        boolean temp = treeOfPlaces.addNode(paPlace);
        return temp;
    }

    /**
     * @Delete a place from Petri Net
     */
    public boolean deletePlace(String paName) {
        //for (Place actResource : listOfPlaces) {
        //    if (actResource.getName().equals(paName)) {
        Place actPlace = (Place) treeOfPlaces.find(new StringKey(paName));
        //System.out.println("mazem: "+paName);
        if (actPlace == null) {
            //System.out.println("aaa");
            return false;
        }
        for (Arc actArc : actPlace.getListOfInArcs()) {
            Transition temp = (Transition) actArc.getOutElement();
            temp.getListOfOutArcs().remove(actArc);
            temp.getListOfOutPlaces().remove(actPlace);
            System.out.println("Mazem hranu " + actArc.getKey().getKey());
            treeOfArcs.delete(actArc.getKey());
        }

        for (Arc actArc : actPlace.getListOfOutArcs()) {
            Transition temp = (Transition) actArc.getInElement();
            temp.getListOfInArcs().remove(actArc);
            temp.getListOfInPlaces().remove(actPlace);
            System.out.println("Mazem hranu " + actArc.getKey().getKey());
            treeOfArcs.delete(actArc.getKey());
        }

        treeOfPlaces.delete(actPlace.getKey());
        return true;
        //    }
        //}
        //return false;
    }

    
    
    
        /**
     * @Add a place to the Petri Net
     */
    public boolean addResource(Resource paResource) {
//        for (Place actResource : listOfPlaces) {
//            if (actResource.getName().equals(paPlace.getName())) {
//                return false;
//            }
//        }
        //listOfPlaces.add(paPlace);
        //return true;
        boolean temp = treeOfResources.addNode(paResource);
        return temp;
    }

    /**
     * @Delete a place from Petri Net
     */
    public boolean deleteResource(String paName) {
        //for (Place actResource : listOfPlaces) {
        //    if (actResource.getName().equals(paName)) {
        Resource actResource = (Resource) treeOfResources.find(new StringKey(paName));
        //System.out.println("mazem: "+paName);
        if (actResource == null) {
            //System.out.println("aaa");
            return false;
        }
        for (Arc actArc : actResource.getListOfInArcs()) {
            Transition temp = (Transition) actArc.getOutElement();
            temp.getListOfOutArcs().remove(actArc);
            temp.getListOfOutPlaces().remove(actResource);
            System.out.println("Mazem hranu " + actArc.getKey().getKey());
            treeOfArcs.delete(actArc.getKey());
        }

        for (Arc actArc : actResource.getListOfOutArcs()) {
            Transition temp = (Transition) actArc.getInElement();
            temp.getListOfInArcs().remove(actArc);
            temp.getListOfInPlaces().remove(actResource);
            System.out.println("Mazem hranu " + actArc.getKey().getKey());
            treeOfArcs.delete(actArc.getKey());
        }

        treeOfPlaces.delete(actResource.getKey());
        return true;
        //    }
        //}
        //return false;
    }
    
    
    
    /**
     * @Add a transition to the Petri net
     */
    public boolean addTransition(Transition paTransition) {
//        for (Transition actResource : listOfTransitions) {
//            if (actResource.getName().equals(paTransition.getName())) {
//                return false;
//            }
//        }
//        listOfTransitions.add(paTransition);
//        return true;

        boolean temp = treeOfTransitions.addNode(paTransition);
        return temp;
    }

    /**
     * @Delete a transition from Petri Net
     */
    public boolean deleteTransition(String paName) {
        //for (Transition actTransition : listOfTransitions) {
        //    if (actTransition.getName().equals(paName)) {
        Transition actTransition = (Transition) treeOfTransitions.find(new StringKey(paName));
        if (actTransition == null) {
            return false;
        }
        for (Arc actArc : actTransition.getListOfInArcs()) {
            Place temp = (Place) actArc.getOutElement();
            temp.getListOfOutArcs().remove(actArc);
            temp.getListOfOutTransitions().remove(actTransition);
            treeOfArcs.delete(actArc.getKey());
        }

        for (Arc actArc : actTransition.getListOfOutArcs()) {
            Place temp = (Place) actArc.getInElement();
            temp.getListOfInArcs().remove(actArc);
            temp.getListOfInTransitions().remove(actTransition);
            treeOfArcs.delete(actArc.getKey());
        }

        treeOfTransitions.delete(actTransition.getKey());
        return true;
        //   }
        //}
        //return false;
    }

    /**
     * @Add an arc to the Petri net
     */
    public boolean addArc(Arc paArc) {

//        for (Arc actArc : listOfArcs) {
//            if (actArc.getName().equals(paArc.getName())) {
//                return false;
//            }
//        }
        boolean temp = treeOfArcs.addNode(paArc);
        if (temp == false) {
            return false;
        }
        if (paArc.getOutElement() instanceof Transition) {
            Transition tempTran = (Transition) paArc.getOutElement();
            AbsPlace tempPla = (AbsPlace) paArc.getInElement();

            
            tempTran.getListOfOutArcs().add(paArc);
            tempTran.getListOfOutPlaces().add(tempPla);

            tempPla.getListOfInArcs().add(paArc);
            tempPla.getListOfInTransitions().add(tempTran);
            return true;
        }else{
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
//        for (Arc actArc : listOfArcs) {
//            if (actArc.getName().equals(paName)) {
        Arc actArc = (Arc) treeOfArcs.find(new StringKey(paName));
        if (actArc == null) {
            //System.out.println("aaa");
            return false;
        }
        if (actArc.getOutElement() instanceof Transition) {
            Transition tempTr = (Transition) actArc.getOutElement();
            AbsPlace tempPl = (Place) actArc.getInElement();

            tempTr.getListOfOutArcs().remove(actArc);
            tempTr.getListOfOutPlaces().remove(tempPl);

            tempPl.getListOfInArcs().remove(actArc);
            tempPl.getListOfInTransitions().remove(tempTr);
        }else{
            Transition tempTr = (Transition) actArc.getInElement();
            AbsPlace tempPl = (Place) actArc.getOutElement();

            tempTr.getListOfInArcs().remove(actArc);
            tempTr.getListOfInPlaces().remove(tempPl);

            tempPl.getListOfOutArcs().remove(actArc);
            tempPl.getListOfOutTransitions().remove(tempTr);
        }

        treeOfArcs.delete(actArc.getKey());
        return true;
        //    }
        //}
        //return false;
    }

    /**
     * @return the listOfPlaces
     */
    public ArrayList<Place> getListOfPlaces() {

        ArrayList<Place> ret = new ArrayList<>();

        for (AVL_Tree.Node n : treeOfPlaces.inOrder()) {
            Place tempPlace = (Place) n;
            ret.add(tempPlace);
        }

        return ret;
    }
    
    
        public ArrayList<Resource> getListOfResources() {

        ArrayList<Resource> ret = new ArrayList<>();

        for (AVL_Tree.Node n : treeOfResources.inOrder()) {
            Resource tempPlace = (Resource) n;
            ret.add(tempPlace);
        }

        return ret;
    }

    /**
     * @return the listOfTransitions
     */
    public ArrayList<Transition> getListOfTransitions() {
        ArrayList<Transition> ret = new ArrayList<>();

        for (AVL_Tree.Node n : treeOfTransitions.inOrder()) {
            Transition tempTran = (Transition) n;
            ret.add(tempTran);
        }

        return ret;
    }

    /**
     * @return the listOfArcs
     */
    public ArrayList<Arc> getListOfArcs() {
        ArrayList<Arc> ret = new ArrayList<>();

        for (AVL_Tree.Node n : treeOfArcs.inOrder()) {
            Arc tempArc = (Arc) n;
            ret.add(tempArc);
        }

        return ret;
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


    /**
     * @return the listOfPlaces
     */
    public AVL_Tree.Tree getTreeOfPlaces() {
        return treeOfPlaces;
    }

    /**
     * @return the listOfTransitions
     */
    public AVL_Tree.Tree getTreeOfTransitions() {
        return treeOfTransitions;
    }

    /**
     * @return the listOfArcs
     */
    public AVL_Tree.Tree getTreeOfArcs() {
        return treeOfArcs;
    }

    public Arc getArc(String name) {
        return (Arc) treeOfArcs.find(new StringKey(name));
    }

    public Transition getTransition(String name) {
        return (Transition) treeOfTransitions.find(new StringKey(name));
    }

    public Place getPlace(String name) {
        return (Place) treeOfPlaces.find(new StringKey(name));
    }

    public void changeNameOfPlace(String oldName, String newName) {
        Place temp = (Place) treeOfPlaces.find(new StringKey(oldName));
        treeOfPlaces.delete(temp.getKey());
        temp.setName(newName);
        treeOfPlaces.addNode(temp);
    }
    
        public void changeNameOfResource(String oldName, String newName) {
        Resource temp = (Resource) treeOfResources.find(new StringKey(oldName));
        treeOfResources.delete(temp.getKey());
        temp.setName(newName);
        treeOfResources.addNode(temp);
    }

    public void changeNameOfTransition(String oldName, String newName) {
       Transition temp = (Transition) treeOfTransitions.find(new StringKey(oldName));
        treeOfTransitions.delete(temp.getKey());
        temp.setName(newName);
        treeOfTransitions.addNode(temp);
    }

    public void changeNameOfArc(String oldName, String newName) {
        Arc temp = (Arc) treeOfArcs.find(new StringKey(oldName));
        treeOfArcs.delete(temp.getKey());
        temp.setName(newName);
        treeOfArcs.addNode(temp);
    }
}
