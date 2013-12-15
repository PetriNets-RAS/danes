/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import GUI.MagneticLine;
import java.util.ArrayList;

/**
 *
 * @author marek
 */
public abstract class Graph {
    private ArrayList<MagneticLine> magneticLines;

    public ArrayList<MagneticLine> getMagneticLines() {
        return magneticLines;
    }

    public void setMagneticLines(ArrayList<MagneticLine> magneticLines) {
        this.magneticLines = magneticLines;
    }
    
    public Graph(){
        this.magneticLines = new ArrayList<MagneticLine>();
    }
}
