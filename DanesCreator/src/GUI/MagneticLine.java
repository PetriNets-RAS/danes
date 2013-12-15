/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Core.ComplexElement;
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
    protected int x1, y1, x2, y2;
    private ArrayList<ComplexElement> connectedElemenets;
    
    public MagneticLine(Graphics2D pG2d){
        this.connectedElemenets = new ArrayList<ComplexElement>();
        this.g2d = pG2d;
        this.g2d.setColor(LINE_COLOR);
    }
    
    public MagneticLine(){
        this.connectedElemenets = new ArrayList<ComplexElement>();
    };
    
    public void addElement(ComplexElement pElement){
        if(pElement != null){
            this.getConnectedElemenets().add(pElement);
        }
    }
    
    public void removeElement(ComplexElement pElement){
        if(pElement != null){
            this.getConnectedElemenets().remove(pElement);
        }
    }
    
    /*
    public void unConnectElementsFromMagneticLine(){
        for (ComplexElement complexElement : this.getConnectedElemenets()) {
            complexElement.setConnectedToHorizontalMagnet(false);
        }
    }
    */

    /**
     * @return the connectedElemenets
     */
    public ArrayList<ComplexElement> getConnectedElemenets() {
        return connectedElemenets;
    }
    
    /**
     * draw magnetic line 
     * Note: this method doesnt work
     * @param length length of magnetic line
     */
    public abstract void drawMagneticLine(int length);
    
    /**
     * 
     * @param elements list of all graph elements 
     */
    public abstract void addElementsToMagneticLine(ArrayList<ComplexElement> elements);

    public abstract void checkConnectedElements();
    
    public abstract void unConnectElementsFromMagneticLine();
    
}
