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
import Core.Resource;
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
import java.util.ArrayList;
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
    private PrecedenceGraph pg;
    private File selectedFile;
    
    public View(PetriNet pa_petriNet,Controller pa_controller) {        
        super();  
        this.graph          =pa_petriNet;  
        this.controller     =pa_controller;
        this.diagramPanel   =null;
        initComponents();
        
        about = new AboutUs(this, rootPaneCheckingEnabled);
        String IconPath="Images\\icon.png";
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
        resuorceButton = new javax.swing.JToggleButton();
        propertiesMenu = new javax.swing.JPanel();
        specificPropertiesMenu = new javax.swing.JPanel();
        propertiesTab = new javax.swing.JTabbedPane();
        generalProperties = new GUI.PropertiesMenu();
        notes = new javax.swing.JTextArea();
        diagramScrollPane = new javax.swing.JScrollPane();
        topMenu = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newPetriNet = new javax.swing.JMenuItem();
        newPrecedenceNet = new javax.swing.JMenuItem();
        saveItem = new javax.swing.JMenuItem();
        saveAsItem = new javax.swing.JMenuItem();
        loadItem = new javax.swing.JMenuItem();
        exitItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        convert = new javax.swing.JMenuItem();
        export = new javax.swing.JMenuItem();
        aboutUs = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        sideMenu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        sideMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ellipseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/EllipseIcon.png"))); // NOI18N
        ellipseButton.setToolTipText("Pridat miesto");
        ellipseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ellipseButtonActionPerformed(evt);
            }
        });
        sideMenu.add(ellipseButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 82, 23));

        rectangleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/RectangleIcon.png"))); // NOI18N
        rectangleButton.setToolTipText("pridate priechod");
        rectangleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rectangleButtonActionPerformed(evt);
            }
        });
        sideMenu.add(rectangleButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 82, 23));

        lineButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lineIcon.png"))); // NOI18N
        lineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lineButtonActionPerformed(evt);
            }
        });
        sideMenu.add(lineButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 82, 24));

        resuorceButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Resources.png"))); // NOI18N
        resuorceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resuorceButtonActionPerformed(evt);
            }
        });
        sideMenu.add(resuorceButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 80, -1));

        propertiesMenu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                propertiesMenuFocusLost(evt);
            }
        });

        javax.swing.GroupLayout specificPropertiesMenuLayout = new javax.swing.GroupLayout(specificPropertiesMenu);
        specificPropertiesMenu.setLayout(specificPropertiesMenuLayout);
        specificPropertiesMenuLayout.setHorizontalGroup(
            specificPropertiesMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        specificPropertiesMenuLayout.setVerticalGroup(
            specificPropertiesMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 361, Short.MAX_VALUE)
        );

        propertiesTab.setBackground(new java.awt.Color(204, 204, 204));

        generalProperties.setFocusCycleRoot(true);
        generalProperties.setFocusTraversalPolicyProvider(true);
        generalProperties.setName(""); // NOI18N
        generalProperties.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                generalPropertiesFocusLost(evt);
            }
        });
        propertiesTab.addTab("Properties", generalProperties);

        notes.setColumns(20);
        notes.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        notes.setLineWrap(true);
        notes.setRows(5);
        notes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                notesFocusLost(evt);
            }
        });
        propertiesTab.addTab("Notes", notes);

        javax.swing.GroupLayout propertiesMenuLayout = new javax.swing.GroupLayout(propertiesMenu);
        propertiesMenu.setLayout(propertiesMenuLayout);
        propertiesMenuLayout.setHorizontalGroup(
            propertiesMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(propertiesMenuLayout.createSequentialGroup()
                .addGroup(propertiesMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(propertiesTab, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                    .addComponent(specificPropertiesMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 14, Short.MAX_VALUE))
        );
        propertiesMenuLayout.setVerticalGroup(
            propertiesMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(propertiesMenuLayout.createSequentialGroup()
                .addComponent(propertiesTab, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(specificPropertiesMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        propertiesTab.getAccessibleContext().setAccessibleName("Properties");

        sideMenu.add(propertiesMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 105, 240, -1));

        diagramScrollPane.setBorder(null);
        diagramScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        fileMenu.setText("File");

        newPetriNet.setText("Petri net");
        newPetriNet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newPetriNetActionPerformed(evt);
            }
        });
        fileMenu.add(newPetriNet);

        newPrecedenceNet.setText("Precedence graph");
        fileMenu.add(newPrecedenceNet);

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

        editMenu.setText("Edit");

        convert.setText("Convert");
        editMenu.add(convert);

        export.setText("Export");
        editMenu.add(export);

        topMenu.add(editMenu);

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
        selectedFile = fileChooser.getSelectedFile();
        if(!selectedFile.exists())
        {
            selectedFile = new File(selectedFile.getAbsolutePath());
            try
            {
            selectedFile.createNewFile();
            }catch(IOException e)
            {
                System.out.print("Chyba pri praci so suborom");
            }
        }
        FileManager.CoBA_XMLManager newXML=new CoBA_XMLManager();
        newXML.createPetriXML(p,selectedFile);
        
        if (showOpenDialog != JFileChooser.APPROVE_OPTION) return; 
    }//GEN-LAST:event_saveAsItemActionPerformed

    private void newPetriNetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newPetriNetActionPerformed
       
        //Create and display new panel
         //Petri Net ukazka ************************************************
        p=new PetriNet("Empty");        
         //umele pridanie siete
        Place a=new Place("a");
        a.setDiagramElement(new DiagramElement(500, 400));
        Transition b= new Transition("b");
        b.setDiagramElement(new DiagramElement(100, 400));
        Arc c=new Arc("c", b, a);
        Place p1=new Place("p1");
        p1.setDiagramElement(new DiagramElement(300, 200));
        Place p2=new Place("p2");        
        p2.setDiagramElement(new DiagramElement(400, 600));
        
        Transition t1= new Transition("t1");        
        t1.setDiagramElement(new DiagramElement(500, 200));
        Transition t2= new Transition("t2");
        t2.setDiagramElement(new DiagramElement(500, 600));        
        
        Resource r1=new Resource("r1");
        r1.setDiagramElement(new DiagramElement(100, 100));
        Arc a2=new Arc("a2", t1, r1);
        
        p.addResource(r1);
        p.addPlace(a);
        p.addTransition(b);
        p.addArc(c);       
        p.addArc(a2);
        p.addPlace(p1);
        p.addPlace(p2);
        p.addTransition(t1);
        p.addTransition(t2);
        // koniec umele pridanie siete
        //*********************************************************** 
        /*
        pg=new PrecedenceGraph("Test");
        Node n1=new Node("n1"); n1.setDiagramElement(new DiagramElement(4,2));
        Node n2=new Node("n2"); n2.setDiagramElement(new DiagramElement(4,4));
        Node n3=new Node("n3"); n3.setDiagramElement(new DiagramElement(4,6));
        
        Arc a1=new Arc("a1", n2, n1);
        Arc a2=new Arc("a2", n2, n3);

        pg.addNode(n1);
        pg.addNode(n2);
        pg.addNode(n3);
        pg.addArc(a1);
        pg.addArc(a2);
         
        controller.setModel(pg);
        this.diagramPanel   =   new DiagramPanel(pg);
        */
        
        controller.setModel(p);
        this.diagramPanel   =   new DiagramPanel(p);
        
     
        
        diagramScrollPane.setViewportView(this.diagramPanel);
        
        sideMenu.setVisible(true);
        // hide side menu
        propertiesMenu.setVisible(false);
    }//GEN-LAST:event_newPetriNetActionPerformed

    private void aboutUsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aboutUsMouseClicked
        //AboutUs about = new AboutUs(this, rootPaneCheckingEnabled);
        about.setVisible(true);
    }//GEN-LAST:event_aboutUsMouseClicked

    private void ellipseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ellipseButtonActionPerformed
        rectangleButton.setSelected(false);
        lineButton.setSelected(false);
        resuorceButton.setSelected(false);
    }//GEN-LAST:event_ellipseButtonActionPerformed

    private void rectangleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rectangleButtonActionPerformed
        ellipseButton.setSelected(false);
        lineButton.setSelected(false);
        resuorceButton.setSelected(false);
    }//GEN-LAST:event_rectangleButtonActionPerformed

    private void lineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lineButtonActionPerformed
        ellipseButton.setSelected(false);
        rectangleButton.setSelected(false);
        resuorceButton.setSelected(false);
    }//GEN-LAST:event_lineButtonActionPerformed

    private void loadItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadItemActionPerformed
        // TODO add your handling code here:
        JFileChooser jFileChooser = new JFileChooser();
        int openShowDialog = jFileChooser.showOpenDialog(this);
        
        selectedFile = jFileChooser.getSelectedFile();
        File inputFile=new File(selectedFile.getAbsolutePath());
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
        if(selectedFile==null)
        {
            JFileChooser fileChooser = new JFileChooser();  
            int showOpenDialog = fileChooser.showSaveDialog(this);
            selectedFile = fileChooser.getSelectedFile();
            if(!selectedFile.exists())
            {
                selectedFile = new File(selectedFile.getAbsolutePath());
                try
                {
                    selectedFile.createNewFile();
                }catch(IOException e)
                {
                    System.out.print("Chyba pri praci so suborom");
                }
            }
            newXML.createPetriXML(p,selectedFile);  
            if (showOpenDialog != JFileChooser.APPROVE_OPTION) return; 
        }
        else
        {    
            newXML.createPetriXML(p,new File(selectedFile.getAbsolutePath()));
        }
    }//GEN-LAST:event_saveItemActionPerformed

    private void notesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_notesFocusLost
       //currentElement.setNote(notes.getText());
       // System.out.print("notes");
    }//GEN-LAST:event_notesFocusLost

    private void propertiesMenuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_propertiesMenuFocusLost
        System.out.print("properties");        // TODO add your handling code here:
    }//GEN-LAST:event_propertiesMenuFocusLost

    private void generalPropertiesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_generalPropertiesFocusLost
        System.out.print("properties");        // TODO add your handling code here:
    }//GEN-LAST:event_generalPropertiesFocusLost

    private void resuorceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resuorceButtonActionPerformed
        lineButton.setSelected(false);
        rectangleButton.setSelected(false);
        ellipseButton.setSelected(false);// TODO add your handling code here:
    }//GEN-LAST:event_resuorceButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu aboutUs;
    private javax.swing.JMenuItem convert;
    private javax.swing.JScrollPane diagramScrollPane;
    private javax.swing.JMenu editMenu;
    private javax.swing.JToggleButton ellipseButton;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JMenuItem export;
    private javax.swing.JMenu fileMenu;
    private GUI.PropertiesMenu generalProperties;
    private javax.swing.JToggleButton lineButton;
    private javax.swing.JMenuItem loadItem;
    private javax.swing.JMenuItem newPetriNet;
    private javax.swing.JMenuItem newPrecedenceNet;
    private javax.swing.JTextArea notes;
    private javax.swing.JPanel propertiesMenu;
    private javax.swing.JTabbedPane propertiesTab;
    private javax.swing.JToggleButton rectangleButton;
    private javax.swing.JToggleButton resuorceButton;
    private javax.swing.JMenuItem saveAsItem;
    private javax.swing.JMenuItem saveItem;
    private javax.swing.JPanel sideMenu;
    private javax.swing.JPanel specificPropertiesMenu;
    private javax.swing.JMenuBar topMenu;
    // End of variables declaration//GEN-END:variables
class DiagramPanel extends javax.swing.JPanel {
    private DiagramMouseAdapter     mouseAdapter;    
    
    private Graph                   graph;
    
    private Graphics2D              g2d;
    private ArrayList<Element>      selectedElements;
    private Element                 draggedElement;
    private Object                  draggedObject;
    private Color                   draggedColor;
    /**
     * Creates new form GraphPanel
     */
    
    public DiagramPanel(Graph pa_graph) {                      
        this.graph=pa_graph;
        this.draggedObject=null;
        this.draggedElement=null;
        this.selectedElements=new ArrayList<Element>();
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
        drawSelectedElements();

    }
   
    public void drawPlace(int column,int row){
        drawPlace(column, row, Color.BLACK, Color.WHITE);          
    }
    public void drawPlace(int column,int row,Color c1, Color c2){
        // Place / Ring
        g2d.setColor(c2);
        g2d.fill(new Ellipse2D.Double(column+5,row+5,40,40));        
        g2d.setColor(c1);    
        g2d.draw(new Ellipse2D.Double(column+5,row+5,40,40));                
    }

    public void drawResource(int column,int row){
        drawPlace(column, row, Color.BLACK, Color.GRAY);          
    }
    public void drawResource(int column,int row,Color c1, Color c2){
        // Place / Ring
        g2d.setColor(c2); //white vypln
        g2d.fill(new Ellipse2D.Double(column+5,row+5,40,40));        
        g2d.setColor(c1); //black   
        g2d.draw(new Ellipse2D.Double(column+5,row+5,40,40));                
    }
    
    public void drawTransition(int column,int row){
        drawTransition(column, row ,Color.BLACK,Color.WHITE);
    }
    public void drawTransition(int column,int row,Color c1,Color c2){
        // Transition / Rectangle
        g2d.setColor(c2);
        g2d.fill(new Rectangle2D.Float(column+12,row+5,25,40));                
        g2d.setColor(c1);
        g2d.draw(new Rectangle2D.Float(column+12,row+5,25,40));                        
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
        drawArrow( column1+25  ,row1+25,
                   column2+25,  row2+25,"short");

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
            
            // Draw all resources
            for(Element e:((PetriNet)graph).getListOfResources())
            {            
                drawResource(e.getDiagramElement().getX(), e.getDiagramElement().getY());
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
            if (draggedObject==null && draggedElement==null)
                return;
            
            g2d.setColor(draggedColor);
            
            // Rectangle 
            if (draggedElement instanceof Transition)
            {                
                int x=draggedElement.getDiagramElement().getX();
                int y=draggedElement.getDiagramElement().getY();

                drawTransition(x, y, Color.GRAY, Color.WHITE);
                //g2d.fill((Rectangle2D)draggedObject);
            }

            // Ellipse white
            if (draggedElement instanceof Place || draggedElement instanceof Node || draggedElement instanceof Resource)                           
            {
                int x=draggedElement.getDiagramElement().getX();
                int y=draggedElement.getDiagramElement().getY();

                if (draggedElement instanceof Resource)
                    drawPlace(x, y, Color.GRAY, Color.gray);
                else
                    drawPlace(x, y, Color.GRAY, Color.WHITE);

                //g2d.fill((Ellipse2D)draggedObject);
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
        private void drawSelectedElements() {
            // Nothing to draw
            if (selectedElements.isEmpty())
                return;
            
            Element e=selectedElements.get(0);            
            drawPlace(e.getDiagramElement().getX(),e.getDiagramElement().getY(),Color.RED,Color.RED);
        }
    public void mouseLeftClick(int x, int y) {  
        // Select 1 element
        selectedElements.clear();
        Element e=controller.getLocationElement(x,y);
        Arc     a=controller.getLocationArc(x,y);
        if (e!=null)
            selectedElements.add(e);
        if (a!=null)
            selectedElements.add(a);
        
        // Create new
        if (selectedElements.isEmpty())
        {
               this.draggedObject=null;               
               
               // Place / Node
               if (ellipseButton.isSelected())
               {        
                   if (graph instanceof PetriNet)
                       controller.addPlace("Place",x,y);
                   if (graph instanceof PrecedenceGraph)
                       controller.addNode("Node",x,y);
               }    
               // Resource
               if (resuorceButton.isSelected())
               {        
                   if (graph instanceof PetriNet)
                       controller.addResource("Resource",x,y);        
                }
               // Transition
               if (rectangleButton.isSelected())
               {        
                   controller.addTransition("Transition", x, y);                   
               }                             
               
        }
        // Dragging preparation & create new arc
        else if (selectedElements.size()==1)
        {
            Element currentElement=selectedElements.get(0);
            
            // Creat new Arc
            if ((currentElement instanceof Transition || currentElement instanceof Place || currentElement instanceof Node || currentElement instanceof Resource)
                    &&  lineButton.isSelected()  )
            {
                this.draggedColor=Color.GRAY;
                this.draggedObject=new Line2D.Float(x,y,x,y);                
            }               
            else    
            
            // Place or Node or Resource or Transition
            if (currentElement instanceof Place || currentElement instanceof Node || currentElement instanceof Resource || currentElement instanceof Transition)
            {   
                this.draggedElement=currentElement;
                propertiesMenu.setVisible(true);
            }            

            // Arc
            if (currentElement instanceof Arc)
            {
                //this.draggedColor=Color.GRAY;
                //this.draggedObject=new Rectangle2D.Float(x, y, 25, elementWidth-10);
                propertiesMenu.setVisible(true);
            }
        }
        
        
        
       
        if (selectedElements.isEmpty())
            propertiesMenu.setVisible(false);
        repaint();    
    }
    
    public void mouseRightClick(int x, int y) {  
        // Create RED shadow line indicating deletion of arc
        controller.deleteElement(x,y);
        controller.deleteArc(x,y);
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
        // None element or arc
        if (draggedObject==null && draggedElement==null)
            return;        
        // Element
        if (draggedElement instanceof Place || draggedElement instanceof Node || draggedElement instanceof Resource ||
                draggedElement instanceof Transition)
        {
            draggedElement.getDiagramElement().setX(x);
            draggedElement.getDiagramElement().setY(y);
        }
            //draggedObject=new Rectangle2D.Float(x, y, 25, 40);
        
        // Ellipse
       /* if (draggedObject instanceof Ellipse2D)
            draggedObject=new Ellipse2D.Float(x, y, 40, 40);       
        */
        // Rectangle
        /*if (draggedObject instanceof Rectangle2D)
            draggedObject=new Rectangle2D.Float(x, y, 25, 40);
        
        // Ellipse
        if (draggedObject instanceof Ellipse2D)
            draggedObject=new Ellipse2D.Float(x, y, 40, 40);       
        */
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
        
        // Move place / transition
        // && resource isSelected
        if (!ellipseButton.isSelected() && !rectangleButton.isSelected() && !lineButton.isSelected())
        {                    
            controller.moveElement(x_old,y_old,x_new,y_new);
        }   
        
        // Add arc
        if (lineButton.isSelected() && draggedObject instanceof Line2D)
        {
            controller.addArc("Arc", x_old, y_old, x_new, y_new);
        }
        
        draggedObject=null;
        draggedElement=null;
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

        private void loadElementProperties(Element currentElement) 
        {
            generalProperties.loadProperties(currentElement);
            notes.setText(currentElement.getNote());
            /*
            generalProperties.setElementName(currentElement.getName());
            generalProperties.setElementFontSize(Integer.toString(currentElement.getFontSize()));
            notes.setText(currentElement.getNote());
            if(currentElement instanceof AbsPlace)
            {
                AbsPlace current= (AbsPlace) currentElement;
                generalProperties.setElementWidth(Integer.toString(current.getWidth()));
                generalProperties.setElementHeight(Integer.toString(current.getHeight()));
            }
            if(currentElement instanceof Transition)
            {
                Transition current= (Transition) currentElement;
                generalProperties.setElementWidth(Integer.toString(current.getWidth()));
                generalProperties.setElementHeight(Integer.toString(current.getHeight()));
            }
            */ 
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
