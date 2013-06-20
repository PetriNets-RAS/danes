/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import Core.Element;
import java.util.ArrayList;


/**
 *
 * @author Michal Skovajsa
 */
public class Place extends AbsPlace {
    
    private boolean start;
    private boolean end; 
    private Marking markings;
     /**
     * @Class constructor.
     */
    public Place(String paName){       
        super(paName);
        start=false;
        end=false;
        this.markings = new Marking(this);
    }


    /**
     * @return the start
     */
    public boolean isStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(boolean start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    public boolean isEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(boolean end) {
        this.end = end;
    }
    
        
    /**
     * @return the markings
     */
    public Marking getMarkings() {
        return markings;
    }

    /**
     * @param markings the markings to set
     */
    public void setMarkings(Marking markings) {
        this.markings = markings;
    }
    
    /**
     * @param mark 
     */
    public void addMarking(int mark)
    {
        this.markings.getMarkings().add(mark);
    }
    
    /**
     * @param mark
     */
    public boolean removeMarking(int mark)
    {
        if (this.markings.getMarkings().isEmpty()) {
            return false;
        }
        int indexOfMark = this.markings.getMarkings().indexOf(mark);
        if (indexOfMark == -1) {
            return false;
        }
        else
        {
            this.markings.getMarkings().remove(indexOfMark);
        }
        return true;
    }
    
}
