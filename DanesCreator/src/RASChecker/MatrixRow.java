/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RASChecker;

/**
 *
 * @author Michal
 */
public class MatrixRow {

    private int[] pSemiflow;
    private int[] incidenceRow;
    private boolean canceled;

    public MatrixRow(int[] ps, int[] ir) {
        this.pSemiflow = ps;
        this.incidenceRow = ir;
        canceled = false;
    }

    public MatrixRow substractRow(MatrixRow mr, int column) {

        int multiplier = incidenceRow[column];
        if(mr.getIncidenceRow()[column]>0) multiplier = multiplier*(-1);
        int[] newIncidRow = new int[incidenceRow.length];
        int[] newpSemFlw = new int[pSemiflow.length];
        for (int i = 0; i < incidenceRow.length; i++) {
            newIncidRow[i] = incidenceRow[i] + multiplier * mr.getIncidenceRow()[i];
        }
        for (int i = 0; i < pSemiflow.length; i++) {
            newpSemFlw[i] = pSemiflow[i] + multiplier * mr.getpSemiflow()[i];
        }
        canceled = true;
        return new MatrixRow(newpSemFlw, newIncidRow);
    }

    /**
     * @return the pSemiflow
     */
    public int[] getpSemiflow() {
        return pSemiflow;
    }

    /**
     * @param pSemiflow the pSemiflow to set
     */
    public void setpSemiflow(int[] pSemiflow) {
        this.pSemiflow = pSemiflow;
    }

    /**
     * @return the incidenceRow
     */
    public int[] getIncidenceRow() {
        return incidenceRow;
    }

    /**
     * @param incidenceRow the incidenceRow to set
     */
    public void setIncidenceRow(int[] incidenceRow) {
        this.incidenceRow = incidenceRow;
    }

    public String toString() {
        String output = "";
        for (int i = 0; i < pSemiflow.length; i++) {
            output = output + pSemiflow[i] + " ";
        }
        output = output + " | ";
        for (int i = 0; i < incidenceRow.length; i++) {
            output = output + incidenceRow[i] + " ";
        }
        output = output + " | " + isCanceled();
        return output;
    }

    /**
     * @return the canceled
     */
    public boolean isCanceled() {
        return canceled;
    }

    /**
     * @param canceled the canceled to set
     */
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
