package bc.util;

public class FIFO<E> extends Linked<E> { // List

  protected Object[] tail = null;

  public void add(E item) {
    var next = new Object[] { null, item };
    if (tail != null) {
      tail[NEXT] = next;
    }
    tail = next;
    if (head == null) {
      head = tail;
    }
    size++;
  }

  @SuppressWarnings("unchecked")
  public E take() {
    if (head != null) {
      var next = head;
      head = (Object[]) next[NEXT];
      size--;
      if (head == null) {
        tail = null;
      }
      return (E) next[ITEM];
    }
    return null;
  }

}
