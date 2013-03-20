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
public class Node extends Element{
    
    private ArrayList<Node> listOfInNodes;
    private ArrayList<Node> listOfOutNodes;
    private ArrayList<Arc> listOfInArcs;
    private ArrayList<Arc> listOfOutArcs;
    private int width;
    private int height;
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
        this.width=50;
        this.height=35;
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
    
   /* Autosize */
   @Override
   public void setName(String name) {
        super.setName(name);
        this.width=-1;
        this.height=-1;
    }        
}
