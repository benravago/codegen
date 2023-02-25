package bc.spec;

import java.util.Arrays;

public interface UTF {

  static CharSequence decode(byte[] b, int o, int len) {
    var r = new Object() {
      char[] buf = new char[len];
      int count = 0;

      void add(int i) {
        var n = buf.length;
        if (n <= count) {
          var b = buf;
          buf = new char[n + (n/2)];
          System.arraycopy(b,0,buf,0,n);
        }
        buf[count++] = (char)i;
      }
    };

    var p = o + len;
    int u, v, w, x, y;
    while (o < p) {
      int c = -1;
      u = b[o++] & 0x0ff;
      if (u < 0b00000001) { // 0x01
        // invalid
      } else {
        if (u < 0b10000000) { // 0x7f + 1
          c = u; // 1 byte, code point u0001:u007f
        } else {
          if (u < 0b11000000) { // 0x0c0
            // invalid
          } else {
            if (u < 0b11100000) { // 0x0df + 1
              v = b[o++]; // 2 bytes,  code point u0000 & u0080:u07ff
              c = ((u & 0x01f) << 6) + (v & 0x03f);
            } else {
              if (u < 0b11110000) { // 0x0ef + 1
                if (u != 0b11101101) {
                  v = b[o++];
                  w = b[o++]; // 3 bytes,  code point u0800:uffff
                  c = ((u & 0x00f) << 12) + ((v & 0x03f) << 6) + (w & 0x03f);
                } else { // 0x0ed
                  v = b[o++];
                  w = b[o++];
                  o++; // skip
                  x = b[o++];
                  y = b[o++]; // 6 bytes, code point > uffff
                  r.add((char) ((v & 0x00f) + 1)); // supplementary character
                  c = ((w & 0x03f) << 10) + ((x & 0x00f) << 6) + (y & 0x03f);
                }
              }
              // else invalid
            }
          }
        }
      }
      if (c < 0 || o > p) {
        throw new IllegalArgumentException("at "+o);
      }
      r.add((char)c);
    }
    return new String(r.buf,0,r.count);
  }

  static byte[] encode(CharSequence s) { return encode(s,0); }
  
  static byte[] encode(CharSequence s, int prefix) {
    var len = s.length();
    var r = new Object() {
      byte[] buf = new byte[prefix+len];
      int count = prefix;

      void add(int i) {
        var n = buf.length;
        if (n <= count) {
          var b = buf;
          buf = new byte[n + (n/2)];
          System.arraycopy(b,0,buf,0,n);
        }
        buf[count++] = (byte)i;
      }
    };

    for (var i = 0; i < len; i++) {
      var c = s.charAt(i);
      if (c > 1 && c < 0x080) { // 0x01 to 0x7f
        r.add(c);
      } else {
        if (c < 0x0800) { // 0x0080 to 0x07ff
          r.add(0xc0 | ((c >> 6) & 0x1f));
          r.add(0x80 | (c & 0x3f));
        } else {
          if (c < 0x010000) { // 0x0800 to 0xffff
            r.add(0xe0 | ((c >> 12) & 0x0f));
            r.add(0x80 | ((c >> 6) & 0x3f));
            r.add(0x80 | (c & 0x3f));
          } else { // 0x00010000+
            r.add(0xed);
            r.add(0xa0 | (((c >> 16) - 1) & 0x0f));
            r.add(0x80 | ((c >> 10) & 0x3f));
            r.add(0xed);
            r.add(0xb0 | ((c >> 6) & 0x0f));
            r.add(0x80 | (c & 0x3f));
          }
        }
      }
    }
    return Arrays.copyOf(r.buf,r.count);
  }

}
