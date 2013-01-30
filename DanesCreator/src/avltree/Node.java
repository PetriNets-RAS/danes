/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package avltree;

/**
 *
 * @author Atarin
 */
public class Node{
    
    //protected String note;
    protected Node father;
    protected Node leftSon;
    protected Node rightSon;
    protected int leftHigh;
    protected int rightHigh;
    protected int equanimity;
    protected Key key;

    public Node(Key paKey) {
        key = paKey;
        //note = paNote;
        
    }
    public Node() {
        
    }

//    public String getNote() {
//        return note;
//    }
//

    public Node getFather() {
        return father;
    }

    public void setFather(Node father) {
        this.father = father;
    }

    public void setRightSon(Node son) {
        this.rightSon = son;
    }

    public void setLeftSon(Node son) {
        this.leftSon = son;
    }

    public Node getLeftSon() {
        return leftSon;
    }

    public Node getRightSon() {
        return rightSon;
    }
//-------------------------
    public boolean setSon(Node son) {
        if (son == this) {
            return false;
        }        
        
        if (son.getKey().compareTo(this.getKey().getKey()) <0 && this.getLeftSon() == null) {
            //System.out.println("Vkladam otcovi "+this.getKey().getKey()+" --- "+son.compareTo(this.getKey()));
            this.setLeftSon(son);
            son.setFather(this);
            return true;
        }
        if (son.getKey().compareTo(this.getKey().getKey()) >0 && this.getRightSon() == null) {
            //System.out.println("Vkladam otcovi "+this.getKey().getKey());
            this.setRightSon(son);
            son.setFather(this);
            return true;
        }
        if (son.getKey().compareTo(this.getKey().getKey()) <0 && this.getLeftSon() != null) {
            this.getLeftSon().setSon(son);
            return true;
        }
        if (son.getKey().compareTo(this.getKey().getKey()) >0 && this.getRightSon() != null) {
            this.getRightSon().setSon(son);
            return true;
        }
        return false;
    }
//------------------------
    
    
    public Node find(Key paKey) {

        if (this.getKey().compareTo(paKey.getKey()) == 0) {
            return this;
        }
        if (this.getKey().compareTo(paKey.getKey())==1 && this.getLeftSon() == null) { 
            return null;
        }
        if (this.getKey().compareTo(paKey.getKey())==-1 && this.getRightSon() == null) {
            return null;
        }
        if (this.getKey().compareTo(paKey.getKey())==1 && this.getLeftSon() != null) {
            return (this.getLeftSon().find(paKey));
        }
        if (this.getKey().compareTo(paKey.getKey())==-1 && this.getRightSon() != null) { 
            return (this.getRightSon().find(paKey));
        }
        return null;
    }
   
    /**
     * @return the leftHigh
     */
    public int getLeftHigh() {
        return leftHigh;
    }

    /**
     * @param leftHigh the leftHigh to set
     */
    public void setLeftHigh(int leftHigh) {
        this.leftHigh = leftHigh;
    }

    /**
     * @return the rightHigh
     */
    public int getRightHigh() {
        return rightHigh;
    }

    /**
     * @param rightHigh the rightHigh to set
     */
    public void setRightHigh(int rightHigh) {
        this.rightHigh = rightHigh;
    }

    /**
     * @return the equanimity
     */
    public int getEquanimity() {
        return equanimity;
    }

    /**
     * @param equanimity the equanimity to set
     */
    public void setEquanimity(int equanimity) {
        this.equanimity = equanimity;
    }

    public void compensate() {
        int vyvaz;
        if (this.getRightSon() == null) {
            this.setRightHigh(0);
        } else {
            if (this.getRightSon().getLeftHigh() >= this.getRightSon().getRightHigh()) {
                vyvaz = this.getRightSon().getLeftHigh();
            } else {
                vyvaz = this.getRightSon().getRightHigh();
            }
            this.setRightHigh(vyvaz + 1);
        }

        if (this.getLeftSon() == null) {
            this.setLeftHigh(0);
        } else {
            if (this.getLeftSon().getLeftHigh() >= this.getLeftSon().getRightHigh()) {
                vyvaz = this.getLeftSon().getLeftHigh();
            } else {
                vyvaz = this.getLeftSon().getRightHigh();
            }
            this.setLeftHigh(vyvaz + 1);
        }
        this.equanimity = this.rightHigh - this.leftHigh;

    }
    
    public int compareTo(Key n){
        return 0;
    }

    /**
     * @return the key
     */
    public Key getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(Key key) {
        this.key = key;
    }

    
    @Override
    public String toString(){
        String ret="";
        //ret=this.getKey().getKey();
        return ret;
    }
    
}
