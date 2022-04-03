package bc.parser;

import bc.parser.Bytecode.Span;
import bc.ClassFile.Opcode;
import static bc.JVMS.*;

class Disasm {

  static Opcode decode(Span a, int base) {
    var pc = (short)( a.p - base );
    var op = a.u1();
    var args = switch(op) {

      case OP_nop,
           OP_aconst_null,
           OP_iconst_m1,
           OP_iconst_0, OP_iconst_1, OP_iconst_2, OP_iconst_3, OP_iconst_4, OP_iconst_5,
           OP_lconst_0, OP_lconst_1,
           OP_fconst_0, OP_fconst_1, OP_fconst_2,
           OP_dconst_0, OP_dconst_1
           -> null; // 00-0f

      case OP_bipush
           -> a.u1(); // 10 (1) byte
      case OP_sipush
           -> a.u2(); // 11 (2) short

      case OP_ldc
           -> a.u1(); // 12 (1) cp.index
      case OP_ldc_w, OP_ldc2_w
           -> a.u2(); // 13-14 (2) cp.index

      case OP_iload, OP_lload, OP_fload, OP_dload, OP_aload
           -> a.u1(); // 15-19 (1) lv.index

      case OP_iload_0, OP_iload_1, OP_iload_2, OP_iload_3,
           OP_lload_0, OP_lload_1, OP_lload_2, OP_lload_3,
           OP_fload_0, OP_fload_1, OP_fload_2, OP_fload_3,
           OP_dload_0, OP_dload_1, OP_dload_2, OP_dload_3,
           OP_aload_0, OP_aload_1, OP_aload_2, OP_aload_3
           -> null; // 1a-2d

      case OP_iaload, OP_laload, OP_faload, OP_daload,
           OP_aaload, OP_baload, OP_caload, OP_saload
           -> null; // 2e-35

      case OP_istore, OP_lstore, OP_fstore, OP_dstore, OP_astore
           -> a.u1(); // 36-3a (1) lv.index

      case OP_istore_0, OP_istore_1, OP_istore_2, OP_istore_3,
           OP_lstore_0, OP_lstore_1, OP_lstore_2, OP_lstore_3,
           OP_fstore_0, OP_fstore_1, OP_fstore_2, OP_fstore_3,
           OP_dstore_0, OP_dstore_1, OP_dstore_2, OP_dstore_3,
           OP_astore_0, OP_astore_1, OP_astore_2, OP_astore_3
           -> null; // 3b-4e

      case OP_iastore, OP_lastore, OP_fastore, OP_dastore,
           OP_aastore, OP_bastore, OP_castore, OP_sastore
           -> null; // 4f-56

      case OP_pop, OP_pop2,
           OP_dup, OP_dup_x1, OP_dup_x2,
           OP_dup2, OP_dup2_x1, OP_dup2_x2,
           OP_swap
           -> null; // 57-5f

      case OP_iadd, OP_ladd, OP_fadd, OP_dadd,
           OP_isub, OP_lsub, OP_fsub, OP_dsub,
           OP_imul, OP_lmul, OP_fmul, OP_dmul,
           OP_idiv, OP_ldiv, OP_fdiv, OP_ddiv,
           OP_irem, OP_lrem, OP_frem, OP_drem,
           OP_ineg, OP_lneg, OP_fneg, OP_dneg
           -> null; // 60-77

      case OP_ishl, OP_lshl,
           OP_ishr, OP_lshr,
           OP_iushr, OP_lushr,
           OP_iand, OP_land,
           OP_ior, OP_lor,
           OP_ixor, OP_lxor
           -> null; // 78-83

      case OP_iinc
           -> new Object[]{ a.u1(), a.u1() }; // 84 (1,1) lv.index, const

      case OP_i2l, OP_i2f, OP_i2d,
           OP_l2i, OP_l2f, OP_l2d,
           OP_f2i, OP_f2l, OP_f2d,
           OP_d2i, OP_d2l, OP_d2f,
           OP_i2b, OP_i2c, OP_i2s
           -> null; // 85-93

      case OP_lcmp,
           OP_fcmpl, OP_fcmpg,
           OP_dcmpl, OP_dcmpg
           -> null; // 94-98

      case OP_ifeq, OP_ifne, OP_iflt, OP_ifge, OP_ifgt, OP_ifle,
           OP_if_icmpeq, OP_if_icmpne, OP_if_icmplt, OP_if_icmpge, OP_if_icmpgt, OP_if_icmple,
           OP_if_acmpeq, OP_if_acmpne
           -> a.u2(); // 99-a6 (2) branch

      case OP_goto, OP_jsr
           -> a.u2(); // a7-a8 (2) branch
      case OP_ret
           -> a.u1(); // a9 (1) lv.index

      case OP_tableswitch -> {
             var pad = pc % 4;
             a.p += pad; // skip padding
             var def = a.u4();
             var low = a.u4();
             var high = a.u4();
             var v = new Integer[4 + ((high - low) + 1)];
             v[0] = pad; v[1] = def; v[2] = low; v[3] = high;
             for (int i = 4, m = v.length; i < m; i++) v[i] = a.u4();
             yield v;
           } // aa (0-3,4,4,4,...) padding, default, low, high, jump offsets

      case OP_lookupswitch -> {
             var pad = pc % 4;
             a.p += pad; // skip padding
             var def = a.u4();
             var npairs = a.u4();
             var v = new Integer[3 + (2 * npairs)];
             v[0] = pad; v[1] = def; v[2] = npairs;
             for (int i = 3, m = v.length; i < m;) { v[i++] = a.u4(); v[i++] = a.u4(); }
             yield v;
           } // ab (0-3,4,4,...) padding, default, npairs, match/offset pairs

      case OP_ireturn, OP_lreturn, OP_freturn, OP_dreturn, OP_areturn, OP_return
           -> null; // ac-b1

      case OP_getstatic, OP_putstatic,
           OP_getfield, OP_putfield,
           OP_invokevirtual, OP_invokespecial, OP_invokestatic
           -> a.u2(); // b2-b8 (2) cp.index

      case OP_invokeinterface -> {
             var o = new Object[]{ a.u2(), a.u1() };
             a.p += 1; // skip last byte
             yield o;
           } // b9 (2,1,0) cp.index, count, 0

      case OP_invokedynamic -> {
             var o = a.u2();
             a.p += 2; // skip last 2 bytes
             yield o;
           } // ba (2,0,0) cp.index, 0, 0

      case OP_new
           -> a.u2(); // bb (2) cp.index
      case OP_newarray
           -> a.u1(); // bc (1) array.type
      case OP_anewarray
           -> a.u2(); // bd (2) cp.index

      case OP_arraylength, OP_athrow
           -> null; // be-bf

      case OP_checkcast, OP_instanceof
           -> a.u2(); // c0-c1 (2) cp.index

      case OP_monitorenter, OP_monitorexit
           -> null; // c2-c3

      case OP_wide -> {
             var w = a.u1();
             yield (w == OP_iinc)
               ? new Object[]{ w, a.u2(), a.u2() }
               : new Object[]{ w, a.u2() };
           } // c4 ( 1,2,2 | 1,2 ) 'iinc', cp.index, count | opcode, cp.index

      case OP_multianewarray
           -> new Object[]{ a.u2(), a.u1() }; // c5 (2,1) cp.index, dimensions

      case OP_ifnull, OP_ifnonnull
           -> a.u2(); // c6-c7 (2) branch

      case OP_goto_w, OP_jsr_w
           -> a.u4(); // c8-c9 (4) branch

      case OP_breakpoint,
           OP_impdep1, OP_impdep2
           -> null; // ca, fe-ff

      default -> throw new IllegalArgumentException("op "+op+" at "+a.p);
    };

    return new Opcode(pc,op,args);
  }

}
