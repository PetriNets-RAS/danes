/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package danescreator.diagram;

import danescreator.diagram.DiagramPanel;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JScrollPane;

/**
 *
 * @author marek
 */
public class DiagramSrollPane extends JScrollPane{

    private DiagramPanel graphPanel;
    
    public DiagramSrollPane() {
        super();
        setPreferredSize(new Dimension(400, 400));
        this.graphPanel=new DiagramPanel();        
        setViewportView(graphPanel);
 /*       Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(new GraphSrollPane(), BorderLayout.CENTER);        */
    }
    
    
    
}
