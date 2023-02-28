package bc.encoder.op;

import java.lang.constant.Constable;

import bc.CompilationUnit.Code;
import bc.encoder.cp.LocalVariables;
import bc.encoder.cp.JumpTable;
import bc.encoder.cp.ExceptionTable;
import static bc.spec.JVMS.*;

public abstract class Encode extends Operation {

  LocalVariables local = new LocalVariables();

  private final byte lv(String v) { return local.slot(v); }
  private final short lv_w(String v) { return local.wide(v); }

  JumpTable jumps = new JumpTable();

  private final short to(String j) { return (short) jumps.mark(j,position); }
  private final int to_w(String j) { return jumps.wide(j,position); }
  
  ExceptionTable faults = new ExceptionTable();

  // TODO: handle ConstantPool in Class
  //       also refine api per Constable kind
  
  abstract short cp(Constable c);
  
  //  Code  ldc(Constable c) { ldc((byte)cp(c)); return this; }
  //  Code  ldc_w(Constable c) { ldc_w(cp(c)); return this; }
  //  Code  ldc2_w(Constable c) { ldc2_w(cp(c)); return this; }

  @Override public Code iload(String v) { iload(lv(v)); return this; }
  @Override public Code lload(String v) { lload(lv(v)); return this; }
  @Override public Code fload(String v) { fload(lv(v)); return this; }
  @Override public Code dload(String v) { dload(lv(v)); return this; }
  @Override public Code aload(String v) { aload(lv(v)); return this; }

  @Override public Code istore(String v) { istore(lv(v)); return this; }
  @Override public Code lstore(String v) { lstore(lv(v)); return this; }
  @Override public Code fstore(String v) { fstore(lv(v)); return this; }
  @Override public Code dstore(String v) { dstore(lv(v)); return this; }
  @Override public Code astore(String v) { astore(lv(v)); return this; }

  @Override public Code iinc(String v, byte d) { iinc(lv(v),d); return this; }

  @Override public Code ifeq(String j) { ifeq(to(j)); return this; }
  @Override public Code ifne(String j) { ifne(to(j)); return this; }
  @Override public Code iflt(String j) { iflt(to(j)); return this; }
  @Override public Code ifge(String j) { ifge(to(j)); return this; }
  @Override public Code ifgt(String j) { ifgt(to(j)); return this; }
  @Override public Code ifle(String j) { ifle(to(j)); return this; }

  @Override public Code if_icmpeq(String j) { if_icmpeq(to(j)); return this; }
  @Override public Code if_icmpne(String j) { if_icmpne(to(j)); return this; }
  @Override public Code if_icmplt(String j) { if_icmplt(to(j)); return this; }
  @Override public Code if_icmpge(String j) { if_icmpge(to(j)); return this; }
  @Override public Code if_icmpgt(String j) { if_icmpgt(to(j)); return this; }
  @Override public Code if_icmple(String j) { if_icmple(to(j)); return this; }
  @Override public Code if_acmpeq(String j) { if_acmpeq(to(j)); return this; }
  @Override public Code if_acmpne(String j) { if_acmpne(to(j)); return this; }

  @Override public Code goto_v(String j) { goto_v(to(j)); return this; }
  @Override public Code jsr_v(String j) { jsr_v(to(j)); return this; }
  
  @Override public Code ret(String v) { ret(lv(v)); return this; }

  
  @Override 
  public Code tableswitch(String d, Object...p) { // default, base, offset ...
    assert p.length > 2 && p[0] instanceof Integer : "bad offset parameters";
    var dflt = to_w(d); // default jump offset
    var low = ((Integer)p[1]).intValue(); // index base
    var high = low + p.length - 2; // last index
    var offsets = new int[p.length-1];
    for (var i = 0; i < offsets.length;) {
      offsets[i] = to_w((String)p[++i]);
    }
    tableswitch(dflt,low,high,offsets);
    return this;
  }
  // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.tableswitch
  
  @Override
  public Code lookupswitch(String d, Object...p) { // default, match/offset ...
    assert p.length > 0 && p.length % 2 == 0 : "bad match/offset parameters";
    var dflt = to_w(d); // default jump offset
    var pairs = new int[p.length];
    for (var i = 0; i < p.length;) {
      pairs[i] = ((Integer)p[i++]).intValue(); // match value
      pairs[i] = to_w((String)p[i++]); // jump offset
    }
    sort(pairs);
    lookupswitch(dflt, pairs.length >>> 1, pairs);
    return this;
  }
  // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.lookupswitch
  
  static void sort(int[] mo) { // a simple pair-aware bubble sort
    boolean swap;
    do {
      swap = false;
      for (var i = 0; i < mo.length - 2; i += 2) {
        if (mo[i] > mo[i+2]) {
          var m = mo[i]; mo[i] = mo[i+2]; mo[i+2] = m;
          var o = mo[i+1]; mo[i+1] = mo[i+3]; mo[i+3] = o;
          swap = true;
        }
      }
    } while (swap);
  }

  //  Code  getstatic(Constable c) { getstatic(cp(c)); return this; }
  //  Code  putstatic(Constable c) { putstatic(cp(c)); return this; }

  //  Code  getfield(Constable c) { getfield(cp(c)); return this; }
  //  Code  putfield(Constable c) { putfield(cp(c)); return this; }

  //  Code  invokevirtual(Constable c) { invokevirtual(cp(c)); return this; }
  //  Code  invokespecial(Constable c) { invokespecial(cp(c)); return this; }
  //  Code  invokestatic(Constable c) { invokestatic(cp(c)); return this; }
  //  Code  invokeinterface(Constable c, byte d) { invokeinterface(cp(c),d); return this; }
  //  Code  invokedynamic(Constable c) { invokedynamic(cp(c)); return this; }

  //  Code  anew(Constable c) { anew(cp(c)); return this; }

  @Override public Code newarray(AT t) { newarray(t.bits); return this; }

  //  Code  anewarray(Constable c) { anewarray(cp(c));  return this; }

  //  Code  checkcast(Constable c) { checkcast(cp(c));  return this; }
  //  Code  instance_of(Constable c) { instance_of(cp(c));  return this; }

  @Override public Code wide(WIDE w, String v, short... c) { wide(w.bits,lv_w(v),c); return this; }

  //  Code  multianewarray(Constable c, byte d) { multianewarray(cp(c),d); return this; }

  @Override public Code ifnull(String j) { ifnull(to(j)); return this; }
  @Override public Code ifnonnull(String j) { ifnonnull(to(j)); return this; }

  @Override public Code goto_w(String j) { goto_w(to_w(j)); return this; }
  @Override public Code jsr_w(String j) { jsr_w(to_w(j)); return this; }

  @Override
  public Code $(String tag) {
    jumps.set(tag, position);
    return this;
  }
  @Override
  public Code $var(String tag, int slots) {
    assert slots == 1 || slots == 2 : "invalid slot count";
    local.set(tag,slots);
    return this;
  }
  
  @Override
  public Code $try(String tag) {
    faults.start(tag, position);
    return this;
  }
  @Override
  public Code $end(String tag) {
    faults.end(tag, position);
    return this;
  }
  @Override
  public Code $catch(String tag, Constable...exception) {
    for (var e:exception) faults.handler(tag, position, cp(e));
    return this;
  }


}
