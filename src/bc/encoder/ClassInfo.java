package bc.encoder;

import bc.CompilationUnit;
import bc.encoder.cp.ConstantPool;
import bc.encoder.fn.Access;
import static bc.encoder.fn.Macros.*;
import static bc.spec.JVMS.*;

import java.lang.constant.Constable;

class ClassInfo implements CompilationUnit.ClassInfo {

  // contstant_pool_count
  cp_info CONSTANT = new cp_info();

  short access_flags;
  short this_class, super_class;

  // interfaces_count
  short[] interfaces;

  // fields_count, fields[]

  // methods_count, methods[]

  // attributes_count, attributes[]

  ClassInfo(Constable... type) {
    this_class = CONSTANT.Class_info(arg(type,0));
    super_class = CONSTANT.Class_info(arg(type,1,Object.class));
    /*
    Method("<init>")
     .flags(SUPER)
     .Code().code()
       .aload_0()
       .invokespecial(d(Object.class,"<init>"))
       .vreturn()
    */
  }

  @Override
  public ClassInfo flags(ACC... flags) {
    access_flags = Access.forClass(flags);
    return this;
  }

    @Override public ClassInfo interfaces(Constable... type) { return this; }
    @Override public FieldInfo Field(String name, Constable descriptor) { return null; }
    @Override public MethodInfo Method(String name, Constable... descriptor) { return null; }
    @Override public RecordInfo Record() { return null; }
    @Override public ClassInfo InnerClasses() { return this; }
    @Override public ClassInfo EnclosingMethod() { return this; }
    @Override public ClassInfo Synthetic() { return this; }
    @Override public ClassInfo Signature(String signature) { return this; }
    @Override public ClassInfo SourceFile(String sourcefile) { return this; }
    @Override public ClassInfo SourceDebugExtension() { return this; }
    @Override public ClassInfo Deprecated() { return this; }
    @Override public ClassInfo RuntimeVisibleAnnotations() { return this; }
    @Override public ClassInfo RuntimeInvisibleAnnotations() { return this; }
    @Override public ClassInfo RuntimeVisibleTypeAnnotations() { return this; }
    @Override public ClassInfo RuntimeInvisibleTypeAnnotations() { return this; }
    @Override public ClassInfo BootstrapMethods() { return this; }
    @Override public ClassInfo Module() { return this; }
    @Override public ClassInfo ModulePackages() { return this; }
    @Override public ClassInfo ModuleMainClass() { return this; }
    @Override public ClassInfo NestHost() { return this; }
    @Override public ClassInfo NestMembers() { return this; }
    @Override public ClassInfo PermittedSubclasses() { return this; }
   // ClassInfo

  class FieldInfo implements CompilationUnit.FieldInfo {
    @Override public FieldInfo flags(ACC... flags) { return this; }
    @Override public FieldInfo ConstantValue() { return this; }
    @Override public FieldInfo Synthetic() { return this; }
    @Override public FieldInfo Signature() { return this; }
    @Override public FieldInfo Deprecated() { return this; }
    @Override public FieldInfo RuntimeVisibleAnnotations() { return this; }
    @Override public FieldInfo RuntimeInvisibleAnnotations() { return this; }
    @Override public FieldInfo RuntimeVisibleTypeAnnotations() { return this; }
    @Override public FieldInfo RuntimeInvisibleTypeAnnotations() { return this; }
  } // FieldInfo

  class MethodInfo implements CompilationUnit.MethodInfo {

    @Override public MethodInfo flags(ACC... flags) { return this; }
    @Override public CodeInfo Code() { return null; }

    @Override public MethodInfo Exceptions() { return this; }
    @Override public MethodInfo Synthetic() { return this; }
    @Override public MethodInfo Signature(String signature) { return this; }
    @Override public MethodInfo Deprecated() { return this; }
    @Override public MethodInfo RuntimeVisibleAnnotations(String... annotation) { return null; }
    @Override public MethodInfo RuntimeInvisibleAnnotations() { return this; }
    @Override public MethodInfo RuntimeVisibleParameterAnnotations() { return this; }
    @Override public MethodInfo RuntimeInvisibleParameterAnnotations() { return this; }
    @Override public MethodInfo RuntimeVisibleTypeAnnotations() { return this; }
    @Override public MethodInfo RuntimeInvisibleTypeAnnotations() { return this; }
    @Override public MethodInfo AnnotationDefault() { return this; }
    @Override public MethodInfo MethodParameters() { return this; }
  } // MethodInfo

  class CodeInfo implements CompilationUnit.CodeInfo {

    @Override public CodeInfo alloc(short max_stack, short max_locals) { return this; }

    @Override public CompilationUnit.Code code() { return null; }
    // TODO:  public Encode code() { return new Encode(...); }

    @Override public CodeInfo StackMapTable() { return this; }
    @Override public CodeInfo LineNumberTable(int... tag) { return this; }
    @Override public CodeInfo LocalVariableTable(int... tag) { return this; }
    @Override public CodeInfo LocalVariableTypeTable() { return this; }
    @Override public CodeInfo RuntimeVisibleTypeAnnotations() { return this; }
    @Override public CodeInfo RuntimeInvisibleTypeAnnotations() { return this; }
  } // CodeInfo

  class RecordInfo implements CompilationUnit.RecordInfo {
    @Override public RecordInfo Signature() { return this; }
    @Override public RecordInfo RuntimeVisibleAnnotations() { return this; }
    @Override public RecordInfo RuntimeInvisibleAnnotations() { return this; }
    @Override public RecordInfo RuntimeVisibleTypeAnnotations() { return this; }
    @Override public RecordInfo RuntimeInvisibleTypeAnnotations() { return this; }
  } // RecordInfo


  class cp_info extends ConstantPool {
    short Class_info(Object o) {
      return -1; // TODO: Class(classDesc(o).descriptorString());
    }
  }

}
