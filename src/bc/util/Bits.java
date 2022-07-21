package bc.util;

public class Bits {

  final static int LRECL = 256; // buffer size

  FIFO<byte[]> records = new FIFO<>(); // buffer queue

  byte[] record; // current buffer
  int p = LRECL; // buffer pointer

  public void add(byte... v) {
    final var j = v.length;
    var i = 0;
    while (i < j) {
      if (p >= LRECL) {
        p = 0;
        record = new byte[LRECL];
        records.add(record);
      }
      var q = p + (j-i);
      if (q > LRECL) q = LRECL;
      while (p < q) {
        record[p++] = v[i++];
      }
    }
  }

  public int size() {
    return (LRECL * records.size()) - (LRECL - p);
  }

  public int copyTo(byte[] b, int off, int len) {
    var z = off;
    if (len > 0) {
      for (var rec:records) {
        var n = LRECL < len ? LRECL : len;
        System.arraycopy(rec, 0, b, off, n);
        off += n;
        len -= n;
        if (len < 1) break;
      }
    }
    return (off-z);
  }

}
