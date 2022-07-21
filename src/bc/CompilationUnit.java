package bc;

public interface CompilationUnit {

  // version( major_version [ . minor_version ] )
  ClassInfo Class(Object... type); // this_class [, super_class]

  interface ClassInfo {
    byte[] bytes();

    ClassInfo flags(short... flags);
    ClassInfo interfaces(String... types);

    FieldInfo Field(String name, String descriptor);
    MethodInfo Method(String name, String descriptor);

    RecordInfo  Record ();

    ClassInfo  InnerClasses ();
    ClassInfo  EnclosingMethod ();
    ClassInfo  Synthetic ();
    ClassInfo  Signature(String signature);
    ClassInfo  SourceFile(String sourcefile);
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

    CodeInfo Code();

    MethodInfo  Exceptions ();
    MethodInfo  Synthetic ();
    MethodInfo  Signature(String signature);
    MethodInfo  Deprecated ();
    MethodInfo  RuntimeVisibleAnnotations(String... annotation);
    MethodInfo  RuntimeInvisibleAnnotations ();
    MethodInfo  RuntimeVisibleParameterAnnotations ();
    MethodInfo  RuntimeInvisibleParameterAnnotations ();
    MethodInfo  RuntimeVisibleTypeAnnotations ();
    MethodInfo  RuntimeInvisibleTypeAnnotations ();
    MethodInfo  AnnotationDefault ();
    MethodInfo  MethodParameters ();
  }

  interface CodeInfo {

    CodeInfo alloc(short max_stack, short max_locals);

    Code code(); // code[] and exception_table[]

    CodeInfo  StackMapTable ();
    CodeInfo  LineNumberTable(int... tag);
    CodeInfo  LocalVariableTable(int... tag);
    CodeInfo  LocalVariableTypeTable ();
    CodeInfo  RuntimeVisibleTypeAnnotations ();
    CodeInfo  RuntimeInvisibleTypeAnnotations ();
  }

  interface Code {

    Code _nop                               (); // 00
    Code _aconst_null                       (); // 01
    Code _iconst_m1                         (); // 02
    Code _iconst_0                          (); // 03
    Code _iconst_1                          (); // 04
    Code _iconst_2                          (); // 05
    Code _iconst_3                          (); // 06
    Code _iconst_4                          (); // 07
    Code _iconst_5                          (); // 08
    Code _lconst_0                          (); // 09
    Code _lconst_1                          (); // 0a
    Code _fconst_0                          (); // 0b
    Code _fconst_1                          (); // 0c
    Code _fconst_2                          (); // 0d
    Code _dconst_0                          (); // 0e
    Code _dconst_1                          (); // 0f
    Code _bipush                      (byte b); // 10  (1)  byte
    Code _sipush                     (short s); // 11  (2)  short
    Code _ldc                     (Object...c); // 12  (1)  cp.index
    Code _ldc_w                   (Object...c); // 13  (2)  cp.index
    Code _ldc2_w                  (Object...c); // 14  (2)  cp.index
    Code _iload                        (int v); // 15  (1)  lv.index
    Code _lload                        (int v); // 16  (1)  lv.index
    Code _fload                        (int v); // 17  (1)  lv.index
    Code _dload                        (int v); // 18  (1)  lv.index
    Code _aload                        (int v); // 19  (1)  lv.index
    Code _iload_0                           (); // 1a
    Code _iload_1                           (); // 1b
    Code _iload_2                           (); // 1c
    Code _iload_3                           (); // 1d
    Code _lload_0                           (); // 1e
    Code _lload_1                           (); // 1f
    Code _lload_2                           (); // 20
    Code _lload_3                           (); // 21
    Code _fload_0                           (); // 22
    Code _fload_1                           (); // 23
    Code _fload_2                           (); // 24
    Code _fload_3                           (); // 25
    Code _dload_0                           (); // 26
    Code _dload_1                           (); // 27
    Code _dload_2                           (); // 28
    Code _dload_3                           (); // 29
    Code _aload_0                           (); // 2a
    Code _aload_1                           (); // 2b
    Code _aload_2                           (); // 2c
    Code _aload_3                           (); // 2d
    Code _iaload                            (); // 2e
    Code _laload                            (); // 2f
    Code _faload                            (); // 30
    Code _daload                            (); // 31
    Code _aaload                            (); // 32
    Code _baload                            (); // 33
    Code _caload                            (); // 34
    Code _saload                            (); // 35
    Code _istore                       (int v); // 36  (1)  lv.index
    Code _lstore                       (int v); // 37  (1)  lv.index
    Code _fstore                       (int v); // 38  (1)  lv.index
    Code _dstore                       (int v); // 39  (1)  lv.index
    Code _astore                       (int v); // 3a  (1)  lv.index
    Code _istore_0                          (); // 3b
    Code _istore_1                          (); // 3c
    Code _istore_2                          (); // 3d
    Code _istore_3                          (); // 3e
    Code _lstore_0                          (); // 3f
    Code _lstore_1                          (); // 40
    Code _lstore_2                          (); // 41
    Code _lstore_3                          (); // 42
    Code _fstore_0                          (); // 43
    Code _fstore_1                          (); // 44
    Code _fstore_2                          (); // 45
    Code _fstore_3                          (); // 46
    Code _dstore_0                          (); // 47
    Code _dstore_1                          (); // 48
    Code _dstore_2                          (); // 49
    Code _dstore_3                          (); // 4a
    Code _astore_0                          (); // 4b
    Code _astore_1                          (); // 4c
    Code _astore_2                          (); // 4d
    Code _astore_3                          (); // 4e
    Code _iastore                           (); // 4f
    Code _lastore                           (); // 50
    Code _fastore                           (); // 51
    Code _dastore                           (); // 52
    Code _aastore                           (); // 53
    Code _bastore                           (); // 54
    Code _castore                           (); // 55
    Code _sastore                           (); // 56
    Code _pop                               (); // 57
    Code _pop2                              (); // 58
    Code _dup                               (); // 59
    Code _dup_x1                            (); // 5a
    Code _dup_x2                            (); // 5b
    Code _dup2                              (); // 5c
    Code _dup2_x1                           (); // 5d
    Code _dup2_x2                           (); // 5e
    Code _swap                              (); // 5f
    Code _iadd                              (); // 60
    Code _ladd                              (); // 61
    Code _fadd                              (); // 62
    Code _dadd                              (); // 63
    Code _isub                              (); // 64
    Code _lsub                              (); // 65
    Code _fsub                              (); // 66
    Code _dsub                              (); // 67
    Code _imul                              (); // 68
    Code _lmul                              (); // 69
    Code _fmul                              (); // 6a
    Code _dmul                              (); // 6b
    Code _idiv                              (); // 6c
    Code _ldiv                              (); // 6d
    Code _fdiv                              (); // 6e
    Code _ddiv                              (); // 6f
    Code _irem                              (); // 70
    Code _lrem                              (); // 71
    Code _frem                              (); // 72
    Code _drem                              (); // 73
    Code _ineg                              (); // 74
    Code _lneg                              (); // 75
    Code _fneg                              (); // 76
    Code _dneg                              (); // 77
    Code _ishl                              (); // 78
    Code _lshl                              (); // 79
    Code _ishr                              (); // 7a
    Code _lshr                              (); // 7b
    Code _iushr                             (); // 7c
    Code _lushr                             (); // 7d
    Code _iand                              (); // 7e
    Code _land                              (); // 7f
    Code _ior                               (); // 80
    Code _lor                               (); // 81
    Code _ixor                              (); // 82
    Code _lxor                              (); // 83
    Code _iinc                 (int v, byte d); // 84  (1,1)  lv.index, const
    Code _i2l                               (); // 85
    Code _i2f                               (); // 86
    Code _i2d                               (); // 87
    Code _l2i                               (); // 88
    Code _l2f                               (); // 89
    Code _l2d                               (); // 8a
    Code _f2i                               (); // 8b
    Code _f2l                               (); // 8c
    Code _f2d                               (); // 8d
    Code _d2i                               (); // 8e
    Code _d2l                               (); // 8f
    Code _d2f                               (); // 90
    Code _i2b                               (); // 91
    Code _i2c                               (); // 92
    Code _i2s                               (); // 93
    Code _lcmp                              (); // 94
    Code _fcmpl                             (); // 95
    Code _fcmpg                             (); // 96
    Code _dcmpl                             (); // 97
    Code _dcmpg                             (); // 98
    Code _ifeq                         (int j); // 99  (2)  branch
    Code _ifne                         (int j); // 9a  (2)  branch
    Code _iflt                         (int j); // 9b  (2)  branch
    Code _ifge                         (int j); // 9c  (2)  branch
    Code _ifgt                         (int j); // 9d  (2)  branch
    Code _ifle                         (int j); // 9e  (2)  branch
    Code _if_icmpeq                    (int j); // 9f  (2)  branch
    Code _if_icmpne                    (int j); // a0  (2)  branch
    Code _if_icmplt                    (int j); // a1  (2)  branch
    Code _if_icmpge                    (int j); // a2  (2)  branch
    Code _if_icmpgt                    (int j); // a3  (2)  branch
    Code _if_icmple                    (int j); // a4  (2)  branch
    Code _if_acmpeq                    (int j); // a5  (2)  branch
    Code _if_acmpne                    (int j); // a6  (2)  branch
    Code _goto                         (int j); // a7  (2)  branch
    Code _jsr                          (int j); // a8  (2)  branch
    Code _ret                          (int v); // a9  (1)  lv.index
    Code _tableswitch                (int...a); // aa  (0-3,4,4,4,...) padding, default, low, high, jump offsets
    Code _lookupswitch               (int...a); // ab  (0-3,4,4,...)  padding, default, npairs, match/offset pairs
    Code _ireturn                           (); // ac
    Code _lreturn                           (); // ad
    Code _freturn                           (); // ae
    Code _dreturn                           (); // af
    Code _areturn                           (); // b0
    Code _return                            (); // b1
    Code _getstatic               (Object...c); // b2  (2)  cp.index
    Code _putstatic               (Object...c); // b3  (2)  cp.index
    Code _getfield                (Object...c); // b4  (2)  cp.index
    Code _putfield                (Object...c); // b5  (2)  cp.index
    Code _invokevirtual           (Object...c); // b6  (2)  cp.index
    Code _invokespecial           (Object...c); // b7  (2)  cp.index
    Code _invokestatic            (Object...c); // b8  (2)  cp.index
    Code _invokeinterface (byte d, Object...c); // b9  (2,1,0)  cp.index, count, 0
    Code _invokedynamic           (Object...c); // ba  (2,0,0)  cp.index, 0, 0
    Code _new                     (Object...c); // bb  (2)  cp.index
    Code _newarray                    (byte t); // bc  (1)  atype
    Code _anewarray               (Object...c); // bd  (2)  cp.index
    Code _arraylength                       (); // be
    Code _athrow                            (); // bf
    Code _checkcast               (Object...c); // c0  (2)  cp.index
    Code _instanceof              (Object...c); // c1  (2)  cp.index
    Code _monitorenter                      (); // c2
    Code _monitorexit                       (); // c3
    Code _wide               (byte w, int...a); // c4  (1,2 | 1,2,2)  opcode, lv.index | iinc, lv.index, count
    Code _multianewarray  (byte d, Object...c); // c5  (2,1)  cp.index, dimensions
    Code _ifnull                       (int j); // c6  (2)  branch
    Code _ifnonnull                    (int j); // c7  (2)  branch
    Code _goto_w                       (int j); // c8  (4)  branch
    Code _jsr_w                        (int j); // c9  (4)  branch
    Code _breakpoint                        (); // ca
    Code _impdep1                           (); // fe
    Code _impdep2                           (); // ff

    Code $(int tag); // line number and/or jump offset label

    Code $var(int tag, String name, Object... info); // descriptor, signature
    Code $try(int tag, Object type);

    Code $start(int... tag);
    Code $end(int... tag);
  }

  interface RecordInfo {
    RecordInfo  Signature ();
    RecordInfo  RuntimeVisibleAnnotations ();
    RecordInfo  RuntimeInvisibleAnnotations ();
    RecordInfo  RuntimeVisibleTypeAnnotations ();
    RecordInfo  RuntimeInvisibleTypeAnnotations ();
  }

  static CompilationUnit build(double v) { // major_version[.minor_version]
    try {
      return (CompilationUnit)
        Class.forName("bc.builder.Bytecode").getConstructor(Double.TYPE).newInstance(v);
    } catch (Exception e) {
      throw new UnsupportedOperationException(e);
    }
  }

}
