/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Core.AbsPlace;
import Core.Arc;
import Core.Element;
import Core.Graph;
import Core.Logic;
import Core.Marking;
import Core.Node;
import Core.PetriNet;
import Core.Place;
import Core.Resource;
import Core.Transition;
import GUI.View.DiagramPanel;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JColorChooser;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Miso
 */
public class PropertiesMenu extends javax.swing.JPanel {

    private String elementName;
    private String elementWidth;
    private String elementHeight;
    private String elementFontSize;
    private String elementMarking;
    private boolean elementStart;
    private boolean elementEnd;
    private Color elementColor;
    private Graph graph;
    private DiagramPanel window;
    private Element element;
    private Logic log;
    private ArrayList<Element> listOfElements;

    /**
     * Creates new form PropertiesParentMenu
     */
    public PropertiesMenu() {
        initComponents();
        setListeners();
        log = new Logic();
    }

    private void setListeners() {
        nameText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        nameText.selectAll();
                    }
                });
            }
        });

        heightText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        heightText.selectAll();
                    }
                });
            }
        });

        widthText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        widthText.selectAll();
                    }
                });
            }
        });

        fontSizeText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        fontSizeText.selectAll();
                    }
                });
            }
        });

        ChangeListener listener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (element instanceof Arc) {
                    ((Arc) element).setCapacity((Integer) capacitySpiner.getValue());
                }
                if (element instanceof Node) {
                    ((Node) element).setCapacity((Integer) capacitySpiner.getValue());
                }
                if (element instanceof Resource) {
                    ((Resource) element).setMarking((Integer) capacitySpiner.getValue());
                }
                if (element instanceof Place) {
                    int countOfMarks = (Integer) capacitySpiner.getValue();
                    Marking tempMark = new Marking(element);
                    ArrayList<Integer> tempMarking = new ArrayList<Integer>();
                    for (int i = 0; i < countOfMarks; i++) {
                        tempMarking.add(i + 1);
                    }
                    tempMark.setMarkings(tempMarking);
                    ((Place) element).setMarkings(tempMark);
                }
                window.updateComponentListFromDiagram();
                window.repaint();
            }
        };

        capacitySpiner.addChangeListener(listener);

//
//        capacityText.addFocusListener(new java.awt.event.FocusAdapter() {
//            public void focusGained(java.awt.event.FocusEvent evt) {
//                SwingUtilities.invokeLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        capacityText.selectAll();
//                    }
//                });
//            }
//        });


    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label4 = new java.awt.Label();
        jButton4 = new javax.swing.JButton();
        jTextField4 = new javax.swing.JTextField();
        label6 = new java.awt.Label();
        jButton6 = new javax.swing.JButton();
        jTextField6 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        nameText = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        heightText = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        widthText = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        fontSizeText = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        colorLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        fontColorLabel = new javax.swing.JLabel();
        specific = new javax.swing.JPanel();
        endCheckBox = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        startCheckBox = new javax.swing.JCheckBox();
        capacitySpiner = new javax.swing.JSpinner();

        label4.setName(""); // NOI18N
        label4.setText("Width");

        jButton4.setText("Ok");

        label6.setName(""); // NOI18N
        label6.setText("Font size");

        jButton6.setText("Ok");

        setRequestFocusEnabled(false);

        jLabel1.setText("Name");
        jLabel1.setToolTipText("Name of element");

        nameText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                nameTextFocusLost(evt);
            }
        });
        nameText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nameTextKeyReleased(evt);
            }
        });

        jLabel2.setText("Height");
        jLabel2.setToolTipText("GUI height of element");

        heightText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                heightTextFocusLost(evt);
            }
        });
        heightText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                heightTextKeyReleased(evt);
            }
        });

        jLabel3.setText("Width");
        jLabel3.setToolTipText("GUI width of element");

        widthText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                widthTextFocusLost(evt);
            }
        });
        widthText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                widthTextKeyReleased(evt);
            }
        });

        jLabel4.setText("Font Size");
        jLabel4.setToolTipText("Font Size");

        fontSizeText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fontSizeTextActionPerformed(evt);
            }
        });
        fontSizeText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fontSizeTextFocusLost(evt);
            }
        });
        fontSizeText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fontSizeTextKeyReleased(evt);
            }
        });

        jLabel5.setText("Font color");
        jLabel5.setToolTipText("Color of font");

        colorLabel.setBackground(new java.awt.Color(0, 0, 102));
        colorLabel.setForeground(new java.awt.Color(255, 255, 255));
        colorLabel.setOpaque(true);
        colorLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                colorLabelMouseClicked(evt);
            }
        });

        jLabel6.setText("Color");
        jLabel6.setToolTipText("Color of element");

        fontColorLabel.setBackground(new java.awt.Color(0, 0, 102));
        fontColorLabel.setForeground(new java.awt.Color(255, 255, 255));
        fontColorLabel.setOpaque(true);
        fontColorLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fontColorLabelMouseClicked(evt);
            }
        });

        endCheckBox.setText("End");
        endCheckBox.setToolTipText("Is place end place?");
        endCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endCheckBoxActionPerformed(evt);
            }
        });

        jLabel7.setText("Capacity");

        startCheckBox.setText("Start");
        startCheckBox.setToolTipText("Is place start place?");
        startCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startCheckBoxActionPerformed(evt);
            }
        });

        capacitySpiner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(0), null, Integer.valueOf(1)));

        javax.swing.GroupLayout specificLayout = new javax.swing.GroupLayout(specific);
        specific.setLayout(specificLayout);
        specificLayout.setHorizontalGroup(
            specificLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(specificLayout.createSequentialGroup()
                .addGroup(specificLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(specificLayout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(capacitySpiner, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 26, Short.MAX_VALUE))
                    .addComponent(startCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(endCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        specificLayout.setVerticalGroup(
            specificLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(specificLayout.createSequentialGroup()
                .addGroup(specificLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(capacitySpiner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(endCheckBox)
                .addGap(0, 26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameText)
                    .addComponent(heightText)
                    .addComponent(fontSizeText)
                    .addComponent(widthText))
                .addGap(10, 10, 10))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fontColorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(colorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(specific, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nameText)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(heightText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(widthText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fontSizeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(colorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fontColorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(specific, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(86, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void colorLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colorLabelMouseClicked
        JColorChooser colorChooser = new JColorChooser();
        elementColor = JColorChooser.showDialog(this, "Vyberte farbu", this.element.getColor());

        if (listOfElements != null && listOfElements.size() > 1) {
            for (Element e : listOfElements) {
                e.setColor(elementColor);
            }
            this.colorLabel.setOpaque(true);
            this.colorLabel.setBackground(elementColor);
            window.repaint();
        } else {
            element.setColor(elementColor);
            this.colorLabel.setOpaque(true);
            this.colorLabel.setBackground(elementColor);
            window.repaint();
        }
    }//GEN-LAST:event_colorLabelMouseClicked

    private void fontColorLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fontColorLabelMouseClicked
        JColorChooser colorChooser = new JColorChooser();
        elementColor = JColorChooser.showDialog(this, "Vyberte farbu", this.element.getColor2());

        if (listOfElements != null && listOfElements.size() > 1) {
            for (Element e : listOfElements) {
                e.setColor2(elementColor);
            }
            this.fontColorLabel.setOpaque(true);
            this.fontColorLabel.setBackground(elementColor);
            window.repaint();
        } else {
            element.setColor2(elementColor);
            this.fontColorLabel.setOpaque(true);
            this.fontColorLabel.setBackground(elementColor);
            window.repaint();
        }

    }//GEN-LAST:event_fontColorLabelMouseClicked

    private void nameTextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameTextKeyReleased
        if (!nameText.getText().equals("")) {
            element.setName(nameText.getText());
        }
        window.updateComponentListFromDiagram();
        window.repaint();
    }//GEN-LAST:event_nameTextKeyReleased

    private void heightTextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_heightTextKeyReleased

        if (!heightText.getText().equals("")) {
            if (!log.checkNumbString(heightText.getText())) {
                return;
            }
            if (listOfElements != null && listOfElements.size() > 1) {
                for (Element e : listOfElements) {
                    if (e instanceof AbsPlace) {
                        AbsPlace abs = (AbsPlace) e;
                        abs.setHeight(Integer.parseInt(heightText.getText()));
                    }
                    if (e instanceof Transition) {
                        Transition abs = (Transition) e;
                        abs.setHeight(Integer.parseInt(heightText.getText()));
                    }
                    if (e instanceof Node) {
                        Node node = (Node) e;
                        node.setHeight(Integer.parseInt(heightText.getText()));
                    }
                }
            } else {
                if (element instanceof AbsPlace) {
                    AbsPlace abs = (AbsPlace) element;
                    abs.setHeight(Integer.parseInt(heightText.getText()));
                }
                if (element instanceof Transition) {
                    Transition abs = (Transition) element;
                    abs.setHeight(Integer.parseInt(heightText.getText()));
                }
                if (element instanceof Node) {
                    Node node = (Node) element;
                    node.setHeight(Integer.parseInt(heightText.getText()));
                }
            }
            window.repaint();
        }
    }//GEN-LAST:event_heightTextKeyReleased

    private void widthTextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_widthTextKeyReleased
        if (!widthText.getText().equals("")) {
            if (!log.checkNumbString(widthText.getText())) {
                return;
            }
            if (listOfElements != null && listOfElements.size() > 1) {
                for (Element e : listOfElements) {
                    if (e instanceof AbsPlace) {
                        AbsPlace abs = (AbsPlace) e;
                        abs.setWidth(Integer.parseInt(widthText.getText()));
                    }
                    if (e instanceof Transition) {
                        Transition abs = (Transition) e;
                        abs.setWidth(Integer.parseInt(widthText.getText()));
                    }
                    if (e instanceof Node) {
                        Node node = (Node) e;
                        node.setWidth(Integer.parseInt(widthText.getText()));
                    }
                }
            } else {
                if (element instanceof AbsPlace) {
                    AbsPlace abs = (AbsPlace) element;
                    abs.setWidth(Integer.parseInt(widthText.getText()));
                }
                if (element instanceof Transition) {
                    Transition abs = (Transition) element;
                    abs.setWidth(Integer.parseInt(widthText.getText()));
                }
                if (element instanceof Node) {
                    Node node = (Node) element;
                    node.setWidth(Integer.parseInt(widthText.getText()));
                }
            }
            window.repaint();
        }
    }//GEN-LAST:event_widthTextKeyReleased

    private void fontSizeTextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fontSizeTextKeyReleased
        if (!fontSizeText.getText().equals("")) {
            if (!log.checkNumbString(fontSizeText.getText())) {
                return;
            }
            if (listOfElements != null && listOfElements.size() > 1) {
                for (Element e : listOfElements) {
                    e.setFontSize(Integer.parseInt(fontSizeText.getText()));
                }

            } else {
                element.setFontSize(Integer.parseInt(fontSizeText.getText()));
            }
            window.repaint();
        }
    }//GEN-LAST:event_fontSizeTextKeyReleased

    private void startCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startCheckBoxActionPerformed
        if (element instanceof AbsPlace) {
            AbsPlace abs = (AbsPlace) element;
            if (startCheckBox.isSelected()) {
                abs.setStart(true);
            } else {
                abs.setStart(false);
            }
        }
    }//GEN-LAST:event_startCheckBoxActionPerformed

    private void endCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endCheckBoxActionPerformed
        if (element instanceof AbsPlace) {
            AbsPlace abs = (AbsPlace) element;
            if (endCheckBox.isSelected()) {
                abs.setEnd(true);
            } else {
                abs.setEnd(false);
            }

        }
    }//GEN-LAST:event_endCheckBoxActionPerformed

    private void nameTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameTextFocusLost
    }//GEN-LAST:event_nameTextFocusLost

    private void heightTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_heightTextFocusLost
    }//GEN-LAST:event_heightTextFocusLost

    private void widthTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_widthTextFocusLost
    }//GEN-LAST:event_widthTextFocusLost

    private void fontSizeTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fontSizeTextFocusLost
    }//GEN-LAST:event_fontSizeTextFocusLost

    private void fontSizeTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fontSizeTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fontSizeTextActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner capacitySpiner;
    private javax.swing.JLabel colorLabel;
    private javax.swing.JCheckBox endCheckBox;
    private javax.swing.JLabel fontColorLabel;
    private javax.swing.JTextField fontSizeText;
    private javax.swing.JTextField heightText;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField6;
    private java.awt.Label label4;
    private java.awt.Label label6;
    private javax.swing.JTextField nameText;
    private javax.swing.JPanel specific;
    private javax.swing.JCheckBox startCheckBox;
    private javax.swing.JTextField widthText;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the elementWidth
     */
    public String getElementWidth() {
        return elementWidth;
    }

    /**
     * @param elementWidth the elementWidth to set
     */
    public void setElementWidth(String elementWidth) {

        this.widthText.setText(elementWidth);
        //this.elementWidth = elementWidth;
    }

    /**
     * @return the elementHeight
     */
    public String getElementHeight() {
        return elementHeight;
    }

    /**
     * @param elementHeight the elementHeight to set
     */
    public void setElementHeight(String elementHeight) {
        //this.elementHeight = elementHeight;
        this.heightText.setText(elementHeight);
    }

    /**
     * @return the elementFontSize
     */
    public String getElementFontSize() {
        return elementFontSize;
    }

    /**
     * @param elementFontSize the elementFontSize to set
     */
    public void setElementFontSize(String elementFontSize) {
        //this.elementFontSize = elementFontSize;
        this.fontSizeText.setText(elementFontSize);
    }

    /**
     * @return the elementColor
     */
    public Color getElementColor() {
        return elementColor;
    }

    /**
     * @param elementColor the elementColor to set
     */
    public void setElementColor(Color elementColor) {
        this.elementColor = elementColor;
        //this.colorText.setText(elementColor);
    }

    /**
     * @return the elementName
     */
    public String getElementName() {
        return elementName;
    }

    /**
     * @param elementName the elementName to set
     */
    public void setElementName(String elementName) {
        //this.elementName = elementName;
        this.nameText.setText(elementName);
    }

    /**
     * @return the elementMarking
     */
    public String getElementMarking() {
        return elementMarking;
    }

    /**
     * @param elementMarking the elementMarking to set
     */
    public void setElementMarking(int elementMarking) {
        capacitySpiner.setValue(new Integer(elementMarking));
    }

    /**
     * @return the elementStart
     */
    public boolean isElementStart() {
        return elementStart;
    }

    /**
     * @param elementStart the elementStart to set
     */
    public void setElementStart(boolean elementStart) {
        if (elementStart) {
            this.startCheckBox.setSelected(true);
        } else {
            this.startCheckBox.setSelected(false);
        }
    }

    /**
     * @return the elementEnd
     */
    public boolean isElementEnd() {
        return elementEnd;
    }

    /**
     * @param elementEnd the elementEnd to set
     */
    public void setElementEnd(boolean elementEnd) {
        if (elementEnd) {
            this.endCheckBox.setSelected(true);
        } else {
            this.endCheckBox.setSelected(false);
        }
    }

    public void loadMultipleProperties(ArrayList<Element> selectedElements, Graph graph, DiagramPanel window) {
        listOfElements = selectedElements;
        boolean arcExists = false;
        boolean sameTextSize = true;
        int textSize = selectedElements.get(0).getFontSize();
        boolean sameWidth = true;
        int w = -1;
        boolean sameHeight = true;
        int h = -1;
        boolean sameColor1 = true;
        Color c1 = selectedElements.get(0).getColor();
        boolean sameColor2 = true;
        Color fontColor = selectedElements.get(0).getColor2();

        for (Element e : selectedElements) {
            int actFontSize = e.getFontSize();
            if (actFontSize != textSize) {
                sameTextSize = false;
            }
            if (!e.getColor().equals(c1)) {
                sameColor1 = false;
            }
            if (!e.getColor2().equals(fontColor)) {
                sameColor2 = false;
            }
            if (e instanceof Arc) {
                arcExists = true;
            }
        }

        if (!arcExists) {
            int inc = 0;
            for (Element e : selectedElements) {
                if (graph instanceof PetriNet) {
                    if (e instanceof AbsPlace) {
                        AbsPlace ap = (AbsPlace) e;
                        if (inc == 0) {
                            w = ap.getWidth();
                            h = ap.getHeight();
                        }
                        if (w != ap.getWidth()) {
                            sameWidth = false;
                        }
                        if (h != ap.getHeight()) {
                            sameHeight = false;
                        }

                    } else if (e instanceof Transition) {
                        Transition t = (Transition) e;
                        if (inc == 0) {
                            w = t.getWidth();
                            h = t.getHeight();
                        }
                        if (w != t.getWidth()) {
                            sameWidth = false;
                        }
                        if (h != t.getHeight()) {
                            sameHeight = false;
                        }
                    }
                } else {
                    if (e instanceof Node) {
                        Node n = (Node) e;
                        if (inc == 0) {
                            w = n.getWidth();
                            h = n.getHeight();
                        }
                        if (w != n.getWidth()) {
                            sameWidth = false;
                        }
                        if (h != n.getHeight()) {
                            sameHeight = false;
                        }
                    }
                }
                inc++;
            }
        }
        nameText.setText("");
        nameText.setEnabled(false);
        specific.setVisible(false);

        if (sameWidth) {
            widthText.setText(w + "");
        } else {
            widthText.setText("");
        }
        if (sameHeight) {
            heightText.setText(h + "");
        } else {
            heightText.setText("");
        }
        if (sameTextSize) {
            fontSizeText.setText(textSize + "");
        } else {
            fontSizeText.setText("");
        }
        if (sameColor1) {
            colorLabel.setBackground(c1);
        } else {
            colorLabel.setBackground(Color.BLACK);
        }
        if (sameColor2) {
            fontColorLabel.setBackground(fontColor);
        } else {
            fontColorLabel.setBackground(Color.WHITE);
        }
        if (arcExists) {
            heightText.setText("");
            widthText.setText("");
        }
    }

    public void loadProperties(Element currentElement, Graph graph, DiagramPanel window) {
        nameText.setEnabled(true);
        widthText.setEnabled(true);
        heightText.setEnabled(true);
        this.element = currentElement;
        heightText.setEnabled(true);
        widthText.setEnabled(true);
        //this.graph = graph;
        this.window = window;
        setElementName(currentElement.getName());
        setElementColor(currentElement.getColor());
        colorLabel.setBackground(currentElement.getColor());
        fontColorLabel.setBackground(currentElement.getColor2());
        setElementFontSize(Integer.toString(currentElement.getFontSize()));
        //notes.setText(currentElement.getNote());
        if (currentElement instanceof AbsPlace) {
            jLabel7.setText("Marking");
            int marking;
            if (currentElement instanceof Place) {
                marking = ((Place) currentElement).getMarkings().getMarkings().size();
            } else {
                marking = ((Resource) currentElement).getMarking();
            }
            AbsPlace current = (AbsPlace) currentElement;
            setElementWidth(Integer.toString(current.getWidth()));
            setElementHeight(Integer.toString(current.getHeight()));
            setElementMarking(marking);
            startCheckBox.setVisible(true);
            endCheckBox.setVisible(true);
            setElementEnd(current.isEnd());
            setElementStart(current.isStart());
            specific.setVisible(true);
            jLabel7.setToolTipText("Marking of place/resource");
        }
        if (currentElement instanceof Node) {
            jLabel7.setText("Marking");
            Node current = (Node) currentElement;
            setElementWidth(Integer.toString(current.getWidth()));
            setElementHeight(Integer.toString(current.getHeight()));
            setElementMarking(current.getCapacity());
            startCheckBox.setVisible(false);
            endCheckBox.setVisible(false);
            specific.setVisible(true);
            jLabel7.setToolTipText("Marking of node");
        }
        if (currentElement instanceof Transition) {
            Transition current = (Transition) currentElement;
            setElementWidth(Integer.toString(current.getWidth()));
            setElementHeight(Integer.toString(current.getHeight()));
            specific.setVisible(false);
        }
        if (currentElement instanceof Arc) {
            Arc current = (Arc) currentElement;
            widthText.setEnabled(false);
            widthText.setText("");
            heightText.setEnabled(false);
            heightText.setText("");
            jLabel7.setText("Capacity");
            capacitySpiner.setValue(new Integer(current.getCapacity()));
            //capacityText.setText(current.getCapacity() + "");                
            capacitySpiner.setSize(nameText.getHeight(), nameText.getWidth());
            startCheckBox.setVisible(false);
            endCheckBox.setVisible(false);
            specific.setVisible(true);
            jLabel7.setToolTipText("Capacity of a arc");
        }
        this.revalidate();
        this.validate();
    }
}
