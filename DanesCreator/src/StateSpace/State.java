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
    private State parent;
    
    public State(int[] pMarkigField, int pLastMarkedItem, State pParent){
        int count = pMarkigField.length;
        this.markingField = new int[count];
        this.markingField=pMarkigField.clone();
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
        for (int i = 0; i < this.markingField.length; i++) {
            stateName.append(this.markingField[i]);
        }
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
 
    
    
}
