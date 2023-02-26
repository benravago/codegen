package bc.encoder.op;

import bc.CompilationUnit.Code;
import static bc.spec.JVMS.*;

abstract class Instruction {

  public Code nop                                      () { return i_             ( OP_nop                     ); } // 00
  public Code aconst_null                              () { return i_             ( OP_aconst_null             ); } // 01
  public Code iconst_m1                                () { return i_             ( OP_iconst_m1               ); } // 02
  public Code iconst_0                                 () { return i_             ( OP_iconst_0                ); } // 03
  public Code iconst_1                                 () { return i_             ( OP_iconst_1                ); } // 04
  public Code iconst_2                                 () { return i_             ( OP_iconst_2                ); } // 05
  public Code iconst_3                                 () { return i_             ( OP_iconst_3                ); } // 06
  public Code iconst_4                                 () { return i_             ( OP_iconst_4                ); } // 07
  public Code iconst_5                                 () { return i_             ( OP_iconst_5                ); } // 08
  public Code lconst_0                                 () { return i_             ( OP_lconst_0                ); } // 09
  public Code lconst_1                                 () { return i_             ( OP_lconst_1                ); } // 0a
  public Code fconst_0                                 () { return i_             ( OP_fconst_0                ); } // 0b
  public Code fconst_1                                 () { return i_             ( OP_fconst_1                ); } // 0c
  public Code fconst_2                                 () { return i_             ( OP_fconst_2                ); } // 0d
  public Code dconst_0                                 () { return i_             ( OP_dconst_0                ); } // 0e
  public Code dconst_1                                 () { return i_             ( OP_dconst_1                ); } // 0f
  public Code bipush                             (byte b) { return i_1b           ( OP_bipush, b               ); } // 10  (1)  byte
  public Code sipush                            (short s) { return i_2s           ( OP_sipush, s               ); } // 11  (2)  short
  public Code ldc                                (byte c) { return i_1c           ( OP_ldc, c                  ); } // 12  (1)  cp.index
  public Code ldc_w                             (short c) { return i_2c           ( OP_ldc_w, c                ); } // 13  (2)  cp.index
  public Code ldc2_w                            (short c) { return i_2c           ( OP_ldc2_w, c               ); } // 14  (2)  cp.index
  public Code iload                              (byte v) { return i_1v           ( OP_iload, v                ); } // 15  (1)  lv.index
  public Code lload                              (byte v) { return i_1v           ( OP_lload, v                ); } // 16  (1)  lv.index
  public Code fload                              (byte v) { return i_1v           ( OP_fload, v                ); } // 17  (1)  lv.index
  public Code dload                              (byte v) { return i_1v           ( OP_dload, v                ); } // 18  (1)  lv.index
  public Code aload                              (byte v) { return i_1v           ( OP_aload, v                ); } // 19  (1)  lv.index
  public Code iload_0                                  () { return i_             ( OP_iload_0                 ); } // 1a
  public Code iload_1                                  () { return i_             ( OP_iload_1                 ); } // 1b
  public Code iload_2                                  () { return i_             ( OP_iload_2                 ); } // 1c
  public Code iload_3                                  () { return i_             ( OP_iload_3                 ); } // 1d
  public Code lload_0                                  () { return i_             ( OP_lload_0                 ); } // 1e
  public Code lload_1                                  () { return i_             ( OP_lload_1                 ); } // 1f
  public Code lload_2                                  () { return i_             ( OP_lload_2                 ); } // 20
  public Code lload_3                                  () { return i_             ( OP_lload_3                 ); } // 21
  public Code fload_0                                  () { return i_             ( OP_fload_0                 ); } // 22
  public Code fload_1                                  () { return i_             ( OP_fload_1                 ); } // 23
  public Code fload_2                                  () { return i_             ( OP_fload_2                 ); } // 24
  public Code fload_3                                  () { return i_             ( OP_fload_3                 ); } // 25
  public Code dload_0                                  () { return i_             ( OP_dload_0                 ); } // 26
  public Code dload_1                                  () { return i_             ( OP_dload_1                 ); } // 27
  public Code dload_2                                  () { return i_             ( OP_dload_2                 ); } // 28
  public Code dload_3                                  () { return i_             ( OP_dload_3                 ); } // 29
  public Code aload_0                                  () { return i_             ( OP_aload_0                 ); } // 2a
  public Code aload_1                                  () { return i_             ( OP_aload_1                 ); } // 2b
  public Code aload_2                                  () { return i_             ( OP_aload_2                 ); } // 2c
  public Code aload_3                                  () { return i_             ( OP_aload_3                 ); } // 2d
  public Code iaload                                   () { return i_             ( OP_iaload                  ); } // 2e
  public Code laload                                   () { return i_             ( OP_laload                  ); } // 2f
  public Code faload                                   () { return i_             ( OP_faload                  ); } // 30
  public Code daload                                   () { return i_             ( OP_daload                  ); } // 31
  public Code aaload                                   () { return i_             ( OP_aaload                  ); } // 32
  public Code baload                                   () { return i_             ( OP_baload                  ); } // 33
  public Code caload                                   () { return i_             ( OP_caload                  ); } // 34
  public Code saload                                   () { return i_             ( OP_saload                  ); } // 35
  public Code istore                             (byte v) { return i_1v           ( OP_istore, v               ); } // 36  (1)  lv.index
  public Code lstore                             (byte v) { return i_1v           ( OP_lstore, v               ); } // 37  (1)  lv.index
  public Code fstore                             (byte v) { return i_1v           ( OP_fstore, v               ); } // 38  (1)  lv.index
  public Code dstore                             (byte v) { return i_1v           ( OP_dstore, v               ); } // 39  (1)  lv.index
  public Code astore                             (byte v) { return i_1v           ( OP_astore, v               ); } // 3a  (1)  lv.index
  public Code istore_0                                 () { return i_             ( OP_istore_0                ); } // 3b
  public Code istore_1                                 () { return i_             ( OP_istore_1                ); } // 3c
  public Code istore_2                                 () { return i_             ( OP_istore_2                ); } // 3d
  public Code istore_3                                 () { return i_             ( OP_istore_3                ); } // 3e
  public Code lstore_0                                 () { return i_             ( OP_lstore_0                ); } // 3f
  public Code lstore_1                                 () { return i_             ( OP_lstore_1                ); } // 40
  public Code lstore_2                                 () { return i_             ( OP_lstore_2                ); } // 41
  public Code lstore_3                                 () { return i_             ( OP_lstore_3                ); } // 42
  public Code fstore_0                                 () { return i_             ( OP_fstore_0                ); } // 43
  public Code fstore_1                                 () { return i_             ( OP_fstore_1                ); } // 44
  public Code fstore_2                                 () { return i_             ( OP_fstore_2                ); } // 45
  public Code fstore_3                                 () { return i_             ( OP_fstore_3                ); } // 46
  public Code dstore_0                                 () { return i_             ( OP_dstore_0                ); } // 47
  public Code dstore_1                                 () { return i_             ( OP_dstore_1                ); } // 48
  public Code dstore_2                                 () { return i_             ( OP_dstore_2                ); } // 49
  public Code dstore_3                                 () { return i_             ( OP_dstore_3                ); } // 4a
  public Code astore_0                                 () { return i_             ( OP_astore_0                ); } // 4b
  public Code astore_1                                 () { return i_             ( OP_astore_1                ); } // 4c
  public Code astore_2                                 () { return i_             ( OP_astore_2                ); } // 4d
  public Code astore_3                                 () { return i_             ( OP_astore_3                ); } // 4e
  public Code iastore                                  () { return i_             ( OP_iastore                 ); } // 4f
  public Code lastore                                  () { return i_             ( OP_lastore                 ); } // 50
  public Code fastore                                  () { return i_             ( OP_fastore                 ); } // 51
  public Code dastore                                  () { return i_             ( OP_dastore                 ); } // 52
  public Code aastore                                  () { return i_             ( OP_aastore                 ); } // 53
  public Code bastore                                  () { return i_             ( OP_bastore                 ); } // 54
  public Code castore                                  () { return i_             ( OP_castore                 ); } // 55
  public Code sastore                                  () { return i_             ( OP_sastore                 ); } // 56
  public Code pop                                      () { return i_             ( OP_pop                     ); } // 57
  public Code pop2                                     () { return i_             ( OP_pop2                    ); } // 58
  public Code dup                                      () { return i_             ( OP_dup                     ); } // 59
  public Code dup_x1                                   () { return i_             ( OP_dup_x1                  ); } // 5a
  public Code dup_x2                                   () { return i_             ( OP_dup_x2                  ); } // 5b
  public Code dup2                                     () { return i_             ( OP_dup2                    ); } // 5c
  public Code dup2_x1                                  () { return i_             ( OP_dup2_x1                 ); } // 5d
  public Code dup2_x2                                  () { return i_             ( OP_dup2_x2                 ); } // 5e
  public Code swap                                     () { return i_             ( OP_swap                    ); } // 5f
  public Code iadd                                     () { return i_             ( OP_iadd                    ); } // 60
  public Code ladd                                     () { return i_             ( OP_ladd                    ); } // 61
  public Code fadd                                     () { return i_             ( OP_fadd                    ); } // 62
  public Code dadd                                     () { return i_             ( OP_dadd                    ); } // 63
  public Code isub                                     () { return i_             ( OP_isub                    ); } // 64
  public Code lsub                                     () { return i_             ( OP_lsub                    ); } // 65
  public Code fsub                                     () { return i_             ( OP_fsub                    ); } // 66
  public Code dsub                                     () { return i_             ( OP_dsub                    ); } // 67
  public Code imul                                     () { return i_             ( OP_imul                    ); } // 68
  public Code lmul                                     () { return i_             ( OP_lmul                    ); } // 69
  public Code fmul                                     () { return i_             ( OP_fmul                    ); } // 6a
  public Code dmul                                     () { return i_             ( OP_dmul                    ); } // 6b
  public Code idiv                                     () { return i_             ( OP_idiv                    ); } // 6c
  public Code ldiv                                     () { return i_             ( OP_ldiv                    ); } // 6d
  public Code fdiv                                     () { return i_             ( OP_fdiv                    ); } // 6e
  public Code ddiv                                     () { return i_             ( OP_ddiv                    ); } // 6f
  public Code irem                                     () { return i_             ( OP_irem                    ); } // 70
  public Code lrem                                     () { return i_             ( OP_lrem                    ); } // 71
  public Code frem                                     () { return i_             ( OP_frem                    ); } // 72
  public Code drem                                     () { return i_             ( OP_drem                    ); } // 73
  public Code ineg                                     () { return i_             ( OP_ineg                    ); } // 74
  public Code lneg                                     () { return i_             ( OP_lneg                    ); } // 75
  public Code fneg                                     () { return i_             ( OP_fneg                    ); } // 76
  public Code dneg                                     () { return i_             ( OP_dneg                    ); } // 77
  public Code ishl                                     () { return i_             ( OP_ishl                    ); } // 78
  public Code lshl                                     () { return i_             ( OP_lshl                    ); } // 79
  public Code ishr                                     () { return i_             ( OP_ishr                    ); } // 7a
  public Code lshr                                     () { return i_             ( OP_lshr                    ); } // 7b
  public Code iushr                                    () { return i_             ( OP_iushr                   ); } // 7c
  public Code lushr                                    () { return i_             ( OP_lushr                   ); } // 7d
  public Code iand                                     () { return i_             ( OP_iand                    ); } // 7e
  public Code land                                     () { return i_             ( OP_land                    ); } // 7f
  public Code ior                                      () { return i_             ( OP_ior                     ); } // 80
  public Code lor                                      () { return i_             ( OP_lor                     ); } // 81
  public Code ixor                                     () { return i_             ( OP_ixor                    ); } // 82
  public Code lxor                                     () { return i_             ( OP_lxor                    ); } // 83
  public Code iinc                       (byte v, byte d) { return i_1v_1d        ( OP_iinc, v, d              ); } // 84  (1,1)  lv.index, const
  public Code i2l                                      () { return i_             ( OP_i2l                     ); } // 85
  public Code i2f                                      () { return i_             ( OP_i2f                     ); } // 86
  public Code i2d                                      () { return i_             ( OP_i2d                     ); } // 87
  public Code l2i                                      () { return i_             ( OP_l2i                     ); } // 88
  public Code l2f                                      () { return i_             ( OP_l2f                     ); } // 89
  public Code l2d                                      () { return i_             ( OP_l2d                     ); } // 8a
  public Code f2i                                      () { return i_             ( OP_f2i                     ); } // 8b
  public Code f2l                                      () { return i_             ( OP_f2l                     ); } // 8c
  public Code f2d                                      () { return i_             ( OP_f2d                     ); } // 8d
  public Code d2i                                      () { return i_             ( OP_d2i                     ); } // 8e
  public Code d2l                                      () { return i_             ( OP_d2l                     ); } // 8f
  public Code d2f                                      () { return i_             ( OP_d2f                     ); } // 90
  public Code i2b                                      () { return i_             ( OP_i2b                     ); } // 91
  public Code i2c                                      () { return i_             ( OP_i2c                     ); } // 92
  public Code i2s                                      () { return i_             ( OP_i2s                     ); } // 93
  public Code lcmp                                     () { return i_             ( OP_lcmp                    ); } // 94
  public Code fcmpl                                    () { return i_             ( OP_fcmpl                   ); } // 95
  public Code fcmpg                                    () { return i_             ( OP_fcmpg                   ); } // 96
  public Code dcmpl                                    () { return i_             ( OP_dcmpl                   ); } // 97
  public Code dcmpg                                    () { return i_             ( OP_dcmpg                   ); } // 98
  public Code ifeq                              (short j) { return i_2j           ( OP_ifeq, j                 ); } // 99  (2)  branch
  public Code ifne                              (short j) { return i_2j           ( OP_ifne, j                 ); } // 9a  (2)  branch
  public Code iflt                              (short j) { return i_2j           ( OP_iflt, j                 ); } // 9b  (2)  branch
  public Code ifge                              (short j) { return i_2j           ( OP_ifge, j                 ); } // 9c  (2)  branch
  public Code ifgt                              (short j) { return i_2j           ( OP_ifgt, j                 ); } // 9d  (2)  branch
  public Code ifle                              (short j) { return i_2j           ( OP_ifle, j                 ); } // 9e  (2)  branch
  public Code if_icmpeq                         (short j) { return i_2j           ( OP_if_icmpeq, j            ); } // 9f  (2)  branch
  public Code if_icmpne                         (short j) { return i_2j           ( OP_if_icmpne, j            ); } // a0  (2)  branch
  public Code if_icmplt                         (short j) { return i_2j           ( OP_if_icmplt, j            ); } // a1  (2)  branch
  public Code if_icmpge                         (short j) { return i_2j           ( OP_if_icmpge, j            ); } // a2  (2)  branch
  public Code if_icmpgt                         (short j) { return i_2j           ( OP_if_icmpgt, j            ); } // a3  (2)  branch
  public Code if_icmple                         (short j) { return i_2j           ( OP_if_icmple, j            ); } // a4  (2)  branch
  public Code if_acmpeq                         (short j) { return i_2j           ( OP_if_acmpeq, j            ); } // a5  (2)  branch
  public Code if_acmpne                         (short j) { return i_2j           ( OP_if_acmpne, j            ); } // a6  (2)  branch
  public Code goto_v                            (short j) { return i_2j           ( OP_goto, j                 ); } // a7  (2)  branch
  public Code jsr                               (short j) { return i_2j           ( OP_jsr, j                  ); } // a8  (2)  branch
  public Code ret                                (byte v) { return i_1v           ( OP_ret, v                  ); } // a9  (1)  lv.index
  public Code tableswitch  (int d, int l, int h, int...j) { return i_p_4d_4l_4h_x ( OP_tableswitch, d, l, h, j ); } // aa  (0-3,4,4,4,...) padding, default, low, high, jump offsets
  public Code lookupswitch       (int d, int n, int...mo) { return i_p_4d_4n_x    ( OP_lookupswitch, d, n, mo  ); } // ab  (0-3,4,4,...)  padding, default, npairs, match/offset pairs
  public Code ireturn                                  () { return i_             ( OP_ireturn                 ); } // ac
  public Code lreturn                                  () { return i_             ( OP_lreturn                 ); } // ad
  public Code freturn                                  () { return i_             ( OP_freturn                 ); } // ae
  public Code dreturn                                  () { return i_             ( OP_dreturn                 ); } // af
  public Code areturn                                  () { return i_             ( OP_areturn                 ); } // b0
  public Code vreturn                                  () { return i_             ( OP_return                  ); } // b1
  public Code getstatic                         (short c) { return i_2c           ( OP_getstatic, c            ); } // b2  (2)  cp.index
  public Code putstatic                         (short c) { return i_2c           ( OP_putstatic, c            ); } // b3  (2)  cp.index
  public Code getfield                          (short c) { return i_2c           ( OP_getfield, c             ); } // b4  (2)  cp.index
  public Code putfield                          (short c) { return i_2c           ( OP_putfield, c             ); } // b5  (2)  cp.index
  public Code invokevirtual                     (short c) { return i_2c           ( OP_invokevirtual, c        ); } // b6  (2)  cp.index
  public Code invokespecial                     (short c) { return i_2c           ( OP_invokespecial, c        ); } // b7  (2)  cp.index
  public Code invokestatic                      (short c) { return i_2c           ( OP_invokestatic, c         ); } // b8  (2)  cp.index
  public Code invokeinterface           (short c, byte d) { return i_2c_1d_0      ( OP_invokeinterface, c, d   ); } // b9  (2,1,0)  cp.index, count, 0
  public Code invokedynamic                     (short c) { return i_2c_0_0       ( OP_invokedynamic, c        ); } // ba  (2,0,0)  cp.index, 0, 0
  public Code new_v                             (short c) { return i_2c           ( OP_new, c                  ); } // bb  (2)  cp.index
  public Code newarray                           (byte t) { return i_1t           ( OP_newarray, t             ); } // bc  (1)  atype
  public Code anewarray                         (short c) { return i_2c           ( OP_anewarray, c            ); } // bd  (2)  cp.index
  public Code arraylength                              () { return i_             ( OP_arraylength             ); } // be
  public Code athrow                                   () { return i_             ( OP_athrow                  ); } // bf
  public Code checkcast                         (short c) { return i_2c           ( OP_checkcast, c            ); } // c0  (2)  cp.index
  public Code instance_of                       (short c) { return i_2c           ( OP_instanceof, c           ); } // c1  (2)  cp.index
  public Code monitorenter                             () { return i_             ( OP_monitorenter            ); } // c2
  public Code monitorexit                              () { return i_             ( OP_monitorexit             ); } // c3
  public Code wide           (byte w, short v, short...d) { return i_1w_2v_d      ( OP_wide, w, v, d           ); } // c4  (1,2 | 1,2,2)  opcode, lv.index | iinc, lv.index, count
  public Code multianewarray            (short c, byte d) { return i_2c_1d        ( OP_multianewarray, c, d    ); } // c5  (2,1)  cp.index, dimensions
  public Code ifnull                            (short j) { return i_2j           ( OP_ifnull, j               ); } // c6  (2)  branch
  public Code ifnonnull                         (short j) { return i_2j           ( OP_ifnonnull, j            ); } // c7  (2)  branch
  public Code goto_w                              (int j) { return i_4j           ( OP_goto_w, j               ); } // c8  (4)  branch
  public Code jsr_w                               (int j) { return i_4j           ( OP_jsr_w, j                ); } // c9  (4)  branch
  public Code breakpoint                               () { return i_             ( OP_breakpoint              ); } // ca
  public Code impdep1                                  () { return i_             ( OP_impdep1                 ); } // fe
  public Code impdep2                                  () { return i_             ( OP_impdep2                 ); } // ff


  abstract Code i_             (byte o);                               //  -

  abstract Code i_1b           (byte o, byte b);                       //  (1)  byte
  abstract Code i_2s           (byte o, short s);                      //  (2)  short

  abstract Code i_1c           (byte o, byte c);                       //  (1)  cp.index
  abstract Code i_2c           (byte o, short c);                      //  (2)  cp.index
  abstract Code i_2c_1d        (byte o, short c, byte d);              //  (2,1)  cp.index, const

  abstract Code i_1v           (byte o, byte v);                       //  (1)  lv.index
  abstract Code i_1v_1d        (byte o, byte v, byte d);               //  (1,1)  lv.index, const

  abstract Code i_1t           (byte o, byte t);                       //  (1)  atype

  abstract Code i_2j           (byte o, short j);                      //  (2)  branch
  abstract Code i_4j           (byte o, int j);                        //  (4)  branch

  abstract Code i_2c_1d_0      (byte o, short c, byte d);              //  (2,1,0)  cp.index, count, 0
  abstract Code i_2c_0_0       (byte o, short c);                      //  (2,0,0)  cp.index, 0, 0

  abstract Code i_p_4d_4l_4h_x (byte o, int d, int l, int h, int...j); //  (0-3,4,4,4,...)  padding, default, low, high, jump offsets
  abstract Code i_p_4d_4n_x    (byte o, int d, int n, int...mo);       //  (0-3,4,4,...)  padding, default, npairs, match/offset pairs

  abstract Code i_1w_2v_d      (byte o, byte w, short v, short...n);   //  (1,2 | 1,2,2)  opcode, lv.index | iinc, lv.index, count

}
