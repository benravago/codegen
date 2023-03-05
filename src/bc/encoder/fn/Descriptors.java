package bc.encoder.fn;

import java.lang.constant.ClassDesc;
import java.lang.constant.Constable;
import java.lang.constant.ConstantDesc;
import java.lang.constant.MethodTypeDesc;
import java.lang.reflect.Type;
import java.util.Optional;

public interface Descriptors {

  static ClassDesc classDesc(Constable o) {
    return switch(o) {
      case String s -> s.charAt(0) == 'L' && s.endsWith(";") ? ClassDesc.ofDescriptor(s) : ClassDesc.of(s);
      case Class<?> c -> ClassDesc.of(c.getName());
      default -> ClassDesc.of(o.getClass().getName());
    };
  }

  static Constable loadable(Constable...o) {
    return null; // 32 bit frame slot
  }
  
  static Constable loadable2(Constable...o) {
    return null;  // 64 bit frame slot
  }
  
  static Constable field(Constable...o) {
    return null;
  }

  static Constable method(Constable...o) {
    return null;
  }
  
  static Constable invocable(Constable...o) {
    return null; // method or interfaceMethod
  }

  static Constable interfaceMethod(Constable...o) {
    return null;
  }
  
  static Constable callsite(Constable...o) {
    return null;
  }
  
  static Constable type(Constable o) {
    return switch (o) {
      case String s -> o;
      case Class<?> c -> c;
      default -> { assert false : "not a Type"; yield null; }
    };
  }
  
  static Constable component(Constable o) {
    return switch (o) {
      case String s -> o;
      case Class<?> c -> { assert !c.isArray() : "not a component Type"; yield c; }
      default -> { assert false : "not a Type"; yield null; }
    };
  }

  static Constable exception(Constable o) {
    return switch (o) {
      case String s -> o;
      case Class<?> c -> { assert c.isAssignableFrom(Throwable.class) : o.toString()+" is not a Throwable"; yield c; }
      default -> { assert false : "not an Exception"; yield null; }
    };
  }

  record NameAndType(String name, ClassDesc type) implements Constable {
    public Optional<? extends ConstantDesc> describeConstable() { return Optional.of(type); }
  }
  
  static Constable named(String name, Type type) {
    return new NameAndType(name,ClassDesc.of(type.getTypeName()));
  }

}
