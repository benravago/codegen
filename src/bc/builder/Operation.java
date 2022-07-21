package bc.builder;

import bc.CompilationUnit.Code;
import bc.util.Bits;
import bc.JVMS;

class Operation extends Instruction implements Code {

  @Override
  Code i_(byte o) {
    buf.add(o);  // 1() op
    return this;
  }
  @Override
  Code i_1b(byte o, byte b) {
    buf.add(o,b);  //  1(1) op, byte
    return this;
  }
  @Override
  Code i_2s(byte o, short s) {
    buf.add((byte)o,(byte)(s>>>8),(byte)(s));  // 1(2) op, short
    return this;
  }
  @Override
  Code i_1c(int o, Object...c) {
    var s = cp_index(o,c);
    buf.add((byte)o,(byte)(s));  // 1(1) op, cp.index
    return this;
  }
  @Override
  Code i_2c(int o, Object...c) {
    var s = cp_index(o,c);
    buf.add((byte)o,(byte)(s>>>8),(byte)(s));  // 1(2) op, cp.index
    return this;
  }
  @Override
  Code i_2c_1d(int o, byte d, Object...c) {
    var s = cp_index(o,c);
    buf.add((byte)o,(byte)(s>>>8),(byte)(s),d);  // 1(2,1) op, cp.index, const
    return this;
  }
  @Override
  Code i_1v(byte o, int v) {
    buf.add(o,(byte)(lv_index(v)));  // 1(1) lv.index
    return this;
  }
  @Override
  Code i_1v_1d (byte o, int v, byte d) {
    buf.add(o,(byte)(lv_index(v)),d);  // 1(1,1) op, lv.index, const
    return this;
  }
  @Override
  Code i_1t(byte o, byte t) {
    buf.add(o,t);  // 1(1) op, atype
    return this;
  }
  @Override
  Code i_2j(byte o, int j) {
    var i = branch(j);
    buf.add(o,(byte)(i>>>8),(byte)(i));  // 1(2) op, branch
    return this;
  }
  @Override
  Code i_4j(byte o, int j) {
    var i = branch(j);
    buf.add(o,(byte)(i>>>24),(byte)(i>>>16),(byte)(i>>>8),(byte)(i));  // 1(4) op, branch
    return this;
  }
  @Override
  Code i_2c_1d_0(int o, byte d, Object...c) {
    var s = cp_index(o,c);
    buf.add((byte)o,(byte)(s>>>8),(byte)(s),d,(byte)0);  // 1(2,1,0) op, cp.index, count, 0
    return this;
  }
  @Override
  Code i_2c_0_0(int o, Object...c) {
    var s = cp_index(o,c);
    buf.add((byte)o,(byte)(s>>>8),(byte)(s),(byte)0,(byte)0);  // 1(2,0,0) op, cp.index, 0, 0
    return this;
  }
  @Override
  Code i_p_4d_4d_4d_x(byte o, int...a) {
    assert a.length > 3;
    u0(o); u4(a[0]); u4(a[1]); u4(a[2]);
    for (var i = 3; i < a.length; i++) {
      u4(branch(a[i]));
    } // 1(0-3,4,4,4,...) op, padding, default, low, high, jump offsets
    return this;
  }
  @Override
  Code i_p_4d_4d_x(byte o, int...a) {
    assert a.length > 2 && a.length % 2 == 0;
    u0(o); u4(a[0]); u4(a[1]);
    assert a[1] == (a.length - 2) / 2;
    for (var i = 2; i < a.length; i++) {
      u4(a[i++]); u4(branch(a[i]));
    } // 1(0-3,4,4,...) op, padding, default, npairs, match/offset pairs
    return this;
  }
  @Override
  Code i_1w_2v_x(byte o, byte w, int...a) {
    assert a.length > 1;
    var v = lv_index(a[0]);
    if (w == JVMS.OP_iinc) {
      assert a.length == 2;
      var s = a[1];
      buf.add(o,w,(byte)(v>>>8),(byte)(v),(byte)(s>>>8),(byte)(s));  // 1(1,2,2) op, iinc, lv.index, count
    } else {
      assert a.length == 1;
      buf.add(o,w,(byte)(v>>>8),(byte)(v));  // 1(1,2) op, wide, lv.index
    }
    return this;
  }

  // other info

  @Override public Code $(int tag) { return this; }

  @Override public Code $var(int tag, String name, Object... info) { return this; }
  @Override public Code $try(int tag, Object type) { return this; }

  @Override public Code $start(int... tag) { return this; }
  @Override public Code $end(int... tag) { return this; }

  // TODO:

  Bits buf;

  short cp_index(int ref, Object...args) { return -1; }
  short lv_index(int tag) { return -1; }
  int branch(int tag) { return -1; }
  void u0(byte o) {}
  void u4(int i) {} // add to buf
}
