package bc.printer;

import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import bc.ClassFile;
import static bc.ClassFile.*;
import static bc.JVMS.*;

public class Decode {

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
  void f(String format, Object... args) { out.format(format,args); }

  public void decode(byte[] b) {
    var cf = ClassFile.parse(b);
                       // magic
    class_info(cf);    // minor_version, major_version, access_flags, this_class, super_class
    interfaces(cf);    // interfaces_count, interfaces[interfaces_count]
    fields(cf);        // fields_count, fields[fields_count]
    methods(cf);       // methods_count, methods[methods_count]
    attributes(cf);    // attributes_count, attributes[attributes_count]
    constant_pool(cf); // constant_pool_count, constant_pool[constant_pool_count-1]
  }

  void class_info(ClassFile cf) {
    f("CompilationUnit.of(\"%s\")\n", cf.type() );
    f("  superType(\"%s\")\n", cf.superType() );
    f("  version(%d.%d)\n", cf.major(), cf.minor() );
    f("  flags(0x%04x)\n", cf.flags() );
  }

  void interfaces(ClassFile cf) {
    var n = cf.interfacesCount();
    if (n < 1) {
      f("# %d interfaces\n", n);
    } else {
      f("  interfaces( #%d\n", n);
      for (var i:cf.interfaces()) {
        f("    \"%s\"\n", i);
      }
      f("  )\n");
    }
  }

  void fields(ClassFile cf) {
    var n = cf.fieldsCount();
    f("# %d fields\n", n);
    for (var f:cf.fields()) {
      f("  FieldInfo(\"%s\", \"%s\")\n", f.name(), f.descriptor());
      f("    flags(0x%04x)\n", f.flags() );
      p(2);
      for (var a:f.attributes()) {
        f("    -> %s\n", a);
      }
      p(-2);
    }
  }

  void methods(ClassFile cf) {
    var n = cf.methodsCount();
    f("# %d methods\n", n);
    for (var m:cf.methods()) {
      f("  MethodInfo(\"%s\", \"%s\")\n", m.name(), m.descriptor());
      f("    flags(0x%04x)\n", m.flags() );
      p(2);
      for (var a:m.attributes()) {
        attribute(a);
      }
      p(-2);
    }
  }

  void constant_pool(ClassFile cf) {
    f("# %d constants\n", cf.constantCount());
    for (var c:cf.constantPool()) {
      switch (c.tag()) {
        case CONSTANT_Utf8 -> ed((Utf8Ref)c);
          // CONSTANT_Integer -> ed((IntegerRef)c);
          // CONSTANT_Float -> ed((FloatRef)c);
          // CONSTANT_Long -> ed((LongRef)c);
          // CONSTANT_Double -> ed((DoubleRef)c);
        case CONSTANT_Class -> ed((ClassRef)c);
          // CONSTANT_String -> ed((StringRef)c);
          // CONSTANT_Fieldref -> ed((FieldRef)c);
        case CONSTANT_Methodref -> ed((MethodRef)c);
          // CONSTANT_InterfaceMethodref -> ed((InterfaceMethodRef)c);
        case CONSTANT_NameAndType -> ed((NameAndTypeRef)c);
          // CONSTANT_MethodHandle -> ed((MethodHandleRef)c);
          // CONSTANT_MethodType -> ed((MethodTypeRef)c);
          // CONSTANT_Dynamic -> ed((DynamicRef)c);
          // CONSTANT_InvokeDynamic -> ed((InvokeDynamicRef)c);
          // CONSTANT_Module -> ed((ModuleRef)c);
          // CONSTANT_Package -> ed((PackageRef)c);

        default -> f("--> %s\n", c);
      }
    }
  }

  void ed(Utf8Ref c) {
    f("  TODO: %s\n", c);
  }
  void ed(ClassRef c) {
    f("  TODO: %s\n", c);
  }
  void ed(MethodRef c) {
    f("  TODO: %s\n", c);
  }
  void ed(NameAndTypeRef c) {
    f("  TODO: %s\n", c);
  }

  // void ed(DoubleRef c)          { f("  TODO: %s\n", c); }
  // void ed(DynamicRef c)         { f("  TODO: %s\n", c); }
  // void ed(FieldRef c)           { f("  TODO: %s\n", c); }
  // void ed(FloatRef c)           { f("  TODO: %s\n", c); }
  // void ed(IntegerRef c)         { f("  TODO: %s\n", c); }
  // void ed(InterfaceMethodRef c) { f("  TODO: %s\n", c); }
  // void ed(InvokeDynamicRef c)   { f("  TODO: %s\n", c); }
  // void ed(LongRef c)            { f("  TODO: %s\n", c); }
  // void ed(MethodHandleRef c)    { f("  TODO: %s\n", c); }
  // void ed(MethodTypeRef c)      { f("  TODO: %s\n", c); }
  // void ed(ModuleRef c)          { f("  TODO: %s\n", c); }
  // void ed(PackageRef c)         { f("  TODO: %s\n", c); }
  // void ed(StringRef c)          { f("  TODO: %s\n", c); }

  String P = " ";
  void p(int n) { P = "                     ".substring(0,P.length()+n); }

  void attributes(ClassFile cf) {
    var n = cf.attributesCount();
    f("# %d attributes\n", n);
    for (var a:cf.attributes()) {
      attribute(a);
    }
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
        // ATTRIBUTE_RuntimeVisibleAnnotations -> ed((RuntimeVisibleAnnotations)a);
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

  /**
   *  Signature_attribute :
   *    u2 signature_index
   */
  void ed(Signature a) {
    f("%s Signature(\"%s\")\n", P, a.signature());
  }

  /**
   *  SourceFile_attribute :
   *    u2 sourcefile_index
   */
  void ed(SourceFile a) {
    f("%s SourceFile(\"%s\")\n", P, a.sourcefile());
  }

  /**
   *  Code_attribute :
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
    f("%s # instructions\n", P);
    f("%s # exceptions\n", P);
    f("%s # attributes\n", P);
    p(+2);
    for (var a:c.attributes()) {
      attribute(a);
    }
    p(-4);
  }

  // Code()
  //   stack(n)
  //   locals(n)
  //   _{instr}...


  /**
   *  LineNumberTable_attribute :
   *    u2 line_number_table_length
   *    { u2 start_pc
   *      u2 line_number
   *    } line_number_table[line_number_table_length]
   */
  void ed(LineNumberTable a) {
    f("%s TODO: %s\n", P, a);
  }

  /**
   *  LocalVariableTable_attribute :
   *    u2 local_variable_table_length
   *    { u2 start_pc
   *      u2 length
   *      u2 name_index
   *      u2 descriptor_index
   *      u2 index
   *    } local_variable_table[local_variable_table_length]
   */
  void ed(LocalVariableTable a) {
    f("%s TODO: %s\n", P, a);
  }

  // void ed(AnnotationDefault a)                    { f("%s TODO: %s\n", P, a); }
  // void ed(BootstrapMethods a)                     { f("%s TODO: %s\n", P, a); }
  // void ed(ConstantValue a)                        { f("%s TODO: %s\n", P, a); }
  // void ed(Deprecated a)                           { f("%s TODO: %s\n", P, a); }
  // void ed(EnclosingMethod a)                      { f("%s TODO: %s\n", P, a); }
  // void ed(Exceptions a)                           { f("%s TODO: %s\n", P, a); }
  // void ed(InnerClasses a)                         { f("%s TODO: %s\n", P, a); }
  // void ed(LocalVariableTypeTable a)               { f("%s TODO: %s\n", P, a); }
  // void ed(MethodParameters a)                     { f("%s TODO: %s\n", P, a); }
  // void ed(Module a)                               { f("%s TODO: %s\n", P, a); }
  // void ed(ModuleMainClass a)                      { f("%s TODO: %s\n", P, a); }
  // void ed(ModulePackages a)                       { f("%s TODO: %s\n", P, a); }
  // void ed(NestHost a)                             { f("%s TODO: %s\n", P, a); }
  // void ed(NestMembers a)                          { f("%s TODO: %s\n", P, a); }
  // void ed(PermittedSubclasses a)                  { f("%s TODO: %s\n", P, a); }
  // void ed(Record a)                               { f("%s TODO: %s\n", P, a); }
  // void ed(RuntimeInvisibleAnnotations a)          { f("%s TODO: %s\n", P, a); }
  // void ed(RuntimeInvisibleParameterAnnotations a) { f("%s TODO: %s\n", P, a); }
  // void ed(RuntimeInvisibleTypeAnnotations a)      { f("%s TODO: %s\n", P, a); }
  // void ed(RuntimeVisibleAnnotations a)            { f("%s TODO: %s\n", P, a); }
  // void ed(RuntimeVisibleParameterAnnotations a)   { f("%s TODO: %s\n", P, a); }
  // void ed(RuntimeVisibleTypeAnnotations a)        { f("%s TODO: %s\n", P, a); }
  // void ed(SourceDebugExtension a)                 { f("%s TODO: %s\n", P, a); }
  // void ed(StackMapTable a)                        { f("%s TODO: %s\n", P, a); }
  // void ed(Synthetic a)                            { f("%s TODO: %s\n", P, a); }

}
