/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Atarin
 */
public class Resource extends AbsPlace {

    private ArrayList<Integer> procesMarkings;
    private int[] process;

    public Resource(String paName) {
        super(paName);
        super.color = new Color(super.generalSettingsManager.getResourceCol2());
        super.color2 = new Color(super.generalSettingsManager.getResourceCol1());
        procesMarkings = new ArrayList<Integer>();
        super.height = super.generalSettingsManager.getResourceHeight();
        super.width = super.generalSettingsManager.getResourceWidth();
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

    public void incrementProcess(int mark) {
        procesMarkings.add(mark);
    }

    public void decrementProcess(int mark) {
        Process p;
        for (int i = 0; i < procesMarkings.size(); i++) {
            if (procesMarkings.get(i) == mark) {
                procesMarkings.remove(i);
                break;
            }
        }
    }

    /**
     * @return the process
     */
    public int[] getProcess() {
        return process;
    }

    /**
     * @param process the process to set
     */
    public void setProcess(int[] process) {
        this.process = process;
    }
    



}
