/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AVL_Tree;

/**
 *
 * @author Atarin
 */
public class StringKey extends Key<String> {
    
    String pp;
    
    public StringKey(String paKey){
        super(paKey);
        pp=paKey;
    }
    
    
    @Override
    public String getKey(){
        return pp;
    }

    @Override
    public int compareTo(String n){
        if(this.getKey().equals(n))return 0;
        if( this.getKey().compareTo(n) >0 ) return 1;
        if( this.getKey().compareTo(n) <0 ) return -1;

        return -1;
    }
    
}
