/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Core.Element;

/**
 *
 * @author marek
 */
public class DiagramElement {
    private int x;
    private int y;
    private int width;
    private int height;

    public DiagramElement(int x, int y) {
        this(x, y, 50,50);
    }

    public DiagramElement(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
 

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    
    
}
