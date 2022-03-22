package bc.parser;

import bc.parser.Bytecode.Span;
import static bc.ClassFile.CP;
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

  /**
   *  cp_info :
   *    u1 tag
   *    u1 info[]
   */
  static CP.Info info(Span a, short i) {
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
   *    u2 length
   *    u1 bytes[length]
   */
  static CP.Utf8 utf8_info(Span a, short i) {
    var len = a.u2();
    return new CP.Utf8(CONSTANT_Utf8, i, (short)a.p, len );
  }

  /**
   *  CONSTANT_Integer_info :
   *    u4 bytes
   */
  static CP.Integer integer_info(Span a, short i) {
    return new CP.Integer(CONSTANT_Integer, i, (short)a.p );
  }

  /**
   *  CONSTANT_Float_info :
   *    u4 bytes
   */
  static CP.Float float_info(Span a, short i) {
    return new CP.Float(CONSTANT_Float, i, (short)a.p );
  }

  /**
   *  CONSTANT_Long_info :
   *    u4 high_bytes
   *    u4 low_bytes
   */
  static CP.Long long_info(Span a, short i) {
    return new CP.Long(CONSTANT_Long, i, (short)a.p );
  }
  /**
   *  CONSTANT_Double_info :
   *    u4 high_bytes
   *    u4 low_bytes
   */
  static CP.Double double_info(Span a, short i) {
    return new CP.Double(CONSTANT_Double, i, (short)a.p );
  }

  /**
   *  CONSTANT_Class_info :
   *    u2 name_index
   */
  static CP.Class class_info(Span a, short i) {
    return new CP.Class(CONSTANT_Class, i, a.u2() );
  }

  /**
   *  CONSTANT_String_info :
   *    u2 string_index
   */
  static CP.String string_info(Span a, short i) {
    return new CP.String(CONSTANT_String, i, a.u2() );
  }

  /**
   *  CONSTANT_Fieldref_info :
   *    u2 class_index
   *    u2 name_and_type_index
   */
  static CP.Field fieldref_info(Span a, short i) {
    return new CP.Field(CONSTANT_Fieldref, i, a.u2(), a.u2() );
  }

  /**
   *  CONSTANT_Methodref_info :
   *    u2 class_index
   *    u2 name_and_type_index
   */
  static CP.Method methodref_info(Span a, short i) {
    return new CP.Method(CONSTANT_Methodref, i, a.u2(), a.u2() );
  }

  /**
   *  CONSTANT_InterfaceMethodref_info :
   *    u2 class_index
   *    u2 name_and_type_index
   */
  static CP.InterfaceMethod interface_methodref_info(Span a, short i) {
    return new CP.InterfaceMethod(CONSTANT_InterfaceMethodref, i, a.u2(), a.u2() );
  }

  /**
   *  CONSTANT_NameAndType_info :
   *    u2 name_index
   *    u2 descriptor_index
   */
  static CP.NameAndType name_and_type_info(Span a, short i) {
    return new CP.NameAndType(CONSTANT_NameAndType, i, a.u2(), a.u2() );
  }

  /**
   *  CONSTANT_MethodHandle_info :
   *    u1 reference_kind
   *    u2 reference_index
   */
  static CP.MethodHandle methodhandle_info(Span a, short i) {
    return new CP.MethodHandle(CONSTANT_MethodHandle, i, a.u1(), a.u2() );
  }

  /**
   *  CONSTANT_MethodType_info :
   *    u2 descriptor_index
   */
  static CP.MethodType methodtype_info(Span a, short i) {
    return new CP.MethodType(CONSTANT_MethodType, i, a.u2() );
  }

  /**
   *  CONSTANT_Dynamic_info :
   *    u2 bootstrap_method_attr_index
   *    u2 name_and_type_index
   */
  static CP.Dynamic dynamic_info(Span a, short i) {
    return new CP.Dynamic(CONSTANT_Dynamic, i, a.u2(), a.u2() );
  }

  /**
   *  CONSTANT_InvokeDynamic_info :
   *    u2 bootstrap_method_attr_index
   *    u2 name_and_type_index
   */
  static CP.InvokeDynamic invokedynamic_info(Span a, short i) {
    return new CP.InvokeDynamic(CONSTANT_InvokeDynamic, i, a.u2(), a.u2() );
  }

  /**
   *  CONSTANT_Module_info :
   *    u2 name_index
   */
  static CP.Module module_info(Span a, short i) {
    return new CP.Module(CONSTANT_Module, i, a.u2() );
  }

  /**
   *  CONSTANT_Package_info :
   *    u2 name_index
   */
  static CP.Package package_info(Span a, short i) {
    return new CP.Package(CONSTANT_Package, i, a.u2() );
  }

}
