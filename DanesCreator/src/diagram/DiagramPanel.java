/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package danescreator.diagram;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author marek
 */
public class DiagramPanel extends javax.swing.JPanel {

    private int                 rozmerPrvku;
    private DiagramMouseAdapter   mouseAdapter;    
    /**
     * Creates new form GraphPanel
     */
    public DiagramPanel() {                      
        // Max sirka,vyska = 1000x1000
        this.rozmerPrvku    =   50; // 50 Px jeden prvok
        this.mouseAdapter   =   new DiagramMouseAdapter(this); 
                
        // Listeneri
       // addMouseMotionListener(mouseAdapter);
        addMouseListener(mouseAdapter);
        
        setPreferredSize(new Dimension(1000, 1000));
        setBackground(Color.WHITE);
    }
    
    @Override
    public void paint(Graphics g) 
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        
        nakresliMiesto(g2d, 2, 2);
        nakresliPrechod(g2d, 3,2);
        
        nakresliMiesto  (g2d, 4, 2);
        nakresliPrechod (g2d, 5, 2);
        nakresliMiesto  (g2d, 6, 2);

    }
   
    public void nakresliMiesto(Graphics2D g2d,int stlpec,int riadok){
        // Miesto / Kruh
        g2d.setColor(new Color(0, 0, 0));
        g2d.fill(new Ellipse2D.Double(stlpec*rozmerPrvku,riadok*rozmerPrvku,rozmerPrvku,rozmerPrvku));        
    }

    public void nakresliPrechod(Graphics2D g2d,int stlpec,int riadok){
        // Prechod / Obdlznik
        g2d.setColor(new Color(0, 0, 0));
        g2d.fill(new Rectangle2D.Float(stlpec*rozmerPrvku+12,riadok*rozmerPrvku,25,rozmerPrvku));                
    }
    
    
    
    // Dorobit 
    // atribut siet
    public void nakresliSiet()                  {}
    
    public void pridajMiesto(int x,int y)       {}
    public void pridajHranu (int x, int y)      {}
}
