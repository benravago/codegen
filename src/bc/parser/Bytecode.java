package bc.parser;

import static bc.Format.*;

public final class Bytecode implements bc.ClassFile {

  final byte[] b;
                                // ClassFile {
  int     magic;                //   u4             magic;
  short   minor_version;        //   u2             minor_version;
  short   major_version;        //   u2             major_version;
  short   constant_pool_count;  //   u2             constant_pool_count;
  int[]   constant_pool;        //   cp_info        constant_pool[constant_pool_count-1];
  short   access_flags;         //   u2             access_flags;
  short   this_class;           //   u2             this_class;
  short   super_class;          //   u2             super_class;
  short   interfaces_count;     //   u2             interfaces_count;
  int[]   interfaces;           //   u2             interfaces[interfaces_count];
  short   fields_count;         //   u2             fields_count;
  int[][] fields;               //   field_info     fields[fields_count];
  short   methods_count;        //   u2             methods_count;
  int[][] methods;              //   method_info    methods[methods_count];
  short   attributes_count;     //   u2             attributes_count;
  int[]   attributes;           //   attribute_info attribute_list[attributes_count];
                                // }

  final Attribute attribute;
  final CpInfo[] cache;

  public Bytecode(byte[] bytes) {
    b = bytes;

    var a = span(0,b.length);

    magic = a.u4();
    minor_version = a.u2();
    major_version = a.u2();
    constant_pool_count = a.u2();
    constant_pool = Constant.index(a,constant_pool_count);
    access_flags = a.u2();
    this_class = a.u2();
    super_class = a.u2();
    interfaces_count = a.u2();
    interfaces = interfaces(a,interfaces_count);
    fields_count = a.u2();
    fields = accessibles(a,fields_count);
    methods_count = a.u2();
    methods = accessibles(a,methods_count);
    attributes_count = a.u2();
    attributes = Attribute.index(a,attributes_count);

    assert !a.more();
    attribute = new Attribute(this);
    cache = new CpInfo[constant_pool_count];
  }

  @Override public int    magic()           { return magic; }
  @Override public short  minor()           { return minor_version; }
  @Override public short  major()           { return major_version; }
  @Override public short  constantCount()   { return constant_pool_count; }
  @Override public short  flags()           { return access_flags; }
  @Override public CpInfo type()            { return cp_info(this_class); }
  @Override public CpInfo superType()       { return cp_info(super_class); }
  @Override public short  interfacesCount() { return interfaces_count; }
  @Override public short  fieldsCount()     { return fields_count; }
  @Override public short  methodsCount()    { return methods_count; }
  @Override public short  attributesCount() { return attributes_count; }

  @Override
  public Iterable<CpInfo> constantPool() {
    return Iter.of(1, constant_pool.length, i -> cp_info(i) );
  }
  @Override
  public Iterable<CpInfo> interfaces() {
    return Iter.of(span(interfaces[0],interfaces[1]), a -> cp_info(a.u2()) );
  }
  @Override
  public Iterable<FieldInfo> fields() {
    return Iter.of(0, fields.length, i -> field_info(fields[i]) );
  }
  @Override
  public Iterable<MethodInfo> methods() {
    return Iter.of(0, methods.length, i -> method_info(methods[i]) );
  }
  @Override
  public Iterable<AttributeInfo> attributes() {
    return attribute_list(attributes);
  }

  Iterable<AttributeInfo> attribute_list(int[] a) {
    return Iter.of(1, a.length, i -> attribute.info(span(a[i-1],a[i])) );
  }

  /**
   * returns [start,end] of 'interfaces' index array
   */
  int[] interfaces(Span a, int n) {
    return new int[]{ a.p, a.p += ( 2 * n ) };
  }

  /**
   * returns array of field or method attribute index'es
   * where: i[n] is 'attribute' index array of field or method #n
   *        field or method info starts at attribute[0] - 8
   */
  int[][] accessibles(Span a, int n) {
    var y = new int[n][];
    for (var i = 0; i < n; i++) {
      a.p += 6;
      var c = a.u2();
      y[i] = Attribute.index(a,c);
    }
    return y;
  }

  /**
   * cp_info {
   *  u1 tag;
   *  u1 info[];
   * }
   */
  CpInfo cp_info(int i) {
    var c = cache[i];
    if (c == null) {
      var s = constant_pool[i-1];
      var e = constant_pool[i];
      c = cache[i] = s < e ? Constant.info(span(s,e),(short)i) : new Nil((byte)0,(short)i);
    }
    return c;
  }

  record Nil(byte tag, short index) implements CpInfo {}

  /**
   * field_info {
  u2             access_flags;
  u2             name_index;
  u2             descriptor_index;
  u2             attributes_count;
  attribute_info attribute_list[attributes_count];
 }
   */
  FieldInfo field_info(int[] attr) {
    var a = span( attr[0]-8, attr[0] );
    return new FieldInfo( a.u2(), cp_info(a.u2()), cp_info(a.u2()), attribute_list(attr) );
  }

  /**
   * method_info {
  u2             access_flags;
  u2             name_index;
  u2             descriptor_index;
  u2             attributes_count;
  attribute_info attribute_list[attributes_count];
 }
   */
  MethodInfo method_info(int[] attr) {
    var a = span( attr[0]-8, attr[0] );
    return new MethodInfo( a.u2(), cp_info(a.u2()), cp_info(a.u2()), attribute_list(attr) );
  }

  // decode parts of Bytecode buffer

  @Override public CharSequence chars(int off, int len) { return utf8(b,off,len); }
  @Override public int int32(int off) { return i32(b,off); }
  @Override public long int64(int off) { return i64(b,off); }


  // access Bytecode buffer by range

  class Span {
    private Span(int start, int end) {
      p = start; m = end;
    }

    int p;
    final int m;

    Span dup() {
      return new Span(p,m);
    }
    boolean more() {
      return p < m;
    }
    byte u1() {
      assert p <= m;
      return (byte)( b[p++] );
    }
    short u2() {
      assert p <= m-2;
      return (short)( ((b[p++] & 0x0ff) << 8) | (b[p++] & 0x0ff) );
    }
    int u4() {
      assert p <= m-4;
      return ( ((b[p++] & 0x0ff) << 24) | ((b[p++] & 0x0ff) << 16)
             | ((b[p++] & 0x0ff) <<  8) |  (b[p++] & 0x0ff) );
    }
  }

  Span span(int start, int end) { return new Span(start,end); }

}
