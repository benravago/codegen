package bc.encoder.op;

import java.util.Arrays;

import bc.CompilationUnit.Code;
import static bc.spec.JVMS.*;

abstract class Forms /* extends Format */  implements Code {

  byte[] buffer = new byte[256];
  int position = 0;

  void ensure(int length) {
    if (buffer.length <= position + length) {
      buffer = Arrays.copyOf(buffer, (int)((position+length) * 1.5) );
    }
  }

  //  i_             (byte o);                               //  -

  //  i_1b           (byte o, byte b);                       //  (1)  byte
  //  i_2s           (byte o, short s);                      //  (2)  short

  //  i_1c           (byte o, byte c);                       //  (1)  cp.index
  //  i_2c           (byte o, short c);                      //  (2)  cp.index
  //  i_2c_1d        (byte o, short c, byte d);              //  (2,1)  cp.index, const

  //  i_1v           (byte o, byte v);                       //  (1)  lv.index
  //  i_1v_1d        (byte o, byte v, byte d);               //  (1,1)  lv.index, const

  //  i_1t           (byte o, byte t);                       //  (1)  atype

  //  i_2j           (byte o, short j);                      //  (2)  branch
  //  i_4j           (byte o, int j);                        //  (4)  branch

  //  i_2c_1d_0      (byte o, short c, byte d);              //  (2,1,0)  cp.index, count, 0
  //  i_2c_0_0       (byte o, short c);                      //  (2,0,0)  cp.index, 0, 0

  //  i_p_4d_4l_4h_x (byte o, int d, int l, int h, int...j); //  (0-3,4,4,4,...)  padding, default, low, high, jump offsets
  //  i_p_4d_4n_x    (byte o, int d, int n, int...mo);       //  (0-3,4,4,...)  padding, default, npairs, match/offset pairs

  //  i_1w_2v_d      (byte o, byte w, short v, short...n);   //  (1,2 | 1,2,2)  opcode, lv.index | iinc, lv.index, count

  Code i_(byte o) {
    ensure(1);
    buffer[position++] = o;
    return this;
  }

  Code i_1b(byte o, byte b) {
    ensure(2);
    buffer[position] = o;
    buffer[position+1] = b;
    position += 2;
    return this;
  }

  Code i_2s(byte o, short s) {
    ensure(3);
    buffer[position] = o;
    buffer[position+1] = (byte)(s>>>8); buffer[position+2] = (byte)(s);
    position += 3;
    return this;
  }

  Code i_1c(byte o, byte c) {
    ensure(2);
    buffer[position] = o;
    buffer[position+1] = c;
    position += 2;
    return this;
  }

  Code i_2c(byte o, short c) {
    ensure(3);
    buffer[position] = o;
    buffer[position+1] = (byte)(c>>>8); buffer[position+2] = (byte)(c);
    position += 3;
    return this;
  }

  Code i_2c_1d(byte o, short c, byte d) {
    ensure(4);
    buffer[position] = o;
    buffer[position+1] = (byte)(c>>>8); buffer[position+2] = (byte)(c);
    buffer[position+3] = d;
    position += 4;
    return this;
  }

  Code i_1v(byte o, byte v) {
    ensure(2);
    buffer[position] = o;
    buffer[position+1] = v;
    position += 2;
    return this;
  }

  Code i_1v_1d(byte o, byte v, byte d) {
    ensure(3);
    buffer[position] = o;
    buffer[position+1] = v;
    buffer[position+2] = d;
    position += 3;
    return this;
  }

  Code i_1t(byte o, byte t) {
    ensure(2);
    buffer[position] = o;
    buffer[position+1] = t;
    position += 2;
    return this;
  }

  Code i_2j(byte o, short j) {
    ensure(3);
    buffer[position] = o;
    buffer[position+1] = (byte)(j>>>8); buffer[position+2] = (byte)(j);
    position += 3;
    return this;
  }

  Code i_4j(byte o, int j) {
    ensure(5);
    buffer[position] = o;
    buffer[position+1] = (byte)(j>>>24); buffer[position+2] = (byte)(j>>>16);
    buffer[position+3] = (byte)(j>>>8); buffer[position+4] = (byte)(j);
    position += 5;
    return this;
  }

  Code i_2c_1d_0(byte o, short c, byte d) {
    ensure(5);
    buffer[position] = o;
    buffer[position+1] = (byte)(c>>>8); buffer[position+2] = (byte)(c);
    buffer[position+3] = d;
    buffer[position+4] = 0;
    position += 5;
    return this;
  }

  Code i_2c_0_0(byte o, short c) {
    ensure(5);
    buffer[position] = o;
    buffer[position+1] = (byte)(c>>>8); buffer[position+2] = (byte)(c);
    buffer[position+3] = buffer[position+4] = 0;
    position += 5;
    return this;
  }

  static int padding(int p) { return (4-(p&3))%4; } // or ((p+3)&-4)-p
  void pad(int n) { while (n-- > 0) buffer[position++] = 0; }

  void u1(byte b) {
    buffer[position++] = b;
  }
  void u2(short s) {
    buffer[position++] = (byte)(s>>>8); buffer[position++] = (byte)(s);
  }
  void u4(int i) {
    buffer[position++] = (byte)(i>>>24); buffer[position++] = (byte)(i>>>16);
    buffer[position++] = (byte)(i>>>8); buffer[position++] = (byte)(i);
  }

  Code i_p_4d_4l_4h_x(byte o, int d, int l, int h, int...a) {
    assert a.length > 0 : "no jump offsets";
    assert h - l == a.length : "invalid range";
    var p = padding(position+1);
    ensure(1+p+4+4+4+(4*a.length)); // 1,0-3,4,4,4,...  op, padding, default, low, high, jump offsets
    u1(o); pad(p); u4(d); u4(l); u4(h);
    for (var x:a) u4(x);
    return this;
  }

  Code i_p_4d_4n_x(byte o, int d, int n, int...a) {
    assert a.length > 0 : "no match/offset pairs";
    assert n * 2 == a.length : "wrong pair count";
    var p = padding(position+1);
    ensure(1+p+4+4+(4*a.length)); // 1,0-3,4,4,...  op, padding, default, npairs, match/offset pairs
    u1(o); pad(p); u4(d); u4(n);
    for (var x:a) u4(x);
    return this;
  }

  Code i_1w_2v_d(byte o, byte w, short v, short...d) {
    if (w == OP_iinc) {
      assert d.length == 1 : "invalid count";
      ensure(1+1+2+2); // 1,1,2,2  op, iinc, lv.index, count
      u1(o); u1(w); u2(v); u2(d[0]);
    } else { // optimistic
      assert d.length == 0 : "invalid parameter";
      ensure(1+1+2); // 1,1,2  op, wide, lv.index
      u1(o); u1(w); u2(v);
    }
    return this;
  }

}
