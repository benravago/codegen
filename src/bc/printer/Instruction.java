package bc.printer;

import java.util.Formatter;

import bc.ClassFile.Opcode;
import static bc.spec.JVMS.*;

abstract class Instruction {

  protected Opcode o; // injected by format()
  protected Formatter t; // injected by formatTo()

  void edit() {
    switch (o.op()) {
      case OP_nop             -> i_             (); // 00
      case OP_aconst_null     -> i_             (); // 01
      case OP_iconst_m1       -> i_             (); // 02
      case OP_iconst_0        -> i_             (); // 03
      case OP_iconst_1        -> i_             (); // 04
      case OP_iconst_2        -> i_             (); // 05
      case OP_iconst_3        -> i_             (); // 06
      case OP_iconst_4        -> i_             (); // 07
      case OP_iconst_5        -> i_             (); // 08
      case OP_lconst_0        -> i_             (); // 09
      case OP_lconst_1        -> i_             (); // 0a
      case OP_fconst_0        -> i_             (); // 0b
      case OP_fconst_1        -> i_             (); // 0c
      case OP_fconst_2        -> i_             (); // 0d
      case OP_dconst_0        -> i_             (); // 0e
      case OP_dconst_1        -> i_             (); // 0f
      case OP_bipush          -> i_1b           (); // 10  (1)  byte
      case OP_sipush          -> i_2s           (); // 11  (2)  short
      case OP_ldc             -> i_1c           (); // 12  (1)  cp.index
      case OP_ldc_w           -> i_2c           (); // 13  (2)  cp.index
      case OP_ldc2_w          -> i_2c           (); // 14  (2)  cp.index
      case OP_iload           -> i_1v           (); // 15  (1)  lv.index
      case OP_lload           -> i_1v           (); // 16  (1)  lv.index
      case OP_fload           -> i_1v           (); // 17  (1)  lv.index
      case OP_dload           -> i_1v           (); // 18  (1)  lv.index
      case OP_aload           -> i_1v           (); // 19  (1)  lv.index
      case OP_iload_0         -> i_             (); // 1a
      case OP_iload_1         -> i_             (); // 1b
      case OP_iload_2         -> i_             (); // 1c
      case OP_iload_3         -> i_             (); // 1d
      case OP_lload_0         -> i_             (); // 1e
      case OP_lload_1         -> i_             (); // 1f
      case OP_lload_2         -> i_             (); // 20
      case OP_lload_3         -> i_             (); // 21
      case OP_fload_0         -> i_             (); // 22
      case OP_fload_1         -> i_             (); // 23
      case OP_fload_2         -> i_             (); // 24
      case OP_fload_3         -> i_             (); // 25
      case OP_dload_0         -> i_             (); // 26
      case OP_dload_1         -> i_             (); // 27
      case OP_dload_2         -> i_             (); // 28
      case OP_dload_3         -> i_             (); // 29
      case OP_aload_0         -> i_             (); // 2a
      case OP_aload_1         -> i_             (); // 2b
      case OP_aload_2         -> i_             (); // 2c
      case OP_aload_3         -> i_             (); // 2d
      case OP_iaload          -> i_             (); // 2e
      case OP_laload          -> i_             (); // 2f
      case OP_faload          -> i_             (); // 30
      case OP_daload          -> i_             (); // 31
      case OP_aaload          -> i_             (); // 32
      case OP_baload          -> i_             (); // 33
      case OP_caload          -> i_             (); // 34
      case OP_saload          -> i_             (); // 35
      case OP_istore          -> i_1v           (); // 36  (1)  lv.index
      case OP_lstore          -> i_1v           (); // 37  (1)  lv.index
      case OP_fstore          -> i_1v           (); // 38  (1)  lv.index
      case OP_dstore          -> i_1v           (); // 39  (1)  lv.index
      case OP_astore          -> i_1v           (); // 3a  (1)  lv.index
      case OP_istore_0        -> i_             (); // 3b
      case OP_istore_1        -> i_             (); // 3c
      case OP_istore_2        -> i_             (); // 3d
      case OP_istore_3        -> i_             (); // 3e
      case OP_lstore_0        -> i_             (); // 3f
      case OP_lstore_1        -> i_             (); // 40
      case OP_lstore_2        -> i_             (); // 41
      case OP_lstore_3        -> i_             (); // 42
      case OP_fstore_0        -> i_             (); // 43
      case OP_fstore_1        -> i_             (); // 44
      case OP_fstore_2        -> i_             (); // 45
      case OP_fstore_3        -> i_             (); // 46
      case OP_dstore_0        -> i_             (); // 47
      case OP_dstore_1        -> i_             (); // 48
      case OP_dstore_2        -> i_             (); // 49
      case OP_dstore_3        -> i_             (); // 4a
      case OP_astore_0        -> i_             (); // 4b
      case OP_astore_1        -> i_             (); // 4c
      case OP_astore_2        -> i_             (); // 4d
      case OP_astore_3        -> i_             (); // 4e
      case OP_iastore         -> i_             (); // 4f
      case OP_lastore         -> i_             (); // 50
      case OP_fastore         -> i_             (); // 51
      case OP_dastore         -> i_             (); // 52
      case OP_aastore         -> i_             (); // 53
      case OP_bastore         -> i_             (); // 54
      case OP_castore         -> i_             (); // 55
      case OP_sastore         -> i_             (); // 56
      case OP_pop             -> i_             (); // 57
      case OP_pop2            -> i_             (); // 58
      case OP_dup             -> i_             (); // 59
      case OP_dup_x1          -> i_             (); // 5a
      case OP_dup_x2          -> i_             (); // 5b
      case OP_dup2            -> i_             (); // 5c
      case OP_dup2_x1         -> i_             (); // 5d
      case OP_dup2_x2         -> i_             (); // 5e
      case OP_swap            -> i_             (); // 5f
      case OP_iadd            -> i_             (); // 60
      case OP_ladd            -> i_             (); // 61
      case OP_fadd            -> i_             (); // 62
      case OP_dadd            -> i_             (); // 63
      case OP_isub            -> i_             (); // 64
      case OP_lsub            -> i_             (); // 65
      case OP_fsub            -> i_             (); // 66
      case OP_dsub            -> i_             (); // 67
      case OP_imul            -> i_             (); // 68
      case OP_lmul            -> i_             (); // 69
      case OP_fmul            -> i_             (); // 6a
      case OP_dmul            -> i_             (); // 6b
      case OP_idiv            -> i_             (); // 6c
      case OP_ldiv            -> i_             (); // 6d
      case OP_fdiv            -> i_             (); // 6e
      case OP_ddiv            -> i_             (); // 6f
      case OP_irem            -> i_             (); // 70
      case OP_lrem            -> i_             (); // 71
      case OP_frem            -> i_             (); // 72
      case OP_drem            -> i_             (); // 73
      case OP_ineg            -> i_             (); // 74
      case OP_lneg            -> i_             (); // 75
      case OP_fneg            -> i_             (); // 76
      case OP_dneg            -> i_             (); // 77
      case OP_ishl            -> i_             (); // 78
      case OP_lshl            -> i_             (); // 79
      case OP_ishr            -> i_             (); // 7a
      case OP_lshr            -> i_             (); // 7b
      case OP_iushr           -> i_             (); // 7c
      case OP_lushr           -> i_             (); // 7d
      case OP_iand            -> i_             (); // 7e
      case OP_land            -> i_             (); // 7f
      case OP_ior             -> i_             (); // 80
      case OP_lor             -> i_             (); // 81
      case OP_ixor            -> i_             (); // 82
      case OP_lxor            -> i_             (); // 83
      case OP_iinc            -> i_1v_1d        (); // 84  (1,1)  lv.index, const
      case OP_i2l             -> i_             (); // 85
      case OP_i2f             -> i_             (); // 86
      case OP_i2d             -> i_             (); // 87
      case OP_l2i             -> i_             (); // 88
      case OP_l2f             -> i_             (); // 89
      case OP_l2d             -> i_             (); // 8a
      case OP_f2i             -> i_             (); // 8b
      case OP_f2l             -> i_             (); // 8c
      case OP_f2d             -> i_             (); // 8d
      case OP_d2i             -> i_             (); // 8e
      case OP_d2l             -> i_             (); // 8f
      case OP_d2f             -> i_             (); // 90
      case OP_i2b             -> i_             (); // 91
      case OP_i2c             -> i_             (); // 92
      case OP_i2s             -> i_             (); // 93
      case OP_lcmp            -> i_             (); // 94
      case OP_fcmpl           -> i_             (); // 95
      case OP_fcmpg           -> i_             (); // 96
      case OP_dcmpl           -> i_             (); // 97
      case OP_dcmpg           -> i_             (); // 98
      case OP_ifeq            -> i_2j           (); // 99  (2)  branch
      case OP_ifne            -> i_2j           (); // 9a  (2)  branch
      case OP_iflt            -> i_2j           (); // 9b  (2)  branch
      case OP_ifge            -> i_2j           (); // 9c  (2)  branch
      case OP_ifgt            -> i_2j           (); // 9d  (2)  branch
      case OP_ifle            -> i_2j           (); // 9e  (2)  branch
      case OP_if_icmpeq       -> i_2j           (); // 9f  (2)  branch
      case OP_if_icmpne       -> i_2j           (); // a0  (2)  branch
      case OP_if_icmplt       -> i_2j           (); // a1  (2)  branch
      case OP_if_icmpge       -> i_2j           (); // a2  (2)  branch
      case OP_if_icmpgt       -> i_2j           (); // a3  (2)  branch
      case OP_if_icmple       -> i_2j           (); // a4  (2)  branch
      case OP_if_acmpeq       -> i_2j           (); // a5  (2)  branch
      case OP_if_acmpne       -> i_2j           (); // a6  (2)  branch
      case OP_goto            -> i_2j           (); // a7  (2)  branch
      case OP_jsr             -> i_2j           (); // a8  (2)  branch
      case OP_ret             -> i_1v           (); // a9  (1)  lv.index
      case OP_tableswitch     -> i_p_4d_4d_4d_x (); // aa  (0-3,4,4,4,...) padding, default, low, high, jump offsets
      case OP_lookupswitch    -> i_p_4d_4d_x    (); // ab  (0-3,4,4,...)  padding, default, npairs, match/offset pairs
      case OP_ireturn         -> i_             (); // ac
      case OP_lreturn         -> i_             (); // ad
      case OP_freturn         -> i_             (); // ae
      case OP_dreturn         -> i_             (); // af
      case OP_areturn         -> i_             (); // b0
      case OP_return          -> i_             (); // b1
      case OP_getstatic       -> i_2c           (); // b2  (2)  cp.index
      case OP_putstatic       -> i_2c           (); // b3  (2)  cp.index
      case OP_getfield        -> i_2c           (); // b4  (2)  cp.index
      case OP_putfield        -> i_2c           (); // b5  (2)  cp.index
      case OP_invokevirtual   -> i_2c           (); // b6  (2)  cp.index
      case OP_invokespecial   -> i_2c           (); // b7  (2)  cp.index
      case OP_invokestatic    -> i_2c           (); // b8  (2)  cp.index
      case OP_invokeinterface -> i_2c_1d_0      (); // b9  (2,1,0)  cp.index, count, 0
      case OP_invokedynamic   -> i_2c_0_0       (); // ba  (2,0,0)  cp.index, 0, 0
      case OP_new             -> i_2c           (); // bb  (2)  cp.index
      case OP_newarray        -> i_1t           (); // bc  (1)  atype
      case OP_anewarray       -> i_2c           (); // bd  (2)  cp.index
      case OP_arraylength     -> i_             (); // be
      case OP_athrow          -> i_             (); // bf
      case OP_checkcast       -> i_2c           (); // c0  (2)  cp.index
      case OP_instanceof      -> i_2c           (); // c1  (2)  cp.index
      case OP_monitorenter    -> i_             (); // c2
      case OP_monitorexit     -> i_             (); // c3
      case OP_wide            -> i_1w_2v_x      (); // c4  (1,2 | 1,2,2)  opcode, lv.index | iinc, lv.index, count
      case OP_multianewarray  -> i_2c_1d        (); // c5  (2,1)  cp.index, dimensions
      case OP_ifnull          -> i_2j           (); // c6  (2)  branch
      case OP_ifnonnull       -> i_2j           (); // c7  (2)  branch
      case OP_goto_w          -> i_4j           (); // c8  (4)  branch
      case OP_jsr_w           -> i_4j           (); // c9  (4)  branch
      case OP_breakpoint      -> i_             (); // ca
      case OP_impdep1         -> i_             (); // fe
      case OP_impdep2         -> i_             (); // ff
    }
  }

  abstract void i_             (); //  -
  abstract void i_1b           (); //  (1)  byte
  abstract void i_2s           (); //  (2)  short
  abstract void i_1c           (); //  (1)  cp.index
  abstract void i_2c           (); //  (2)  cp.index
  abstract void i_2c_1d        (); //  (2,1)  cp.index, const
  abstract void i_1v           (); //  (1)  lv.index
  abstract void i_1v_1d        (); //  (1,1)  lv.index, const
  abstract void i_1t           (); //  (1)  atype
  abstract void i_2j           (); //  (2)  branch
  abstract void i_4j           (); //  (4)  branch
  abstract void i_2c_1d_0      (); //  (2,1,0)  cp.index, count, 0
  abstract void i_2c_0_0       (); //  (2,0,0)  cp.index, 0, 0

  abstract void i_p_4d_4d_4d_x (); //  (0-3,4,4,4,...) padding, default, low, high, jump offsets
  abstract void i_p_4d_4d_x    (); //  (0-3,4,4,...)  padding, default, npairs, match/offset pairs

  abstract void i_1w_2v_x      (); //  (1,2 | 1,2,2)  opcode, cp.index | iinc, cp.index, count

  void t(String format, Object... args) { t.format(format,args); }

  Byte    u1() { return (Byte)   o.args(); }
  Short   u2() { return (Short)  o.args(); }
  Integer u4() { return (Integer)o.args(); }

  Byte  u1(int i) { return (Byte) ((Object[])o.args())[i]; }
  Short u2(int i) { return (Short)((Object[])o.args())[i]; }

  String n() { return name[o.op() & 0x0ff]; }

  final static String name[];
  static {
    name = new String[256];
    try {
      for (var f:bc.spec.JVMS.class.getFields()) {
        var n = f.getName();
        if (n.startsWith("OP_")) {
          var b = (Byte) f.get(null);
          name[b & 0x0ff] = n.substring(3);
        }
      }
    }
    catch (Exception x) { throw new UnsupportedOperationException(x.toString()); }
  }

  static String wide_op(byte op) {
    return switch(op) {
      case OP_iload, OP_fload, OP_aload, OP_lload, OP_dload,
           OP_istore, OP_fstore, OP_astore, OP_lstore, OP_dstore,
           OP_ret -> name[op & 0x0ff];
      default -> throw new IllegalArgumentException("wide_op="+op);
    };
  }

}
