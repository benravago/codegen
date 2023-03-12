package bc.encoder.cp;

import java.util.Arrays;

public class Table {

  String[] keys;
  int[][] values;
  int limit;

  public int set(String key) {
    var i = Arrays.binarySearch(keys, 0, limit, key);
    if (i >= 0) return i;

    i = -i - 1; // get insertion point
    var k = keys;
    var v = values;
    if (limit >= keys.length) {
      var n = (int)(limit * 1.5);
      keys = new String[n]; values = new int[n][];
      if (0 < i) {
        System.arraycopy(k,0,keys,0,i);
        System.arraycopy(v,0,values,0,i);
      }
    }
    if (i < limit) {
      var n = limit-i;
      System.arraycopy(k,i,keys,i+1,n);
      System.arraycopy(v,i,values,i+1,n);
    }
    limit += 1;
    keys[i] = key;
    return i;
  }

}
