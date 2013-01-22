/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package danescreator;

import java.util.ArrayList;

/**
 *
 * @author Atarin
 */
public class PetrihoSiet {

    private ArrayList<Miesto> listMiest;
    private ArrayList<Prechod> listPrechodov;
    private ArrayList<Hrana> listHran;
    private String nazov;

    public PetrihoSiet(String paNazov) {
        this.nazov = paNazov;
        this.listMiest = new ArrayList<>();
        this.listHran = new ArrayList<>();
        this.listPrechodov = new ArrayList<>();
    }

    public boolean pridajMiesto(Miesto paMiesto) {
        for (Miesto prechMiesto : getListMiest()) {
            if (prechMiesto.getNazov().equals(paMiesto.getNazov())) {
                return false;
            }
        }
        getListMiest().add(paMiesto);
        return true;
    }

    public boolean odstranMiesto(String paNazov) {
        for (Miesto prechMiesto : getListMiest()) {
            if (prechMiesto.getNazov().equals(paNazov)) {
                this.listMiest.remove(prechMiesto);
                return true;
            }
        }
        return false;
    }

    public boolean pridajPrechod(Prechod paPrechod) {
        for (Prechod prechPrechod : listPrechodov) {
            if (prechPrechod.getNazov().equals(paPrechod.getNazov())) {
                return false;
            }
        }
        listPrechodov.add(paPrechod);
        return true;
    }

    public boolean odstranPrechod(String paNazov) {
        for (Prechod prechPrechod : listPrechodov) {
            if (prechPrechod.getNazov().equals(paNazov)) {
                listPrechodov.remove(prechPrechod);
                return true;
            }
        }
        return false;
    }
    
        public boolean pridajHranu(Hrana paHrana) {
        for (Hrana prechHrana : listHran) {
            if (prechHrana.getNazov().equals(paHrana.getNazov())) {
                return false;
            }
        }
        listHran.add(paHrana);
        return true;
    }

    public boolean odstranHranu(String paNazov) {
        for (Hrana prechHrana : listHran) {
            if (prechHrana.getNazov().equals(paNazov)) {
                listHran.remove(prechHrana);
                return true;
            }
        }
        return false;
    }

    /**
     * @return the listMiest
     */
    public ArrayList<Miesto> getListMiest() {
        return listMiest;
    }

    /**
     * @param listMiest the listMiest to set
     */
    public void setListMiest(ArrayList<Miesto> listMiest) {
        this.listMiest = listMiest;
    }

    /**
     * @return the listPrechodov
     */
    public ArrayList<Prechod> getListPrechodov() {
        return listPrechodov;
    }

    /**
     * @param listPrechodov the listPrechodov to set
     */
    public void setListPrechodov(ArrayList<Prechod> listPrechodov) {
        this.listPrechodov = listPrechodov;
    }

    /**
     * @return the listHran
     */
    public ArrayList<Hrana> getListHran() {
        return listHran;
    }

    /**
     * @param listHran the listHran to set
     */
    public void setListHran(ArrayList<Hrana> listHran) {
        this.listHran = listHran;
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
}
