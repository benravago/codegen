package bc.spec;

public interface JVMS {

  // Table 4.1-B. Class access and property modifiers
  // Table 4.5-A. Field access and property flags
  // Table 4.6-A. Method access and property flags

  static final short
    ACC_PUBLIC         = 0x0001 ,  // c f m
    ACC_PRIVATE        = 0x0002 ,  //   f m
    ACC_PROTECTED      = 0x0004 ,  //   f m
    ACC_STATIC         = 0x0008 ,  //   f m
    ACC_FINAL          = 0x0010 ,  // c f m
    ACC_SUPER          = 0x0020 ,  // c
    ACC_SYNCHRONIZED   = 0x0020 ,  //     m
    ACC_VOLATILE       = 0x0040 ,  //   f
    ACC_BRIDGE         = 0x0040 ,  //     m
    ACC_TRANSIENT      = 0x0080 ,  //   f
    ACC_VARARGS        = 0x0080 ,  //     m
    ACC_NATIVE         = 0x0100 ,  //     m
    ACC_INTERFACE      = 0x0200 ,  // c
    ACC_ABSTRACT       = 0x0400 ,  // c   m
    ACC_STRICT         = 0x0800 ,  //     m
    ACC_SYNTHETIC      = 0x1000 ,  // c f m
    ACC_ANNOTATION     = 0x2000 ,  // c
    ACC_ENUM           = 0x4000 ,  // c f
    ACC_MODULE = (short) 0x8000 ;  // c

  enum ACC {
    PUBLIC       (ACC_PUBLIC),
    PRIVATE      (ACC_PRIVATE),
    PROTECTED    (ACC_PROTECTED),
    STATIC       (ACC_STATIC),
    FINAL        (ACC_FINAL),
    SUPER        (ACC_SUPER),
    SYNCHRONIZED (ACC_SYNCHRONIZED),
    VOLATILE     (ACC_VOLATILE),
    BRIDGE       (ACC_BRIDGE),
    TRANSIENT    (ACC_TRANSIENT),
    VARARGS      (ACC_VARARGS),
    NATIVE       (ACC_NATIVE),
    INTERFACE    (ACC_INTERFACE),
    ABSTRACT     (ACC_ABSTRACT),
    STRICT       (ACC_STRICT),
    SYNTHETIC    (ACC_SYNTHETIC),
    ANNOTATION   (ACC_ANNOTATION),
    ENUM         (ACC_ENUM),
    MODULE       (ACC_MODULE);

    ACC(short b) { bits=b; }
    public final short bits;
  }


  // 4.4. The Constant Pool           // Table 4.4 - Constant pool tags
                                      //  section version java-se loadable
  static final byte
    CONSTANT_Utf8               =  1, //   4.4.7    45.3   1.0.2
    CONSTANT_Integer            =  3, //   4.4.4    45.3   1.0.2   y
    CONSTANT_Float              =  4, //   4.4.4    45.3   1.0.2   y
    CONSTANT_Long               =  5, //   4.4.5    45.3   1.0.2   y
    CONSTANT_Double             =  6, //   4.4.5    45.3   1.0.2   y
    CONSTANT_Class              =  7, //   4.4.1    45.3   1.0.2   y
    CONSTANT_String             =  8, //   4.4.3    45.3   1.0.2   y
    CONSTANT_Fieldref           =  9, //   4.4.2    45.3   1.0.2
    CONSTANT_Methodref          = 10, //   4.4.2    45.3   1.0.2
    CONSTANT_InterfaceMethodref = 11, //   4.4.2    45.3   1.0.2
    CONSTANT_NameAndType        = 12, //   4.4.6    45.3   1.0.2
    CONSTANT_MethodHandle       = 15, //   4.4.8    51.0   7       y
    CONSTANT_MethodType         = 16, //   4.4.9    51.0   7       y
    CONSTANT_Dynamic            = 17, //   4.4.10   55.0   11      y
    CONSTANT_InvokeDynamic      = 18, //   4.4.10   51.0   7
    CONSTANT_Module             = 19, //   4.4.11   53.0   9
    CONSTANT_Package            = 20; //   4.4.12   53.0   9


  // 4.4.8. The CONSTANT_MethodHandle_info Structure

  final static byte // for Reference.kind
    REF_getField         = 1,
    REF_getStatic        = 2,
    REF_putField         = 3,
    REF_putStatic        = 4,
    REF_invokeVirtual    = 5,
    REF_invokeStatic     = 6,
    REF_invokeSpecial    = 7,
    REF_newInvokeSpecial = 8,
    REF_invokeInterface  = 9;


  // Table 4.7 Predefined class file attributes
                                                         // section version java-se  -location-
  static final byte
    ATTRIBUTE_ConstantValue                        =  2, //  4.7.2    45.3   1.0.2             field_info
    ATTRIBUTE_Code                                 =  3, //  4.7.3    45.3   1.0.2                        method_info
    ATTRIBUTE_StackMapTable                        =  4, //  4.7.4    50.0   6                                        Code
    ATTRIBUTE_Exceptions                           =  5, //  4.7.5    45.3   1.0.2                        method_info
    ATTRIBUTE_InnerClasses                         =  6, //  4.7.6    45.3   1.1     ClassFile
    ATTRIBUTE_EnclosingMethod                      =  7, //  4.7.7    49.0   5.0     ClassFile
    ATTRIBUTE_Synthetic                            =  8, //  4.7.8    45.3   1.1     ClassFile field_info method_info
    ATTRIBUTE_Signature                            =  9, //  4.7.9    49.0   5.0     ClassFile field_info method_info      record_component_info
    ATTRIBUTE_SourceFile                           = 10, //  4.7.10   45.3   1.0.2   ClassFile
    ATTRIBUTE_SourceDebugExtension                 = 11, //  4.7.11   49.0   5.0     ClassFile
    ATTRIBUTE_LineNumberTable                      = 12, //  4.7.12   45.3   1.0.2                                    Code
    ATTRIBUTE_LocalVariableTable                   = 13, //  4.7.13   45.3   1.0.2                                    Code
    ATTRIBUTE_LocalVariableTypeTable               = 14, //  4.7.14   49.0   5.0                                      Code
    ATTRIBUTE_Deprecated                           = 15, //  4.7.15   45.3   1.1     ClassFile field_info method_info
    ATTRIBUTE_RuntimeVisibleAnnotations            = 16, //  4.7.16   49.0   5.0     ClassFile field_info method_info      record_component_info
    ATTRIBUTE_RuntimeInvisibleAnnotations          = 17, //  4.7.17   49.0   5.0     ClassFile field_info method_info      record_component_info
    ATTRIBUTE_RuntimeVisibleParameterAnnotations   = 18, //  4.7.18   49.0   5.0                          method_info
    ATTRIBUTE_RuntimeInvisibleParameterAnnotations = 19, //  4.7.19   49.0   5.0                          method_info
    ATTRIBUTE_RuntimeVisibleTypeAnnotations        = 20, //  4.7.20   52.0   8       ClassFile field_info method_info Code record_component_info
    ATTRIBUTE_RuntimeInvisibleTypeAnnotations      = 21, //  4.7.21   52.0   8       ClassFile field_info method_info Code record_component_info
    ATTRIBUTE_AnnotationDefault                    = 22, //  4.7.22   49.0   5.0                          method_info
    ATTRIBUTE_BootstrapMethods                     = 23, //  4.7.23   51.0   7       ClassFile
    ATTRIBUTE_MethodParameters                     = 24, //  4.7.24   52.0   8                            method_info
    ATTRIBUTE_Module                               = 25, //  4.7.25   53.0   9       ClassFile
    ATTRIBUTE_ModulePackages                       = 26, //  4.7.26   53.0   9       ClassFile
    ATTRIBUTE_ModuleMainClass                      = 27, //  4.7.27   53.0   9       ClassFile
    ATTRIBUTE_NestHost                             = 28, //  4.7.28   55.0   11      ClassFile
    ATTRIBUTE_NestMembers                          = 29, //  4.7.29   55.0   11      ClassFile
    ATTRIBUTE_Record                               = 30, //  4.7.30   60.0   16      ClassFile
    ATTRIBUTE_PermittedSubclasses                  = 31; //  4.7.31   61.0   17      ClassFile


  // Chapter 7. Opcode Mnemonics by Opcode

  final static byte
                      //  byte -> dec hex  - args -
    //  Constants

    OP_nop             =    0, //   0  00
    OP_aconst_null     =    1, //   1  01
    OP_iconst_m1       =    2, //   2  02
    OP_iconst_0        =    3, //   3  03
    OP_iconst_1        =    4, //   4  04
    OP_iconst_2        =    5, //   5  05
    OP_iconst_3        =    6, //   6  06
    OP_iconst_4        =    7, //   7  07
    OP_iconst_5        =    8, //   8  08
    OP_lconst_0        =    9, //   9  09
    OP_lconst_1        =   10, //  10  0a
    OP_fconst_0        =   11, //  11  0b
    OP_fconst_1        =   12, //  12  0c
    OP_fconst_2        =   13, //  13  0d
    OP_dconst_0        =   14, //  14  0e
    OP_dconst_1        =   15, //  15  0f
    OP_bipush          =   16, //  16  10  (1)  byte
    OP_sipush          =   17, //  17  11  (2)  short
    OP_ldc             =   18, //  18  12  (1)  cp.index
    OP_ldc_w           =   19, //  19  13  (2)  cp.index
    OP_ldc2_w          =   20, //  20  14  (2)  cp.index

    // Loads

    OP_iload           =   21, //  21  15  (1)  lv.index
    OP_lload           =   22, //  22  16  (1)  lv.index
    OP_fload           =   23, //  23  17  (1)  lv.index
    OP_dload           =   24, //  24  18  (1)  lv.index
    OP_aload           =   25, //  25  19  (1)  lv.index
    OP_iload_0         =   26, //  26  1a
    OP_iload_1         =   27, //  27  1b
    OP_iload_2         =   28, //  28  1c
    OP_iload_3         =   29, //  29  1d
    OP_lload_0         =   30, //  30  1e
    OP_lload_1         =   31, //  31  1f
    OP_lload_2         =   32, //  32  20
    OP_lload_3         =   33, //  33  21
    OP_fload_0         =   34, //  34  22
    OP_fload_1         =   35, //  35  23
    OP_fload_2         =   36, //  36  24
    OP_fload_3         =   37, //  37  25
    OP_dload_0         =   38, //  38  26
    OP_dload_1         =   39, //  39  27
    OP_dload_2         =   40, //  40  28
    OP_dload_3         =   41, //  41  29
    OP_aload_0         =   42, //  42  2a
    OP_aload_1         =   43, //  43  2b
    OP_aload_2         =   44, //  44  2c
    OP_aload_3         =   45, //  45  2d
    OP_iaload          =   46, //  46  2e
    OP_laload          =   47, //  47  2f
    OP_faload          =   48, //  48  30
    OP_daload          =   49, //  49  31
    OP_aaload          =   50, //  50  32
    OP_baload          =   51, //  51  33
    OP_caload          =   52, //  52  34
    OP_saload          =   53, //  53  35

    // Stores

    OP_istore          =   54, //  54  36  (1)  lv.index
    OP_lstore          =   55, //  55  37  (1)  lv.index
    OP_fstore          =   56, //  56  38  (1)  lv.index
    OP_dstore          =   57, //  57  39  (1)  lv.index
    OP_astore          =   58, //  58  3a  (1)  lv.index
    OP_istore_0        =   59, //  59  3b
    OP_istore_1        =   60, //  60  3c
    OP_istore_2        =   61, //  61  3d
    OP_istore_3        =   62, //  62  3e
    OP_lstore_0        =   63, //  63  3f
    OP_lstore_1        =   64, //  64  40
    OP_lstore_2        =   65, //  65  41
    OP_lstore_3        =   66, //  66  42
    OP_fstore_0        =   67, //  67  43
    OP_fstore_1        =   68, //  68  44
    OP_fstore_2        =   69, //  69  45
    OP_fstore_3        =   70, //  70  46
    OP_dstore_0        =   71, //  71  47
    OP_dstore_1        =   72, //  72  48
    OP_dstore_2        =   73, //  73  49
    OP_dstore_3        =   74, //  74  4a
    OP_astore_0        =   75, //  75  4b
    OP_astore_1        =   76, //  76  4c
    OP_astore_2        =   77, //  77  4d
    OP_astore_3        =   78, //  78  4e
    OP_iastore         =   79, //  79  4f
    OP_lastore         =   80, //  80  50
    OP_fastore         =   81, //  81  51
    OP_dastore         =   82, //  82  52
    OP_aastore         =   83, //  83  53
    OP_bastore         =   84, //  84  54
    OP_castore         =   85, //  85  55
    OP_sastore         =   86, //  86  56

    // Stack

    OP_pop             =   87, //  87  57
    OP_pop2            =   88, //  88  58
    OP_dup             =   89, //  89  59
    OP_dup_x1          =   90, //  90  5a
    OP_dup_x2          =   91, //  91  5b
    OP_dup2            =   92, //  92  5c
    OP_dup2_x1         =   93, //  93  5d
    OP_dup2_x2         =   94, //  94  5e
    OP_swap            =   95, //  95  5f

    // Math

    OP_iadd            =   96, //  96  60
    OP_ladd            =   97, //  97  61
    OP_fadd            =   98, //  98  62
    OP_dadd            =   99, //  99  63
    OP_isub            =  100, // 100  64
    OP_lsub            =  101, // 101  65
    OP_fsub            =  102, // 102  66
    OP_dsub            =  103, // 103  67
    OP_imul            =  104, // 104  68
    OP_lmul            =  105, // 105  69
    OP_fmul            =  106, // 106  6a
    OP_dmul            =  107, // 107  6b
    OP_idiv            =  108, // 108  6c
    OP_ldiv            =  109, // 109  6d
    OP_fdiv            =  110, // 110  6e
    OP_ddiv            =  111, // 111  6f
    OP_irem            =  112, // 112  70
    OP_lrem            =  113, // 113  71
    OP_frem            =  114, // 114  72
    OP_drem            =  115, // 115  73
    OP_ineg            =  116, // 116  74
    OP_lneg            =  117, // 117  75
    OP_fneg            =  118, // 118  76
    OP_dneg            =  119, // 119  77
    OP_ishl            =  120, // 120  78
    OP_lshl            =  121, // 121  79
    OP_ishr            =  122, // 122  7a
    OP_lshr            =  123, // 123  7b
    OP_iushr           =  124, // 124  7c
    OP_lushr           =  125, // 125  7d
    OP_iand            =  126, // 126  7e
    OP_land            =  127, // 127  7f
    OP_ior             = -128, // 128  80
    OP_lor             = -127, // 129  81
    OP_ixor            = -126, // 130  82
    OP_lxor            = -125, // 131  83
    OP_iinc            = -124, // 132  84  (1,1)  lv.index, const

    // Conversions

    OP_i2l             = -123, // 133  85
    OP_i2f             = -122, // 134  86
    OP_i2d             = -121, // 135  87
    OP_l2i             = -120, // 136  88
    OP_l2f             = -119, // 137  89
    OP_l2d             = -118, // 138  8a
    OP_f2i             = -117, // 139  8b
    OP_f2l             = -116, // 140  8c
    OP_f2d             = -115, // 141  8d
    OP_d2i             = -114, // 142  8e
    OP_d2l             = -113, // 143  8f
    OP_d2f             = -112, // 144  90
    OP_i2b             = -111, // 145  91
    OP_i2c             = -110, // 146  92
    OP_i2s             = -109, // 147  93

    // Comparisons

    OP_lcmp            = -108, // 148  94
    OP_fcmpl           = -107, // 149  95
    OP_fcmpg           = -106, // 150  96
    OP_dcmpl           = -105, // 151  97
    OP_dcmpg           = -104, // 152  98
    OP_ifeq            = -103, // 153  99  (2)  branch
    OP_ifne            = -102, // 154  9a  (2)  branch
    OP_iflt            = -101, // 155  9b  (2)  branch
    OP_ifge            = -100, // 156  9c  (2)  branch
    OP_ifgt            = -99 , // 157  9d  (2)  branch
    OP_ifle            = -98 , // 158  9e  (2)  branch
    OP_if_icmpeq       = -97 , // 159  9f  (2)  branch
    OP_if_icmpne       = -96 , // 160  a0  (2)  branch
    OP_if_icmplt       = -95 , // 161  a1  (2)  branch
    OP_if_icmpge       = -94 , // 162  a2  (2)  branch
    OP_if_icmpgt       = -93 , // 163  a3  (2)  branch
    OP_if_icmple       = -92 , // 164  a4  (2)  branch
    OP_if_acmpeq       = -91 , // 165  a5  (2)  branch
    OP_if_acmpne       = -90 , // 166  a6  (2)  branch

    // Control

    OP_goto            = -89 , // 167  a7  (2)  branch
    OP_jsr             = -88 , // 168  a8  (2)  branch
    OP_ret             = -87 , // 169  a9  (1)  lv.index
    OP_tableswitch     = -86 , // 170  aa  (0-3,4,4,4,...)  padding, default, low, high, jump offsets
    OP_lookupswitch    = -85 , // 171  ab  (0-3,4,4,...)  padding, default, npairs, match/offset pairs
    OP_ireturn         = -84 , // 172  ac
    OP_lreturn         = -83 , // 173  ad
    OP_freturn         = -82 , // 174  ae
    OP_dreturn         = -81 , // 175  af
    OP_areturn         = -80 , // 176  b0
    OP_return          = -79 , // 177  b1

    // References

    OP_getstatic       = -78 , // 178  b2  (2)  cp.index
    OP_putstatic       = -77 , // 179  b3  (2)  cp.index
    OP_getfield        = -76 , // 180  b4  (2)  cp.index
    OP_putfield        = -75 , // 181  b5  (2)  cp.index
    OP_invokevirtual   = -74 , // 182  b6  (2)  cp.index
    OP_invokespecial   = -73 , // 183  b7  (2)  cp.index
    OP_invokestatic    = -72 , // 184  b8  (2)  cp.index
    OP_invokeinterface = -71 , // 185  b9  (2,1,0)  cp.index, count, 0
    OP_invokedynamic   = -70 , // 186  ba  (2,0,0)  cp.index, 0, 0
    OP_new             = -69 , // 187  bb  (2)  cp.index
    OP_newarray        = -68 , // 188  bc  (1)  atype
    OP_anewarray       = -67 , // 189  bd  (2)  cp.index
    OP_arraylength     = -66 , // 190  be
    OP_athrow          = -65 , // 191  bf
    OP_checkcast       = -64 , // 192  c0  (2)  cp.index
    OP_instanceof      = -63 , // 193  c1  (2)  cp.index
    OP_monitorenter    = -62 , // 194  c2
    OP_monitorexit     = -61 , // 195  c3

    // Extended

    OP_wide            = -60 , // 196  c4  (1,2 | 1,2,2)  opcode, lv.index | iinc, lv.index, count
    OP_multianewarray  = -59 , // 197  c5  (2,1)  cp.index, dimensions
    OP_ifnull          = -58 , // 198  c6  (2)  branch
    OP_ifnonnull       = -57 , // 199  c7  (2)  branch
    OP_goto_w          = -56 , // 200  c8  (4)  branch
    OP_jsr_w           = -55 , // 201  c9  (4)  branch

    // Reserved

    OP_breakpoint      = -54 , // 202  ca
    OP_impdep1         = -2  , // 254  fe
    OP_impdep2         = -1  ; // 255  ff

  enum WIDE {
    IINC   (OP_iinc),
    ILOAD  (OP_iload), 
    FLOAD  (OP_fload), 
    LLOAD  (OP_lload), 
    DLOAD  (OP_dload), 
    ALOAD  (OP_aload),
    ISTORE (OP_istore), 
    FSTORE (OP_fstore), 
    LSTORE (OP_lstore), 
    DSTORE (OP_dstore), 
    ASTORE (OP_astore),
    RET    (OP_ret);
 
    WIDE(byte b) { bits=b; }
    public final byte bits;
  }


  // Table 6.5.newarray-A. Array type codes

  final static byte
    T_BOOLEAN = 4,
    T_CHAR    = 5,
    T_FLOAT   = 6,
    T_DOUBLE  = 7,
    T_BYTE    = 8,
    T_SHORT   = 9,
    T_INT     = 10,
    T_LONG    = 11;

  enum AT {
    BOOLEAN (T_BOOLEAN),
    CHAR    (T_CHAR),
    FLOAT   (T_FLOAT),
    DOUBLE  (T_DOUBLE),
    BYTE    (T_BYTE),
    SHORT   (T_SHORT),
    INT     (T_INT),
    LONG    (T_LONG);

    AT(byte b) { bits=b; }
    public final byte bits;
  }

}
