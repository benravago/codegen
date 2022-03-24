package bc.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

abstract class Linked<E> implements Iterable<E> {

  protected int size = 0;
  protected Object[] head = null;

  protected static final int NEXT = 0, ITEM = 1;

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

  @SuppressWarnings("unchecked")
  public E peek() {
    return head != null ? (E) head[ITEM] : null;
  }

  @Override
  public Iterator<E> iterator() {
    return new Iterator<>() {
      Object[] previous, current, next = head;

      @Override
      public boolean hasNext() {
        return next != null;
      }

      @SuppressWarnings("unchecked")
      @Override
      public E next() {
        if (hasNext()) {
          previous = current;
          current = next;
          next = (Object[]) next[NEXT];
          return (E) current[ITEM];
        }
        throw new NoSuchElementException();
      }

      @Override
      public void remove() {
        if (current == null) {
          throw new IllegalStateException();
        }
        if (previous != null) {
          previous[NEXT] = current[NEXT];
        } else {
          assert current == head;
          head = next;
        }
        current[NEXT] = null;
      }

    };
  }

}
