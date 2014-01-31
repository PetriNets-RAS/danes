/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.awt.Color;
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
        super.setWidth(super.generalSettingsManager.getNodeWidth());
        super.setHeight(super.generalSettingsManager.getNodeHeight());
        super.color = new Color(super.generalSettingsManager.getNodeCol2());
        super.color2 = new Color(super.generalSettingsManager.getNodeCol1());
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
