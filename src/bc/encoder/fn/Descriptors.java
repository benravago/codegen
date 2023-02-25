package bc.encoder.fn;

import java.lang.constant.ClassDesc;
import java.lang.constant.Constable;
import java.lang.constant.MethodTypeDesc;

public interface Descriptors {

  static ClassDesc classDesc(Object o) {
    return switch(o) {
      case String s -> s.charAt(0) == 'L' && s.endsWith(";") ? ClassDesc.ofDescriptor(s) : ClassDesc.of(s);
      case Class<?> c -> ClassDesc.of(c.getName());
      default -> ClassDesc.of(o.getClass().getName());
    };
  }

  static Constable methodDesc(Object...o) {
    return null;
  }

  static Constable fieldDesc(Object...o) {
    return null;
  }

}
