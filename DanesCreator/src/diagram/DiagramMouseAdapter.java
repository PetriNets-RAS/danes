/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diagram;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

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
      
 
    
       
      if (SwingUtilities.isLeftMouseButton  (e) ) {
            graphTest.pridajMiesto(x, y);
        }

      if (SwingUtilities.isRightMouseButton   (e) ) {
            graphTest.pridajHranu(x, y);
        }
      /*if (SwingUtilities.isMiddleMouseButton  (e) )
          System.out.println("stredny "+x+" "+y);*/
      
      
      graphTest.repaint();
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
    
