package bc.encoder.cp;

import java.util.Arrays;

public class JumpTable extends Table {

  // value = int[]{ offset, reference ... }

  public int set(String key, int offset) {
    var i = set(key);
    var v = values[i];
    if (v == null) {
      v = new int[]{offset};
    } else {
      assert v[0] == 0 : "label '"+key+"' is already defined";
      v[0] = offset;
    }
    values[i] = v;
    return i;
  }

  public int mark(String key, int reference) {
    var i = set(key);
    var v = values[i];
    if (v == null) {
      v = new int[]{0,reference};
    } else {
      v = Arrays.copyOf(v,v.length+1);
      v[v.length-1] = reference;
    }
    values[i] = v;
    return i;
  }

  public int wide(String key, int reference) {
    return mark(key, reference | 0x8000_0000); // set wide bit
  }

  public void resolve(byte[] b) {
    for (var v = 0; v < limit; v++) {
      var ref = values[v];
      var j = ref[0];
      for (var i = 1; i < ref.length; i++) {
        var r = ref[i];
        if (r < 0) { // is wide bit on? - or check for goto_w/jsr_w at b[r-1]
          r &= 0x7fff_ffff;
          b[r] = (byte)(j>>>24); b[r+1] = (byte)(j>>>16); b[r+2] = (byte)(j>>>8); b[r+3] = (byte)j;
        } else {
          b[r] = (byte)(j>>>8); b[r+1] = (byte)j;
        }
      }
    }
  }

}
