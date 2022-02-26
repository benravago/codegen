package bc.parser;

import bc.parser.Bytecode.Span;
import static bc.ClassFile.*;
import static bc.JVMS.*;

class Constant {

  final Bytecode bc;
  final Object[] cache;

  Constant(Bytecode a) {
    bc = a;
    cache = new Object[bc.constant_pool.length];
  }

  @SuppressWarnings("unchecked")
  <T> T info(int n) {
    assert n > 0 && n < cache.length;
    if (cache[n] == null) {
      cache[n] = cp_info(bc.span( bc.constant_pool[n-1], bc.constant_pool[n] ));
    }
    return (T)cache[n];
  }

  Object cp_info(Span a) {
    var tag = a.u1();
    return switch(tag) {
      case CONSTANT_Utf8 -> Utf8_info(a);
      case CONSTANT_Integer -> Integer_info(a);
      case CONSTANT_Float -> Float_info(a);
      case CONSTANT_Long -> Long_info(a);
      case CONSTANT_Double -> Double_info(a);
      case CONSTANT_Class -> Class_info(a);
      case CONSTANT_String -> String_info(a);
      case CONSTANT_Fieldref -> Fieldref_info(a);
      case CONSTANT_Methodref -> Methodref_info(a);
      case CONSTANT_InterfaceMethodref -> InterfaceMethodref_info(a);
      case CONSTANT_NameAndType -> NameAndType_info(a);
      case CONSTANT_MethodHandle -> MethodHandle_info(a);
      case CONSTANT_MethodType -> MethodType_info(a);
      case CONSTANT_Dynamic -> Dynamic_info(a);
      case CONSTANT_InvokeDynamic -> InvokeDynamic_info(a);
      case CONSTANT_Module -> Module_info(a);
      case CONSTANT_Package -> Package_info(a);
      default -> null;
    };
  }

  static int[] pool_index(Span a, int n) {
    var z = new int[n];
    z[0] = a.p;
    for (var i = 1; i < n; i++) {
      var tag = a.u1();
      a.p += switch (tag) {
        case CONSTANT_Class -> 2;
        case CONSTANT_Fieldref -> 4;
        case CONSTANT_Methodref -> 4;
        case CONSTANT_InterfaceMethodref -> 4;
        case CONSTANT_String -> 2;
        case CONSTANT_Integer -> 4;
        case CONSTANT_Float -> 4;
        case CONSTANT_Long -> 8;
        case CONSTANT_Double -> 8;
        case CONSTANT_NameAndType -> 4;
        case CONSTANT_Utf8 -> a.u2() + 2;
        case CONSTANT_MethodHandle -> 3;
        case CONSTANT_MethodType -> 2;
        case CONSTANT_Dynamic -> 4;
        case CONSTANT_InvokeDynamic -> 4;
        case CONSTANT_Module -> 2;
        case CONSTANT_Package -> 2;
        default -> throw new IllegalArgumentException("tag "+tag+" at "+a.p);
      };
      z[i] = a.p;
      if (tag == CONSTANT_Long || tag == CONSTANT_Double) {
        z[++i] = a.p; /* jvms 4.5.5 -- In retrospect, making 8-byte constants take two constant pool entries was a poor choice. */
      }
    }
    return z;
  }

  /**
   *  CONSTANT_Utf8_info :
   *   u1 tag
   *   u2 length
   *   u1 bytes[length]
   */
  CharSequence Utf8_info(Span a) {
    var len = a.u2();
    return Utf8.decode(bc.b, a.p, len);
  }

  /**
   *  CONSTANT_Integer_info :
   *    u1 tag
   *    u4 bytes
   */
  Integer Integer_info(Span a) {
    return a.u4();
  }

  /**
   *  CONSTANT_Float_info :
   *    u1 tag
   *    u4 bytes
   */
  Float Float_info(Span a) {
    return Float.intBitsToFloat(a.u4()); // IEEE-754 format
  }

  /**
   *  CONSTANT_Long_info :
   *    u1 tag
   *    u4 high_bytes
   *    u4 low_bytes
   */
  Long Long_info(Span a) {
    return u8(bc.b,a.p);
  }
  /**
   *  CONSTANT_Double_info :
   *    u1 tag
   *    u4 high_bytes
   *    u4 low_bytes
   */
  Double Double_info(Span a) {
    return Double.longBitsToDouble(u8(bc.b,a.p)); // IEEE-754 format
  }

  static long u8(byte[] b, int p) {
    return ( ((b[p++] & 0x0ffL) << 56) | ((b[p++] & 0x0ffL) << 48) | ((b[p++] & 0x0ffL) << 40) | ((b[p++] & 0x0ffL) << 32)
           | ((b[p++] & 0x0ffL) << 24) | ((b[p++] & 0x0ffL) << 16) | ((b[p++] & 0x0ffL) <<  8) | ( b[p++] & 0x0ffL) ) ;
  }

  /**
   *  CONSTANT_Class_info :
   *    u1 tag
   *    u2 name_index
   */
  CharSequence Class_info(Span a) {
    return info(a.u2());
  }

  /**
   *  CONSTANT_String_info :
   *    u1 tag
   *    u2 string_index
   */
  CharSequence String_info(Span a) {
    return info(a.u2());
  }

  /**
   *  CONSTANT_Fieldref_info :
   *    u1 tag
   *    u2 class_index
   *    u2 name_and_type_index
   */
  Variable Fieldref_info(Span a) {
    return new Variable( info(a.u2()), info(a.u2()) );
  }

  /**
   *  CONSTANT_Methodref_info :
   *    u1 tag
   *    u2 class_index
   *    u2 name_and_type_index
   */
  Variable Methodref_info(Span a) {
    return new Variable( info(a.u2()), info(a.u2()) );
  }

  /**
   *  CONSTANT_InterfaceMethodref_info :
   *    u1 tag
   *    u2 class_index
   *    u2 name_and_type_index
   */
  Variable InterfaceMethodref_info(Span a) {
    return new Variable( info(a.u2()), info(a.u2()) );
  }

  /**
   *  CONSTANT_NameAndType_info :
   *    u1 tag
   *    u2 name_index
   *    u2 descriptor_index
   */
  Accessible NameAndType_info(Span a) {
    return new Accessible( info(a.u2()), info(a.u2()) );
  }

  /**
   *  CONSTANT_MethodHandle_info :
   *    u1 tag
   *    u1 reference_kind
   *    u2 reference_index
   */
  Reference MethodHandle_info(Span a) {
    return new Reference( a.u1(), info(a.u2()) );
  }

  /**
   *  CONSTANT_MethodType_info :
   *    u1 tag
   *    u2 descriptor_index
   */
  CharSequence MethodType_info(Span a) {
    return info(a.u2());
  }

  /**
   *  CONSTANT_Dynamic_info :
   *    u1 tag
   *    u2 bootstrap_method_attr_index
   *    u2 name_and_type_index
   */
  Dynamic Dynamic_info(Span a) {
    return new Dynamic( a.u2(), info(a.u2()) );
  }

  /**
   *  CONSTANT_InvokeDynamic_info :
   *    u1 tag
   *    u2 bootstrap_method_attr_index
   *    u2 name_and_type_index
   */
  Dynamic InvokeDynamic_info(Span a) {
    return new Dynamic( a.u2(), info(a.u2()) );
  }

  /**
   *  CONSTANT_Module_info :
   *    u1 tag
   *    u2 name_index
   */
  CharSequence Module_info(Span a) {
    return info(a.u2());
  }

  /**
   *  CONSTANT_Package_info :
   *    u1 tag
   *    u2 name_index
   */
  CharSequence Package_info(Span a) {
    return info(a.u2());
  }

}
