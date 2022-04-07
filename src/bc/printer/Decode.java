package bc.printer;

import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.function.Consumer;

import bc.ClassFile;
import static bc.ClassFile.*;
import static bc.JVMS.*;

public class Decode { // for bc.builder

  public static void main(String...args) throws Exception {
    if (args.length < 1) {
      System.out.println("usage: Decode <class.file> ...");
    } else {
      var d = new Decode(System.out);
      for (var file:args) {
        var p = Paths.get(file);
        System.out.println("Classfile "+p.normalize().toAbsolutePath());
        d.decode(Files.readAllBytes(p));
      }
    }
  }

  public Decode(PrintStream out) {
    this.out = out;
  }

  PrintStream out;
  ClassFile cf;

  public void decode(byte[] b) {
    cf = ClassFile.parse(b);
    init();        // magic
    class_info();  // minor_version, major_version, access_flags, this_class, super_class
    interfaces();  // interfaces_count, interfaces[interfaces_count]
    fields();      // fields_count, fields[fields_count]
    methods();     // methods_count, methods[methods_count]
    attributes();  // attributes_count, attributes[attributes_count]
    constants();   // constant_pool_count, constants[constant_pool_count-1]
  }

  void init() {
    assert cf.magic() == 0xcafebabe;
    values();
  }

  /**
   *  ClassFile: # 4.1
   */
  void class_info() {
    f("CompilationUnit.of(%s)\n", s(cf.type()) );
    f("  superType(%s)\n", s(cf.superType()) );
    f("  version(%d.%d)\n", cf.major(), cf.minor() );
    f("  flags(0x%04x)\n", cf.flags() );
  }

  void interfaces() {
    var n = cf.interfacesCount();
    if (n < 1) {
      f("# %d interfaces\n", n);
    } else {
      f("  interfaces( #%d\n", n);
      g(cf.interfaces(), i -> f("    %s\n", s(i)) );
      f("  )\n");
    }
  }

  /**
   *  cp_info : # 4.4
   *    u1 tag
   *    u1 info[]
   */
  void constants() {
    f("# %d constants\n", cf.constantCount());
    g(cf.constantPool(), this::constant);
  }

  void constant(CP.Info c) {
    switch (c.tag()) {
      case CONSTANT_Utf8 -> ed((CP.Utf8)c);
        // CONSTANT_Integer -> ed((IntegerRef)c);
        // CONSTANT_Float -> ed((FloatRef)c);
        // CONSTANT_Long -> ed((LongRef)c);
        // CONSTANT_Double -> ed((DoubleRef)c);
      case CONSTANT_Class -> ed((CP.Class)c);
        // CONSTANT_String -> ed((StringRef)c);
      case CONSTANT_Fieldref -> ed((CP.Field)c);
      case CONSTANT_Methodref -> ed((CP.Method)c);
        // CONSTANT_InterfaceMethodref -> ed((InterfaceMethodRef)c);
      case CONSTANT_NameAndType -> ed((CP.NameAndType)c);
        // CONSTANT_MethodHandle -> ed((MethodHandleRef)c);
        // CONSTANT_MethodType -> ed((MethodTypeRef)c);
        // CONSTANT_Dynamic -> ed((DynamicRef)c);
        // CONSTANT_InvokeDynamic -> ed((InvokeDynamicRef)c);
        // CONSTANT_Module -> ed((ModuleRef)c);
        // CONSTANT_Package -> ed((PackageRef)c);

      default -> f("--> %s\n", c);
    }
  }

  /**
   *  CONSTANT_Class_info : # 4.4.1
   *    u2 name_index
   */
  void ed(CP.Class c) {
    f("  TODO: %s\n", c);
  }

  // # 4.4.2
  void ed(CP.Field c) {
    f("  TODO: %s\n", c);
  }

  /**
   *  CONSTANT_Methodref_info : # 4.4.2
   *    u2 class_index -> Class
   *    u2 name_and_type_index -> NameAndType
   */
  void ed(CP.Method c) {
    f("  TODO: %s\n", c);
  }

  // void ed(InterfaceMethodRef c) { d("  TODO: %s\n", c); } # 4.4.2

  // void ed(StringRef c) { d("  TODO: %s\n", c); } # 4.4.3

  // void ed(FloatRef c) { d("  TODO: %s\n", c); } # 4.4.4
  // void ed(IntegerRef c) { d("  TODO: %s\n", c); } # 4.4.4

  // void ed(LongRef c) { d("  TODO: %s\n", c); } # 4.4.5
  // void ed(DoubleRef c) { d("  TODO: %s\n", c); } # 4.4.5

  /**
   *  CONSTANT_NameAndType_info : # 4.4.6
   *    u2 name_index -> Utf8
   *    u2 descriptor_index -> Utf8
   */
  void ed(CP.NameAndType c) {
    f("  TODO: %s\n", c);
  }

  /**
   *  CONSTANT_Utf8_info : # 4.4.7
   *   u2 length
   *   u1 bytes[length]
   */
  void ed(CP.Utf8 c) {
    f("  TODO: %s\n", c);
  }

  // void ed(MethodHandleRef c) { d("  TODO: %s\n", c); } # 4.4.8

  // void ed(MethodTypeRef c) { d("  TODO: %s\n", c); } # 4.4.9

  // void ed(DynamicRef c)  { d("  TODO: %s\n", c); } # 4.4.10
  // void ed(InvokeDynamicRef c) { d("  TODO: %s\n", c); } # 4.4.10

  // void ed(ModuleRef c) { d("  TODO: %s\n", c); } # 4.4.11

  // void ed(PackageRef c) { d("  TODO: %s\n", c); } # 4.4.12

  /**
   *  field_info : # 4.5
   *    u2 access_flags
   *    u2 name_index
   *    u2 descriptor_index
   *    u2 attributes_count
   *    attribute_info attributes[attributes_count]
   */
  void fields() {
    f("# %d fields\n", cf.fieldsCount());
    for (var d:cf.fields()) {
      f("  FieldInfo(%s, %s)\n", s(d.name()), s(d.descriptor()));
      f("    flags(0x%04x)\n", d.flags() );
      q(d.attributes(), this::attribute);
    }
  }

  /**
   *  method_info : # 4.6
   *    u2 access_flags
   *    u2 name_index
   *    u2 descriptor_index
   *    u2 attributes_count
   *    attribute_info attributes[attributes_count]
   */
  void methods() {
    f("# %d methods\n", cf.methodsCount());
    for (var d:cf.methods()) {
      f("  MethodInfo(%s, %s)\n", s(d.name()), s(d.descriptor()));
      f("    flags(0x%04x)\n", d.flags() );
      q(d.attributes(), this::attribute);
    }
  }

  /**
   * attribute_info : # 4.7
   *   u2 attribute_name_index
   *   u4 attribute_length
   *   u1 info[attribute_length]
   */
  void attributes() {
    f("# %d attributes\n", cf.attributesCount());
    g(cf.attributes(), this::attribute);
  }

  void attribute(AttributeInfo a) {
    switch (a.tag()) {
        // ATTRIBUTE_ConstantValue -> ed((ConstantValue)a);
      case ATTRIBUTE_Code -> ed((Code)a);
        // ATTRIBUTE_StackMapTable -> ed((StackMapTable)a);
        // ATTRIBUTE_Exceptions -> ed((Exceptions)a);
        // ATTRIBUTE_InnerClasses -> ed((InnerClasses)a);
        // ATTRIBUTE_EnclosingMethod -> ed((EnclosingMethod)a);
        // ATTRIBUTE_Synthetic -> ed((Synthetic)a);
      case ATTRIBUTE_Signature -> ed((Signature)a);
      case ATTRIBUTE_SourceFile -> ed((SourceFile)a);
        // ATTRIBUTE_SourceDebugExtension -> ed((SourceDebugExtension)a);
      case ATTRIBUTE_LineNumberTable -> ed((LineNumberTable)a);
      case ATTRIBUTE_LocalVariableTable -> ed((LocalVariableTable)a);
        // ATTRIBUTE_LocalVariableTypeTable -> ed((LocalVariableTypeTable)a);
        // ATTRIBUTE_Deprecated -> ed((Deprecated)a);
      case ATTRIBUTE_RuntimeVisibleAnnotations -> ed((RuntimeVisibleAnnotations)a);
        // ATTRIBUTE_RuntimeInvisibleAnnotations -> ed((RuntimeInvisibleAnnotations)a);
        // ATTRIBUTE_RuntimeVisibleParameterAnnotations -> ed((RuntimeVisibleParameterAnnotations)a);
        // ATTRIBUTE_RuntimeInvisibleParameterAnnotations -> ed((RuntimeInvisibleParameterAnnotations)a);
        // ATTRIBUTE_RuntimeVisibleTypeAnnotations -> ed((RuntimeVisibleTypeAnnotations)a);
        // ATTRIBUTE_RuntimeInvisibleTypeAnnotations -> ed((RuntimeInvisibleTypeAnnotations)a);
        // ATTRIBUTE_AnnotationDefault -> ed((AnnotationDefault)a);
        // ATTRIBUTE_BootstrapMethods -> ed((BootstrapMethods)a);
        // ATTRIBUTE_MethodParameters -> ed((MethodParameters)a);
        // ATTRIBUTE_Module -> ed((Module)a);
        // ATTRIBUTE_ModulePackages -> ed((ModulePackages)a);
        // ATTRIBUTE_ModuleMainClass -> ed((ModuleMainClass)a);
        // ATTRIBUTE_NestHost -> ed((NestHost)a);
        // ATTRIBUTE_NestMembers -> ed((NestMembers)a);
        // ATTRIBUTE_Record -> ed((Record)a);
        // ATTRIBUTE_PermittedSubclasses -> ed((PermittedSubclasses)a);

      default -> f("--> a %s\n", a);
    }
  }

  // void ed(ConstantValue a) { d("%s TODO: %s\n", P, a); } # 4.7.2

  /**
   *  Code_attribute : # 4.7.3
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
    f("%s Code()\n", P);
    p(+2);
    f("%s stack(%d)\n", P, c.maxStack());
    f("%s locals(%d)\n", P, c.maxLocals());
    instructions(c);
    exceptions(c);
    attributes(c);
    p(-2);
  }

  Operation op = new Operation();

  void instructions(Code c) {
    f("%s # instructions\n", P);
    q(c.codes(), o -> f("%s %s\n", P, op.format(o)) );
  }

  void exceptions(Code c) {
    f("%s # exceptions\n", P);
    q(c.exceptions(), e -> f("%s --> %s\n", P, e) );
  }

  void attributes(Code c) {
    f("%s # attributes\n", P);
    q(c.attributes(), this::attribute);
  }

  // void ed(StackMapTable a) { d("%s TODO: %s\n", P, a); } # 4.7.4

  // void ed(Exceptions a) { d("%s TODO: %s\n", P, a); } # 4.7.5

  // void ed(InnerClasses a) { d("%s TODO: %s\n", P, a); } # 4.7.6

  // void ed(EnclosingMethod a) { d("%s TODO: %s\n", P, a); } # 4.7.7

  // void ed(Synthetic a) { d("%s TODO: %s\n", P, a); } # 4.7.8

  /**
   *  Signature_attribute : # 4.7.9
   *    u2 signature_index
   */
  void ed(Signature a) {
    f("%s Signature(%s)\n", P, s(a.signature()));
  }

  /**
   *  SourceFile_attribute : # 4.7.10
   *    u2 sourcefile_index
   */
  void ed(SourceFile a) {
    f("%s SourceFile(%s)\n", P, s(a.sourcefile()));
  }

  // void ed(SourceDebugExtension a) { d("%s TODO: %s\n", P, a); } # 4.7.11

  /**
   *  LineNumberTable_attribute : # 4.7.12
   *    u2 line_number_table_length
   *    { u2 start_pc
   *      u2 line_number
   *    } line_number_table[line_number_table_length]
   */
  void ed(LineNumberTable t) {
    f("%s LineNumberTable()\n", P);
    q(t.lines(), l -> f("%s %04x  @%d\n", P, l.pc(), l.n()) );
  }

  /**
   *  LocalVariableTable_attribute : # 4.7.13
   *    u2 local_variable_table_length
   *    { u2 start_pc
   *      u2 length
   *      u2 name_index
   *      u2 descriptor_index
   *      u2 index
   *    } local_variable_table[local_variable_table_length]
   */
  void ed(LocalVariableTable t) {
    f("%s LocalVariableTable()\n", P);
    q(t.locals(), l -> f("%s %04x  $%d  %d %s %s\n", P, l.pc(), l.index(), l.len(), s(l.name()), s(l.descriptor()) ) );
  }

  // void ed(LocalVariableTypeTable a) { d("%s TODO: %s\n", P, a); } # 4.7.14

  // void ed(Deprecated a) { d("%s TODO: %s\n", P, a); } # 4.7.15

  // # 4.7.16
  void ed(RuntimeVisibleAnnotations a) {
    f("%s TODO: %s\n", P, a);
  }

  // void ed(RuntimeInvisibleAnnotations a) { d("%s TODO: %s\n", P, a); } # 4.7.17

  // void ed(RuntimeVisibleParameterAnnotations a) { d("%s TODO: %s\n", P, a); } # 4.7.18

  // void ed(RuntimeInvisibleParameterAnnotations a) { d("%s TODO: %s\n", P, a); } # 4.7.19

  // void ed(RuntimeVisibleTypeAnnotations a) { d("%s TODO: %s\n", P, a); } # 4.7.20

  // void ed(RuntimeInvisibleTypeAnnotations a) { d("%s TODO: %s\n", P, a); } # 4.7.21

  // void ed(AnnotationDefault a) { d("%s TODO: %s\n", P, a); } # 4.7.22

  // void ed(BootstrapMethods a) { d("%s TODO: %s\n", P, a); } # 4.7.23

  // void ed(MethodParameters a) { d("%s TODO: %s\n", P, a); } # 4.7.24

  // void ed(Module a) { d("%s TODO: %s\n", P, a); } # 4.7.25

  // void ed(ModulePackages a) { d("%s TODO: %s\n", P, a); } # 4.7.26

  // void ed(ModuleMainClass a) { d("%s TODO: %s\n", P, a); } # 4.7.27

  // void ed(NestHost a) { d("%s TODO: %s\n", P, a); } # 4.7.28

  // void ed(NestMembers a) { d("%s TODO: %s\n", P, a); } # 4.7.29

  // void ed(Record a) { d("%s TODO: %s\n", P, a); } # 4.7.30

  // void ed(PermittedSubclasses a) { d("%s TODO: %s\n", P, a); } # 4.7.31


  String P = " ";

  void p(int n) { P = "                     ".substring(0,P.length()+n); }
  <T> void q(Iterable<T> i, Consumer<T> c) { p(+2); for (var t:i) c.accept(t); p(-2); }

  void f(String format, Object... args) { out.format(format,args); }
  <T> void g(Iterable<T> i, Consumer<T> c) { for (var t:i) c.accept(t); }

  Object[] V;

  void values() {
    V = new Object[cf.constantCount()];
    for (var i:cf.constantPool()) {
      V[i.index()] = switch(i.tag()) {

        case CONSTANT_Utf8 -> "\"" + cf.chars(((CP.Utf8)i).offset(),((CP.Utf8)i).length()) + "\"";

        case CONSTANT_Integer -> cf.int32(((CP.Integer)i).offset());
        case CONSTANT_Float -> Float.floatToIntBits(cf.int32(((CP.Float)i).offset()));

        case CONSTANT_Long -> cf.int64(((CP.Long)i).offset());
        case CONSTANT_Double -> Double.longBitsToDouble(cf.int64(((CP.Double)i).offset()));

        default -> null;
      };
    }
  }

  String s(CP.Info i) {
    return String.valueOf(switch (i.tag()) {

      case CONSTANT_Class -> V[((CP.Class)i).name()];
      case CONSTANT_Module -> V[((CP.Module)i).name()];
      case CONSTANT_Package -> V[((CP.Package)i).name()];

      case CONSTANT_MethodType -> V[((CP.MethodType)i).descriptor()];

      case CONSTANT_String -> V[((CP.String)i).string()];

      case CONSTANT_Fieldref, CONSTANT_Methodref, CONSTANT_InterfaceMethodref,
           CONSTANT_NameAndType, CONSTANT_MethodHandle,      // TODO:
           CONSTANT_Dynamic, CONSTANT_InvokeDynamic -> null; // multiple values

      default -> V[i.index()];
    });
  }

  String s(short i) { return String.valueOf(V[i]); }
}
/*
*/
