package bc.encoder.cp;

import java.util.Arrays;

public class ExceptionTable extends Table {

  // value = int[]{ start,end, handler,exception ... }

  public void start(String tag, int offset) {
    var i = set(tag);
    assert values[i] == null : "try '"+tag+"' already defined";
    values[i] = new int[]{offset,0};
  }

  int get(String tag) {
    var i = Arrays.binarySearch(keys,tag);
    assert i >= 0 : "try '"+tag+"' not previously defined";
    return i;
  }

  public void end(String tag, int offset) {
    var i = get(tag);
    var v = values[i];
    assert v[1] == 0 : "try '"+tag+"' already closed";
    v[1] = offset;
  }

  public void handler(String tag, int offset, int exception) {
    var i = get(tag);
    var v = values[i];
    var n = v.length+2;
    v = Arrays.copyOf(v, n);
    v[--n] = exception;
    v[--n] = offset;
    values[i] = v;
  }

}
/*
 *       u2 exception_table_length;
       {
         u2 start_pc;   -> $try()
         u2 end_pc;     -> $end()
         u2 handler_pc; -> $catch(tag
         u2 catch_type; ->           ,type)
         
       } exception_table[exception_table_length];
 *
*/
