package bc.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

abstract class Linked<E> implements Iterable<E> {

  protected class Node { Node next; E item; }

  protected int size = 0;
  protected Node head = null;

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public void clear() {
    head = null;
    size = 0;
  }

  public E peek() {
    return head != null ? head.item : null;
  }

  @Override
  public Iterator<E> iterator() {
    return new Iterator<>() {
      Node previous, current, node = head;

      @Override
      public boolean hasNext() {
        return node != null;
      }

      @Override
      public E next() {
        if (hasNext()) {
          previous = current;
          current = node;
          node = node.next;
          return current.item;
        }
        throw new NoSuchElementException();
      }

      @Override
      public void remove() {
        if (current == null) {
          throw new IllegalStateException();
        }
        if (previous != null) {
          previous.next = current.next;
        } else {
          assert current == head;
          head = node;
        }
        current.next = null;
      }

    };
  }

}
