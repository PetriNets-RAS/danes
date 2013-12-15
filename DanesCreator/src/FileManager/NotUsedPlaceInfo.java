/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FileManager;

import Core.AbsPlace;
import Core.Place;

/**
 *
 * @author Michal
 */
public class NotUsedPlaceInfo {

    private String type;
    private String initmarking;
    private int ID;
    private AbsPlace place;

    public NotUsedPlaceInfo(int paID, String patype, String paMarking, AbsPlace paP) {
        this.ID = paID;
        this.type = patype;
        this.initmarking = paMarking;
        this.place = paP;
    }

    public NotUsedPlaceInfo(int paID,  AbsPlace paP) {
        this.ID = paID;
        this.type = null;
        this.initmarking = null;
        this.place = paP;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the initmarking
     */
    public String getInitmarking() {
        return initmarking;
    }

    /**
     * @param initmarking the initmarking to set
     */
    public void setInitmarking(String initmarking) {
        this.initmarking = initmarking;
    }

    /**
     * @return the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * @return the place
     */
    public AbsPlace getPlace() {
        return place;
    }

    /**
     * @param place the place to set
     */
    public void setPlace(Place place) {
        this.place = place;
    }
}
