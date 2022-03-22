package bc.printer;

import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import bc.ClassFile;
import bc.ClassFile.Module;
import bc.ClassFile.Record;
import bc.ClassFile.Deprecated;
import static bc.ClassFile.*;
import static bc.JVMS.*;

public class Print { // javap

  public static void main(String...args) throws Exception {
    if (args.length < 1) {
      System.out.println("usage: Print <class.file> ...");
    } else {
      var p = new Print(System.out);
      for (var file:args) {
        var f = Paths.get(file);
        System.out.println("Classfile "+f.normalize().toAbsolutePath());
        p.print(Files.readAllBytes(f));
      }
    }
  }

  public Print(PrintStream out) {
    this.out = out;
  }

  PrintStream out;
  ClassFile cf;

  void f(String format, Object... args) { out.format(format,args); }

  /**
   *  ClassFile : @ 4.1
   *    u4 magic
   *    u2 minor_version
   *    u2 major_version
   *    u2 constant_pool_count
   *    cp_info constant_pool[constant_pool_count-1]
   *    u2 access_flags
   *    u2 this_class
   *    u2 super_class
   *    u2 interfaces_count
   *    u2 interfaces[interfaces_count]
   *    u2 fields_count
   *    field_info fields[fields_count]
   *    u2 methods_count
   *    method_info methods[methods_count]
   *    u2 attributes_count
   *    attribute_info attributes[attributes_count]
   */
  public void print(byte[] b) {
    cf = ClassFile.parse(b);

    f("+ magic %08x\n", cf.magic());
    f("+ minor_version %04x\n", cf.minor());
    f("+ major_version %04x\n", cf.major());
    f("+ constant_pool_count %d\n", cf.constantCount());
    constants();
    f("+ access_flags %04x\n", cf.flags());
    f("+ this_class %s\n", cf.type());
    f("+ super_class %s\n", cf.superType());
    f("+ interfaces_count %d\n", cf.interfacesCount());
    interfaces();
    f("+ fields_count %d\n", cf.fieldsCount());
    //fields();
    f("+ methods_count %d\n", cf.methodsCount());
    methods();
    f("+ attributes_count %d\n", cf.attributesCount());
    attributes();
  }

  /**
   *  cp_info : @ 4.4
   *    u1 tag
   *    u1 info[]
   */
  void constants() {
    for (var c:cf.constantPool()) ed(c);
  }

  void ed(CP.Info c) {
    switch (c.tag()) {
      case CONSTANT_Utf8 -> ed((CP.Utf8)c);
      case CONSTANT_Integer -> ed((CP.Integer)c);
      case CONSTANT_Float -> ed((CP.Float)c);
      case CONSTANT_Long -> ed((CP.Long)c);
      case CONSTANT_Double -> ed((CP.Double)c);
      case CONSTANT_Class -> ed((CP.Class)c);
      case CONSTANT_String -> ed((CP.String)c);
      case CONSTANT_Fieldref -> ed((CP.Field)c);
      case CONSTANT_Methodref -> ed((CP.Method)c);
      case CONSTANT_InterfaceMethodref -> ed((CP.InterfaceMethod)c);
      case CONSTANT_NameAndType -> ed((CP.NameAndType)c);
      case CONSTANT_MethodHandle -> ed((CP.MethodHandle)c);
      case CONSTANT_MethodType -> ed((CP.MethodType)c);
      case CONSTANT_Dynamic -> ed((CP.Dynamic)c);
      case CONSTANT_InvokeDynamic -> ed((CP.InvokeDynamic)c);
      case CONSTANT_Module -> ed((CP.Module)c);
      case CONSTANT_Package -> ed((CP.Package)c);

      default -> f("--> %s\n", c);
    }
  }

  /**
   *  CONSTANT_Class_info : @ 4.4.1
   *    u2 name_index
   */
  void ed(CP.Class c) {
    f(" %s\n", c);
  }

  /**
   *  CONSTANT_Fieldref_info : @ 4.4.2
   *    u2 class_index
   *    u2 name_and_type_index
   */
  void ed(CP.Field c) {
    f(" %s\n", c);
  }

  /**
   *  CONSTANT_Methodref_info : @ 4.4.2
   *    u2 class_index
   *    u2 name_and_type_index
   */
  void ed(CP.Method c) {
    f(" %s\n", c);
  }

  /**
   *  CONSTANT_InterfaceMethodref_info:  # 4.4.2
   *    u2 class_index
   *    u2 name_and_type_index
   */
  void ed(CP.InterfaceMethod c) {
     f(" %s\n", c);
  }

  /**
   *  CONSTANT_String_info : @ 4.4.3
   *    u2 string_index
   */
  void ed(CP.String c) {
    f(" %s\n", c);
  }

  /**
   *  CONSTANT_Integer_info : @ 4.4.4
   *    u4 bytes
   */
  void ed(CP.Integer i) {
    f(" %s\n  #%d %d\n", i, i.index(), cf.int32(i.offset()) );
  }

  /**
   *  CONSTANT_Float_info : @ 4.4.4
   *    u1 tag
   *    u4 bytes
   */
  void ed(CP.Float f) {
    f(" %s\n  #%d %d\n", f, f.index(), Float.intBitsToFloat( cf.int32(f.offset()) ) ); // IEEE-754 format
  }

  /**
   *  CONSTANT_Long_info : 4.4.5
   *    u4 high_bytes
   *    u4 low_bytes
   */
  void ed(CP.Long l) {
    f(" %s\n  #%d %d\n", l, l.index(), cf.int64(l.offset()) );
  }

  /**
   *  CONSTANT_Double_info : @ 4.4.5
   *    u4 high_bytes
   *    u4 low_bytes
   */
  void ed(CP.Double d) {
    f(" %s\n  #%d %d\n", d, d.index(), Double.longBitsToDouble( cf.int64(d.offset()) ) ); // IEEE-754 format
  }

  /**
   *  CONSTANT_NameAndType_info : @ 4.4.6
   *    u2 name_index
   *    u2 descriptor_index
   */
  void ed(CP.NameAndType c) {
    f(" %s\n", c);
  }

  /**
   *  CONSTANT_Utf8_info : @ 4.4.7
   *   u2 length
   *   u1 bytes[length]
   */
  void ed(CP.Utf8 u) {
    f(" %s\n  #%d `%s`\n", u, u.index(), cf.chars(u.offset(),u.length()) );
  }

  /**
   *  CONSTANT_MethodHandle_info : @ 4.4.8
   *    u1 reference_kind
   *    u2 reference_index
   */
  void ed(CP.MethodHandle c) {
    f(" %s\n", c);
  }

  /**
   *  CONSTANT_MethodType_info : @ 4.4.9
   *    u2 descriptor_index
   */
  void ed(CP.MethodType c) {
    f(" %s\n", c);
  }

  /**
   *  CONSTANT_Dynamic_info : @ 4.4.10
   *    u2 bootstrap_method_attr_index
   *    u2 name_and_type_index;
   */
  void ed(CP.Dynamic c) {
    f(" %s\n", c);
  }

  /**
   *  CONSTANT_InvokeDynamic_info : @ 4.4.10
   *    u2 bootstrap_method_attr_index
   *    u2 name_and_type_index
   */
  void ed(CP.InvokeDynamic c) {
    f(" %s\n", c);
  }

  /**
   *  CONSTANT_Module_info : @ 4.4.11
   *    u2 name_index
   */
  void ed(CP.Module c) {
    f(" %s\n", c);
  }

  /**
   *  CONSTANT_Package_info : @ 4.4.12
   *    u2 name_index
   */
  void ed(CP.Package c) {
    f(" %s\n", c);
  }

  void interfaces() {
    for (var i:cf.interfaces()) f(" %s\n", i);
  }

  /**
   *  field_info : @ 4.5
   *    u2 access_flags
   *    u2 name_index
   *    u2 descriptor_index
   *    u2 attributes_count
   *    attribute_info attributes[attributes_count]
   */
  void fields() {
    for (var d:cf.fields()) {
      f(" %s\n", d);
      attributes(d.attributes()," ");
    }
  }


  /**
   *  method_info : @ 4.6
   *    u2 access_flags
   *    u2 name_index
   *    u2 descriptor_index
   *    u2 attributes_count
   *    attribute_info attributes[attributes_count]
   */
  void methods() {
    for (var d:cf.methods()) {
      f(" %s\n", d);
      attributes(d.attributes()," ");
    }
  }

  void attributes() {
    attributes(cf.attributes(),"");
  }

  /**
   * attribute_info : @ 4.7
   *   u2 attribute_name_index
   *   u4 attribute_length
   *   u1 info[attribute_length]
   */
  void attributes(Iterable<AttributeInfo> attributes, String indent) {
    P = indent;
    for (var a:attributes) ed(a);
  }

  String P = "";

  void ed(AttributeInfo a) {
    switch (a.tag()) {
      case ATTRIBUTE_ConstantValue -> ed((ConstantValue)a);
      case ATTRIBUTE_Code -> ed((Code)a);
      case ATTRIBUTE_StackMapTable -> ed((StackMapTable)a);
      case ATTRIBUTE_Exceptions -> ed((Exceptions)a);
      case ATTRIBUTE_InnerClasses -> ed((InnerClasses)a);
      case ATTRIBUTE_EnclosingMethod -> ed((EnclosingMethod)a);
      case ATTRIBUTE_Synthetic -> ed((Synthetic)a);
      case ATTRIBUTE_Signature -> ed((Signature)a);
      case ATTRIBUTE_SourceFile -> ed((SourceFile)a);
      case ATTRIBUTE_SourceDebugExtension -> ed((SourceDebugExtension)a);
      case ATTRIBUTE_LineNumberTable -> ed((LineNumberTable)a);
      case ATTRIBUTE_LocalVariableTable -> ed((LocalVariableTable)a);
      case ATTRIBUTE_LocalVariableTypeTable -> ed((LocalVariableTypeTable)a);
      case ATTRIBUTE_Deprecated -> ed((Deprecated)a);
      case ATTRIBUTE_RuntimeVisibleAnnotations -> ed((RuntimeVisibleAnnotations)a);
      case ATTRIBUTE_RuntimeInvisibleAnnotations -> ed((RuntimeInvisibleAnnotations)a);
      case ATTRIBUTE_RuntimeVisibleParameterAnnotations -> ed((RuntimeVisibleParameterAnnotations)a);
      case ATTRIBUTE_RuntimeInvisibleParameterAnnotations -> ed((RuntimeInvisibleParameterAnnotations)a);
      case ATTRIBUTE_RuntimeVisibleTypeAnnotations -> ed((RuntimeVisibleTypeAnnotations)a);
      case ATTRIBUTE_RuntimeInvisibleTypeAnnotations -> ed((RuntimeInvisibleTypeAnnotations)a);
      case ATTRIBUTE_AnnotationDefault -> ed((AnnotationDefault)a);
      case ATTRIBUTE_BootstrapMethods -> ed((BootstrapMethods)a);
      case ATTRIBUTE_MethodParameters -> ed((MethodParameters)a);
      case ATTRIBUTE_Module -> ed((Module)a);
      case ATTRIBUTE_ModulePackages -> ed((ModulePackages)a);
      case ATTRIBUTE_ModuleMainClass -> ed((ModuleMainClass)a);
      case ATTRIBUTE_NestHost -> ed((NestHost)a);
      case ATTRIBUTE_NestMembers -> ed((NestMembers)a);
      case ATTRIBUTE_Record -> ed((Record)a);
      case ATTRIBUTE_PermittedSubclasses -> ed((PermittedSubclasses)a);

      default -> f("--> a %s\n", a);
    }
  }

  /**
   *  ConstantValue_attribute : @ 4.7.2
   *    u2 constantvalue_index
   */
  void ed(ConstantValue a) {
    f("%s %s\n", P, a);
  }

  /**
   *  Code_attribute : @ 4.7.3
   *    u2 attribute_name_index
   *    u4 attribute_length
   *    u2 max_stack
   *    u2 max_locals
   *    u4 code_length
   *    u1 code[code_length]
   *    u2 exception_table_length
   *    { u2 start_pc
   *      u2 end_pc
   *      u2 handler_pc
   *      u2 catch_type
   *    } exception_table[exception_table_length]
   *    u2 attributes_count
   *    attribute_info attributes[attributes_count]
   */
  void ed(Code c) {
    f("%s %s\n", P, c);
    f("  instructions:\n");
    for (var i:c.codes()) f("   %s\n", instruction.format(i));
    f("  exceptions:\n");
    for (var e:c.exceptions()) f("   %s\n", e);
    f("  attributes:\n");
    attributes(c.attributes(),"  ");
  }

  Instruction instruction = new Instruction();

  /**
   *  StackMapTable_attribute : @ 4.7.4
   *    u2 number_of_entries
   *    stack_map_frame entries[number_of_entries]
   */
  void ed(StackMapTable t) {
    f("%s %s\n", P, t);
    for (var f:t.entries()) ed(f);
  }

  void ed(StackMapFrame f) {
    f("%s  %s\n", P, f);
    // TODO: print stack_map_frame
  }

  /**
   *  Exceptions_attribute : @ 4.7.5
   *    u2 number_of_exceptions
   *    u2 exception_index_table[number_of_exceptions]
   */
  void ed(Exceptions e) {
    f("%s %s\n", P, e);
    for (var t:e.types()) ed(t);
  }

  /**
   *  InnerClasses_attribute : @ 4.7.6
   *    u2 number_of_classes
   *    { u2 inner_class_info_index
   *      u2 outer_class_info_index
   *      u2 inner_name_index
   *      u2 inner_class_access_flags
   *    } classes[number_of_classes]
   */
  void ed(InnerClasses c) {
    f("%s %s\n", P, c);
    for (var t:c.types()) f("%s  %s\n", P, t);
  }

  /**
   *  EnclosingMethod_attribute : @ 4.7.7
   *    u2 class_index
   *    u2 method_index
   */
  void ed(EnclosingMethod a) {
    f("%s %s\n", P, a);
  }

  /**
   *  Synthetic_attribute : @ 4.7.8
   */
  void ed(Synthetic a) {
    f("%s %s\n", P, a);
  }

  /**
   *  Signature_attribute : @ 4.7.9
   *    u2 signature_index
   */
  void ed(Signature a) {
    f("%s %s\n", P, a);
  }

  /**
   *  SourceFile_attribute : @ 4.7.10
   *    u2 sourcefile_index
   */
  void ed(SourceFile a) {
    f("%s %s\n", P, a);
  }

  /**
   *  SourceDebugExtension_attribute : @ 4.7.11
   *    u1 debug_extension[attribute_length]
   */
  void ed(SourceDebugExtension a) {
    f("%s %s\n", P, a);
    // TODO: hex dump debug_extension[]
  }

  /**
   *  LineNumberTable_attribute : @ 4.7.12
   *    u2 line_number_table_length
   *    { u2 start_pc
   *      u2 line_number
   *    } line_number_table[line_number_table_length]
   */
  void ed(LineNumberTable t) {
    f("%s %s\n", P, t);
    for (var l:t.lines()) f("%s  %s\n", P, l);
  }

  /**
   *  LocalVariableTable_attribute : @ 4.7.13
   *    u2 local_variable_table_length
   *    { u2 start_pc
   *      u2 length
   *      u2 name_index
   *      u2 descriptor_index
   *      u2 index
   *    } local_variable_table[local_variable_table_length]
   */
  void ed(LocalVariableTable t) {
    f("%s %s\n", P, t);
    for (var l:t.locals()) f("%s  %s\n", P, l);
  }

  /**
   *  LocalVariableTypeTable_attribute : @ 4.7.14
   *    u2 local_variable_type_table_length
   *    { u2 start_pc
   *      u2 length
   *      u2 name_index
   *      u2 signature_index
   *      u2 index
   *    } local_variable_type_table[local_variable_type_table_length]
   */
  void ed(LocalVariableTypeTable t) {
    f("%s %s\n", P, t);
    for (var l:t.types()) f("%s  %s\n", P, l);
  }

  /**
   *  Deprecated_attribute : @ 4.7.15
   */
  void ed(Deprecated a) {
    f("%s %s\n", P, a);
  }

  /**
   *  RuntimeVisibleAnnotations_attribute : @ 4.7.16
   *    u2 num_annotations
   *    attributes annotations[num_annotations]
   */
  void ed(RuntimeVisibleAnnotations r) {
    f("%s %s\n", P, r);
    for (var a:r.annotations()) ed(a);
  }

  /**
   *  RuntimeInvisibleAnnotations_attribute : @ 4.7.17
   *    u2 num_annotations
   *    attributes annotations[num_annotations]
   */
  void ed(RuntimeInvisibleAnnotations r) {
    f("%s %s\n", P, r);
    for (var a:r.annotations()) ed(a);
  }

  /**
   *  RuntimeVisibleParameterAnnotations_attribute : @ 4.7.18
   *   u1 num_parameters
   *   { u2 num_annotations
   *     attributes annotations[num_annotations]
   *   } parameter_annotations[num_parameters]
   */
  void ed(RuntimeVisibleParameterAnnotations r) {
    f("%s %s\n", P, r);
    for (var p:r.annotations()) ed(p);
  }

  /**
   *  RuntimeInvisibleParameterAnnotations_attribute : @ 4.7.19
   *    u1 num_parameters
   *    { u2 num_annotations
   *      attributes annotations[num_annotations]
   *    } parameter_annotations[num_parameters]
   */
  void ed(RuntimeInvisibleParameterAnnotations r) {
    f("%s %s\n", P, r);
    for (var p:r.annotations()) ed(p);
  }

  /**
   *  RuntimeVisibleTypeAnnotations_attribute : @ 4.7.20
   *    u2 num_annotations
   *    type_annotation annotations[num_annotations]
   */
  void ed(RuntimeVisibleTypeAnnotations r) {
    f("%s %s\n", P, r);
    for (var t:r.annotations()) ed(t);
  }

  /**
   *  RuntimeInvisibleTypeAnnotations_attribute : @ 4.7.21
   *    u2 num_annotations
   *    type_annotation annotations[num_annotations]
   */
  void ed(RuntimeInvisibleTypeAnnotations r) {
    f("%s %s\n", P, r);
    for (var t:r.annotations()) ed(t);
  }

  /**
   *  AnnotationDefault_attribute : @ 4.7.22
   *    element_value default_value
   */
  void ed(AnnotationDefault a) {
    f("%s %s\n", P, a);
  }

  void ed(Annotation a) {
    f("%s  %s\n", P, a);
    // TODO: print annotation
  }

  void ed(ParameterAnnotation a) {
    f("%s  %s\n", P, a);
    // TODO: print parameter_annotation
  }

  void ed(TypeAnnotation a) {
    f("%s  %s\n", P, a);
    // TODO: print type_annotation
  }

  /**
   *  BootstrapMethods_attribute : @ 4.7.23
   *    u2 num_bootstrap_methods
   *    { u2 bootstrap_method_ref
   *      u2 num_bootstrap_arguments
   *      u2 bootstrap_arguments[num_bootstrap_arguments]
   *    } bootstrap_methods[num_bootstrap_methods]
   */
  void ed(BootstrapMethods b) {
    f("%s %s\n", P, b);
    for (var m:b.methods()) {
      f("%s  %s\n", P, m);
      // TODO: print bootstrap_arguments[]
    }
  }

  /**
   *  MethodParameters_attribute : @ 4.7.24
   *    u1 parameters_count
   *    { u2 name_index
   *      u2 access_flags
   *    } parameters[parameters_count]
   */
  void ed(MethodParameters m) {
    f("%s %s\n", P, m);
    for (var p:m.parameters()) f("%s  %s\n", P, p);
  }

  /**
   *  Module_attribute : @ 4.7.25
   *    u2 module_name_index
   *    u2 module_flags
   *    u2 module_version_index
   *    u2 requires_count
   *    { u2 requires_index
   *      u2 requires_flags
   *      u2 requires_version_index
   *    } requires[requires_count]
   *    u2 exports_count
   *    { u2 exports_index
   *      u2 exports_flags
   *      u2 exports_to_count
   *      u2 exports_to_index[exports_to_count]
   *    } exports[exports_count]
   *    u2 opens_count
   *    { u2 opens_index
   *      u2 opens_flags
   *      u2 opens_to_count
   *      u2 opens_to_index[opens_to_count]
   *    } opens[opens_count]
   *    u2 uses_count
   *    u2 uses_index[uses_count]
   *    u2 provides_count
   *    { u2 provides_index
   *      u2 provides_with_count
   *      u2 provides_with_index[provides_with_count]
   *    } provides[provides_count]
   */
  void ed(Module a) {
    f("%s %s\n", P, a);
    // TODO: print requires[], exports[], opens[], uses_index[], provides[]
  }

  /**
   *  ModulePackages_attribute : @ 4.7.26
   *    u2 package_count
   *    u2 package_index[package_count]
   */
  void ed(ModulePackages m) {
    f("%s %s\n", P, m);
    for (var p:m.packages()) f("%s  %s\n", P, p);
  }

  /**
   *  ModuleMainClass_attribute : @ 4.7.27
   *    u2 main_class_index
   */
  void ed(ModuleMainClass a) {
    f("%s %s\n", P, a);
  }

  /**
   *  NestHost_attribute : @ 4.7.28
   *    u2 host_class_index
   */
  void ed(NestHost a) {
    f("%s %s\n", P, a);
  }

  /**
   *  NestMembers_attribute : @ 4.7.29
   *    u2 number_of_classes
   *    u2 classes[number_of_classes]
   */
  void ed(NestMembers a) {
    f("%s %s\n", P, a);
    for (var m:a.members()) f("%s  %s\n", P, m);
  }

  /**
   *  Record_attribute : @ 4.7.30
   *    u2 components_count
   *    record_component_info components[components_count]
   */
  void ed(Record r) {
    f("%s %s\n", P, r);
    for (var c:r.components()) ed(c);
  }

  void ed(RecordComponent c) {
    f("%s  %s\n", P, c);
    // TODO: print record_component
  }

  /**
   *  PermittedSubclasses_attribute : @ 4.7.31
   *    u2 number_of_classes
   *    u2 classes[number_of_classes]
   */
  void ed(PermittedSubclasses a) {
    f("%s %s\n", P, a);
    for (var s:a.subclasses()) f("%s  %s\n", P, s);
  }

}
