/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package avltree;

/**
 *
 * @author Atarin
 */
public class stringKatUzemie extends Node{
    
    
    private StringKey kk;
    private int cisloKatastra;

    public stringKatUzemie(String paKey, int paCisloKatastra) {
        
        super(new StringKey(paKey));
        kk=new StringKey(paKey);
        cisloKatastra=paCisloKatastra;
    }

    @Override
    public stringKatUzemie getLeftSon() {
        return (stringKatUzemie) this.leftSon;
    }

    @Override
    public stringKatUzemie getRightSon() {
        return (stringKatUzemie) this.rightSon;
    }
    @Override
    public stringKatUzemie getFather() {
        return (stringKatUzemie) this.father;
    }

    //@Override
     public int compareTo(stringKatUzemie temp){
        //System.out.println("hhhhhh "+this.getKey().getKey().compareTo(temp.getKey().getKey()));
         
        if(this.getKey().getKey().compareTo(temp.getKey().getKey())==0) return 0;
        if(this.getKey().getKey().compareTo(temp.getKey().getKey())>0) return 1;
        if(this.getKey().getKey().compareTo(temp.getKey().getKey())<0) return -1;
        return -1;
                }
    @Override
    public StringKey getKey(){
        return this.kk;
    }

    
    @Override
     public String toString(){
        String ret="";
        
        ret=this.kk.getKey()+" ";
        
        return ret;
    }
    
}
