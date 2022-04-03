package bc.builder;

import static bc.JVMS.*;

class Instruction { // implements bc.CompilationUnit.Code

  Instruction $(String s) { return label(s); }

  Instruction _nop                             () { return i_             ( OP_nop                   ); } // 00
  Instruction _aconst_null                     () { return i_             ( OP_aconst_null           ); } // 01
  Instruction _iconst_m1                       () { return i_             ( OP_iconst_m1             ); } // 02
  Instruction _iconst_0                        () { return i_             ( OP_iconst_0              ); } // 03
  Instruction _iconst_1                        () { return i_             ( OP_iconst_1              ); } // 04
  Instruction _iconst_2                        () { return i_             ( OP_iconst_2              ); } // 05
  Instruction _iconst_3                        () { return i_             ( OP_iconst_3              ); } // 06
  Instruction _iconst_4                        () { return i_             ( OP_iconst_4              ); } // 07
  Instruction _iconst_5                        () { return i_             ( OP_iconst_5              ); } // 08
  Instruction _lconst_0                        () { return i_             ( OP_lconst_0              ); } // 09
  Instruction _lconst_1                        () { return i_             ( OP_lconst_1              ); } // 0a
  Instruction _fconst_0                        () { return i_             ( OP_fconst_0              ); } // 0b
  Instruction _fconst_1                        () { return i_             ( OP_fconst_1              ); } // 0c
  Instruction _fconst_2                        () { return i_             ( OP_fconst_2              ); } // 0d
  Instruction _dconst_0                        () { return i_             ( OP_dconst_0              ); } // 0e
  Instruction _dconst_1                        () { return i_             ( OP_dconst_1              ); } // 0f
  Instruction _bipush                    (byte b) { return i_1b           ( OP_bipush, b             ); } // 10  (1)  byte
  Instruction _sipush                   (short s) { return i_2s           ( OP_sipush, s             ); } // 11  (2)  short
  Instruction _ldc                     (Object c) { return i_1c           ( OP_ldc, c                ); } // 12  (1)  cp.index
  Instruction _ldc_w                   (Object c) { return i_2c           ( OP_ldc_w, c              ); } // 13  (2)  cp.index
  Instruction _ldc2_w                  (Object c) { return i_2c           ( OP_ldc2_w, c             ); } // 14  (2)  cp.index
  Instruction _iload                   (Object v) { return i_1v           ( OP_iload, v              ); } // 15  (1)  lv.index
  Instruction _lload                   (Object v) { return i_1v           ( OP_lload, v              ); } // 16  (1)  lv.index
  Instruction _fload                   (Object v) { return i_1v           ( OP_fload, v              ); } // 17  (1)  lv.index
  Instruction _dload                   (Object v) { return i_1v           ( OP_dload, v              ); } // 18  (1)  lv.index
  Instruction _aload                   (Object v) { return i_1v           ( OP_aload, v              ); } // 19  (1)  lv.index
  Instruction _iload_0                         () { return i_             ( OP_iload_0               ); } // 1a
  Instruction _iload_1                         () { return i_             ( OP_iload_1               ); } // 1b
  Instruction _iload_2                         () { return i_             ( OP_iload_2               ); } // 1c
  Instruction _iload_3                         () { return i_             ( OP_iload_3               ); } // 1d
  Instruction _lload_0                         () { return i_             ( OP_lload_0               ); } // 1e
  Instruction _lload_1                         () { return i_             ( OP_lload_1               ); } // 1f
  Instruction _lload_2                         () { return i_             ( OP_lload_2               ); } // 20
  Instruction _lload_3                         () { return i_             ( OP_lload_3               ); } // 21
  Instruction _fload_0                         () { return i_             ( OP_fload_0               ); } // 22
  Instruction _fload_1                         () { return i_             ( OP_fload_1               ); } // 23
  Instruction _fload_2                         () { return i_             ( OP_fload_2               ); } // 24
  Instruction _fload_3                         () { return i_             ( OP_fload_3               ); } // 25
  Instruction _dload_0                         () { return i_             ( OP_dload_0               ); } // 26
  Instruction _dload_1                         () { return i_             ( OP_dload_1               ); } // 27
  Instruction _dload_2                         () { return i_             ( OP_dload_2               ); } // 28
  Instruction _dload_3                         () { return i_             ( OP_dload_3               ); } // 29
  Instruction _aload_0                         () { return i_             ( OP_aload_0               ); } // 2a
  Instruction _aload_1                         () { return i_             ( OP_aload_1               ); } // 2b
  Instruction _aload_2                         () { return i_             ( OP_aload_2               ); } // 2c
  Instruction _aload_3                         () { return i_             ( OP_aload_3               ); } // 2d
  Instruction _iaload                          () { return i_             ( OP_iaload                ); } // 2e
  Instruction _laload                          () { return i_             ( OP_laload                ); } // 2f
  Instruction _faload                          () { return i_             ( OP_faload                ); } // 30
  Instruction _daload                          () { return i_             ( OP_daload                ); } // 31
  Instruction _aaload                          () { return i_             ( OP_aaload                ); } // 32
  Instruction _baload                          () { return i_             ( OP_baload                ); } // 33
  Instruction _caload                          () { return i_             ( OP_caload                ); } // 34
  Instruction _saload                          () { return i_             ( OP_saload                ); } // 35
  Instruction _istore                  (Object v) { return i_1v           ( OP_istore, v             ); } // 36  (1)  lv.index
  Instruction _lstore                  (Object v) { return i_1v           ( OP_lstore, v             ); } // 37  (1)  lv.index
  Instruction _fstore                  (Object v) { return i_1v           ( OP_fstore, v             ); } // 38  (1)  lv.index
  Instruction _dstore                  (Object v) { return i_1v           ( OP_dstore, v             ); } // 39  (1)  lv.index
  Instruction _astore                  (Object v) { return i_1v           ( OP_astore, v             ); } // 3a  (1)  lv.index
  Instruction _istore_0                        () { return i_             ( OP_istore_0              ); } // 3b
  Instruction _istore_1                        () { return i_             ( OP_istore_1              ); } // 3c
  Instruction _istore_2                        () { return i_             ( OP_istore_2              ); } // 3d
  Instruction _istore_3                        () { return i_             ( OP_istore_3              ); } // 3e
  Instruction _lstore_0                        () { return i_             ( OP_lstore_0              ); } // 3f
  Instruction _lstore_1                        () { return i_             ( OP_lstore_1              ); } // 40
  Instruction _lstore_2                        () { return i_             ( OP_lstore_2              ); } // 41
  Instruction _lstore_3                        () { return i_             ( OP_lstore_3              ); } // 42
  Instruction _fstore_0                        () { return i_             ( OP_fstore_0              ); } // 43
  Instruction _fstore_1                        () { return i_             ( OP_fstore_1              ); } // 44
  Instruction _fstore_2                        () { return i_             ( OP_fstore_2              ); } // 45
  Instruction _fstore_3                        () { return i_             ( OP_fstore_3              ); } // 46
  Instruction _dstore_0                        () { return i_             ( OP_dstore_0              ); } // 47
  Instruction _dstore_1                        () { return i_             ( OP_dstore_1              ); } // 48
  Instruction _dstore_2                        () { return i_             ( OP_dstore_2              ); } // 49
  Instruction _dstore_3                        () { return i_             ( OP_dstore_3              ); } // 4a
  Instruction _astore_0                        () { return i_             ( OP_astore_0              ); } // 4b
  Instruction _astore_1                        () { return i_             ( OP_astore_1              ); } // 4c
  Instruction _astore_2                        () { return i_             ( OP_astore_2              ); } // 4d
  Instruction _astore_3                        () { return i_             ( OP_astore_3              ); } // 4e
  Instruction _iastore                         () { return i_             ( OP_iastore               ); } // 4f
  Instruction _lastore                         () { return i_             ( OP_lastore               ); } // 50
  Instruction _fastore                         () { return i_             ( OP_fastore               ); } // 51
  Instruction _dastore                         () { return i_             ( OP_dastore               ); } // 52
  Instruction _aastore                         () { return i_             ( OP_aastore               ); } // 53
  Instruction _bastore                         () { return i_             ( OP_bastore               ); } // 54
  Instruction _castore                         () { return i_             ( OP_castore               ); } // 55
  Instruction _sastore                         () { return i_             ( OP_sastore               ); } // 56
  Instruction _pop                             () { return i_             ( OP_pop                   ); } // 57
  Instruction _pop2                            () { return i_             ( OP_pop2                  ); } // 58
  Instruction _dup                             () { return i_             ( OP_dup                   ); } // 59
  Instruction _dup_x1                          () { return i_             ( OP_dup_x1                ); } // 5a
  Instruction _dup_x2                          () { return i_             ( OP_dup_x2                ); } // 5b
  Instruction _dup2                            () { return i_             ( OP_dup2                  ); } // 5c
  Instruction _dup2_x1                         () { return i_             ( OP_dup2_x1               ); } // 5d
  Instruction _dup2_x2                         () { return i_             ( OP_dup2_x2               ); } // 5e
  Instruction _swap                            () { return i_             ( OP_swap                  ); } // 5f
  Instruction _iadd                            () { return i_             ( OP_iadd                  ); } // 60
  Instruction _ladd                            () { return i_             ( OP_ladd                  ); } // 61
  Instruction _fadd                            () { return i_             ( OP_fadd                  ); } // 62
  Instruction _dadd                            () { return i_             ( OP_dadd                  ); } // 63
  Instruction _isub                            () { return i_             ( OP_isub                  ); } // 64
  Instruction _lsub                            () { return i_             ( OP_lsub                  ); } // 65
  Instruction _fsub                            () { return i_             ( OP_fsub                  ); } // 66
  Instruction _dsub                            () { return i_             ( OP_dsub                  ); } // 67
  Instruction _imul                            () { return i_             ( OP_imul                  ); } // 68
  Instruction _lmul                            () { return i_             ( OP_lmul                  ); } // 69
  Instruction _fmul                            () { return i_             ( OP_fmul                  ); } // 6a
  Instruction _dmul                            () { return i_             ( OP_dmul                  ); } // 6b
  Instruction _idiv                            () { return i_             ( OP_idiv                  ); } // 6c
  Instruction _ldiv                            () { return i_             ( OP_ldiv                  ); } // 6d
  Instruction _fdiv                            () { return i_             ( OP_fdiv                  ); } // 6e
  Instruction _ddiv                            () { return i_             ( OP_ddiv                  ); } // 6f
  Instruction _irem                            () { return i_             ( OP_irem                  ); } // 70
  Instruction _lrem                            () { return i_             ( OP_lrem                  ); } // 71
  Instruction _frem                            () { return i_             ( OP_frem                  ); } // 72
  Instruction _drem                            () { return i_             ( OP_drem                  ); } // 73
  Instruction _ineg                            () { return i_             ( OP_ineg                  ); } // 74
  Instruction _lneg                            () { return i_             ( OP_lneg                  ); } // 75
  Instruction _fneg                            () { return i_             ( OP_fneg                  ); } // 76
  Instruction _dneg                            () { return i_             ( OP_dneg                  ); } // 77
  Instruction _ishl                            () { return i_             ( OP_ishl                  ); } // 78
  Instruction _lshl                            () { return i_             ( OP_lshl                  ); } // 79
  Instruction _ishr                            () { return i_             ( OP_ishr                  ); } // 7a
  Instruction _lshr                            () { return i_             ( OP_lshr                  ); } // 7b
  Instruction _iushr                           () { return i_             ( OP_iushr                 ); } // 7c
  Instruction _lushr                           () { return i_             ( OP_lushr                 ); } // 7d
  Instruction _iand                            () { return i_             ( OP_iand                  ); } // 7e
  Instruction _land                            () { return i_             ( OP_land                  ); } // 7f
  Instruction _ior                             () { return i_             ( OP_ior                   ); } // 80
  Instruction _lor                             () { return i_             ( OP_lor                   ); } // 81
  Instruction _ixor                            () { return i_             ( OP_ixor                  ); } // 82
  Instruction _lxor                            () { return i_             ( OP_lxor                  ); } // 83
  Instruction _iinc            (Object c, byte d) { return i_1c_1d        ( OP_iinc, c, d            ); } // 84  (1,1)  cp.index, const
  Instruction _i2l                             () { return i_             ( OP_i2l                   ); } // 85
  Instruction _i2f                             () { return i_             ( OP_i2f                   ); } // 86
  Instruction _i2d                             () { return i_             ( OP_i2d                   ); } // 87
  Instruction _l2i                             () { return i_             ( OP_l2i                   ); } // 88
  Instruction _l2f                             () { return i_             ( OP_l2f                   ); } // 89
  Instruction _l2d                             () { return i_             ( OP_l2d                   ); } // 8a
  Instruction _f2i                             () { return i_             ( OP_f2i                   ); } // 8b
  Instruction _f2l                             () { return i_             ( OP_f2l                   ); } // 8c
  Instruction _f2d                             () { return i_             ( OP_f2d                   ); } // 8d
  Instruction _d2i                             () { return i_             ( OP_d2i                   ); } // 8e
  Instruction _d2l                             () { return i_             ( OP_d2l                   ); } // 8f
  Instruction _d2f                             () { return i_             ( OP_d2f                   ); } // 90
  Instruction _i2b                             () { return i_             ( OP_i2b                   ); } // 91
  Instruction _i2c                             () { return i_             ( OP_i2c                   ); } // 92
  Instruction _i2s                             () { return i_             ( OP_i2s                   ); } // 93
  Instruction _lcmp                            () { return i_             ( OP_lcmp                  ); } // 94
  Instruction _fcmpl                           () { return i_             ( OP_fcmpl                 ); } // 95
  Instruction _fcmpg                           () { return i_             ( OP_fcmpg                 ); } // 96
  Instruction _dcmpl                           () { return i_             ( OP_dcmpl                 ); } // 97
  Instruction _dcmpg                           () { return i_             ( OP_dcmpg                 ); } // 98
  Instruction _ifeq                    (Object j) { return i_2j           ( OP_ifeq, j               ); } // 99  (2)  branch
  Instruction _ifne                    (Object j) { return i_2j           ( OP_ifne, j               ); } // 9a  (2)  branch
  Instruction _iflt                    (Object j) { return i_2j           ( OP_iflt, j               ); } // 9b  (2)  branch
  Instruction _ifge                    (Object j) { return i_2j           ( OP_ifge, j               ); } // 9c  (2)  branch
  Instruction _ifgt                    (Object j) { return i_2j           ( OP_ifgt, j               ); } // 9d  (2)  branch
  Instruction _ifle                    (Object j) { return i_2j           ( OP_ifle, j               ); } // 9e  (2)  branch
  Instruction _if_icmpeq               (Object j) { return i_2j           ( OP_if_icmpeq, j          ); } // 9f  (2)  branch
  Instruction _if_icmpne               (Object j) { return i_2j           ( OP_if_icmpne, j          ); } // a0  (2)  branch
  Instruction _if_icmplt               (Object j) { return i_2j           ( OP_if_icmplt, j          ); } // a1  (2)  branch
  Instruction _if_icmpge               (Object j) { return i_2j           ( OP_if_icmpge, j          ); } // a2  (2)  branch
  Instruction _if_icmpgt               (Object j) { return i_2j           ( OP_if_icmpgt, j          ); } // a3  (2)  branch
  Instruction _if_icmple               (Object j) { return i_2j           ( OP_if_icmple, j          ); } // a4  (2)  branch
  Instruction _if_acmpeq               (Object j) { return i_2j           ( OP_if_acmpeq, j          ); } // a5  (2)  branch
  Instruction _if_acmpne               (Object j) { return i_2j           ( OP_if_acmpne, j          ); } // a6  (2)  branch
  Instruction _goto                    (Object j) { return i_2j           ( OP_goto, j               ); } // a7  (2)  branch
  Instruction _jsr                     (Object j) { return i_2j           ( OP_jsr, j                ); } // a8  (2)  branch
  Instruction _ret                     (Object c) { return i_1c           ( OP_ret, c                ); } // a9  (1)  cp.index
  Instruction _tableswitch           (Object...a) { return i_p_4d_4d_4d_v ( OP_tableswitch, a        ); } // aa  (0-3,4,4,4,...) padding, default, low, high, jump offsets
  Instruction _lookupswitch          (Object...a) { return i_p_4d_4d_v    ( OP_lookupswitch, a       ); } // ab  (0-3,4,4,...)  padding, default, npairs, match/offset pairs
  Instruction _ireturn                         () { return i_             ( OP_ireturn               ); } // ac
  Instruction _lreturn                         () { return i_             ( OP_lreturn               ); } // ad
  Instruction _freturn                         () { return i_             ( OP_freturn               ); } // ae
  Instruction _dreturn                         () { return i_             ( OP_dreturn               ); } // af
  Instruction _areturn                         () { return i_             ( OP_areturn               ); } // b0
  Instruction _return                          () { return i_             ( OP_return                ); } // b1
  Instruction _getstatic               (Object c) { return i_2c           ( OP_getstatic, c          ); } // b2  (2)  cp.index
  Instruction _putstatic               (Object c) { return i_2c           ( OP_putstatic, c          ); } // b3  (2)  cp.index
  Instruction _getfield                (Object c) { return i_2c           ( OP_getfield, c           ); } // b4  (2)  cp.index
  Instruction _putfield                (Object c) { return i_2c           ( OP_putfield, c           ); } // b5  (2)  cp.index
  Instruction _invokevirtual           (Object c) { return i_2c           ( OP_invokevirtual, c      ); } // b6  (2)  cp.index
  Instruction _invokespecial           (Object c) { return i_2c           ( OP_invokespecial, c      ); } // b7  (2)  cp.index
  Instruction _invokestatic            (Object c) { return i_2c           ( OP_invokestatic, c       ); } // b8  (2)  cp.index
  Instruction _invokeinterface (Object c, byte d) { return i_2c_1d_0      ( OP_invokeinterface, c, d ); } // b9  (2,1,0)  cp.index, count, 0
  Instruction _invokedynamic           (Object c) { return i_2c_0_0       ( OP_invokedynamic, c      ); } // ba  (2,0,0)  cp.index, 0, 0
  Instruction _new                     (Object c) { return i_2c           ( OP_new, c                ); } // bb  (2)  cp.index
  Instruction _newarray                  (byte t) { return i_1t           ( OP_newarray, t           ); } // bc  (1)  atype
  Instruction _anewarray               (Object c) { return i_2c           ( OP_anewarray, c          ); } // bd  (2)  cp.index
  Instruction _arraylength                     () { return i_             ( OP_arraylength           ); } // be
  Instruction _athrow                          () { return i_             ( OP_athrow                ); } // bf
  Instruction _checkcast               (Object c) { return i_2c           ( OP_checkcast, c          ); } // c0  (2)  cp.index
  Instruction _instanceof              (Object c) { return i_2c           ( OP_instanceof, c         ); } // c1  (2)  cp.index
  Instruction _monitorenter                    () { return i_             ( OP_monitorenter          ); } // c2
  Instruction _monitorexit                     () { return i_             ( OP_monitorexit           ); } // c3
  Instruction _wide          (byte w, Object...a) { return i_1w_2c_v      ( OP_wide, w, a            ); } // c4  (1,2 | 1,2,2)  opcode, cp.index | iinc, cp.index, count
  Instruction _multianewarray  (Object c, byte d) { return i_2c_1d        ( OP_multianewarray, c, d  ); } // c5  (2,1)  cp.index, dimensions
  Instruction _ifnull                  (Object j) { return i_2j           ( OP_ifnull, j             ); } // c6  (2)  branch
  Instruction _ifnonnull               (Object j) { return i_2j           ( OP_ifnonnull, j          ); } // c7  (2)  branch
  Instruction _goto_w                  (Object j) { return i_4j           ( OP_goto_w, j             ); } // c8  (4)  branch
  Instruction _jsr_w                   (Object j) { return i_4j           ( OP_jsr_w, j              ); } // c9  (4)  branch
  Instruction _breakpoint                      () { return i_             ( OP_breakpoint            ); } // ca
  Instruction _impdep1                         () { return i_             ( OP_impdep1               ); } // fe
  Instruction _impdep2                         () { return i_             ( OP_impdep2               ); } // ff

  Instruction i_             (byte o)                     { return this; } //  -
  Instruction i_1b           (byte o, byte b)             { return this; } //  (1)  byte
  Instruction i_2s           (byte o, short s)            { return this; } //  (2)  short
  Instruction i_1c           (byte o, Object c)           { return this; } //  (1)  cp.index
  Instruction i_2c           (byte o, Object c)           { return this; } //  (2)  cp.index
  Instruction i_1v           (byte o, Object v)           { return this; } //  (1)  lv.index
  Instruction i_2j           (byte o, Object j)           { return this; } //  (2)  branch
  Instruction i_4j           (byte o, Object j)           { return this; } //  (4)  branch
  Instruction i_1t           (byte o, byte t)             { return this; } //  (1)  atype
  Instruction i_1c_1d        (byte o, Object c, byte d)   { return this; } //  (1,1)  cp.index, const
  Instruction i_2c_1d        (byte o, Object c, byte d)   { return this; } //  (2,1)  cp.index, const
  Instruction i_2c_1d_0      (byte o, Object c, byte d)   { return this; } //  (2,1,0)  cp.index, count, 0
  Instruction i_2c_0_0       (byte o, Object c)           { return this; } //  (2,0,0)  cp.index, 0, 0
  Instruction i_p_4d_4d_4d_v (byte o, Object...a)         { return this; } //  (0-3,4,4,4,...) padding, default, low, high, jump offsets
  Instruction i_p_4d_4d_v    (byte o, Object...a)         { return this; } //  (0-3,4,4,...)  padding, default, npairs, match/offset pairs
  Instruction i_1w_2c_v      (byte o, byte w, Object...a) { return this; } //  (1,2 | 1,2,2) opcode, cp.index | iinc, cp.index, count

  Instruction label(String s) { return this; } // marks a label for jump offsets, etc

}
