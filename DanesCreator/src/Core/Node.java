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
public class Node extends ComplexElement{
    
    private ArrayList<Node> listOfInNodes;
    private ArrayList<Node> listOfOutNodes;
    private ArrayList<Arc> listOfInArcs;
    private ArrayList<Arc> listOfOutArcs;
    private int capacity;
    
    /**
     * @Class constructor 
     */
    public Node(String paName){
        super(paName);
        listOfInArcs=new ArrayList<Arc>();
        listOfOutArcs=new ArrayList<Arc>();
        listOfInNodes=new ArrayList<Node>();
        listOfOutNodes=new ArrayList<Node>();
        super.setWidth(70);
        super.setHeight(43);
    }

    /**
     * @return the listOfInNodes
     */
    public ArrayList<Node> getListOfInNodes() {
        return listOfInNodes;
    }

    /**
     * @return the listOfOutNodes
     */
    public ArrayList<Node> getListOfOutNodes() {
        return listOfOutNodes;
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
   
}
