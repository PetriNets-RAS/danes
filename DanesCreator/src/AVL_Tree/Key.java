/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AVL_Tree;

/**
 *
 * @author Atarin
 */
public abstract class Key <T>{
    
    T key;
    
    public Key(T paKey){
        key=paKey;
    }

    public int compareTo(){
        
        return 0;
    }
    
    public void setKey(T paKey){
        key=paKey;
    }
    
    public T getKey(){
        return key;
    }
    public void vypis(){
        
    }

    public int compareTo(T n){
        
         return -1;
    }

    
}
