package bc.parser;

import bc.parser.Bytecode.Span;
import static bc.ClassFile.*;
import static bc.JVMS.*;

class Constant {


  /**
   * returns index of 'constant_pool' section
   * where: i[0] is start of 'constant_pool'
   *        i[n] is end of 'constant_pool' item #n
   */
  static int[] index(Span a, int n) {
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

  static CpInfo info(Span a, short i) {
    var tag = a.u1();
    return switch(tag) {
      case CONSTANT_Utf8 -> utf8_info(a,i);
      case CONSTANT_Integer -> integer_info(a,i);
      case CONSTANT_Float -> float_info(a,i);
      case CONSTANT_Long -> long_info(a,i);
      case CONSTANT_Double -> double_info(a,i);
      case CONSTANT_Class -> class_info(a,i);
      case CONSTANT_String -> string_info(a,i);
      case CONSTANT_Fieldref -> fieldref_info(a,i);
      case CONSTANT_Methodref -> methodref_info(a,i);
      case CONSTANT_InterfaceMethodref -> interface_methodref_info(a,i);
      case CONSTANT_NameAndType -> name_and_type_info(a,i);
      case CONSTANT_MethodHandle -> methodhandle_info(a,i);
      case CONSTANT_MethodType -> methodtype_info(a,i);
      case CONSTANT_Dynamic -> dynamic_info(a,i);
      case CONSTANT_InvokeDynamic -> invokedynamic_info(a,i);
      case CONSTANT_Module -> module_info(a,i);
      case CONSTANT_Package -> package_info(a,i);
      default -> null;
    };
  }

  /**
   *  CONSTANT_Utf8_info :
   *    u1 tag
   *    u2 length
   *    u1 bytes[length]
   */
  static Utf8Ref utf8_info(Span a, short i) {
    var len = a.u2();
    return new Utf8Ref(CONSTANT_Utf8, i, (short)a.p, len );
  }

  /**
   *  CONSTANT_Integer_info :
   *    u1 tag
   *    u4 bytes
   */
  static IntegerRef integer_info(Span a, short i) {
    return new IntegerRef(CONSTANT_Integer, i, (short)a.p );
  }

  /**
   *  CONSTANT_Float_info :
   *    u1 tag
   *    u4 bytes
   */
  static FloatRef float_info(Span a, short i) {
    return new FloatRef(CONSTANT_Float, i, (short)a.p );
  }

  /**
   *  CONSTANT_Long_info :
   *    u1 tag
   *    u4 high_bytes
   *    u4 low_bytes
   */
  static LongRef long_info(Span a, short i) {
    return new LongRef(CONSTANT_Long, i, (short)a.p );
  }
  /**
   *  CONSTANT_Double_info :
   *    u1 tag
   *    u4 high_bytes
   *    u4 low_bytes
   */
  static DoubleRef double_info(Span a, short i) {
    return new DoubleRef(CONSTANT_Double, i, (short)a.p );
  }

  /**
   *  CONSTANT_Class_info :
   *    u1 tag
   *    u2 name_index
   */
  static ClassRef class_info(Span a, short i) {
    return new ClassRef(CONSTANT_Class, i, a.u2() );
  }

  /**
   *  CONSTANT_String_info :
   *    u1 tag
   *    u2 string_index
   */
  static StringRef string_info(Span a, short i) {
    return new StringRef(CONSTANT_String, i, a.u2() );
  }

  /**
   *  CONSTANT_Fieldref_info :
   *    u1 tag
   *    u2 class_index
   *    u2 name_and_type_index
   */
  static FieldRef fieldref_info(Span a, short i) {
    return new FieldRef(CONSTANT_Fieldref, i, a.u2(), a.u2() );
  }

  /**
   *  CONSTANT_Methodref_info :
   *    u1 tag
   *    u2 class_index
   *    u2 name_and_type_index
   */
  static MethodRef methodref_info(Span a, short i) {
    return new MethodRef(CONSTANT_Methodref, i, a.u2(), a.u2() );
  }

  /**
   *  CONSTANT_InterfaceMethodref_info :
   *    u1 tag
   *    u2 class_index
   *    u2 name_and_type_index
   */
  static InterfaceMethodRef interface_methodref_info(Span a, short i) {
    return new InterfaceMethodRef(CONSTANT_InterfaceMethodref, i, a.u2(), a.u2() );
  }

  /**
   *  CONSTANT_NameAndType_info :
   *    u1 tag
   *    u2 name_index
   *    u2 descriptor_index
   */
  static NameAndTypeRef name_and_type_info(Span a, short i) {
    return new NameAndTypeRef(CONSTANT_NameAndType, i, a.u2(), a.u2() );
  }

  /**
   *  CONSTANT_MethodHandle_info :
   *    u1 tag
   *    u1 reference_kind
   *    u2 reference_index
   */
  static MethodHandleRef methodhandle_info(Span a, short i) {
    return new MethodHandleRef(CONSTANT_MethodHandle, i, a.u1(), a.u2() );
  }

  /**
   *  CONSTANT_MethodType_info :
   *    u1 tag
   *    u2 descriptor_index
   */
  static MethodTypeRef methodtype_info(Span a, short i) {
    return new MethodTypeRef(CONSTANT_MethodType, i, a.u2() );
  }

  /**
   *  CONSTANT_Dynamic_info :
   *    u1 tag
   *    u2 bootstrap_method_attr_index
   *    u2 name_and_type_index
   */
  static DynamicRef dynamic_info(Span a, short i) {
    return new DynamicRef(CONSTANT_Dynamic, i, a.u2(), a.u2() );
  }

  /**
   *  CONSTANT_InvokeDynamic_info :
   *    u1 tag
   *    u2 bootstrap_method_attr_index
   *    u2 name_and_type_index
   */
  static InvokeDynamicRef invokedynamic_info(Span a, short i) {
    return new InvokeDynamicRef(CONSTANT_InvokeDynamic, i, a.u2(), a.u2() );
  }

  /**
   *  CONSTANT_Module_info :
   *    u1 tag
   *    u2 name_index
   */
  static ModuleRef module_info(Span a, short i) {
    return new ModuleRef(CONSTANT_Module, i, a.u2() );
  }

  /**
   *  CONSTANT_Package_info :
   *    u1 tag
   *    u2 name_index
   */
  static PackageRef package_info(Span a, short i) {
    return new PackageRef(CONSTANT_Package, i, a.u2() );
  }

}
