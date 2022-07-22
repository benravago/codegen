package bc.builder;

import bc.CompilationUnit.Code;
import static bc.JVMS.*;

abstract class Instruction {

  public Code _nop                               () { return i_             ( OP_nop                   ); } // 00
  public Code _aconst_null                       () { return i_             ( OP_aconst_null           ); } // 01
  public Code _iconst_m1                         () { return i_             ( OP_iconst_m1             ); } // 02
  public Code _iconst_0                          () { return i_             ( OP_iconst_0              ); } // 03
  public Code _iconst_1                          () { return i_             ( OP_iconst_1              ); } // 04
  public Code _iconst_2                          () { return i_             ( OP_iconst_2              ); } // 05
  public Code _iconst_3                          () { return i_             ( OP_iconst_3              ); } // 06
  public Code _iconst_4                          () { return i_             ( OP_iconst_4              ); } // 07
  public Code _iconst_5                          () { return i_             ( OP_iconst_5              ); } // 08
  public Code _lconst_0                          () { return i_             ( OP_lconst_0              ); } // 09
  public Code _lconst_1                          () { return i_             ( OP_lconst_1              ); } // 0a
  public Code _fconst_0                          () { return i_             ( OP_fconst_0              ); } // 0b
  public Code _fconst_1                          () { return i_             ( OP_fconst_1              ); } // 0c
  public Code _fconst_2                          () { return i_             ( OP_fconst_2              ); } // 0d
  public Code _dconst_0                          () { return i_             ( OP_dconst_0              ); } // 0e
  public Code _dconst_1                          () { return i_             ( OP_dconst_1              ); } // 0f
  public Code _bipush                      (byte b) { return i_1b           ( OP_bipush, b             ); } // 10  (1)  byte
  public Code _sipush                     (short s) { return i_2s           ( OP_sipush, s             ); } // 11  (2)  short
  public Code _ldc                     (Object...c) { return i_1c           ( OP_ldc, c                ); } // 12  (1)  cp.index
  public Code _ldc_w                   (Object...c) { return i_2c           ( OP_ldc_w, c              ); } // 13  (2)  cp.index
  public Code _ldc2_w                  (Object...c) { return i_2c           ( OP_ldc2_w, c             ); } // 14  (2)  cp.index
  public Code _iload                        (int v) { return i_1v           ( OP_iload, v              ); } // 15  (1)  lv.index
  public Code _lload                        (int v) { return i_1v           ( OP_lload, v              ); } // 16  (1)  lv.index
  public Code _fload                        (int v) { return i_1v           ( OP_fload, v              ); } // 17  (1)  lv.index
  public Code _dload                        (int v) { return i_1v           ( OP_dload, v              ); } // 18  (1)  lv.index
  public Code _aload                        (int v) { return i_1v           ( OP_aload, v              ); } // 19  (1)  lv.index
  public Code _iload_0                           () { return i_             ( OP_iload_0               ); } // 1a
  public Code _iload_1                           () { return i_             ( OP_iload_1               ); } // 1b
  public Code _iload_2                           () { return i_             ( OP_iload_2               ); } // 1c
  public Code _iload_3                           () { return i_             ( OP_iload_3               ); } // 1d
  public Code _lload_0                           () { return i_             ( OP_lload_0               ); } // 1e
  public Code _lload_1                           () { return i_             ( OP_lload_1               ); } // 1f
  public Code _lload_2                           () { return i_             ( OP_lload_2               ); } // 20
  public Code _lload_3                           () { return i_             ( OP_lload_3               ); } // 21
  public Code _fload_0                           () { return i_             ( OP_fload_0               ); } // 22
  public Code _fload_1                           () { return i_             ( OP_fload_1               ); } // 23
  public Code _fload_2                           () { return i_             ( OP_fload_2               ); } // 24
  public Code _fload_3                           () { return i_             ( OP_fload_3               ); } // 25
  public Code _dload_0                           () { return i_             ( OP_dload_0               ); } // 26
  public Code _dload_1                           () { return i_             ( OP_dload_1               ); } // 27
  public Code _dload_2                           () { return i_             ( OP_dload_2               ); } // 28
  public Code _dload_3                           () { return i_             ( OP_dload_3               ); } // 29
  public Code _aload_0                           () { return i_             ( OP_aload_0               ); } // 2a
  public Code _aload_1                           () { return i_             ( OP_aload_1               ); } // 2b
  public Code _aload_2                           () { return i_             ( OP_aload_2               ); } // 2c
  public Code _aload_3                           () { return i_             ( OP_aload_3               ); } // 2d
  public Code _iaload                            () { return i_             ( OP_iaload                ); } // 2e
  public Code _laload                            () { return i_             ( OP_laload                ); } // 2f
  public Code _faload                            () { return i_             ( OP_faload                ); } // 30
  public Code _daload                            () { return i_             ( OP_daload                ); } // 31
  public Code _aaload                            () { return i_             ( OP_aaload                ); } // 32
  public Code _baload                            () { return i_             ( OP_baload                ); } // 33
  public Code _caload                            () { return i_             ( OP_caload                ); } // 34
  public Code _saload                            () { return i_             ( OP_saload                ); } // 35
  public Code _istore                       (int v) { return i_1v           ( OP_istore, v             ); } // 36  (1)  lv.index
  public Code _lstore                       (int v) { return i_1v           ( OP_lstore, v             ); } // 37  (1)  lv.index
  public Code _fstore                       (int v) { return i_1v           ( OP_fstore, v             ); } // 38  (1)  lv.index
  public Code _dstore                       (int v) { return i_1v           ( OP_dstore, v             ); } // 39  (1)  lv.index
  public Code _astore                       (int v) { return i_1v           ( OP_astore, v             ); } // 3a  (1)  lv.index
  public Code _istore_0                          () { return i_             ( OP_istore_0              ); } // 3b
  public Code _istore_1                          () { return i_             ( OP_istore_1              ); } // 3c
  public Code _istore_2                          () { return i_             ( OP_istore_2              ); } // 3d
  public Code _istore_3                          () { return i_             ( OP_istore_3              ); } // 3e
  public Code _lstore_0                          () { return i_             ( OP_lstore_0              ); } // 3f
  public Code _lstore_1                          () { return i_             ( OP_lstore_1              ); } // 40
  public Code _lstore_2                          () { return i_             ( OP_lstore_2              ); } // 41
  public Code _lstore_3                          () { return i_             ( OP_lstore_3              ); } // 42
  public Code _fstore_0                          () { return i_             ( OP_fstore_0              ); } // 43
  public Code _fstore_1                          () { return i_             ( OP_fstore_1              ); } // 44
  public Code _fstore_2                          () { return i_             ( OP_fstore_2              ); } // 45
  public Code _fstore_3                          () { return i_             ( OP_fstore_3              ); } // 46
  public Code _dstore_0                          () { return i_             ( OP_dstore_0              ); } // 47
  public Code _dstore_1                          () { return i_             ( OP_dstore_1              ); } // 48
  public Code _dstore_2                          () { return i_             ( OP_dstore_2              ); } // 49
  public Code _dstore_3                          () { return i_             ( OP_dstore_3              ); } // 4a
  public Code _astore_0                          () { return i_             ( OP_astore_0              ); } // 4b
  public Code _astore_1                          () { return i_             ( OP_astore_1              ); } // 4c
  public Code _astore_2                          () { return i_             ( OP_astore_2              ); } // 4d
  public Code _astore_3                          () { return i_             ( OP_astore_3              ); } // 4e
  public Code _iastore                           () { return i_             ( OP_iastore               ); } // 4f
  public Code _lastore                           () { return i_             ( OP_lastore               ); } // 50
  public Code _fastore                           () { return i_             ( OP_fastore               ); } // 51
  public Code _dastore                           () { return i_             ( OP_dastore               ); } // 52
  public Code _aastore                           () { return i_             ( OP_aastore               ); } // 53
  public Code _bastore                           () { return i_             ( OP_bastore               ); } // 54
  public Code _castore                           () { return i_             ( OP_castore               ); } // 55
  public Code _sastore                           () { return i_             ( OP_sastore               ); } // 56
  public Code _pop                               () { return i_             ( OP_pop                   ); } // 57
  public Code _pop2                              () { return i_             ( OP_pop2                  ); } // 58
  public Code _dup                               () { return i_             ( OP_dup                   ); } // 59
  public Code _dup_x1                            () { return i_             ( OP_dup_x1                ); } // 5a
  public Code _dup_x2                            () { return i_             ( OP_dup_x2                ); } // 5b
  public Code _dup2                              () { return i_             ( OP_dup2                  ); } // 5c
  public Code _dup2_x1                           () { return i_             ( OP_dup2_x1               ); } // 5d
  public Code _dup2_x2                           () { return i_             ( OP_dup2_x2               ); } // 5e
  public Code _swap                              () { return i_             ( OP_swap                  ); } // 5f
  public Code _iadd                              () { return i_             ( OP_iadd                  ); } // 60
  public Code _ladd                              () { return i_             ( OP_ladd                  ); } // 61
  public Code _fadd                              () { return i_             ( OP_fadd                  ); } // 62
  public Code _dadd                              () { return i_             ( OP_dadd                  ); } // 63
  public Code _isub                              () { return i_             ( OP_isub                  ); } // 64
  public Code _lsub                              () { return i_             ( OP_lsub                  ); } // 65
  public Code _fsub                              () { return i_             ( OP_fsub                  ); } // 66
  public Code _dsub                              () { return i_             ( OP_dsub                  ); } // 67
  public Code _imul                              () { return i_             ( OP_imul                  ); } // 68
  public Code _lmul                              () { return i_             ( OP_lmul                  ); } // 69
  public Code _fmul                              () { return i_             ( OP_fmul                  ); } // 6a
  public Code _dmul                              () { return i_             ( OP_dmul                  ); } // 6b
  public Code _idiv                              () { return i_             ( OP_idiv                  ); } // 6c
  public Code _ldiv                              () { return i_             ( OP_ldiv                  ); } // 6d
  public Code _fdiv                              () { return i_             ( OP_fdiv                  ); } // 6e
  public Code _ddiv                              () { return i_             ( OP_ddiv                  ); } // 6f
  public Code _irem                              () { return i_             ( OP_irem                  ); } // 70
  public Code _lrem                              () { return i_             ( OP_lrem                  ); } // 71
  public Code _frem                              () { return i_             ( OP_frem                  ); } // 72
  public Code _drem                              () { return i_             ( OP_drem                  ); } // 73
  public Code _ineg                              () { return i_             ( OP_ineg                  ); } // 74
  public Code _lneg                              () { return i_             ( OP_lneg                  ); } // 75
  public Code _fneg                              () { return i_             ( OP_fneg                  ); } // 76
  public Code _dneg                              () { return i_             ( OP_dneg                  ); } // 77
  public Code _ishl                              () { return i_             ( OP_ishl                  ); } // 78
  public Code _lshl                              () { return i_             ( OP_lshl                  ); } // 79
  public Code _ishr                              () { return i_             ( OP_ishr                  ); } // 7a
  public Code _lshr                              () { return i_             ( OP_lshr                  ); } // 7b
  public Code _iushr                             () { return i_             ( OP_iushr                 ); } // 7c
  public Code _lushr                             () { return i_             ( OP_lushr                 ); } // 7d
  public Code _iand                              () { return i_             ( OP_iand                  ); } // 7e
  public Code _land                              () { return i_             ( OP_land                  ); } // 7f
  public Code _ior                               () { return i_             ( OP_ior                   ); } // 80
  public Code _lor                               () { return i_             ( OP_lor                   ); } // 81
  public Code _ixor                              () { return i_             ( OP_ixor                  ); } // 82
  public Code _lxor                              () { return i_             ( OP_lxor                  ); } // 83
  public Code _iinc                 (int v, byte d) { return i_1v_1d        ( OP_iinc, v, d            ); } // 84  (1,1)  lv.index, const
  public Code _i2l                               () { return i_             ( OP_i2l                   ); } // 85
  public Code _i2f                               () { return i_             ( OP_i2f                   ); } // 86
  public Code _i2d                               () { return i_             ( OP_i2d                   ); } // 87
  public Code _l2i                               () { return i_             ( OP_l2i                   ); } // 88
  public Code _l2f                               () { return i_             ( OP_l2f                   ); } // 89
  public Code _l2d                               () { return i_             ( OP_l2d                   ); } // 8a
  public Code _f2i                               () { return i_             ( OP_f2i                   ); } // 8b
  public Code _f2l                               () { return i_             ( OP_f2l                   ); } // 8c
  public Code _f2d                               () { return i_             ( OP_f2d                   ); } // 8d
  public Code _d2i                               () { return i_             ( OP_d2i                   ); } // 8e
  public Code _d2l                               () { return i_             ( OP_d2l                   ); } // 8f
  public Code _d2f                               () { return i_             ( OP_d2f                   ); } // 90
  public Code _i2b                               () { return i_             ( OP_i2b                   ); } // 91
  public Code _i2c                               () { return i_             ( OP_i2c                   ); } // 92
  public Code _i2s                               () { return i_             ( OP_i2s                   ); } // 93
  public Code _lcmp                              () { return i_             ( OP_lcmp                  ); } // 94
  public Code _fcmpl                             () { return i_             ( OP_fcmpl                 ); } // 95
  public Code _fcmpg                             () { return i_             ( OP_fcmpg                 ); } // 96
  public Code _dcmpl                             () { return i_             ( OP_dcmpl                 ); } // 97
  public Code _dcmpg                             () { return i_             ( OP_dcmpg                 ); } // 98
  public Code _ifeq                         (int j) { return i_2j           ( OP_ifeq, j               ); } // 99  (2)  branch
  public Code _ifne                         (int j) { return i_2j           ( OP_ifne, j               ); } // 9a  (2)  branch
  public Code _iflt                         (int j) { return i_2j           ( OP_iflt, j               ); } // 9b  (2)  branch
  public Code _ifge                         (int j) { return i_2j           ( OP_ifge, j               ); } // 9c  (2)  branch
  public Code _ifgt                         (int j) { return i_2j           ( OP_ifgt, j               ); } // 9d  (2)  branch
  public Code _ifle                         (int j) { return i_2j           ( OP_ifle, j               ); } // 9e  (2)  branch
  public Code _if_icmpeq                    (int j) { return i_2j           ( OP_if_icmpeq, j          ); } // 9f  (2)  branch
  public Code _if_icmpne                    (int j) { return i_2j           ( OP_if_icmpne, j          ); } // a0  (2)  branch
  public Code _if_icmplt                    (int j) { return i_2j           ( OP_if_icmplt, j          ); } // a1  (2)  branch
  public Code _if_icmpge                    (int j) { return i_2j           ( OP_if_icmpge, j          ); } // a2  (2)  branch
  public Code _if_icmpgt                    (int j) { return i_2j           ( OP_if_icmpgt, j          ); } // a3  (2)  branch
  public Code _if_icmple                    (int j) { return i_2j           ( OP_if_icmple, j          ); } // a4  (2)  branch
  public Code _if_acmpeq                    (int j) { return i_2j           ( OP_if_acmpeq, j          ); } // a5  (2)  branch
  public Code _if_acmpne                    (int j) { return i_2j           ( OP_if_acmpne, j          ); } // a6  (2)  branch
  public Code _goto                         (int j) { return i_2j           ( OP_goto, j               ); } // a7  (2)  branch
  public Code _jsr                          (int j) { return i_2j           ( OP_jsr, j                ); } // a8  (2)  branch
  public Code _ret                          (int v) { return i_1v           ( OP_ret, v                ); } // a9  (1)  lv.index
  public Code _tableswitch                (int...a) { return i_p_4d_4d_4d_x ( OP_tableswitch, a        ); } // aa  (0-3,4,4,4,...) padding, default, low, high, jump offsets
  public Code _lookupswitch               (int...a) { return i_p_4d_4d_x    ( OP_lookupswitch, a       ); } // ab  (0-3,4,4,...)  padding, default, npairs, match/offset pairs
  public Code _ireturn                           () { return i_             ( OP_ireturn               ); } // ac
  public Code _lreturn                           () { return i_             ( OP_lreturn               ); } // ad
  public Code _freturn                           () { return i_             ( OP_freturn               ); } // ae
  public Code _dreturn                           () { return i_             ( OP_dreturn               ); } // af
  public Code _areturn                           () { return i_             ( OP_areturn               ); } // b0
  public Code _return                            () { return i_             ( OP_return                ); } // b1
  public Code _getstatic               (Object...c) { return i_2c           ( OP_getstatic, c          ); } // b2  (2)  cp.index
  public Code _putstatic               (Object...c) { return i_2c           ( OP_putstatic, c          ); } // b3  (2)  cp.index
  public Code _getfield                (Object...c) { return i_2c           ( OP_getfield, c           ); } // b4  (2)  cp.index
  public Code _putfield                (Object...c) { return i_2c           ( OP_putfield, c           ); } // b5  (2)  cp.index
  public Code _invokevirtual           (Object...c) { return i_2c           ( OP_invokevirtual, c      ); } // b6  (2)  cp.index
  public Code _invokespecial           (Object...c) { return i_2c           ( OP_invokespecial, c      ); } // b7  (2)  cp.index
  public Code _invokestatic            (Object...c) { return i_2c           ( OP_invokestatic, c       ); } // b8  (2)  cp.index
  public Code _invokeinterface (byte d, Object...c) { return i_2c_1d_0      ( OP_invokeinterface, d, c ); } // b9  (2,1,0)  cp.index, count, 0
  public Code _invokedynamic           (Object...c) { return i_2c_0_0       ( OP_invokedynamic, c      ); } // ba  (2,0,0)  cp.index, 0, 0
  public Code _new                     (Object...c) { return i_2c           ( OP_new, c                ); } // bb  (2)  cp.index
  public Code _newarray                    (byte t) { return i_1t           ( OP_newarray, t           ); } // bc  (1)  atype
  public Code _anewarray               (Object...c) { return i_2c           ( OP_anewarray, c          ); } // bd  (2)  cp.index
  public Code _arraylength                       () { return i_             ( OP_arraylength           ); } // be
  public Code _athrow                            () { return i_             ( OP_athrow                ); } // bf
  public Code _checkcast               (Object...c) { return i_2c           ( OP_checkcast, c          ); } // c0  (2)  cp.index
  public Code _instanceof              (Object...c) { return i_2c           ( OP_instanceof, c         ); } // c1  (2)  cp.index
  public Code _monitorenter                      () { return i_             ( OP_monitorenter          ); } // c2
  public Code _monitorexit                       () { return i_             ( OP_monitorexit           ); } // c3
  public Code _wide               (byte w, int...a) { return i_1w_2v_x      ( OP_wide, w, a            ); } // c4  (1,2 | 1,2,2)  opcode, lv.index | iinc, lv.index, count
  public Code _multianewarray  (byte d, Object...c) { return i_2c_1d        ( OP_multianewarray, d, c  ); } // c5  (2,1)  cp.index, dimensions
  public Code _ifnull                       (int j) { return i_2j           ( OP_ifnull, j             ); } // c6  (2)  branch
  public Code _ifnonnull                    (int j) { return i_2j           ( OP_ifnonnull, j          ); } // c7  (2)  branch
  public Code _goto_w                       (int j) { return i_4j           ( OP_goto_w, j             ); } // c8  (4)  branch
  public Code _jsr_w                        (int j) { return i_4j           ( OP_jsr_w, j              ); } // c9  (4)  branch
  public Code _breakpoint                        () { return i_             ( OP_breakpoint            ); } // ca
  public Code _impdep1                           () { return i_             ( OP_impdep1               ); } // fe
  public Code _impdep2                           () { return i_             ( OP_impdep2               ); } // ff

  abstract Code i_             (byte o);                     //  -
  abstract Code i_1b           (byte o, byte b);             //  (1)  byte
  abstract Code i_2s           (byte o, short s);            //  (2)  short
  abstract Code i_1c           (byte o, Object...c);         //  (1)  cp.index
  abstract Code i_2c           (byte o, Object...c);         //  (2)  cp.index
  abstract Code i_2c_1d        (byte o, byte d, Object...c); //  (2,1)  cp.index, const
  abstract Code i_1v           (byte o, int v);              //  (1)  lv.index
  abstract Code i_1v_1d        (byte o, int v, byte d);      //  (1,1)  lv.index, const
  abstract Code i_1t           (byte o, byte t);             //  (1)  atype
  abstract Code i_2j           (byte o, int j);              //  (2)  branch
  abstract Code i_4j           (byte o, int j);              //  (4)  branch
  abstract Code i_2c_1d_0      (byte o, byte d, Object...c); //  (2,1,0)  cp.index, count, 0
  abstract Code i_2c_0_0       (byte o, Object...c);         //  (2,0,0)  cp.index, 0, 0
  abstract Code i_p_4d_4d_4d_x (byte o, int...a);            //  (0-3,4,4,4,...)  padding, default, low, high, jump offsets
  abstract Code i_p_4d_4d_x    (byte o, int...a);            //  (0-3,4,4,...)  padding, default, npairs, match/offset pairs
  abstract Code i_1w_2v_x      (byte o, byte w, int...a);    //  (1,2 | 1,2,2)  opcode, lv.index | iinc, lv.index, count

}
