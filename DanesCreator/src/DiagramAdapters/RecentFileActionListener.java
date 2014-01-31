/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DiagramAdapters;

import GUI.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 *
 * @author MISO
 */
public class RecentFileActionListener implements ActionListener{

    private String fileName;
    private View view;
    private File recentFile;
    public RecentFileActionListener(View view, File recentFile){
        this.view = view;
        this.recentFile = recentFile;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        this.view.drawLoadGraph(this.view.getFileManager().loadGraph(this.recentFile));
        this.view.repaint();
        //System.out.println(fileName);
    }
    
}
