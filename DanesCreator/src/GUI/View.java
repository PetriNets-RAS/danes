/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Core.Arc;
import Core.Element;
import Core.Graph;
import Core.Node;
import Core.PetriNet;
import Core.Place;
import Core.PrecedenceGraph;
import Core.Transition;
import FileManager.CoBA_XMLManager;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

/**
 *
 * @author marek
 */
public class View extends javax.swing.JFrame {

    private Graph           graph;
    private DiagramPanel    diagramPanel;
    
    private Controller  controller;
    private AboutUs about;
    private PetriNet p;
    //private PrecedenceGraph p;
    
    public View(PetriNet pa_petriNet,Controller pa_controller) {        
        super();  
        this.graph          =pa_petriNet;  
        this.controller     =pa_controller;
        this.diagramPanel   =null;
        initComponents();
        
        about = new AboutUs(this, rootPaneCheckingEnabled);
        String IconPath="..\\DanesCreator\\Images\\icon.png";
        BufferedImage icon = null;
        try{
            File iconFile = new File(IconPath);
            icon = ImageIO.read(iconFile);
        } 
        catch (IOException e){
            System.out.print("Image was not found");
        }
        
        this.setIconImage(icon);
        // hide side panels
        sideMenu.setVisible(false);
        propertiesMenu.setVisible(false);
        // Custom init 
        setTitle("DANES Creator");
       /* setSize(800, 600); */
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
        lineButton = new javax.swing.JToggleButton();
        propertiesMenu = new javax.swing.JPanel();
        specificPropertiesMenu = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        propertiesParentMenu1 = new GUI.PropertiesParentMenu();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        diagramScrollPane = new javax.swing.JScrollPane();
        topMenu = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newProjectItem = new javax.swing.JMenuItem();
        saveItem = new javax.swing.JMenuItem();
        saveAsItem = new javax.swing.JMenuItem();
        loadItem = new javax.swing.JMenuItem();
        exitItem = new javax.swing.JMenuItem();
        editMenuItem = new javax.swing.JMenu();
        aboutUs = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        sideMenu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        sideMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ellipseButton.setIcon(new javax.swing.ImageIcon("..\\DanesCreator\\Images\\EllipseIcon.png"));
        ellipseButton.setToolTipText("Pridat miesto");
        ellipseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ellipseButtonActionPerformed(evt);
            }
        });
        sideMenu.add(ellipseButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 37, 82, 23));

        rectangleButton.setIcon(new javax.swing.ImageIcon("..\\DanesCreator\\Images\\RectangleIcon.png"));
        rectangleButton.setToolTipText("pridate priechod");
        rectangleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rectangleButtonActionPerformed(evt);
            }
        });
        sideMenu.add(rectangleButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 71, 82, 23));

        lineButton.setIcon(new javax.swing.ImageIcon("..\\DanesCreator\\Images\\LineIcon.png"));
        lineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lineButtonActionPerformed(evt);
            }
        });
        sideMenu.add(lineButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 2, 82, 24));

        javax.swing.GroupLayout specificPropertiesMenuLayout = new javax.swing.GroupLayout(specificPropertiesMenu);
        specificPropertiesMenu.setLayout(specificPropertiesMenuLayout);
        specificPropertiesMenuLayout.setHorizontalGroup(
            specificPropertiesMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 340, Short.MAX_VALUE)
        );
        specificPropertiesMenuLayout.setVerticalGroup(
            specificPropertiesMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 340, Short.MAX_VALUE)
        );

        jTabbedPane1.setBackground(new java.awt.Color(204, 204, 204));
        jTabbedPane1.addTab("Properties", propertiesParentMenu1);

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTabbedPane1.addTab("Notes", jScrollPane1);

        javax.swing.GroupLayout propertiesMenuLayout = new javax.swing.GroupLayout(propertiesMenu);
        propertiesMenu.setLayout(propertiesMenuLayout);
        propertiesMenuLayout.setHorizontalGroup(
            propertiesMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(propertiesMenuLayout.createSequentialGroup()
                .addGroup(propertiesMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(specificPropertiesMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        propertiesMenuLayout.setVerticalGroup(
            propertiesMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(propertiesMenuLayout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(specificPropertiesMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Properties");

        sideMenu.add(propertiesMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 105, 240, -1));

        diagramScrollPane.setBorder(null);
        diagramScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        fileMenu.setText("File");

        newProjectItem.setText("New net");
        newProjectItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newProjectItemActionPerformed(evt);
            }
        });
        fileMenu.add(newProjectItem);

        saveItem.setText("Save");
        saveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveItem);

        saveAsItem.setText("Save as ...");
        saveAsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveAsItem);

        loadItem.setText("Load");
        loadItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadItemActionPerformed(evt);
            }
        });
        fileMenu.add(loadItem);

        exitItem.setText("Exit");
        exitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitItem);

        topMenu.add(fileMenu);

        editMenuItem.setText("Export");
        topMenu.add(editMenuItem);

        aboutUs.setText("About us");
        aboutUs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                aboutUsMouseClicked(evt);
            }
        });
        topMenu.add(aboutUs);

        setJMenuBar(topMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(diagramScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sideMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(diagramScrollPane)
                    .addComponent(sideMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)))
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
       
        //Create and display new panel
         //Petri Net ukazka ************************************************
        p=new PetriNet("Empty");        
         //umele pridanie siete
        Place a=new Place("a");
        a.setDiagramElement(new DiagramElement(4, 4));
        Transition b= new Transition("b");
        b.setDiagramElement(new DiagramElement(8, 4));
        Arc c=new Arc("c", b, a);
        Place p1=new Place("p1");
        p1.setDiagramElement(new DiagramElement(4, 2));
        Place p2=new Place("p2");        
        p2.setDiagramElement(new DiagramElement(4, 6));
        
        Transition t1= new Transition("t1");        
        t1.setDiagramElement(new DiagramElement(8, 2));
        Transition t2= new Transition("t2");
        t2.setDiagramElement(new DiagramElement(8, 6));        
        
        p.addPlace(a);
        p.addTransition(b);
        p.addArc(c);        
        p.addPlace(p1);
        p.addPlace(p2);
        p.addTransition(t1);
        p.addTransition(t2);
        // koniec umele pridanie siete
        //*********************************************************** 
        /*
        PrecedenceGraph p=new PrecedenceGraph("Test");
        Node n1=new Node("n1"); n1.setDiagramElement(new DiagramElement(4,2));
        Node n2=new Node("n2"); n2.setDiagramElement(new DiagramElement(4,4));
        Node n3=new Node("n3"); n3.setDiagramElement(new DiagramElement(4,6));
        
        Arc a1=new Arc("a1", n2, n1);
        Arc a2=new Arc("a2", n2, n3);

        p.addNode(n1);
        p.addNode(n2);
        p.addNode(n3);
        p.addArc(a1);
        p.addArc(a2);
        */
        controller.setModel(p);
        this.diagramPanel   =   new DiagramPanel(p);
        diagramScrollPane.setViewportView(this.diagramPanel);
        
        sideMenu.setVisible(true);
        // hide side menu
        propertiesMenu.setVisible(false);
    }//GEN-LAST:event_newProjectItemActionPerformed

    private void aboutUsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aboutUsMouseClicked
        //AboutUs about = new AboutUs(this, rootPaneCheckingEnabled);
        about.setVisible(true);
    }//GEN-LAST:event_aboutUsMouseClicked

    private void ellipseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ellipseButtonActionPerformed
        rectangleButton.setSelected(false);
        lineButton.setSelected(false);
    }//GEN-LAST:event_ellipseButtonActionPerformed

    private void rectangleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rectangleButtonActionPerformed
        ellipseButton.setSelected(false);
        lineButton.setSelected(false);
    }//GEN-LAST:event_rectangleButtonActionPerformed

    private void lineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lineButtonActionPerformed
        ellipseButton.setSelected(false);
        rectangleButton.setSelected(false);
    }//GEN-LAST:event_lineButtonActionPerformed

    private void loadItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadItemActionPerformed
        // TODO add your handling code here:
        File inputFile=new File("C:\\file.xml");
        FileManager.CoBA_XMLManager x = new CoBA_XMLManager();
        
        p=x.getPetriNetFromXML(inputFile);
        
        controller.setModel(p);
        this.diagramPanel   =   new DiagramPanel(p);
        diagramScrollPane.setViewportView(this.diagramPanel);
        
        sideMenu.setVisible(true);
        // hide side menu
        propertiesMenu.setVisible(false);
        ///
    }//GEN-LAST:event_loadItemActionPerformed

    private void saveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveItemActionPerformed
        // TODO add your handling code here:
        FileManager.CoBA_XMLManager newXML=new CoBA_XMLManager();
        newXML.createPetriXML(p,new File("C:\\file.xml"));       
    }//GEN-LAST:event_saveItemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu aboutUs;
    private javax.swing.JScrollPane diagramScrollPane;
    private javax.swing.JMenu editMenuItem;
    private javax.swing.JToggleButton ellipseButton;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JToggleButton lineButton;
    private javax.swing.JMenuItem loadItem;
    private javax.swing.JMenuItem newProjectItem;
    private javax.swing.JPanel propertiesMenu;
    private GUI.PropertiesParentMenu propertiesParentMenu1;
    private javax.swing.JToggleButton rectangleButton;
    private javax.swing.JMenuItem saveAsItem;
    private javax.swing.JMenuItem saveItem;
    private javax.swing.JPanel sideMenu;
    private javax.swing.JPanel specificPropertiesMenu;
    private javax.swing.JMenuBar topMenu;
    // End of variables declaration//GEN-END:variables
class DiagramPanel extends javax.swing.JPanel {

    private int                     elementWidth;
    private DiagramMouseAdapter     mouseAdapter;    
    
    private Graph                   graph;
    
    private Graphics2D              g2d;
    
    private Object                  draggedObject;
    private Color                   draggedColor;
    /**
     * Creates new form GraphPanel
     */
    
    public DiagramPanel(Graph pa_graph) {                      
        this.graph=pa_graph;
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
        
        drawGraph();
        drawDraggedObject();

    }
   
    public void drawPlace(int column,int row){
        // Place / Ring
        g2d.setColor(new Color(0, 0, 0));
        g2d.fill(new Ellipse2D.Double(column*elementWidth+5,row*elementWidth+5,elementWidth-10,elementWidth-10));        
    }

    public void drawTransition(int column,int row){
        // Transition / Rectangle
        g2d.setColor(new Color(0, 0, 0));
        g2d.fill(new Rectangle2D.Float(column*elementWidth+12,row*elementWidth+5,25,elementWidth-10));                
    }

    public void drawArrow(int x1, int y1, int x2, int y2, String type) 
    {
         // Size of arrow in px
         int ARR_SIZE=8;

         double dx = x2 - x1;
         double dy = y2 - y1;
         double angle = Math.atan2(dy, dx);
         int len = (int) Math.sqrt(dx*dx + dy*dy);
         AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
         at.concatenate(AffineTransform.getRotateInstance(angle));

         // Save and Rotate
         AffineTransform oldTransform = g2d.getTransform();
         g2d.transform(at);                


         // Draw horizontal arrow starting in (0, 0)
         // Length decrease by X pixels if type of arrow is short
         if (type=="short")
            len=len-20;
         
         g2d.drawLine(0, 0, len-5, 0);
         g2d.fillPolygon(new int[]   {len, len-ARR_SIZE  , len-ARR_SIZE    , len},
                         new int[]     {0  , -ARR_SIZE     , ARR_SIZE      , 0}, 4 );
         // Retract old
         g2d.setTransform(oldTransform);
     }

    public void drawArc(int column1,int row1,int column2,int row2){        
        // Arc / Arrow
        g2d.setColor(new Color(27,161,226)); // Windows8Blue
        g2d.setStroke(new BasicStroke(3));        
        // Draw arrow        
        drawArrow( column1*elementWidth+elementWidth/2  ,row1*elementWidth+elementWidth/2,
                   column2*elementWidth+elementWidth/2,  row2*elementWidth+elementWidth/2,"short");

    }    
    
    
   
    public void drawGraph()                  
    {
        if (graph==null)
            return;
        if (graph instanceof PetriNet)
        {
            // Arcs , Places, Transitions
            // Draw all arcs
            for(Element e:((PetriNet)graph).getListOfArcs())
            {     
                DiagramElement in  =((Arc)e).getInElement().getDiagramElement();
                DiagramElement out =((Arc)e).getOutElement().getDiagramElement();

                drawArc(out.getX(),out.getY(),in.getX(),in.getY());
            }  

            // Draw all places
            for(Element e:((PetriNet)graph).getListOfPlaces())
            {            
                drawPlace(e.getDiagramElement().getX(), e.getDiagramElement().getY());
            }

            // Draw all transitions
            for(Element e:((PetriNet)graph).getListOfTransitions())
            {            
                drawTransition(e.getDiagramElement().getX(), e.getDiagramElement().getY());
            }      
            
            return;
        }   // Koniec Petri net

        if (graph instanceof PrecedenceGraph)
        {
            // Arcs, Nodes
            
            // Draw all arcs
            for(Element e:((PrecedenceGraph)graph).getListOfArcs())
            {     
                DiagramElement in  =((Arc)e).getInElement().getDiagramElement();
                DiagramElement out =((Arc)e).getOutElement().getDiagramElement();

                drawArc(out.getX(),out.getY(),in.getX(),in.getY());
            }  

            // Draw all nodes
            for(Element e:((PrecedenceGraph)graph).getListOfNodes())
            {            
                drawPlace(e.getDiagramElement().getX(), e.getDiagramElement().getY());
            }  
            
            return;
        }   // Koniec Precedence Graph
        
    }
    
    private void drawDraggedObject() {
            // Nothing to draw
            if (draggedObject==null)
                return;
            
            g2d.setColor(draggedColor);
            
            // Rectangle 
            if (draggedObject instanceof Rectangle2D)
            {
                int x=(int) ((Rectangle2D)draggedObject).getX();
                int y=(int) ((Rectangle2D)draggedObject).getY();    

                g2d.fill((Rectangle2D)draggedObject);
            }

            // Ellipse
            if (draggedObject instanceof Ellipse2D)
            {
                int x=(int) ((Ellipse2D)draggedObject).getX();
                int y=(int) ((Ellipse2D)draggedObject).getY();    

                g2d.fill((Ellipse2D)draggedObject);
            }
            
            if (draggedObject instanceof Line2D)
            {
                int x1=(int)((Line2D)draggedObject).getX1();
                int y1=(int)((Line2D)draggedObject).getY1();
                int x2=(int)((Line2D)draggedObject).getX2();
                int y2=(int)((Line2D)draggedObject).getY2();
                
                drawArrow(x1, y1, x2, y2,"long");            
            }
        }    

    public void mouseLeftClick(int x, int y) {  
        
        // Get current selected element
        Element currentElement=controller.getLocationElement(x/elementWidth,y/elementWidth);
        if (currentElement==null)
            currentElement=controller.getLocationArc(x/elementWidth,y/elementWidth);
        
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // !!!!!!!!!!!!!! DOPLNIT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // Parameter place trans - instance of
        // if (currentElement!=null)
        // Dakypanel dakyPanel = new DakyPanel(controller.getLocationElement(x, y));        
        // dakyPanel.setVisible(true);
        
        // Place or Node     
        if (currentElement instanceof Place || currentElement instanceof Node)
        {   
            this.draggedColor=Color.GRAY;        
            this.draggedObject=new Ellipse2D.Float(x, y, elementWidth-10, elementWidth-10);
            propertiesMenu.setVisible(true);
        }
        // Transition
        if (currentElement instanceof Transition)
        {
            this.draggedColor=Color.GRAY;
            this.draggedObject=new Rectangle2D.Float(x, y, 25, elementWidth-10);
            propertiesMenu.setVisible(true);
        }

        // Arc
        if (currentElement instanceof Arc)
        {
            //this.draggedColor=Color.GRAY;
            //this.draggedObject=new Rectangle2D.Float(x, y, 25, elementWidth-10);
            propertiesMenu.setVisible(true);
        }
        
        
        // Arc  create
        if ((currentElement instanceof Transition || currentElement instanceof Place || currentElement instanceof Node)
                &&  lineButton.isSelected()  )
        {
            this.draggedColor=Color.GRAY;
            this.draggedObject=new Line2D.Float(x,y,x,y);
        }
        
        if (currentElement == null)
        {
            propertiesMenu.setVisible(false);
        }
        
        // None exist or not creating mode
        if (currentElement==null            || 
            rectangleButton.isSelected()    || 
            ellipseButton.  isSelected()    //||
//            lineButton.     isSelected()                
            )
        {
            this.draggedObject=null;
        }           
        
        // Create new        
        // Creating new place
        if (ellipseButton.isSelected())
        {        
            int x_location=x/elementWidth;
            int y_location=y/elementWidth;
            if (graph instanceof PetriNet)
                controller.addPlace("Place",x_location,y_location);
            if (graph instanceof PrecedenceGraph)
                controller.addNode("Node",x_location,y_location);
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
        // Create RED shadow line indicating deletion of arc
        controller.deleteElement(x/elementWidth,y/elementWidth);
        controller.deleteArc(x/elementWidth,y/elementWidth);
        repaint();
        /*Element currentElement=controller.getLocationElement(x/elementWidth,y/elementWidth);
        if (currentElement!=null)
            controller.deleteElement(x, y);*/
        // Arc 
        /*if (currentElement!=null)//  &&  lineButton.isSelected()  )
        {
            this.draggedColor=Color.RED;
            this.draggedObject=new Line2D.Float(x,y,x,y);
        }  */
        
        // Not reapinting ! Deleting single item
        //repaint();
    }

    private void mouseLeftDragged(int x, int y) {
        // None
        if (draggedObject==null)
            return;

        // Rectangle
        if (draggedObject instanceof Rectangle2D)
            draggedObject=new Rectangle2D.Float(x, y, 25, elementWidth-10);
        
        // Ellipse
        if (draggedObject instanceof Ellipse2D)
            draggedObject=new Ellipse2D.Float(x, y, elementWidth-10, elementWidth-10);       
        
        // Line
        if (draggedObject instanceof Line2D)
        {
            int x1=(int)((Line2D)draggedObject).getX1();
            int y1=(int)((Line2D)draggedObject).getY1();                
            draggedObject=new Line2D.Float(x1,y1,x,y);
        }            

        repaint();
    }
      
    private void mouseRightDragged(int x, int y) {
        // Line
        /*if (draggedObject instanceof Line2D)
        {
            int x1=(int)((Line2D)draggedObject).getX1();
            int y1=(int)((Line2D)draggedObject).getY1();                
            draggedObject=new Line2D.Float(x1,y1,x,y);
        }  
        repaint();     */   
    }    
    
    public void mouseLeftReleased(int x_old, int y_old, int x_new, int y_new) {
        // Old and current positions
        int x_old_location=x_old/elementWidth;
        int y_old_location=y_old/elementWidth;            
        int x_new_location=x_new/elementWidth;
        int y_new_location=y_new/elementWidth;
        
        // Move place / transition
        if (!ellipseButton.isSelected() && !rectangleButton.isSelected() && !lineButton.isSelected())
        {                    
            controller.moveElement(x_old_location,y_old_location,x_new_location,y_new_location);
        }   
        
        // Add arc
        if (lineButton.isSelected() && draggedObject instanceof Line2D)
        {
            controller.addArc("Arc", x_old_location, y_old_location, x_new_location, y_new_location);
        }
        draggedObject=null;
        repaint();
    }

    private void mouseRightReleased(int x_old, int y_old, int x_new, int y_new)
    {
        /*
        // Old and current positions
        int x_old_location=x_old/elementWidth;
        int y_old_location=y_old/elementWidth;            
        int x_new_location=x_new/elementWidth;
        int y_new_location=y_new/elementWidth;
        
        // Select existing 
        // Same location - delete element
        if (x_old_location == x_new_location && y_old_location == y_new_location)
        {
            controller.deleteElement(x_new_location,y_new_location);
        }
        
        // Delete arc
        {
            //controller.deleteArc(x_old_location, y_old_location, x_new_location, y_new_location);            
        }
        draggedObject=null;
        repaint();*/
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
      // Old location is different from current        
      //if (x != e.getX()   ||     y != e.getY())
      if(true)
      {
            // Left
            if (SwingUtilities.isLeftMouseButton  (e) )
            {
                  diagramPanel.mouseLeftReleased(x,y,e.getX(),e.getY());
            }      
            // Right
            if (SwingUtilities.isRightMouseButton(e) )
            {
                  //diagramPanel.mouseRightReleased(x,y,e.getX(),e.getY());
            }      
      }
    }
    
     
    @Override
    public void mouseDragged(MouseEvent e) 
    {
        // Left
        if (SwingUtilities.isLeftMouseButton  (e) ) 
        {
            diagramPanel.mouseLeftDragged(e.getX(),e.getY());    
        }      
        // Right - but left functionality
        if (SwingUtilities.isRightMouseButton(e) ) 
        {
            //diagramPanel.mouseLeftDragged(e.getX(),e.getY());    
            diagramPanel.mouseRightDragged(e.getX(),e.getY());    
        }                
    }    
  }

}
