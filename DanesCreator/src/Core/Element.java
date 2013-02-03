/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import AVL_Tree.StringKey;
import GUI.DiagramElement;
import java.awt.Color;

/**
 *
 * @author Michal Skovajsa
 */
public class Element extends AVL_Tree.Node{
    
    private String name;
    private String note;
    private Color color;
    private int fontSize;
    private StringKey kk;
    
    private DiagramElement diagramElement;
        
     /**
     * @Class constructor.
     */
    public Element(String paName){
        super(new AVL_Tree.StringKey(paName));
        kk=new StringKey(paName);
        this.name=paName;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return the fontSize
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * @param fontSize the fontSize to set
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public DiagramElement getDiagramElement() {
        return diagramElement;
    }

    public void setDiagramElement(DiagramElement diagramElement) {
        this.diagramElement = diagramElement;
    }
    
    
    //-----------
    
    @Override
    public Element getLeftSon() {
        return (Element) this.leftSon;
    }

    @Override
    public Element getRightSon() {
        return (Element) this.rightSon;
    }
    @Override
    public Element getFather() {
        return (Element) this.father;
    }

    //@Override
     public int compareTo(Element temp){
        //System.out.println("hhhhhh "+this.getKey().getKey().compareTo(temp.getKey().getKey()));
         
        if(this.getKey().getKey().compareTo(temp.getKey().getKey())==0) return 0;
        if(this.getKey().getKey().compareTo(temp.getKey().getKey())>0) return 1;
        if(this.getKey().getKey().compareTo(temp.getKey().getKey())<0) return -1;
        return -1;
                }
    @Override
    public StringKey getKey(){
        return kk;
        //return this.getKey();
    }

    
    @Override
     public String toString(){
        String ret="";       
        ret=this.getKey().getKey()+" ";
        
        return ret;
    }
    
}
