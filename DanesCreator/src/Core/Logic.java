/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.util.StringTokenizer;

/**
 *
 * @author Michal Skovajsa
 */
public class Logic {

    /**
     * @Class constructor.
     */
    public Logic() {
    }

    /**
     * @method, that checks arc validity
     */
    public boolean checkArc(Arc paArc, Graph graph) {

        Element p1 = paArc.getOutElement();
        Element p2 = paArc.getInElement();
        // Petri Net - Place / Transition
        if (graph instanceof PetriNet) {
            if (((p1 instanceof AbsPlace) && (p2 instanceof Transition)) | ((p2 instanceof AbsPlace) && (p1 instanceof Transition))) {
                return true;
            }
        }
        // Precedence Graph - Node / Node
        if (graph instanceof PrecedenceGraph) {
            if (((p1 instanceof Node) && (p2 instanceof Node)) && !p1.equals(p2)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkNumbString(String input) {

        for (int i = 0; i < input.length(); i++) {
            if ((input.charAt(i) < 48) || (input.charAt(i) > 57)) {
                return false;
            }
        }
        if (input.charAt(0) == '0') {
            return false;
        }
        return true;
    }

    public static String[] splitIntoLine(String input, int maxCharInLine) {

        StringTokenizer tok = new StringTokenizer(input, " ");
        StringBuilder output = new StringBuilder(input.length());
        int lineLen = 0;
        while (tok.hasMoreTokens()) {
            String word = tok.nextToken();

            while (word.length() > maxCharInLine) {
                output.append(word.substring(0, maxCharInLine - lineLen) + "\n");
                word = word.substring(maxCharInLine - lineLen);
                lineLen = 0;
            }

            if (lineLen + word.length() > maxCharInLine) {
                output.append("\n");
                lineLen = 0;
            }
            output.append(word + " ");

            lineLen += word.length() + 1;
        }
        return output.toString().split("\n");
    }

    public static int maxWorldLength(String input) {
        StringTokenizer tok = new StringTokenizer(input, " ");
        StringBuilder output = new StringBuilder(input.length());
        int lineLen = 0;
        int maxWLength=0;
        while (tok.hasMoreTokens()) {
            String word = tok.nextToken();
            if(word.length()>maxWLength)maxWLength=word.length();

        }
        return maxWLength;

    }
}
