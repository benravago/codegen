package bc.util;

public interface ByteSequence {
  byte byteAt(int index);
  int length();
  ByteSequence subSequence(int start, int end);
  String toString();
}
