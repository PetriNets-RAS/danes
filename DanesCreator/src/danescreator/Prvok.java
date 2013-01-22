/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package danescreator;

import java.awt.Color;

/**
 *
 * @author Atarin
 */
public class Prvok {
    
    private String nazov;
    private String popis;
    private Color farba;
    private int velkostPisma;
    
    public Prvok(String paNazov){
        this.nazov=paNazov;
    }

    /**
     * @return the nazov
     */
    public String getNazov() {
        return nazov;
    }

    /**
     * @param nazov the nazov to set
     */
    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    /**
     * @return the popis
     */
    public String getPopis() {
        return popis;
    }

    /**
     * @param popis the popis to set
     */
    public void setPopis(String popis) {
        this.popis = popis;
    }

    /**
     * @return the farba
     */
    public Color getFarba() {
        return farba;
    }

    /**
     * @param farba the farba to set
     */
    public void setFarba(Color farba) {
        this.farba = farba;
    }

    /**
     * @return the velkostPisma
     */
    public int getVelkostPisma() {
        return velkostPisma;
    }

    /**
     * @param velkostPisma the velkostPisma to set
     */
    public void setVelkostPisma(int velkostPisma) {
        this.velkostPisma = velkostPisma;
    }
    
}
