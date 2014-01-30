/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Core.ComplexElement;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author MISO
 */
public class VerticalMagneticLine extends MagneticLine{
    public VerticalMagneticLine(Graphics2D pG2d, int pX){
        super(pG2d);
        super.y1 = 0;
        super.x1 = pX;
    }
    public int getX(){
        return super.x1;
    }
    
    public VerticalMagneticLine(int pX,int pHeight){
        super.x1 = pX;
        super.x2 = pX;
        super.y1 = 0;
        super.y2 = pHeight;
    }
    
    @Override
    public void drawMagneticLine(int height) {
        g2d.drawLine(super.x1, super.y1, super.x1, height);
    }
    

    @Override
    public void addElementsToMagneticLine(ArrayList<ComplexElement> elements) {
        for (ComplexElement complexElement : elements) {
            if(!complexElement.isConnectedToVerticalMagnet()&&(complexElement.getX() <= super.x1 && complexElement.getX()+complexElement.getWidth() >= super.x1)){
                super.addElement(complexElement);
                //complexElement.setConnectedToHorizontalMagnet(true);
                complexElement.setConnectedToVerticalMagnet(true);
                int halfWidth =(int)(complexElement.getWidth()/2);
                int length = super.x1 - halfWidth;
                complexElement.setX(length);
            }
        }
        this.checkConnectedElements();
    }
    
    @Override
    public void checkConnectedElements() {
        ArrayList<ComplexElement> elements = super.getConnectedElemenets();
        ArrayList<ComplexElement> removeElements = new ArrayList<ComplexElement>();
        for (int i = 0; i < elements.size(); i++) {
            if((elements.get(i).getX() > super.x1 || (elements.get(i).getX()+elements.get(i).getWidth()) < super.x1)){
                elements.get(i).setConnectedToVerticalMagnet(false);
                removeElements.add(elements.get(i));
            }            
        }
        super.getConnectedElemenets().removeAll(removeElements);
    }
    
    @Override
    public void unConnectElementsFromMagneticLine(){
        for (ComplexElement complexElement : this.getConnectedElemenets()) {
            complexElement.setConnectedToVerticalMagnet(false);
        }
    }
    
    @Override
    public String toString(){
        return "Vertical magnetic line";
    }


}
