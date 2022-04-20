package bc.builder;

import bc.CompilationUnit.Code;

class Opcode extends Instruction implements Code {

  @Override Code i_             (byte o)                     { return this; } //  -
  @Override Code i_1b           (byte o, byte b)             { return this; } //  (1)  byte
  @Override Code i_2s           (byte o, short s)            { return this; } //  (2)  short
  @Override Code i_1c           (byte o, Object c)           { return this; } //  (1)  cp.index
  @Override Code i_2c           (byte o, Object c)           { return this; } //  (2)  cp.index
  @Override Code i_2c_1d        (byte o, Object c, byte d)   { return this; } //  (2,1)  cp.index, const
  @Override Code i_1v           (byte o, Object v)           { return this; } //  (1)  lv.index
  @Override Code i_1v_1d        (byte o, Object v, byte d)   { return this; } //  (1,1)  lv.index, const
  @Override Code i_1t           (byte o, byte t)             { return this; } //  (1)  atype
  @Override Code i_2j           (byte o, Object j)           { return this; } //  (2)  branch
  @Override Code i_4j           (byte o, Object j)           { return this; } //  (4)  branch
  @Override Code i_2c_1d_0      (byte o, Object c, byte d)   { return this; } //  (2,1,0)  cp.index, count, 0
  @Override Code i_2c_0_0       (byte o, Object c)           { return this; } //  (2,0,0)  cp.index, 0, 0
  @Override Code i_p_4d_4d_4d_v (byte o, Object...a)         { return this; } //  (0-3,4,4,4,...) padding, default, low, high, jump offsets
  @Override Code i_p_4d_4d_v    (byte o, Object...a)         { return this; } //  (0-3,4,4,...)  padding, default, npairs, match/offset pairs
  @Override Code i_1w_2c_v      (byte o, byte w, Object...a) { return this; } //  (1,2 | 1,2,2) opcode, cp.index | iinc, cp.index, count

  // other info

  @Override public Code $(int n) { return this; }

  @Override public Code $var(int index, String name, String... info) { return this; }
  @Override public Code $drop(int... index) { return this; }

  @Override public Code $start(int n) { return this; }
  @Override public Code $end(int n) { return this; }
  @Override public Code $handle(int n, Object type) { return this; }

}
