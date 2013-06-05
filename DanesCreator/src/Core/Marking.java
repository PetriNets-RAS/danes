/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Miso
 */
public class Marking {
    private ArrayList<Integer> markings;
    private Element element;
    public Marking(Element pAbsPlace)
    {
        this.markings = new ArrayList<Integer>();
        this.element = pAbsPlace;
    }
    
    /**
     * @return the markings
     */
    public List<Integer> getMarkings() {
        return markings;
    }

    /**
     * @param markings the markings to set
     */
    public void setMarkings(ArrayList<Integer> markings) {
        this.markings = markings;
    }
    
    @Override
    public String toString()
    {
        String prefix = "";
        if (this.element instanceof Resource) {
            prefix = "R_";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (int i = 0; i < this.markings.size(); i++) {
            if (i == (this.markings.size() - 1)) {
                builder.append(prefix + this.markings.get(i));
            }
            else
            {
                builder.append(prefix + this.markings.get(i)+",");
            }
        }
        builder.append("}");
        return builder.toString();
    }
}

