package bc;

public interface CompilationUnit {

  // version( major_version [ . minor_version ] )
  ClassInfo Class(Object... type); // this_class [, super_class]

  interface ClassInfo {
    byte[] bytes();

    ClassInfo flags(short... flags);
    ClassInfo interfaces(Object... type);

    FieldInfo Field(Object... type);  // name, descriptor
    MethodInfo Method(Object... type); // name, descriptor

    RecordInfo  Record ();

    ClassInfo  InnerClasses ();
    ClassInfo  EnclosingMethod ();
    ClassInfo  Synthetic ();
    ClassInfo  Signature ();
    ClassInfo  SourceFile ();
    ClassInfo  SourceDebugExtension ();
    ClassInfo  Deprecated ();
    ClassInfo  RuntimeVisibleAnnotations ();
    ClassInfo  RuntimeInvisibleAnnotations ();
    ClassInfo  RuntimeVisibleTypeAnnotations ();
    ClassInfo  RuntimeInvisibleTypeAnnotations ();
    ClassInfo  BootstrapMethods ();
    ClassInfo  Module ();
    ClassInfo  ModulePackages ();
    ClassInfo  ModuleMainClass ();
    ClassInfo  NestHost ();
    ClassInfo  NestMembers ();
    ClassInfo  PermittedSubclasses ();
  }

  interface FieldInfo {

    FieldInfo flags(short... flags);

    FieldInfo  ConstantValue ();
    FieldInfo  Synthetic ();
    FieldInfo  Signature ();
    FieldInfo  Deprecated ();
    FieldInfo  RuntimeVisibleAnnotations ();
    FieldInfo  RuntimeInvisibleAnnotations ();
    FieldInfo  RuntimeVisibleTypeAnnotations ();
    FieldInfo  RuntimeInvisibleTypeAnnotations ();
  }

  interface MethodInfo {

    MethodInfo flags(short... flags);

    CodeInfo  Code ();

    MethodInfo  Exceptions ();
    MethodInfo  Synthetic ();
    MethodInfo  Signature ();
    MethodInfo  Deprecated ();
    MethodInfo  RuntimeVisibleAnnotations ();
    MethodInfo  RuntimeInvisibleAnnotations ();
    MethodInfo  RuntimeVisibleParameterAnnotations ();
    MethodInfo  RuntimeInvisibleParameterAnnotations ();
    MethodInfo  RuntimeVisibleTypeAnnotations ();
    MethodInfo  RuntimeInvisibleTypeAnnotations ();
    MethodInfo  AnnotationDefault ();
    MethodInfo  MethodParameters ();
  }

  interface CodeInfo {
    CodeInfo  StackMapTable ();
    CodeInfo  LineNumberTable ();
    CodeInfo  LocalVariableTable ();
    CodeInfo  LocalVariableTypeTable ();
    CodeInfo  RuntimeVisibleTypeAnnotations ();
    CodeInfo  RuntimeInvisibleTypeAnnotations ();
  }

  interface RecordInfo {
    RecordInfo  Signature ();
    RecordInfo  RuntimeVisibleAnnotations ();
    RecordInfo  RuntimeInvisibleAnnotations ();
    RecordInfo  RuntimeVisibleTypeAnnotations ();
    RecordInfo  RuntimeInvisibleTypeAnnotations ();
  }

  // factory

  static CompilationUnit build(float v) { // major_version[.minor_version]
    try {
      return (CompilationUnit)
        Class.forName("bc.builder.Bytecode").getConstructor(Float.TYPE).newInstance(v);
    } catch (Exception e) {
      throw new UnsupportedOperationException(e);
    }
  }

}
