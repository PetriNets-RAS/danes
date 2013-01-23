/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diagram;

import java.util.ArrayList;

/**
 *
 * @author marek
 */
public class DiagramPetrihoSiet {
    private ArrayList<DiagramPrvok> diagramPrvky;
    
    public DiagramPetrihoSiet()
    {
        this.diagramPrvky=new ArrayList<>();
    }
    public void pridajDiagramPrvok(DiagramPrvok pa_diagramPrvok)
    {
        this.diagramPrvky.add(pa_diagramPrvok);
    }

    public ArrayList<DiagramPrvok> getDiagramPrvky() {
        return diagramPrvky;
    }
    
    
}
