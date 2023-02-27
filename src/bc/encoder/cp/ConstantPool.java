package bc.encoder.cp;

import static bc.spec.JVMS.*;
import static bc.spec.UTF.*;

public class ConstantPool extends Pool {

  // CONSTANT_Utf8_info {
  //   u1 tag = 1;
  //   u2 length;
  //   u1 bytes[length];
  // }
  public short Utf8(String s) {
    var b = encode(s,3); // add space for tag & length
    b[0] = CONSTANT_Utf8;
    var n = b.length - 3;
    b[1] = (byte)(n>>>8); b[2] = (byte)(n);
    return (short)put(b,1);
  }

  // CONSTANT_Integer_info {
  //   u1 tag = 3;
  //   u4 bytes;
  // }
  public short Integer(int value) {
    return t32(CONSTANT_Integer, value);
  }

  // CONSTANT_Float_info {
  //   u1 tag = 4;
  //   u4 bytes;
  // }
  public short Float(float value) {
    return t32(CONSTANT_Float, Float.floatToIntBits(value));
  }

  public short t32(byte tag, int v) {
    byte[] b = { tag, 0,0,0,0 };
    b[1] = (byte)(v>>>24); b[2] = (byte)(v>>>16); b[3] = (byte)(v>>>8); b[4] = (byte)(v);
    return (short)put(b,1);
  }

  // CONSTANT_Long_info {
  //   u1 tag = 5;
  //   u4 high_bytes;
  //   u4 low_bytes;
  // }
  public short Long(long value) { return t64(CONSTANT_Long, value); }

  // CONSTANT_Double_info {
  //   u1 tag = 6;
  //   u4 high_bytes;
  //   u4 low_bytes;
  // }
  public short Double(double value) {
    return t64(CONSTANT_Double, Double.doubleToLongBits(value));
  }

  public short t64(byte tag, long v) {
    byte[] b = { tag, 0,0,0,0, 0,0,0,0 };
    b[1] = (byte)(v>>>56); b[2] = (byte)(v>>>48); b[3] = (byte)(v>>>40); b[4] = (byte)(v>>>32);
    b[5] = (byte)(v>>>24); b[6] = (byte)(v>>>16); b[7] = (byte)(v>>> 8); b[8] = (byte)(v);
    // jvms 4.4.5 # In retrospect, making 8-byte constants take two constant pool entries was a poor choice.
    return (short)put(b,2);
  }
  
  //  7 CONSTANT_Class_info { u1 tag; u2 name_index; }
  public short Class(String s) { return t16(CONSTANT_Class, Utf8(s)); }

  //  8 CONSTANT_String_info { u1 tag; u2 string_index; }
  public short String(String s) { return t16(CONSTANT_String, Utf8(s)); }

  public short t16(byte tag, short v) {
    byte[] b = { tag, 0,0 };
    b[1] = (byte)(v>>>8); b[2] = (byte)(v);
    return (short)put(b,1);
  }

  // CONSTANT_Fieldref_info {
  //   u1 tag = 9;
  //   u2 class_index;
  //   u2 name_and_type_index;
  // }
  public short Field(String c, String n, String d) {
    return t1616(CONSTANT_Fieldref, Utf8(c), NameAndType(n,d));
  }

  // CONSTANT_Methodref_info {
  //   u1 tag = 10;
  //   u2 class_index;
  //   u2 name_and_type_index;
  // }
  public short Method(String c, String n, String d) {
    return t1616(CONSTANT_Methodref, Utf8(c), NameAndType(n,d));
  }

  // CONSTANT_InterfaceMethodref_info {
  //   u1 tag = 11;
  //   u2 class_index;
  //   u2 name_and_type_index;
  // }
  public short InterfaceMethod(String c, String n, String d) {
    return t1616(CONSTANT_InterfaceMethodref, Utf8(c), NameAndType(n,d));
  }

  // CONSTANT_NameAndType_info {
  //   u1 tag = 12;
  //   u2 name_index;
  //   u2 descriptor_index;
  // }
  public short NameAndType(String name, String descriptor) {
    return t1616(CONSTANT_NameAndType, Utf8(name), Utf8(descriptor));
  }

  public short t1616(byte tag, short x, short y) {
    byte[] b = { tag, 0,0, 0,0 };
    b[1] = (byte)(x>>>8); b[2] = (byte)(x); b[3] = (byte)(y>>>8); b[4] = (byte)(y);
    return (short)put(b,1);
  }

  // CONSTANT_MethodHandle_info {
  //   u1 tag = 15;
  //   u1 reference_kind;
  //   u2 reference_index;
  // }
  public short MethodHandle(byte kind, short index) {
    byte[] b = {CONSTANT_MethodHandle, kind, 0,0 };
    b[2] = (byte)(index>>>8); b[3] = (byte)(index);
    return (short)put(b,1);
  }

  // CONSTANT_MethodType_info {
  //   u1 tag = 16;
  //   u2 descriptor_index;
  // }
  public short MethodType(String s) {
    return t16(CONSTANT_MethodType, Utf8(s));
  }

  // CONSTANT_Dynamic_info {
  //   u1 tag = 17;
  //   u2 bootstrap_method_attr_index;
  //   u2 name_and_type_index;
  // }
  public short Dynamic(short index, String n, String d) {
    return t1616(CONSTANT_Dynamic, index, NameAndType(n,d));
  }

  // CONSTANT_InvokeDynamic_info {
  //   u1 tag = 18;
  //   u2 bootstrap_method_attr_index;
  //   u2 name_and_type_index;
  // }
  public short InvokeDynamic(short index, String n, String d) {
    return t1616(CONSTANT_InvokeDynamic, index, NameAndType(n,d));
  }

  // CONSTANT_Module_info {
  //   u1 tag = 19;
  //   u2 name_index;
  // }
  public short Module(String s) {
    return t16(CONSTANT_Module, Utf8(s));
  }

  // CONSTANT_Package_info {
  //   u1 tag = 20;
  //   u2 name_index;
  // }
  public short Package(String s) {
    return t16(CONSTANT_Package, Utf8(s)); 
  }

}
