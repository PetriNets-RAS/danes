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
public class HorizontalMagneticLine extends MagneticLine{
    public HorizontalMagneticLine(Graphics2D pG2d,int pY){
        super(pG2d);
        super.x1 = 0;
        super.y1 = pY;
    }
    
    public HorizontalMagneticLine(int pY,int pWidth){
        super.x1 = 0;
        super.x2 = pWidth;
        super.y1 = pY;
        super.y2 = pY;
    }

    
    @Override
    public void drawMagneticLine(int width) {
        super.g2d.drawLine(super.x1, super.y1, width, super.y1);
    }

    @Override
    public void addElementsToMagneticLine(ArrayList<ComplexElement> elements) {
        for (ComplexElement complexElement : elements) {
            if(!complexElement.isConnectedToHorizontalMagnet() && (complexElement.getY() <= super.y1 && (complexElement.getY()+complexElement.getHeight())>= super.y1)){
                super.addElement(complexElement);
                complexElement.setConnectedToHorizontalMagnet(true);
                int halfHeight =(int)(complexElement.getHeight()/2);
                int length = super.y1 - halfHeight;
                complexElement.setY(length);
            }
        }
        this.checkConnectedElements();
    }
    
    @Override
    public void checkConnectedElements() {
        ArrayList<ComplexElement> elements = super.getConnectedElemenets();
        ArrayList<ComplexElement> removeElements = new ArrayList<ComplexElement>();
        for (int i = 0; i < elements.size(); i++) {
            if((elements.get(i).getY() > super.y1 || (elements.get(i).getY()+elements.get(i).getHeight()) < super.y1)){
                elements.get(i).setConnectedToHorizontalMagnet(false);
                removeElements.add(elements.get(i));
            }            
        }
        super.getConnectedElemenets().removeAll(removeElements);
    }
    
    @Override
    public void unConnectElementsFromMagneticLine(){
        for (ComplexElement complexElement : this.getConnectedElemenets()) {
            complexElement.setConnectedToHorizontalMagnet(false);
        }
    }
    
    
    @Override
    public String toString(){
        return "Horiznotal magnetic line";
    }


    
    
}
