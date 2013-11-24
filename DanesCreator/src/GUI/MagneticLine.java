/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Core.Element;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author MISO
 */
public abstract class MagneticLine {
    protected static final Color LINE_COLOR = Color.BLACK;
    protected Graphics2D g2d;
    protected int x, y;
    private ArrayList<Element> connectedElemenets;
    
    public MagneticLine(Graphics2D pG2d){
        this.connectedElemenets = new ArrayList<Element>();
        this.g2d = pG2d;
        this.g2d.setColor(LINE_COLOR);
    }
    
    public void addElement(Element pElement){
        this.connectedElemenets.add(pElement);
    }
    
    /**
     * draw magnetic line
     * @param length length of magnetic line
     */
    public abstract void drawMagneticLine(int length);
    
    
}
