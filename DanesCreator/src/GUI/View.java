/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import ConfigManagers.GeneralSettingsManager;
import ConfigManagers.ShortcutsManager;
import DiagramAdapters.DiagramMouseWheelAdapter;
import DiagramAdapters.DiagramMouseAdapter;
import Core.AbsPlace;
import Core.Arc;
import Core.ComplexElement;
import Core.Element;
import Core.Graph;
import Core.Node;
import Core.PairValue;
import Core.PetriNet;
import Core.Place;
import Core.PrecedenceGraph;
import Core.Resource;
import Core.Transition;
import DiagramAdapters.DiagramKeyAdapter;
import DiagramAdapters.RecentFileActionListener;
import FileManager.XMLCPNManager;
import FileManager.XMLPetriManager;
import FileManager.XMLPrecedenceManager;
import StateSpace.StateSpaceCalculator;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import FileManager.FileManager;
import GUI.RASCheckerWindow.RASCheckerWindow;
import GUI.RASCheckerWindow.RASCheckerWindow;
import java.awt.Component;
import java.awt.MenuItem;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
//import sun.org.mozilla.javascript.internal.xmlimpl.XML;

/**
 *
 * @author marek
 */
public class View extends javax.swing.JFrame {

    private static final String APP_NAME = "Danes Creator";
    private static final String PETRI_NAME = "Petri net";
    private static final String PRECEDENCE_GRAPH = "Precedence graph";
    private static final int CUSTOM_ELLIPSE_CURSOR = 1;
    private static final int CUSTOM_RES_CURSOR = 2;
    private static final int CUSTOM_TRANSITION_CURSOR = 3;
    private static final int CUSTOM_ARROW_CURSOR = 4;
    private static final int CUSTOM_HAND_CURSOR = 5;
    private static final int CUSTOM_MOVE_CURSOR = 6;
    private Graph graph;
    private DiagramPanel diagramPanel;
    private Controller controller;
    private AboutUs about;
    private File selectedFile;
    private String selectedFileName;
    private String selectedFilePath;
    private AffineTransform oldTransform;
    private int lastMouseClickX, lastMouseClickY;
    private DefaultListModel listModel = new DefaultListModel();
    private Cursor ellipseCursor, transitionCursor, arcCursor, resCursor, handCursor, moveHandCursor;
    private PopUpMenu popMenu;
    private FileManager fileManager;
    private ShortcutsManager shortcutsManager;
    private GeneralSettingsManager generalSettingsManager;
    private AbstractButton selectedButton;
    private AbstractButton selectedButtonTemp;
   

    public View(PetriNet pa_petriNet, Controller pa_controller) {
        super();
        //this.setLocationRelativeTo(null);
        this.graph = pa_petriNet;
        this.controller = pa_controller;
        
        this.diagramPanel = null;
        
        this.generalSettingsManager = new GeneralSettingsManager();
        this.controller.setSettigns(generalSettingsManager);
        int width = this.generalSettingsManager.getWindowWidth();
        int height = this.generalSettingsManager.getWindowHeight();
        int windowX = this.generalSettingsManager.getWindowX();
        int windowY = this.generalSettingsManager.getWindowY();
        if(width != 0 && height != 0){
            setPreferredSize(new Dimension(width,height));
        }
        setLocation(windowX, windowY);
        initComponents();
        for (int i = 0; i < this.generalSettingsManager.getRecentFiles().length; i++) {
            if(!this.generalSettingsManager.getRecentFiles()[i].equals("")){
                File recentFile = new File(this.generalSettingsManager.getRecentFiles()[i]);
                if(recentFile.exists()){
                    JMenuItem recentMenuItem = new JMenuItem(recentFile.getName());
                    recentMenuItem.addActionListener(new RecentFileActionListener(this,recentFile));
                    this.recentFilesMenu.add(recentMenuItem);
                }
            }
        }
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Point cursorHotSpot = new Point(0, 0);
        this.ellipseCursor = toolkit.createCustomCursor(toolkit.getImage("Images\\hand_with_place.png"), cursorHotSpot, "ellipse_cursor");
        this.resCursor = toolkit.createCustomCursor(toolkit.getImage("Images\\hand_with_res.png"), cursorHotSpot, "res_cursor");
        this.transitionCursor = toolkit.createCustomCursor(toolkit.getImage("Images\\hand_with_trans.png"), cursorHotSpot, "trans_cursor");
        this.arcCursor = toolkit.createCustomCursor(toolkit.getImage("Images\\hand_with_arrow.png"), cursorHotSpot, "arrow_cursor");
        this.moveHandCursor = toolkit.createCustomCursor(toolkit.getImage("Images\\move_hand.png"), cursorHotSpot, "move_cursor");
        this.handCursor = toolkit.createCustomCursor(toolkit.getImage("Images\\hand.png"), cursorHotSpot, "hand_cursor");

        this.fileManager = new FileManager();

        this.shortcutsManager = new ShortcutsManager();

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
        zoomAllButton.setVisible(false);
        bendButton.setVisible(false);
        deleteBendButton.setVisible(false);

        // Custom init 
        setTitle("DANES Creator");
        //setSize(800, 600); 
        setVisible(true);

        /*
         KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
         manager.addKeyEventDispatcher(new ViewKeyEventDispatcher());
         */

        this.updateComponentList();
        
        
    }
    /* Class for keyMapping */

    /*
     private class ViewKeyEventDispatcher implements KeyEventDispatcher {

     @Override
     public boolean dispatchKeyEvent(KeyEvent e) {
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
     if(KeyEvent.VK_ESCAPE == e.getKeyCode()){
     resetForm();
     }
     }
     return false;
     }
     }
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sideMenu = new javax.swing.JPanel();
        propertiesMenu = new javax.swing.JPanel();
        propertiesTab = new javax.swing.JTabbedPane();
        generalProperties = new GUI.PropertiesMenu();
        notes = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        componentList = new javax.swing.JList();
        buttonsPanel = new javax.swing.JPanel();
        ellipseButton = new javax.swing.JToggleButton();
        rectangleButton = new javax.swing.JToggleButton();
        lineButton = new javax.swing.JToggleButton();
        resuorceButton = new javax.swing.JToggleButton();
        horizontalMagnetButton = new javax.swing.JToggleButton();
        verticalMagnetButton = new javax.swing.JToggleButton();
        modelInfo = new javax.swing.JLabel();
        curButton = new javax.swing.JToggleButton();
        diagramScrollPane = new javax.swing.JScrollPane();
        backgroundImageLabel = new javax.swing.JLabel();
        toolBar = new javax.swing.JToolBar();
        alignTopButton = new javax.swing.JButton();
        alignBottomButton = new javax.swing.JButton();
        alignLeftButton = new javax.swing.JButton();
        alignRightButton = new javax.swing.JButton();
        zoomInButton = new javax.swing.JButton();
        zoomOutButton = new javax.swing.JButton();
        resetZoomButton = new javax.swing.JButton();
        zoomAllButton = new javax.swing.JButton();
        bendButton = new javax.swing.JToggleButton();
        deleteBendButton = new javax.swing.JToggleButton();
        topMenu = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newPetriNet = new javax.swing.JMenuItem();
        newPrecedenceNet = new javax.swing.JMenuItem();
        saveItem = new javax.swing.JMenuItem();
        saveAsItem = new javax.swing.JMenuItem();
        loadItem = new javax.swing.JMenuItem();
        recentFilesMenu = new javax.swing.JMenu();
        settingsItem = new javax.swing.JMenuItem();
        exitItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        convert = new javax.swing.JMenuItem();
        export = new javax.swing.JMenuItem();
        create_state_diagram = new javax.swing.JMenuItem();
        RASMenuItem = new javax.swing.JMenuItem();
        shorcutConfig = new javax.swing.JMenuItem();
        aboutUs = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        sideMenu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        sideMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        propertiesTab.setBackground(new java.awt.Color(204, 204, 204));

        generalProperties.setToolTipText("");
        generalProperties.setFocusCycleRoot(true);
        generalProperties.setFocusTraversalPolicyProvider(true);
        generalProperties.setName(""); // NOI18N
        generalProperties.setPreferredSize(new java.awt.Dimension(188, 500));
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
        notes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                notesKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                notesKeyReleased(evt);
            }
        });
        propertiesTab.addTab("Notes", notes);

        javax.swing.GroupLayout propertiesMenuLayout = new javax.swing.GroupLayout(propertiesMenu);
        propertiesMenu.setLayout(propertiesMenuLayout);
        propertiesMenuLayout.setHorizontalGroup(
            propertiesMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, propertiesMenuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(propertiesTab, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        propertiesMenuLayout.setVerticalGroup(
            propertiesMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(propertiesMenuLayout.createSequentialGroup()
                .addComponent(propertiesTab, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        propertiesTab.getAccessibleContext().setAccessibleName("Properties");

        sideMenu.add(propertiesMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 240, 310));

        componentList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        componentList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                componentListValueChanged(evt);
            }
        });
        componentList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                componentListKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(componentList);

        sideMenu.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, 226, 120));

        ellipseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/EllipseIcon.png"))); // NOI18N
        ellipseButton.setToolTipText("Add new place/node");
        ellipseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ellipseButtonActionPerformed(evt);
            }
        });

        rectangleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/RectangleIcon.png"))); // NOI18N
        rectangleButton.setToolTipText("Add new transiton");
        rectangleButton.setBorder(null);
        rectangleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rectangleButtonActionPerformed(evt);
            }
        });

        lineButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lineIcon.png"))); // NOI18N
        lineButton.setToolTipText("Add new arc");
        lineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lineButtonActionPerformed(evt);
            }
        });

        resuorceButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Resources.png"))); // NOI18N
        resuorceButton.setToolTipText("Add new resource");
        resuorceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resuorceButtonActionPerformed(evt);
            }
        });

        horizontalMagnetButton.setText("horizont");
        horizontalMagnetButton.setToolTipText("Add horizontal megnetic line");
        horizontalMagnetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                horizontalMagnetButtonActionPerformed(evt);
            }
        });

        verticalMagnetButton.setText("vertical");
        verticalMagnetButton.setToolTipText("Add new place/node");
        verticalMagnetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verticalMagnetButtonActionPerformed(evt);
            }
        });

        curButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cursor.png"))); // NOI18N
        curButton.setToolTipText("Free Move");
        curButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                curButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonsPanelLayout = new javax.swing.GroupLayout(buttonsPanel);
        buttonsPanel.setLayout(buttonsPanelLayout);
        buttonsPanelLayout.setHorizontalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(buttonsPanelLayout.createSequentialGroup()
                        .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(buttonsPanelLayout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addComponent(horizontalMagnetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(verticalMagnetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(buttonsPanelLayout.createSequentialGroup()
                                .addComponent(curButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(buttonsPanelLayout.createSequentialGroup()
                                        .addComponent(lineButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rectangleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(buttonsPanelLayout.createSequentialGroup()
                                        .addComponent(ellipseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(resuorceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 12, Short.MAX_VALUE))
                    .addGroup(buttonsPanelLayout.createSequentialGroup()
                        .addComponent(modelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        buttonsPanelLayout.setVerticalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsPanelLayout.createSequentialGroup()
                .addComponent(modelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lineButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rectangleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(curButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ellipseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resuorceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(verticalMagnetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(horizontalMagnetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
        );

        sideMenu.add(buttonsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 250, 110));

        diagramScrollPane.setBorder(null);
        diagramScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        diagramScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
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
        diagramScrollPane.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                diagramScrollPaneKeyPressed(evt);
            }
        });

        backgroundImageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        backgroundImageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/big.png"))); // NOI18N
        backgroundImageLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        diagramScrollPane.setViewportView(backgroundImageLabel);

        toolBar.setFloatable(false);
        toolBar.setRollover(true);
        toolBar.setEnabled(false);

        alignTopButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/alignTop.png"))); // NOI18N
        alignTopButton.setToolTipText("Align top");
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
        zoomOutButton.setToolTipText("Zoom out");
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
        resetZoomButton.setToolTipText("Reset zoom");
        resetZoomButton.setFocusable(false);
        resetZoomButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        resetZoomButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        resetZoomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetZoomButtonActionPerformed(evt);
            }
        });
        toolBar.add(resetZoomButton);

        zoomAllButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/zoom_all.png"))); // NOI18N
        zoomAllButton.setToolTipText("");
        zoomAllButton.setFocusable(false);
        zoomAllButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zoomAllButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        zoomAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomAllButtonActionPerformed(evt);
            }
        });
        toolBar.add(zoomAllButton);

        bendButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bendLine.png"))); // NOI18N
        bendButton.setFocusable(false);
        bendButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bendButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bendButtonActionPerformed(evt);
            }
        });
        toolBar.add(bendButton);

        deleteBendButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/DeleteLineBend.png"))); // NOI18N
        deleteBendButton.setFocusable(false);
        deleteBendButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteBendButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        deleteBendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBendButtonActionPerformed(evt);
            }
        });
        toolBar.add(deleteBendButton);

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

        recentFilesMenu.setText("Recent files");
        fileMenu.add(recentFilesMenu);

        settingsItem.setText("Settings");
        settingsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsItemActionPerformed(evt);
            }
        });
        fileMenu.add(settingsItem);

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

        RASMenuItem.setText("RAS Checker");
        RASMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RASMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(RASMenuItem);

        shorcutConfig.setText("Shortcut config");
        shorcutConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shorcutConfigActionPerformed(evt);
            }
        });
        editMenu.add(shorcutConfig);

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
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(diagramScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sideMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(diagramScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sideMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
        System.exit(0);        // TODO add your handling code here:
    }//GEN-LAST:event_exitItemActionPerformed

    private void saveAsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsItemActionPerformed
        this.fileManager.saveGraphAs(graph, this);
        this.getInfoAboutFile(this.fileManager.getSelectedFile());
    }//GEN-LAST:event_saveAsItemActionPerformed

    private void getInfoAboutFile(File file) {
        selectedFileName = this.fileManager.getSelectedFileName();
        selectedFilePath = this.fileManager.getSelectedFilePath();
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
        this.setPopMenu(new PopUpMenu(getDiagramPanel()));
        diagramScrollPane.setViewportView(this.getDiagramPanel());    
        this.getDiagramPanel().setFocusable(true);
        this.getDiagramPanel().requestFocusInWindow();
        this.diagramScrollPane.setFocusable(false);
        diagramScrollPane.getViewport().setViewPosition(new java.awt.Point(4750, 4750));
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
        zoomAllButton.setVisible(true);
        rectangleButton.setVisible(true);
        resuorceButton.setVisible(true);
        //cursorButton.setSelected(true);
        curButton.setVisible(true);
        bendButton.setVisible(true);
        deleteBendButton.setVisible(true);

        toolBar.setEnabled(true);
        getInfoAboutModel(graph);
    }//GEN-LAST:event_newPetriNetActionPerformed

    private void aboutUsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aboutUsMouseClicked
        //AboutUs about = new AboutUs(this, rootPaneCheckingEnabled);
        about.setLocationRelativeTo(this);
        about.setVisible(true);
    }//GEN-LAST:event_aboutUsMouseClicked

    private void ellipseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ellipseButtonActionPerformed
        this.toggleButtons(evt);
    }//GEN-LAST:event_ellipseButtonActionPerformed

    private void rectangleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rectangleButtonActionPerformed
        this.toggleButtons(evt);
    }//GEN-LAST:event_rectangleButtonActionPerformed

    private void lineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lineButtonActionPerformed
        this.toggleButtons(evt);
    }//GEN-LAST:event_lineButtonActionPerformed

    private void loadItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadItemActionPerformed
        graph = this.fileManager.loadGraph(this);
        int j = 0;
        for (int i = this.fileManager.getBufferRecentFiles().size()-1; i >= Math.max(0,this.fileManager.getBufferRecentFiles().size()-5); i--) {
            this.generalSettingsManager.getRecentFiles()[j] = this.fileManager.getBufferRecentFiles().get(i);
            j++;
        }
        getInfoAboutFile(this.fileManager.getSelectedFile());
        drawLoadGraph(graph);
        curButton.setSelected(true);
    }//GEN-LAST:event_loadItemActionPerformed

    public void drawLoadGraph(Graph graph){
        if (graph==null) return ;
        controller.setModel(graph);
        this.diagramPanel = new DiagramPanel(graph);
        this.setPopMenu(new PopUpMenu(getDiagramPanel()));
        diagramScrollPane.setViewportView(this.getDiagramPanel());
        int x = 0;
        int y = 0;
        if (graph instanceof PetriNet) {
            x = ((PetriNet) graph).getListOfPlaces().get(0).getX();
            y = ((PetriNet) graph).getListOfPlaces().get(0).getY();
        } else if (graph instanceof PrecedenceGraph) {
            x = ((PrecedenceGraph) graph).getListOfNodes().get(0).getX();
            y = ((PrecedenceGraph) graph).getListOfNodes().get(0).getY();
        }
        if ((x - 50) < 0 || (y - 50 < 0)) {
            x = 0;
            y = 0;
        }

        diagramScrollPane.getViewport().setViewPosition(new java.awt.Point(x - 50, y - 50));

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
            zoomAllButton.setVisible(true);
            deleteBendButton.setVisible(true);
            bendButton.setVisible(true);

        ///
        getInfoAboutModel(graph);
    }
    
    private void saveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveItemActionPerformed
        this.fileManager.saveGraph(graph, this);
        getInfoAboutFile(this.fileManager.getSelectedFile());

    }//GEN-LAST:event_saveItemActionPerformed

    private void notesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_notesFocusLost
        
    }//GEN-LAST:event_notesFocusLost

    private void resuorceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resuorceButtonActionPerformed
        this.toggleButtons(evt);
    }//GEN-LAST:event_resuorceButtonActionPerformed

    private void newPrecedenceNetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newPrecedenceNetActionPerformed
        selectedFile = null;
        PrecedenceGraph pg = new PrecedenceGraph("New precedence graph");
        graph = pg;
        controller.setModel(graph);
        this.diagramPanel = new DiagramPanel(graph);
        this.setPopMenu(new PopUpMenu(getDiagramPanel()));
        diagramScrollPane.setViewportView(this.getDiagramPanel());
        diagramScrollPane.getViewport().setViewPosition(new java.awt.Point(4750, 4750));
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
        zoomAllButton.setVisible(true);
        //cursorButton.setSelected(true);
        curButton.setSelected(true);
        bendButton.setVisible(true);
        deleteBendButton.setVisible(true);

        rectangleButton.setVisible(false);
        resuorceButton.setVisible(false);
        toolBar.setEnabled(true);
        getInfoAboutModel(graph);
    }//GEN-LAST:event_newPrecedenceNetActionPerformed

    private void convertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_convertActionPerformed
        if (graph != null && graph instanceof PrecedenceGraph) {
            PrecedenceGraph pg = (PrecedenceGraph) graph;
            PetriNet converted = pg.changePrecedenceGraphToPN();
            graph = converted;
            controller.setModel(converted);
            this.setPopMenu(new PopUpMenu(getDiagramPanel()));
            this.diagramPanel = new DiagramPanel(converted);
            diagramScrollPane.setViewportView(this.getDiagramPanel());
            diagramScrollPane.getViewport().setViewPosition(new java.awt.Point(4750, 4750));
            sideMenu.setVisible(true);
            // hide side menu
            propertiesMenu.setVisible(false);
            getInfoAboutModel(graph);
            rectangleButton.setVisible(true);
            resuorceButton.setVisible(true);
        }
    }//GEN-LAST:event_convertActionPerformed

    private void diagramScrollPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_diagramScrollPaneMouseClicked
    }//GEN-LAST:event_diagramScrollPaneMouseClicked

    private void diagramScrollPaneMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_diagramScrollPaneMousePressed
    }//GEN-LAST:event_diagramScrollPaneMousePressed

    private void create_state_diagramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_create_state_diagramActionPerformed
        if (graph != null) {


            PetriNet ssPetriNet = null;
            if (graph instanceof PetriNet) {
                ssPetriNet = (PetriNet) graph;
            }
            if (graph instanceof PrecedenceGraph) {
                ssPetriNet = ((PrecedenceGraph) graph).changePrecedenceGraphToPN();
            }

            StateSpaceCalculator ssCalc = new StateSpaceCalculator(ssPetriNet);
            ssCalc.calculateStateSpace();
            ssPetriNet.setStates(ssCalc.getResult());
            StatesOutputDialog sod = new StatesOutputDialog(this, true);
            sod.setStates(ssCalc.getResult());
            sod.setVisible(true);
            
            
        }
    }//GEN-LAST:event_create_state_diagramActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.resetForm();
        }
    }//GEN-LAST:event_formKeyPressed

    private void alignTopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alignTopButtonActionPerformed
        controller.alignTop(getDiagramPanel().selectedElements);
        repaint();
    }//GEN-LAST:event_alignTopButtonActionPerformed

    private void alignBottomButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alignBottomButtonActionPerformed
        controller.alignBottom(getDiagramPanel().selectedElements);
        repaint();
    }//GEN-LAST:event_alignBottomButtonActionPerformed

    private void alignLeftButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alignLeftButtonActionPerformed
        controller.alignLeft(getDiagramPanel().selectedElements);
        repaint();
    }//GEN-LAST:event_alignLeftButtonActionPerformed

    private void alignRightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alignRightButtonActionPerformed
        controller.alignRight(getDiagramPanel().selectedElements);
        repaint();
    }//GEN-LAST:event_alignRightButtonActionPerformed

    private void zoomInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomInButtonActionPerformed
        diagramPanel.getScaleRatio()[0] += 0.25;
        diagramPanel.getScaleRatio()[1] += 0.25;
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = 0;
        int maxY = 0;
        Element e = null, e2 = null;
        ArrayList<Element> elements = new ArrayList<Element>();
        if (this.graph instanceof PetriNet) {
            PetriNet pn = (PetriNet) this.graph;
            elements = new ArrayList<Element>();
            elements.addAll(pn.getListOfArcs());
            elements.addAll(pn.getListOfPlaces());
            elements.addAll(pn.getListOfResources());
            elements.addAll(pn.getListOfTransitions());
        }
        if (this.graph instanceof PrecedenceGraph) {
            PrecedenceGraph pg = (PrecedenceGraph) this.graph;
            elements = new ArrayList<Element>();
            elements.addAll(pg.getListOfArcs());
            elements.addAll(pg.getListOfNodes());
        }
        for (Element element : elements) {
            if (minX > element.getX()) {
                minX = element.getX();
                e = element;
            }
            if (minY > element.getY()) {
                minY = element.getY();
                e2 = element;
            }
            if (maxX < element.getX()) {
                maxX = element.getX();
            }
            if (maxY < element.getY()) {
                maxY = element.getY();
            }
        }
        int graphWidth = maxX - minX;
        int graphHeight = maxY - minY;
        int w = (int) (((diagramScrollPane.getWidth()/2)-(graphWidth/2))/getDiagramPanel().getScaleRatio()[0]);
        int h = (int) (((diagramScrollPane.getHeight()/2)-(graphHeight/2))/getDiagramPanel().getScaleRatio()[1]);
        int newX = (int) ((minX-w)*this.getDiagramPanel().getScaleRatio()[0]);
        int newY = (int) ((minY-h)*this.getDiagramPanel().getScaleRatio()[1]);
        this.diagramScrollPane.getViewport().setViewPosition(new java.awt.Point(newX,newY));
        repaint();
    }//GEN-LAST:event_zoomInButtonActionPerformed

    private void zoomOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomOutButtonActionPerformed
        diagramPanel.getScaleRatio()[0] -= 0.25;
        diagramPanel.getScaleRatio()[1] -= 0.25;
        diagramPanel.getScaleRatio()[0] = Math.max(0.3, getDiagramPanel().getScaleRatio()[0]);
        diagramPanel.getScaleRatio()[1] = Math.max(0.3, getDiagramPanel().getScaleRatio()[1]);
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = 0;
        int maxY = 0;
        Element e = null;
        Element e2 = null;
        ArrayList<Element> elements = new ArrayList<Element>();
        if (this.graph instanceof PetriNet) {
            PetriNet pn = (PetriNet) this.graph;
            elements = new ArrayList<Element>();
            elements.addAll(pn.getListOfArcs());
            elements.addAll(pn.getListOfPlaces());
            elements.addAll(pn.getListOfResources());
            elements.addAll(pn.getListOfTransitions());
        }
        if (this.graph instanceof PrecedenceGraph) {
            PrecedenceGraph pg = (PrecedenceGraph) this.graph;
            elements = new ArrayList<Element>();
            elements.addAll(pg.getListOfArcs());
            elements.addAll(pg.getListOfNodes());
        }
        for (Element element : elements) {
            if (minX > element.getX()) {
                minX = element.getX();
                e = element;
            }
            if (minY > element.getY()) {
                minY = element.getY();
                e2 = element;
            }
            if (maxX < element.getX()) {
                maxX = element.getX();
            }
            if (maxY < element.getY()) {
                maxY = element.getY();
            }
        }
        int graphWidth = maxX - minX;
        int graphHeight = maxY - minY;
        int w = (int) (((this.diagramScrollPane.getWidth()/2)-(graphWidth/2))/this.getDiagramPanel().getScaleRatio()[0]);
        int h = (int) (((this.diagramScrollPane.getHeight()/2)-(graphHeight/2))/this.getDiagramPanel().getScaleRatio()[1]);
        int newX = (int) ((minX-w-50/this.getDiagramPanel().getScaleRatio()[0])*this.getDiagramPanel().getScaleRatio()[0]);
        int newY = (int) ((minY-h-50/this.getDiagramPanel().getScaleRatio()[0])*this.getDiagramPanel().getScaleRatio()[1]);
        this.diagramScrollPane.getViewport().setViewPosition(new java.awt.Point(newX,newY));
        repaint();
    }//GEN-LAST:event_zoomOutButtonActionPerformed

    private void resetZoomButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetZoomButtonActionPerformed
        diagramPanel.getScaleRatio()[0] = 1;
        diagramPanel.getScaleRatio()[1] = 1;
        diagramPanel.getScaleRatio()[0] = Math.max(0.3, getDiagramPanel().getScaleRatio()[0]);
        diagramPanel.getScaleRatio()[1] = Math.max(0.3, getDiagramPanel().getScaleRatio()[1]);
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = 0;
        int maxY = 0;
        Element e = null;
        Element e2 = null;
        ArrayList<Element> elements = new ArrayList<Element>();
        if (this.graph instanceof PetriNet) {
            PetriNet pn = (PetriNet) this.graph;
            elements = new ArrayList<Element>();
            elements.addAll(pn.getListOfArcs());
            elements.addAll(pn.getListOfPlaces());
            elements.addAll(pn.getListOfResources());
            elements.addAll(pn.getListOfTransitions());
        }
        if (this.graph instanceof PrecedenceGraph) {
            PrecedenceGraph pg = (PrecedenceGraph) this.graph;
            elements = new ArrayList<Element>();
            elements.addAll(pg.getListOfArcs());
            elements.addAll(pg.getListOfNodes());
        }
        for (Element element : elements) {
            if (minX > element.getX()) {
                minX = element.getX();
                e = element;
            }
            if (minY > element.getY()) {
                minY = element.getY();
                e2 = element;
            }
            if (maxX < element.getX()) {
                maxX = element.getX();
            }
            if (maxY < element.getY()) {
                maxY = element.getY();
            }
        }
        int graphWidth = maxX - minX;
        int graphHeight = maxY - minY;
        int w = (int) (((this.diagramScrollPane.getWidth()/2)-(graphWidth/2))/this.getDiagramPanel().getScaleRatio()[0]);
        int h = (int) (((this.diagramScrollPane.getHeight()/2)-(graphHeight/2))/this.getDiagramPanel().getScaleRatio()[1]);
        int newX = (int) ((minX-w-50/this.getDiagramPanel().getScaleRatio()[0])*this.getDiagramPanel().getScaleRatio()[0]);
        int newY = (int) ((minY-h-50/this.getDiagramPanel().getScaleRatio()[0])*this.getDiagramPanel().getScaleRatio()[1]);
        this.diagramScrollPane.getViewport().setViewPosition(new java.awt.Point(newX,newY));
        repaint();
    }//GEN-LAST:event_resetZoomButtonActionPerformed

    private void diagramScrollPaneMouseDragged(java.awt.event.MouseEvent evt) {
    }

    private void updateComponentList() {
        ArrayList<Element> elements = new ArrayList<Element>();
        if (this.graph instanceof PetriNet) {
            PetriNet pn = (PetriNet) this.graph;
            elements = new ArrayList<Element>();
            elements.addAll(pn.getListOfArcs());
            elements.addAll(pn.getListOfPlaces());
            elements.addAll(pn.getListOfResources());
            elements.addAll(pn.getListOfTransitions());
        }
        if (this.graph instanceof PrecedenceGraph) {
            PrecedenceGraph pg = (PrecedenceGraph) this.graph;
            elements = new ArrayList<Element>();
            elements.addAll(pg.getListOfArcs());
            elements.addAll(pg.getListOfNodes());
        }
        //ArrayList<String> componentsName = new ArrayList<String>();
        this.listModel.removeAllElements();
        for (Element element : elements) {
            //componentsName.add(element.getName());
            this.listModel.addElement(element);
        }

        this.componentList.removeAll();
        this.componentList.setModel(this.listModel);
    }

    private void diagramScrollPaneMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_diagramScrollPaneMouseMoved
    }//GEN-LAST:event_diagramScrollPaneMouseMoved

    private void notesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_notesKeyPressed
    }//GEN-LAST:event_notesKeyPressed

    private void notesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_notesKeyReleased
    }//GEN-LAST:event_notesKeyReleased

    private void diagramScrollPaneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_diagramScrollPaneKeyPressed

    }//GEN-LAST:event_diagramScrollPaneKeyPressed

    private void componentListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_componentListValueChanged
        if(this.componentList.getSelectedValuesList().isEmpty())return;
        this.getDiagramPanel().selectedElements.clear();
        ArrayList<Element> elements = (ArrayList<Element>) this.componentList.getSelectedValuesList();             
        for (Element element : elements) {
            this.getDiagramPanel().selectedElements.add(element);
        }
        if(elements.size()==1){
            diagramPanel.loadElementProperties(elements.get(0));
            propertiesMenu.setVisible(true);
        }else{
            diagramPanel.loadMultipleProperties(elements);
            propertiesMenu.setVisible(true);
        }
        this.getDiagramPanel().repaint();
    }//GEN-LAST:event_componentListValueChanged

    private void componentListKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_componentListKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_C && evt.isControlDown()){
            this.getDiagramPanel().copySelectedElements();
        }
        if(evt.getKeyCode() == KeyEvent.VK_V && evt.isControlDown()){
            this.getDiagramPanel().pasteSelectedElements(0, 0);
        }
        if(evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.getDiagramPanel().deleteSelectedElements();
        }
    }//GEN-LAST:event_componentListKeyReleased

    private void shorcutConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shorcutConfigActionPerformed
        ShortcutConfig shortcutConfig = new ShortcutConfig(this, true);
        shortcutConfig.setLocationRelativeTo(this);
        shortcutConfig.setVisible(true);
    }//GEN-LAST:event_shorcutConfigActionPerformed

    private void horizontalMagnetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_horizontalMagnetButtonActionPerformed
        this.toggleButtons(evt);
    }//GEN-LAST:event_horizontalMagnetButtonActionPerformed

    private void verticalMagnetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verticalMagnetButtonActionPerformed
        this.toggleButtons(evt);
    }//GEN-LAST:event_verticalMagnetButtonActionPerformed

    private void zoomAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomAllButtonActionPerformed
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = 0;
        int maxY = 0;
        Element minEX = null;
        Element minEY = null;
        Element maxEX = null;
        Element maxEY = null;
        ArrayList<Element> elements = new ArrayList<Element>();
        if (this.graph instanceof PetriNet) {
            PetriNet pn = (PetriNet) this.graph;
            elements = new ArrayList<Element>();
            elements.addAll(pn.getListOfArcs());
            elements.addAll(pn.getListOfPlaces());
            elements.addAll(pn.getListOfResources());
            elements.addAll(pn.getListOfTransitions());
        }
        if (this.graph instanceof PrecedenceGraph) {
            PrecedenceGraph pg = (PrecedenceGraph) this.graph;
            elements = new ArrayList<Element>();
            elements.addAll(pg.getListOfArcs());
            elements.addAll(pg.getListOfNodes());
        }
        for (Element element : elements) {
            if (minX > element.getX()) {
                minX = element.getX();
                minEX = element;
            }
            if (minY > element.getY()) {
                minY = element.getY();
                minEY = element;
            }
            if (maxX < element.getX()) {
                maxX = element.getX();
                maxEX = element;
            }
            if (maxY < element.getY()) {
                maxY = element.getY();
                maxEY = element;
            }
        }
        int graphWidth = maxX - minX;
        int graphHeight = maxY - minY;
        int maxGraphSize = Math.max(graphHeight, graphWidth);
        int minDiagramSize = Math.min(this.diagramScrollPane.getWidth(), this.diagramScrollPane.getHeight());
        double diagramSlant = Math.sqrt((minDiagramSize * minDiagramSize) + (minDiagramSize * minDiagramSize));
        double graphSlant = Math.sqrt((maxGraphSize * maxGraphSize) + (maxGraphSize * maxGraphSize));
        if (graphSlant > diagramSlant) {
            this.diagramPanel.getScaleRatio()[1] = (diagramSlant / graphSlant) - 0.05;
            this.diagramPanel.getScaleRatio()[0] = (diagramSlant / graphSlant) - 0.05;
        } else {
            this.diagramPanel.getScaleRatio()[1] = 1;
            this.diagramPanel.getScaleRatio()[0] = 1;
        }
        this.diagramScrollPane.getViewport().setViewPosition(new java.awt.Point((int)(minX*this.getDiagramPanel().getScaleRatio()[0]),(int)(minY*this.getDiagramPanel().getScaleRatio()[1])));
        repaint();        
    }//GEN-LAST:event_zoomAllButtonActionPerformed

    private void settingsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsItemActionPerformed
        GeneralSettings generalSettings = new GeneralSettings(this, true, this.generalSettingsManager);
        generalSettings.setLocationRelativeTo(this);
        generalSettings.setVisible(true);
        if(diagramPanel != null){
            if(this.diagramPanel.getBackground().getRGB() != this.generalSettingsManager.getBackgroundColor()){
                this.diagramPanel.setBackground(new Color(this.generalSettingsManager.getBackgroundColor()));
                this.diagramPanel.repaint();
            }
        }
    }//GEN-LAST:event_settingsItemActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Dimension dimension = getSize();
        Point location = getLocationOnScreen();
        this.generalSettingsManager.setWindowHeight(dimension.height);
        this.generalSettingsManager.setWindowWidth(dimension.width);
        this.generalSettingsManager.setWindowX(location.x);
        this.generalSettingsManager.setWindowY(location.y);
        this.generalSettingsManager.writeConfig();
        this.generalSettingsManager.writeRecentFile();
    }//GEN-LAST:event_formWindowClosing

    private void bendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bendButtonActionPerformed
        this.toggleButtons(evt);
    }//GEN-LAST:event_bendButtonActionPerformed

    private void deleteBendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBendButtonActionPerformed
        this.toggleButtons(evt);
    }//GEN-LAST:event_deleteBendButtonActionPerformed

    private void curButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_curButtonActionPerformed
        this.toggleButtons(evt);
    }//GEN-LAST:event_curButtonActionPerformed

    private void RASMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RASMenuItemActionPerformed
        RASCheckerWindow rasWin = new RASCheckerWindow(this,true,(PetriNet)this.graph);
        rasWin.setVisible(true);
    }//GEN-LAST:event_RASMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem RASMenuItem;
    private javax.swing.JMenu aboutUs;
    private javax.swing.JButton alignBottomButton;
    private javax.swing.JButton alignLeftButton;
    private javax.swing.JButton alignRightButton;
    private javax.swing.JButton alignTopButton;
    private javax.swing.JLabel backgroundImageLabel;
    private javax.swing.JToggleButton bendButton;
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JList componentList;
    private javax.swing.JMenuItem convert;
    private javax.swing.JMenuItem create_state_diagram;
    private javax.swing.JToggleButton curButton;
    private javax.swing.JToggleButton deleteBendButton;
    private javax.swing.JScrollPane diagramScrollPane;
    private javax.swing.JMenu editMenu;
    private javax.swing.JToggleButton ellipseButton;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JMenuItem export;
    private javax.swing.JMenu fileMenu;
    private GUI.PropertiesMenu generalProperties;
    private javax.swing.JToggleButton horizontalMagnetButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton lineButton;
    private javax.swing.JMenuItem loadItem;
    private javax.swing.JLabel modelInfo;
    private javax.swing.JMenuItem newPetriNet;
    private javax.swing.JMenuItem newPrecedenceNet;
    private javax.swing.JTextArea notes;
    private javax.swing.JPanel propertiesMenu;
    private javax.swing.JTabbedPane propertiesTab;
    private javax.swing.JMenu recentFilesMenu;
    private javax.swing.JToggleButton rectangleButton;
    private javax.swing.JButton resetZoomButton;
    private javax.swing.JToggleButton resuorceButton;
    private javax.swing.JMenuItem saveAsItem;
    private javax.swing.JMenuItem saveItem;
    private javax.swing.JMenuItem settingsItem;
    private javax.swing.JMenuItem shorcutConfig;
    private javax.swing.JPanel sideMenu;
    private javax.swing.JToolBar toolBar;
    private javax.swing.JMenuBar topMenu;
    private javax.swing.JToggleButton verticalMagnetButton;
    private javax.swing.JButton zoomAllButton;
    private javax.swing.JButton zoomInButton;
    private javax.swing.JButton zoomOutButton;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the shortcutsManager
     */
    public ShortcutsManager getShortcutsManager() {
        return shortcutsManager;
    }

    /**
     * @param shortcutsManager the shortcutsManager to set
     */
    public void setShortcutsManager(ShortcutsManager shortcutsManager) {
        this.shortcutsManager = shortcutsManager;
    }

    /**
     * @return the popMenu
     */
    public PopUpMenu getPopMenu() {
        return popMenu;
    }

    /**
     * @param popMenu the popMenu to set
     */
    public void setPopMenu(PopUpMenu popMenu) {
        this.popMenu = popMenu;
    }

    /**
     * @return the diagramPanel
     */
    public DiagramPanel getDiagramPanel() {
        return diagramPanel;
    }

    public class DiagramPanel extends javax.swing.JPanel {

        private DiagramMouseAdapter mouseAdapter;
        private Graph graph;
        private Graphics2D g2d;
        private ArrayList<Element> selectedElements;
        private ArrayList<Element> copiedElements;
        private Element draggedElement;
        private Object draggedObject;
        private Color draggedColor;
        private double[] scaleRatio;
        private boolean isCTRLdown = false;
        private int clickedX;
        private int clickedY;
        private Element bubbleElement;
        HashMap<Element,Element> places = new HashMap<Element, Element>();
        HashMap<Element,Element> transitions = new HashMap<Element, Element>();
        HashMap<Element,Element> nodes = new HashMap<Element, Element>();
        ArrayList<PairValue<Element,Element,Arc>> inToOutElement = new ArrayList<PairValue<Element, Element,Arc>>();
        PairValue<Element,Element,Arc> pair;
        int offset = 50;
        private Timer timer;
        //magnetic lines
        private MagneticLine selectedMagneticLine;

        /**
         * Creates new form GraphPanel
         */
        public DiagramPanel(Graph pa_graph) {
            super();
            this.graph = pa_graph;
            this.scaleRatio = new double[]{1.0, 1.0};
            this.draggedObject = null;
            this.draggedElement = null;
            this.selectedElements = new ArrayList<Element>();
            this.copiedElements = new ArrayList<Element>();
            this.mouseAdapter = new DiagramMouseAdapter(this,diagramScrollPane);
            // Click listener, drag listener
            addMouseListener(mouseAdapter);
            addMouseMotionListener(mouseAdapter);
            addMouseWheelListener(new DiagramMouseWheelAdapter(this, diagramScrollPane));
            addKeyListener(new DiagramKeyAdapter(this, shortcutsManager));
            // Max sirka,vyska = 1000x1000
            setPreferredSize(new Dimension(10000, 10000));
            setBackground(new Color(generalSettingsManager.getBackgroundColor()));
            setBackground(Color.WHITE);

            notes.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent event) {
                    String tempNote = notes.getText();
                    selectedElements.get(0).setNote(tempNote);
                }
            });
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            this.g2d = (Graphics2D) g;
            //g2d.scale(scaleRatio[0], scaleRatio[1]);
            g2d.setStroke(new BasicStroke(3));
            drawMagneticLines();
            autosetWidthHeight();
            drawGraph();
            drawDraggedObject();
            drawSelectedElements();
            drawBuble();
            
        }

        public Controller getDiagramController(){
            return controller;
        }
        
        public void saveGraph(){
            fileManager.saveGraph(getGraph(), this);
        }

        public void saveGraphAs() {
            fileManager.saveGraphAs(getGraph(), this);
        }

        public void loadGraph() {
            setGraph(fileManager.loadGraph(this));
        }

        public void drawBuble() {
            if (getBubbleElement() != null && curButton.isSelected() && draggedElement == null && draggedObject == null) {
                int width = 0;
                int height = 0;
                if (getBubbleElement() instanceof AbsPlace) {
                    AbsPlace n = (AbsPlace) getBubbleElement();
                    width = n.getWidth();
                    height = n.getHeight();
                } else if (getBubbleElement() instanceof Transition) {
                    Transition n = (Transition) getBubbleElement();
                    width = n.getWidth();
                    height = n.getHeight();
                } else {
                    Node n = (Node) getBubbleElement();
                    width = n.getWidth();
                    height = n.getHeight();
                }
                SpeechBubble.drawBubble(g2d, getBubbleElement().getX() + width / 2, getBubbleElement().getY(), getBubbleElement().getNote());
            }
        }

        public void drawPlace(int column, int row, Color c1, Color c2, int width, int height, String name, int fontSize) {
            // Place / Ring
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

        public int[] drawArrow(Arc a, int x1, int y1, int midX, int midY, int x2, int y2, String type, String name, int fontSize, Element e, int width, int height, int capacity) {
            // Size of arrow in px
            int ARR_SIZE = 8;
            int[] field = solvePointsOfArrow(x1, x2, y1, y2, midX, midY, e, width, height);
            midX = field[0];
            midY = field[1];

            a.setOutPoint(new Point(x1, y1));
            a.setInPoint(new Point(midX, midY));

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

                int x, y;
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
            //System.out.println("HOVADINA");
            //a.setOutPoint(new Point(x1, y1));
            //a.setInPoint(new Point(x2, y2));

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

        public int[] drawArc(Arc arc, int column1, int row1, int width1, int height1, int column2, int row2, int width2, int height2) {
            // Arc / Arrow
            //arc, out.getX(), out.getY(), width2, height2, in.getX(), in.getY(), width1, height1, arc.getColor(), arc.getName(), arc.getFontSize(), arc.getInElement(), arc.getInElement().getX(), arc.getInElement().getY(), capacity
            Color color = arc.getColor();
            String name = arc.getName();
            int fontSize = arc.getFontSize();
            Element e = arc.getInElement();
            int upperLeftAngleX = arc.getInElement().getX();
            int upperLeftAngleY = arc.getInElement().getY();
            int capacity = arc.getCapacity();
            //int from


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

            Point lastPoint = new Point(column1 + (int) (width1 / 2.0), row1 + (int) (height1 / 2.0));

            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(3));
            if (arc.getBendPoints().size() > 0) {

                for (Point nextPoint : arc.getBendPoints()) {
                    g2d.drawLine((int) lastPoint.getX(), (int) lastPoint.getY(), (int) nextPoint.getX(), (int) nextPoint.getY());
                    Color tempCol = g2d.getColor();
                    g2d.setColor(Color.black);
                    g2d.fillRect((int) nextPoint.getX() - 5, (int) nextPoint.getY() - 5, 10, 10);


                    lastPoint = nextPoint;
                    g2d.setColor(tempCol);
                }

            }


            // Draw arrow       
            int intercection[];

            intercection = drawArrow(arc, (int) lastPoint.getX(), (int) lastPoint.getY(),
                    column2 + (int) (width2 / 2.0), row2 + (int) (height2 / 2.0), upperLeftAngleX, upperLeftAngleY,
                    "short", name, fontSize, e, width2, height2, capacity);

//            intercection = drawArrow(column1 + (int) (width1 / 2.0), row1 + (int) (height1 / 2.0),
//                    column2 + (int) (width2 / 2.0), row2 + (int) (height2 / 2.0), upperLeftAngleX, upperLeftAngleY,
//                    "short", name, fontSize, e, width2, height2, capacity);

            return intercection;
        }

        public void drawArcSelected(Arc a, int column1, int row1, int column2, int row2) {
            /* Calculating */
            // Get line between 2 points
            //Line2D.Double ln = new Line2D.Double(column1 + 25, row1 + 25, column2 + 25, row2 + 25);
            int inc = 0;
            double lastX = column1;
            double lastY = row1;
            double k1 = 0;
            double k2 = 0;

            Path2D path = new Path2D.Double();

            for (Point nextPoint : a.getBendPoints()) {
                inc++;
                // Get line between 2 points
                Line2D.Double ln = new Line2D.Double(lastX, lastY, nextPoint.getX(), nextPoint.getY());

                // Distance from central line
                double indent = 10.0;
                double length = ln.getP1().distance(ln.getP2());

                double dx_li = (ln.getX2() - ln.getX1()) / length * indent;
                double dy_li = (ln.getY2() - ln.getY1()) / length * indent;

                // line moved to the left
                double lX1 = ln.getX1() - dy_li;
                double lY1 = ln.getY1() + dx_li;
                double lX2 = ln.getX2() - dy_li;
                double lY2 = ln.getY2() + dx_li;

                // line moved to the right
                double rX1 = ln.getX1() + dy_li;
                double rY1 = ln.getY1() - dx_li;
                double rX2 = ln.getX2() + dy_li;
                double rY2 = ln.getY2() - dx_li;



                if (inc == 1) {
                    path.moveTo(lX1, lY1);
                    k1 = rX1;
                    k2 = rY1;

                }
                path.moveTo(lX1, lY1);
                path.lineTo(lX1, lY1);
                path.lineTo(lX2, lY2);
                //path.lineTo(p2X, p2Y);
                //path.moveTo(rX2, rY2);
                path.lineTo(rX2, rY2);
                path.lineTo(rX1, rY1);

                lastX = nextPoint.getX();
                lastY = nextPoint.getY();

            }

            // Get line between 2 points
            Line2D.Double ln = new Line2D.Double(lastX, lastY, column2, row2);

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
            double rX1 = ln.getX1() + dy_li;
            double rY1 = ln.getY1() - dx_li;
            double rX2 = ln.getX2() + dy_li;
            double rY2 = ln.getY2() - dx_li;

            if (inc == 0) {
                //path.moveTo(lX1, lY1);
                k1 = rX1;
                k2 = rY1;
            }
            path.moveTo(lX1, lY1);
            path.lineTo(lX1, lY1);
            path.lineTo(lX2, lY2);
            //path.lineTo(p2X, p2Y);
            path.lineTo(rX2, rY2);
            path.lineTo(rX1, rY1);

            path.moveTo(k1, k2);
            path.closePath();

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
            if (getGraph() == null) {
                return;
            }
            if (getGraph() instanceof PetriNet) {
                // Arcs , Places, Transitions
                // Draw all arcs
                for (Element e : ((PetriNet) getGraph()).getListOfArcs()) {
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
                    intersection = drawArc(arc, out.getX(), out.getY(), width2, height2, in.getX(), in.getY(), width1, height1);
                    //drawArc(out.getX(), out.getY(), width1, height1, in.getX(), in.getY(), width2, height2, arc.getColor(), arc.getName(), arc.getFontSize());
                    arc.setIntercectionX(intersection[0]);
                    arc.setIntercectionY(intersection[1]);
                }

                // Draw all places
                for (Element e : ((PetriNet) getGraph()).getListOfPlaces()) {
                    drawPlace(e.getX(), e.getY(), e.getColor(), e.getColor2(), ((Place) e).getWidth(), ((Place) e).getHeight(), e.getName() + " :" + ((Place) e).getMarkings(), e.getFontSize());
                }

                // Draw all resources
                for (Element e : ((PetriNet) getGraph()).getListOfResources()) {
                    drawResource(e.getX(), e.getY(), e.getColor(), e.getColor2(), ((Resource) e).getWidth(), ((Resource) e).getHeight(), e.getName() + " :" + ((Resource) e).getMarking(), e.getFontSize());
                }

                // Draw all transitions
                for (Element e : ((PetriNet) getGraph()).getListOfTransitions()) {
                    drawTransition(e.getX(), e.getY(), e.getColor(), e.getColor2(), ((Transition) e).getWidth(), ((Transition) e).getHeight(), e.getName(), e.getFontSize());
                }


                return;
            }   // Koniec Petri net

            if (getGraph() instanceof PrecedenceGraph) {
                // Arcs, Nodes

                // Draw all arcs
                for (Element e : ((PrecedenceGraph) getGraph()).getListOfArcs()) {
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
                    intersection = drawArc(arc, out.getX(), out.getY(), width2, height2, in.getX(), in.getY(), width1, height1);
                    arc.setIntercectionX(intersection[0]);
                    arc.setIntercectionY(intersection[1]);
                }

                // Draw all nodes
                for (Element e : ((PrecedenceGraph) getGraph()).getListOfNodes()) {
                    drawPlace(e.getX(), e.getY(), e.getColor(), e.getColor2(), ((Node) e).getWidth(), ((Node) e).getHeight(), e.getName() + " :" + ((Node) e).getCapacity(), e.getFontSize());
                }

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
                //Arc a=(Arc) draggedObject;

                // Arc is selected when it is moving
                //Arc _arc=(Arc)selectedElements.get(0); 
                drawArrow(x1, y1, x2, y2, "long", "", 0);//_arc.getName(),_arc.getFontSize());
            }

        }

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

                    Element in = ((Arc) e).getInElement();
                    Element out = ((Arc) e).getOutElement();

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

                    drawArcSelected((Arc) e, outX, outY, ((Arc) e).getIntercectionX(), ((Arc) e).getIntercectionY());
                }
            }
        }
        
        public void drawMagneticLines(){
            Stroke oldStroke = g2d.getStroke();
            for (MagneticLine magneticLine : graph.getMagneticLines()) {
                if(getSelectedMagneticLine() == magneticLine){
                    this.g2d.setColor(new Color(81, 22, 22));
                    this.g2d.setStroke(new BasicStroke(2.5f));
                }else{
                    this.g2d.setColor(new Color(183, 152, 152));
                    this.g2d.setStroke(new BasicStroke(0.7f));
                }
                this.g2d.drawLine((int)(magneticLine.x1*getScaleRatio()[0]), (int)(magneticLine.y1*getScaleRatio()[1]), (int)(magneticLine.x2*getScaleRatio()[0]), (int)(magneticLine.y2*getScaleRatio()[1]));   
                this.g2d.setStroke(oldStroke);
            }
        }
        

        public void mouseLeftClick(int x, int y) {
            // Select 1 element
            Point selectedArcPoint = null;
            x = (int) (x / getScaleRatio()[0]);// +(1-scaleRatio[0])*(x-0));// --scaleRatio[0])*x/2);
            y = (int) (y / getScaleRatio()[1]);// +(1-scaleRatio[1])*(y-0));// -(1-scaleRatio[1])*y/2);

            /* If CTRL is pressed, dont clear list, just add item */
            if (isIsCTRLdown() == false) {
                selectedElements.clear();
                setSelectedMagneticLine(null);
            }
            Element e = null;
            
            Object o = controller.getLocationElement(x, y);
            if (o instanceof Element) {
                e = (Element) o;
            } else if (o instanceof Point) {
                draggedObject = (Point) o;
                if (getGraph() instanceof PetriNet) {
                    Arc tempArc = ((PetriNet) getGraph()).getArc((Point) draggedObject);
                    selectedElements.add(tempArc);
                    if (deleteBendButton.isSelected()) {
                        tempArc.removeBendPoint((Point) draggedObject);
                        return;
                    }
                } else if (getGraph() instanceof PrecedenceGraph) {
                    Arc tempArc = ((PrecedenceGraph) getGraph()).getArc((Point) draggedObject);
                    selectedElements.add(tempArc);
                    if (deleteBendButton.isSelected()) {
                        tempArc.removeBendPoint((Point) draggedObject);
                        return;
                    }
                }
            }
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
                    if (getGraph() instanceof PetriNet) {
                        controller.addPlace("P", x - 38, y - 19);
                    }
                    if (getGraph() instanceof PrecedenceGraph) {
                        controller.addNode("Node", x - 25, y - 12);
                    }
                }
                // Resource
                if (resuorceButton.isSelected()) {
                    if (getGraph() instanceof PetriNet) {
                        controller.addResource("R", x - 50, y - 19);
                    }
                }
                // Transition
                if (rectangleButton.isSelected()) {
                    controller.addTransition("T", x - 43, y - 19);
                }

                setSelectedMagneticLine(controller.getLocationMagneticLine(x, y));
                if(getSelectedMagneticLine() == null){
                    //vertical magnetic line
                    if (verticalMagnetButton.isSelected()) {
                        VerticalMagneticLine verticalMagnet = new VerticalMagneticLine((int)(x), this.getHeight());
                        graph.getMagneticLines().add(verticalMagnet);
                        controller.setElementsToMagneticLine(verticalMagnet);
                    }
                    // horizontal magnetic line
                    if (horizontalMagnetButton.isSelected()) {
                        HorizontalMagneticLine horizontalMagnet = new HorizontalMagneticLine((int)(y),this.getWidth());
                        graph.getMagneticLines().add(horizontalMagnet);
                        controller.setElementsToMagneticLine(horizontalMagnet);
                    }
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
                    if (bendButton.isSelected()) {
                        Arc temp = (Arc) currentElement;
                        temp.addBendPoint(new Point(x, y));
                    }
                    propertiesMenu.setVisible(true);
                    //PetriNet pn = (PetriNet) graph;
                }
            }

            if (selectedElements.size() > 1) {
                loadMultipleProperties(selectedElements);
                //propertiesMenu.setVisible(true);
            }

            if (selectedElements.isEmpty()) {
                propertiesMenu.setVisible(false);
            }
            updateComponentList();
            repaint();
        }

        public void mouseRightClick(int x, int y) {
            x = (int) (x / getScaleRatio()[0]);
            y = (int) (y / getScaleRatio()[1]);
            Element e = null;
            setSelectedMagneticLine(null);
            Object o = controller.getLocationElement(x, y);
            if (!this.isIsCTRLdown()) {
                selectedElements.clear();
            }
            if (o instanceof Element) {
                e = (Element) o;
            }
            Arc a = controller.getLocationArc(x, y);
            if (e != null) {
                selectedElements.add(e);
                loadElementProperties(e);
                propertiesMenu.setVisible(true);
            } else if (a != null) {
                selectedElements.add(a);
                loadElementProperties(a);
                propertiesMenu.setVisible(true);
            } else {
                propertiesMenu.setVisible(false);
            }
            setSelectedMagneticLine(controller.getLocationMagneticLine(x, y));
            // Create RED shadow line indicating deletion of arc        
            //controller.deleteElement(x, y);
            //controller.deleteArc(x, y);
            //selectedElements.clear();
            updateComponentList();
            repaint();
        }

        public void mouseLeftDragged(int x, int y) {
            int origX = x;
            int origY = y;
            x = (int) (x / getScaleRatio()[0]);
            y = (int) (y / getScaleRatio()[1]);

            // None element or arc
            if (draggedObject == null && draggedElement == null && getSelectedMagneticLine() == null) {
                if(!ellipseButton.isSelected()&&!rectangleButton.isSelected()&&!resuorceButton.isSelected()&&!lineButton.isSelected()
                   &&!bendButton.isSelected()&&!deleteBendButton.isSelected()){
                    int scrollPositionX = diagramScrollPane.getViewport().getViewPosition().x;
                    int scrollPositionY = diagramScrollPane.getViewport().getViewPosition().y;
                    int offsetX = (this.mouseAdapter.getX() - origX + scrollPositionX);
                    int offsetY = (this.mouseAdapter.getY() - origY + scrollPositionY);
                    if(offsetY<=0)offsetY=1;
                    if(offsetX<=0)offsetX=1;
                        diagramScrollPane.getViewport().setViewPosition(new java.awt.Point(offsetX, offsetY));
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
            // Line
            if (draggedObject instanceof Line2D) {

                int x1 = (int) ((Line2D) draggedObject).getX1();
                int y1 = (int) ((Line2D) draggedObject).getY1();
                draggedObject = new Line2D.Float(x1, y1, x, y);
            }
            // Point on a line
            if (draggedObject instanceof Point) {
                Point p = (Point) draggedObject;
                p.setLocation(x, y);
                clickedX = x;
                clickedY = y;
                draggedObject = p;
            }
            
            if (getSelectedMagneticLine() != null) {
                if(getSelectedMagneticLine() instanceof HorizontalMagneticLine){
                    selectedMagneticLine.y1 = y;
                    selectedMagneticLine.y2 = y;
                    for (ComplexElement complexElement : getSelectedMagneticLine().getConnectedElemenets()) {
                        int halfHeight =(int)(complexElement.getHeight()/2);
                        int length = getSelectedMagneticLine().y1 - halfHeight;
                        complexElement.setY(length);
                    }
                    clickedX = x;
                    clickedY = y;
                }
                else if(getSelectedMagneticLine() instanceof VerticalMagneticLine){
                    selectedMagneticLine.x1 = x;
                    selectedMagneticLine.x2 = x;
                    for (ComplexElement complexElement : getSelectedMagneticLine().getConnectedElemenets()) {
                        int halfWidth =(int)(complexElement.getWidth()/2);
                        int length = getSelectedMagneticLine().x1 - halfWidth;
                        complexElement.setX(length);
                    }
                    clickedX = x;
                    clickedY = y;
                }
            }
            repaint();
        }

        public void mouseLeftReleased(int x_old, int y_old, int x_new, int y_new) {
            x_old = (int) (x_old / this.getScaleRatio()[0]);
            x_new = (int) (x_new / this.getScaleRatio()[0]);
            y_old = (int) (y_old / this.getScaleRatio()[1]);
            y_new = (int) (y_new / this.getScaleRatio()[1]);

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

            /*
            if (this.selectedMagneticLine != null) {
                controller.setElementsToMagneticLine(selectedMagneticLine);
            }
            */ 
            for (MagneticLine magneticLine : graph.getMagneticLines()) {
                controller.setElementsToMagneticLine(magneticLine);
            }
            
            draggedObject = null;
            draggedElement = null;
            repaint();
        }

        private void loadElementProperties(Element currentElement) {
            generalProperties.loadProperties(currentElement, getGraph(), this);
            notes.setText(currentElement.getNote());

        }

        private void loadMultipleProperties(ArrayList<Element> currentElements) {
            generalProperties.loadMultipleProperties(currentElements, graph, this);
            //notes.setText(currentElement.getNote());
        }

        private void autosetWidthHeight() {
            int marginWidth = 20;
            int marginHeight = 20;
            Font oldfont = g2d.getFont();
            g2d.scale(1, 1);
            // Petri net
            if (getGraph() instanceof PetriNet) {
                for (Place p : ((PetriNet) getGraph()).getListOfPlaces()) {
                    //if (p.getWidth() == -1 && p.getHeight() == -1) {
                    Font newFont = new Font("Times New Roman", Font.PLAIN, p.getFontSize());
                    g2d.setFont(newFont);

                    FontMetrics fm = g2d.getFontMetrics(newFont);
                    java.awt.geom.Rectangle2D rect = fm.getStringBounds(p.getName() + " :" + p.getMarking() + "{;}", g2d);

                    double textWidth = (rect.getWidth());
                    double textHeight = (rect.getHeight());
                    if ((textWidth + marginWidth) > p.getWidth()) {
                        p.setWidth((int) (textWidth + marginWidth));
                    }
                    if ((textHeight + marginHeight) > p.getHeight()) {
                        p.setHeight((int) textHeight + marginHeight);
                    }
                    //}
                }
                for (Transition t : ((PetriNet) getGraph()).getListOfTransitions()) {
                    //if (t.getWidth() == -1 && t.getHeight() == -1) {
                    Font newFont = new Font("Times New Roman", Font.PLAIN, t.getFontSize());
                    g2d.setFont(newFont);

                    FontMetrics fm = g2d.getFontMetrics(newFont);
                    java.awt.geom.Rectangle2D rect = fm.getStringBounds(t.getName(), g2d);

                    double textWidth = (rect.getWidth());
                    double textHeight = (rect.getHeight());

                    if ((textWidth + marginWidth) > t.getWidth()) {
                        t.setWidth((int) (textWidth + marginWidth));
                    }
                    if ((textHeight + marginHeight) > t.getHeight()) {
                        t.setHeight((int) textHeight + marginHeight);
                    }
                    // }
                }

                for (Resource r : ((PetriNet) getGraph()).getListOfResources()) {
                    //if (r.getWidth() == -1 && r.getHeight() == -1) {
                    Font newFont = new Font("Times New Roman", Font.PLAIN, r.getFontSize());
                    g2d.setFont(newFont);

                    FontMetrics fm = g2d.getFontMetrics(newFont);
                    java.awt.geom.Rectangle2D rect = fm.getStringBounds(r.getName() + " :" + r.getMarking(), g2d);

                    int textWidth = (int) (rect.getWidth());
                    int textHeight = (int) (rect.getHeight());

                    if ((textWidth + marginWidth) > r.getWidth()) {
                        r.setWidth((int) (textWidth + marginWidth));
                    }
                    if ((textHeight + marginHeight) > r.getHeight()) {
                        r.setHeight((int) textHeight + marginHeight);
                    }
                    //}
                }
            }

            // Precedence graph
            if (getGraph() instanceof PrecedenceGraph) {
                for (Node n : ((PrecedenceGraph) getGraph()).getListOfNodes()) {
                    if (n.getWidth() == -1 && n.getHeight() == -1) {
                        Font newFont = new Font("Times New Roman", Font.PLAIN, n.getFontSize());
                        g2d.setFont(newFont);

                        FontMetrics fm = g2d.getFontMetrics(newFont);
                        java.awt.geom.Rectangle2D rect = fm.getStringBounds(n.getName() + " :" + n.getCapacity(), g2d);

                        int textWidth = (int) (rect.getWidth());
                        int textHeight = (int) (rect.getHeight());

                        if ((textWidth + marginWidth) > n.getWidth()) {
                            n.setWidth((int) (textWidth + marginWidth));
                        }
                        if ((textHeight + marginHeight) > n.getHeight()) {
                            n.setHeight((int) textHeight + marginHeight);
                        }
                    }
                }
            }
            // Revert settings
            g2d.scale(getScaleRatio()[0], getScaleRatio()[1]);
            g2d.setFont(oldfont);
        } // Function end

        public void copySelectedElements() {
            copiedElements.clear();
            copiedElements.addAll(selectedElements);
            repaint();
        }

        public void pasteSelectedElements(int x, int y) {
            selectedElements.clear();
            if(graph instanceof PetriNet){
                for (Element element : copiedElements) {
                    int index = 1;
                    if(element instanceof Place){
                        Place place;
                        do{
                            place = new Place("P"+(((PetriNet)getGraph()).getListOfPlaces().size()+index));
                            place.setX(element.getX()+offset);
                            place.setY(element.getY()+offset);
                            place.setColor(element.getColor());
                            place.setColor2(element.getColor2());
                            index++;
                        }while (!((PetriNet) graph).addPlace(place));
                        places.put(element, place);
                        selectedElements.add(place);  
                    }
                    if(element instanceof Resource){
                        Resource resource;
                        do{
                            resource = new Resource("R"+(((PetriNet)getGraph()).getListOfResources().size()+index));
                            resource.setX(element.getX()+offset);
                            resource.setY(element.getY()+offset);
                            resource.setColor(element.getColor());
                            resource.setColor2(element.getColor2());
                            index++;
                        }while (!((PetriNet) getGraph()).addResource(resource));
                        places.put(element, resource);
                        selectedElements.add(resource);
                    }
                    if(element instanceof Transition){
                        Transition transition;
                        do{
                            transition = new Transition("T"+(((PetriNet)getGraph()).getListOfTransitions().size()+index));
                            transition.setX(element.getX()+offset);
                            transition.setY(element.getY()+offset);
                            transition.setColor(element.getColor());
                            transition.setColor2(element.getColor2());
                            index++;
                        }while (!((PetriNet) getGraph()).addTransition(transition));
                        transitions.put(element, transition);
                        selectedElements.add(transition);
                    }
                }
                // nastavim dvojice medzi, kotrymi maju byt vztahy
                for (Element placeElement : places.keySet()) {
                    AbsPlace place = (AbsPlace) placeElement;
                    for (Element transitionElement : transitions.keySet()) {
                        if(place.getListOfInTransitions().size()>0){
                            for (Transition inTrans : place.getListOfInTransitions()) {
                                if(inTrans == transitionElement){            
                                    for (Arc arc : inTrans.getListOfOutArcs()) {
                                        for (Arc arc2 : place.getListOfInArcs()) {
                                            if(arc == arc2){
                                                pair = new PairValue<Element, Element,Arc>(transitions.get(transitionElement), places.get(placeElement),arc);    
                                            }
                                        }
                                    }
                                    inToOutElement.add(pair);
                                }
                            }
                        }
                        if(place.getListOfOutTransitions().size()>0){
                            for (Transition outTrans : place.getListOfOutTransitions()) {
                                if(outTrans == transitionElement){
                                    for (Arc arc : outTrans.getListOfInArcs()) {
                                        for (Arc arc2 : place.getListOfOutArcs()) {
                                            if(arc == arc2){
                                                pair = new PairValue<Element, Element, Arc>(places.get(placeElement),transitions.get(transitionElement),arc);    
                                            }
                                        }
                                    }
                                    inToOutElement.add(pair);
                                }
                            }
                        }
                    }
                }
            }else{
                for (Element element : copiedElements) {
                    int index = 1;
                    Node node;
                    if(element instanceof Node){
                        do{
                            node = new Node("node"+(((PrecedenceGraph)getGraph()).getListOfNodes().size()+index));
                            node.setX(element.getX()+offset);
                            node.setY(element.getY()+offset);
                            node.setColor(element.getColor());
                            node.setColor2(element.getColor2());
                            index++;
                        }while (!((PrecedenceGraph) getGraph()).addNode(node));
                        nodes.put(element, node);
                        selectedElements.add(node);
                    }
                }
                for (Element element : nodes.keySet()) {
                    for (Element outArc : ((Node)element).getListOfOutArcs()) {
                        Element inElement = nodes.get(((Arc)outArc).getInElement());
                        pair = new PairValue<Element, Element, Arc>(nodes.get(element), inElement, (Arc)outArc);
                        inToOutElement.add(pair);
                    }
                }
            }
            
            // Vytvorim nove vztahy
            for (PairValue<Element, Element, Arc> pairsElement : inToOutElement) {
                int index = 1;
                Arc arc = null;
                if(graph instanceof PetriNet){
                    do{
                        arc = new Arc("Arc"+(((PetriNet)getGraph()).getListOfArcs().size()+index), pairsElement.getLeft(), pairsElement.getRight());
                        arc.setColor(pairsElement.getData().getColor());
                        arc.setColor2(pairsElement.getData().getColor2());
                        arc.setCapacity(pairsElement.getData().getCapacity());
                        arc.setFontSize(pairsElement.getData().getFontSize());
                        index++;
                    }while (!((PetriNet) getGraph()).addArc(arc));
                }else{
                    do{
                        arc = new Arc("Arc"+(((PrecedenceGraph)getGraph()).getListOfArcs().size()+index), pairsElement.getLeft(), pairsElement.getRight());
                        arc.setColor(pairsElement.getData().getColor());
                        arc.setColor2(pairsElement.getData().getColor2());
                        arc.setCapacity(pairsElement.getData().getCapacity());
                        arc.setFontSize(pairsElement.getData().getFontSize());
                        index++;
                    }while (!((PrecedenceGraph) getGraph()).addArc(arc));
                }
                selectedElements.add(arc);
            }

            places.clear();
            transitions.clear();
            inToOutElement.clear();
            copiedElements.clear();
            copiedElements.addAll(selectedElements);
            updateComponentList();
            repaint();
        }

        public void deleteSelectedElements() {
            for (Element element : selectedElements) {
                // Place
                if (element instanceof Place) {
                    ((PetriNet) getGraph()).deletePlace(((Place) element).getName());
                }
                // Transition
                if (element instanceof Transition) {
                    ((PetriNet) getGraph()).deleteTransition(((Transition) element).getName());
                }
                // Node
                if (element instanceof Node) {
                    ((PrecedenceGraph) getGraph()).deleteNode(((Node) element).getName());
                }
                // Place
                if (element instanceof Resource) {
                    ((PetriNet) getGraph()).deleteResource(((Resource) element).getName());
                }
                if (element instanceof Arc) {
                    ((PetriNet) getGraph()).deleteArc(((Arc) element).getName());
                }
            }
            selectedElements.clear();
            updateComponentList();
            resetForm();
            repaint();
        }

        public void setDiagramCursor() {
            setCustomCursor();
        }

        public PopUpMenu getPopUpMenu() {
            return getPopMenu();
        }

        public void updateComponentListFromDiagram() {
            updateComponentList();
        }

        public void setEllipseButtonSelected(Boolean selected) {
            disableButtons();
            ellipseButton.setSelected(selected);
            this.setDiagramCursor();
        }

        public void setTransitionButtonSelected(Boolean selected) {
            disableButtons();
            rectangleButton.setSelected(selected);
            this.setDiagramCursor();
        }

        public void setResourceButtonSelected(Boolean selected) {
            disableButtons();
            resuorceButton.setSelected(selected);
            this.setDiagramCursor();
        }

        public void setArcButtonSelected(Boolean selected) {
            disableButtons();
            lineButton.setSelected(selected);
            this.setDiagramCursor();
        }

        /**
         * @return the graph
         */
        public Graph getGraph() {
            return graph;
        }

        /**
         * @param graph the graph to set
         */
        public void setGraph(Graph graph) {
            this.graph = graph;
        }

        /**
         * @return the scaleRatio
         */
        public double[] getScaleRatio() {
            return scaleRatio;
        }

        /**
         * @param scaleRatio the scaleRatio to set
         */
        public void setScaleRatio(double[] scaleRatio) {
            this.scaleRatio = scaleRatio;
        }

        public void setMoveHandCursor() {
            this.setCursor(moveHandCursor);
        }

        public void setHandCursor() {
            this.setCursor(handCursor);
        }

        public void resetAllButtons() {
            resetForm();
        }

        /**
         * @return the isCTRLdown
         */
        public boolean isIsCTRLdown() {
            return isCTRLdown;
        }

        /**
         * @param isCTRLdown the isCTRLdown to set
         */
        public void setIsCTRLdown(boolean isCTRLdown) {
            this.isCTRLdown = isCTRLdown;
        }

        /**
         * @return the bubbleElement
         */
        public Element getBubbleElement() {
            return bubbleElement;
        }

        /**
         * @param bubbleElement the bubbleElement to set
         */
        public void setBubbleElement(Element bubbleElement) {
            this.bubbleElement = bubbleElement;
        }

        /**
         * @return the timer
         */
        public Timer getTimer() {
            return timer;
        }

        /**
         * @param timer the timer to set
         */
        public void setTimer(Timer timer) {
            this.timer = timer;
        }

        /**
         * @return the selectedMagneticLine
         */
        public MagneticLine getSelectedMagneticLine() {
            return selectedMagneticLine;
        }

        /**
         * @param selectedMagneticLine the selectedMagneticLine to set
         */
        public void setSelectedMagneticLine(MagneticLine selectedMagneticLine) {
            this.selectedMagneticLine = selectedMagneticLine;
        }
    }

    private void resetForm() {
        this.disableButtons();
        curButton.setSelected(true);
        //cursorButton.setSelected(true);
        propertiesMenu.setVisible(false);
        this.getDiagramPanel().setCursor(this.handCursor);
        this.revalidate();
        this.repaint();
    }

    /**
     *
     * @param evt button or toggleButton
     *
     */
    public void toggleButtons(java.awt.event.ActionEvent evt){
        selectedButtonTemp = (JToggleButton)evt.getSource();
        this.disableButtons();
        if(evt.getSource() instanceof JToggleButton){
            if(selectedButton != selectedButtonTemp){
                ((JToggleButton)evt.getSource()).setSelected(true);
                selectedButton = ((JToggleButton)evt.getSource());
            }else{
                selectedButton = null;
            }
        }
        /*
        if(evt.getSource() instanceof JButton){
            if (((JButton)evt.getSource()) != selectedButton){
                ((JButton)evt.getSource()).setSelected(true);
            }
            selectedButton = ((JButton)evt.getSource());          
        }
        */ 
        this.setCustomCursor();
    }

    /**
     * Set custom cursors to diagramPanel
     */
    public void setCustomCursor() {
        if (this.ellipseButton.isSelected()) {
            this.getDiagramPanel().setCursor(this.ellipseCursor);
        } 
        else if ((this.resuorceButton.isSelected())) {
            this.getDiagramPanel().setCursor(this.resCursor);
        }
        else if ((this.rectangleButton.isSelected())) {
            this.getDiagramPanel().setCursor(this.transitionCursor);
        }
        else if ((this.lineButton.isSelected())) {
            this.getDiagramPanel().setCursor(this.arcCursor);
        }
        else if ((this.bendButton.isSelected())) {
            this.getDiagramPanel().setCursor(this.handCursor);
        }
        else if ((this.deleteBendButton.isSelected())) {
            this.getDiagramPanel().setCursor(this.handCursor);
        }
        else {
            this.getDiagramPanel().setCursor(this.handCursor);
        }
    }

    /**
     * Disable all buttons in buttonsPanel and toolBar
     */
    public void disableButtons() {
        for (Component button : this.buttonsPanel.getComponents()) {
            if (button instanceof JToggleButton) {
                ((JToggleButton) button).setSelected(false);
            }
        }
        for (Component button : this.toolBar.getComponents()) {
            if(button instanceof JToggleButton){
                ((JToggleButton)button).setSelected(false);
            }
        }
        /*
        for (Component button : this.toolBar.getComponents()) {
            if(button instanceof JButton){
                if (selectedButton != null && !selectedButton.isSelected()){
                    selectedButton = null;
                }
                ((JButton)button).setFocusCycleRoot(false);
                ((JButton)button).setFocusPainted(false);
                ((JButton)button).setSelected(false);
            }
        }
        */ 
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }
    
    
}
