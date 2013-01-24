/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diagram;

import danescreator.PetriNet;
import java.awt.Dimension;
import javax.swing.JScrollPane;

/**
 *
 * @author marek
 */
public class DiagramSrollPane extends JScrollPane{

    //private DiagramPanel graphPanel;
    
    public DiagramSrollPane(PetriNet pa_petriho_siet) {
        super();
        setPreferredSize(new Dimension(400, 400));
        DiagramPanel    graphPanel=new DiagramPanel(pa_petriho_siet);        
        setViewportView(graphPanel);
 /*       Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(new GraphSrollPane(), BorderLayout.CENTER);        */
    }
    
    
    
}
