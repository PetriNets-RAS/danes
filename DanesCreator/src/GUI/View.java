/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Core.Element;
import Core.PetriNet;
import Core.Place;
import Core.Transition;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

/**
 *
 * @author marek
 */
public class View extends javax.swing.JFrame {

    private PetriNet        petriNet;
    private DiagramPanel    diagramPanel;
    
    private Controller  controller;
    
    public View(PetriNet pa_petriNet,Controller pa_controller) {        
        super();  
        this.petriNet       =pa_petriNet;  
        this.controller     =pa_controller;
        this.diagramPanel   =null;
        initComponents();
        
        // Custom init 
        setTitle("Danes Creator");
        setSize(800, 600); 
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sideMenu = new javax.swing.JPanel();
        ellipseButton = new javax.swing.JToggleButton();
        rectangleButton = new javax.swing.JToggleButton();
        diagramScrollPane = new javax.swing.JScrollPane();
        topMenu = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newProjectItem = new javax.swing.JMenuItem();
        saveItem = new javax.swing.JMenuItem();
        saveAsItem = new javax.swing.JMenuItem();
        exitItem = new javax.swing.JMenuItem();
        editMenuItem = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        sideMenu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ellipseButton.setText("m");
        ellipseButton.setToolTipText("Pridat miesto");

        rectangleButton.setText("p");
        rectangleButton.setToolTipText("pridate priechod");

        javax.swing.GroupLayout sideMenuLayout = new javax.swing.GroupLayout(sideMenu);
        sideMenu.setLayout(sideMenuLayout);
        sideMenuLayout.setHorizontalGroup(
            sideMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sideMenuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ellipseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rectangleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );
        sideMenuLayout.setVerticalGroup(
            sideMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideMenuLayout.createSequentialGroup()
                .addGroup(sideMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ellipseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rectangleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 351, Short.MAX_VALUE))
        );

        diagramScrollPane.setBorder(null);

        fileMenu.setText("File");

        newProjectItem.setText("Nova siet");
        newProjectItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newProjectItemActionPerformed(evt);
            }
        });
        fileMenu.add(newProjectItem);

        saveItem.setText("Uložiť");
        fileMenu.add(saveItem);

        saveAsItem.setText("Uložiť ako ...");
        saveAsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveAsItem);

        exitItem.setText("Koniec");
        exitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitItem);

        topMenu.add(fileMenu);

        editMenuItem.setText("Edit");
        topMenu.add(editMenuItem);

        setJMenuBar(topMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(diagramScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                .addGap(49, 49, 49)
                .addComponent(sideMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sideMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(diagramScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
        System.exit(0);        // TODO add your handling code here:
    }//GEN-LAST:event_exitItemActionPerformed

    private void saveAsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsItemActionPerformed
      JFileChooser fileChooser = new JFileChooser();
        int showOpenDialog = fileChooser.showSaveDialog(this);

        if (showOpenDialog != JFileChooser.APPROVE_OPTION) return; 
    }//GEN-LAST:event_saveAsItemActionPerformed

    private void newProjectItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newProjectItemActionPerformed
        // Create and display new panel
        PetriNet p=new PetriNet("Empty");
        controller.setModel(p);
        this.diagramPanel   =   new DiagramPanel(p);
        diagramScrollPane.setViewportView(this.diagramPanel);
    }//GEN-LAST:event_newProjectItemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane diagramScrollPane;
    private javax.swing.JMenu editMenuItem;
    private javax.swing.JToggleButton ellipseButton;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem newProjectItem;
    private javax.swing.JToggleButton rectangleButton;
    private javax.swing.JMenuItem saveAsItem;
    private javax.swing.JMenuItem saveItem;
    private javax.swing.JPanel sideMenu;
    private javax.swing.JMenuBar topMenu;
    // End of variables declaration//GEN-END:variables
class DiagramPanel extends javax.swing.JPanel {

    private int                     elementWidth;
    private DiagramMouseAdapter     mouseAdapter;    
    
    private PetriNet                petriNet;
    
    private Graphics2D              g2d;
    
    private Object                  draggedObject;
    /**
     * Creates new form GraphPanel
     */
    
    public DiagramPanel(PetriNet pa_petriNet) {                      
        this.petriNet=pa_petriNet;
        this.draggedObject=null;
        this.elementWidth    =   50; // 50 Px jeden prvok
        this.mouseAdapter   =   new DiagramMouseAdapter(); 
                
        // Click listener, drag listener
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);        
   
        // Max sirka,vyska = 1000x1000
        setPreferredSize(new Dimension(1000, 1000));
        setBackground(Color.WHITE);
    }
    
    @Override
    public void paint(Graphics g) 
    {
        super.paint(g);
        this.g2d = (Graphics2D) g;
        
        drawPetriNet();
        drawDraggedObject();

    }
   
    public void drawPlace(int stlpec,int riadok){
        // Place / Ring
        g2d.setColor(new Color(0, 0, 0));
        g2d.fill(new Ellipse2D.Double(stlpec*elementWidth,riadok*elementWidth,elementWidth,elementWidth));        
    }

    public void drawTransition(int stlpec,int riadok){
        // Transition / Rectangle
        g2d.setColor(new Color(0, 0, 0));
        g2d.fill(new Rectangle2D.Float(stlpec*elementWidth+12,riadok*elementWidth,25,elementWidth));                
    }
    
    
   
    public void drawPetriNet()                  
    {
        // Draw all places
        for(Element e:petriNet.getListOfPlaces())
        {            
            drawPlace(e.getDiagramElement().getX(), e.getDiagramElement().getY());
        }
        
        // Draw all transitions
        for(Element e:petriNet.getListOfTransitions())
        {            
            drawTransition(e.getDiagramElement().getX(), e.getDiagramElement().getY());
        }        
        
    }
    
    private void drawDraggedObject() {
            // Nothing to draw
            if (draggedObject==null)
                return;
            
            // Rectangle 
            if (draggedObject instanceof Rectangle2D)
            {
                int x=(int) ((Rectangle2D)draggedObject).getX();
                int y=(int) ((Rectangle2D)draggedObject).getY();    

                g2d.setColor(Color.GRAY);
                g2d.fill((Rectangle2D)draggedObject);
            }

            // Ellipse
            if (draggedObject instanceof Ellipse2D)
            {
                int x=(int) ((Ellipse2D)draggedObject).getX();
                int y=(int) ((Ellipse2D)draggedObject).getY();    

                g2d.setColor(Color.GRAY);
                g2d.fill((Ellipse2D)draggedObject);
            }
        }    

    public void mouseLeftClick(int x, int y) {  
        // Select existing     
        Element currentElement=controller.getLocationElement(x/elementWidth,y/elementWidth);
        // Place               
        if (currentElement instanceof Place)
        {   
            this.draggedObject=new Ellipse2D.Float(x, y, elementWidth, elementWidth);
        }
        // Transition
        if (currentElement instanceof Transition)
        {
            this.draggedObject=new Rectangle2D.Float(x, y, 25, elementWidth);            
        }
        // None exist or not creating mode
        if (currentElement==null            || 
            rectangleButton.isSelected()    || 
            ellipseButton.  isSelected()     )
        {
            this.draggedObject=null;
        }           
           
        // Create new        
        // Creating new place
        if (ellipseButton.isSelected())
        {        
            int x_location=x/elementWidth;
            int y_location=y/elementWidth;
            controller.addPlace("Place",x_location,y_location);
        }    
            
        // Creating new transition
        if (rectangleButton.isSelected())
        {        
            int x_location=x/elementWidth;
            int y_location=y/elementWidth;   
            controller.addTransition("Transition", x_location, y_location);                   
        }
        
        repaint();    
    }
    
    public void mouseRightClick(int x, int y) {  
        // Select existing           
        controller.deleteElement(x/elementWidth,y/elementWidth);
        repaint();
    }

    private void mouseLeftDragged(int x, int y) {
        // None
        if (draggedObject==null)
            return;

        // Rectangle
        if (draggedObject instanceof Rectangle2D)
            draggedObject=new Rectangle2D.Float(x, y, 25, elementWidth);
        
        // Ellipse
        if (draggedObject instanceof Ellipse2D)
            draggedObject=new Ellipse2D.Float(x, y, elementWidth, elementWidth);        

        repaint();
    }
      
    public void mouseLeftMove(int x_old, int y_old, int x_new, int y_new) {
        // No buttons for adding choosen
        if (!ellipseButton.isSelected() && !rectangleButton.isSelected())
        {        
            int x_old_location=x_old/elementWidth;
            int y_old_location=y_old/elementWidth;
            
            int x_new_location=x_new/elementWidth;
            int y_new_location=y_new/elementWidth;
            
            controller.moveElement(x_old_location,y_old_location,x_new_location,y_new_location);
        }   
        
        draggedObject=null;
        repaint();
    }

  

        

}


public class DiagramMouseAdapter extends MouseAdapter 
{
    private int x;
    private int y;

    public DiagramMouseAdapter()    {}
    
    @Override
    public void mousePressed(MouseEvent e) 
    {
      // Save current
      x = e.getX();
      y = e.getY();
      
      // Check for click button
      if (SwingUtilities.isLeftMouseButton  (e) ) {
        diagramPanel.mouseLeftClick(x,y);
        }
      if (SwingUtilities.isRightMouseButton   (e) ) {
        diagramPanel.mouseRightClick(x,y);
        
    }
      /*if (SwingUtilities.isMiddleMouseButton  (e) )
          System.out.println("stredny "+x+" "+y);*/
    }
    
    @Override
    public void mouseReleased(MouseEvent e)
    {
      // Object try to be moved LEFT click
      if (SwingUtilities.isLeftMouseButton  (e) )
      {
        if (x != e.getX() ||     y != e.getY())
        { 
            diagramPanel.mouseLeftMove(x,y,e.getX(),e.getY());
        }
      }
    }
    
     
    @Override
    public void mouseDragged(MouseEvent e) 
    {
        if (SwingUtilities.isLeftMouseButton  (e) ) 
        {
            diagramPanel.mouseLeftDragged(e.getX(),e.getY());    
        }        
    }    
  }

}
