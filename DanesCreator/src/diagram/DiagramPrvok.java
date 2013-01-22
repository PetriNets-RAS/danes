/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diagram;

import danescreator.Prvok;

/**
 *
 * @author marek
 */
public class DiagramPrvok {
    private Prvok prvok;
    private int x;
    private int y;

    public DiagramPrvok(Prvok prvok, int x, int y) {
        this.prvok = prvok;
        this.x = x;
        this.y = y;
    }

    public Prvok getPrvok() {
        return prvok;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    
    
}
