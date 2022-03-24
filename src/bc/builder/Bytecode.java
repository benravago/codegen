package bc.builder;

import bc.CompilationUnit;

public class Bytecode implements CompilationUnit {

  final short major_version, minor_version;

  public Bytecode(float version) {
    assert version > 1.0;
    var s = Float.toString(version);
    var p = s.indexOf('.');
    major_version = Short.valueOf(s.substring(0,p));
    minor_version = Short.valueOf(s.substring(p+1));
  }

  @Override
  public ClassInfo Class(Object... type) {
    return new ClassInfo();
  }

  class ClassInfo implements CompilationUnit.ClassInfo {
    @Override public byte[] bytes() { return null; }
    @Override public ClassInfo flags(short... flags) { return this; }
    @Override public ClassInfo interfaces(Object... type) { return this; }
    @Override public FieldInfo Field(Object... type) { return null; }
    @Override public MethodInfo Method(Object... type) { return null; }
    @Override public RecordInfo Record() { return null; }
    @Override public ClassInfo InnerClasses() { return this; }
    @Override public ClassInfo EnclosingMethod() { return this; }
    @Override public ClassInfo Synthetic() { return this; }
    @Override public ClassInfo Signature() { return this; }
    @Override public ClassInfo SourceFile() { return this; }
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
  } // ClassInfo

  class FieldInfo implements CompilationUnit.FieldInfo {
    @Override public FieldInfo flags(short... flags) { return this; }
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
    @Override public MethodInfo flags(short... flags) { return this; }
    @Override public CodeInfo Code() { return null; }
    @Override public MethodInfo Exceptions() { return this; }
    @Override public MethodInfo Synthetic() { return this; }
    @Override public MethodInfo Signature() { return this; }
    @Override public MethodInfo Deprecated() { return this; }
    @Override public MethodInfo RuntimeVisibleAnnotations() { return this; }
    @Override public MethodInfo RuntimeInvisibleAnnotations() { return this; }
    @Override public MethodInfo RuntimeVisibleParameterAnnotations() { return this; }
    @Override public MethodInfo RuntimeInvisibleParameterAnnotations() { return this; }
    @Override public MethodInfo RuntimeVisibleTypeAnnotations() { return this; }
    @Override public MethodInfo RuntimeInvisibleTypeAnnotations() { return this; }
    @Override public MethodInfo AnnotationDefault() { return this; }
    @Override public MethodInfo MethodParameters() { return this; }
  } // MethodInfo

  class CodeInfo implements CompilationUnit.CodeInfo {
    @Override public CodeInfo StackMapTable() { return this; }
    @Override public CodeInfo LineNumberTable() { return this; }
    @Override public CodeInfo LocalVariableTable() { return this; }
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

}
