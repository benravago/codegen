package bc.encoder.cp;

public class LocalVariables extends Pool {

  final static byte[] THIS = {116,104,105,115};
  /*<init>*/ { put(THIS,1); }
 
  public int v32(String name) {
    var i = put(name.getBytes(),1);
    assert i == last || limit[i] != limit[i+1] : "var '"+name+"' is not 32-bit wide";
    return i;
  }

  public int v64(String name) {
    var i = put(name.getBytes(),2);
    assert i < last && limit[i] == limit[i+1] : "var '"+name+"' is not 64-bit wide";
    return i;
  }

  public int set(String name, int slots) {
    return put(name.getBytes(),slots)-1;
  }

}
