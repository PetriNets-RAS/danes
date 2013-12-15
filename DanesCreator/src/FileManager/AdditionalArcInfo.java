/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FileManager;

import Core.Arc;

/**
 *
 * @author Michal
 */
public class AdditionalArcInfo {
    
    private Arc arc;
    private String text;
    
    public AdditionalArcInfo(Arc paA, String paText){
        this.arc=paA;
        this.text=paText;
    }

    /**
     * @return the arc
     */
    public Arc getArc() {
        return arc;
    }

    /**
     * @param arc the arc to set
     */
    public void setArc(Arc arc) {
        this.arc = arc;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
}
