/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import Core.Element;


/**
 *
 * @author Michal Skovajsa
 */
public class Place extends Element{

    private int capacity;
    private int width;
    private int height;
        
     /**
     * @Class constructor.
     */
    public Place(String paName){
        super(paName);
        this.width=50;
        this.height=35;
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

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }
    
}
