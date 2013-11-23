/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.awt.Color;

/**
 *
 * @author Michal Skovajsa
 */
public class Element{
    
    /* Defaul vaules */
    private String name="unset";
    private String note="unset";
    private Color color =   new Color(10,10,10);
    private Color color2=   new Color(255,255,255);
    private int x=-1;
    private int y=-1;
    private int fontSize=16;    
     /**
     * @Class constructor.
     */
    public Element(String paName){
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


    /**
     * @return the color2
     */
    public Color getColor2() {
        return color2;
    }

    /**
     * @param color2 the color2 to set
     */
    public void setColor2(Color color2) {
        this.color2 = color2;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }
    @Override
    public String toString(){
        String ret;
        ret = getName();
        if(this instanceof Resource){
            ret+=": { "+((Resource)this).getMarking()+" }";
        }
        else if(this instanceof Place){
            ret+=": "+((Place)this).getMarkings().toString();
        }
        return ret;
    }
    
    
}
