package bc;

import java.lang.constant.Constable;
import static bc.spec.JVMS.*;

public interface CompilationUnit {

  ClassInfo Class (Object... type); // this_class [, super_class]

  interface ClassInfo {

    ClassInfo flags (ACC... flags);
    ClassInfo interfaces (String... types);

    FieldInfo Field (String name, String descriptor);
    MethodInfo Method (String name, String descriptor);

    RecordInfo  Record ();

    ClassInfo  InnerClasses ();
    ClassInfo  EnclosingMethod ();
    ClassInfo  Synthetic ();
    ClassInfo  Signature (String signature);
    ClassInfo  SourceFile (String sourcefile);
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

    FieldInfo flags (ACC... flags);

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

    MethodInfo flags (ACC... flags);

    CodeInfo Code ();

    MethodInfo  Exceptions ();
    MethodInfo  Synthetic ();
    MethodInfo  Signature (String signature);
    MethodInfo  Deprecated ();
    MethodInfo  RuntimeVisibleAnnotations (String... annotation);
    MethodInfo  RuntimeInvisibleAnnotations ();
    MethodInfo  RuntimeVisibleParameterAnnotations ();
    MethodInfo  RuntimeInvisibleParameterAnnotations ();
    MethodInfo  RuntimeVisibleTypeAnnotations ();
    MethodInfo  RuntimeInvisibleTypeAnnotations ();
    MethodInfo  AnnotationDefault ();
    MethodInfo  MethodParameters ();
  }

  interface CodeInfo {

    CodeInfo alloc (short max_stack, short max_locals);

    Code code (); // code[] and exception_table[]

    CodeInfo  StackMapTable ();
    CodeInfo  LineNumberTable (int... tag);
    CodeInfo  LocalVariableTable (int... tag);
    CodeInfo  LocalVariableTypeTable ();
    CodeInfo  RuntimeVisibleTypeAnnotations ();
    CodeInfo  RuntimeInvisibleTypeAnnotations ();
  }

  interface Code {

    Code  nop                                (); // 00
    Code  aconst_null                        (); // 01
    Code  iconst_m1                          (); // 02
    Code  iconst_0                           (); // 03
    Code  iconst_1                           (); // 04
    Code  iconst_2                           (); // 05
    Code  iconst_3                           (); // 06
    Code  iconst_4                           (); // 07
    Code  iconst_5                           (); // 08
    Code  lconst_0                           (); // 09
    Code  lconst_1                           (); // 0a
    Code  fconst_0                           (); // 0b
    Code  fconst_1                           (); // 0c
    Code  fconst_2                           (); // 0d
    Code  dconst_0                           (); // 0e
    Code  dconst_1                           (); // 0f
    Code  bipush                       (byte b); // 10  (1)  byte
    Code  sipush                      (short s); // 11  (2)  short
    Code  ldc                     (Constable c); // 12  (1)  cp.index
    Code  ldc_w                   (Constable c); // 13  (2)  cp.index
    Code  ldc2_w                  (Constable c); // 14  (2)  cp.index
    Code  iload                      (String v); // 15  (1)  lv.index
    Code  lload                      (String v); // 16  (1)  lv.index
    Code  fload                      (String v); // 17  (1)  lv.index
    Code  dload                      (String v); // 18  (1)  lv.index
    Code  aload                      (String v); // 19  (1)  lv.index
    Code  iload_0                            (); // 1a
    Code  iload_1                            (); // 1b
    Code  iload_2                            (); // 1c
    Code  iload_3                            (); // 1d
    Code  lload_0                            (); // 1e
    Code  lload_1                            (); // 1f
    Code  lload_2                            (); // 20
    Code  lload_3                            (); // 21
    Code  fload_0                            (); // 22
    Code  fload_1                            (); // 23
    Code  fload_2                            (); // 24
    Code  fload_3                            (); // 25
    Code  dload_0                            (); // 26
    Code  dload_1                            (); // 27
    Code  dload_2                            (); // 28
    Code  dload_3                            (); // 29
    Code  aload_0                            (); // 2a
    Code  aload_1                            (); // 2b
    Code  aload_2                            (); // 2c
    Code  aload_3                            (); // 2d
    Code  iaload                             (); // 2e
    Code  laload                             (); // 2f
    Code  faload                             (); // 30
    Code  daload                             (); // 31
    Code  aaload                             (); // 32
    Code  baload                             (); // 33
    Code  caload                             (); // 34
    Code  saload                             (); // 35
    Code  istore                     (String v); // 36  (1)  lv.index
    Code  lstore                     (String v); // 37  (1)  lv.index
    Code  fstore                     (String v); // 38  (1)  lv.index
    Code  dstore                     (String v); // 39  (1)  lv.index
    Code  astore                     (String v); // 3a  (1)  lv.index
    Code  istore_0                           (); // 3b
    Code  istore_1                           (); // 3c
    Code  istore_2                           (); // 3d
    Code  istore_3                           (); // 3e
    Code  lstore_0                           (); // 3f
    Code  lstore_1                           (); // 40
    Code  lstore_2                           (); // 41
    Code  lstore_3                           (); // 42
    Code  fstore_0                           (); // 43
    Code  fstore_1                           (); // 44
    Code  fstore_2                           (); // 45
    Code  fstore_3                           (); // 46
    Code  dstore_0                           (); // 47
    Code  dstore_1                           (); // 48
    Code  dstore_2                           (); // 49
    Code  dstore_3                           (); // 4a
    Code  astore_0                           (); // 4b
    Code  astore_1                           (); // 4c
    Code  astore_2                           (); // 4d
    Code  astore_3                           (); // 4e
    Code  iastore                            (); // 4f
    Code  lastore                            (); // 50
    Code  fastore                            (); // 51
    Code  dastore                            (); // 52
    Code  aastore                            (); // 53
    Code  bastore                            (); // 54
    Code  castore                            (); // 55
    Code  sastore                            (); // 56
    Code  pop                                (); // 57
    Code  pop2                               (); // 58
    Code  dup                                (); // 59
    Code  dup_x1                             (); // 5a
    Code  dup_x2                             (); // 5b
    Code  dup2                               (); // 5c
    Code  dup2_x1                            (); // 5d
    Code  dup2_x2                            (); // 5e
    Code  swap                               (); // 5f
    Code  iadd                               (); // 60
    Code  ladd                               (); // 61
    Code  fadd                               (); // 62
    Code  dadd                               (); // 63
    Code  isub                               (); // 64
    Code  lsub                               (); // 65
    Code  fsub                               (); // 66
    Code  dsub                               (); // 67
    Code  imul                               (); // 68
    Code  lmul                               (); // 69
    Code  fmul                               (); // 6a
    Code  dmul                               (); // 6b
    Code  idiv                               (); // 6c
    Code  ldiv                               (); // 6d
    Code  fdiv                               (); // 6e
    Code  ddiv                               (); // 6f
    Code  irem                               (); // 70
    Code  lrem                               (); // 71
    Code  frem                               (); // 72
    Code  drem                               (); // 73
    Code  ineg                               (); // 74
    Code  lneg                               (); // 75
    Code  fneg                               (); // 76
    Code  dneg                               (); // 77
    Code  ishl                               (); // 78
    Code  lshl                               (); // 79
    Code  ishr                               (); // 7a
    Code  lshr                               (); // 7b
    Code  iushr                              (); // 7c
    Code  lushr                              (); // 7d
    Code  iand                               (); // 7e
    Code  land                               (); // 7f
    Code  ior                                (); // 80
    Code  lor                                (); // 81
    Code  ixor                               (); // 82
    Code  lxor                               (); // 83
    Code  iinc               (String v, byte d); // 84  (1,1)  lv.index, const
    Code  i2l                                (); // 85
    Code  i2f                                (); // 86
    Code  i2d                                (); // 87
    Code  l2i                                (); // 88
    Code  l2f                                (); // 89
    Code  l2d                                (); // 8a
    Code  f2i                                (); // 8b
    Code  f2l                                (); // 8c
    Code  f2d                                (); // 8d
    Code  d2i                                (); // 8e
    Code  d2l                                (); // 8f
    Code  d2f                                (); // 90
    Code  i2b                                (); // 91
    Code  i2c                                (); // 92
    Code  i2s                                (); // 93
    Code  lcmp                               (); // 94
    Code  fcmpl                              (); // 95
    Code  fcmpg                              (); // 96
    Code  dcmpl                              (); // 97
    Code  dcmpg                              (); // 98
    Code  ifeq                       (String j); // 99  (2)  branch
    Code  ifne                       (String j); // 9a  (2)  branch
    Code  iflt                       (String j); // 9b  (2)  branch
    Code  ifge                       (String j); // 9c  (2)  branch
    Code  ifgt                       (String j); // 9d  (2)  branch
    Code  ifle                       (String j); // 9e  (2)  branch
    Code  if_icmpeq                  (String j); // 9f  (2)  branch
    Code  if_icmpne                  (String j); // a0  (2)  branch
    Code  if_icmplt                  (String j); // a1  (2)  branch
    Code  if_icmpge                  (String j); // a2  (2)  branch
    Code  if_icmpgt                  (String j); // a3  (2)  branch
    Code  if_icmple                  (String j); // a4  (2)  branch
    Code  if_acmpeq                  (String j); // a5  (2)  branch
    Code  if_acmpne                  (String j); // a6  (2)  branch
    Code  goto_v                     (String j); // a7  (2)  branch
    Code  jsr_v                      (String j); // a8  (2)  branch
    Code  ret                        (String v); // a9  (1)  lv.index
    Code  tableswitch              (String...j); // aa  (0-3,4,4,4,...) padding, default, low, high, jump offsets
    Code  lookupswitch             (Object...p); // ab  (0-3,4,4,...)  padding, default, npairs, match/offset pairs
    Code  ireturn                            (); // ac
    Code  lreturn                            (); // ad
    Code  freturn                            (); // ae
    Code  dreturn                            (); // af
    Code  areturn                            (); // b0
    Code  vreturn                            (); // b1
    Code  getstatic               (Constable c); // b2  (2)  cp.index
    Code  putstatic               (Constable c); // b3  (2)  cp.index
    Code  getfield                (Constable c); // b4  (2)  cp.index
    Code  putfield                (Constable c); // b5  (2)  cp.index
    Code  invokevirtual           (Constable c); // b6  (2)  cp.index
    Code  invokespecial           (Constable c); // b7  (2)  cp.index
    Code  invokestatic            (Constable c); // b8  (2)  cp.index
    Code  invokeinterface (Constable c, byte d); // b9  (2,1,0)  cp.index, count, 0
    Code  invokedynamic           (Constable c); // ba  (2,0,0)  cp.index, 0, 0
    Code  anew                    (Constable c); // bb  (2)  cp.index
    Code  newarray                       (AT t); // bc  (1)  atype
    Code  anewarray               (Constable c); // bd  (2)  cp.index
    Code  arraylength                        (); // be
    Code  athrow                             (); // bf
    Code  checkcast               (Constable c); // c0  (2)  cp.index
    Code  instance_of             (Constable c); // c1  (2)  cp.index
    Code  monitorenter                       (); // c2
    Code  monitorexit                        (); // c3
    Code  wide    (WIDE w, String v, short...d); // c4  (1,2 | 1,2,2)  opcode, lv.index | iinc, lv.index, count
    Code  multianewarray  (Constable c, byte d); // c5  (2,1)  cp.index, dimensions
    Code  ifnull                     (String j); // c6  (2)  branch
    Code  ifnonnull                  (String j); // c7  (2)  branch
    Code  goto_w                     (String j); // c8  (4)  branch
    Code  jsr_w                      (String j); // c9  (4)  branch
    Code  breakpoint                         (); // ca
    Code  impdep1                            (); // fe
    Code  impdep2                            (); // ff

    Code  $ (String tag);

    Code  $try (String tag);
    Code  $end (String tag);
    Code  $catch (String tag, Constable... exception);

    Code  $var (Constable def);
  }

  interface RecordInfo {
    RecordInfo  Signature ();
    RecordInfo  RuntimeVisibleAnnotations ();
    RecordInfo  RuntimeInvisibleAnnotations ();
    RecordInfo  RuntimeVisibleTypeAnnotations ();
    RecordInfo  RuntimeInvisibleTypeAnnotations ();
  }

  static CompilationUnit of (double v) { // major_version[.minor_version]
    return new bc.encoder.Bytecode (v);
  }

  byte[] bytes ();
  // version version ();
  // cp_info constantPool ()

}
