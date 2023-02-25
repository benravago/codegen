package bc.encoder.cp;

import java.util.Arrays;

class Pool {

  byte[] buffer = new byte[256];
  int position = 0;
  
  int[] limit = new int[16];
  int index = 0;
   
  int put(byte[] record) {
    var length = record.length;
    A: // see if record is already in buffer
    for (var i = 1; i <= index; i++) {
      var start = limit[i-1];
      var end = limit[i];
      if (length != end - start) continue A;
      for (var p = 0; start < end; p++, start++) {
        if (record[p] != buffer[start]) continue A;
      }
      return i; // found it; same length and content
    }
    // B: not found; copy b to buffer
    if (position + length >= buffer.length) {
      buffer = Arrays.copyOf(buffer, (int)((position+length) * 1.5));
    }
    System.arraycopy(record,0,buffer,position,length);
    // C: set next position
    position += length;
    // D: store into index
    return put(position);
  }
  
  int put(int offset) {
    if (index + 1 >= limit.length) {
      limit = Arrays.copyOf(limit, (int)(limit.length * 1.5));
    }
    limit[++index] = offset;
    return index;
  }
  
  int put() { return put(position); }

  byte[] copyOf() { return Arrays.copyOfRange(buffer,0,position); }
  int copyTo(byte[] dest, int destPos) { System.arraycopy(buffer,0,dest,destPos,position); return position; }

  int length() { return position; } 
  int count() { return index; }

}
