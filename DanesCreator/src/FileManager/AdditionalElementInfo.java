/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FileManager;

import Core.AbsPlace;
import Core.Element;

/**
 *
 * @author Michal
 */
public class AdditionalElementInfo {
    
    private String type;
    private String initmarking;
    private int ID;
    private Element element;
    private String text;
    
    public AdditionalElementInfo(){
        
    }
    
    public AdditionalElementInfo(Element e, String paText){
        this.element=e;
        this.text=paText;
    }
    
    public AdditionalElementInfo( int id,Element e){
        this.element=e;
        this.ID=id;
    }
    
    public AdditionalElementInfo(int paID, String patype, String paMarking, Element paP){
        this.element=paP;
        this.type=patype;
        this.ID=paID;
        this.initmarking=paMarking;
        
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the initmarking
     */
    public String getInitmarking() {
        return initmarking;
    }

    /**
     * @param initmarking the initmarking to set
     */
    public void setInitmarking(String initmarking) {
        this.initmarking = initmarking;
    }

    /**
     * @return the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * @return the element
     */
    public Element getElement() {
        return element;
    }

    /**
     * @param element the element to set
     */
    public void setElement(Element element) {
        this.element = element;
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
