/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package danescreator.diagram;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author marek
 */
public class DiagramMouseAdapter extends MouseAdapter 
{
    DiagramPanel graphTest;
    private int x;
    private int y;

    public DiagramMouseAdapter(DiagramPanel p)
    {
        this.graphTest= p;
    }
    
    @Override
    public void mousePressed(MouseEvent e) 
    {
      // Zaznamenaj
      x = e.getX();
      y = e.getY();
      
      /*
      if (SwingUtilities.isLeftMouseButton  (e) )
          System.out.println("lavy "+x+" "+y);
      if (SwingUtilities.isRightMouseButton   (e) )
          System.out.println("pravy "+x+" "+y);
      if (SwingUtilities.isMiddleMouseButton  (e) )
          System.out.println("stredny "+x+" "+y);
      */
      
      //    kde som klikol;
      //    cim som klikol;
      
      // Pridaj miesto / hranu do Petriho siete
      
    }
/*
      @Override
    public void mouseDragged(MouseEvent e) 
    {
      int dx = e.getX() - x;
      int dy = e.getY() - y;

      if (myRect.getBounds2D().contains(x, y)) 
      {
        myRect.x += dx;
        myRect.y += dy;
        repaint();
      }
      x += dx;
      y += dy;
    }*/
  }
    
