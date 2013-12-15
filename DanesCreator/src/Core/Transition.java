/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 *
 * @author Michal Skovajsa
 */
public class Transition extends ComplexElement {


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
    }    
   
   /* Check if transition is active */
   public ArrayList<Integer> isActive()
   {
       int _return=-1;
       int min = Integer.MAX_VALUE;
      // Place minPlace = null;
       ArrayList<Place> listOfPlaces = new ArrayList<Place>();
       ArrayList<Resource> listOfResources = new ArrayList<Resource>();
       for (AbsPlace absPlace : this.getListOfInPlaces()) {
           if (absPlace instanceof Place) {
               listOfPlaces.add((Place)absPlace);
           }
           else {
               listOfResources.add((Resource)absPlace);
           }
       }
       for (int i = 0; i < listOfResources.size(); i++) {
           for (int j = 0; j < listOfResources.get(i).getListOfOutTransitions().size(); j++) {
               if (listOfResources.get(i).getListOfOutTransitions().get(j).equals(this)) {
                   if (listOfResources.get(i).getMarking() < listOfResources.get(i).getListOfOutArcs().get(j).getCapacity()) {
                       _return = -1;
                       return null;
                   }
               }
           }
       }
       //ArrayList<Integer> iterimResult = (ArrayList)listOfPlaces.get(0).getMarkings().getMarkings();
       HashSet<Integer> result = new HashSet<Integer>(listOfPlaces.get(0).getMarkings().getMarkings());
       HashSet<Integer> iterimResult = new HashSet<Integer>(); 
       int iterator = 1;
       while(iterator < listOfPlaces.size()){
           for (int i = 0; i < result.size(); i++) {
               for (int j = 0; j < listOfPlaces.get(iterator).getMarkings().getMarkings().size(); j++) {
                   if (result.contains(listOfPlaces.get(iterator).getMarkings().getMarkings().get(j))) {
                       iterimResult.add(listOfPlaces.get(iterator).getMarkings().getMarkings().get(j));
                   }
               }
           }
           result.clear();
           result.addAll(iterimResult);
           iterimResult.clear();
           iterator++;
       }
       if (result.size()>0) {
           ArrayList<Integer> res = new ArrayList<Integer>();
           for (int value : result) {
               res.add(value);
           }
           return res;
       }else{
            return null;
       }
   }
   
   public boolean executeTransition(int k)
   {
       ArrayList<Integer> markings = isActive();
       if (markings == null) {
           return false;
       }
       int marking = markings.get(k); //token
       /* Minus for input arcs */
       for(Arc arc : listOfInArcs)
       {    
           AbsPlace outElement=(AbsPlace)arc.getOutElement();
           if (outElement instanceof Resource) {
               int outMarkingOld = outElement.getMarking();
               outElement.setMarking(outMarkingOld - arc.getCapacity());
               Resource r=(Resource)outElement;
               r.incrementProcess(marking);        
           }else
           {
               Place outPlace = (Place)outElement;
               outPlace.removeMarking(marking);
           }    
       }
       /* Plus for output arcs */
       for(Arc arc : listOfOutArcs)
       {
           AbsPlace inElement=(AbsPlace)arc.getInElement();
           if (inElement instanceof Resource) {
               int inMarkingOld=inElement.getMarking();
               inElement.setMarking(inMarkingOld+arc.getCapacity());  
               Resource r=(Resource)inElement;
               r.decrementProcess(marking);

           }else{
               Place inPlace = (Place)inElement;
               inPlace.getMarkings().getMarkings().add(marking);
               Collections.sort(inPlace.getMarkings().getMarkings());
           }
       }
       return true;
       
   }
}
