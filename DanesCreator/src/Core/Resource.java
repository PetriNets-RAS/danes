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
public class Resource extends AbsPlace{
    
    private ArrayList<Integer> procesMarkings;
    
    public Resource(String paName){
        super(paName);
    }

    /**
     * @return the procesMarkings
     */
    public ArrayList<Integer> getProcesMarkings() {
        return procesMarkings;
    }

    /**
     * @param procesMarkings the procesMarkings to set
     */
    public void setProcesMarkings(ArrayList<Integer> procesMarkings) {
        this.procesMarkings = procesMarkings;
    }
    
}
