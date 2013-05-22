/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import java.awt.Graphics2D;
import java.util.ArrayList;
import org.omg.CORBA.INTERNAL;

/**
 *
 * @author Michal Skovajsa
 */
public class Transition extends Element {

    private int width=-1;
    private int height=-1;
    private ArrayList<AbsPlace> listOfInPlaces;
    private ArrayList<AbsPlace> listOfOutPlaces;
    private ArrayList<Arc> listOfInArcs;
    private ArrayList<Arc> listOfOutArcs;
    private Marking transmitCondition;
    /**
     * @Class constructor.
     */
    public Transition(String paNazov) {
        super(paNazov);
        listOfInArcs=new ArrayList<Arc>();
        listOfOutArcs=new ArrayList<Arc>();
        listOfInPlaces=new ArrayList<AbsPlace>();
        listOfOutPlaces=new ArrayList<AbsPlace>();
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
    public ArrayList<AbsPlace> getListOfInPlaces() {
        return listOfInPlaces;
    }

    /**
     * @return the listOfOutPlaces
     */
    public ArrayList<AbsPlace> getListOfOutPlaces() {
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
       
   /* Autosize */
   @Override
   public void setName(String name) {
        super.setName(name);
        this.width=-1;
        this.height=-1;   
    }    
   
   /* Check if transition is active */
   public boolean isActive()
   {
       int _return=-1;
       int min = Integer.MAX_VALUE;
       Place minPlace = null;
       // find place with minimum marking count
       for (AbsPlace absPlace : listOfInPlaces) {
           if (absPlace instanceof Place) {  // only if it`s Place 
                Place place = (Place) absPlace;
                if (place.getMarkings().getMarkings().size() < min) {
                    min = place.getMarkings().getMarkings().size();
                    minPlace = place;
                }
           }
       }
       // check if every inPlace have the same mark
       for (int i = 0; i < minPlace.getMarkings().getMarkings().size(); i++) {
           for (AbsPlace absPlace : listOfInPlaces) {
               if (absPlace != minPlace && absPlace instanceof Place) {
                   Place place = (Place) absPlace;
                   for (int j = 0; j < place.getMarkings().getMarkings().size(); j++) {
                       if (minPlace.getMarkings().getMarkings().get(i) == place.getMarkings().getMarkings().get(j)) {
                           _return = minPlace.getMarkings().getMarkings().get(i);
                           break;
                       }  
                   }
                   if (_return == -1) {
                       return false;
                   }
               }
               else if (absPlace instanceof Resource){ // if it`s resources, check capacity of outArc and Resource marking 
                   for (Arc arcToTransition : listOfInArcs) {
                       for (Arc arcFromAbsPlace : absPlace.getListOfOutArcs()) {
                           if (arcFromAbsPlace.equals(arcToTransition)) {
                               if (arcToTransition.getCapacity() > absPlace.getMarking()) {
                                   return false;
                               }
                           }
                       }
                   }
               }
           }
       }     
       return true;
   }
   
   public boolean executeTransition()
   {
       boolean marking = isActive();
       if (!marking) {
           return false;
       }
       
       /* Minus for input arcs */
       for(Arc arc : listOfInArcs)
       {
           /*
           AbsPlace outElement=(AbsPlace)arc.getOutElement();
           int outMarkingOld=outElement.getMarking();
           outElement.setMarking(outMarkingOld-arc.getCapacity());
           */
           AbsPlace outElement = (AbsPlace)arc.getOutElement();
           //outElement.removeMarking(marking);
       }
       /* Plus for output arcs */
       for(Arc arc : listOfOutArcs)
       {
           /*
           AbsPlace inElement=(AbsPlace)arc.getInElement();
           int inMarkingOld=inElement.getMarking();
           inElement.setMarking(inMarkingOld+arc.getCapacity()); 
           */
           AbsPlace inElement=(AbsPlace)arc.getInElement();
           //inElement.getMarkings().getMarkings().add(marking);
       }
       return true;
       
   }
}
