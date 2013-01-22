/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diagram;

import danescreator.Hrana;
import danescreator.Miesto;
import danescreator.PetrihoSiet;
import danescreator.Prechod;
import danescreator.Prvok;
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
    
    private PetrihoSiet             petrihoSiet;
    private DiagramPetrihoSiet      diagramPerihosieSiet;
    
    private Graphics2D              g2d;
    /**
     * Creates new form GraphPanel
     */
    
    public DiagramPanel(PetrihoSiet pa_petrihoSiet) {                      
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
        // Miesto / Kruh
        g2d.setColor(new Color(0, 0, 0));
        g2d.fill(new Ellipse2D.Double(stlpec*rozmerPrvku,riadok*rozmerPrvku,rozmerPrvku,rozmerPrvku));        
    }

    public void nakresliPrechod(int stlpec,int riadok){
        // Prechod / Obdlznik
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
            if (x.getPrvok()instanceof Miesto)
            {
                nakresliMiesto(x.getX(), x.getY());
            }
            if (x.getPrvok()instanceof Prechod)
            {
                nakresliPrechod(x.getX(), x.getY());
            }
            
            nakresliMiesto(2, 2);
        }
        
    }
    
    public void pridajMiesto(int x,int y)       
    {
        this.diagramPerihosieSiet.pridajDiagramPrvok(new DiagramPrvok(new Miesto("test"), x/rozmerPrvku, y/rozmerPrvku));
    }
    public void pridajHranu (int x, int y)      
    {
        this.diagramPerihosieSiet.pridajDiagramPrvok(new DiagramPrvok(new Prechod("test"), x/rozmerPrvku, y/rozmerPrvku));
    }
}
