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
public class HorizontalMagneticLine extends MagneticLine{
    public HorizontalMagneticLine(Graphics2D pG2d,int pY){
        super(pG2d);
        super.x = 0;
        super.y = pY;
    }

    @Override
    public void drawMagneticLine(int width) {
        super.g2d.drawLine(super.x, super.y, width, super.y);
    }
    
}
