package bc.encoder.fn;

public interface Macros {

  static String moreThan(int i) { return "more than "+i+" values"; }
  static String lessThan(int i) { return "less than "+i+" values"; }

  static <T> T arg(T[] a, int i) {
    if (a.length > i) return a[i];
    throw new IllegalArgumentException("missing argument["+i+']');
  }

  static <T> T arg(T[] a, int i, T d) {
    return a.length > i && a[i] != null ? a[i] : d;
  }

}

