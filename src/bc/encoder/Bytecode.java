package bc.encoder;

import bc.CompilationUnit;

public class Bytecode implements CompilationUnit {

  final short major_version, minor_version;
  
  public Bytecode(double version) {
    var s = version > 0 ? Double.toString(version) : System.getProperty("java.class.version");
    var p = s.indexOf('.');
    major_version = Short.valueOf(s.substring(0,p));
    minor_version = Short.valueOf(s.substring(p+1));
  }

  @Override 
  public ClassInfo Class(Object... type) { 
    return new bc.encoder.ClassInfo(type); 
  }
  
  @Override
  public byte[] bytes() {
    // TODO Auto-generated method stub
    return null;
  }
  
}
