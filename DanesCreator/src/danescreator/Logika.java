/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package danescreator;

/**
 *
 * @author Michal Skovajsa
 */
public class Logika {
        
     /**
     * @Konstruktor triedy Logika
     */
    public Logika(){
        
    }
    
    /**
     * @metoda, ktora skontroluje hranu, ci moze byt platna
     */
    public boolean skontrolujHranu(Hrana paHrana){
    
        Prvok p1=paHrana.getVstupneMiesto();
        Prvok p2=paHrana.getVystupneMiesto();
        if( ((p1 instanceof Miesto)&&(p2 instanceof Prechod)) | ((p2 instanceof Miesto)&&(p1 instanceof Prechod)) ) {
            return true;
        }    
        return false;
    }
    
}
