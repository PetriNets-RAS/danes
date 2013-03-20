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
public class Place extends AbsPlace{


    private boolean start;
    private boolean end;  
     /**
     * @Class constructor.
     */
    public Place(String paName){       
        super(paName);
        start=false;
        end=false;
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
}
