package bc.encoder.cp;

public class LocalVariables extends Pool {

  final static byte[] THIS = {116,104,105,115};
  /*<init>*/ { put(THIS,1); }

  public byte slot(String name) {
    return (byte)(put(name.getBytes(),1)-1);
  }

  public short wide(String name) {
    return (short)(put(name.getBytes(),2)-1);
  }

}
