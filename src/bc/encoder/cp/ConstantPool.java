package bc.encoder.cp;

public class ConstantPool extends Constant {
  /*
    Pool
     ^
     |
    Constant
     ^
     |
    ConstantPool  <- Descriptors
     ^
     |
    callers =  ConstantPool cp;
               ...
               Code putfield(Constable c) { putfield(cp.field(c)); return this; }

  */
}
