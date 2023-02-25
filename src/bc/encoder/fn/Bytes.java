package bc.encoder.fn;

public interface Bytes {
  int copyTo(byte[] dest, int destPos);
  int size();
}
