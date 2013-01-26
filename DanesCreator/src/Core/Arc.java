/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

/**
 *
 * @author Michal Skovajsa
 */
public class Arc extends Element {

    private int capacity;
    private Element inElement;
    private Element outElement;
    
     /**
     * @Class constructor.
     */
    public Arc(String paName, Element paOutElement, Element paInElement) {
        super(paName);
        this.outElement=paOutElement;
        this.inElement=paInElement;
    }

    /**
     * @return the inElement
     */
    public Element getInElement() {
        return inElement;
    }

    /**
     * @param inElement the inElement to set
     */
    public void setInElement(Element inElement) {
        this.inElement = inElement;
    }

    /**
     * @return the outElement
     */
    public Element getOutElement() {
        return outElement;
    }

    /**
     * @param outElement the outElement to set
     */
    public void setOutElement(Element outElement) {
        this.outElement = outElement;
    }

    /**
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @param capacity the capacity to set
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}