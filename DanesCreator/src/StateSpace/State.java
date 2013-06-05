/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StateSpace;

import Core.PetriNet;
import java.util.ArrayList;

/**
 *
 * @author Atarin
 */
public class State {
    
    private int[] markingField;
    private ArrayList<StateItem> childs;
    private int lastMarkedItem;
    private ArrayList<ArrayList<Integer>> placeMarkings;
    private State parent;
    
    /*
    public State(int[] pMarkigField, int pLastMarkedItem, State pParent){
        int count = pMarkigField.length;
        this.markingField = new int[count];
        this.placeMarkings = new ArrayList<int[]>();
        this.markingField=pMarkigField.clone();
        this.lastMarkedItem = pLastMarkedItem;
        this.parent = pParent;
        this.childs = new ArrayList<StateItem>();
    }
    */
    public State(ArrayList<ArrayList<Integer>> pPlaceMarkings, int pLastMarkedItem, State pParent){
        //int count = pMarkigField.length;
        //this.markingField = new int[count];
        this.placeMarkings = new ArrayList<ArrayList<Integer>>();
        this.placeMarkings = pPlaceMarkings;
        //this.markingField=pMarkigField.clone();
        this.lastMarkedItem = pLastMarkedItem;
        this.parent = pParent;
        this.childs = new ArrayList<StateItem>();

    }
    
    public int[] getMarkingField(){
        return markingField;
    }
    
     /**
     * @param markingField the markingField to set
     */
    public void setMarkingField(int[] markingField) {
        this.markingField = markingField;
    }
    
     /**
     * @return the listStateItems
     */
    public ArrayList<StateItem> getChilds() {
        return childs;
    }
    
    public void addChild(StateItem s)
    {
        this.childs.add(s);
    }
    
    @Override
    public String toString()
    {
        StringBuilder stateName = new StringBuilder();
        /*
        for (int i = 0; i < this.markingField.length; i++) {
            stateName.append(this.markingField[i]);
        }
        */
        stateName.append("{");
        for (int i = 0; i < this.placeMarkings.size()-1; i++) {
            stateName.append("P").append(i+1).append(":");
            for (int j = 0; j < this.placeMarkings.get(i).size(); j++) {
                if(j < this.placeMarkings.get(i).size() -1){
                    stateName.append("1'").append(this.placeMarkings.get(i).get(j)).append("++");
                }else{
                    stateName.append("1'").append(this.placeMarkings.get(i).get(j));
                }       
            }
            stateName.append(";");
        }
        int lastIndexOfArray = this.placeMarkings.size()-1; // resources
        for (int j = 0; j < this.placeMarkings.get(lastIndexOfArray).size(); j++) {
            stateName.append("R").append(j+1).append(":").append(this.placeMarkings.get(lastIndexOfArray).get(j)).append(";");       
        }
        stateName.append("}");
        return stateName.toString();
    }    

    public int getLastMarkedItem() {
        return lastMarkedItem;
    }

    public void increaseLastMarkedItem() {
        this.lastMarkedItem += 1;
    }

    public State getParent() {
        return parent;
    }

    /**
     * @return the placeMarkings
     */
    public ArrayList<ArrayList<Integer>> getPlaceMarkings() {
        return placeMarkings;
    }

    /**
     * @param placeMarkings the placeMarkings to set
     */
    public void setPlaceMarkings(ArrayList<ArrayList<Integer>> placeMarkings) {
        this.placeMarkings = placeMarkings;
    }
 
    
    
}
