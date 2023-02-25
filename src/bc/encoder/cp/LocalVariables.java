package bc.encoder.cp;

public class LocalVariables extends Pool {

  final static byte[] THIS = {116,104,105,115};
  /*<init>*/ { put(THIS); }
  
  public short slot(String name) {
    return (short)(put(name.getBytes())-1);
  }
  
  public short wide(String name) {
    var lower = put(name.getBytes());
    put(); // extra slot for long, double
    return (short)(lower-1);
  }

}
