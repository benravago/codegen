package bc.printer;

import java.util.Formatter;
import java.util.function.IntFunction;

import bc.ClassFile.Opcode;
import static bc.JVMS.*;

public class Instruction {

  public Instruction(IntFunction<CharSequence> constantPoolResolver, IntFunction<CharSequence> localVariableResolver) {
    cpResolver = constantPoolResolver;
    lvResolver = localVariableResolver;
  }

  StringBuilder s = new StringBuilder();
  Formatter f = new Formatter(s);

  CharSequence format(Opcode o) {
    s.setLength(0);
    switch (o.op()) {
      case OP_nop             -> i_             (o, "nop"             ); // 00
      case OP_aconst_null     -> i_             (o, "aconst_null"     ); // 01
      case OP_iconst_m1       -> i_             (o, "iconst_m1"       ); // 02
      case OP_iconst_0        -> i_             (o, "iconst_0"        ); // 03
      case OP_iconst_1        -> i_             (o, "iconst_1"        ); // 04
      case OP_iconst_2        -> i_             (o, "iconst_2"        ); // 05
      case OP_iconst_3        -> i_             (o, "iconst_3"        ); // 06
      case OP_iconst_4        -> i_             (o, "iconst_4"        ); // 07
      case OP_iconst_5        -> i_             (o, "iconst_5"        ); // 08
      case OP_lconst_0        -> i_             (o, "lconst_0"        ); // 09
      case OP_lconst_1        -> i_             (o, "lconst_1"        ); // 0a
      case OP_fconst_0        -> i_             (o, "fconst_0"        ); // 0b
      case OP_fconst_1        -> i_             (o, "fconst_1"        ); // 0c
      case OP_fconst_2        -> i_             (o, "fconst_2"        ); // 0d
      case OP_dconst_0        -> i_             (o, "dconst_0"        ); // 0e
      case OP_dconst_1        -> i_             (o, "dconst_1"        ); // 0f
      case OP_bipush          -> i_1b           (o, "bipush"          ); // 10  (1)  byte
      case OP_sipush          -> i_2s           (o, "sipush"          ); // 11  (2)  short
      case OP_ldc             -> i_1c           (o, "ldc"             ); // 12  (1)  cp.index
      case OP_ldc_w           -> i_2c           (o, "ldc_w"           ); // 13  (2)  cp.index
      case OP_ldc2_w          -> i_2c           (o, "ldc2_w"          ); // 14  (2)  cp.index
      case OP_iload           -> i_1v           (o, "iload"           ); // 15  (1)  lv.index
      case OP_lload           -> i_1v           (o, "lload"           ); // 16  (1)  lv.index
      case OP_fload           -> i_1v           (o, "fload"           ); // 17  (1)  lv.index
      case OP_dload           -> i_1v           (o, "dload"           ); // 18  (1)  lv.index
      case OP_aload           -> i_1v           (o, "aload"           ); // 19  (1)  lv.index
      case OP_iload_0         -> i_             (o, "iload_0"         ); // 1a
      case OP_iload_1         -> i_             (o, "iload_1"         ); // 1b
      case OP_iload_2         -> i_             (o, "iload_2"         ); // 1c
      case OP_iload_3         -> i_             (o, "iload_3"         ); // 1d
      case OP_lload_0         -> i_             (o, "lload_0"         ); // 1e
      case OP_lload_1         -> i_             (o, "lload_1"         ); // 1f
      case OP_lload_2         -> i_             (o, "lload_2"         ); // 20
      case OP_lload_3         -> i_             (o, "lload_3"         ); // 21
      case OP_fload_0         -> i_             (o, "fload_0"         ); // 22
      case OP_fload_1         -> i_             (o, "fload_1"         ); // 23
      case OP_fload_2         -> i_             (o, "fload_2"         ); // 24
      case OP_fload_3         -> i_             (o, "fload_3"         ); // 25
      case OP_dload_0         -> i_             (o, "dload_0"         ); // 26
      case OP_dload_1         -> i_             (o, "dload_1"         ); // 27
      case OP_dload_2         -> i_             (o, "dload_2"         ); // 28
      case OP_dload_3         -> i_             (o, "dload_3"         ); // 29
      case OP_aload_0         -> i_             (o, "aload_0"         ); // 2a
      case OP_aload_1         -> i_             (o, "aload_1"         ); // 2b
      case OP_aload_2         -> i_             (o, "aload_2"         ); // 2c
      case OP_aload_3         -> i_             (o, "aload_3"         ); // 2d
      case OP_iaload          -> i_             (o, "iaload"          ); // 2e
      case OP_laload          -> i_             (o, "laload"          ); // 2f
      case OP_faload          -> i_             (o, "faload"          ); // 30
      case OP_daload          -> i_             (o, "daload"          ); // 31
      case OP_aaload          -> i_             (o, "aaload"          ); // 32
      case OP_baload          -> i_             (o, "baload"          ); // 33
      case OP_caload          -> i_             (o, "caload"          ); // 34
      case OP_saload          -> i_             (o, "saload"          ); // 35
      case OP_istore          -> i_1v           (o, "istore"          ); // 36  (1)  lv.index
      case OP_lstore          -> i_1v           (o, "lstore"          ); // 37  (1)  lv.index
      case OP_fstore          -> i_1v           (o, "fstore"          ); // 38  (1)  lv.index
      case OP_dstore          -> i_1v           (o, "dstore"          ); // 39  (1)  lv.index
      case OP_astore          -> i_1v           (o, "astore"          ); // 3a  (1)  lv.index
      case OP_istore_0        -> i_             (o, "istore_0"        ); // 3b
      case OP_istore_1        -> i_             (o, "istore_1"        ); // 3c
      case OP_istore_2        -> i_             (o, "istore_2"        ); // 3d
      case OP_istore_3        -> i_             (o, "istore_3"        ); // 3e
      case OP_lstore_0        -> i_             (o, "lstore_0"        ); // 3f
      case OP_lstore_1        -> i_             (o, "lstore_1"        ); // 40
      case OP_lstore_2        -> i_             (o, "lstore_2"        ); // 41
      case OP_lstore_3        -> i_             (o, "lstore_3"        ); // 42
      case OP_fstore_0        -> i_             (o, "fstore_0"        ); // 43
      case OP_fstore_1        -> i_             (o, "fstore_1"        ); // 44
      case OP_fstore_2        -> i_             (o, "fstore_2"        ); // 45
      case OP_fstore_3        -> i_             (o, "fstore_3"        ); // 46
      case OP_dstore_0        -> i_             (o, "dstore_0"        ); // 47
      case OP_dstore_1        -> i_             (o, "dstore_1"        ); // 48
      case OP_dstore_2        -> i_             (o, "dstore_2"        ); // 49
      case OP_dstore_3        -> i_             (o, "dstore_3"        ); // 4a
      case OP_astore_0        -> i_             (o, "astore_0"        ); // 4b
      case OP_astore_1        -> i_             (o, "astore_1"        ); // 4c
      case OP_astore_2        -> i_             (o, "astore_2"        ); // 4d
      case OP_astore_3        -> i_             (o, "astore_3"        ); // 4e
      case OP_iastore         -> i_             (o, "iastore"         ); // 4f
      case OP_lastore         -> i_             (o, "lastore"         ); // 50
      case OP_fastore         -> i_             (o, "fastore"         ); // 51
      case OP_dastore         -> i_             (o, "dastore"         ); // 52
      case OP_aastore         -> i_             (o, "aastore"         ); // 53
      case OP_bastore         -> i_             (o, "bastore"         ); // 54
      case OP_castore         -> i_             (o, "castore"         ); // 55
      case OP_sastore         -> i_             (o, "sastore"         ); // 56
      case OP_pop             -> i_             (o, "pop"             ); // 57
      case OP_pop2            -> i_             (o, "pop2"            ); // 58
      case OP_dup             -> i_             (o, "dup"             ); // 59
      case OP_dup_x1          -> i_             (o, "dup_x1"          ); // 5a
      case OP_dup_x2          -> i_             (o, "dup_x2"          ); // 5b
      case OP_dup2            -> i_             (o, "dup2"            ); // 5c
      case OP_dup2_x1         -> i_             (o, "dup2_x1"         ); // 5d
      case OP_dup2_x2         -> i_             (o, "dup2_x2"         ); // 5e
      case OP_swap            -> i_             (o, "swap"            ); // 5f
      case OP_iadd            -> i_             (o, "iadd"            ); // 60
      case OP_ladd            -> i_             (o, "ladd"            ); // 61
      case OP_fadd            -> i_             (o, "fadd"            ); // 62
      case OP_dadd            -> i_             (o, "dadd"            ); // 63
      case OP_isub            -> i_             (o, "isub"            ); // 64
      case OP_lsub            -> i_             (o, "lsub"            ); // 65
      case OP_fsub            -> i_             (o, "fsub"            ); // 66
      case OP_dsub            -> i_             (o, "dsub"            ); // 67
      case OP_imul            -> i_             (o, "imul"            ); // 68
      case OP_lmul            -> i_             (o, "lmul"            ); // 69
      case OP_fmul            -> i_             (o, "fmul"            ); // 6a
      case OP_dmul            -> i_             (o, "dmul"            ); // 6b
      case OP_idiv            -> i_             (o, "idiv"            ); // 6c
      case OP_ldiv            -> i_             (o, "ldiv"            ); // 6d
      case OP_fdiv            -> i_             (o, "fdiv"            ); // 6e
      case OP_ddiv            -> i_             (o, "ddiv"            ); // 6f
      case OP_irem            -> i_             (o, "irem"            ); // 70
      case OP_lrem            -> i_             (o, "lrem"            ); // 71
      case OP_frem            -> i_             (o, "frem"            ); // 72
      case OP_drem            -> i_             (o, "drem"            ); // 73
      case OP_ineg            -> i_             (o, "ineg"            ); // 74
      case OP_lneg            -> i_             (o, "lneg"            ); // 75
      case OP_fneg            -> i_             (o, "fneg"            ); // 76
      case OP_dneg            -> i_             (o, "dneg"            ); // 77
      case OP_ishl            -> i_             (o, "ishl"            ); // 78
      case OP_lshl            -> i_             (o, "lshl"            ); // 79
      case OP_ishr            -> i_             (o, "ishr"            ); // 7a
      case OP_lshr            -> i_             (o, "lshr"            ); // 7b
      case OP_iushr           -> i_             (o, "iushr"           ); // 7c
      case OP_lushr           -> i_             (o, "lushr"           ); // 7d
      case OP_iand            -> i_             (o, "iand"            ); // 7e
      case OP_land            -> i_             (o, "land"            ); // 7f
      case OP_ior             -> i_             (o, "ior"             ); // 80
      case OP_lor             -> i_             (o, "lor"             ); // 81
      case OP_ixor            -> i_             (o, "ixor"            ); // 82
      case OP_lxor            -> i_             (o, "lxor"            ); // 83
      case OP_iinc            -> i_1c_1d        (o, "iinc"            ); // 84  (1,1)  cp.index, const
      case OP_i2l             -> i_             (o, "i2l"             ); // 85
      case OP_i2f             -> i_             (o, "i2f"             ); // 86
      case OP_i2d             -> i_             (o, "i2d"             ); // 87
      case OP_l2i             -> i_             (o, "l2i"             ); // 88
      case OP_l2f             -> i_             (o, "l2f"             ); // 89
      case OP_l2d             -> i_             (o, "l2d"             ); // 8a
      case OP_f2i             -> i_             (o, "f2i"             ); // 8b
      case OP_f2l             -> i_             (o, "f2l"             ); // 8c
      case OP_f2d             -> i_             (o, "f2d"             ); // 8d
      case OP_d2i             -> i_             (o, "d2i"             ); // 8e
      case OP_d2l             -> i_             (o, "d2l"             ); // 8f
      case OP_d2f             -> i_             (o, "d2f"             ); // 90
      case OP_i2b             -> i_             (o, "i2b"             ); // 91
      case OP_i2c             -> i_             (o, "i2c"             ); // 92
      case OP_i2s             -> i_             (o, "i2s"             ); // 93
      case OP_lcmp            -> i_             (o, "lcmp"            ); // 94
      case OP_fcmpl           -> i_             (o, "fcmpl"           ); // 95
      case OP_fcmpg           -> i_             (o, "fcmpg"           ); // 96
      case OP_dcmpl           -> i_             (o, "dcmpl"           ); // 97
      case OP_dcmpg           -> i_             (o, "dcmpg"           ); // 98
      case OP_ifeq            -> i_2j           (o, "ifeq"            ); // 99  (2)  branch
      case OP_ifne            -> i_2j           (o, "ifne"            ); // 9a  (2)  branch
      case OP_iflt            -> i_2j           (o, "iflt"            ); // 9b  (2)  branch
      case OP_ifge            -> i_2j           (o, "ifge"            ); // 9c  (2)  branch
      case OP_ifgt            -> i_2j           (o, "ifgt"            ); // 9d  (2)  branch
      case OP_ifle            -> i_2j           (o, "ifle"            ); // 9e  (2)  branch
      case OP_if_icmpeq       -> i_2j           (o, "if_icmpeq"       ); // 9f  (2)  branch
      case OP_if_icmpne       -> i_2j           (o, "if_icmpne"       ); // a0  (2)  branch
      case OP_if_icmplt       -> i_2j           (o, "if_icmplt"       ); // a1  (2)  branch
      case OP_if_icmpge       -> i_2j           (o, "if_icmpge"       ); // a2  (2)  branch
      case OP_if_icmpgt       -> i_2j           (o, "if_icmpgt"       ); // a3  (2)  branch
      case OP_if_icmple       -> i_2j           (o, "if_icmple"       ); // a4  (2)  branch
      case OP_if_acmpeq       -> i_2j           (o, "if_acmpeq"       ); // a5  (2)  branch
      case OP_if_acmpne       -> i_2j           (o, "if_acmpne"       ); // a6  (2)  branch
      case OP_goto            -> i_2j           (o, "goto"            ); // a7  (2)  branch
      case OP_jsr             -> i_2j           (o, "jsr"             ); // a8  (2)  branch
      case OP_ret             -> i_1c           (o, "ret"             ); // a9  (1)  cp.index
      case OP_tableswitch     -> i_p_4d_4d_4d_v (o, "tableswitch"     ); // aa  (0-3,4,4,4,...) padding, default, low, high, jump offsets
      case OP_lookupswitch    -> i_p_4d_4d_v    (o, "lookupswitch"    ); // ab  (0-3,4,4,...)  padding, default, npairs, match/offset pairs
      case OP_ireturn         -> i_             (o, "ireturn"         ); // ac
      case OP_lreturn         -> i_             (o, "lreturn"         ); // ad
      case OP_freturn         -> i_             (o, "freturn"         ); // ae
      case OP_dreturn         -> i_             (o, "dreturn"         ); // af
      case OP_areturn         -> i_             (o, "areturn"         ); // b0
      case OP_return          -> i_             (o, "return"          ); // b1
      case OP_getstatic       -> i_2c           (o, "getstatic"       ); // b2  (2)  cp.index
      case OP_putstatic       -> i_2c           (o, "putstatic"       ); // b3  (2)  cp.index
      case OP_getfield        -> i_2c           (o, "getfield"        ); // b4  (2)  cp.index
      case OP_putfield        -> i_2c           (o, "putfield"        ); // b5  (2)  cp.index
      case OP_invokevirtual   -> i_2c           (o, "invokevirtual"   ); // b6  (2)  cp.index
      case OP_invokespecial   -> i_2c           (o, "invokespecial"   ); // b7  (2)  cp.index
      case OP_invokestatic    -> i_2c           (o, "invokestatic"    ); // b8  (2)  cp.index
      case OP_invokeinterface -> i_2c_1d_0      (o, "invokeinterface" ); // b9  (2,1,0)  index, count, 0
      case OP_invokedynamic   -> i_2c_0_0       (o, "invokedynamic"   ); // ba  (2,0,0)  index, 0, 0
      case OP_new             -> i_2c           (o, "new"             ); // bb  (2)  cp.index
      case OP_newarray        -> i_1t           (o, "newarray"        ); // bc  (1)  atype
      case OP_anewarray       -> i_2c           (o, "anewarray"       ); // bd  (2)  cp.index
      case OP_arraylength     -> i_             (o, "arraylength"     ); // be
      case OP_athrow          -> i_             (o, "athrow"          ); // bf
      case OP_checkcast       -> i_2c           (o, "checkcast"       ); // c0  (2)  cp.index
      case OP_instanceof      -> i_2c           (o, "instanceof"      ); // c1  (2)  cp.index
      case OP_monitorenter    -> i_             (o, "monitorenter"    ); // c2
      case OP_monitorexit     -> i_             (o, "monitorexit"     ); // c3
      case OP_wide            -> i_1w_2c_v      (o, "wide"            ); // c4  (1,2 | 1,2,2) opcode, index | iinc, index, count
      case OP_multianewarray  -> i_2c_1d        (o, "multianewarray"  ); // c5  (2,1)  index, dimensions
      case OP_ifnull          -> i_2j           (o, "ifnull"          ); // c6  (2)  branch
      case OP_ifnonnull       -> i_2j           (o, "ifnonnull"       ); // c7  (2)  branch
      case OP_goto_w          -> i_4j           (o, "goto_w"          ); // c8  (4)  branch
      case OP_jsr_w           -> i_4j           (o, "jsr_w"           ); // c9  (4)  branch
      case OP_breakpoint      -> i_             (o, "breakpoint"      ); // ca
      case OP_impdep1         -> i_             (o, "impdep1"         ); // fe
      case OP_impdep2         -> i_             (o, "impdep2"         ); // ff
    }
    return s;
  }

  void i_        (Opcode o, String i) { f.format( "%04x  %s"            , o.pc(), i                      ); } //  -
  void i_1b      (Opcode o, String i) { f.format( "%04x  %s  %d"        , o.pc(), i, (Byte)o.args()      ); } //  (1)  byte
  void i_2s      (Opcode o, String i) { f.format( "%04x  %s  %d"        , o.pc(), i, (Short)o.args()     ); } //  (2)  short
  void i_1c      (Opcode o, String i) { f.format( "%04x  %s  %s"        , o.pc(), i, cp((Byte)o.args())  ); } //  (1)  cp.index
  void i_2c      (Opcode o, String i) { f.format( "%04x  %s  %s"        , o.pc(), i, cp((Short)o.args()) ); } //  (2)  cp.index
  void i_1v      (Opcode o, String i) { f.format( "%04x  %s  %s"        , o.pc(), i, lv((Byte)o.args())  ); } //  (1)  lv.index
  void i_2j      (Opcode o, String i) { f.format( "%04x  %s  0x%04x"    , o.pc(), i, (Short)o.args()     ); } //  (2)  branch
  void i_4j      (Opcode o, String i) { f.format( "%04x  %s  0x%08x"    , o.pc(), i, (Integer)o.args()   ); } //  (4)  branch
  void i_1t      (Opcode o, String i) { f.format( "%04x  %s  +%d"       , o.pc(), i, (Byte)o.args()      ); } //  (1)  atype
  void i_1c_1d   (Opcode o, String i) { f.format( "%04x  %s  %s, %d"    , o.pc(), i, c1(o,0), u1(o,1)    ); } //  (1,1)  cp.index, const
  void i_2c_1d   (Opcode o, String i) { f.format( "%04x  %s  %s, %d"    , o.pc(), i, c2(o,0), u1(o,1)    ); } //  (2,1)  cp.index, const
  void i_2c_1d_0 (Opcode o, String i) { f.format( "%04x  %s  %s, %d, 0" , o.pc(), i, c2(o,0), u1(o,1)    ); } //  (2,1,0)  cp.index, count, 0
  void i_2c_0_0  (Opcode o, String i) { f.format( "%04x  %s  %s, 0, 0"  , o.pc(), i, c2(o,0)             ); } //  (2,0,0)  cp.index, 0, 0

  void i_p_4d_4d_4d_v(Opcode o, String ins) {
    var v = (Integer[])o.args();
    f.format("%04x  %s  %d,", o.pc(), ins, v[0] ); // pc, op, padding
    for (int i = 1, m = v.length; i < m; i++) {
      f.format( " 0x%08x,", v[i] ); // default, low, high, jump offsets
    }
    s.setLength(s.length()-1); // remove trailing ','
  }

  void i_p_4d_4d_v(Opcode o, String ins) {
    var v = (Integer[])o.args();
    f.format("%04x  %s  %d, 0x%08x, %d,", o.pc(), ins, v[0], v[1], v[2] ); // pc, op, padding, default, npairs
    for (int i = 3, m = v.length; i < m;) {
      f.format( " 0x%08x, 0x%08x,", v[i++], v[i++] ); // match/offset pairs
    }
    s.setLength(s.length()-1); // remove trailing ','
  }

  void i_1w_2c_v(Opcode o, String ins) {
    var i = u1(o,0);
    if (i == OP_iinc) {
      f.format("%04x  %s  %s, %s, %d" , o.pc(), ins, "iinc", c2(o,1), u2(o,2) ); // (1,2,2) 'iinc', cp.index, count
    } else {
      f.format("%04x  %s  %s, %s" , o.pc(), ins, wide_op(i), c2(o,1) ); // (1,2) opcode, cp.index
    }
  }

  CharSequence wide_op(byte i) {
    return switch(i) {
      case OP_iload  -> "iload";
      case OP_fload  -> "fload";
      case OP_aload  -> "aload";
      case OP_lload  -> "lload";
      case OP_dload  -> "dload";
      case OP_istore -> "istore";
      case OP_fstore -> "fstore";
      case OP_astore -> "astore";
      case OP_lstore -> "lstore";
      case OP_dstore -> "dstore";
      case OP_ret    -> "ret";
      default -> throw new IllegalArgumentException("wide_op="+i);
    };
  }

  static Byte  u1(Opcode o, int i) { return (Byte) ((Object[])o.args())[i]; }
  static Short u2(Opcode o, int i) { return (Short)((Object[])o.args())[i]; }

  CharSequence c1(Opcode o, int i) { return cp(u1(o,i)); }
  CharSequence c2(Opcode o, int i) { return cp(u2(o,i)); }

  CharSequence cp(int i) { return cpResolver.apply(i); }
  CharSequence lv(int i) { return lvResolver.apply(i); }

  final IntFunction<CharSequence> cpResolver;
  final IntFunction<CharSequence> lvResolver;

}
