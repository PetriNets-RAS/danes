/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.util.ArrayList;

/**
 *
 * @author Michal Skovajsa
 */
public class Transition extends Element {

    private int width;
    private int height;
    private ArrayList<Place> listOfInPlaces;
    private ArrayList<Place> listOfOutPlaces;
    private ArrayList<Arc> listOfInArcs;
    private ArrayList<Arc> listOfOutArcs;

    /**
     * @Class constructor.
     */
    public Transition(String paNazov) {
        super(paNazov);
        listOfInArcs=new ArrayList<>();
        listOfOutArcs=new ArrayList<>();
        listOfInPlaces=new ArrayList<>();
        listOfOutPlaces=new ArrayList<>();
        width = 45;
        height = 25;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the listOfInPlaces
     */
    public ArrayList<Place> getListOfInPlaces() {
        return listOfInPlaces;
    }

    /**
     * @return the listOfOutPlaces
     */
    public ArrayList<Place> getListOfOutPlaces() {
        return listOfOutPlaces;
    }

    /**
     * @return the listOfInArcs
     */
    public ArrayList<Arc> getListOfInArcs() {
        return listOfInArcs;
    }

    /**
     * @return the listOfOutArcs
     */
    public ArrayList<Arc> getListOfOutArcs() {
        return listOfOutArcs;
    }

    public void delete() {

        for (Arc actArc : listOfInArcs) {
            Place temp = (Place) actArc.getOutElement();
            temp.getListOfOutArcs().remove(actArc);
        }

        for (Arc actArc : listOfOutArcs) {
            Place temp = (Place) actArc.getInElement();
            temp.getListOfInArcs().remove(actArc);
        }

    }
}
