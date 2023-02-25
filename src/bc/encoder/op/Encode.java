package bc.encoder.op;

import java.lang.constant.Constable;
import bc.CompilationUnit.Code;

public abstract class Encode extends Operation {

  @Override
  public Code ldc(Constable c) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code ldc_w(Constable c) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code ldc2_w(Constable c) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code iload(String v) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code lload(String v) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code fload(String v) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code dload(String v) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code aload(String v) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code istore(String v) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code lstore(String v) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code fstore(String v) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code dstore(String v) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code astore(String v) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code ifeq(String j) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code ifne(String j) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code iflt(String j) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code ifge(String j) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code ifgt(String j) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code ifle(String j) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code if_icmpeq(String j) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code if_icmpne(String j) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code if_icmplt(String j) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code if_icmpge(String j) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code if_icmpgt(String j) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code if_icmple(String j) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code if_acmpeq(String j) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code if_acmpne(String j) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code goto_v(String j) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code jsr(String j) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code ret(String v) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code tableswitch(String... j) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code lookupswitch(Object... p) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code getstatic(Constable c) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code putstatic(Constable c) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code getfield(Constable c) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code putfield(Constable c) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code invokevirtual(Constable c) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code invokespecial(Constable c) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code invokestatic(Constable c) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code invokeinterface(Constable c, byte d) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code invokedynamic(Constable c) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code new_v(Constable c) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code anewarray(Constable c) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code checkcast(Constable c) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code instance_of(Constable c) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code wide(byte w, String v, int... c) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code multianewarray(Constable c, byte d) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code ifnull(String j) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code ifnonnull(String j) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code goto_w(String j) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code jsr_w(String j) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code $(String tag) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code $try(String tag) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public Code $catch(String tag, Constable...exception) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code $end(String tag) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code $var(Constable def) {
    // TODO Auto-generated method stub
    return null;
  }

}
