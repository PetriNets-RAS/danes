/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diagram;

import Core.Arc;
import Core.Place;
import Core.PetriNet;
import Core.Transition;
import Core.Element;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author marek
 */
public class DiagramPanel extends javax.swing.JPanel {

    private int                     rozmerPrvku;
    private DiagramMouseAdapter     mouseAdapter;    
    
    private PetriNet             petrihoSiet;
    private DiagramPetrihoSiet      diagramPerihosieSiet;
    
    private Graphics2D              g2d;
    /**
     * Creates new form GraphPanel
     */
    
    public DiagramPanel(PetriNet pa_petrihoSiet) {                      
        this.petrihoSiet=pa_petrihoSiet;
        this.diagramPerihosieSiet=new DiagramPetrihoSiet();
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
        this.g2d = (Graphics2D) g;
        
        nakresliSiet();
        /*
        nakresliMiesto(2, 2);
        nakresliPrechod(3,2);
        
        nakresliMiesto  (4, 2);
        nakresliPrechod (5, 2);
        nakresliMiesto  (6, 2);

                */
    }
   
    public void nakresliMiesto(int stlpec,int riadok){
        // Place / Kruh
        g2d.setColor(new Color(0, 0, 0));
        g2d.fill(new Ellipse2D.Double(stlpec*rozmerPrvku,riadok*rozmerPrvku,rozmerPrvku,rozmerPrvku));        
    }

    public void nakresliPrechod(int stlpec,int riadok){
        // Transition / Obdlznik
        g2d.setColor(new Color(0, 0, 0));
        g2d.fill(new Rectangle2D.Float(stlpec*rozmerPrvku+12,riadok*rozmerPrvku,25,rozmerPrvku));                
    }
    
    
    
    // Dorobit 
    // atribut siet
    public void nakresliSiet()                  
    {
        // 
        //petrihoSiet.getListMiest();
        //petrihoSiet.getListPrechodov();
        for (DiagramPrvok x : diagramPerihosieSiet.getDiagramPrvky() ) 
        {
            if (x.getPrvok()instanceof Place)
            {
                nakresliMiesto(x.getX(), x.getY());
            }
            if (x.getPrvok()instanceof Transition)
            {
                nakresliPrechod(x.getX(), x.getY());
            }
            
        }
        
    }
    
    public void pridajMiesto(int x,int y)       
    {
        this.diagramPerihosieSiet.pridajDiagramPrvok(new DiagramPrvok(new Place("test"), x/rozmerPrvku, y/rozmerPrvku));
    }
    public void pridajHranu (int x, int y)      
    {
        this.diagramPerihosieSiet.pridajDiagramPrvok(new DiagramPrvok(new Transition("test"), x/rozmerPrvku, y/rozmerPrvku));
    }
}
