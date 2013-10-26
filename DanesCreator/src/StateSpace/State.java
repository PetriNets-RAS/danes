/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StateSpace;

import Core.PetriNet;
import Core.Resource;
import java.util.ArrayList;

/**
 *
 * @author Atarin
 */
public class State {

    private int[] markingField;
    private ArrayList<StateItem> childs;
    private int lastMarkedItem;
    private ArrayList<ArrayList<Integer>> placeMarkings;
    private State parent;
    private PetriNet pn;

    /*
     public State(int[] pMarkigField, int pLastMarkedItem, State pParent){
     int count = pMarkigField.length;
     this.markingField = new int[count];
     this.placeMarkings = new ArrayList<int[]>();
     this.markingField=pMarkigField.clone();
     this.lastMarkedItem = pLastMarkedItem;
     this.parent = pParent;
     this.childs = new ArrayList<StateItem>();
     }
     */
    public State(ArrayList<ArrayList<Integer>> pPlaceMarkings, int pLastMarkedItem, State pParent, PetriNet pn) {
        //int count = pMarkigField.length;
        //this.markingField = new int[count];
        this.placeMarkings = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < pPlaceMarkings.size(); i++) {
            ArrayList a = new ArrayList();
            for (int j = 0; j < pPlaceMarkings.get(i).size(); j++) {
                a.add(new Integer(pPlaceMarkings.get(i).get(j)));
            }
            placeMarkings.add(a);
        }
        this.pn = pn;
        //this.placeMarkings = pPlaceMarkings;
        //this.markingField=pMarkigField.clone();
        this.lastMarkedItem = pLastMarkedItem;
        this.parent = pParent;
        this.childs = new ArrayList<StateItem>();

    }

    public int[] getMarkingField() {
        return markingField;
    }

    /**
     * @param markingField the markingField to set
     */
    public void setMarkingField(int[] markingField) {
        this.markingField = markingField;
    }

    /**
     * @return the listStateItems
     */
    public ArrayList<StateItem> getChilds() {
        return childs;
    }

    public void addChild(StateItem s) {
        this.childs.add(s);
    }

    @Override
    public String toString() {
        StringBuilder stateName = new StringBuilder();

        stateName.append("");
        //Append for places
        for (int i = 0; i < pn.getListOfPlaces().size(); i++) {
            stateName.append(pn.getListOfPlaces().get(i).getName()).append(":");
            if (this.placeMarkings.get(i).isEmpty()) {
                stateName.append("0");
            }
            for (int j = 0; j < this.placeMarkings.get(i).size(); j++) {
                if (j < this.placeMarkings.get(i).size() - 1) {
                    stateName.append("1'").append(this.placeMarkings.get(i).get(j)).append("++");
                } else {
                    stateName.append("1'").append(this.placeMarkings.get(i).get(j));
                }
            }
            stateName.append(";");
        }
        //Append for resources
        for (int i = 0; i < pn.getListOfResources().size(); i++) {
            int[] process = getCount(this.placeMarkings.get(i + pn.getListOfPlaces().size()), pn.getListOfResources().get(i));
            if (this.placeMarkings.get(i + pn.getListOfPlaces().size()).get(0) != 0) {
                //add free resources
                stateName.append(this.placeMarkings.get(i + pn.getListOfPlaces().size()).get(0)).append("'(");
                stateName.append(pn.getListOfResources().get(i).getName()).append(",0)");
                //add "++" if som resources are used
//                if(process.length >0 && isNull(process,0)){
//                    stateName.append("++");
//                }

//                if (process.length <= 0 || isNull(process, 0)) {
//                } else {
//                    stateName.append("++");
//                }


            }

            //add used resources
            for (int k = 0; k < process.length; k++) {
                if (process[k] != 0) {

                    stateName.append("++").append(process[k]).append("'(").append(pn.getListOfResources().get(i).getName()).append(",").append((k + 1)).append(")");
//                    if (isNull(process, k)) {
//                        stateName.append("++");
//                    }
                }
            }


            //System.out.println("i: "+i);
            //System.out.println("size: "+(pn.getListOfResources().size()-1));
            //if not a last resource add++
            if (i < pn.getListOfResources().size() - 1
                    && this.placeMarkings.get(i + 1 + pn.getListOfPlaces().size()).get(0) > 0) {
                stateName.append("++");
            }
        }
        stateName.append(";");
        return stateName.toString();
    }

    public String getKey() {
        StringBuilder stateName = new StringBuilder();
        /*
         for (int i = 0; i < this.markingField.length; i++) {
         stateName.append(this.markingField[i]);
         }
         */
        stateName.append("{");
        for (int i = 0; i < pn.getListOfPlaces().size() - 1; i++) {
            //for (int i = 0; i < this.placeMarkings.size() - 1; i++) {
            stateName.append(pn.getListOfPlaces().get(i).getName()).append(":");
            //stateName.append("P").append(i+1).append(":");
            for (int j = 0; j < this.placeMarkings.get(i).size(); j++) {
                if (j < this.placeMarkings.get(i).size() - 1) {
                    stateName.append("1'").append(this.placeMarkings.get(i).get(j)).append("++");
                } else {
                    stateName.append("1'").append(this.placeMarkings.get(i).get(j));
                }
            }
            stateName.append(";");
        }
        for (int i = 0; i < pn.getListOfResources().size(); i++) {
            //for (int i = 0; i < this.placeMarkings.size() - 1; i++) {
            stateName.append(pn.getListOfResources().get(i).getName()).append(":");
            stateName.append(this.placeMarkings.get(i + pn.getListOfPlaces().size()).get(0));
            //stateName.append("P").append(i+1).append(":");
//            for (int j = 0; j < this.placeMarkings.get(i).size(); j++) {
//                if (j < this.placeMarkings.get(i).size() - 1) {
//                    stateName.append("1'").append(this.placeMarkings.get(i).get(j)).append("++");
//                } else {
//                    stateName.append("1'").append(this.placeMarkings.get(i).get(j));
//                }
//            }
            stateName.append(";");
        }

//        int lastIndexOfArray = this.placeMarkings.size() - 1; // resources
//        for (int j = 0; j < this.placeMarkings.get(lastIndexOfArray).size(); j++) {
//            stateName.append(pn.getListOfResources().get(j).getName()).append(j + 1).append(":").append(this.placeMarkings.get(lastIndexOfArray).get(j)).append(";");
//            //stateName.append("R").append(":").append(this.placeMarkings.get(lastIndexOfArray).get(j)).append(";");       
//        }


//        int lastIndexOfArray = this.placeMarkings.size() - 1; // resources
//        for (int j = 0; j < this.placeMarkings.get(lastIndexOfArray).size(); j++) {
//            stateName.append(pn.getListOfResources().get(j).getName()).append(j + 1).append(":").append(this.placeMarkings.get(lastIndexOfArray).get(j)).append(";");
//            //stateName.append("R").append(":").append(this.placeMarkings.get(lastIndexOfArray).get(j)).append(";");       
//        }
        stateName.append("}");
        return stateName.toString();
    }

    public int getLastMarkedItem() {
        return lastMarkedItem;
    }

    public void increaseLastMarkedItem() {
        this.lastMarkedItem += 1;
    }

    public State getParent() {
        return parent;
    }

    /**
     * @return the placeMarkings
     */
    public ArrayList<ArrayList<Integer>> getPlaceMarkings() {
        return placeMarkings;
    }

    /**
     * @param placeMarkings the placeMarkings to set
     */
    public void setPlaceMarkings(ArrayList<ArrayList<Integer>> placeMarkings) {
        this.placeMarkings = placeMarkings;
    }

    /**
     * @return the pn
     */
    public PetriNet getPn() {
        return pn;
    }

    public int[] getCount(ArrayList<Integer> procesMarkings, Resource r) {
        int[] result = new int[getMax(procesMarkings)];
        for (int i = 1; i < procesMarkings.size(); i++) {
            int selected = procesMarkings.get(i);
            result[selected - 1]++;
        }
        return result;
    }

    public int getMax(ArrayList<Integer> procesMarkings) {
        int max = 0;
        for (int i = 0; i < procesMarkings.size(); i++) {
            if (procesMarkings.get(i) > max) {
                max = procesMarkings.get(i);
            }
        }

        return max;
    }
}
