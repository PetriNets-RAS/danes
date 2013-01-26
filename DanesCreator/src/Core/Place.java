/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import Core.Element;
import java.util.ArrayList;


/**
 *
 * @author Michal Skovajsa
 */
public class Place extends Element{

    private int capacity;
    private int width;
    private int height;
    private ArrayList<Transition> listOfInTransitions;
    private ArrayList<Transition> listOfOutTransitions;
    private ArrayList<Arc> listOfInArcs;
    private ArrayList<Arc> listOfOutArcs;    
     /**
     * @Class constructor.
     */
    public Place(String paName){       
        super(paName);
        listOfInArcs=new ArrayList<>();
        listOfOutArcs=new ArrayList<>();
        listOfInTransitions=new ArrayList<>();
        listOfOutTransitions=new ArrayList<>();
        this.width=50;
        this.height=35;
    }

    /**
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @param capacity the capacity to set
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the listOfInTransitions
     */
    public ArrayList<Transition> getListOfInTransitions() {
        return listOfInTransitions;
    }

    /**
     * @return the listOfOutTransitions
     */
    public ArrayList<Transition> getListOfOutTransitions() {
        return listOfOutTransitions;
    }

    /**
     * @return the listOfInArcs
     */
    public ArrayList<Arc> getListOfInArcs() {
        return listOfInArcs;
    }

    /**
     * @return the listOfOutArcs
     */
    public ArrayList<Arc> getListOfOutArcs() {
        return listOfOutArcs;
    }
    
    /**
     * @return the listOfOutArcs
     */
    public void delete() {
        
        for(Arc actArc : listOfInArcs){
                Transition temp=(Transition) actArc.getOutElement();
                temp.getListOfOutArcs().remove(actArc);         
        }
        
        for(Arc actArc : listOfOutArcs){
                Transition temp=(Transition) actArc.getInElement();
                temp.getListOfInArcs().remove(actArc);           
        }
        
    }
    
}
