/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Core.AbsPlace;
import Core.Arc;
import Core.Element;
import Core.Graph;
import Core.Node;
import Core.PetriNet;
import Core.Place;
import Core.PrecedenceGraph;
import Core.Resource;
import Core.Transition;
import FileManager.XMLPetriManager;
import FileManager.XMLPrecedenceManager;
import StateSpace.StateSpaceCalculator;
import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
//import sun.org.mozilla.javascript.internal.xmlimpl.XML;

/**
 *
 * @author marek
 */
public class View extends javax.swing.JFrame {

    private static final String APP_NAME = "Danes Creator";
    private static final String PETRI_NAME = "Petri net";
    private static final String PRECEDENCE_GRAPH = "Precedence graph";
    private static final String CUSTOM_HAND_CURSOR = "hand_cursor";
    private static final String CUSTOM_MOVE_CURSOR = "move_cursor";
    
    private Graph graph;
    private DiagramPanel diagramPanel;
    //private DiagramKeyListener diagramKeyListener;
    private Controller controller;
    private AboutUs about;
    //private Graph g;
    //private PetriNet p;
    //private PrecedenceGraph pg;
    private File selectedFile;
    private String selectedFileName;
    private String selectedFilePath;
    private AffineTransform oldTransform;
    private int lastMouseClickX,lastMouseClickY;
    private DefaultListModel listModel = new DefaultListModel();
   

    public View(PetriNet pa_petriNet, Controller pa_controller) {
        super();
        //this.setLocationRelativeTo(null);
        this.graph = pa_petriNet;
        this.controller = pa_controller;
        this.diagramPanel = null;
        initComponents();

        about = new AboutUs(this, rootPaneCheckingEnabled);
        String IconPath = "Images\\icon.png";
        BufferedImage icon = null;
        try {
            File iconFile = new File(IconPath);
            icon = ImageIO.read(iconFile);
        } catch (IOException e) {
            System.out.print("Image was not found");
        }

        this.setIconImage(icon);
        // hide side panels
        sideMenu.setVisible(false);
        propertiesMenu.setVisible(false);
        /* Hide zoom buttons */
//        btnZoomIn.setVisible(false);
//        btnZoomOut.setVisible(false);
//        btnZoomReset.setVisible(false);
        /* Hide align buttons */
        alignLeftButton.setVisible(false);
        alignRightButton.setVisible(false);
        alignTopButton.setVisible(false);
        alignBottomButton.setVisible(false);
        zoomInButton.setVisible(false);
        zoomOutButton.setVisible(false);
        resetZoomButton.setVisible(false);

        // Custom init 
        setTitle("DANES Creator");
        //setSize(800, 600); 
        setVisible(true);
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new ViewKeyEventDispatcher());

        setFocusable(true);
        setFocusableWindowState(true);
        //addKeyListener(diagramKeyListener);

        this.updateComponentList();
    }
    /* Class for keyMapping */

    private class ViewKeyEventDispatcher implements KeyEventDispatcher {

        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            /* React only to CTRL */
            if (diagramPanel != null) {
                if (KeyEvent.VK_CONTROL == e.getKeyCode()) {
                    if (e.getID() == KeyEvent.KEY_PRESSED) {
                        diagramPanel.isCTRLdown = true;
                    } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                        diagramPanel.isCTRLdown = false;
                    } else if (e.getID() == KeyEvent.KEY_TYPED) {
                        //diagramPanel.isCTRLdown=true;
                    }
                }
            }
            return false;
        }
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
        propertiesTab = new javax.swing.JTabbedPane();
        generalProperties = new GUI.PropertiesMenu();
        notes = new javax.swing.JTextArea();
        modelInfo = new javax.swing.JLabel();
        cursorButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        componentList = new javax.swing.JList();
        diagramScrollPane = new javax.swing.JScrollPane();
        toolBar = new javax.swing.JToolBar();
        alignTopButton = new javax.swing.JButton();
        alignBottomButton = new javax.swing.JButton();
        alignLeftButton = new javax.swing.JButton();
        alignRightButton = new javax.swing.JButton();
        zoomInButton = new javax.swing.JButton();
        zoomOutButton = new javax.swing.JButton();
        resetZoomButton = new javax.swing.JButton();
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
        create_state_diagram = new javax.swing.JMenuItem();
        aboutUs = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        sideMenu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        sideMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ellipseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/EllipseIcon.png"))); // NOI18N
        ellipseButton.setToolTipText("Add new place/node");
        ellipseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ellipseButtonActionPerformed(evt);
            }
        });
        sideMenu.add(ellipseButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 82, 23));

        rectangleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/RectangleIcon.png"))); // NOI18N
        rectangleButton.setToolTipText("Add new transiton");
        rectangleButton.setBorder(null);
        rectangleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rectangleButtonActionPerformed(evt);
            }
        });
        sideMenu.add(rectangleButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 82, 23));

        lineButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lineIcon.png"))); // NOI18N
        lineButton.setToolTipText("Add new arc");
        lineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lineButtonActionPerformed(evt);
            }
        });
        sideMenu.add(lineButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 82, 23));

        resuorceButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Resources.png"))); // NOI18N
        resuorceButton.setToolTipText("Add new resource");
        resuorceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resuorceButtonActionPerformed(evt);
            }
        });
        sideMenu.add(resuorceButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 82, 23));

        propertiesTab.setBackground(new java.awt.Color(204, 204, 204));

        generalProperties.setToolTipText("");
        generalProperties.setFocusCycleRoot(true);
        generalProperties.setFocusTraversalPolicyProvider(true);
        generalProperties.setName(""); // NOI18N
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
                .addComponent(propertiesTab, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 14, Short.MAX_VALUE))
        );
        propertiesMenuLayout.setVerticalGroup(
            propertiesMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(propertiesTab, javax.swing.GroupLayout.PREFERRED_SIZE, 294, Short.MAX_VALUE)
        );

        propertiesTab.getAccessibleContext().setAccessibleName("Properties");

        sideMenu.add(propertiesMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 105, 240, 290));
        sideMenu.add(modelInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 170, 20));

        cursorButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cursor.png"))); // NOI18N
        cursorButton.setToolTipText("Free move");
        cursorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cursorButtonActionPerformed(evt);
            }
        });
        sideMenu.add(cursorButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 50, -1));

        componentList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(componentList);

        sideMenu.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 226, 120));

        diagramScrollPane.setBorder(null);
        diagramScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        diagramScrollPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        diagramScrollPane.setWheelScrollingEnabled(false);
        diagramScrollPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                diagramScrollPaneMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                diagramScrollPaneMousePressed(evt);
            }
        });
        diagramScrollPane.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                diagramScrollPaneMouseDragged(evt);
            }
        });

        toolBar.setRollover(true);
        toolBar.setEnabled(false);

        alignTopButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/alignTop.png"))); // NOI18N
        alignTopButton.setToolTipText("Align Top");
        alignTopButton.setFocusable(false);
        alignTopButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        alignTopButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        alignTopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alignTopButtonActionPerformed(evt);
            }
        });
        toolBar.add(alignTopButton);

        alignBottomButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/alignBottom.png"))); // NOI18N
        alignBottomButton.setToolTipText("Align bottom");
        alignBottomButton.setFocusable(false);
        alignBottomButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        alignBottomButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        alignBottomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alignBottomButtonActionPerformed(evt);
            }
        });
        toolBar.add(alignBottomButton);

        alignLeftButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/alignLeft.png"))); // NOI18N
        alignLeftButton.setToolTipText("Align left");
        alignLeftButton.setFocusable(false);
        alignLeftButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        alignLeftButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        alignLeftButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alignLeftButtonActionPerformed(evt);
            }
        });
        toolBar.add(alignLeftButton);

        alignRightButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/alignRight.png"))); // NOI18N
        alignRightButton.setToolTipText("Align right");
        alignRightButton.setFocusable(false);
        alignRightButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        alignRightButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        alignRightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alignRightButtonActionPerformed(evt);
            }
        });
        toolBar.add(alignRightButton);

        zoomInButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/zoomIn.png"))); // NOI18N
        zoomInButton.setToolTipText("Zoom in");
        zoomInButton.setFocusable(false);
        zoomInButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zoomInButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        zoomInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomInButtonActionPerformed(evt);
            }
        });
        toolBar.add(zoomInButton);

        zoomOutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/zoomOut.png"))); // NOI18N
        zoomOutButton.setFocusable(false);
        zoomOutButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zoomOutButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        zoomOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomOutButtonActionPerformed(evt);
            }
        });
        toolBar.add(zoomOutButton);

        resetZoomButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/magnifier.png"))); // NOI18N
        resetZoomButton.setFocusable(false);
        resetZoomButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        resetZoomButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        resetZoomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetZoomButtonActionPerformed(evt);
            }
        });
        toolBar.add(resetZoomButton);

        fileMenu.setText("File");

        newPetriNet.setText("New Petri net");
        newPetriNet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newPetriNetActionPerformed(evt);
            }
        });
        fileMenu.add(newPetriNet);

        newPrecedenceNet.setText("New graph");
        newPrecedenceNet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newPrecedenceNetActionPerformed(evt);
            }
        });
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
        convert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                convertActionPerformed(evt);
            }
        });
        editMenu.add(convert);

        export.setText("Export");
        editMenu.add(export);

        create_state_diagram.setText("Create state diagram");
        create_state_diagram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                create_state_diagramActionPerformed(evt);
            }
        });
        editMenu.add(create_state_diagram);

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
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 298, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(diagramScrollPane)
                        .addGap(29, 29, 29)))
                .addComponent(sideMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(diagramScrollPane))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(sideMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)))
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
        selectedFile = fileChooser.getSelectedFile();
        if (!selectedFile.exists()) {
            selectedFile = new File(selectedFile.getAbsolutePath());
            try {
                selectedFile.createNewFile();
            } catch (IOException e) {
                System.out.print("Chyba pri praci so suborom");
            }
        }
        getInfoAboutFile(selectedFile);
        /*
         if (g instanceof PetriNet) {
         FileManager.XMLPetriManager newXML = new XMLPetriManager();
         newXML.createPetriXML(g, selectedFile);
         System.out.println("UKLADAM PN");
         } else {
         FileManager.XMLPrecedenceManager newXML = new XMLPrecedenceManager();
         newXML.createPrecedenceXML(g, selectedFile);
         }
         */
        if (showOpenDialog != JFileChooser.APPROVE_OPTION) {
            return;
        }
        checkAndSave(graph, selectedFile);
    }//GEN-LAST:event_saveAsItemActionPerformed

    private void getInfoAboutFile(File file) {
        selectedFileName = file.getName();
        selectedFilePath = file.getPath();
        this.setTitle(APP_NAME + " - " + selectedFileName);
    }

    private void getInfoAboutModel(Graph g) {
        if (g instanceof PetriNet) {
            modelInfo.setText(PETRI_NAME);
        } else {
            modelInfo.setText(PRECEDENCE_GRAPH);
        }
    }

    private void newPetriNetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newPetriNetActionPerformed

        //Create and display new panel
        PetriNet p = new PetriNet("New petri net");

        graph = p;
        controller.setModel(graph);
        this.diagramPanel = new DiagramPanel(graph);



        diagramScrollPane.setViewportView(this.diagramPanel);       
        diagramScrollPane.getViewport().setViewPosition(new java.awt.Point(4750,4750));
        
        sideMenu.setVisible(true);
        // hide side menu
        propertiesMenu.setVisible(false);

        // Zoom
//        btnZoomIn.setVisible(true);
//        btnZoomOut.setVisible(true);
//        btnZoomReset.setVisible(true);
        /* Show align buttons */
        alignLeftButton.setVisible(true);
        alignRightButton.setVisible(true);
        alignTopButton.setVisible(true);
        alignBottomButton.setVisible(true);
        zoomInButton.setVisible(true);
        zoomOutButton.setVisible(true);
        resetZoomButton.setVisible(true);
        rectangleButton.setVisible(true);
        resuorceButton.setVisible(true);
        cursorButton.setSelected(true);

        toolBar.setEnabled(true);
        getInfoAboutModel(graph);
    }//GEN-LAST:event_newPetriNetActionPerformed

    private void aboutUsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aboutUsMouseClicked
        //AboutUs about = new AboutUs(this, rootPaneCheckingEnabled);
        about.setVisible(true);
    }//GEN-LAST:event_aboutUsMouseClicked

    private void ellipseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ellipseButtonActionPerformed
        rectangleButton.setSelected(false);
        lineButton.setSelected(false);
        resuorceButton.setSelected(false);
        cursorButton.setSelected(false);
    }//GEN-LAST:event_ellipseButtonActionPerformed

    private void rectangleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rectangleButtonActionPerformed
        ellipseButton.setSelected(false);
        lineButton.setSelected(false);
        resuorceButton.setSelected(false);
        cursorButton.setSelected(false);
    }//GEN-LAST:event_rectangleButtonActionPerformed

    private void lineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lineButtonActionPerformed
        ellipseButton.setSelected(false);
        rectangleButton.setSelected(false);
        resuorceButton.setSelected(false);
        cursorButton.setSelected(false);
    }//GEN-LAST:event_lineButtonActionPerformed

    private void loadItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadItemActionPerformed
        // TODO add your handling code here:
        JFileChooser jFileChooser = new JFileChooser();
        int openShowDialog = jFileChooser.showOpenDialog(this);

        selectedFile = jFileChooser.getSelectedFile();
        File inputFile = new File(selectedFile.getAbsolutePath());

        int x,y;
        
        if ("dpn".equals(inputFile.getName().substring(inputFile.getName().length() - 3))) {
            FileManager.XMLPetriManager loader = new XMLPetriManager();
            PetriNet p = loader.getPetriNetFromXML(inputFile);
            graph = p;
            x=p.getListOfPlaces().get(0).getX();
            y=p.getListOfPlaces().get(0).getY();
        } else {
            FileManager.XMLPrecedenceManager loader = new XMLPrecedenceManager();
            PrecedenceGraph pg = loader.getPrecedenceFromXML(inputFile);
            graph = pg;
            x=pg.getListOfNodes().get(0).getX();
            y=pg.getListOfNodes().get(0).getY();
        }
        getInfoAboutFile(inputFile);
        controller.setModel(graph);
        this.diagramPanel = new DiagramPanel(graph);
        diagramScrollPane.setViewportView(this.diagramPanel);
        
        if((x-50)<0 || (y-50<0)){
            x=0;
            y=0;
        }
        
        diagramScrollPane.getViewport().setViewPosition(new java.awt.Point(x-50,y-50));

        sideMenu.setVisible(true);
        // hide side menu
        propertiesMenu.setVisible(false);

        /* Show zoom buttos */
//        btnZoomIn.setVisible(true);
//        btnZoomOut.setVisible(true);
//        btnZoomReset.setVisible(true);
        /* Show align buttons */
        alignLeftButton.setVisible(true);
        alignRightButton.setVisible(true);
        alignTopButton.setVisible(true);
        alignBottomButton.setVisible(true);
        zoomOutButton.setVisible(true);
        zoomInButton.setVisible(true);
        resetZoomButton.setVisible(true);

        ///
        getInfoAboutModel(graph);
        
        
        
    }//GEN-LAST:event_loadItemActionPerformed

    private void saveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveItemActionPerformed
        // TODO add your handling code here:
        FileManager.XMLPetriManager newXML = new XMLPetriManager();
        if (selectedFile == null) {
            JFileChooser fileChooser = new JFileChooser();
            int showOpenDialog = fileChooser.showSaveDialog(this);
            selectedFile = fileChooser.getSelectedFile();
            if (!selectedFile.exists()) {
                selectedFile = new File(selectedFile.getAbsolutePath());
                try {
                    selectedFile.createNewFile();
                } catch (IOException e) {
                    System.out.print("Chyba pri praci so suborom");
                }
            }
            if (showOpenDialog != JFileChooser.APPROVE_OPTION) {
                return;
            }
            checkAndSave(graph, selectedFile);
            //newXML.createPetriXML(g, selectedFile);
        } else {
            //newXML.createPetriXML(g, new File(selectedFile.getAbsolutePath()));
            checkAndSave(graph, selectedFile);
        }
        getInfoAboutFile(selectedFile);
    }//GEN-LAST:event_saveItemActionPerformed

    private void checkAndSave(Graph g, File selectFile) {
        String sufix;
        if (g instanceof PetriNet) {
            sufix = ".dpn";
            FileManager.XMLPetriManager newXML = new XMLPetriManager();

            if ((selectedFile.getName().length() < 5) || (!"dpn".equals(selectedFile.getName().substring(selectedFile.getName().length() - 3)))) {
                File temp = selectedFile;
                selectedFile = new File(selectedFile.getAbsolutePath() + sufix);
                temp.delete();
            }

            newXML.createPetriXML(g, selectedFile);
            System.out.println("UKLADAM PN");
        } else {
            sufix = ".dpg";

            FileManager.XMLPrecedenceManager newXML = new XMLPrecedenceManager();
            if ((selectedFile.getName().length() < 5) || (!"dpg".equals(selectedFile.getName().substring(selectedFile.getName().length() - 3)))) {
                File temp = selectedFile;
                selectedFile = new File(selectedFile.getAbsolutePath() + sufix);
                temp.delete();
            }
            newXML.createPrecedenceXML(g, selectedFile);
        }
        getInfoAboutFile(selectFile);
    }

    private void notesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_notesFocusLost
        //currentElement.setNote(notes.getText());
        // System.out.print("notes");
    }//GEN-LAST:event_notesFocusLost

    private void resuorceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resuorceButtonActionPerformed
        lineButton.setSelected(false);
        rectangleButton.setSelected(false);
        ellipseButton.setSelected(false);
        cursorButton.setSelected(false);
    }//GEN-LAST:event_resuorceButtonActionPerformed

    private void newPrecedenceNetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newPrecedenceNetActionPerformed
        selectedFile = null;
        PrecedenceGraph pg = new PrecedenceGraph("New precedence graph");
        graph = pg;
        controller.setModel(graph);
        this.diagramPanel = new DiagramPanel(graph);
        diagramScrollPane.setViewportView(this.diagramPanel);
        diagramScrollPane.getViewport().setViewPosition(new java.awt.Point(4750,4750));
        sideMenu.setVisible(true);
        // hide side menu
        propertiesMenu.setVisible(false);
        /* Show align and zoom buttons */
        alignLeftButton.setVisible(true);
        alignRightButton.setVisible(true);
        alignTopButton.setVisible(true);
        alignBottomButton.setVisible(true);
        zoomOutButton.setVisible(true);
        zoomInButton.setVisible(true);
        resetZoomButton.setVisible(true);
        cursorButton.setSelected(true);

        rectangleButton.setVisible(false);
        resuorceButton.setVisible(false);
        toolBar.setEnabled(true);
        getInfoAboutModel(graph);
    }//GEN-LAST:event_newPrecedenceNetActionPerformed

    private void convertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_convertActionPerformed
        PrecedenceGraph pg = (PrecedenceGraph) graph;
        PetriNet converted = pg.changePrecedenceGraphToPN();
        graph = converted;
        controller.setModel(converted);
        this.diagramPanel = new DiagramPanel(converted);
        diagramScrollPane.setViewportView(this.diagramPanel);
        sideMenu.setVisible(true);
        // hide side menu
        propertiesMenu.setVisible(false);
        getInfoAboutModel(graph);
        
        rectangleButton.setVisible(true);
        resuorceButton.setVisible(true);
    }//GEN-LAST:event_convertActionPerformed

    private void diagramScrollPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_diagramScrollPaneMouseClicked
    }//GEN-LAST:event_diagramScrollPaneMouseClicked

    private void diagramScrollPaneMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_diagramScrollPaneMousePressed
    }//GEN-LAST:event_diagramScrollPaneMousePressed

    private void create_state_diagramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_create_state_diagramActionPerformed
        if (graph == null) {
            return;
        }

        PetriNet ssPetriNet = null;
        if (graph instanceof PetriNet) {
            ssPetriNet = (PetriNet) graph;
        }
        if (graph instanceof PrecedenceGraph) {
            ssPetriNet = ((PrecedenceGraph) graph).changePrecedenceGraphToPN();
        }

        StateSpaceCalculator ssCalc = new StateSpaceCalculator(ssPetriNet);
        ssCalc.calculateStateSpace();
    }//GEN-LAST:event_create_state_diagramActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.resetForm();
        }
    }//GEN-LAST:event_formKeyPressed

    private void alignTopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alignTopButtonActionPerformed
        controller.alignTop(diagramPanel.selectedElements);
        repaint();
    }//GEN-LAST:event_alignTopButtonActionPerformed

    private void alignBottomButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alignBottomButtonActionPerformed
        controller.alignBottom(diagramPanel.selectedElements);
        repaint();
    }//GEN-LAST:event_alignBottomButtonActionPerformed

    private void alignLeftButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alignLeftButtonActionPerformed
        controller.alignLeft(diagramPanel.selectedElements);
        repaint();
    }//GEN-LAST:event_alignLeftButtonActionPerformed

    private void alignRightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alignRightButtonActionPerformed
        controller.alignRight(diagramPanel.selectedElements);
        repaint();
    }//GEN-LAST:event_alignRightButtonActionPerformed

    private void zoomInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomInButtonActionPerformed
        diagramPanel.scaleRatio[0] += 0.1;
        diagramPanel.scaleRatio[1] += 0.1;
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = 0;
        int maxY = 0;
        ArrayList<Element> elements = new ArrayList<Element>();
        if(this.graph instanceof PetriNet){
            PetriNet pn = (PetriNet)this.graph;
            elements = new ArrayList<Element>();
            elements.addAll(pn.getListOfArcs());
            elements.addAll(pn.getListOfPlaces());
            elements.addAll(pn.getListOfResources());
            elements.addAll(pn.getListOfTransitions());
        }
        if(this.graph instanceof PrecedenceGraph){
            PrecedenceGraph pg = (PrecedenceGraph)this.graph;
            elements = new ArrayList<Element>();
            elements.addAll(pg.getListOfArcs());
            elements.addAll(pg.getListOfNodes());
        }
        for (Element element : elements) {
            if(minX>element.getX()){
                minX = element.getX();
            }
            if(minY>element.getY()){
                minY = element.getY();
            }
            if(maxX<element.getX()){
                maxX = element.getX();
            }
            if(maxY<element.getY()){
                maxY = element.getY();
            }
        }
        //diagramPanel.scaleRatio[0] = 1;
        //diagramPanel.scaleRatio[1] = 1;
        /*
        System.out.println(diagramPanel.scaleRatio[0]+" - "+diagramPanel.scaleRatio[1]);
        this.diagramPanel.setAlignmentX(minX);
        this.diagramPanel.setAlignmentY(minY);
        this.diagramScrollPane.getViewport().setViewPosition(new java.awt.Point(minX,minY));
        */ 
        int graphWidth = maxX - minX;
        int graphHeight = maxY - minY;
        int maxGraphSize = Math.max(graphHeight, graphWidth);
        double graphSlant = Math.sqrt((maxGraphSize*maxGraphSize)+(maxGraphSize*maxGraphSize));
        
        int newX = (int) ((minX-(graphSlant/2))*this.diagramPanel.scaleRatio[0]);
        int newY = (int) ((minY-(graphSlant/2))*this.diagramPanel.scaleRatio[1]);
        /*
        int newX = (int) (minX*this.diagramPanel.scaleRatio[0]);
        int newY = (int) (minY*this.diagramPanel.scaleRatio[1]);
        */
        this.diagramScrollPane.getViewport().setViewPosition(new java.awt.Point(newX,newY));
        repaint();
    }//GEN-LAST:event_zoomInButtonActionPerformed

    private void zoomOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomOutButtonActionPerformed
        diagramPanel.scaleRatio[0] -= 0.1;
        diagramPanel.scaleRatio[1] -= 0.1;
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = 0;
        int maxY = 0;
        ArrayList<Element> elements = new ArrayList<Element>();
        if(this.graph instanceof PetriNet){
            PetriNet pn = (PetriNet)this.graph;
            elements = new ArrayList<Element>();
            elements.addAll(pn.getListOfArcs());
            elements.addAll(pn.getListOfPlaces());
            elements.addAll(pn.getListOfResources());
            elements.addAll(pn.getListOfTransitions());
        }
        if(this.graph instanceof PrecedenceGraph){
            PrecedenceGraph pg = (PrecedenceGraph)this.graph;
            elements = new ArrayList<Element>();
            elements.addAll(pg.getListOfArcs());
            elements.addAll(pg.getListOfNodes());
        }
        for (Element element : elements) {
            if(minX>element.getX()){
                minX = element.getX();
            }
            if(minY>element.getY()){
                minY = element.getY();
            }
            if(maxX<element.getX()){
                maxX = element.getX();
            }
            if(maxY<element.getY()){
                maxY = element.getY();
            }
        }
        int graphWidth = maxX - minX;
        int graphHeight = maxY - minY;
        int maxGraphSize = Math.max(graphHeight, graphWidth);
        double graphSlant = Math.sqrt((maxGraphSize*maxGraphSize)+(maxGraphSize*maxGraphSize));
        
        int newX = (int) ((minX-(graphSlant/2))*this.diagramPanel.scaleRatio[0]);
        int newY = (int) ((minY-(graphSlant/2))*this.diagramPanel.scaleRatio[1]);
        
        /*
        int newX = (int) (minX*this.diagramPanel.scaleRatio[0]);
        int newY = (int) (minY*this.diagramPanel.scaleRatio[1]);
        */
        this.diagramScrollPane.getViewport().setViewPosition(new java.awt.Point(newX,newY));
        repaint();
    }//GEN-LAST:event_zoomOutButtonActionPerformed

    private void resetZoomButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetZoomButtonActionPerformed
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = 0;
        int maxY = 0;
        Element minEX = null;
        Element minEY = null;
        Element maxEX = null;
        Element maxEY = null;
        ArrayList<Element> elements = new ArrayList<Element>();
        if(this.graph instanceof PetriNet){
            PetriNet pn = (PetriNet)this.graph;
            elements = new ArrayList<Element>();
            elements.addAll(pn.getListOfArcs());
            elements.addAll(pn.getListOfPlaces());
            elements.addAll(pn.getListOfResources());
            elements.addAll(pn.getListOfTransitions());
        }
        if(this.graph instanceof PrecedenceGraph){
            PrecedenceGraph pg = (PrecedenceGraph)this.graph;
            elements = new ArrayList<Element>();
            elements.addAll(pg.getListOfArcs());
            elements.addAll(pg.getListOfNodes());
        }
        for (Element element : elements) {
            if(minX>element.getX()){
                minX = element.getX();
                minEX = element;
            }
            if(minY>element.getY()){
                minY = element.getY();
                minEY = element;
            }
            if(maxX<element.getX()){
                maxX = element.getX();
                maxEX = element;
            }
            if(maxY<element.getY()){
                maxY = element.getY();
                maxEY = element;
            }
        }
        int graphWidth = maxX - minX;
        int graphHeight = maxY - minY;
        int maxGraphSize = Math.max(graphHeight, graphWidth);
        int minDiagramSize = Math.min(this.diagramScrollPane.getWidth(), this.diagramScrollPane.getHeight());
        double diagramSlant = Math.sqrt((minDiagramSize*minDiagramSize)+(minDiagramSize*minDiagramSize));
        double graphSlant = Math.sqrt((maxGraphSize*maxGraphSize)+(maxGraphSize*maxGraphSize));
        if(graphSlant > diagramSlant){
            this.diagramPanel.scaleRatio[1] = (diagramSlant/graphSlant) - 0.05;
            this.diagramPanel.scaleRatio[0] = (diagramSlant/graphSlant) - 0.05;
        }else{
            this.diagramPanel.scaleRatio[1] = 1;
            this.diagramPanel.scaleRatio[0] = 1;
        }
        System.out.println("Uhlopriecky "+ diagramSlant+" " +graphSlant);
        System.out.println("Diagram " + this.diagramScrollPane.getWidth()+" " +this.diagramScrollPane.getHeight());
        System.out.println("Graf" +"("+maxX+" - "+minX+")"+",("+maxY+" - "+minY+")");
        System.out.println("Najlavejsi: " + minEX.getName());
        System.out.println("Najvyssi: " + minEY.getName());
        System.out.println("Najpravejsi: " + maxEX.getName());
        System.out.println("Najspodnejsi: " + maxEY.getName());
        this.diagramScrollPane.getViewport().setViewPosition(new java.awt.Point((int)(minX*this.diagramPanel.scaleRatio[0]),(int)(minY*this.diagramPanel.scaleRatio[1])));
        repaint();
    }//GEN-LAST:event_resetZoomButtonActionPerformed

    private void cursorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cursorButtonActionPerformed
        ellipseButton.setSelected(false);
        lineButton.setSelected(false);
        resuorceButton.setSelected(false);
        rectangleButton.setSelected(false);
    }//GEN-LAST:event_cursorButtonActionPerformed

    private void diagramScrollPaneMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_diagramScrollPaneMouseDragged

    }//GEN-LAST:event_diagramScrollPaneMouseDragged

    private void updateComponentList(){
        ArrayList<Element> elements = new ArrayList<Element>();
        if(this.graph instanceof PetriNet){
            PetriNet pn = (PetriNet)this.graph;
            elements = new ArrayList<Element>();
            elements.addAll(pn.getListOfArcs());
            elements.addAll(pn.getListOfPlaces());
            elements.addAll(pn.getListOfResources());
            elements.addAll(pn.getListOfTransitions());
        }
        if(this.graph instanceof PrecedenceGraph){
            PrecedenceGraph pg = (PrecedenceGraph)this.graph;
            elements = new ArrayList<Element>();
            elements.addAll(pg.getListOfArcs());
            elements.addAll(pg.getListOfNodes());
        }
        //ArrayList<String> componentsName = new ArrayList<String>();
        this.listModel.removeAllElements();
        for (Element element : elements) {
            //componentsName.add(element.getName());
            this.listModel.addElement(element.getName());
        }
        
        this.componentList.removeAll();
        this.componentList.setModel(this.listModel);
        
        //this.componentList.add(componentsName);
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu aboutUs;
    private javax.swing.JButton alignBottomButton;
    private javax.swing.JButton alignLeftButton;
    private javax.swing.JButton alignRightButton;
    private javax.swing.JButton alignTopButton;
    private javax.swing.JList componentList;
    private javax.swing.JMenuItem convert;
    private javax.swing.JMenuItem create_state_diagram;
    private javax.swing.JButton cursorButton;
    private javax.swing.JScrollPane diagramScrollPane;
    private javax.swing.JMenu editMenu;
    private javax.swing.JToggleButton ellipseButton;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JMenuItem export;
    private javax.swing.JMenu fileMenu;
    private GUI.PropertiesMenu generalProperties;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton lineButton;
    private javax.swing.JMenuItem loadItem;
    private javax.swing.JLabel modelInfo;
    private javax.swing.JMenuItem newPetriNet;
    private javax.swing.JMenuItem newPrecedenceNet;
    private javax.swing.JTextArea notes;
    private javax.swing.JPanel propertiesMenu;
    private javax.swing.JTabbedPane propertiesTab;
    private javax.swing.JToggleButton rectangleButton;
    private javax.swing.JButton resetZoomButton;
    private javax.swing.JToggleButton resuorceButton;
    private javax.swing.JMenuItem saveAsItem;
    private javax.swing.JMenuItem saveItem;
    private javax.swing.JPanel sideMenu;
    private javax.swing.JToolBar toolBar;
    private javax.swing.JMenuBar topMenu;
    private javax.swing.JButton zoomInButton;
    private javax.swing.JButton zoomOutButton;
    // End of variables declaration//GEN-END:variables

    class DiagramPanel extends javax.swing.JPanel {

        private DiagramMouseAdapter mouseAdapter;
        private Graph graph;
        private Graphics2D g2d;
        private ArrayList<Element> selectedElements;
        private Element draggedElement;
        private Object draggedObject;
        private Color draggedColor;
        private double[] scaleRatio;
        boolean isCTRLdown = false;
        private int clickedX;
        private int clickedY;
        private Cursor draggedHandCursor,handCursor;

        /**
         * Creates new form GraphPanel
         */
        public DiagramPanel(Graph pa_graph) {
            super();
            setFocusable(true);
            //addKeyListener(new DiagramKeyAdapter());*/
            this.graph = pa_graph;
            //this.g2d;//null;
            this.scaleRatio = new double[]{1.0, 1.0};
            this.draggedObject = null;
            this.draggedElement = null;
            this.selectedElements = new ArrayList<Element>();
            this.mouseAdapter = new DiagramMouseAdapter();


            /*    Action _ctrlAction = new AbstractAction() {
             @Override
             public void actionPerformed(ActionEvent e) {
             isCTRLdown=true;
             System.out.println("ctrl pressed");
             }
             };
             getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.CTRL_DOWN_MASK,1,tru),"ctrlPressed");
             getActionMap().put("ctrlPressed",                _ctrlAction);
             */

            // Click listener, drag listener
            addMouseListener(mouseAdapter);
            addMouseMotionListener(mouseAdapter);
            addMouseWheelListener(new ScaleHandler());
            // Max sirka,vyska = 1000x1000
            setPreferredSize(new Dimension(10000, 10000));
            setBackground(Color.WHITE);
            /* Key */
            addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent event) {
                    if (KeyEvent.VK_ENTER == event.getKeyCode()) {
                        setVisible(true);
                    }
                }
            });
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image cursorImage = toolkit.getImage("Images\\move_hand_trans.png");
            Image cursorImage2 = toolkit.getImage("Images\\trans_hand.png");
            Point cursorHotSpot = new Point(0,0);
            this.draggedHandCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot,CUSTOM_MOVE_CURSOR);
            this.handCursor = toolkit.createCustomCursor(cursorImage2, cursorHotSpot, CUSTOM_HAND_CURSOR);
            this.setCursor(this.handCursor);
        }

        
        @Override 
        public void paint(Graphics g) {
            super.paint(g);
            this.g2d = (Graphics2D) g;
            //g2d.scale(scaleRatio[0], scaleRatio[1]);
            //g2d.translate(scaleRatio[0], scaleRatio[1]);
            /*
             if (g2d==null)
          
             else
             g=this.g2d;*/
            autosetWidthHeight();
            drawGraph();
            drawDraggedObject();
            drawSelectedElements();
        }
        /*
         public void drawPlace(int column,int row){
         drawPlace(column, row, Color.BLACK, Color.WHITE);          
         }
         public void drawPlace(int column,int row,Color c1, Color c2){
         // Place / Ring
         g2d.setColor(c2);
         g2d.fill(new Ellipse2D.Double(column+5,row+5,40,40));        
         g2d.setColor(c1);    
         g2d.draw(new 2D.Double(column+5,row+5,40,40));                
         }*/

        public void drawPlace(int column, int row, Color c1, Color c2, int width, int height, String name, int fontSize) {
            // Place / Ring
        /*g2d.setColor(c2);
             g2d.fill(new Ellipse2D.Double(column+5,row+5,width,height));        
             g2d.setColor(c1);    
             g2d.draw(new Ellipse2D.Double(column+5,row+5,width,height));   
             */
            g2d.setColor(c2);
            g2d.fill(new Ellipse2D.Double(column, row, width, height));
            g2d.setColor(c1);
            g2d.draw(new Ellipse2D.Double(column, row, width, height));


            // Remember old
            Font oldFont = g2d.getFont();
            Font newFont = new Font("Times New Roman", Font.PLAIN, fontSize);
            g2d.setFont(newFont);

            // Center string
            // Find the size of string NAME in font FONT in the current Graphics context G2D
            FontMetrics fm = g2d.getFontMetrics(newFont);
            java.awt.geom.Rectangle2D rect = fm.getStringBounds(name, g2d);

            int textWidth = (int) (rect.getWidth());
            int textHeight = (int) (rect.getHeight());

            // Center text horizontally and vertically
            int x = (int) (column + width / 2.0 - textWidth / 2.0);
            int y = (int) (row + height / 2.0 - textHeight / 2.0) + fm.getAscent();

            // Draw the string.        

            g2d.drawString(name, x, y);

            // Revert back
            g2d.setFont(oldFont);

        }

        public void drawPlace(int column, int row, Color c1, Color c2, int width, int height, String name, int fontSize, int alpha) {
            /* Transparency */
            Color c1alpha = c1;
            Color c2alpha = c2;
            for (int i = 1; i <= alpha; i++) {
                c1alpha = c1alpha.brighter();
                c2alpha = c2alpha.brighter();
            }
            /*
             Color c1alpha=c1.brighter();
             Color c2alpha=c2.brighter();*/
            //c1alpha.seta

            /*
             g2d.setColor(Color.GREEN);
             g2d.fill(new Ellipse2D.Double(column,row,width,height));        
             g2d.setColor(c1alpha);    
             g2d.draw(new Ellipse2D.Double(column,row,width,height));      */

            drawPlace(column, row, c1alpha, c2alpha, width, height, name, fontSize);
        }

        public void drawPlaceSelected(int column, int row, int width, int height) {
            Color color = g2d.getColor();
            Stroke _oldStroke = g2d.getStroke();
            g2d.setColor(Color.GRAY);
            Stroke s = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0);
            g2d.setStroke(s);
            //g2d.draw(new Ellipse2D.Double(column+5-10,row+5-10,40+20,40+20));  
            int distance = 10;

            g2d.draw(new Ellipse2D.Double(column - distance, row - distance, width + distance * 2, height + distance * 2));

            g2d.setColor(color);
            g2d.setStroke(_oldStroke);
        }

        /*public void drawResource(int column,int row){
         drawPlace(column, row, Color.BLACK, Color.GRAY);          
         }
         public void drawResource(int column,int row,Color c1, Color c2){
         // Place / Ring
         g2d.setColor(c2); //white vypln
         g2d.fill(new Ellipse2D.Double(column+5,row+5,40,40));        
         g2d.setColor(c1); //black   
         g2d.draw(new Ellipse2D.Double(column+5,row+5,40,40));                
         }*/
        public void drawResource(int column, int row, Color c1, Color c2, int width, int height, String name, int fontSize) {
            // Place / Ring
            Stroke _oldStroke = g2d.getStroke();

            g2d.setColor(c2); //white vypln
            g2d.fill(new Ellipse2D.Double(column, row, width, height));

            // Border more width
            g2d.setStroke(new BasicStroke(5));
            g2d.setColor(c1); //black   
            g2d.draw(new Ellipse2D.Double(column, row, width, height));
            g2d.setStroke(_oldStroke);


// Remember old
            Font oldFont = g2d.getFont();
            Font newFont = new Font("Times New Roman", Font.PLAIN, fontSize);
            g2d.setFont(newFont);

            // Center string
            // Find the size of string NAME in font FONT in the current Graphics context G2D
            FontMetrics fm = g2d.getFontMetrics(newFont);
            java.awt.geom.Rectangle2D rect = fm.getStringBounds(name, g2d);

            int textWidth = (int) (rect.getWidth());
            int textHeight = (int) (rect.getHeight());

            // Center text horizontally and vertically
            int x = (int) (column + width / 2.0 - textWidth / 2.0);
            int y = (int) (row + height / 2.0 - textHeight / 2.0) + fm.getAscent();

            // Draw the string.             
            g2d.drawString(name, x, y);
            // Revert back
            g2d.setFont(oldFont);
        }

        public void drawResource(int column, int row, Color c1, Color c2, int width, int height, String name, int fontSize, int alpha) {
            // Place / Ring
            Color c1alpha = new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), alpha);
            Color c2alpha = new Color(c2.getRed(), c2.getGreen(), c2.getBlue(), alpha);
            drawResource(column, row, c1alpha, c2alpha, width, height, name, fontSize);
        }

        /*
         public void drawTransition(int column,int row){
         drawTransition(column, row ,Color.BLACK,Color.WHITE);
         }
         public void drawTransition(int column,int row,Color c1,Color c2){
         // Transition / Rectangle
         g2d.setColor(c2);
         g2d.fill(new Rectangle2D.Float(column+12,row+5,25,40));                
         g2d.setColor(c1);
         g2d.draw(new Rectangle2D.Float(column+12,row+5,25,40));                        
         }*/
        public void drawTransition(int column, int row, Color c1, Color c2, int width, int height, String name, int fontSize) {
            // Transition / Rectangle                        

            g2d.setColor(c2);
            g2d.fill(new Rectangle2D.Float(column, row, width, height));
            g2d.setColor(c1);
            g2d.draw(new Rectangle2D.Float(column, row, width, height));

            // Remember old
            Font oldFont = g2d.getFont();
            Font newFont = new Font("Times New Roman", Font.PLAIN, fontSize);
            g2d.setFont(newFont);

            // Center string
            // Find the size of string NAME in font FONT in the current Graphics context G2D
            FontMetrics fm = g2d.getFontMetrics(newFont);
            java.awt.geom.Rectangle2D rect = fm.getStringBounds(name, g2d);

            int textWidth = (int) (rect.getWidth());
            int textHeight = (int) (rect.getHeight());

            // Center text horizontally and vertically
            int x = (int) (column + width / 2.0 - textWidth / 2.0);
            int y = (int) (row + height / 2.0 - textHeight / 2.0) + fm.getAscent();

            // Draw the string.            
            g2d.drawString(name, x, y);
            // Revert back
            g2d.setFont(oldFont);
        }

        public void drawTransition(int column, int row, Color c1, Color c2, int width, int height, String name, int fontSize, int alpha) {
            /* Transparency */
            Color c1alpha = c1;
            Color c2alpha = c2;
            for (int i = 1; i <= alpha; i++) {
                c1alpha = c1alpha.brighter();
                c2alpha = c2alpha.brighter();
            }

            //drawTransition(column, row, c1alpha, c2alpha, width, height, name, fontSize);
            drawTransition(column, row, c1alpha, c2alpha, width, height, name, fontSize);
        }

        public void drawTransitionSelected(int column, int row, int width, int height) {
            Color color = g2d.getColor();
            g2d.setColor(Color.GRAY);
            Stroke s = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0);
            g2d.setStroke(s);
            int distance = 10;
            g2d.draw(new Rectangle2D.Float(column - distance, row - distance, width + distance * 2, height + distance * 2));
            g2d.setColor(color);
        }

        public int[] solvePointsOfArrow(double x1, double x2, double y1, double y2, double midX, double midY, Element e, double width, double height) {

            if (e instanceof Transition) {
                double kx = midY - y1;
                double ky = midX - x1;
                Point[] listOfPoints = new Point[4];
                int numberOfValidPoints = 0;


//---------------------------LEFT--------------------------
                double leftDownY = y2 + height;

                double t1 = (x2 - x1) / ky;
                double t2 = (y1 + kx * t1 - y2) / (leftDownY - y2);

                int leftX = (int) (x1 + ky * t1);
                int leftY = (int) (y1 + kx * t1);
                Point left = new Point(leftX, leftY);
                if (left.getY() >= y2 && left.getY() <= y2 + height) {
                    listOfPoints[numberOfValidPoints] = left;
                    numberOfValidPoints++;
                }
//----------------------------RIGHT------------------------

                x2 += width;
                t1 = (x2 - x1) / ky;
                int rightX = (int) (x1 + ky * t1);
                int rightY = (int) (y1 + kx * t1);
                Point right = new Point(rightX, rightY);
                if (right.getY() >= y2 && right.getY() <= y2 + height) {
                    listOfPoints[numberOfValidPoints] = right;
                    numberOfValidPoints++;
                }
                x2 = x2 - width;
//-----------------------------UP--------------------------        
                double rightUpX = x2 + width;

                t1 = (y2 - y1) / kx;

                int upX = (int) (x1 + ky * t1);
                int upY = (int) (y1 + kx * t1);
                Point up = new Point(upX, upY);
                if (up.getX() >= x2 && up.getX() <= x2 + width) {
                    listOfPoints[numberOfValidPoints] = up;
                    numberOfValidPoints++;
                }
//----------------------------DOWN-------------------------         
                double rightDownX = x2 + width;
                y2 = y2 + height;
                t1 = (y2 - y1) / kx;

                int downX = (int) (x1 + ky * t1);
                int downY = (int) (y1 + kx * t1);
                Point down = new Point(downX, downY);
                if (down.getX() >= x2 && down.getX() <= x2 + width) {
                    listOfPoints[numberOfValidPoints] = down;
                    numberOfValidPoints++;
                }

                bubbleSort1(listOfPoints, new Point((int) x1, (int) y1), numberOfValidPoints);

                int[] field = new int[2];
                field[0] = (int) listOfPoints[0].getX();
                field[1] = (int) listOfPoints[0].getY();
                return field;
            } else {
                int[] field = new int[2];
                double b = height / 2.0;
                double a = width / 2.0;

                double k1 = midX - x1;
                double k2 = midY - y1;

                //slope-intercept form:
                double m = k2 / k1;
                double c = midY - (k2 / k1) * midX;
                //if slope is infinite
                if (Double.isInfinite(m) && Double.isInfinite(c) && c < 0) {
                    field[0] = (int) midX;
                    field[1] = (int) (midY - b);
                    return field;
                }
                if (Double.isInfinite(m) && Double.isInfinite(c) && m < 0) {
                    field[0] = (int) midX;
                    field[1] = (int) (midY + b);
                    return field;
                }

                double k = midY;
                double h = midX;
                double epsilon = c - k;
                double delta = c + m * h;
                double sqrt = Math.sqrt(a * a * m * m + b * b - delta * delta - k * k + 2.0 * delta * k);
                double menovatel = a * a * m * m + b * b;

                double intersectionX1 = (h * b * b - m * a * a * epsilon - a * b * sqrt) / menovatel;
                double intersectionX2 = (h * b * b - m * a * a * epsilon + a * b * sqrt) / menovatel;

                double intersectionY1 = (b * b * delta + k * m * m * a * a - a * b * m * sqrt) / menovatel;
                double intersectionY2 = (b * b * delta + k * m * m * a * a + a * b * m * sqrt) / menovatel;

                Point[] listOfPoints = new Point[2];
                listOfPoints[0] = new Point((int) (intersectionX1), (int) (intersectionY1));
                listOfPoints[1] = new Point((int) (intersectionX2), (int) (intersectionY2));

                bubbleSort1(listOfPoints, new Point((int) x1, (int) y1), 2);

                field[0] = (int) listOfPoints[0].getX();
                field[1] = (int) listOfPoints[0].getY();
                return field;

            }

        }

        public double vectorSize(Point a, Point b) {
            return Math.sqrt(((a.getX() - b.getX()) * (a.getX() - b.getX())) + ((a.getY() - b.getY()) * (a.getY() - b.getY())));
        }

        public void bubbleSort1(Point[] x, Point a, int size) {
            for (int pass = 1; pass < size; pass++) {
                for (int i = 0; i < size - pass; i++) {
                    if (x[i + 1] != null && (vectorSize(x[i], a) > vectorSize(x[i + 1], a))) {
                        Point temp = x[i];
                        x[i] = x[i + 1];
                        x[i + 1] = temp;
                    }
                }
            }
        }

        public int[] drawArrow(int x1, int y1, int midX, int midY, int x2, int y2, String type, String name, int fontSize, Element e, int width, int height, int capacity) {
            // Size of arrow in px
            int ARR_SIZE = 8;
            int[] field = solvePointsOfArrow(x1, x2, y1, y2, midX, midY, e, width, height);
            midX = field[0];
            midY = field[1];


            double dx = midX - x1;
            double dy = midY - y1;


            double angle = Math.atan2(dy, dx);
            int len = (int) Math.sqrt(dx * dx + dy * dy);
            AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
            at.concatenate(AffineTransform.getRotateInstance(angle));

            // Save and Rotate
            AffineTransform oldTransform = g2d.getTransform();
            g2d.transform(at);

            // Draw horizontal arrow starting in (0, 0)
            // Length decrease by X pixels if type of arrow is short

//            if ("short".equals(type)) {
//                // Add functionality
//                len = len - 30;
//            }


            g2d.drawLine(0, 0, len - 5, 0);
            g2d.fillPolygon(new int[]{len, len - ARR_SIZE, len - ARR_SIZE, len},
                    new int[]{0, -ARR_SIZE, ARR_SIZE, 0}, 4);
            // Retract old
            g2d.setTransform(oldTransform);

            if (capacity > 1) {
                Font oldFont = g2d.getFont();
                Font newFont = new Font("Times New Roman", Font.PLAIN, fontSize);
                g2d.setFont(newFont);

                int x = 0;
                int y = 0;
                if (x1 < midX) {
                    x = x1 + (midX - x1 + width / 2 - 10) / 2;
                    y = y1 + (midY - y1) / 2 + 15;
                } else {
                    x = x1 + (midX - x1 - width / 2 - 10) / 2;
                    y = y1 + (midY - y1) / 2 + 15;
                }

                // Draw the string.        
                g2d.drawString(capacity + "", x, y);

                // Revert back
                g2d.setFont(oldFont);
            }

            return field;
        }

        public void drawArrow(int x1, int y1, int x2, int y2, String type, String name, int fontSize) {
            // Size of arrow in px
            int ARR_SIZE = 8;

            double dx = x2 - x1;
            double dy = y2 - y1;
            double angle = Math.atan2(dy, dx);
            int len = (int) Math.sqrt(dx * dx + dy * dy);
            AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
            at.concatenate(AffineTransform.getRotateInstance(angle));

            // Save and Rotate
            AffineTransform oldTransform = g2d.getTransform();
            g2d.transform(at);

            // Draw horizontal arrow starting in (0, 0)
            // Length decrease by X pixels if type of arrow is short
            if ("short".equals(type)) {
                // Add functionality
                len = len - 30;
            }


            g2d.drawLine(0, 0, len - 5, 0);
            g2d.fillPolygon(new int[]{len, len - ARR_SIZE, len - ARR_SIZE, len},
                    new int[]{0, -ARR_SIZE, ARR_SIZE, 0}, 4);
            // Retract old
            g2d.setTransform(oldTransform);

            //Font oldFont = g2d.getFont();
            //Font newFont = new Font("Times New Roman", Font.PLAIN, fontSize);
            //g2d.setFont(newFont);

            //int x = 0;
            //int y = 0;

            // Draw the string.        

            //g2d.drawString(, x, y);

            // Revert back
            //g2d.setFont(oldFont);



            /*
             * Add name and fontSize drawString
             * \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\    FILL   ///////////////////////////
             */
        }

        public int[] drawArc(int column1, int row1, int width1, int height1, int column2, int row2, int width2, int height2, Color color, String name, int fontSize, Element e, int upperLeftAngleX, int upperLeftAngleY, int capacity) {
            // Arc / Arrow
            //g2d.setColor(new Color(27,161,226)); // Windows8Blue
//            System.out.println("x1 "+column1);
//            System.out.println("y1 "+row1);
//            
//            System.out.println("x2 "+column2);
//            System.out.println("y2 "+row2);
//            
//            System.out.println("width out "+width1);
//            System.out.println("height out "+height1);
//            System.out.println("width in "+width2);
//            System.out.println("height in "+height2);
            
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(3));
            // Draw arrow       
            int intercection[];
            intercection = drawArrow(column1 + (int) (width1 / 2.0), row1 + (int) (height1 / 2.0),
                    column2 + (int) (width2 / 2.0), row2 + (int) (height2 / 2.0), upperLeftAngleX, upperLeftAngleY,
                    "short", name, fontSize, e, width2, height2, capacity);

            return intercection;
        }

        public void drawArcSelected(int column1, int row1, int column2, int row2) {
            /* Calculating */
            // Get line between 2 points
            //Line2D.Double ln = new Line2D.Double(column1 + 25, row1 + 25, column2 + 25, row2 + 25);
            Line2D.Double ln = new Line2D.Double(column1, row1, column2, row2);
            // Distance from central line
            double indent = 10.0;
            double length = ln.getP1().distance(ln.getP2());

            double dx_li = (ln.getX2() - ln.getX1()) / length * indent;
            double dy_li = (ln.getY2() - ln.getY1()) / length * indent;

            // moved p1 point
            //double p1X = ln.getX1() - dx_li;
            //double p1Y = ln.getY1() - dy_li;

            // line moved to the left
            double lX1 = ln.getX1() - dy_li;
            double lY1 = ln.getY1() + dx_li;
            double lX2 = ln.getX2() - dy_li;
            double lY2 = ln.getY2() + dx_li;

            // moved p2 point
            //double p2X = ln.getX2() + dx_li;
            //double p2Y = ln.getY2() + dy_li;

            // line moved to the right
            double rX1_ = ln.getX1() + dy_li;
            double rY1 = ln.getY1() - dx_li;
            double rX2 = ln.getX2() + dy_li;
            double rY2 = ln.getY2() - dx_li;

            Path2D path = new Path2D.Double();
            path.moveTo(lX1, lY1);

            path.lineTo(lX1, lY1);
            path.lineTo(lX2, lY2);
            //path.lineTo(p2X, p2Y);
            path.lineTo(rX2, rY2);
            path.lineTo(rX1_, rY1);
            //path.lineTo(p1X, p1Y);

            // Add result
            Area area = new Area();
            area.add(new Area(path));
            /* End calculating */


            // Arc / Arrow
            g2d.setColor(Color.GRAY);
            Stroke s = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0);
            g2d.setStroke(s);
            // Draw arrow        
            //drawArrow( column1+25  ,row1+25,
            //           column2+25,  row2+25,"short");   
            // Draw dashed line around line
            g2d.draw(area);
        }

        public void drawGraph() {
            if (graph == null) {
                return;
            }
            if (graph instanceof PetriNet) {
                // Arcs , Places, Transitions
                // Draw all arcs
                for (Element e : ((PetriNet) graph).getListOfArcs()) {
                    Element in = ((Arc) e).getInElement();
                    Element out = ((Arc) e).getOutElement();
                    Arc arc = (Arc) e;

                    int width1 = 0, width2 = 0, height1 = 0, height2 = 0;
                    int capacity = arc.getCapacity();

                    // In element
                    if (in instanceof Place || in instanceof Resource) {
                        width1 = ((AbsPlace) in).getWidth();
                        height1 = ((AbsPlace) in).getHeight();
                    } else if (in instanceof Node) {
                        width1 = ((Node) in).getWidth();
                        height1 = ((Node) in).getHeight();
                    } else if (in instanceof Transition) {
                        width1 = ((Transition) in).getWidth();
                        height1 = ((Transition) in).getHeight();
                    }

                    // Out element
                    if (out instanceof Place || out instanceof Resource) {
                        width2 = ((AbsPlace) out).getWidth();
                        height2 = ((AbsPlace) out).getHeight();
                    } else if (out instanceof Node) {
                        width2 = ((Node) out).getWidth();
                        height2 = ((Node) out).getHeight();
                    } else if (out instanceof Transition) {
                        width2 = ((Transition) out).getWidth();
                        height2 = ((Transition) out).getHeight();
                    }
                    int intersection[];

                    //drawArc(out.getX(),out.getY(),in.getX(),in.getY(),arc.getColor(),arc.getName(),arc.getFontSize());
                    intersection = drawArc(out.getX(), out.getY(), width2, height2, in.getX(), in.getY(), width1, height1, arc.getColor(), arc.getName(), arc.getFontSize(), arc.getInElement(), arc.getInElement().getX(), arc.getInElement().getY(), capacity);
                    //drawArc(out.getX(), out.getY(), width1, height1, in.getX(), in.getY(), width2, height2, arc.getColor(), arc.getName(), arc.getFontSize());
                    arc.setIntercectionX(intersection[0]);
                    arc.setIntercectionY(intersection[1]);
                }

                // Draw all places
                for (Element e : ((PetriNet) graph).getListOfPlaces()) {
                    drawPlace(e.getX(), e.getY(), e.getColor(), e.getColor2(), ((Place) e).getWidth(), ((Place) e).getHeight(), e.getName() + " :" + ((Place) e).getMarkings(), e.getFontSize());
                }

                // Draw all resources
                for (Element e : ((PetriNet) graph).getListOfResources()) {
                    drawResource(e.getX(), e.getY(), e.getColor(), e.getColor2(), ((Resource) e).getWidth(), ((Resource) e).getHeight(), e.getName() + " :" + ((Resource) e).getMarking(), e.getFontSize());
                }

                // Draw all transitions
                for (Element e : ((PetriNet) graph).getListOfTransitions()) {
                    drawTransition(e.getX(), e.getY(), e.getColor(), e.getColor2(), ((Transition) e).getWidth(), ((Transition) e).getHeight(), e.getName(), e.getFontSize());
                }


                return;
            }   // Koniec Petri net

            if (graph instanceof PrecedenceGraph) {
                // Arcs, Nodes

                // Draw all arcs
                for (Element e : ((PrecedenceGraph) graph).getListOfArcs()) {
                    Element in = ((Arc) e).getInElement();
                    Element out = ((Arc) e).getOutElement();
                    Arc arc = (Arc) e;

                    int width1 = 0, width2 = 0, height1 = 0, height2 = 0;
                    int capacity = arc.getCapacity();
                    // In element
                    if (in instanceof Node) {
                        width1 = ((Node) in).getWidth();
                        height1 = ((Node) in).getHeight();
                    }

                    // Out element
                    if (out instanceof Node) {
                        width2 = ((Node) out).getWidth();
                        height2 = ((Node) out).getHeight();
                    }
                    int intersection[];
                    //drawArc(out.getX(),out.getY(),in.getX(),in.getY(),arc.getColor(),arc.getName(),arc.getFontSize());
                    //drawArc(out.getX(), out.getY(), width1, height1, in.getX(), in.getY(), width2, height2, arc.getColor(), arc.getName(), arc.getFontSize());
                    intersection = drawArc(out.getX(), out.getY(), width2, height2, in.getX(), in.getY(), width1, height1, arc.getColor(), arc.getName(), arc.getFontSize(), arc.getInElement(), arc.getInElement().getX(), arc.getInElement().getY(), capacity);
                    arc.setIntercectionX(intersection[0]);
                    arc.setIntercectionY(intersection[1]);
                }

                // Draw all nodes
                for (Element e : ((PrecedenceGraph) graph).getListOfNodes()) {
                    drawPlace(e.getX(), e.getY(), e.getColor(), e.getColor2(), ((Node) e).getWidth(), ((Node) e).getHeight(), e.getName() + " :" + ((Node) e).getCapacity(), e.getFontSize());
                }

                return;
            }   

        }

        private void drawDraggedObject() {

            if (draggedObject == null && draggedElement == null) {
                // Nothing to draw
                return;
            }

            g2d.setColor(draggedColor);
            // Rectangle 
            if (draggedElement instanceof Transition) {
                Transition t = (Transition) draggedElement;
                drawTransition(t.getX(), t.getY(), t.getColor(), t.getColor2(), t.getWidth(), t.getHeight(), t.getName(), t.getFontSize(), 2);
            }

            // Resource
            if (draggedElement instanceof Resource) {
                Resource r = (Resource) draggedElement;
                drawPlace(r.getX(), r.getY(), r.getColor(), r.getColor2(), r.getWidth(), r.getHeight(), r.getName() + " :" + r.getMarking(), r.getFontSize(), 2);
                //drawPlace(x, y, Color.GRAY, Color.gray);

            }

            // Place
            if (draggedElement instanceof Place) {
                Place p = (Place) draggedElement;
                drawPlace(p.getX(), p.getY(), p.getColor(), p.getColor2(), p.getWidth(), p.getHeight(), p.getName() + " :" + p.getMarkings(), p.getFontSize(), 2);
                //drawPlace(x, y, Color.GRAY, Color.gray);

            }
            // Node
            if (draggedElement instanceof Node) {
                Node n = (Node) draggedElement;
                drawPlace(n.getX(), n.getY(), n.getColor(), n.getColor2(), n.getWidth(), n.getHeight(), n.getName() + " :" + n.getCapacity(), n.getFontSize(), 2);
                //drawPlace(x, y, Color.GRAY, Color.gray);
            }



            // Selected is 
            if (draggedObject instanceof Line2D) //if(lineButton.isSelected())
            {
                int x1 = (int) ((Line2D) draggedObject).getX1();
                int y1 = (int) ((Line2D) draggedObject).getY1();
                int x2 = (int) ((Line2D) draggedObject).getX2();
                int y2 = (int) ((Line2D) draggedObject).getY2();

                // Arc is selected when it is moving
                //Arc _arc=(Arc)selectedElements.get(0); 
                drawArrow(x1, y1, x2, y2, "long", "", 0);//_arc.getName(),_arc.getFontSize());
            }

        }
//**

        private void drawSelectedElements() {
            /* Drah all elements */
            for (Element e : selectedElements) {
                // Ring


                if (e instanceof Place) {
                    Place p = (Place) e;
                    //drawPlaceSelected(e.getDiagramElement().getX(), e.getDiagramElement().getY());
                    drawPlaceSelected(p.getX(), p.getY(), p.getWidth(), p.getHeight());
                }
                if (e instanceof Node) {
                    Node n = (Node) e;
                    //drawPlaceSelected(e.getDiagramElement().getX(), e.getDiagramElement().getY());
                    drawPlaceSelected(n.getX(), n.getY(), n.getWidth(), n.getHeight());
                }
                if (e instanceof Resource) {
                    Resource r = (Resource) e;
                    //drawPlaceSelected(e.getDiagramElement().getX(), e.getDiagramElement().getY());
                    drawPlaceSelected(r.getX(), r.getY(), r.getWidth(), r.getHeight());
                }
                if (e instanceof Transition) {
                    Transition t = (Transition) e;
                    //drawTransitionSelected(e.getDiagramElement().getX(), e.getDiagramElement().getY());
                    drawTransitionSelected(t.getX(), t.getY(), t.getWidth(), t.getHeight());
                    //drawTransitionSelected(t.getX()-t.getWidth()/2, t.getY()-t.getHeight()/2, t.getWidth(), t.getHeight());
                }
                if (e instanceof Arc) {
                    int outX = 0;
                    int outY = 0;
                    /*   DiagramElement in  =((Arc)e).getInElement().getDiagramElement();
                     DiagramElement out =((Arc)e).getOutElement().getDiagramElement();

                     drawArcSelected(out.getX(),out.getY(),in.getX(),in.getY());*/
                    Element in = ((Arc) e).getInElement();
                    Element out = ((Arc) e).getOutElement();
//                    System.out.println(out);
//                    System.out.println(out.getX());
//                    System.out.println(out.getY());
//                    System.out.println(in);
//                    System.out.println(in.getX());
//                    System.out.println(in.getY());
                    if (out instanceof Transition) {
                        outX = ((Transition) out).getWidth() / 2 + out.getX();
                        outY = ((Transition) out).getHeight() / 2 + out.getY();
                    }
                    if (out instanceof Place) {
                        outX = ((Place) out).getWidth() / 2 + out.getX();
                        outY = ((Place) out).getHeight() / 2 + out.getY();
                    }
                    if (out instanceof Resource) {
                        outX = ((Resource) out).getWidth() / 2 + out.getX();
                        outY = ((Resource) out).getHeight() / 2 + out.getY();
                    }
                    if (out instanceof Node) {
                        outX = ((Node) out).getWidth() / 2 + out.getX();
                        outY = ((Node) out).getHeight() / 2 + out.getY();
                    }
                    drawArcSelected(outX, outY, ((Arc) e).getIntercectionX(), ((Arc) e).getIntercectionY());
                }

            }
        }

        public void mouseLeftClick(int x, int y) {
            // Select 1 element
            //g2d.drawString(name, x, y);
            //g2d.drawString(name, po.x, po.y);
            x = (int) (x / scaleRatio[0]);// +(1-scaleRatio[0])*(x-0));// --scaleRatio[0])*x/2);
            y = (int) (y / scaleRatio[1]);// +(1-scaleRatio[1])*(y-0));// -(1-scaleRatio[1])*y/2);

            /* If CTRL is pressed, dont clear list, just add item */
            if (isCTRLdown == false) {
                selectedElements.clear();
            }

            Element e = controller.getLocationElement(x, y);
            Arc a = controller.getLocationArc(x, y);
            if (e != null) {
                selectedElements.add(e);
                loadElementProperties(e);
            } else if (a != null) {
                selectedElements.add(a);
                loadElementProperties(a);
            }

            // Create new
            if (selectedElements.isEmpty()) {
                this.draggedObject = null;
                //h
                // Place / Node
                if (ellipseButton.isSelected()) {
                    if (graph instanceof PetriNet) {
                        controller.addPlace("P", x - 38, y - 19);
                    }
                    if (graph instanceof PrecedenceGraph) {
                        controller.addNode("Node", x - 25, y - 12);
                    }
                }
                // Resource
                if (resuorceButton.isSelected()) {
                    if (graph instanceof PetriNet) {
                        controller.addResource("R", x - 50, y - 19);
                    }
                }
                // Transition
                if (rectangleButton.isSelected()) {
                    controller.addTransition("T", x - 43, y - 19);
                }

            }

            // Dragging preparation & create new arc
            //else 
            if (selectedElements.size() == 1) {
                Element currentElement = selectedElements.get(0);

                if (currentElement instanceof Transition || currentElement instanceof Place
                        || currentElement instanceof Node || currentElement instanceof Resource) {
                    // Place or Node or Resource or Transition
                    if (lineButton.isSelected()) {
                        // Creat new Arc
                        this.draggedColor = Color.GRAY;
                        this.draggedObject = new Line2D.Float(x, y, x, y);  // start of new arc
                    } else {
                        // select the object      
                        clickedX = x;
                        clickedY = y;
                        this.draggedElement = currentElement;
                        propertiesMenu.setVisible(true);
                    }
                }

                // Arc
                if (currentElement instanceof Arc) {
                    //this.draggedColor=Color.GRAY;
                    //this.draggedObject=new Rectangle2D.Float(x, y, 25, elementWidth-10);
                    propertiesMenu.setVisible(true);
                }
            }




            if (selectedElements.isEmpty()) {
                propertiesMenu.setVisible(false);
            }
            updateComponentList();
            repaint();
        }

        public void mouseRightClick(int x, int y) {
            x = (int) (x / scaleRatio[0]);
            y = (int) (y / scaleRatio[1]);
            // Create RED shadow line indicating deletion of arc        
            controller.deleteElement(x, y);
            controller.deleteArc(x, y);
            selectedElements.clear();
            updateComponentList();
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
            int origX = x;
            int origY = y;
            x = (int) (x / scaleRatio[0]);
            y = (int) (y / scaleRatio[1]);

            // None element or arc
            if (draggedObject == null && draggedElement == null) {
                int scrollPositionX = diagramScrollPane.getViewport().getViewPosition().x;
                int scrollPositionY = diagramScrollPane.getViewport().getViewPosition().y;
                int offsetX = (this.mouseAdapter.getX() - origX+scrollPositionX);
                int offsetY = (this.mouseAdapter.getY() - origY+scrollPositionY);
                if(offsetY > 0 && offsetX > 0){
                    diagramScrollPane.getViewport().setViewPosition(new java.awt.Point(offsetX,offsetY));
                }
                return;
            }
            // Element
            if (draggedElement instanceof Place || draggedElement instanceof Node || draggedElement instanceof Resource
                    || draggedElement instanceof Transition) {

                draggedElement.setX(draggedElement.getX() + (x - clickedX));
                draggedElement.setY(draggedElement.getY() + (y - clickedY));
                clickedX = x;
                clickedY = y;
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
            if (draggedObject instanceof Line2D) {
                int x1 = (int) ((Line2D) draggedObject).getX1();
                int y1 = (int) ((Line2D) draggedObject).getY1();
                draggedObject = new Line2D.Float(x1, y1, x, y);
            }

            repaint();
        }

        /*private void mouseRightDragged(int x, int y) {
         // Line
         /*if (draggedObject instanceof Line2D)
         {
         int x1=(int)((Line2D)draggedObject).getX1();
         int y1=(int)((Line2D)draggedObject).getY1();                
         draggedObject=new Line2D.Float(x1,y1,x,y);
         }  
         repaint();     */
        //}
        public void mouseLeftReleased(int x_old, int y_old, int x_new, int y_new) {
            x_old = (int) (x_old / this.scaleRatio[0]);
            x_new = (int) (x_new / this.scaleRatio[0]);
            y_old = (int) (y_old / this.scaleRatio[1]);
            y_new = (int) (y_new / this.scaleRatio[1]);

            // Old and current positions

            // Move place / transition
            // && resource isSelected
            if (!ellipseButton.isSelected() && !rectangleButton.isSelected() && !lineButton.isSelected()) {
                controller.moveElement(x_old, y_old, x_new, y_new);
            }

            // Add arc
            if (lineButton.isSelected() && draggedObject instanceof Line2D) {
                controller.addArc("Arc", x_old, y_old, x_new, y_new);
            }

            draggedObject = null;
            draggedElement = null;
            repaint();
        }

        /*private void mouseRightReleased(int x_old, int y_old, int x_new, int y_new) {
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
        //}
        private void loadElementProperties(Element currentElement) {
            generalProperties.loadProperties(currentElement, graph, this);
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

        // Set Scale Ration depend on all items
        // private void setScaleRatioZoomAll(int width,int height) {
            /*int [] minXYmaxXY=controller.getMinXYMaxXY();
         if (minXYmaxXY[0]==0 && minXYmaxXY[1]==0 && minXYmaxXY[2]==0 && minXYmaxXY[3]==0 )
         return;
            
         /* Count nonempty net for width and height */
        /*int minX=minXYmaxXY[0], minY=minXYmaxXY[1], maxX=minXYmaxXY[2], maxY=minXYmaxXY[3];
         if ((maxX-minX)<width)
         {
         scaleRatio[0]=2;                                      
         scaleRatio[1]=2;
         }
         scaleRatio[0]=2;
         scaleRatio[1]=2;*/
        // }
        private void autosetWidthHeight() {
            int marginWidth = 20;
            int marginHeight = 20;
            Font oldfont = g2d.getFont();
            g2d.scale(1, 1);

            // Petri net
            if (graph instanceof PetriNet) {
                for (Place p : ((PetriNet) graph).getListOfPlaces()) {
                    if (p.getWidth() == -1 && p.getHeight() == -1) {
                        Font newFont = new Font("Times New Roman", Font.PLAIN, p.getFontSize());
                        g2d.setFont(newFont);

                        FontMetrics fm = g2d.getFontMetrics(newFont);
                        java.awt.geom.Rectangle2D rect = fm.getStringBounds(p.getName() + " :" + p.getMarking(), g2d);

                        double textWidth = (rect.getWidth());
                        double textHeight = (rect.getHeight());

                        p.setWidth((int) (textWidth + marginWidth));
                        p.setHeight((int) textHeight + marginHeight);
                    }
                }
                for (Transition t : ((PetriNet) graph).getListOfTransitions()) {
                    if (t.getWidth() == -1 && t.getHeight() == -1) {
                        Font newFont = new Font("Times New Roman", Font.PLAIN, t.getFontSize());
                        g2d.setFont(newFont);

                        FontMetrics fm = g2d.getFontMetrics(newFont);
                        java.awt.geom.Rectangle2D rect = fm.getStringBounds(t.getName(), g2d);

                        double textWidth = (rect.getWidth());
                        double textHeight = (rect.getHeight());

                        t.setWidth((int) (textWidth + marginWidth));
                        t.setHeight((int) textHeight + marginHeight);
                    }
                }

                for (Resource r : ((PetriNet) graph).getListOfResources()) {
                    if (r.getWidth() == -1 && r.getHeight() == -1) {
                        Font newFont = new Font("Times New Roman", Font.PLAIN, r.getFontSize());
                        g2d.setFont(newFont);

                        FontMetrics fm = g2d.getFontMetrics(newFont);
                        java.awt.geom.Rectangle2D rect = fm.getStringBounds(r.getName() + " :" + r.getMarking(), g2d);

                        int textWidth = (int) (rect.getWidth());
                        int textHeight = (int) (rect.getHeight());

                        r.setWidth(textWidth + marginWidth);
                        r.setHeight(textHeight + marginHeight);
                    }
                }
            }

            // Precedence graph
            if (graph instanceof PrecedenceGraph) {
                for (Node n : ((PrecedenceGraph) graph).getListOfNodes()) {
                    if (n.getWidth() == -1 && n.getHeight() == -1) {
                        Font newFont = new Font("Times New Roman", Font.PLAIN, n.getFontSize());
                        g2d.setFont(newFont);

                        FontMetrics fm = g2d.getFontMetrics(newFont);
                        java.awt.geom.Rectangle2D rect = fm.getStringBounds(n.getName() + " :" + n.getCapacity(), g2d);

                        int textWidth = (int) (rect.getWidth());
                        int textHeight = (int) (rect.getHeight());

                        n.setWidth(textWidth + marginWidth);
                        n.setHeight(textHeight + marginHeight);
                    }
                }
            }
            // Revert settings
            g2d.scale(scaleRatio[0], scaleRatio[1]);
            g2d.setFont(oldfont);
        } // Function end

        /**
         * @param draggedHandCursor the draggedHandCursor to set
         */
        public void setDraggedHandCursor(Cursor draggedHandCursor) {
            this.draggedHandCursor = draggedHandCursor;
        }

        /**
         * @param handCursor the handCursor to set
         */
        public void setHandCursor(Cursor handCursor) {
            this.handCursor = handCursor;
        }
    }

    public class DiagramKeyAdapter extends KeyAdapter {//implements KeyListener{

        //@Override
        public void keyTyped(KeyEvent ke) {
            //diagramPanel.isCTRLdown=ke.isControlDown();            
        }

        // @Override
        public void keyPressed(KeyEvent ke) {
            diagramPanel.isCTRLdown = ke.isControlDown();
        }

        //@Override
        public void keyReleased(KeyEvent ke) {
            //diagramPanel.isCTRLdown=ke.isControlDown();
            diagramPanel.isCTRLdown = false;
        }
    }

    public class DiagramMouseAdapter extends MouseAdapter{

        private int x;
        private int y;

        public DiagramMouseAdapter() {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            diagramPanel.setCursor(diagramPanel.draggedHandCursor);
            // Save current            
            x = e.getX();
            y = e.getY();
            // Check for click button
            if (SwingUtilities.isLeftMouseButton(e)) {
                diagramPanel.mouseLeftClick(getX(), getY());
            }
            if (SwingUtilities.isRightMouseButton(e)) {
                diagramPanel.mouseRightClick(getX(), getY());
            }
            /*if (SwingUtilities.isMiddleMouseButton  (e) )
             System.out.println("stredny "+x+" "+y);*/
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)&&e.isControlDown()) {
                int scrollPositionX = diagramScrollPane.getViewport().getViewPosition().x;
                int scrollPositionY = diagramScrollPane.getViewport().getViewPosition().y;
                int offsetX = (getX() - e.getX()+scrollPositionX);
                int offsetY = (getY() - e.getY()+scrollPositionY);
                if(offsetY > 0 && offsetX > 0){
                diagramScrollPane.getViewport().setViewPosition(new java.awt.Point(offsetX,offsetY));
                }
            }
            // Left
            else if (SwingUtilities.isLeftMouseButton(e)) {
                diagramPanel.mouseLeftDragged(e.getX(), e.getY());
            }
            // Right - but left functionality
            /*if (SwingUtilities.isRightMouseButton(e)) {
             //diagramPanel.mouseLeftDragged(e.getX(),e.getY());    
             diagramPanel.mouseRightDragged(e.getX(), e.getY());
             }*/
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // Old location is different from current        
            //if (x != e.getX()   ||     y != e.getY())
            if (true) {
                // Left
                if (SwingUtilities.isLeftMouseButton(e)) {
                    diagramPanel.mouseLeftReleased(getX(), getY(), e.getX(), e.getY());
                }
                // Right
                /*if (SwingUtilities.isRightMouseButton(e)) {
                 //diagramPanel.mouseRightReleased(x,y,e.getX(),e.getY());
                 }*/
                
            }
            diagramPanel.setCursor(diagramPanel.handCursor);
        }

        /**
         * @return the x
         */
        public int getX() {
            return x;
        }

        /**
         * @return the y
         */
        public int getY() {
            return y;
        }
    }
    	private class ScaleHandler implements MouseWheelListener {
                @Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			if(e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL && e.isControlDown()) {
                                    ellipseButton.setSelected(false);
                                    rectangleButton.setSelected(false);
                                    resuorceButton.setSelected(false);
                                    rectangleButton.setSelected(false);
                                    lineButton.setSelected(false);
                                //diagramPanel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                                
                                /*
                                int scrollPositionX = diagramScrollPane.getViewport().getViewPosition().x;
                                int scrollPositionY = diagramScrollPane.getViewport().getViewPosition().y;
                                int offsetX = (getX() - e.getX()+scrollPositionX);
                                int offsetY = (getY() - e.getY()+scrollPositionY);
                                if(offsetY > 0 && offsetX > 0){
                                    diagramScrollPane.getViewport().setViewPosition(new java.awt.Point(offsetX,offsetY));
                                }
                                */ 
                                    int minX = Integer.MAX_VALUE;
                                    int minY = Integer.MAX_VALUE;
                                    int maxX = 0;
                                    int maxY = 0;
                                    ArrayList<Element> elements = new ArrayList<Element>();
                                    if(graph instanceof PetriNet){
                                        PetriNet pn = (PetriNet)graph;
                                        elements = new ArrayList<Element>();
                                        elements.addAll(pn.getListOfArcs());
                                        elements.addAll(pn.getListOfPlaces());
                                        elements.addAll(pn.getListOfResources());
                                        elements.addAll(pn.getListOfTransitions());
                                    }
                                    if(graph instanceof PrecedenceGraph){
                                        PrecedenceGraph pg = (PrecedenceGraph)graph;
                                        elements = new ArrayList<Element>();
                                        elements.addAll(pg.getListOfArcs());
                                        elements.addAll(pg.getListOfNodes());
                                    }
                                    for (Element element : elements) {
                                        if(minX>element.getX()){
                                            minX = element.getX();
                                        }
                                        if(minY>element.getY()){
                                            minY = element.getY();
                                        }
                                        if(maxX<element.getX()){
                                            maxX = element.getX();
                                        }
                                        if(maxY<element.getY()){
                                            maxY = element.getY();
                                        }
                                    }
                                Point mousePosition = diagramPanel.getMousePosition();
                                double scale = (.1 * e.getWheelRotation());
                                System.out.println(""+mousePosition.x+", "+mousePosition.y+" "+ maxX+" "+maxY);
                                if(diagramPanel.scaleRatio[0] > 0.3 || scale > 0){
                                    diagramPanel.scaleRatio[0] += scale;
                                    diagramPanel.scaleRatio[1] += scale;
                                }else{
                                    diagramPanel.scaleRatio[0] = 0.3;
                                    diagramPanel.scaleRatio[1] = 0.3;
                                }
                                System.out.println(""+diagramScrollPane.getViewport().getViewPosition().x+"  "+diagramScrollPane.getViewport().getViewPosition().y);
                                diagramScrollPane.getViewport().setViewPosition(
                                        new java.awt.Point(
                                            (int)(minX*diagramPanel.scaleRatio[0]),
                                            (int)(minY*diagramPanel.scaleRatio[1])));
				// don't cross negative threshold.
				// also, setting scale to 0 has bad effects
				//canvas.scale = Math.max(0.00001, canvas.scale); 
				repaint();
			}
		}
	}

    private void resetForm() {
        ellipseButton.setSelected(false);
        rectangleButton.setSelected(false);
        resuorceButton.setSelected(false);
        rectangleButton.setSelected(false);
        lineButton.setSelected(false);
        propertiesMenu.setVisible(false);
        this.revalidate();
        this.repaint();
}
}
