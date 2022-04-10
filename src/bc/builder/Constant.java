package bc.builder;

import static bc.JVMS.*;

class Constant {

  /**
   *  cp_info :
   *    u1 tag
   *    u1 info[]
   */
  interface cp_info { byte tag(); }

  /**
   *  CONSTANT_Utf8_info :
   *    u1 tag = 1
   *    u2 length
   *    u1 bytes[length]
   */
  record ci_utf8(byte tag, String value) implements cp_info {}
  ci_utf8 Utf8(String value) { return new ci_utf8(CONSTANT_Utf8,value); }

  /**
   *  CONSTANT_Integer_info :
   *    u1 tag = 3
   *    u4 bytes
   */
  record ci_integer(byte tag, int value) implements cp_info {}
  ci_integer Integer(int value) { return new ci_integer(CONSTANT_Integer,value); }

  /**
   *  CONSTANT_Float_info :
   *    u1 tag = 4
   *    u4 bytes
   */
  record ci_float(byte tag, float value) implements cp_info {}
  ci_float Float(float value) { return new ci_float(CONSTANT_Float,value); }

  /**
   *  CONSTANT_Long_info :
   *    u1 tag = 5
   *    u4 high_bytes
   *    u4 low_bytes
   */
  record ci_long(byte tag, long value) implements cp_info {}
  ci_long Long(long value) { return new ci_long(CONSTANT_Long,value); }

  /**
   *  CONSTANT_Double_info :
   *    u1 tag = 6
   *    u4 high_bytes
   *    u4 low_bytes
   */
  record ci_double(byte tag, double value) implements cp_info {}
  ci_double Double(double value) { return new ci_double(CONSTANT_Double,value); }

  /**
   *  CONSTANT_String_info :
   *    u1 tag = 8
   *    u2 string_index ->  CONSTANT_Utf8_info
   */
  record ci_string(byte tag, ci_utf8 string) implements cp_info {}
  ci_string String(Object a) { return new ci_string(CONSTANT_String, Utf8(String.valueOf(a))); }

  /**
   *  CONSTANT_Class_info :
   *    u1 tag = 7
   *    u2 name_index -> CONSTANT_Utf8_info
   */
  record ci_class(byte tag, ci_utf8 name) implements cp_info {}
  ci_class Class(Object a) { return new ci_class(CONSTANT_Class, Utf8(class_d(a))); }

  /**
   *  CONSTANT_NameAndType_info :
   *    u1 tag = 12
   *    u2 name_index -> CONSTANT_Utf8_info
   *    u2 descriptor_index -> CONSTANT_Utf8_info
   */
  record ci_name_and_type(byte tag, ci_utf8 name, ci_utf8 descriptor) implements cp_info {}
  ci_name_and_type NameAndType(Object a, Object b) { return new ci_name_and_type(CONSTANT_NameAndType, Utf8(class_d(a)), descriptor(b)); }

  /**
   *  CONSTANT_Fieldref_info :
   *    u1 tag = 9
   *    u2 class_index -> CONSTANT_Class_info
   *    u2 name_and_type_index -> CONSTANT_NameAndType_info
   */
  record ci_field(byte tag, ci_class owner, ci_name_and_type name_and_type) implements cp_info, ref_info {}
  ci_field Field(Object a, Object...b) { return new ci_field(CONSTANT_Fieldref, Class(a), field(b)); }

  /**
   *  CONSTANT_Methodref_info :
   *    u1 tag = 10
   *    u2 class_index -> CONSTANT_Class_info
   *    u2 name_and_type_index -> CONSTANT_NameAndType_info
   */
  record ci_method(byte tag, ci_class owner, ci_name_and_type name_and_type) implements cp_info, ref_info {}
  ci_method Method(Object a, Object...b) { return new ci_method(CONSTANT_Methodref, Class(a), method(b)); }

  /**
   *  CONSTANT_InterfaceMethodref_info :
   *    u1 tag = 11
   *    u2 class_index -> CONSTANT_Class_info
   *    u2 name_and_type_index -> CONSTANT_NameAndType_info
   */
  record ci_interface_method(byte tag, ci_class owner, ci_name_and_type name_and_type) implements cp_info, ref_info {}
  ci_interface_method InterfaceMethod(Object a, Object...b) { return new ci_interface_method(CONSTANT_InterfaceMethodref, Class(a), method(b)); }

  /**
   *    #  reference_kind        reference_index
   *   --  --------------------  -----------------------
   *    1  REF_getField          CONSTANT_Fieldref_info
   *    2  REF_getStatic         CONSTANT_Fieldref_info
   *    3  REF_putField          CONSTANT_Fieldref_info
   *    4  REF_putStatic         CONSTANT_Fieldref_info
   *    5  REF_invokeVirtual     CONSTANT_Methodref_info
   *    6  REF_invokeStatic      CONSTANT_Methodref_info | CONSTANT_InterfaceMethodref_info
   *    7  REF_invokeSpecial     CONSTANT_Methodref_info | CONSTANT_InterfaceMethodref_info
   *    8  REF_newInvokeSpecial  CONSTANT_Methodref_info
   *    9  REF_invokeInterface   CONSTANT_InterfaceMethodref_info
   */
  interface ref_info { ci_class owner(); ci_name_and_type name_and_type(); }

  /**
   *  CONSTANT_MethodHandle_info :
   *    u1 tag = 15
   *    u1 reference_kind = REF_*
   *    u2 reference_index -> CONSTANT_*ref_info
   */
  record ci_method_handle(byte tag, byte kind, ref_info reference) implements cp_info {}
  ci_method_handle MethodHandle(byte a, Object b) { return new ci_method_handle(CONSTANT_MethodHandle, a, reference(b)); }

  /**
   *  CONSTANT_MethodType_info :
   *    u1 tag = 16
   *    u2 descriptor_index -> CONSTANT_Utf8_info
   */
  record ci_method_type(byte tag, ci_utf8 descriptor) implements cp_info {}
  ci_method_type MethodType(Object a) { return new ci_method_type(CONSTANT_MethodType, descriptor(a)); }

  /**
   *  CONSTANT_Dynamic_info :
   *    u1 tag = 17
   *    u2 bootstrap_method_attr_index = bootstrap_methods[index]
   *    u2 name_and_type_index -> CONSTANT_NameAndType_info
   */
  record ci_dynamic(byte tag, bootstrap method, ci_name_and_type name_and_type) implements cp_info {}
  ci_dynamic Dynamic(Object a, Object b, Object c) { return new ci_dynamic(CONSTANT_Dynamic, bootstrap(a), NameAndType(b,c)); }

  /**
   *  CONSTANT_InvokeDynamic_info :
   *    u1 tag = 18
   *    u2 bootstrap_method_attr_index;
   *    u2 name_and_type_index -> CONSTANT_NameAndType_info
   */
  record ci_invoke_dynamic(byte tag, bootstrap method, ci_name_and_type name_and_type) implements cp_info {}
  ci_invoke_dynamic InvokeDynamic(Object a, Object b, Object c) { return new ci_invoke_dynamic(CONSTANT_InvokeDynamic, bootstrap(a), NameAndType(b,c)); }

  /**
   *  CONSTANT_Module_info :
   *    u1 tag = 19
   *    u2 name_index -> CONSTANT_Utf8_info
   */
  record ci_module(byte tag, ci_utf8 name) implements cp_info {}
  ci_module Module(Object a) { return new ci_module(CONSTANT_Module, Utf8(module_d(a))); }

  /**
   *  CONSTANT_Package_info :
   *    u1 tag = 20
   *    u2 name_index -> CONSTANT_Utf8_info
   */
  record ci_package(byte tag, ci_utf8 name) implements cp_info {}
  ci_package Package(Object a) { return new ci_package(CONSTANT_Package, Utf8(package_d(a))); }


  // TODO:

  // 4.2.1. Binary Class and Interface Names

  static String class_d(Object x) {
    return (x instanceof String s) ? s
         : (x instanceof Class c) ? c.getName().replace('.','/') // descriptorString().substring(1,-1)
         : null;
  }

  // 4.2.3. Module and Package Names

  static String module_d(Object x) { return null; } // 4.4.11
  static String package_d(Object x) { return null; } // 4.4.12


  // 4.2.2. Unqualified Names
  // Names of methods, fields, local variables, and formal parameters are stored as unqualified names.

  ci_utf8 descriptor(Object a) { return null; } // 4.3. Descriptors

  ci_name_and_type field(Object...a) { return null; } // 4.3.2. Field Descriptors
  ci_name_and_type method(Object...a) { return null; } // 4.3.3. Method Descriptors

  ref_info reference(Object a) { return null; }

  //  BootstrapMethods_attribute {
  //    u2 attribute_name_index -> CONSTANT_Utf8_info = "BootstrapMethods"
  //    u4 attribute_length
  //    u2 num_bootstrap_methods
  //    { u2 bootstrap_method_ref -> CONSTANT_MethodHandle_info
  //      u2 num_bootstrap_arguments;
  //      u2 bootstrap_arguments[num_bootstrap_arguments] -> constant_pool[item]
  //    } bootstrap_methods[num_bootstrap_methods];
  //  }

  record bootstrap(ci_method_handle handle, cp_info...arguments) {}

  bootstrap bootstrap(Object arg) { return null; }

}