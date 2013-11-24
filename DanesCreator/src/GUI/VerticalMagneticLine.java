/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Graphics2D;

/**
 *
 * @author MISO
 */
public class VerticalMagneticLine extends MagneticLine{
    public VerticalMagneticLine(Graphics2D pG2d, int pX){
        super(pG2d);
        super.y = 0;
        super.x = pX;
    }
    
    @Override
    public void drawMagneticLine(int height) {
        super.g2d.drawLine(super.x, super.y, super.x, height);
    }
    
}
