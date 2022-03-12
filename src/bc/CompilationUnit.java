package bc;

public interface CompilationUnit {

  interface ClassInfo {                          // ClassFile {
                                                 //   u4             magic;
                                                 //   u2             minor_version;
    ClassInfo version(float m);                  //   u2             major_version;
                                                 //   u2             constant_pool_count;
    ClassInfo constants(Object...value);         //   cp_info        constant_pool[constant_pool_count-1];
    ClassInfo flags(JVMS.Access...flags);        //   u2             access_flags;
                                                 //   u2             this_class;
    ClassInfo superType(Object type);            //   u2             super_class;
                                                 //   u2             interfaces_count;
    ClassInfo interfaces(Object...type);         //   u2             interfaces[interfaces_count];
                                                 //   u2             fields_count;
    ClassInfo fields(FieldInfo...args);          //   field_info     fields[fields_count];
                                                 //   u2             methods_count;
    ClassInfo methods(MethodInfo...args);        //   method_info    methods[methods_count];
                                                 //   u2             attributes_count;
    ClassInfo attributes(AttributeInfo...args);  //   attribute_info attributes[attributes_count];
                                                 // }
    /* generates bytecode */
    byte[] bytes(); 
    
    ConstantPool Constant();

    FieldInfo Field(String name, Object type);
    MethodInfo Method(String name, Object type);

    AttributeInfo InnerClasses();
    AttributeInfo EnclosingMethod();
    AttributeInfo Synthetic();
    AttributeInfo Signature();
    AttributeInfo SourceFile();
    AttributeInfo SourceDebugExtension();
    AttributeInfo Deprecated();
    AttributeInfo RuntimeVisibleAnnotations();
    AttributeInfo RuntimeInvisibleAnnotations();
    AttributeInfo RuntimeVisibleTypeAnnotations();
    AttributeInfo RuntimeInvisibleTypeAnnotations();
    AttributeInfo BootstrapMethods();
    AttributeInfo Module();
    AttributeInfo ModulePackages();
    AttributeInfo ModuleMainClass();
    AttributeInfo NestHost();
    AttributeInfo NestMembers();
    AttributeInfo Record();
    AttributeInfo PermittedSubclasses();
  }

  interface FieldInfo {

    FieldInfo flags(JVMS.Access...flags);
    FieldInfo attributes(AttributeInfo...args);

    AttributeInfo ConstantValue();
    AttributeInfo Synthetic();
    AttributeInfo Signature();
    AttributeInfo Deprecated();
    AttributeInfo RuntimeVisibleAnnotations();
    AttributeInfo RuntimeInvisibleAnnotations();
    AttributeInfo RuntimeVisibleTypeAnnotations();
    AttributeInfo RuntimeInvisibleTypeAnnotations();
  }

  interface MethodInfo {

    MethodInfo flags(JVMS.Access...flags);
    MethodInfo attributes(AttributeInfo...args);

    AttributeInfo Code();
    AttributeInfo Exceptions();
    AttributeInfo Synthetic();
    AttributeInfo Signature();
    AttributeInfo Deprecated();
    AttributeInfo RuntimeVisibleAnnotations();
    AttributeInfo RuntimeInvisibleAnnotations();
    AttributeInfo RuntimeVisibleParameterAnnotations();
    AttributeInfo RuntimeInvisibleParameterAnnotations();
    AttributeInfo RuntimeVisibleTypeAnnotations();
    AttributeInfo RuntimeInvisibleTypeAnnotations();
    AttributeInfo AnnotationDefault();
    AttributeInfo MethodParameters();
  }

  interface ConstantPool {

    CPInfo Class();
    CPInfo Fieldref();
    CPInfo Methodref();
    CPInfo InterfaceMethodref();
    CPInfo String();
    CPInfo Integer();
    CPInfo Float();
    CPInfo Long();
    CPInfo Double();
    CPInfo NameAndType();
    CPInfo Utf8();
    CPInfo MethodHandle();
    CPInfo MethodType();
    CPInfo Dynamic();
    CPInfo InvokeDynamic();
    CPInfo Module();
    CPInfo Package();
  }

  interface CPInfo {}
  interface AttributeInfo {}

  /*

  Code();
    StackMapTable
    LineNumberTable
    LocalVariableTable
    LocalVariableTypeTable
    RuntimeVisibleTypeAnnotations
    RuntimeInvisibleTypeAnnotations

  Record(...)
    Signature
    RuntimeVisibleAnnotations
    RuntimeInvisibleAnnotations
    RuntimeVisibleTypeAnnotations
    RuntimeInvisibleTypeAnnotations

  */
  
  static ClassInfo build(String c) { // this_class
    try {
      return (ClassInfo)
        Class.forName("bc.builder.Bytecode").getConstructor(c.getClass()).newInstance((Object)c);
    } catch (Exception e) {
      throw new UnsupportedOperationException(e);
    }
  }

}
