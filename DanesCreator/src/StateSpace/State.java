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
    
    private int[] stateMarkingArr;
    private ArrayList<StateItem> listStateItems;
    public State(PetriNet pPetriNet){
        int count = pPetriNet.getListOfPlaces().size()+pPetriNet.getListOfResources().size();
        this.stateMarkingArr = new int[count];
        this.listStateItems = new ArrayList<StateItem>();
    }
    
    public int[] getMarkingField(){
        return null;
    }
    
    @Override
    public String toString()
    {
        StringBuilder stateName = new StringBuilder();
        for (int i = 0; i < this.stateMarkingArr.length; i++) {
            stateName.append(this.stateMarkingArr[0]);
        }
        return stateName.toString();
    }
    
}
