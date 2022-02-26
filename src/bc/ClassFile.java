package bc;

public interface ClassFile { // is also Attributed

  int                     magic();
  short                   minor();
  short                   major();
  short                   constantCount();
  Iterable<CPInfo>        constantPool();
  short                   modifiers();
  CharSequence            type();
  CharSequence            superType();
  short                   interfacesCount();
  Iterable<CharSequence>  interfaces();
  short                   fieldsCount();
  Iterable<FieldInfo>     fields();
  short                   methodsCount();
  Iterable<MethodInfo>    methods();
  short                   attributesCount();
  Iterable<AttributeInfo> attributes();

  // for constant_pool lookups
  <T> T constant(int index);
  // for constant_pool compound objects
  record Accessible(CharSequence name, CharSequence descriptor) {}
  record Variable(CharSequence enclosing, Accessible element) {}
  record Dynamic(int bootstrapMethod, Accessible element) {}
  record Reference(byte kind, Variable reference) {}

  // JVMS structures

  record CPInfo(byte tag, int index, Object info) {}

  record FieldInfo(short flags, CharSequence name, CharSequence descriptor, Iterable<AttributeInfo> attributes) {}
  record MethodInfo(short flags, CharSequence name, CharSequence descriptor, Iterable<AttributeInfo> attributes) {}

  interface AttributeInfo { byte tag(); }
  interface Iteration<T> extends Iterable<T> { int size(); }

  record ConstantValue(byte tag, Object value) implements AttributeInfo {}
  record Exceptions(byte tag, Iterable<CharSequence> types) implements AttributeInfo {}
  record EnclosingMethod(byte tag, CharSequence enclosingClass, CharSequence type) implements AttributeInfo {}
  record Synthetic(byte tag) implements AttributeInfo {}
  record Signature(byte tag, CharSequence signature) implements AttributeInfo {}
  record SourceFile(byte tag, CharSequence sourcefile) implements AttributeInfo {}
  record SourceDebugExtension(byte tag, byte[] debugExtension) implements AttributeInfo {}
  record Deprecated(byte tag) implements AttributeInfo {}
  record NestHost(byte tag, CharSequence hostClass) implements AttributeInfo {}
  record NestMembers(byte tag, Iterable<CharSequence> members) implements AttributeInfo {}
  record PermittedSubclasses(byte tag, Iterable<CharSequence> subclasses) implements AttributeInfo {}

  record LineNumberTable(byte tag, Iterable<LineNumber> lines) implements AttributeInfo {}
  record LineNumber(short pc, short n) {}

  record InnerClasses(byte tag, Iterable<InnerClass> types) implements AttributeInfo {}
  record InnerClass(CharSequence info, CharSequence outerClass, CharSequence name, short flags) {}

  record LocalVariableTable(byte tag, Iterable<LocalVariable> locals) implements AttributeInfo {}
  record LocalVariable(short pc, short len, CharSequence name, CharSequence descriptor, short index) {}

  record LocalVariableTypeTable(byte tag, Iterable<LocalVariableType> types) implements AttributeInfo {}
  record LocalVariableType(short pc, short len, CharSequence name, CharSequence signature, short index) {}

  record BootstrapMethods(byte tag, Iterable<BootstrapMethod> methods) implements AttributeInfo {}
  record BootstrapMethod(CharSequence ref, Iterable<CharSequence> arguments) {}

  record MethodParameters(byte tag, Iterable<MethodParameter> parameters) implements AttributeInfo {}
  record MethodParameter(CharSequence name, short flags) {}

  record Record(byte tag, Iterable<RecordComponent> components) implements AttributeInfo {}
  record RecordComponent(CharSequence name, CharSequence descriptor, Iterable<AttributeInfo> attributes) {}

  record Code(
    byte tag,
    short maxStack,
    short maxLocals,
    Iterable<Opcode> codes,
    Iterable<Except> exceptions,
    Iterable<AttributeInfo> attributes
  ) implements AttributeInfo{}

  record Opcode(short pc, byte op, Object args) {}
  record Except(short start, short end, short handler, CharSequence exception) {}

  record Module(
    byte tag,
    CharSequence name,
    short flags,
    CharSequence version,
    Iterable<ModuleRequires> requires,
    Iterable<ModuleExports> exports,
    Iterable<ModuleOpens> opens,
    Iterable<CharSequence> uses,
    Iterable<ModuleProvides> provides
  ) implements AttributeInfo {}

  record ModuleRequires(CharSequence name, short flags, CharSequence version) {}
  record ModuleExports(CharSequence name, short flags, Iterable<CharSequence> to) {}
  record ModuleOpens(CharSequence name, short flags, Iterable<CharSequence> to) {}
  record ModuleProvides(CharSequence name, Iterable<CharSequence> with) {}

  record ModulePackages(byte tag, Iterable<CharSequence> packages) implements AttributeInfo {}
  record ModuleMainClass(byte tag, CharSequence mainClass) implements AttributeInfo {}

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

  record Annotation(CharSequence type, Iterable<ElementValuePair> pairs) {}
  record ElementValuePair(CharSequence name, ElementValue value) {}

  interface ElementValue {
    byte tag();

    record Const(byte tag, CharSequence value) implements ElementValue {}
    record EnumConst(byte tag, CharSequence type, CharSequence name) implements ElementValue {}
    record Class(byte tag, CharSequence info) implements ElementValue {}
    record Annotated(byte tag, Annotation value) implements ElementValue {}
    record Array(byte tag, Iterable<ElementValue> values) implements ElementValue {}
  }

  static ClassFile parse(byte[] b) {
    try {
      return (ClassFile)
        Class.forName("bc.parser.Bytecode").getConstructor(b.getClass()).newInstance((Object)b);
    } catch (Exception e) {
      throw new UnsupportedOperationException(e);
    }
  }

}
