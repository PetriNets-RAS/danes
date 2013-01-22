/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package danescreator;

/**
 *
 * @author Atarin
 */
public class Hrana extends Prvok {

    private Prvok vystupneMiesto;
    private Prvok vstupneMiesto;
    
    public Hrana(String paNazov, Prvok paVystupneMiesto, Prvok paVstupneMiesto) {
        super(paNazov);
        this.vstupneMiesto=paVstupneMiesto;
        this.vystupneMiesto=paVystupneMiesto;
    }

    /**
     * @return the vystupneMiesto
     */
    public Prvok getVystupneMiesto() {
        return vystupneMiesto;
    }

    /**
     * @param vystupneMiesto the vystupneMiesto to set
     */
    public void setVystupneMiesto(Prvok vystupneMiesto) {
        this.vystupneMiesto = vystupneMiesto;
    }

    /**
     * @return the vstupneMiesto
     */
    public Prvok getVstupneMiesto() {
        return vstupneMiesto;
    }

    /**
     * @param vstupneMiesto the vstupneMiesto to set
     */
    public void setVstupneMiesto(Prvok vstupneMiesto) {
        this.vstupneMiesto = vstupneMiesto;
    }
}