package bc.util;

public class ByteBuilder implements ByteSequence {

  protected byte[] buf;
  protected int count;

  public ByteBuilder() {
    this(16);
  }

  public ByteBuilder(int size) {
    count = 0;
    buf = new byte[size];
  }

  public void append(int b) {
    if (count >= buf.length) {
      var more = new byte[buf.length << 1];
      System.arraycopy(buf, 0, more, 0, buf.length);
      buf = more;
    }
    buf[count++] = (byte) b;
  }

  public byte[] toByteArray() {
    var less = new byte[count];
    System.arraycopy(buf, 0, less, 0, count);
    return less;
  }

  @Override
  public byte byteAt(int index) {
    if (-1 < index && index < count) {
      return buf[index];
    }
    throw new IndexOutOfBoundsException(index);
  }

  @Override
  public int length() {
    return count;
  }

  @Override
  public ByteBuilder subSequence(int start, int end) {
    if (0 <= start && start <= end && end <= count) {
      var len = end - start;
      var sub = new ByteBuilder(len);
      sub.count = len;
      System.arraycopy(buf, start, sub.buf, 0, len);
      return sub;
    }
    throw new IndexOutOfBoundsException("" + start + ',' + end);
  }

  @Override
  public String toString() {
    return new String(buf, 0, count);
  }

}
