/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FileManager;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 *
 * @author Michal
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {
        String phrase = "1`1++1`2++2`3";
        String[] tokens = phrase.split("\\+\\+");
        for (int i = 0; i < tokens.length; i++) {
            System.out.println(tokens[i]);
            System.out.println(tokens[i].length());
        }
    }
}
