package bc.printer;

class Names {

  static String[] byOpcode() {
    try {
      var a = new String[256];
      for (var f:bc.JVMS.class.getFields()) {
        var n = f.getName();
        if (n.startsWith("OP_")) {
          var b = (Byte) f.get(null);
          a[b & 0x0ff] = n.substring(3);
        }
      }
      return a;
    }
    catch (Exception e) { throw new UnsupportedOperationException(e.toString()); }
  }

}
