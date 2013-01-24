/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diagram;

import Core.Element;

/**
 *
 * @author marek
 */
public class DiagramPrvok {
    private Element prvok;
    private int x;
    private int y;

    public DiagramPrvok(Element prvok, int x, int y) {
        this.prvok = prvok;
        this.x = x;
        this.y = y;
    }

    public Element getPrvok() {
        return prvok;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    
    
}
