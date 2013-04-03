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
    private ArrayList<StateItem> listStateItems;
    private int lastMarkedItem;
    private State parent;
    
    public State(int[] pMarkigField, int pLastMarkedItem, State pParent){
        int count = pMarkigField.length;
        this.markingField = new int[count];
        this.lastMarkedItem = pLastMarkedItem;
        this.parent = pParent;
        this.listStateItems = new ArrayList<StateItem>();
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
    public ArrayList<StateItem> getListStateItems() {
        return listStateItems;
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
}
