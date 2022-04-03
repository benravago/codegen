package bc.util;

public class FIFO<E> extends Linked<E> { // List

  protected Node tail = null;

  public void add(E item) {
    var node = new Node();
    node.item = item;
    if (tail != null) {
      tail.next = node;
    }
    tail = node;
    if (head == null) {
      head = tail;
    }
    size++;
  }

  public E take() {
    if (head == null) {
      return null;
    }
    var node = head;
    head = node.next;
    size--;
    if (head == null) {
      tail = null;
    }
    return node.item;
  }

}
