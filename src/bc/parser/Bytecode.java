package bc.parser;

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
  int[]   attributes;           //   attribute_info attributes[attributes_count];
                                // }

  Constant cp;
  Attribute attribute;

  public Bytecode(byte[] bytes) {
    b = bytes;

    var a = span(0,b.length);

    magic = a.u4();
    minor_version = a.u2();
    major_version = a.u2();
    constant_pool_count = a.u2();
    constant_pool = constant_pool(a,constant_pool_count);
    access_flags = a.u2();
    this_class = a.u2();
    super_class = a.u2();
    interfaces_count = a.u2();
    interfaces = interfaces(a,interfaces_count);
    fields_count = a.u2();
    fields = accessible(a,fields_count);
    methods_count = a.u2();
    methods = accessible(a,methods_count);
    attributes_count = a.u2();
    attributes = attributes(a,attributes_count);

    assert !a.more();
    cp = new Constant(this);
    attribute = new Attribute(this);
  }

  @Override public int   magic()            { return magic; }
  @Override public short minor()            { return minor_version; }
  @Override public short major()            { return major_version; }
  @Override public short constantCount()    { return constant_pool_count; }
  @Override public short modifiers()        { return access_flags; }
  @Override public CharSequence type()      { return cp.info(this_class); }
  @Override public CharSequence superType() { return cp.info(super_class); }
  @Override public short interfacesCount()  { return interfaces_count; }
  @Override public short fieldsCount()      { return fields_count; }
  @Override public short methodsCount()     { return methods_count; }
  @Override public short attributesCount()  { return attributes_count; }

  @Override public <T> T constant(int i)    { return cp.info(i); }

  @Override
  public Iterable<CPInfo> constantPool() {
    return Iter.of(1, constant_pool.length, i -> cp_info(i) );
  }
  @Override
  public Iterable<CharSequence> interfaces() {
    return Iter.of(span( interfaces[0], interfaces[0] ), a -> cp.info(a.u2()) );
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

  /**
   * returns index of 'constant_pool' section
   * where: i[0] is start of 'constant_pool'
   *        i[n] is end of 'constant_pool' item #n
   */
  int[] constant_pool(Span a, int n) {
    return Constant.pool_index(a,n);
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
  int[][] accessible(Span a, int n) {
    var y = new int[n][];
    for (var i = 0; i < n; i++) {
      a.p += 6;
      var c = a.u2();
      y[i] = attributes(a,c);
    }
    return y;
  }

  /**
   * returns 'attribute' index
   * where: i[0] is start of 'attribute' section
   *        i[n] is end of 'attribute' #n-1
   */
  int[] attributes(Span a, int n) {
    var z = new int[++n];
    z[0] = a.p;
    for (var i = 1; i < n; i++) {
      a.p += 2;
      a.p += a.u4() + 4; // tricky !!
      z[i] = a.p;
    }
    return z;
  }

  /**
   * cp_info {
   *  u1 tag;
   *  u1 info[];
   * }
   */
  CPInfo cp_info(int index) {
    var t = constant_pool[index-1];
    return t < constant_pool[index]
      ? new CPInfo( (byte)b[t], index, cp.info(index) )
      : new CPInfo( (byte)0, index, null );
  }

  /**
   * field_info {
   *  u2             access_flags;
   *  u2             name_index;
   *  u2             descriptor_index;
   *  u2             attributes_count;
   *  attribute_info attributes[attributes_count];
   * }
   */
  FieldInfo field_info(int[] attr) {
    var a = span( attr[0]-8, attr[0] );
    return new FieldInfo( a.u2(), cp.info(a.u2()), cp.info(a.u2()), attribute_list(attr) );
  }

  /**
   * method_info {
   *  u2             access_flags;
   *  u2             name_index;
   *  u2             descriptor_index;
   *  u2             attributes_count;
   *  attribute_info attributes[attributes_count];
   * }
   */
  MethodInfo method_info(int[] attr) {
    var a = span( attr[0]-8, attr[0] );
    return new MethodInfo( a.u2(), cp.info(a.u2()), cp.info(a.u2()), attribute_list(attr) );
  }

  Iterable<AttributeInfo> attribute_list(int[] a) {
    return Iter.of(1, a.length, i -> attribute.info(span(a[i-1],a[i])) );
  }

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
