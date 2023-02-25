package bc.encoder.cp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JumpTable {
  
  // key   = jump label
  // value = [0] -> label offset
  //         [1..n] -> label reference; if 0x80000000 31 bit else 16 bit

  Map<String,int[]> table = new HashMap<>();
  
  public void set(String key, int offset) {
    var ref = table.get(key);
    if (ref == null) {
      ref = new int[]{offset};
    } else {
      if (ref[0] == 0) {
        ref[0] = offset;
      } else {  
        throw new IllegalArgumentException("label '"+key+"' is already defined");
      }  
    }
    table.put(key, new int[] {offset});  
  }
  
  public int add(String key, int reference) {
    var ref = table.get(key);
    if (ref == null) {
      ref = new int[]{0,reference};
    } else {
      ref = Arrays.copyOf(ref,ref.length+1);
      ref[ref.length-1] = reference;
    }
    table.put(key,ref);
    return ref[0];
  }
  
  public int wide(String key, int reference) {
    return add(key, reference | 0x8000_0000);
  }
  
  public void resolve(byte[] b) {
    for (var ref:table.values()) {
      var j = ref[0];
      for (var i = 1; i < ref.length; i++) {
        var r = ref[i];
        if (r < 0) { // high bit on
          r &= 0x7fff_ffff;
          b[r] = (byte)(j>>>24); b[r+1] = (byte)(j>>>16); b[r+2] = (byte)(j>>>8); b[r+3] = (byte)j;
        } else {
          b[r] = (byte)(j>>>8); b[r+1] = (byte)j;
        }
      }
    }
  }
  
}
