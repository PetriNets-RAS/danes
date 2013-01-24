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
public class Transition extends Element{
    
    private int width;
    private int height;
        
     /**
     * @Class constructor.
     */
    public Transition(String paNazov){
        super(paNazov);
        width=45;
        height=25;
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
