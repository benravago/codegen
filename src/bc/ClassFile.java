package bc;

public interface ClassFile {

  int                     magic();
  short                   minor();
  short                   major();
  short                   constantCount();
  Iterable<CP.info>       constants();
  short                   flags();
  CP.info                 type();
  CP.info                 superType();
  short                   interfacesCount();
  Iterable<CP.info>       interfaces();
  short                   fieldsCount();
  Iterable<FieldInfo>     fields();
  short                   methodsCount();
  Iterable<MethodInfo>    methods();
  short                   attributesCount();
  Iterable<AttributeInfo> attributes();

  // constant pool

  interface CP {

    interface info { byte tag(); short index(); }
    interface name extends info { short name(); }
    interface value extends info { short offset(); }
    interface ref extends info { short classRef(); short namedType(); }
    interface func extends info { short bootstrapMethod(); short namedType(); }

    record Utf8(byte tag, short index, short offset, short length) implements value {}

    record Integer(byte tag, short index, short offset) implements value {}
    record Float(byte tag, short index, short offset) implements value {}
    record Long(byte tag, short index, short offset) implements value {}
    record Double(byte tag, short index, short offset) implements value {}

    record String(byte tag, short index, short string) implements info {}

    record Class(byte tag, short index, short name) implements name {}
    record Module(byte tag, short index, short name) implements name {}
    record Package(byte tag, short index, short name) implements name {}

    record MethodType(byte tag, short index, short descriptor) implements info {}

    record NameAndType(byte tag, short index, short name, short descriptor) implements name {}

    record Field(byte tag, short index, short classRef, short namedType) implements ref {}
    record Method(byte tag, short index, short classRef, short namedType) implements ref {}
    record InterfaceMethod(byte tag, short index, short classRef, short namedType) implements ref {}

    record MethodHandle(byte tag, short index, byte kind, short reference) implements info {}

    record Dynamic(byte tag, short index, short bootstrapMethod, short namedType) implements func {}
    record InvokeDynamic(byte tag, short index, short bootstrapMethod, short namedType) implements func {}
  }

  // to get cp_info values

  CharSequence chars(int offset, int length); // Utf8
  int int32(int offset); // Integer, Float
  long int64(int offset); // Long, Double

  // class members

  record FieldInfo(short flags, CP.info name, CP.info descriptor, Iterable<AttributeInfo> attributes) {}
  record MethodInfo(short flags, CP.info name, CP.info descriptor, Iterable<AttributeInfo> attributes) {}

  interface AttributeInfo { byte tag(); }
  interface Iteration<T> extends Iterable<T> { int size(); }

  record ConstantValue(byte tag, Object value) implements AttributeInfo {}
  record Exceptions(byte tag, Iterable<CP.info> types) implements AttributeInfo {}
  record EnclosingMethod(byte tag, CP.info enclosingClass, CP.info type) implements AttributeInfo {}
  record Synthetic(byte tag) implements AttributeInfo {}
  record Signature(byte tag, CP.info signature) implements AttributeInfo {}
  record SourceFile(byte tag, CP.info sourcefile) implements AttributeInfo {}
  record SourceDebugExtension(byte tag, byte[] debugExtension) implements AttributeInfo {}
  record Deprecated(byte tag) implements AttributeInfo {}
  record NestHost(byte tag, CP.info hostClass) implements AttributeInfo {}
  record NestMembers(byte tag, Iterable<CP.info> members) implements AttributeInfo {}
  record PermittedSubclasses(byte tag, Iterable<CP.info> subclasses) implements AttributeInfo {}

  record LineNumberTable(byte tag, Iterable<LineNumber> lines) implements AttributeInfo {}
  record LineNumber(short pc, short n) {}

  record InnerClasses(byte tag, Iterable<InnerClass> types) implements AttributeInfo {}
  record InnerClass(CP.info info, CP.info outerClass, CP.info name, short flags) {}

  record LocalVariableTable(byte tag, Iterable<LocalVariable> locals) implements AttributeInfo {}
  record LocalVariable(short pc, short len, CP.info name, CP.info descriptor, short index) {}

  record LocalVariableTypeTable(byte tag, Iterable<LocalVariableType> types) implements AttributeInfo {}
  record LocalVariableType(short pc, short len, CP.info name, CP.info signature, short index) {}

  record BootstrapMethods(byte tag, Iterable<BootstrapMethod> methods) implements AttributeInfo {}
  record BootstrapMethod(CP.info ref, Iterable<CP.info> arguments) {}

  record MethodParameters(byte tag, Iterable<MethodParameter> parameters) implements AttributeInfo {}
  record MethodParameter(CP.info name, short flags) {}

  record Record(byte tag, Iterable<RecordComponent> components) implements AttributeInfo {}
  record RecordComponent(CP.info name, CP.info descriptor, Iterable<AttributeInfo> attributes) {}

  record Code(
    byte tag,
    short maxStack,
    short maxLocals,
    Iterable<Opcode> codes,
    Iterable<Except> exceptions,
    Iterable<AttributeInfo> attributes
  ) implements AttributeInfo{}

  record Opcode(short pc, byte op, Object args) {}
  record Except(short start, short end, short handler, CP.info catchType) {}

  record Module(
    byte tag,
    CP.info name,
    short flags,
    CP.info version,
    Iterable<ModuleRequires> requires,
    Iterable<ModuleExports> exports,
    Iterable<ModuleOpens> opens,
    Iterable<CP.info> uses,
    Iterable<ModuleProvides> provides
  ) implements AttributeInfo {}

  record ModuleRequires(CP.info name, short flags, CP.info version) {}
  record ModuleExports(CP.info name, short flags, Iterable<CP.info> to) {}
  record ModuleOpens(CP.info name, short flags, Iterable<CP.info> to) {}
  record ModuleProvides(CP.info name, Iterable<CP.info> with) {}

  record ModulePackages(byte tag, Iterable<CP.info> packages) implements AttributeInfo {}
  record ModuleMainClass(byte tag, CP.info mainClass) implements AttributeInfo {}

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

  record Annotation(CP.info type, Iterable<ElementValuePair> pairs) {}
  record ElementValuePair(CP.info name, ElementValue value) {}

  interface ElementValue {
    byte tag();

    record Const(byte tag, CP.info value) implements ElementValue {}
    record EnumConst(byte tag, CP.info type, CP.info name) implements ElementValue {}
    record Class(byte tag, CP.info info) implements ElementValue {}
    record Annotated(byte tag, Annotation value) implements ElementValue {}
    record Array(byte tag, Iterable<ElementValue> values) implements ElementValue {}
  }

  static ClassFile from(byte[] b) {
    return new bc.decoder.Bytecode(b);
  }

}
