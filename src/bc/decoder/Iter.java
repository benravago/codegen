package bc.decoder;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.IntFunction;

import bc.ClassFile.Iteration;
import bc.decoder.Bytecode.Span;

class Iter {

  static <T> Iteration<T> of(int start, int end, IntFunction<T> make) {
    return new Iteration<>() {
      @Override
      public int size() {
        return end - start;
      }
      @Override
      public Iterator<T> iterator() {
        return new Iterator<>() {
          int i = start;
          @Override
          public boolean hasNext() {
            return i < end;
          }
          @Override
          public T next() {
            return i < end ? make.apply(i++) : null;
          }
        };
      }};
  }

  static <T> Iteration<T> of(int count, Span span, Function<Span,T> make) {
    return new Iteration<>() {
      @Override
      public int size() {
        return count;
      }
      @Override
      public Iterator<T> iterator() {
        return new Iterator<>() {
          int i = 0;
          final Span a = span.dup();
          @Override
          public boolean hasNext() {
            return i < count;
          }
          @Override public T next() {
            if (i < count) {
              i++;
              return make.apply(a);
            }
            return null;
          }
        };
      }};
  }

  static <T> Iterable<T> of(Span span, Function<Span,T> make) {
    return () -> new Iterator<>() {
      final Span a = span.dup();
      @Override public boolean hasNext() { return a.more(); }
      @Override public T next() { return make.apply(a); }
    };
  }

}
