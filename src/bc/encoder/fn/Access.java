package bc.encoder.fn;

import static bc.spec.JVMS.ACC;

public interface Access {
  static short forClass(ACC...flags) { return 0; }
  static short forField(ACC...flags) { return 0; }
  static short forMethod(ACC...flags) { return 0; }
}
