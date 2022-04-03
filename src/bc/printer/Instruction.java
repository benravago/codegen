package bc.printer;

import java.util.Formatter;

import bc.ClassFile.Opcode;
import static bc.JVMS.*;

final class Instruction {

  StringBuilder s = new StringBuilder();
  Formatter f = new Formatter(s);
  Opcode o;

  CharSequence format(Opcode opcode) {
    o = opcode;
    s.setLength(0);
    switch (o.op()) {
      case OP_nop             -> i_             ( "nop"             ); // 00
      case OP_aconst_null     -> i_             ( "aconst_null"     ); // 01
      case OP_iconst_m1       -> i_             ( "iconst_m1"       ); // 02
      case OP_iconst_0        -> i_             ( "iconst_0"        ); // 03
      case OP_iconst_1        -> i_             ( "iconst_1"        ); // 04
      case OP_iconst_2        -> i_             ( "iconst_2"        ); // 05
      case OP_iconst_3        -> i_             ( "iconst_3"        ); // 06
      case OP_iconst_4        -> i_             ( "iconst_4"        ); // 07
      case OP_iconst_5        -> i_             ( "iconst_5"        ); // 08
      case OP_lconst_0        -> i_             ( "lconst_0"        ); // 09
      case OP_lconst_1        -> i_             ( "lconst_1"        ); // 0a
      case OP_fconst_0        -> i_             ( "fconst_0"        ); // 0b
      case OP_fconst_1        -> i_             ( "fconst_1"        ); // 0c
      case OP_fconst_2        -> i_             ( "fconst_2"        ); // 0d
      case OP_dconst_0        -> i_             ( "dconst_0"        ); // 0e
      case OP_dconst_1        -> i_             ( "dconst_1"        ); // 0f
      case OP_bipush          -> i_1b           ( "bipush"          ); // 10  (1)  byte
      case OP_sipush          -> i_2s           ( "sipush"          ); // 11  (2)  short
      case OP_ldc             -> i_1c           ( "ldc"             ); // 12  (1)  cp.index
      case OP_ldc_w           -> i_2c           ( "ldc_w"           ); // 13  (2)  cp.index
      case OP_ldc2_w          -> i_2c           ( "ldc2_w"          ); // 14  (2)  cp.index
      case OP_iload           -> i_1v           ( "iload"           ); // 15  (1)  lv.index
      case OP_lload           -> i_1v           ( "lload"           ); // 16  (1)  lv.index
      case OP_fload           -> i_1v           ( "fload"           ); // 17  (1)  lv.index
      case OP_dload           -> i_1v           ( "dload"           ); // 18  (1)  lv.index
      case OP_aload           -> i_1v           ( "aload"           ); // 19  (1)  lv.index
      case OP_iload_0         -> i_             ( "iload_0"         ); // 1a
      case OP_iload_1         -> i_             ( "iload_1"         ); // 1b
      case OP_iload_2         -> i_             ( "iload_2"         ); // 1c
      case OP_iload_3         -> i_             ( "iload_3"         ); // 1d
      case OP_lload_0         -> i_             ( "lload_0"         ); // 1e
      case OP_lload_1         -> i_             ( "lload_1"         ); // 1f
      case OP_lload_2         -> i_             ( "lload_2"         ); // 20
      case OP_lload_3         -> i_             ( "lload_3"         ); // 21
      case OP_fload_0         -> i_             ( "fload_0"         ); // 22
      case OP_fload_1         -> i_             ( "fload_1"         ); // 23
      case OP_fload_2         -> i_             ( "fload_2"         ); // 24
      case OP_fload_3         -> i_             ( "fload_3"         ); // 25
      case OP_dload_0         -> i_             ( "dload_0"         ); // 26
      case OP_dload_1         -> i_             ( "dload_1"         ); // 27
      case OP_dload_2         -> i_             ( "dload_2"         ); // 28
      case OP_dload_3         -> i_             ( "dload_3"         ); // 29
      case OP_aload_0         -> i_             ( "aload_0"         ); // 2a
      case OP_aload_1         -> i_             ( "aload_1"         ); // 2b
      case OP_aload_2         -> i_             ( "aload_2"         ); // 2c
      case OP_aload_3         -> i_             ( "aload_3"         ); // 2d
      case OP_iaload          -> i_             ( "iaload"          ); // 2e
      case OP_laload          -> i_             ( "laload"          ); // 2f
      case OP_faload          -> i_             ( "faload"          ); // 30
      case OP_daload          -> i_             ( "daload"          ); // 31
      case OP_aaload          -> i_             ( "aaload"          ); // 32
      case OP_baload          -> i_             ( "baload"          ); // 33
      case OP_caload          -> i_             ( "caload"          ); // 34
      case OP_saload          -> i_             ( "saload"          ); // 35
      case OP_istore          -> i_1v           ( "istore"          ); // 36  (1)  lv.index
      case OP_lstore          -> i_1v           ( "lstore"          ); // 37  (1)  lv.index
      case OP_fstore          -> i_1v           ( "fstore"          ); // 38  (1)  lv.index
      case OP_dstore          -> i_1v           ( "dstore"          ); // 39  (1)  lv.index
      case OP_astore          -> i_1v           ( "astore"          ); // 3a  (1)  lv.index
      case OP_istore_0        -> i_             ( "istore_0"        ); // 3b
      case OP_istore_1        -> i_             ( "istore_1"        ); // 3c
      case OP_istore_2        -> i_             ( "istore_2"        ); // 3d
      case OP_istore_3        -> i_             ( "istore_3"        ); // 3e
      case OP_lstore_0        -> i_             ( "lstore_0"        ); // 3f
      case OP_lstore_1        -> i_             ( "lstore_1"        ); // 40
      case OP_lstore_2        -> i_             ( "lstore_2"        ); // 41
      case OP_lstore_3        -> i_             ( "lstore_3"        ); // 42
      case OP_fstore_0        -> i_             ( "fstore_0"        ); // 43
      case OP_fstore_1        -> i_             ( "fstore_1"        ); // 44
      case OP_fstore_2        -> i_             ( "fstore_2"        ); // 45
      case OP_fstore_3        -> i_             ( "fstore_3"        ); // 46
      case OP_dstore_0        -> i_             ( "dstore_0"        ); // 47
      case OP_dstore_1        -> i_             ( "dstore_1"        ); // 48
      case OP_dstore_2        -> i_             ( "dstore_2"        ); // 49
      case OP_dstore_3        -> i_             ( "dstore_3"        ); // 4a
      case OP_astore_0        -> i_             ( "astore_0"        ); // 4b
      case OP_astore_1        -> i_             ( "astore_1"        ); // 4c
      case OP_astore_2        -> i_             ( "astore_2"        ); // 4d
      case OP_astore_3        -> i_             ( "astore_3"        ); // 4e
      case OP_iastore         -> i_             ( "iastore"         ); // 4f
      case OP_lastore         -> i_             ( "lastore"         ); // 50
      case OP_fastore         -> i_             ( "fastore"         ); // 51
      case OP_dastore         -> i_             ( "dastore"         ); // 52
      case OP_aastore         -> i_             ( "aastore"         ); // 53
      case OP_bastore         -> i_             ( "bastore"         ); // 54
      case OP_castore         -> i_             ( "castore"         ); // 55
      case OP_sastore         -> i_             ( "sastore"         ); // 56
      case OP_pop             -> i_             ( "pop"             ); // 57
      case OP_pop2            -> i_             ( "pop2"            ); // 58
      case OP_dup             -> i_             ( "dup"             ); // 59
      case OP_dup_x1          -> i_             ( "dup_x1"          ); // 5a
      case OP_dup_x2          -> i_             ( "dup_x2"          ); // 5b
      case OP_dup2            -> i_             ( "dup2"            ); // 5c
      case OP_dup2_x1         -> i_             ( "dup2_x1"         ); // 5d
      case OP_dup2_x2         -> i_             ( "dup2_x2"         ); // 5e
      case OP_swap            -> i_             ( "swap"            ); // 5f
      case OP_iadd            -> i_             ( "iadd"            ); // 60
      case OP_ladd            -> i_             ( "ladd"            ); // 61
      case OP_fadd            -> i_             ( "fadd"            ); // 62
      case OP_dadd            -> i_             ( "dadd"            ); // 63
      case OP_isub            -> i_             ( "isub"            ); // 64
      case OP_lsub            -> i_             ( "lsub"            ); // 65
      case OP_fsub            -> i_             ( "fsub"            ); // 66
      case OP_dsub            -> i_             ( "dsub"            ); // 67
      case OP_imul            -> i_             ( "imul"            ); // 68
      case OP_lmul            -> i_             ( "lmul"            ); // 69
      case OP_fmul            -> i_             ( "fmul"            ); // 6a
      case OP_dmul            -> i_             ( "dmul"            ); // 6b
      case OP_idiv            -> i_             ( "idiv"            ); // 6c
      case OP_ldiv            -> i_             ( "ldiv"            ); // 6d
      case OP_fdiv            -> i_             ( "fdiv"            ); // 6e
      case OP_ddiv            -> i_             ( "ddiv"            ); // 6f
      case OP_irem            -> i_             ( "irem"            ); // 70
      case OP_lrem            -> i_             ( "lrem"            ); // 71
      case OP_frem            -> i_             ( "frem"            ); // 72
      case OP_drem            -> i_             ( "drem"            ); // 73
      case OP_ineg            -> i_             ( "ineg"            ); // 74
      case OP_lneg            -> i_             ( "lneg"            ); // 75
      case OP_fneg            -> i_             ( "fneg"            ); // 76
      case OP_dneg            -> i_             ( "dneg"            ); // 77
      case OP_ishl            -> i_             ( "ishl"            ); // 78
      case OP_lshl            -> i_             ( "lshl"            ); // 79
      case OP_ishr            -> i_             ( "ishr"            ); // 7a
      case OP_lshr            -> i_             ( "lshr"            ); // 7b
      case OP_iushr           -> i_             ( "iushr"           ); // 7c
      case OP_lushr           -> i_             ( "lushr"           ); // 7d
      case OP_iand            -> i_             ( "iand"            ); // 7e
      case OP_land            -> i_             ( "land"            ); // 7f
      case OP_ior             -> i_             ( "ior"             ); // 80
      case OP_lor             -> i_             ( "lor"             ); // 81
      case OP_ixor            -> i_             ( "ixor"            ); // 82
      case OP_lxor            -> i_             ( "lxor"            ); // 83
      case OP_iinc            -> i_1c_1d        ( "iinc"            ); // 84  (1,1)  cp.index, const
      case OP_i2l             -> i_             ( "i2l"             ); // 85
      case OP_i2f             -> i_             ( "i2f"             ); // 86
      case OP_i2d             -> i_             ( "i2d"             ); // 87
      case OP_l2i             -> i_             ( "l2i"             ); // 88
      case OP_l2f             -> i_             ( "l2f"             ); // 89
      case OP_l2d             -> i_             ( "l2d"             ); // 8a
      case OP_f2i             -> i_             ( "f2i"             ); // 8b
      case OP_f2l             -> i_             ( "f2l"             ); // 8c
      case OP_f2d             -> i_             ( "f2d"             ); // 8d
      case OP_d2i             -> i_             ( "d2i"             ); // 8e
      case OP_d2l             -> i_             ( "d2l"             ); // 8f
      case OP_d2f             -> i_             ( "d2f"             ); // 90
      case OP_i2b             -> i_             ( "i2b"             ); // 91
      case OP_i2c             -> i_             ( "i2c"             ); // 92
      case OP_i2s             -> i_             ( "i2s"             ); // 93
      case OP_lcmp            -> i_             ( "lcmp"            ); // 94
      case OP_fcmpl           -> i_             ( "fcmpl"           ); // 95
      case OP_fcmpg           -> i_             ( "fcmpg"           ); // 96
      case OP_dcmpl           -> i_             ( "dcmpl"           ); // 97
      case OP_dcmpg           -> i_             ( "dcmpg"           ); // 98
      case OP_ifeq            -> i_2j           ( "ifeq"            ); // 99  (2)  branch
      case OP_ifne            -> i_2j           ( "ifne"            ); // 9a  (2)  branch
      case OP_iflt            -> i_2j           ( "iflt"            ); // 9b  (2)  branch
      case OP_ifge            -> i_2j           ( "ifge"            ); // 9c  (2)  branch
      case OP_ifgt            -> i_2j           ( "ifgt"            ); // 9d  (2)  branch
      case OP_ifle            -> i_2j           ( "ifle"            ); // 9e  (2)  branch
      case OP_if_icmpeq       -> i_2j           ( "if_icmpeq"       ); // 9f  (2)  branch
      case OP_if_icmpne       -> i_2j           ( "if_icmpne"       ); // a0  (2)  branch
      case OP_if_icmplt       -> i_2j           ( "if_icmplt"       ); // a1  (2)  branch
      case OP_if_icmpge       -> i_2j           ( "if_icmpge"       ); // a2  (2)  branch
      case OP_if_icmpgt       -> i_2j           ( "if_icmpgt"       ); // a3  (2)  branch
      case OP_if_icmple       -> i_2j           ( "if_icmple"       ); // a4  (2)  branch
      case OP_if_acmpeq       -> i_2j           ( "if_acmpeq"       ); // a5  (2)  branch
      case OP_if_acmpne       -> i_2j           ( "if_acmpne"       ); // a6  (2)  branch
      case OP_goto            -> i_2j           ( "goto"            ); // a7  (2)  branch
      case OP_jsr             -> i_2j           ( "jsr"             ); // a8  (2)  branch
      case OP_ret             -> i_1c           ( "ret"             ); // a9  (1)  cp.index
      case OP_tableswitch     -> i_p_4d_4d_4d_v ( "tableswitch"     ); // aa  (0-3,4,4,4,...) padding, default, low, high, jump offsets
      case OP_lookupswitch    -> i_p_4d_4d_v    ( "lookupswitch"    ); // ab  (0-3,4,4,...)  padding, default, npairs, match/offset pairs
      case OP_ireturn         -> i_             ( "ireturn"         ); // ac
      case OP_lreturn         -> i_             ( "lreturn"         ); // ad
      case OP_freturn         -> i_             ( "freturn"         ); // ae
      case OP_dreturn         -> i_             ( "dreturn"         ); // af
      case OP_areturn         -> i_             ( "areturn"         ); // b0
      case OP_return          -> i_             ( "return"          ); // b1
      case OP_getstatic       -> i_2c           ( "getstatic"       ); // b2  (2)  cp.index
      case OP_putstatic       -> i_2c           ( "putstatic"       ); // b3  (2)  cp.index
      case OP_getfield        -> i_2c           ( "getfield"        ); // b4  (2)  cp.index
      case OP_putfield        -> i_2c           ( "putfield"        ); // b5  (2)  cp.index
      case OP_invokevirtual   -> i_2c           ( "invokevirtual"   ); // b6  (2)  cp.index
      case OP_invokespecial   -> i_2c           ( "invokespecial"   ); // b7  (2)  cp.index
      case OP_invokestatic    -> i_2c           ( "invokestatic"    ); // b8  (2)  cp.index
      case OP_invokeinterface -> i_2c_1d_0      ( "invokeinterface" ); // b9  (2,1,0)  cp.index, count, 0
      case OP_invokedynamic   -> i_2c_0_0       ( "invokedynamic"   ); // ba  (2,0,0)  cp.index, 0, 0
      case OP_new             -> i_2c           ( "new"             ); // bb  (2)  cp.index
      case OP_newarray        -> i_1t           ( "newarray"        ); // bc  (1)  atype
      case OP_anewarray       -> i_2c           ( "anewarray"       ); // bd  (2)  cp.index
      case OP_arraylength     -> i_             ( "arraylength"     ); // be
      case OP_athrow          -> i_             ( "athrow"          ); // bf
      case OP_checkcast       -> i_2c           ( "checkcast"       ); // c0  (2)  cp.index
      case OP_instanceof      -> i_2c           ( "instanceof"      ); // c1  (2)  cp.index
      case OP_monitorenter    -> i_             ( "monitorenter"    ); // c2
      case OP_monitorexit     -> i_             ( "monitorexit"     ); // c3
      case OP_wide            -> i_1w_2c_v      ( "wide"            ); // c4  (1,2 | 1,2,2)  opcode, cp.index | iinc, cp.index, count
      case OP_multianewarray  -> i_2c_1d        ( "multianewarray"  ); // c5  (2,1)  cp.index, dimensions
      case OP_ifnull          -> i_2j           ( "ifnull"          ); // c6  (2)  branch
      case OP_ifnonnull       -> i_2j           ( "ifnonnull"       ); // c7  (2)  branch
      case OP_goto_w          -> i_4j           ( "goto_w"          ); // c8  (4)  branch
      case OP_jsr_w           -> i_4j           ( "jsr_w"           ); // c9  (4)  branch
      case OP_breakpoint      -> i_             ( "breakpoint"      ); // ca
      case OP_impdep1         -> i_             ( "impdep1"         ); // fe
      case OP_impdep2         -> i_             ( "impdep2"         ); // ff
    }
    return s;
  }

  void i_        (String i) { f( "%04x  %s"            , o.pc(), i                   ); } //  -
  void i_1b      (String i) { f( "%04x  %s  %d"        , o.pc(), i, u1()             ); } //  (1)  byte
  void i_2s      (String i) { f( "%04x  %s  %d"        , o.pc(), i, u2()             ); } //  (2)  short
  void i_1c      (String i) { f( "%04x  %s  %s"        , o.pc(), i, cp(u1())         ); } //  (1)  cp.index
  void i_2c      (String i) { f( "%04x  %s  %s"        , o.pc(), i, cp(u2())         ); } //  (2)  cp.index
  void i_1v      (String i) { f( "%04x  %s  %s"        , o.pc(), i, lv(u1())         ); } //  (1)  lv.index
  void i_2j      (String i) { f( "%04x  %s  0x%04x"    , o.pc(), i, u2()             ); } //  (2)  branch
  void i_4j      (String i) { f( "%04x  %s  0x%08x"    , o.pc(), i, u4()             ); } //  (4)  branch
  void i_1t      (String i) { f( "%04x  %s  +%d"       , o.pc(), i, u1()             ); } //  (1)  atype
  void i_1c_1d   (String i) { f( "%04x  %s  %s, %d"    , o.pc(), i, cp(u1(0)), u1(1) ); } //  (1,1)  cp.index, const
  void i_2c_1d   (String i) { f( "%04x  %s  %s, %d"    , o.pc(), i, cp(u2(0)), u1(1) ); } //  (2,1)  cp.index, const
  void i_2c_1d_0 (String i) { f( "%04x  %s  %s, %d, 0" , o.pc(), i, cp(u2(0)), u1(1) ); } //  (2,1,0)  cp.index, count, 0
  void i_2c_0_0  (String i) { f( "%04x  %s  %s, 0, 0"  , o.pc(), i, cp(u2(0))        ); } //  (2,0,0)  cp.index, 0, 0

  void i_p_4d_4d_4d_v(String ins) {
    var v = (Integer[])o.args();
    f("%04x  %s  %d,", o.pc(), ins, v[0] ); // pc, op, padding
    for (int i = 1, m = v.length; i < m; i++) {
      f( " 0x%08x,", v[i] ); // default, low, high, jump offsets
    }
    s.setLength(s.length()-1); // remove trailing ','
  }

  void i_p_4d_4d_v(String ins) {
    var v = (Integer[])o.args();
    f("%04x  %s  %d, 0x%08x, %d,", o.pc(), ins, v[0], v[1], v[2] ); // pc, op, padding, default, npairs
    for (int i = 3, m = v.length; i < m;) {
      f( " 0x%08x, 0x%08x,", v[i++], v[i++] ); // match/offset pairs
    }
    s.setLength(s.length()-1); // remove trailing ','
  }

  void i_1w_2c_v(String ins) {
    var i = u1(0);
    if (i == OP_iinc) {
      f("%04x  %s  %s, %s, %d" , o.pc(), ins, "iinc", cp(u2(1)), u2(2) ); // (1,2,2) 'iinc', cp.index, count
    } else {
      f("%04x  %s  %s, %s" , o.pc(), ins, wide_op(i), cp(u2(1)) ); // (1,2) opcode, cp.index
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

  void f(String format, Object... args) { f.format(format,args); }

  Byte    u1() { return (Byte)   o.args(); }
  Short   u2() { return (Short)  o.args(); }
  Integer u4() { return (Integer)o.args(); }

  Byte    u1(int i) { return (Byte) ((Object[])o.args())[i]; }
  Short   u2(int i) { return (Short)((Object[])o.args())[i]; }

  CharSequence cp(int i) { return "#"+i; }
  CharSequence lv(int i) { return "$"+i; }

}
