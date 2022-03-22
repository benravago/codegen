package bc;

public interface ClassFile {

  int                     magic();
  short                   minor();
  short                   major();
  short                   constantCount();
  Iterable<CP.Info>       constantPool();
  short                   flags();
  CP.Info                 type();
  CP.Info                 superType();
  short                   interfacesCount();
  Iterable<CP.Info>       interfaces();
  short                   fieldsCount();
  Iterable<FieldInfo>     fields();
  short                   methodsCount();
  Iterable<MethodInfo>    methods();
  short                   attributesCount();
  Iterable<AttributeInfo> attributes();

  // JVMS structures

  interface CP {

    interface Info { byte tag(); short index(); }

    record Utf8(byte tag, short index, short offset, short length) implements Info {}

    record Integer(byte tag, short index, short offset) implements Info {}
    record Float(byte tag, short index, short offset) implements Info {}
    record Long(byte tag, short index, short offset) implements Info {}
    record Double(byte tag, short index, short offset) implements Info {}

    record String(byte tag, short index, short string) implements Info {}

    record Class(byte tag, short index, short name) implements Info {}
    record Module(byte tag, short index, short name) implements Info {}
    record Package(byte tag, short index, short name) implements Info {}

    record MethodType(byte tag, short index, short descriptor) implements Info {}

    record NameAndType(byte tag, short index, short name, short descriptor) implements Info {}

    record Field(byte tag, short index, short classRef, short namedType) implements Info {}
    record Method(byte tag, short index, short classRef, short namedType) implements Info {}
    record InterfaceMethod(byte tag, short index, short classRef, short namedType) implements Info {}

    record MethodHandle(byte tag, short index, byte kind, short reference) implements Info {}

    record Dynamic(byte tag, short index, short bootstrapMethod, short namedType) implements Info {}
    record InvokeDynamic(byte tag, short index, short bootstrapMethod, short namedType) implements Info {}
  }

  record FieldInfo(short flags, CP.Info name, CP.Info descriptor, Iterable<AttributeInfo> attributes) {}
  record MethodInfo(short flags, CP.Info name, CP.Info descriptor, Iterable<AttributeInfo> attributes) {}

  interface AttributeInfo { byte tag(); }
  interface Iteration<T> extends Iterable<T> { int size(); }

  record ConstantValue(byte tag, Object value) implements AttributeInfo {}
  record Exceptions(byte tag, Iterable<CP.Info> types) implements AttributeInfo {}
  record EnclosingMethod(byte tag, CP.Info enclosingClass, CP.Info type) implements AttributeInfo {}
  record Synthetic(byte tag) implements AttributeInfo {}
  record Signature(byte tag, CP.Info signature) implements AttributeInfo {}
  record SourceFile(byte tag, CP.Info sourcefile) implements AttributeInfo {}
  record SourceDebugExtension(byte tag, byte[] debugExtension) implements AttributeInfo {}
  record Deprecated(byte tag) implements AttributeInfo {}
  record NestHost(byte tag, CP.Info hostClass) implements AttributeInfo {}
  record NestMembers(byte tag, Iterable<CP.Info> members) implements AttributeInfo {}
  record PermittedSubclasses(byte tag, Iterable<CP.Info> subclasses) implements AttributeInfo {}

  record LineNumberTable(byte tag, Iterable<LineNumber> lines) implements AttributeInfo {}
  record LineNumber(short pc, short n) {}

  record InnerClasses(byte tag, Iterable<InnerClass> types) implements AttributeInfo {}
  record InnerClass(CP.Info info, CP.Info outerClass, CP.Info name, short flags) {}

  record LocalVariableTable(byte tag, Iterable<LocalVariable> locals) implements AttributeInfo {}
  record LocalVariable(short pc, short len, CP.Info name, CP.Info descriptor, short index) {}

  record LocalVariableTypeTable(byte tag, Iterable<LocalVariableType> types) implements AttributeInfo {}
  record LocalVariableType(short pc, short len, CP.Info name, CP.Info signature, short index) {}

  record BootstrapMethods(byte tag, Iterable<BootstrapMethod> methods) implements AttributeInfo {}
  record BootstrapMethod(CP.Info ref, Iterable<CP.Info> arguments) {}

  record MethodParameters(byte tag, Iterable<MethodParameter> parameters) implements AttributeInfo {}
  record MethodParameter(CP.Info name, short flags) {}

  record Record(byte tag, Iterable<RecordComponent> components) implements AttributeInfo {}
  record RecordComponent(CP.Info name, CP.Info descriptor, Iterable<AttributeInfo> attributes) {}

  record Code(
    byte tag,
    short maxStack,
    short maxLocals,
    Iterable<Opcode> codes,
    Iterable<Except> exceptions,
    Iterable<AttributeInfo> attributes
  ) implements AttributeInfo{}

  record Opcode(short pc, byte op, Object args) {}
  record Except(short start, short end, short handler, CP.Info exception) {}

  record Module(
    byte tag,
    CP.Info name,
    short flags,
    CP.Info version,
    Iterable<ModuleRequires> requires,
    Iterable<ModuleExports> exports,
    Iterable<ModuleOpens> opens,
    Iterable<CP.Info> uses,
    Iterable<ModuleProvides> provides
  ) implements AttributeInfo {}

  record ModuleRequires(CP.Info name, short flags, CP.Info version) {}
  record ModuleExports(CP.Info name, short flags, Iterable<CP.Info> to) {}
  record ModuleOpens(CP.Info name, short flags, Iterable<CP.Info> to) {}
  record ModuleProvides(CP.Info name, Iterable<CP.Info> with) {}

  record ModulePackages(byte tag, Iterable<CP.Info> packages) implements AttributeInfo {}
  record ModuleMainClass(byte tag, CP.Info mainClass) implements AttributeInfo {}

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

  record Annotation(CP.Info type, Iterable<ElementValuePair> pairs) {}
  record ElementValuePair(CP.Info name, ElementValue value) {}

  interface ElementValue {
    byte tag();

    record Const(byte tag, CP.Info value) implements ElementValue {}
    record EnumConst(byte tag, CP.Info type, CP.Info name) implements ElementValue {}
    record Class(byte tag, CP.Info info) implements ElementValue {}
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
