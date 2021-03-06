/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.util.ArrayList;

/**
 *
 * @author Atarin
 */
public abstract class AbsPlace extends ComplexElement{
    private int marking;
    private boolean start;
    private boolean end;
    //private int tokens;
    private ArrayList<Transition> listOfInTransitions;
    private ArrayList<Transition> listOfOutTransitions;
    private ArrayList<Arc> listOfInArcs;
    private ArrayList<Arc> listOfOutArcs;
    
    
    public AbsPlace(String paName){
        super(paName);
        listOfInArcs=new ArrayList<Arc>();
        listOfOutArcs=new ArrayList<Arc>();
        listOfInTransitions=new ArrayList<Transition>();
        listOfOutTransitions=new ArrayList<Transition>();
    }
    
    public void delete() {
        
        for(Arc actArc : getListOfInArcs()){
                Transition temp=(Transition) actArc.getOutElement();
                temp.getListOfOutArcs().remove(actArc);         
        }
        
        for(Arc actArc : getListOfOutArcs()){
                Transition temp=(Transition) actArc.getInElement();
                temp.getListOfInArcs().remove(actArc);           
        }
        
    }

    /**
     * @return the initialMarking
     */
    public int getMarking() {
        return marking;
    }

    /**
     * @param initialMarking the initialMarking to set
     */
    public void setMarking(int initialMarking) {
        this.marking = initialMarking;
    }

    /**
     * @return the listOfInTransitions
     */
    public ArrayList<Transition> getListOfInTransitions() {
        return listOfInTransitions;
    }

    /**
     * @param listOfInTransitions the listOfInTransitions to set
     */
    public void setListOfInTransitions(ArrayList<Transition> listOfInTransitions) {
        this.listOfInTransitions = listOfInTransitions;
    }

    /**
     * @return the listOfOutTransitions
     */
    public ArrayList<Transition> getListOfOutTransitions() {
        return listOfOutTransitions;
    }

    /**
     * @param listOfOutTransitions the listOfOutTransitions to set
     */
    public void setListOfOutTransitions(ArrayList<Transition> listOfOutTransitions) {
        this.listOfOutTransitions = listOfOutTransitions;
    }

    /**
     * @return the listOfInArcs
     */
    public ArrayList<Arc> getListOfInArcs() {
        return listOfInArcs;
    }

    /**
     * @param listOfInArcs the listOfInArcs to set
     */
    public void setListOfInArcs(ArrayList<Arc> listOfInArcs) {
        this.listOfInArcs = listOfInArcs;
    }

    /**
     * @return the listOfOutArcs
     */
    public ArrayList<Arc> getListOfOutArcs() {
        return listOfOutArcs;
    }

    /**
     * @param listOfOutArcs the listOfOutArcs to set
     */
    public void setListOfOutArcs(ArrayList<Arc> listOfOutArcs) {
        this.listOfOutArcs = listOfOutArcs;
    }
    
   /* Autosize */
   @Override
   public void setName(String name) {
        super.setName(name);
    }        

    /**
     * @return the start
     */
    public boolean isStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(boolean start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    public boolean isEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(boolean end) {
        this.end = end;
    }
}
