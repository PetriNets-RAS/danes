/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package danescreator;

/**
 *
 * @author Atarin
 */
public class Logika {
    
    public Logika(){
        
    }
    
    public boolean skontrolujHranu(Hrana paHrana){
    
        Prvok p1=paHrana.getVstupneMiesto();
        Prvok p2=paHrana.getVystupneMiesto();
        if( ((p1 instanceof Miesto)&&(p2 instanceof Prechod)) | ((p2 instanceof Miesto)&&(p1 instanceof Prechod)) ) {
            return true;
        }    
        return false;
    }
    
}
