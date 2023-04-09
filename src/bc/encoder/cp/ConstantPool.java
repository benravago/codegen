package bc.encoder.cp;

import java.lang.constant.ClassDesc;
import java.lang.constant.Constable;
import java.lang.constant.ConstantDesc;
// java.lang.constant.DirectMethodHandleDesc;
import java.lang.constant.DynamicConstantDesc;
import java.lang.constant.MethodHandleDesc;
import java.lang.constant.MethodTypeDesc;

import static bc.encoder.fn.Descriptors.*;
import static bc.spec.JVMS.*;

public class ConstantPool extends ConstantInfo {

  public short info(Constable c) {
    return switch(c) {
      // self as nominal descriptor
      case String s -> String(s);
      case Integer i -> Integer(i);
      case Long l -> Long(l);
      case Float f -> Float(f);
      case Double d -> Double(d);
      // MemberRef to Field/Method/InterfaceMethod
      case MemberRef m -> info(m);
      // ClassRef handled by default
      default -> info(c.describeConstable().get());
    };
  }

  short info(MemberRef m) {
    var r = m.member();
    var n = r.name();
    var d = r.type().toString();
    var c = m.owner().descriptorString();
    return switch(m.tag()) {
      case CONSTANT_Fieldref -> Field(c,n,d);
      case CONSTANT_Methodref -> Method(c,n,d);
      case CONSTANT_InterfaceMethodref -> InterfaceMethod(c,n,d);
      default -> throw new IllegalArgumentException(m.toString());
    };
  }

  short info(ConstantDesc c) {
    return switch(c) {
      case ClassDesc t -> Class(t.descriptorString());
      case MethodTypeDesc m         -> 0; // TODO:
      case MethodHandleDesc h       -> 0; // TODO:
      // DirectMethodHandleDesc
      case DynamicConstantDesc<?> d -> 0; // TODO:
      default -> throw new IllegalArgumentException(c.toString());
    };
  }

}
/*
  Pool
   ^
   |
  Constant
   ^
   |
  ConstantPool  <- Descriptors; how to encode
   ^
   |
  callers =  ConstantPool cp;
             ...
             Code putfield(Constable c) { putfield(cp.field(c)); return this; }

  cp(callsite(c))
  cp(component(c))
  cp(exception(e))
  cp(field(c))
  cp(interfaceMethod(c))
  cp(invocable(c))
  cp(loadable2(c))
  cp(loadable(c))
  cp(method(c))
  cp(type(c))
  cp(type(c))


  Constable {
    Optional<? extends ConstantDesc>  describeConstable()
  }




  Class<?>           Constable           ConstantDesc

    String            String               String
    Integer           Integer              Integer
    Long              Long                 Long
    Float             Float                Float
    Double            Double               Double

    Class             Class                ClassDesc
    MethodType        MethodType           MethodTypeDesc
    MethodHandle      MethodHandle         MethodHandleDesc
                                           DirectMethodHandleDesc

    Enum              Enum                 DynamicConstantDesc
    VarHandle         VarHandle            DynamicConstantDesc


*/
