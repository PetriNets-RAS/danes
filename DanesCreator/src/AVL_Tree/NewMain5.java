/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AVL_Tree;

import java.util.Random;

/**
 *
 * @author Atarin
 */
public class NewMain5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Tree stromVlastnikov = new Tree(null);
        Random gen = new Random();
        int lol=0;
        //naplna vlastnikov
        for (int i = 0; i < 10000; i++) {
            int den = gen.nextInt(1000000) + 1;

            //int rodCislo=Integer.parseInt(hlp);
            //int ll = gen.nextInt(20);
            stringKatUzemie lv = new stringKatUzemie("a" + den, i);
            if (stromVlastnikov.find(lv.getKey()) == null) {
                //System.out.println("vkladam");
                stromVlastnikov.addNode(lv);
                //System.out.println(stromVlastnikov.getRoot().getKey().getKey());
            }
            if(i==9999) lol=den;

        }
        for (int i = 0; i < 10000; i++) {
            int den = gen.nextInt(1000000) + 1;

            //int rodCislo=Integer.parseInt(hlp);
            //int ll = gen.nextInt(20);
            //stringKatUzemie lv = new stringKatUzemie("a" + den, i);
            //if (stromVlastnikov.find(lv.getKey()) == null) {
                //System.out.println("vkladam");
                stromVlastnikov.delete(new StringKey("a"+den));
                System.out.println("mazem "+den);
            //}

        }
        System.out.println(stromVlastnikov.find(new StringKey("a"+lol)));
        stromVlastnikov.InOrder(stromVlastnikov.getRoot());


    }
}
