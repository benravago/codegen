package bc.builder;

import static bc.JVMS.*;

import bc.util.Murmur;
import bc.util.Binary;
import bc.util.Bits;

class ConstantPool {

  Bits buf = new Bits();

  int count = 0;
  int[] keys = new int[32],
      values = new int[32];

  int slot = 0;

  private short cp(byte[] b) {
    var key = Murmur.hash(b);
    var i = Binary.search(keys,0,count,key);
    if (i < 0) {
      i = - (i - 1);
      var t = b[0];
      keys = insert(keys,count,i, key );
      values = insert(values,count,i, ((t << 16) | (++slot)) );
      ++count;
      if (t == CONSTANT_Long || t == CONSTANT_Double) ++slot;
      buf.add(b);
    }
    return (short) values[i];
  }

  private short cp(byte tag, short index) {
    byte[] b = {tag, 0,0 };
    u2(b,1,index);
    return cp(b);
  }

  private short cp(byte tag, short index1, short index2) {
    byte[] b = {tag, 0,0, 0,0 };
    u2(b,1,index1);
    u2(b,3,index2);
    return cp(b);
  }

  private static void u2(byte[] b, int p, short v) {
    b[p] = (byte)(v>>>8); b[p+1] = (byte)(v);
  }

  // CONSTANT_Utf8_info { u1 tag(1); u2 length; u1 bytes[length]; }
  short Utf8(String s) {
    var bytes = s.getBytes();
    var n = bytes.length;
    var b = new byte[1+2+n];
    b[0] = CONSTANT_Utf8;
    u2(b,1,(short)n);
    System.arraycopy(bytes,0,b,3,n);
    return cp(b);
  }

  // CONSTANT_Integer_info { u1 tag(3); u4 bytes; }
  short Integer(int value) { return i32(CONSTANT_Integer,value); }

  // CONSTANT_Float_info { u1 tag(4); u4 bytes; }
  short Float(float value) { return i32(CONSTANT_Float, Float.floatToIntBits(value)); }

  private short i32(byte tag, int v) {
    byte[] b = { tag, 0,0,0,0 };
    b[1] = (byte)(v>>>24); b[2] = (byte)(v>>>16); b[3] = (byte)(v>>>8); b[4] = (byte)(v);
    return cp(b);
  }

  // CONSTANT_Long_info { u1 tag(5); u4 high_bytes; u4 low_bytes; }
  short Long(long value) { return i64(CONSTANT_Long, value); }

  // CONSTANT_Double_info { u1 tag(6); u4 high_bytes; u4 low_bytes; }
  short Double(double value) { return i64(CONSTANT_Double, Double.doubleToLongBits(value)); }

  private short i64(byte tag, long v) {
    byte[] b = { tag, 0,0,0,0, 0,0,0,0 };
    b[1] = (byte)(v>>>56); b[2] = (byte)(v>>>48); b[3] = (byte)(v>>>40); b[4] = (byte)(v>>>32);
    b[5] = (byte)(v>>>24); b[6] = (byte)(v>>>16); b[7] = (byte)(v>>> 8); b[8] = (byte)(v);
    return cp(b);
  }

  // CONSTANT_Class_info { u1 tag(7); u2 name_index; }
  short Class(String s) { return cp(CONSTANT_Class, Utf8(s)); }

  // CONSTANT_String_info { u1 tag(8); u2 string_index; }
  short String(String s) { return cp(CONSTANT_String, Utf8(s)); }

  // CONSTANT_Fieldref_info { u1 tag(9); u2 class_index; u2 name_and_type_index; }
  short Field(String c, String n, String d) { return cp(CONSTANT_Fieldref, Utf8(c), NameAndType(n,d)); }

  // CONSTANT_Methodref_info { u1 tag(10); u2 class_index; u2 name_and_type_index; }
  short Method(String c, String n, String d) { return cp(CONSTANT_Methodref, Utf8(c), NameAndType(n,d)); }

  // CONSTANT_InterfaceMethodref_info { u1 tag(11); u2 class_index; u2 name_and_type_index; }
  short InterfaceMethod(String c, String n, String d) { return cp(CONSTANT_InterfaceMethodref, Utf8(c), NameAndType(n,d)); }

  // CONSTANT_NameAndType_info { u1 tag(12); u2 name_index; u2 descriptor_index; }
  short NameAndType(String name, String descriptor) { return cp(CONSTANT_NameAndType, Utf8(name), Utf8(descriptor)); }

  // CONSTANT_MethodHandle_info { u1 tag(15); u1 reference_kind; u2 reference_index; }
  short MethodHandle(byte kind, short ref) {
    byte[] b = {CONSTANT_MethodHandle, kind, 0,0 };
    u2(b,2,ref);
    return cp(b);
  }

  // CONSTANT_MethodType_info { u1 tag(16); u2 descriptor_index; }
  short MethodType(String s) { return cp(CONSTANT_MethodType, Utf8(s)); }

  // CONSTANT_Dynamic_info { u1 tag(17); u2 bootstrap_method_attr_index; u2 name_and_type_index; } 5
  short Dynamic(short index, String n, String d) { return cp(CONSTANT_Dynamic, index, NameAndType(n,d)); }

  // CONSTANT_InvokeDynamic_info { u1 tag(18); u2 bootstrap_method_attr_index; u2 name_and_type_index; }
  short InvokeDynamic(short index, String n, String d) { return cp(CONSTANT_InvokeDynamic, index, NameAndType(n,d)); }

  // CONSTANT_Module_info { u1 tag(19); u2 name_index; }
  short Module(String s) { return cp(CONSTANT_Module, Utf8(s)); }

  // CONSTANT_Package_info { u1 tag(20); u2 name_index; }
  short Package(String s) { return cp(CONSTANT_Package, Utf8(s)); }

  // TODO:

  short Loadable(Object...c) { return -1; }
  short FieldRef(Object...c) { return -1; }
  short MethodRef(Object...c) { return -1; }
  short TypeRef(Object...c) { return -1; }


  private static int[] insert(int[] src, int used, int index, int item) {
    var n = src.length;
    var dest = used < n ? src : new int[n + (n/2)];
    if (src != dest) System.arraycopy(src,0,dest,0,index);
    if (index < n) System.arraycopy(src,index,dest,index+1,n-index);
    dest[index] = item;
    return dest;
  }

}
