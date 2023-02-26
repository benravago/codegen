package bc.encoder.op;

import java.lang.constant.Constable;

import bc.CompilationUnit.Code;
import static bc.spec.JVMS.*;

public abstract class Encode extends Operation {

  // TODO: handle ConstantPool in Class
  //       also refine api per Constable kind

  abstract short cp(Constable c);

  // TODO: handle LocalVariables, JumpTable in Method/Code

  byte lv(String v) { return -1; }
  short lw(String v) { return -1; }

  short jmp(String j) { return -1; }
  short far(String j) { return -1; }

  // TODO: basic conversions

  @Override public Code ldc(Constable c) { ldc((byte)cp(c)); return this; }
  @Override public Code ldc_w(Constable c) { ldc_w(cp(c)); return this; }
  @Override public Code ldc2_w(Constable c) { ldc2_w(cp(c)); return this; }

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

  @Override public Code iinc(String v, byte d) { iinc((byte)lv(v),d); return this; }

  @Override public Code ifeq(String j) { ifeq(jmp(j)); return this; }
  @Override public Code ifne(String j) { ifne(jmp(j)); return this; }
  @Override public Code iflt(String j) { iflt(jmp(j)); return this; }
  @Override public Code ifge(String j) { ifge(jmp(j)); return this; }
  @Override public Code ifgt(String j) { ifgt(jmp(j)); return this; }
  @Override public Code ifle(String j) { ifle(jmp(j)); return this; }

  @Override public Code if_icmpeq(String j) { if_icmpeq(jmp(j)); return this; }
  @Override public Code if_icmpne(String j) { if_icmpne(jmp(j)); return this; }
  @Override public Code if_icmplt(String j) { if_icmplt(jmp(j)); return this; }
  @Override public Code if_icmpge(String j) { if_icmpge(jmp(j)); return this; }
  @Override public Code if_icmpgt(String j) { if_icmpgt(jmp(j)); return this; }
  @Override public Code if_icmple(String j) { if_icmple(jmp(j)); return this; }
  @Override public Code if_acmpeq(String j) { if_acmpeq(jmp(j)); return this; }
  @Override public Code if_acmpne(String j) { if_acmpne(jmp(j)); return this; }

  @Override public Code goto_v(String j) { goto_v(jmp(j)); return this; }
  @Override public Code jsr(String j) { jsr(jmp(j)); return this; }

  @Override public Code ret(String v) { ret(lv(v)); return this; }

  @Override
  public Code tableswitch(String... j) {
    // TODO Auto-generated method stub
    return this;
  }

  @Override
  public Code lookupswitch(Object... p) {
    // TODO Auto-generated method stub
    return this;
  }

  @Override public Code getstatic(Constable c) { getstatic(cp(c)); return this; }
  @Override public Code putstatic(Constable c) { putstatic(cp(c)); return this; }
  @Override public Code getfield(Constable c) { getfield(cp(c)); return this; }
  @Override public Code putfield(Constable c) { putfield(cp(c)); return this; }
  @Override public Code invokevirtual(Constable c) { invokevirtual(cp(c)); return this; }
  @Override public Code invokespecial(Constable c) { invokespecial(cp(c)); return this; }
  @Override public Code invokestatic(Constable c) { invokestatic(cp(c)); return this; }
  @Override public Code invokeinterface(Constable c, byte d) { invokeinterface(cp(c),d); return this; }
  @Override public Code invokedynamic(Constable c) { invokedynamic(cp(c)); return this; }
  @Override public Code new_v(Constable c) { new_v(cp(c)); return this; }
  @Override public Code newarray(AT t) { newarray(t.bits); return this; }
  @Override public Code anewarray(Constable c) { anewarray(cp(c));  return this; }
  @Override public Code checkcast(Constable c) { checkcast(cp(c));  return this; }
  @Override public Code instance_of(Constable c) { instance_of(cp(c));  return this; }

  @Override public Code wide(WIDE w, String v, short... c) { wide(w.bits,lv(v),c); return this; }

  @Override public Code multianewarray(Constable c, byte d) { multianewarray(cp(c),d); return this; }

  @Override public Code ifnull(String j) { ifnull(jmp(j)); return this; }
  @Override public Code ifnonnull(String j) { ifnonnull(jmp(j)); return this; }

  @Override public Code goto_w(String j) { goto_w(far(j)); return this; }
  @Override public Code jsr_w(String j) { jsr_w(far(j)); return this; }

  // TODO: local Code references

  @Override
  public Code $(String tag) {
    // TODO Auto-generated method stub
    return this;
  }

  @Override
  public Code $try(String tag) {
    // TODO Auto-generated method stub
    return this;
  }

  @Override
  public Code $end(String tag) {
    // TODO Auto-generated method stub
    return this;
  }

  @Override
  public Code $catch(String tag, Constable...exception) {
    // TODO Auto-generated method stub
    return this;
  }

  @Override
  public Code $var(Constable def) {
    // TODO Auto-generated method stub
    return this;
  }

}
