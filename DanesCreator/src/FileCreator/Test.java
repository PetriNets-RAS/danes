/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FileCreator;

import Core.Arc;
import Core.PetriNet;
import Core.Place;
import Core.Transition;

/**
 *
 * @author Atarin
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PetriNet pn = new PetriNet("skuska");
        Place p1 = new Place("P1");
        Place p2 = new Place("P2");
        Place p3 = new Place("P3");
        Place p4 = new Place("P4");
        Place p5 = new Place("P5");
        Place p6 = new Place("P6");
        Place p7 = new Place("P7");

        Transition t1 = new Transition("T1");
        Transition t2 = new Transition("T2");
        Transition t3 = new Transition("T3");
        //vystupny a vstupny
        Arc a1 = new Arc("A1", p1, t1);
        Arc a2 = new Arc("A2", t1, p2);
        Arc a3 = new Arc("A3", p3, t1);
        Arc a4 = new Arc("A4", t2, p2);
        Arc a5 = new Arc("A5", t3, p2);
        Arc a6 = new Arc("A6", t3, p3);
        Arc a7 = new Arc("A7", p4, t2);
        Arc a8 = new Arc("A8", p5, t2);
        Arc a9 = new Arc("A9", p5, t3);
        Arc a10 = new Arc("A10", p6, t3);
        Arc a11 = new Arc("A11", p7, t3);

        pn.addPlace(p1);
        pn.addPlace(p2);
        pn.addPlace(p3);
        pn.addPlace(p4);
        pn.addPlace(p5);
        pn.addPlace(p6);
        pn.addPlace(p7);

        pn.addTransition(t1);
        pn.addTransition(t2);
        pn.addTransition(t3);
        pn.addArc(a1);
        pn.addArc(a2);
        pn.addArc(a3);
        pn.addArc(a4);
        pn.addArc(a5);
        pn.addArc(a6);
        pn.addArc(a7);
        pn.addArc(a8);
        pn.addArc(a9);
        pn.addArc(a10);
        pn.addArc(a11);
        
        XMLFileCreator xml=new XMLFileCreator();
        
        System.out.println(xml.createPetriXML(pn));
        
        
        
    }
}
