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

//    private int capacity;
//    private int width;
//    private int height;
    private boolean start;
    private boolean end;
//    private int tokens;
//    private ArrayList<Transition> listOfInTransitions;
//    private ArrayList<Transition> listOfOutTransitions;
//    private ArrayList<Arc> listOfInArcs;
//    private ArrayList<Arc> listOfOutArcs;    
     /**
     * @Class constructor.
     */
    public Place(String paName){       
        super(paName);
//        listOfInArcs=new ArrayList<>();
//        listOfOutArcs=new ArrayList<>();
//        listOfInTransitions=new ArrayList<>();
//        listOfOutTransitions=new ArrayList<>();
//        this.width=50;
//        this.height=35;
        start=false;
        end=false;
//        tokens=0;
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
