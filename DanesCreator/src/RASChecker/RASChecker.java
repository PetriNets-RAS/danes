/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RASChecker;

import Core.Arc;
import Core.PetriNet;
import Core.Place;
import Core.Resource;
import Core.Transition;
import GUI.RASCheckerWindow.RASCheckerWindow;
import StateSpace.State;
import StateSpace.StateSpaceCalculator;
import java.util.ArrayList;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Michal
 */
public class RASChecker {

    private RASCheckerWindow dialog;
    private PetriNet pn;
    private PSemiFlowSolver pSemiFlowSolver;
    private ArrayList<MatrixRow> matrix;
    //--1a-----------------only one place can own tokens----
    private int numbOfPlacesWithTokens;
    private ArrayList<Place> placesWithToken;
    //--1b-----------------only one place can own tokens----
    private int numbOfPlacesWithoutTokens;
    //--1c-----------------numb of resources----------------
    private int numbOfRecources;
    //--2------------------Own cycles-----------------------
    private ArrayList<OwnCyclePair> listOfOwnCyclePairs;
    //--2------------------Simple PN-----------------------
    private boolean simpleNet;
    //--4------------------Check states-----------------------
    private boolean assignedResourcesToStates;
    //--5------------------initialization-----------------------
    private boolean processPlacesIni;

    public RASChecker(PetriNet pn) {
        this.pn = pn;
        listOfOwnCyclePairs = new ArrayList<OwnCyclePair>();
        numbOfPlacesWithTokens = numbOfPlacesWithoutTokens = 0;
        numbOfRecources = pn.getListOfResources().size();
        placesWithToken = new ArrayList<Place>();
    }

    public ArrayList<int[]> getIncidenceMatrix() {
        ArrayList<int[]> incidenceMatrix = new ArrayList<int[]>();
        for (Place place : getPn().getListOfPlaces()) {
            int[] incidenceRow = new int[getPn().getListOfTransitions().size()];
            for (Arc a : place.getListOfOutArcs()) {
                Transition t = (Transition) a.getInElement();
                int capacity = a.getCapacity();
                int index = getPn().getListOfTransitions().indexOf(t);
                incidenceRow[index] = -capacity;
            }
            for (Arc a : place.getListOfInArcs()) {
                Transition t = (Transition) a.getOutElement();
                int capacity = a.getCapacity();
                int index = getPn().getListOfTransitions().indexOf(t);
                incidenceRow[index] = capacity;
            }
            incidenceMatrix.add(incidenceRow);
        }
        for (Resource res : getPn().getListOfResources()) {
            int[] incidenceRow = new int[getPn().getListOfTransitions().size()];
            for (Arc a : res.getListOfOutArcs()) {
                Transition t = (Transition) a.getInElement();
                int capacity = a.getCapacity();
                int index = getPn().getListOfTransitions().indexOf(t);
                incidenceRow[index] = -capacity;
            }
            for (Arc a : res.getListOfInArcs()) {
                Transition t = (Transition) a.getOutElement();
                int capacity = a.getCapacity();
                int index = getPn().getListOfTransitions().indexOf(t);
                incidenceRow[index] = capacity;
            }
            incidenceMatrix.add(incidenceRow);
        }
        return incidenceMatrix;
    }

    public ArrayList<MatrixRow> createMatrix(ArrayList<int[]> incidenceMatrix) {
        ArrayList<MatrixRow> matrix = new ArrayList<MatrixRow>();
        int size = incidenceMatrix.size();
        for (int i = 0; i < size; i++) {
            int[] pSemiFlowRow = new int[size];
            pSemiFlowRow[i] = 1;
            MatrixRow newMr = new MatrixRow(pSemiFlowRow, incidenceMatrix.get(i));
            matrix.add(newMr);
        }
        this.setMatrix(matrix);
        return matrix;
    }

    public void initialize() throws BadLocationException {
        dialog.addMessageToLogger("Initializing", 1);
        createMatrix(getIncidenceMatrix());
        pSemiFlowSolver = new PSemiFlowSolver(matrix,pn.getListOfPlaces().size());
        pSemiFlowSolver.toString();
        System.out.println("------------------------------------------------------------");
        pSemiFlowSolver.solve();
        //setpSemiFlowSolver(new PSemiFlowSolver(getMatrix()));
        //System.out.println(getpSemiFlowSolver().toString());
    }

    public void checkRAS() throws BadLocationException {
        findOwnCycles();
        findNumbOfPlacesWithWithoutTokens();
        getSimplicityofPN();
        countStates();
        checkStates();
        checkInicialization();
    }

    public void findOwnCycles() throws BadLocationException {
        dialog.addMessageToLogger("Finding own cycles", 1);
        for (Place actPlace : getPn().getListOfPlaces()) {
            for (Transition inTrans : actPlace.getListOfInTransitions()) {
                for (Transition outTrans : actPlace.getListOfOutTransitions()) {
                    if (inTrans == outTrans) {
                        boolean own = false;
                        for (OwnCyclePair actPair : getListOfOwnCyclePairs()) {
                            if (actPair.getPlace() == actPlace && actPair.getTransition() == outTrans) {
                                own = true;
                                break;
                            }
                        }
                        if (!own) {
                            getListOfOwnCyclePairs().add(new OwnCyclePair(actPlace, outTrans));
                        }
                    }
                }
            }
        }
        if (getListOfOwnCyclePairs().isEmpty()) {
            dialog.addMessageToLogger("Own cycles doesnt exists", 1);
        } else {
            String output = "Own cycles founded:";
            for (OwnCyclePair ocp : listOfOwnCyclePairs) {
                output = output + "\n      " + ocp.getPlace().getName() + " - " + ocp.getTransition().getName();
            }
            dialog.addMessageToLogger(output, 3);
        }

    }

    public void findNumbOfPlacesWithWithoutTokens() throws BadLocationException {
        dialog.addMessageToLogger("Checking idle state places / process places ", 1);
        int numbWith = 0;
        int numbWithout = 0;
        for (Place actPlace : getPn().getListOfPlaces()) {
            if (!actPlace.getMarkings().getMarkings().isEmpty()) {
                placesWithToken.add(actPlace);
                numbWith++;
            } else {
                numbWithout++;
            }
        }
        setNumbOfPlacesWithTokens(numbWith);
        setNumbOfPlacesWithoutTokens(numbWithout);
        dialog.addMessageToLogger("Number of places with tokens: " + numbWith, 1);
        dialog.addMessageToLogger("Number of places without tokens: " + numbWithout, 1);
    }

    public boolean getSimplicityofPN() throws BadLocationException {
        dialog.addMessageToLogger("Checking simplicity of a PN", 1);
        boolean simplicity = true;
        for (Arc a : getPn().getListOfArcs()) {
            if (!(a.getOutElement() instanceof Resource) && !(a.getInElement() instanceof Resource) && a.getCapacity() > 1) {
                simplicity = false;
                break;
            }
        }
        simpleNet = simplicity;
        if (simplicity) {
            dialog.addMessageToLogger("PN is simple", 1);
        } else {
            dialog.addMessageToLogger("PN isn't simple", 3);
        }
        return simplicity;
    }

    public void countStates() throws BadLocationException {
        dialog.addMessageToLogger("Finding set of states", 1);
        StateSpaceCalculator ssCalc = new StateSpaceCalculator(pn);
        ssCalc.calculateStateSpace();
        pn.setStates(ssCalc.getResult());
    }

    public void checkInicialization() throws BadLocationException {
        checkIniProcessPlaces();

    }

    public boolean checkIniProcessPlaces() throws BadLocationException {
        dialog.addMessageToLogger("Checking initialization of process places", 1);
        boolean emptyProcessPlaces = true;
        for (Place place : pn.getListOfPlaces()) {
            if (!place.isStart() && place.getMarkings().getMarkings().size() > 0) {
                emptyProcessPlaces = false;
                break;
            }
        }
        setProcessPlacesIni(emptyProcessPlaces);
        if (emptyProcessPlaces) {
            dialog.addMessageToLogger("Valid process places Inicialization", 1);
        } else {
            dialog.addMessageToLogger("Invalid process places Inicialization", 3);
        }
        this.processPlacesIni = emptyProcessPlaces;
        return emptyProcessPlaces;
    }

    public boolean checkStates() throws BadLocationException {
        dialog.addMessageToLogger("Checking states for assignment resources to the states", 1);
        boolean[] assignedStates = new boolean[pn.getStates().size()];
        for (State state : pn.getStates()) {
            int i = pn.getStates().indexOf(state);
            assignedStates[i] = false;
            int sumOfAssigned = 0;
            state.toString();

            for (Resource r : state.getPn().getListOfResources()) {
                for (int j = 0; j < r.getProcess().length; j++) {
                    sumOfAssigned += r.getProcess()[j];
                }
            }
            if (sumOfAssigned > 0) {
                assignedStates[i] = true;
            }
        }

        ArrayList<Integer> listOfUnassigned = new ArrayList<Integer>();
        for (int i = 0; i < assignedStates.length; i++) {
            if (!assignedStates[i]) {
                listOfUnassigned.add(i);
            }
        }
        //System.out.println("SUM OF ALL STATES:" + assignedStates.length);
        //System.out.println("SUM OF ASSIGNED STATES:" + (assignedStates.length - listOfUnassigned.size()));
        State newS = new State(pn.getState(), 0, null, pn);

        //System.out.println(newS.getKey().equals(pn.getStates().get(1).getKey()));
        //System.out.println(newS.getKey()==pn.getStates().get(1).getKey());
        if (listOfUnassigned.size() == 1 && newS.getKey().equals(pn.getStates().get(listOfUnassigned.get(0)).getKey())) {
            setAssignedResourcesToStates(true);
            dialog.addMessageToLogger("Valid assignment of resource to the states", 1);
            return true;
        } else {
            setAssignedResourcesToStates(false);
            dialog.addMessageToLogger("Invalid assignment of resource to the states", 3);
            return false;
        }
    }

    //---------------------GETTERS/SETTERS-----------------------------------------
    /**
     * @return the pn
     */
    public PetriNet getPn() {
        return pn;
    }

    /**
     * @param pn the pn to set
     */
    public void setPn(PetriNet pn) {
        this.pn = pn;
    }

    /**
     * @return the pSemiFlowSolver
     */
    public PSemiFlowSolver getpSemiFlowSolver() {
        return pSemiFlowSolver;
    }

    /**
     * @param pSemiFlowSolver the pSemiFlowSolver to set
     */
    public void setpSemiFlowSolver(PSemiFlowSolver pSemiFlowSolver) {
        this.pSemiFlowSolver = pSemiFlowSolver;
    }

    /**
     * @return the matrix
     */
    public ArrayList<MatrixRow> getMatrix() {
        return matrix;
    }

    /**
     * @param matrix the matrix to set
     */
    public void setMatrix(ArrayList<MatrixRow> matrix) {
        this.matrix = matrix;
    }

    /**
     * @return the numbOfPlacesWithTokens
     */
    public int getNumbOfPlacesWithTokens() {
        return numbOfPlacesWithTokens;
    }

    /**
     * @param numbOfPlacesWithTokens the numbOfPlacesWithTokens to set
     */
    public void setNumbOfPlacesWithTokens(int numbOfPlacesWithTokens) {
        this.numbOfPlacesWithTokens = numbOfPlacesWithTokens;
    }

    /**
     * @return the numbOfPlacesWithoutTokens
     */
    public int getNumbOfPlacesWithoutTokens() {
        return numbOfPlacesWithoutTokens;
    }

    /**
     * @param numbOfPlacesWithoutTokens the numbOfPlacesWithoutTokens to set
     */
    public void setNumbOfPlacesWithoutTokens(int numbOfPlacesWithoutTokens) {
        this.numbOfPlacesWithoutTokens = numbOfPlacesWithoutTokens;
    }

    /**
     * @return the numbOfRecources
     */
    public int getNumbOfRecources() {
        return numbOfRecources;
    }

    /**
     * @param numbOfRecources the numbOfRecources to set
     */
    public void setNumbOfRecources(int numbOfRecources) {
        this.numbOfRecources = numbOfRecources;
    }

    /**
     * @return the listOfOwnCyclePairs
     */
    public ArrayList<OwnCyclePair> getListOfOwnCyclePairs() {
        return listOfOwnCyclePairs;
    }

    /**
     * @param listOfOwnCyclePairs the listOfOwnCyclePairs to set
     */
    public void setListOfOwnCyclePairs(ArrayList<OwnCyclePair> listOfOwnCyclePairs) {
        this.listOfOwnCyclePairs = listOfOwnCyclePairs;
    }

    /**
     * @return the simpleNet
     */
    public boolean isSimpleNet() {
        return simpleNet;
    }

    /**
     * @param simpleNet the simpleNet to set
     */
    public void setSimpleNet(boolean simpleNet) {
        this.simpleNet = simpleNet;
    }

    /**
     * @param dialog the dialog to set
     */
    public void setDialog(RASCheckerWindow dialog) {
        this.dialog = dialog;
    }

    /**
     * @return the placesWithToken
     */
    public ArrayList<Place> getPlacesWithToken() {
        return placesWithToken;
    }

    /**
     * @param placesWithToken the placesWithToken to set
     */
    public void setPlacesWithToken(ArrayList<Place> placesWithToken) {
        this.placesWithToken = placesWithToken;
    }

    /**
     * @return the processPlacesIni
     */
    public boolean isProcessPlacesIni() {
        return processPlacesIni;
    }

    /**
     * @param processPlacesIni the processPlacesIni to set
     */
    public void setProcessPlacesIni(boolean processPlacesIni) {
        this.processPlacesIni = processPlacesIni;
    }

    /**
     * @return the assignedResourcesToStates
     */
    public boolean isAssignedResourcesToStates() {
        return assignedResourcesToStates;
    }

    /**
     * @param assignedResourcesToStates the assignedResourcesToStates to set
     */
    public void setAssignedResourcesToStates(boolean assignedResourcesToStates) {
        this.assignedResourcesToStates = assignedResourcesToStates;
    }
}
