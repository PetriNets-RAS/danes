/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;


public class PairValue<L,R,Data> {
  private final L left;
  private final R right;
  private final Data data;

  public PairValue(L left, R right, Data data) {
    this.left = left;
    this.right = right;
    this.data = data;
  }

  public L getLeft() { return left; }
  public R getRight() { return right; }
  public Data getData() { return data; }

  @Override
  public int hashCode() { return left.hashCode() ^ right.hashCode(); }

  @Override
  public boolean equals(Object o) {
    if (o == null) return false;
    if (!(o instanceof PairValue)) return false;
    PairValue pairo = (PairValue) o;
    return this.left.equals(pairo.getLeft()) &&
           this.right.equals(pairo.getRight());
  }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
  
  
}
