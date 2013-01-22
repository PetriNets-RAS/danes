/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package danescreator;

import java.awt.Color;

/**
 *
 * @author Michal Skovajsa
 */
public class Miesto extends Prvok{

    private int kapacita;
    private int sirka;
    private int vyska;
    
    public Miesto(String paNazov){
        super(paNazov);
        this.sirka=50;
        this.vyska=35;
    }

    /**
     * @return the kapacita
     */
    public int getKapacita() {
        return kapacita;
    }

    /**
     * @param kapacita the kapacita to set
     */
    public void setKapacita(int kapacita) {
        this.kapacita = kapacita;
    }

    /**
     * @return the sirka
     */
    public int getSirka() {
        return sirka;
    }

    /**
     * @param sirka the sirka to set
     */
    public void setSirka(int sirka) {
        this.sirka = sirka;
    }

    /**
     * @return the vyska
     */
    public int getVyska() {
        return vyska;
    }

    /**
     * @param vyska the vyska to set
     */
    public void setVyska(int vyska) {
        this.vyska = vyska;
    }
    
}
