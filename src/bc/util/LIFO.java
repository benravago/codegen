package bc.util;

public class LIFO<E> extends Linked<E> { // Stack

  public void push(E item) {
    var node = new Node();
    node.item = item;
    node.next = head;
    head = node;
    size++;
  }

  public E pop() {
    if (head == null) {
      return null;
    }
    var node = head;
    head = head.next;
    size--;
    return node.item;
  }

}
