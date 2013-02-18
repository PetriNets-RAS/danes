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
public abstract class AbsPlace extends Element{
    
    private int quantity;
    private int width;
    private int height;
    private int tokens;
    private ArrayList<Transition> listOfInTransitions;
    private ArrayList<Transition> listOfOutTransitions;
    private ArrayList<Arc> listOfInArcs;
    private ArrayList<Arc> listOfOutArcs;
    
    public AbsPlace(String paName){
        super(paName);
        listOfInArcs=new ArrayList<>();
        listOfOutArcs=new ArrayList<>();
        listOfInTransitions=new ArrayList<>();
        listOfOutTransitions=new ArrayList<>();
        this.width=50;
        this.height=35;
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
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int capacity) {
        this.quantity = capacity;
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
     * @return the tokens
     */
    public int getTokens() {
        return tokens;
    }

    /**
     * @param tokens the tokens to set
     */
    public void setTokens(int tokens) {
        this.tokens = tokens;
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
    
    
}
