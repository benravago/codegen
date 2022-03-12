package bc.builder;

interface Opcode {

  Opcode _nop             ();  // 00
  Opcode _aconst_null     ();  // 01
  Opcode _iconst_m1       ();  // 02
  Opcode _iconst_0        ();  // 03
  Opcode _iconst_1        ();  // 04
  Opcode _iconst_2        ();  // 05
  Opcode _iconst_3        ();  // 06
  Opcode _iconst_4        ();  // 07
  Opcode _iconst_5        ();  // 08
  Opcode _lconst_0        ();  // 09
  Opcode _lconst_1        ();  // 0a
  Opcode _fconst_0        ();  // 0b
  Opcode _fconst_1        ();  // 0c
  Opcode _fconst_2        ();  // 0d
  Opcode _dconst_0        ();  // 0e
  Opcode _dconst_1        ();  // 0f
  Opcode _bipush          ();  // 10  (1)  byte
  Opcode _sipush          ();  // 11  (2)  short
  Opcode _ldc             ();  // 12  (1)  cp.index
  Opcode _ldc_w           ();  // 13  (2)  cp.index
  Opcode _ldc2_w          ();  // 14  (2)  cp.index
  Opcode _iload           ();  // 15  (1)  lv.index
  Opcode _lload           ();  // 16  (1)  lv.index
  Opcode _fload           ();  // 17  (1)  lv.index
  Opcode _dload           ();  // 18  (1)  lv.index
  Opcode _aload           ();  // 19  (1)  lv.index
  Opcode _iload_0         ();  // 1a
  Opcode _iload_1         ();  // 1b
  Opcode _iload_2         ();  // 1c
  Opcode _iload_3         ();  // 1d
  Opcode _lload_0         ();  // 1e
  Opcode _lload_1         ();  // 1f
  Opcode _lload_2         ();  // 20
  Opcode _lload_3         ();  // 21
  Opcode _fload_0         ();  // 22
  Opcode _fload_1         ();  // 23
  Opcode _fload_2         ();  // 24
  Opcode _fload_3         ();  // 25
  Opcode _dload_0         ();  // 26
  Opcode _dload_1         ();  // 27
  Opcode _dload_2         ();  // 28
  Opcode _dload_3         ();  // 29
  Opcode _aload_0         ();  // 2a
  Opcode _aload_1         ();  // 2b
  Opcode _aload_2         ();  // 2c
  Opcode _aload_3         ();  // 2d
  Opcode _iaload          ();  // 2e
  Opcode _laload          ();  // 2f
  Opcode _faload          ();  // 30
  Opcode _daload          ();  // 31
  Opcode _aaload          ();  // 32
  Opcode _baload          ();  // 33
  Opcode _caload          ();  // 34
  Opcode _saload          ();  // 35
  Opcode _istore          ();  // 36  (1)  lv.index
  Opcode _lstore          ();  // 37  (1)  lv.index
  Opcode _fstore          ();  // 38  (1)  lv.index
  Opcode _dstore          ();  // 39  (1)  lv.index
  Opcode _astore          ();  // 3a  (1)  lv.index
  Opcode _istore_0        ();  // 3b
  Opcode _istore_1        ();  // 3c
  Opcode _istore_2        ();  // 3d
  Opcode _istore_3        ();  // 3e
  Opcode _lstore_0        ();  // 3f
  Opcode _lstore_1        ();  // 40
  Opcode _lstore_2        ();  // 41
  Opcode _lstore_3        ();  // 42
  Opcode _fstore_0        ();  // 43
  Opcode _fstore_1        ();  // 44
  Opcode _fstore_2        ();  // 45
  Opcode _fstore_3        ();  // 46
  Opcode _dstore_0        ();  // 47
  Opcode _dstore_1        ();  // 48
  Opcode _dstore_2        ();  // 49
  Opcode _dstore_3        ();  // 4a
  Opcode _astore_0        ();  // 4b
  Opcode _astore_1        ();  // 4c
  Opcode _astore_2        ();  // 4d
  Opcode _astore_3        ();  // 4e
  Opcode _iastore         ();  // 4f
  Opcode _lastore         ();  // 50
  Opcode _fastore         ();  // 51
  Opcode _dastore         ();  // 52
  Opcode _aastore         ();  // 53
  Opcode _bastore         ();  // 54
  Opcode _castore         ();  // 55
  Opcode _sastore         ();  // 56
  Opcode _pop             ();  // 57
  Opcode _pop2            ();  // 58
  Opcode _dup             ();  // 59
  Opcode _dup_x1          ();  // 5a
  Opcode _dup_x2          ();  // 5b
  Opcode _dup2            ();  // 5c
  Opcode _dup2_x1         ();  // 5d
  Opcode _dup2_x2         ();  // 5e
  Opcode _swap            ();  // 5f
  Opcode _iadd            ();  // 60
  Opcode _ladd            ();  // 61
  Opcode _fadd            ();  // 62
  Opcode _dadd            ();  // 63
  Opcode _isub            ();  // 64
  Opcode _lsub            ();  // 65
  Opcode _fsub            ();  // 66
  Opcode _dsub            ();  // 67
  Opcode _imul            ();  // 68
  Opcode _lmul            ();  // 69
  Opcode _fmul            ();  // 6a
  Opcode _dmul            ();  // 6b
  Opcode _idiv            ();  // 6c
  Opcode _ldiv            ();  // 6d
  Opcode _fdiv            ();  // 6e
  Opcode _ddiv            ();  // 6f
  Opcode _irem            ();  // 70
  Opcode _lrem            ();  // 71
  Opcode _frem            ();  // 72
  Opcode _drem            ();  // 73
  Opcode _ineg            ();  // 74
  Opcode _lneg            ();  // 75
  Opcode _fneg            ();  // 76
  Opcode _dneg            ();  // 77
  Opcode _ishl            ();  // 78
  Opcode _lshl            ();  // 79
  Opcode _ishr            ();  // 7a
  Opcode _lshr            ();  // 7b
  Opcode _iushr           ();  // 7c
  Opcode _lushr           ();  // 7d
  Opcode _iand            ();  // 7e
  Opcode _land            ();  // 7f
  Opcode _ior             ();  // 80
  Opcode _lor             ();  // 81
  Opcode _ixor            ();  // 82
  Opcode _lxor            ();  // 83
  Opcode _iinc            ();  // 84  (1,1)  cp.index, const
  Opcode _i2l             ();  // 85
  Opcode _i2f             ();  // 86
  Opcode _i2d             ();  // 87
  Opcode _l2i             ();  // 88
  Opcode _l2f             ();  // 89
  Opcode _l2d             ();  // 8a
  Opcode _f2i             ();  // 8b
  Opcode _f2l             ();  // 8c
  Opcode _f2d             ();  // 8d
  Opcode _d2i             ();  // 8e
  Opcode _d2l             ();  // 8f
  Opcode _d2f             ();  // 90
  Opcode _i2b             ();  // 91
  Opcode _i2c             ();  // 92
  Opcode _i2s             ();  // 93
  Opcode _lcmp            ();  // 94
  Opcode _fcmpl           ();  // 95
  Opcode _fcmpg           ();  // 96
  Opcode _dcmpl           ();  // 97
  Opcode _dcmpg           ();  // 98
  Opcode _ifeq            ();  // 99  (2)  branch
  Opcode _ifne            ();  // 9a  (2)  branch
  Opcode _iflt            ();  // 9b  (2)  branch
  Opcode _ifge            ();  // 9c  (2)  branch
  Opcode _ifgt            ();  // 9d  (2)  branch
  Opcode _ifle            ();  // 9e  (2)  branch
  Opcode _if_icmpeq       ();  // 9f  (2)  branch
  Opcode _if_icmpne       ();  // a0  (2)  branch
  Opcode _if_icmplt       ();  // a1  (2)  branch
  Opcode _if_icmpge       ();  // a2  (2)  branch
  Opcode _if_icmpgt       ();  // a3  (2)  branch
  Opcode _if_icmple       ();  // a4  (2)  branch
  Opcode _if_acmpeq       ();  // a5  (2)  branch
  Opcode _if_acmpne       ();  // a6  (2)  branch
  Opcode _goto            ();  // a7  (2)  branch
  Opcode _jsr             ();  // a8  (2)  branch
  Opcode _ret             ();  // a9  (1)  cp.index
  Opcode _tableswitch     ();  // aa  (0-3,4,4,4,...) padding, default, low, high, jump offsets
  Opcode _lookupswitch    ();  // ab  (0-3,4,4,...)  padding, default, npairs, match/offset pairs
  Opcode _ireturn         ();  // ac
  Opcode _lreturn         ();  // ad
  Opcode _freturn         ();  // ae
  Opcode _dreturn         ();  // af
  Opcode _areturn         ();  // b0
  Opcode _return          ();  // b1
  Opcode _getstatic       ();  // b2  (2)  cp.index
  Opcode _putstatic       ();  // b3  (2)  cp.index
  Opcode _getfield        ();  // b4  (2)  cp.index
  Opcode _putfield        ();  // b5  (2)  cp.index
  Opcode _invokevirtual   ();  // b6  (2)  cp.index
  Opcode _invokespecial   ();  // b7  (2)  cp.index
  Opcode _invokestatic    ();  // b8  (2)  cp.index
  Opcode _invokeinterface ();  // b9  (2,1,0)  cp.index, count, 0
  Opcode _invokedynamic   ();  // ba  (2,0,0)  cp.index, 0, 0
  Opcode _new             ();  // bb  (2)  cp.index
  Opcode _newarray        ();  // bc  (1)  atype
  Opcode _anewarray       ();  // bd  (2)  cp.index
  Opcode _arraylength     ();  // be
  Opcode _athrow          ();  // bf
  Opcode _checkcast       ();  // c0  (2)  cp.index
  Opcode _instanceof      ();  // c1  (2)  cp.index
  Opcode _monitorenter    ();  // c2
  Opcode _monitorexit     ();  // c3
  Opcode _wide            ();  // c4  (1,2 | 1,2,2) opcode, cp.index | iinc, cp.index, count
  Opcode _multianewarray  ();  // c5  (2,1)  cp.index, dimensions
  Opcode _ifnull          ();  // c6  (2)  branch
  Opcode _ifnonnull       ();  // c7  (2)  branch
  Opcode _goto_w          ();  // c8  (4)  branch
  Opcode _jsr_w           ();  // c9  (4)  branch
  Opcode _breakpoint      ();  // ca
  Opcode _impdep1         ();  // fe
  Opcode _impdep2         ();  // ff

  // $("label")

}
