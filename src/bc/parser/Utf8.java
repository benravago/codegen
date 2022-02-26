package bc.parser;

final class Utf8 {

  static CharSequence decode(byte[] b, int o, int len) {
    len += o;
    int u, v, w, x, y;
    var r = new StringBuilder();
    while (o < len) {
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
                  r.append((char) ((v & 0x00f) + 1)); // supplementary character
                  c = ((w & 0x03f) << 10) + ((x & 0x00f) << 6) + (y & 0x03f);
                }
              }
              // else invalid
            }
          }
        }
      }
      if (c < 0 || o > len) {
        throw new IllegalArgumentException("at "+o);
      }
      r.append((char) c);
    }
    return r;
  }

}
