package bc;

public interface ClassFile {

  int                     magic();
  short                   minor();
  short                   major();
  short                   constantCount();
  Iterable<CpInfo>        constantPool();
  short                   flags();
  CpInfo                  type();
  CpInfo                  superType();
  short                   interfacesCount();
  Iterable<CpInfo>        interfaces();
  short                   fieldsCount();
  Iterable<FieldInfo>     fields();
  short                   methodsCount();
  Iterable<MethodInfo>    methods();
  short                   attributesCount();
  Iterable<AttributeInfo> attributes();

  // JVMS structures

  interface CpInfo { byte tag(); short index(); }

  record Utf8Ref(byte tag, short index, short offset, short length) implements CpInfo {}

  record IntegerRef(byte tag, short index, short offset) implements CpInfo {}
  record FloatRef(byte tag, short index, short offset) implements CpInfo {}
  record LongRef(byte tag, short index, short offset) implements CpInfo {}
  record DoubleRef(byte tag, short index, short offset) implements CpInfo {}

  record StringRef(byte tag, short index, short string) implements CpInfo {}

  record ClassRef(byte tag, short index, short name) implements CpInfo {}
  record ModuleRef(byte tag, short index, short name) implements CpInfo {}
  record PackageRef(byte tag, short index, short name) implements CpInfo {}

  record MethodTypeRef(byte tag, short index, short descriptor) implements CpInfo {}

  record NameAndTypeRef(byte tag, short index, short name, short descriptor) implements CpInfo {}

  record FieldRef(byte tag, short index, short classRef, short namedType) implements CpInfo {}
  record MethodRef(byte tag, short index, short classRef, short namedType) implements CpInfo {}
  record InterfaceMethodRef(byte tag, short index, short classRef, short namedType) implements CpInfo {}

  record MethodHandleRef(byte tag, short index, byte kind, short reference) implements CpInfo {}

  record DynamicRef(byte tag, short index, short bootstrapMethod, short namedType) implements CpInfo {}
  record InvokeDynamicRef(byte tag, short index, short bootstrapMethod, short namedType) implements CpInfo {}

  record FieldInfo(short flags, CpInfo name, CpInfo descriptor, Iterable<AttributeInfo> attributes) {}
  record MethodInfo(short flags, CpInfo name, CpInfo descriptor, Iterable<AttributeInfo> attributes) {}

  interface AttributeInfo { byte tag(); }
  interface Iteration<T> extends Iterable<T> { int size(); }

  record ConstantValue(byte tag, Object value) implements AttributeInfo {}
  record Exceptions(byte tag, Iterable<CpInfo> types) implements AttributeInfo {}
  record EnclosingMethod(byte tag, CpInfo enclosingClass, CpInfo type) implements AttributeInfo {}
  record Synthetic(byte tag) implements AttributeInfo {}
  record Signature(byte tag, CpInfo signature) implements AttributeInfo {}
  record SourceFile(byte tag, CpInfo sourcefile) implements AttributeInfo {}
  record SourceDebugExtension(byte tag, byte[] debugExtension) implements AttributeInfo {}
  record Deprecated(byte tag) implements AttributeInfo {}
  record NestHost(byte tag, CpInfo hostClass) implements AttributeInfo {}
  record NestMembers(byte tag, Iterable<CpInfo> members) implements AttributeInfo {}
  record PermittedSubclasses(byte tag, Iterable<CpInfo> subclasses) implements AttributeInfo {}

  record LineNumberTable(byte tag, Iterable<LineNumber> lines) implements AttributeInfo {}
  record LineNumber(short pc, short n) {}

  record InnerClasses(byte tag, Iterable<InnerClass> types) implements AttributeInfo {}
  record InnerClass(CpInfo info, CpInfo outerClass, CpInfo name, short flags) {}

  record LocalVariableTable(byte tag, Iterable<LocalVariable> locals) implements AttributeInfo {}
  record LocalVariable(short pc, short len, CpInfo name, CpInfo descriptor, short index) {}

  record LocalVariableTypeTable(byte tag, Iterable<LocalVariableType> types) implements AttributeInfo {}
  record LocalVariableType(short pc, short len, CpInfo name, CpInfo signature, short index) {}

  record BootstrapMethods(byte tag, Iterable<BootstrapMethod> methods) implements AttributeInfo {}
  record BootstrapMethod(CpInfo ref, Iterable<CpInfo> arguments) {}

  record MethodParameters(byte tag, Iterable<MethodParameter> parameters) implements AttributeInfo {}
  record MethodParameter(CpInfo name, short flags) {}

  record Record(byte tag, Iterable<RecordComponent> components) implements AttributeInfo {}
  record RecordComponent(CpInfo name, CpInfo descriptor, Iterable<AttributeInfo> attributes) {}

  record Code(
    byte tag,
    short maxStack,
    short maxLocals,
    Iterable<Opcode> codes,
    Iterable<Except> exceptions,
    Iterable<AttributeInfo> attributes
  ) implements AttributeInfo{}

  record Opcode(short pc, byte op, Object args) {}
  record Except(short start, short end, short handler, CpInfo exception) {}

  record Module(
    byte tag,
    CpInfo name,
    short flags,
    CpInfo version,
    Iterable<ModuleRequires> requires,
    Iterable<ModuleExports> exports,
    Iterable<ModuleOpens> opens,
    Iterable<CpInfo> uses,
    Iterable<ModuleProvides> provides
  ) implements AttributeInfo {}

  record ModuleRequires(CpInfo name, short flags, CpInfo version) {}
  record ModuleExports(CpInfo name, short flags, Iterable<CpInfo> to) {}
  record ModuleOpens(CpInfo name, short flags, Iterable<CpInfo> to) {}
  record ModuleProvides(CpInfo name, Iterable<CpInfo> with) {}

  record ModulePackages(byte tag, Iterable<CpInfo> packages) implements AttributeInfo {}
  record ModuleMainClass(byte tag, CpInfo mainClass) implements AttributeInfo {}

  record StackMapTable(byte tag, Iterable<StackMapFrame> entries) implements AttributeInfo {}

  interface StackMapFrame {
    byte type();

    record Same(byte type) implements StackMapFrame {}
    record SameLocals1StackItem(byte type, VerificationType info) implements StackMapFrame {}
    record SameLocals1StackItemExtended(byte type, short offsetDelta, VerificationType info) implements StackMapFrame {}
    record Chop(byte type, short offsetDelta) implements StackMapFrame {}
    record SameExtended(byte type, short offsetDelta) implements StackMapFrame {}
    record Append(byte type, short offsetDelta, Iterable<VerificationType> locals) implements StackMapFrame {}
    record Full(byte type, short offsetDelta, Iterable<VerificationType> locals, Iterable<VerificationType> stack) implements StackMapFrame {}
  }

  interface VerificationType {
    byte tag();

    record Top(byte tag) implements VerificationType {}
    record Integer(byte tag) implements VerificationType {}
    record Float(byte tag) implements VerificationType {}
    record Long(byte tag) implements VerificationType {}
    record Double(byte tag) implements VerificationType {}
    record Null(byte tag) implements VerificationType {}
    record Object(byte tag, short index) implements VerificationType {}
    record Uninitialized(byte tag, short offset) implements VerificationType {}
    record UninitializedThis(byte tag) implements VerificationType {}
  }

  record RuntimeVisibleAnnotations(byte tag, Iterable<Annotation> annotations) implements AttributeInfo {}
  record RuntimeInvisibleAnnotations(byte tag, Iterable<Annotation> annotations) implements AttributeInfo {}
  record RuntimeVisibleParameterAnnotations(byte tag, Iterable<ParameterAnnotation> annotations) implements AttributeInfo {}
  record RuntimeInvisibleParameterAnnotations(byte tag, Iterable<ParameterAnnotation> annotations) implements AttributeInfo {}
  record RuntimeVisibleTypeAnnotations(byte tag, Iterable<TypeAnnotation> annotations) implements AttributeInfo {}
  record RuntimeInvisibleTypeAnnotations(byte tag, Iterable<TypeAnnotation> annotations) implements AttributeInfo {}
  record AnnotationDefault(byte tag, ElementValue value) implements AttributeInfo {}

  record ParameterAnnotation(Iterable<Annotation> annotations) {}

  record TypeAnnotation(Target target, TypePath path, short index, Iterable<ElementValuePair> pairs) {}

  interface Target {
    byte type();

    record TypeParameter(byte type, byte index) implements Target {}
    record Supertype(byte type, short index) implements Target {}
    record TypeParameterBound(byte type, byte parameter, byte bound) implements Target {}
    record Empty(byte type) implements Target {}
    record FormalParameter(byte type, byte index) implements Target {}
    record Throws(byte type, short index) implements Target {}
    record Catch(byte type, short index) implements Target {}
    record Offset(byte type, short offset) implements Target {}
    record TypeArgument(byte type, short offset, byte index) implements Target {}
    record Localvars(byte type, Iterable<Localvar> table) implements Target {}
    record Localvar(short start, short length, short index) {}
  }

  record TypePath(Iterable<Part> parts) {}
  record Part(byte kind, byte index) {}

  record Annotation(CpInfo type, Iterable<ElementValuePair> pairs) {}
  record ElementValuePair(CpInfo name, ElementValue value) {}

  interface ElementValue {
    byte tag();

    record Const(byte tag, CpInfo value) implements ElementValue {}
    record EnumConst(byte tag, CpInfo type, CpInfo name) implements ElementValue {}
    record Class(byte tag, CpInfo info) implements ElementValue {}
    record Annotated(byte tag, Annotation value) implements ElementValue {}
    record Array(byte tag, Iterable<ElementValue> values) implements ElementValue {}
  }

  // to get cp_info values

  CharSequence chars(int offset, int length); // Utf8
  int int32(int offset); // Integer, Float
  long int64(int offset); // Long, Double

  // factory

  static ClassFile parse(byte[] b) {
    try {
      return (ClassFile)
        Class.forName("bc.parser.Bytecode").getConstructor(b.getClass()).newInstance((Object)b);
    } catch (Exception e) {
      throw new UnsupportedOperationException(e);
    }
  }

}
