/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

/**
 *
 * @author MISO
 */
public class ComplexElement extends Element{
    protected int width=-1;
    protected int height=-1;
    private boolean connectedToHorizontalMagnet;
    private boolean connectedToVerticalMagnet;

    
    public ComplexElement(String pString){
        super(pString);
        this.height = -1;
        this.width = -1;
        this.connectedToHorizontalMagnet = false;
    }

   @Override
   public void setName(String name) {
        super.setName(name);
        this.height = -1;
        this.width = -1;
    } 
    
    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the isConnectedToMagnet
     */
    public boolean isConnectedToHorizontalMagnet() {
        return connectedToHorizontalMagnet;
    }

    /**
     * @param isConnectedToMagnet the isConnectedToMagnet to set
     */
    public void setConnectedToHorizontalMagnet(boolean isConnectedToMagnet) {
        this.connectedToHorizontalMagnet = isConnectedToMagnet;
    }
    
    public boolean isConnectedToVerticalMagnet() {
        return connectedToVerticalMagnet;
    }

    public void setConnectedToVerticalMagnet(boolean connectedToVerticalMagnet) {
        this.connectedToVerticalMagnet = connectedToVerticalMagnet;
    }
}
