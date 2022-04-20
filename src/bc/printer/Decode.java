package bc.printer;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import java.util.Formatter;
import java.util.function.Consumer;
import java.util.function.IntFunction;

import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    cf = bc.Bytecode.parse(b);
    init();       // magic, minor_version, major_version
    values();     // constant_pool_count, constants[constant_pool_count-1]
    class_();     // access_flags, this_class, super_class
    interfaces(); // interfaces_count, interfaces[interfaces_count]
    fields();     // fields_count, fields[fields_count]
    methods();    // methods_count, methods[methods_count]
    attributes(); // attributes_count, attributes[attributes_count]
  }

  void init() {
    assert cf.magic() == 0xcafebabe;
    f("Bytecode.build(%d.%d)\n", cf.major(), cf.minor() );
  }

  void class_() {
    f("  Class(%s, %s)\n", cp(cf.type()), cp(cf.superType()) );
    f("    flags(0x%04x)\n", cf.flags() );
  }

  Object[] V;
  CP.info[] W;

  void values() {
    var n = cf.constantCount();
    V = new Object[n];
    W = new CP.info[n];
    for (var w:cf.constants()) {
      n = w.index();
      W[n] = w;
      if (w instanceof CP.value v) {
        V[n] = switch(v.tag()) {
          case CONSTANT_Utf8 -> cf.chars(v.offset(),((CP.Utf8)v).length());
          case CONSTANT_Integer -> cf.int32(v.offset());
          case CONSTANT_Float -> Float.intBitsToFloat(cf.int32(v.offset()));
          case CONSTANT_Long -> cf.int64(v.offset());
          case CONSTANT_Double -> Double.longBitsToDouble(cf.int64(v.offset()));
          default -> null; // should not occur
        };
      }
    }
  }

  void interfaces() {
    var n = cf.interfacesCount();
    if (n < 1) {
      f("# %d interfaces\n", n);
    } else {
      f("  interfaces() #%d\n", n);
      g(cf.interfaces(), i -> f("    add(%s)\n", cp(i)) );
    }
  }

  void fields() { // # 4.5
    f("# %d fields\n", cf.fieldsCount());
    for (var d:cf.fields()) {
      f("  Field(%s, %s)\n", cp(d.name()), cp(d.descriptor()));
      f("    flags(0x%04x)\n", d.flags() );
      q(d.attributes(), this::attribute);
    }
  }

  void methods() { // # 4.6
    f("# %d methods\n", cf.methodsCount());
    for (var d:cf.methods()) {
      f("  Method(%s, %s)\n", cp(d.name()), cp(d.descriptor()));
      f("    flags(0x%04x)\n", d.flags() );
      q(d.attributes(), this::attribute);
    }
  }

  void attributes() { // # 4.7
    f("# %d attributes\n", cf.attributesCount());
    g(cf.attributes(), this::attribute);
  }

  void attribute(AttributeInfo a) {
    switch (a.tag()) {
        // ATTRIBUTE_ConstantValue -> ed((ConstantValue)a);
      case ATTRIBUTE_Code -> ed((Code)a);
      case ATTRIBUTE_StackMapTable -> ed((StackMapTable)a);
        // ATTRIBUTE_Exceptions -> ed((Exceptions)a);
      case ATTRIBUTE_InnerClasses -> ed((InnerClasses)a);
      case ATTRIBUTE_EnclosingMethod -> ed((EnclosingMethod)a);
        // ATTRIBUTE_Synthetic -> ed((Synthetic)a);
      case ATTRIBUTE_Signature -> ed((Signature)a);
      case ATTRIBUTE_SourceFile -> ed((SourceFile)a);
        // ATTRIBUTE_SourceDebugExtension -> ed((SourceDebugExtension)a);
      case ATTRIBUTE_LineNumberTable -> ed((LineNumberTable)a);
      case ATTRIBUTE_LocalVariableTable -> ed((LocalVariableTable)a);
      case ATTRIBUTE_LocalVariableTypeTable -> ed((LocalVariableTypeTable)a);
        // ATTRIBUTE_Deprecated -> ed((Deprecated)a);
      case ATTRIBUTE_RuntimeVisibleAnnotations -> ed((RuntimeVisibleAnnotations)a);
        // ATTRIBUTE_RuntimeInvisibleAnnotations -> ed((RuntimeInvisibleAnnotations)a);
        // ATTRIBUTE_RuntimeVisibleParameterAnnotations -> ed((RuntimeVisibleParameterAnnotations)a);
        // ATTRIBUTE_RuntimeInvisibleParameterAnnotations -> ed((RuntimeInvisibleParameterAnnotations)a);
        // ATTRIBUTE_RuntimeVisibleTypeAnnotations -> ed((RuntimeVisibleTypeAnnotations)a);
        // ATTRIBUTE_RuntimeInvisibleTypeAnnotations -> ed((RuntimeInvisibleTypeAnnotations)a);
        // ATTRIBUTE_AnnotationDefault -> ed((AnnotationDefault)a);
        // ATTRIBUTE_BootstrapMethods -> ed((BootstrapMethods)a);
      case ATTRIBUTE_MethodParameters -> ed((MethodParameters)a);
        // ATTRIBUTE_Module -> ed((Module)a);
        // ATTRIBUTE_ModulePackages -> ed((ModulePackages)a);
        // ATTRIBUTE_ModuleMainClass -> ed((ModuleMainClass)a);
      case ATTRIBUTE_NestHost -> ed((NestHost)a);
      case ATTRIBUTE_NestMembers -> ed((NestMembers)a);
        // ATTRIBUTE_Record -> ed((Record)a);
        // ATTRIBUTE_PermittedSubclasses -> ed((PermittedSubclasses)a);

      default -> f("--> a %s\n", a);
    }
  }

  // void ed(ConstantValue a) { d("%s TODO: %s\n", P, a); } # 4.7.2

  void ed(Code c) { // # 4.7.3
    f("%s Code()\n", P);
    p(+2);
    locals();
    attributes(c);
    variables();
    exceptions(c);
    instructions(c);
    code();
    f("%s ## max stack=%d, locals=%d \n", P, c.maxStack(), c.maxLocals() );
    p(-2);
  }

  void locals() {
    lines = new ArrayList<>();
    defs = new HashMap<>();
    undefs = new HashMap<>();
  }

  List<line> lines;
  record line(short offset, byte weight, CharSequence text) {}
  void line(short o, byte w, CharSequence t) { lines.add(new line(o,w,t)); }

  static final byte
    LABEL = 0,                       // LineNumberTable
    START = 2, END = 7, HANDLE = 8,  // Code.exception_table
    VAR = 4, DROP = 5,               // LocalVariableTable, LocalVariableTypeTable
    INSTRUCTION = 10;                // Code.code

  void code() {
    lines.sort((a,b) -> (
      a.offset < b.offset ? -1 : a.offset > b.offset ? +1 :
      a.weight < b.weight ? -1 : a.weight > b.weight ? +1 : 0
    ));
    f("%s code()\n", P);
    q(lines, l -> f("%s %s\n", P, l.text) );
  }

  Operation op = new Operation(this::constantPool, this::localVariable, this::codeOffset);

  void instructions(Code c) {
    for (var o:c.codes()) {
      line(o.pc(), INSTRUCTION, " "+op.format(o));
    }
  }

  void exceptions(Code c) {
    for (var e:c.exceptions()) {
      var start = e.start();
      var i = -start;
      line(start, START, "$start("+i+')');
      line(e.end(), END, "$end("+i+')');
      line(e.handler(), HANDLE, "$handle("+i+", "+cp(e.catchType())+')');
    }
  }

  void variables() {
    for (var e:defs.entrySet()) {
      var index = e.getKey();
      var v = e.getValue();
      var pc = v.pc;
      line(pc, VAR, "$var(%d, %s, %s, %s) # %04x:%04x"
        .formatted(index, cp(v.name), cp(v.descriptor), cp(v.signature), pc, pc+v.len) );
    }
    for (var e:undefs.entrySet()) {
      var pc = e.getKey();
      var v = e.getValue();
      v.setLength(v.length()-1);
      line(pc, DROP, "$drop("+v+')');
    }
  }

  void attributes(Code c) {
    c.attributes().forEach(this::attribute);
  }

  void ed(StackMapTable a) { // # 4.7.4
    f("%s # stack map table\n", P);
    q(a.entries(), e -> f("%s # %s\n", P, e) );
  }

  // void ed(Exceptions a) { d("%s TODO: %s\n", P, a); } # 4.7.5

  void ed(InnerClasses a) { // # 4.7.6
    f("%s InnerClasses()\n", P);
    q(a.types(), i -> f("%s add(%s, 0x%04x) // %s %s\n", P, cp(i.info()), i.flags(), cp(i.outerClass()), cp(i.name()) ));
  }

  void ed(EnclosingMethod a) { // # 4.7.7
    f("%s EnclosingMethod(%s, %s)\n", P, cp(a.enclosingClass()), cp(a.type()) );
  }

  // void ed(Synthetic a) { d("%s TODO: %s\n", P, a); } # 4.7.8

  void ed(Signature a) { // # 4.7.9
    f("%s Signature(%s)\n", P, cp(a.signature()));
  }

  void ed(SourceFile a) { // # 4.7.10
    f("%s SourceFile(%s)\n", P, cp(a.sourcefile()));
  }

  // void ed(SourceDebugExtension a) { d("%s TODO: %s\n", P, a); } # 4.7.11

  void ed(LineNumberTable t) { // # 4.7.12
    for (var i:t.lines()) {
      line(i.pc(), LABEL, "$(%d) # %04x".formatted(i.n(),i.pc()) );
    }
  }

  String codeOffset(int jm) {
    var offset = "0x%04x".formatted(jm);
    line((short)jm, LABEL, "$("+offset+')' );
    return offset;
  }

  void ed(LocalVariableTable a) { // # 4.7.13
    for (var v:a.locals()) {
      var k = v.index();
      var e = defs.get(k);
      if (e == null) {
        e = define(k,v.pc(),v.len(),v.name());
      } else {
        assert defined(e,v.pc(),v.len(),v.name());
      }
      e.descriptor = v.descriptor();
    }
  }

  void ed(LocalVariableTypeTable a) { // # 4.7.14
    for (var v:a.types()) {
      var k = v.index();
      var e = defs.get(k);
      if (e == null) {
        e = define(k,v.pc(),v.len(),v.name());
      } else {
        assert defined(e,v.pc(),v.len(),v.name());
      }
      e.signature = v.signature();
    }
  }

  class def { short pc, len; CP.info name, descriptor, signature; }
  Map<Short,def> defs;
  Map<Short,StringBuilder> undefs;

  def define(short index, short pc, short len, CP.info name) {
    var v = new def();
    v.pc = pc;
    v.len = len;
    v.name = name;
    defs.put(index,v);
    undefine((short)(pc+len),index);
    return v;
  }

  boolean defined(def v, short pc, short len, CP.info name) {
    return v.pc == pc && v.len == len && v.name == name;
  }

  void undefine(short offset, short index) {
    var v = undefs.get(offset);
    if (v == null) {
      v = new StringBuilder();
      undefs.put(offset,v);
    }
    v.append(index).append(',');
  }

  String localVariable(int lv) {
    var v = defs.get((short)lv);
    return v != null ? cp(v.name) : "&"+lv;
  }



  // void ed(Deprecated a) { d("%s TODO: %s\n", P, a); } # 4.7.15

  void ed(RuntimeVisibleAnnotations a) { // # 4.7.16
    f("%s TODO: %s\n", P, a);
  }

  // void ed(RuntimeInvisibleAnnotations a) { d("%s TODO: %s\n", P, a); } # 4.7.17

  // void ed(RuntimeVisibleParameterAnnotations a) { d("%s TODO: %s\n", P, a); } # 4.7.18

  // void ed(RuntimeInvisibleParameterAnnotations a) { d("%s TODO: %s\n", P, a); } # 4.7.19

  // void ed(RuntimeVisibleTypeAnnotations a) { d("%s TODO: %s\n", P, a); } # 4.7.20

  // void ed(RuntimeInvisibleTypeAnnotations a) { d("%s TODO: %s\n", P, a); } # 4.7.21

  // void ed(AnnotationDefault a) { d("%s TODO: %s\n", P, a); } # 4.7.22

  // void ed(BootstrapMethods a) { d("%s TODO: %s\n", P, a); } # 4.7.23

  void ed(MethodParameters a) { // # 4.7.24
    f("%s MethodParameters()\n", P);
    q(a.parameters(), e -> f("%s add(%s, 0x%04x)\n", P, cp(e.name()), e.flags() ));
  }

  // void ed(Module a) { d("%s TODO: %s\n", P, a); } # 4.7.25

  // void ed(ModulePackages a) { d("%s TODO: %s\n", P, a); } # 4.7.26

  // void ed(ModuleMainClass a) { d("%s TODO: %s\n", P, a); } # 4.7.27

  void ed(NestHost a) { // # 4.7.28
    f("%s NestHost(%s)\n", P, cp(a.hostClass()));
  }

  void ed(NestMembers a) { // # 4.7.29
    f("%s NestMembers()\n", P);
    q(a.members(), m -> f("%s add(%s)\n", P, cp(m))  );
  }

  // void ed(Record a) { d("%s TODO: %s\n", P, a); } # 4.7.30

  // void ed(PermittedSubclasses a) { d("%s TODO: %s\n", P, a); } # 4.7.31

  String P = " ";

  void p(int n) { P = "                     ".substring(0,P.length()+n); }
  <T> void q(Iterable<T> i, Consumer<T> c) { p(+2); for (var t:i) c.accept(t); p(-2); }

  void f(String format, Object... args) { out.format(format,args); }
  <T> void g(Iterable<T> i, Consumer<T> c) { for (var t:i) c.accept(t); }

  String dq(short i) { return "\"" + V[i] + '"'; }

  String cp(CP.info i) {
    return i == null ? "null" : switch (i.tag()) {

      case CONSTANT_Utf8 // 1
        -> dq( i.index() );

      case CONSTANT_Integer, CONSTANT_Float, CONSTANT_Long, CONSTANT_Double 	// 3, 4, 5, 6
        -> String.valueOf(V[i.index()]);

      case CONSTANT_Class, CONSTANT_Module, CONSTANT_Package // 7, 19, 20
        -> dq( ((CP.name)i).name() );

      case CONSTANT_String // 8
        -> dq( ((CP.String)i).string() );

      case CONSTANT_Fieldref, CONSTANT_Methodref, CONSTANT_InterfaceMethodref // 9, 10, 11
        -> {
          var r = (CP.ref) i;
          var c = (CP.Class) W[r.classRef()];
          var n = (CP.NameAndType) W[r.namedType()];
          yield "\"" + V[c.name()] + '.' + V[n.name()] + ':' + V[n.descriptor()] + '"';
        }

      case CONSTANT_NameAndType // 12
        -> {
          var n = (CP.NameAndType) i;
          yield "\"" + V[n.name()] + ':' + V[n.descriptor()] + '"';
        }

      case CONSTANT_MethodHandle // 15
        -> "#"+i.index()+"/"+i.tag(); // TODO:

      case CONSTANT_MethodType // 16
        -> dq( ((CP.MethodType)i).descriptor() );

      case CONSTANT_Dynamic, CONSTANT_InvokeDynamic // 17, 18
        -> "#"+i.index()+"/"+i.tag(); // TODO:

      default -> throw new UnsupportedOperationException(i.toString());
    };
  }

  String constantPool(int ci) { return cp(W[ci]); }

  class Operation extends Instruction {

    Operation(IntFunction<CharSequence> constantPool, IntFunction<CharSequence> localVariable, IntFunction<CharSequence> codeOffset) {
      cp = constantPool; lv = localVariable; jm = codeOffset;
      s = new StringBuilder();
      t = new Formatter(s);
    }

    final StringBuilder s;

    CharSequence format(Opcode opcode) {
      o = opcode;
      s.setLength(0);
      edit();
      return s;
    }

    @Override void i_        () { t( "_%s()"         , n()                   ); }
    @Override void i_1b      () { t( "_%s(%d)"       , n(), u1()             ); }
    @Override void i_2s      () { t( "_%s(%d)"       , n(), u2()             ); }
    @Override void i_1c      () { t( "_%s(%s)"       , n(), cp(u1())         ); }
    @Override void i_2c      () { t( "_%s(%s)"       , n(), cp(u2())         ); }
    @Override void i_2c_1d   () { t( "_%s(%s,%d)"    , n(), cp(u2(0)), u1(1) ); }
    @Override void i_1v      () { t( "_%s(%s)"       , n(), lv(u1())         ); }
    @Override void i_1v_1d   () { t( "_%s(%s,%d)"    , n(), lv(u1(0)), u1(1) ); }
    @Override void i_1t      () { t( "_%s(+%d)"      , n(), u1()             ); }
    @Override void i_2j      () { t( "_%s(%s)"       , n(), jm(u2())         ); }
    @Override void i_4j      () { t( "_%s(%s)"       , n(), jm(u4())         ); }
    @Override void i_2c_1d_0 () { t( "_%s(%s,%d)"    , n(), cp(u2(0)), u1(1) ); }
    @Override void i_2c_0_0  () { t( "_%s(%s)"       , n(), cp(u2(0))        ); }

    @Override void i_p_4d_4d_4d_x() {
      var v = (Integer[])o.args();
      t.format("%04x  %s  %d,", o.pc(), n(), v[0] ); // pc, op, padding
      for (int i = 1, m = v.length; i < m; i++) {
        t.format( " 0x%08x,", v[i] ); // default, low, high, jump offsets
      }
      s.setLength(s.length()-1); // remove trailing ','
    }

    @Override void i_p_4d_4d_x() {
      var v = (Integer[])o.args();
      t("%04x  %s  %d, 0x%08x, %d,", o.pc(), n(), v[0], v[1], v[2] ); // pc, op, padding, default, npairs
      for (int i = 3, m = v.length; i < m;) {
        t( " 0x%08x, 0x%08x,", v[i++], v[i++] ); // match/offset pairs
      }
      s.setLength(s.length()-1); // remove trailing ','
    }

    @Override void i_1w_2c_x() {
      var i = u1(0);
      if (i == OP_iinc) {
        t("%04x  %s  %s, %s, %d", o.pc(), n(), "iinc", cp(u2(1)), u2(2) );
      } else {
        t("%04x  %s  %s, %s", o.pc(), n(), wide_op(i), cp(u2(1)) );
      }
    }

    CharSequence cp(int i) { return cp.apply(i); }
    CharSequence lv(int i) { return lv.apply(i); }
    CharSequence jm(int i) { return jm.apply(i+o.pc()); }

    final IntFunction<CharSequence> cp;
    final IntFunction<CharSequence> lv;
    final IntFunction<CharSequence> jm;
  }

}
