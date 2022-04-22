package bc;

public interface Bytecode {

  static ClassFile parse(byte[] b) {
    try {
      return (ClassFile)
        Class.forName("bc.parser.Bytecode").getConstructor(b.getClass()).newInstance((Object)b);
    } catch (Exception e) {
      throw new UnsupportedOperationException(e);
    }
  }

  static CompilationUnit build(float v) { // major_version[.minor_version]
    try {
      return (CompilationUnit)
        Class.forName("bc.builder.Bytecode").getConstructor(Float.TYPE).newInstance(v);
    } catch (Exception e) {
      throw new UnsupportedOperationException(e);
    }
  }

  static CharSequence utf8(byte[] b, int o, int len) {
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

  static byte[] encode(CharSequence s) {
    var n = s.length();
    var b = new bc.util.ByteBuilder(n);
    for (var i = 0; i < n; i++) {
      var c = s.charAt(i);
      if (c > 1 && c < 0x080) { // 0x01 to 0x7f
        b.append(c);
      } else {
        if (c < 0x0800) { // 0x0080 to 0x07ff
          b.append(0xc0 | ((c >> 6) & 0x1f));
          b.append(0x80 | (c & 0x3f));
        } else {
          if (c < 0x010000) { // 0x0800 to 0xffff
            b.append(0xe0 | ((c >> 12) & 0x0f));
            b.append(0x80 | ((c >> 6) & 0x3f));
            b.append(0x80 | (c & 0x3f));
          } else { // 0x00010000+
            b.append(0xed);
            b.append(0xa0 | (((c >> 16) - 1) & 0x0f));
            b.append(0x80 | ((c >> 10) & 0x3f));
            b.append(0xed);
            b.append(0xb0 | ((c >> 6) & 0x0f));
            b.append(0x80 | (c & 0x3f));
          }
        }
      }
    }
    return b.toByteArray();
  }


  static int i32(byte[] b, int p) {
    return ( ((b[p] & 0x0ff) << 24) | ((b[p+1] & 0x0ff) << 16) | ((b[p+2] & 0x0ff) <<  8) | (b[p+3] & 0x0ff) );
  }
  static byte[] i32(int i) {
    return new byte[]{ (byte)(i >>> 24), (byte)(i >>> 16), (byte)(i >>> 8), (byte)(i) };
  }

  static long i64(byte[] b, int p) {
    return ( ((b[p] & 0x0ffL) << 56) | ((b[p+1] & 0x0ffL) << 48) | ((b[p+2] & 0x0ffL) << 40) | ((b[p+3] & 0x0ffL) << 32)
           | ((b[p+4] & 0x0ffL) << 24) | ((b[p+5] & 0x0ffL) << 16) | ((b[p+6] & 0x0ffL) <<  8) | (b[p+7] & 0x0ffL) ) ;
  }
  static byte[] i64(long l) {
    return new byte[]{ (byte)(l >>> 56), (byte)(l >>> 48), (byte)(l >>> 40), (byte)(l >>> 32),
                       (byte)(l >>> 24), (byte)(l >>> 16), (byte)(l >>> 8), (byte)(l) };
  }

  // IEEE-754 format

  static float fp32(byte[] b, int p) { return Float.intBitsToFloat(i32(b,p)); }
  static byte[] fp32(float f) { return i32(Float.floatToIntBits(f)); }

  static double fp64(byte[] b, int p) { return Double.longBitsToDouble(i64(b,p)); }
  static byte[] fp64(double d, byte[] b, int p) { return i64(Double.doubleToLongBits(d)); }

}
