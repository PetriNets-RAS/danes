/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RASChecker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Michal
 */
public class PSemiFlowSolver {

    private ArrayList<MatrixRow> listOfRows;
    private int numbOfRounds;
    private int numbOfPlaces;
    private int numbOfColumns;

    public PSemiFlowSolver(ArrayList<MatrixRow> listOfRows, int numbOfP) {
        this.listOfRows = listOfRows;
        numbOfRounds = listOfRows.get(0).getIncidenceRow().length;
        this.numbOfPlaces = numbOfP;
        numbOfColumns = listOfRows.size();
    }

    public MatrixRow findFirstNotNullRow(int column) {
        for (MatrixRow mr : listOfRows) {
            if (!mr.isCanceled() && mr.getIncidenceRow()[column] != 0) {
                return mr;
            }
        }
        return null;
    }

    public ArrayList<int[]> getPSemiFlows() {
        System.out.println(toString());
        System.out.println("----------------------------------------------------");

        for (int i = 0; i < numbOfRounds; i++) {
            MatrixRow subtractor = findFirstNotNullRow(i);
            if (subtractor == null) {
                System.out.println("ERROR");
                break;
            }
            System.out.println("SUBS " + (i + 1));
            int size = listOfRows.size();
            for (int j = 0; j < size; j++) {
                MatrixRow actualMR = listOfRows.get(j);
                if (!listOfRows.get(j).isCanceled() && actualMR.getIncidenceRow()[i] != 0 && subtractor != actualMR) {
                    System.out.println("odcitavam " + i + " " + j);
                    MatrixRow newRow = actualMR.substractRow(subtractor, i);
                    //System.out.println(i);
                    System.out.println(newRow);
                    listOfRows.add(newRow);
                }
            }
            System.out.println("----------------------------------------------------");
            System.out.println("koniec kola: " + i);
            subtractor.setCanceled(true);


        }
        ArrayList<int[]> pSemiFlws = new ArrayList<int[]>();
        for (MatrixRow mr : listOfRows) {
            if (!mr.isCanceled()) {
                pSemiFlws.add(mr.getpSemiflow());
                System.out.println(mr);
            }
        }
        return pSemiFlws;
    }

    public void solve() {
        ArrayList<int[]> prePSemiFlows = getPSemiFlows();
        ArrayList<int[]> finalPSemiFlows = new ArrayList<int[]>();
        for (int i = 1; i < prePSemiFlows.size(); i++) {
            Set<Set<int[]>> subSetSemiFlows = getCombinationsFor(prePSemiFlows, i);
            for (Set<int[]> set : subSetSemiFlows) {
                int[] newPSemiFlow = new int[listOfRows.get(0).getpSemiflow().length];
                for (int[] prePSemiFlow : set) {
                    newPSemiFlow = vectorAddition(newPSemiFlow, prePSemiFlow);
                }
                finalPSemiFlows.add(newPSemiFlow);
            }
        }
        System.out.println("---------------------------------------------");
        for (int i = 0; i < finalPSemiFlows.size(); i++) {
            System.out.print(i + ") ");
            for (int j = 0; j < finalPSemiFlows.get(i).length; j++) {
                System.out.print(" " + finalPSemiFlows.get(i)[j]);
            }
            System.out.println();
        }
        int[] summed = new int[finalPSemiFlows.get(0).length];
        for (int i = 0; i < finalPSemiFlows.size(); i++) {
            summed = sumRows(summed, finalPSemiFlows.get(i));
        }
        /*
        System.out.println("---------------------------------------------");
        for (int j = 0; j < summed.length; j++) {
            System.out.print(" " + summed[j]);
        }
        System.out.println();
        System.out.println("IDEM DALEJ");
        //getReducedPSemiFlows(finalPSemiFlows);
*/
        
        
    }
/*
    private ArrayList<int[]> getReducedPSemiFlows(ArrayList<int[]> inputVectors) {
        ArrayList<int[]> psemiFlows = new ArrayList<int[]>();
        for (int i = numbOfPlaces; i < numbOfColumns; i++) {
            int k = 0;
            for (int[] row : inputVectors) {
                System.out.println(k + "} " + getRowSum(row, i));
                k++;
            }
            System.out.println("**********");
        }
        return psemiFlows;
    }

    private boolean getRowSum(int[] row, int index) {
        boolean result = true;
        for (int i = numbOfPlaces; i < row.length; i++) {
            if (i == index) {
                if (row[i] == 0) {
                    result = false;
                    break;
                }
            } else {
                if (row[i] != 0) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }*/

    private int[] sumRows(int[] a, int[] b) {
        int[] result = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] + b[i];
        }
        return result;
    }

    @Override
    public String toString() {
        String output = "";
        for (MatrixRow mr : listOfRows) {
            output = output + mr + "\n";
        }
        return output;
    }

    private int[] vectorAddition(int[] v1, int[] v2) {
        int[] result = new int[v1.length];
        for (int i = 0; i < v1.length; i++) {
            result[i] = v1[i] + v2[i];
        }
        return result;

    }

    public Set<Set<int[]>> getCombinationsFor(List<int[]> group, int subsetSize) {
        Set<Set<int[]>> resultingCombinations = new HashSet<Set<int[]>>();
        int totalSize = group.size();
        if (subsetSize == 0) {
            emptySet(resultingCombinations);
        } else if (subsetSize <= totalSize) {
            List<int[]> remainingElements = new ArrayList<int[]>(group);
            int[] X = popLast(remainingElements);

            Set<Set<int[]>> combinationsExclusiveX = getCombinationsFor(remainingElements, subsetSize);
            Set<Set<int[]>> combinationsInclusiveX = getCombinationsFor(remainingElements, subsetSize - 1);
            for (Set<int[]> combination : combinationsInclusiveX) {
                combination.add(X);
            }
            resultingCombinations.addAll(combinationsExclusiveX);
            resultingCombinations.addAll(combinationsInclusiveX);
        }
        return resultingCombinations;
    }

    private void emptySet(Set<Set<int[]>> resultingCombinations) {
        resultingCombinations.add(new HashSet<int[]>());
    }

    private int[] popLast(List<int[]> elementsExclusiveX) {
        return elementsExclusiveX.remove(elementsExclusiveX.size() - 1);
    }
}
