package one;

public class Tree<T extends Comparable<T>> {

  private class Node<T extends Comparable<T>> {
    private Node left, right, parent;
    private T data;
    public Node(T data) {
      this.data = data;
    }
    
    public void insert( Node<T> node ) {
      if( node.data.compareTo(this.data) > 0 ) {
        if( left != null) {
          left.insert(node);
        }
        else {
          left = node;
          node.parent = this;
        }
      }
      else {
        if( right != null) {
          right.insert(node);
        }
        else {
          right = node;
          node.parent = this;
        }
      }
    }
  }
}
