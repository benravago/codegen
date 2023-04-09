package bc.encoder.fn;

import java.lang.constant.ClassDesc;
import java.lang.constant.Constable;
import java.lang.constant.ConstantDesc;
import java.lang.constant.MethodTypeDesc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.Optional;
import java.util.regex.Pattern;

import static bc.spec.JVMS.*;

public interface Descriptors {
  
  // Constable is non-sealed
  // ConstantDesc is sealed
  
  static Constable loadable(Constable o) {
    return null; // 32 bit frame slot
  }

  static Constable loadable2(Constable o) {
    return null;  // 64 bit frame slot
  }

  record NameAndType(String name, ConstantDesc type) {}

  // type(type)

  static Constable type(Constable c) {
    return new ClassRef(classDesc(c));
  }

  record ClassRef(ClassDesc type) implements Constable {
    @Override
    public Optional<? extends ConstantDesc> describeConstable() {
      return Optional.of(type);
    }
  }

  static ClassDesc classDesc(Constable c) {
    return switch(c) {
      case Class<?> t -> t.describeConstable().get();
      case String s -> isClassDescriptor(s) ? ClassDesc.ofDescriptor(s) : ClassDesc.of(className(s));
      default -> throw new IllegalArgumentException("not a valid reference type: "+c);
    };
  }

  static ClassDesc[] classDesc(int from, Constable...a) {
    var t = new ClassDesc[a.length - from];
    for (var i = 0; i < t.length; i++, from++) t[i] = classDesc(a[from]);
    return t;
  }

  static final Pattern name = Pattern.compile("\\w*");

  static String name(Constable c) {
    if (c instanceof String s && name.matcher(s).matches()) return s;
    throw new IllegalArgumentException("not a valid member name: "+c);
  }

  static final Pattern className = Pattern.compile("[\\w\\.\\$]*");

  static String className(String s) {
    if (className.matcher(s).matches()) return s;
    throw new IllegalArgumentException("not a valid class name: "+s);
  }

  // throw new IllegalArgumentException(String.format("not a valid reference type descriptor: %s", descriptor));

  static Class<?> owner(Constable c) {
    return switch(c) {
      case Class<?> t -> t;
      case String s -> classFor(s);
      default -> throw new IllegalArgumentException("not a valid reference type: "+c);
    };
  }

  static Class<?> classFor(String s) {
    try { return Class.forName(isClassDescriptor(s) ? describedClass(s) : s); }
    catch (Exception e) { throw new IllegalArgumentException("not a valid class reference: "+s); }
  }

  static boolean isClassDescriptor(String s) {
    return s.charAt(0) == 'L' && s.charAt(s.length()-1) == ';';
  }
  static String describedClass(String s) {
    return s.substring(1,s.length()-1).replace('/','.');
  }

  // TODO: put a tag value here
  
  record MemberRef(byte tag, ClassDesc owner, NameAndType member) implements Constable {
    @Override
    public Optional<? extends ConstantDesc> describeConstable() {
      return Optional.of(member.type);
    }
  }

  // field(owner,name)
  //      (owner,name,type|descriptor)

  static Constable field(Constable...a) {
    if (a.length > 1 && a.length < 4) {
      var c = classDesc(a[0]);
      var n = name(a[1]);
      var t = classDesc(a.length > 2 ? a[2] : fieldOf(owner(a[0]),n).getType() );
      return new MemberRef(CONSTANT_Fieldref,c,new NameAndType(n,t));
    }
    throw new IllegalArgumentException("invalid field specification");
  }

  static Field fieldOf(Class<?> owner, String field) {
    for (var f:owner.getDeclaredFields()) {
      if (f.getName().equals(field)) return f;
    }
    throw new IllegalArgumentException("field "+field+" not in class "+owner);
  }

  // method(owner,name)
  //       (owner,name,returnType|descriptor)
  //       (owner,name,returnType,parameterType...)

  static Constable method(Constable...a) {
    if (a.length > 1) {
      var c = classDesc(a[0]);
      var n = name(a[1]);
      MethodTypeDesc t;
      if (a.length == 3 && a[2] instanceof String s && s.charAt(0) == '(') {
        t = MethodTypeDesc.ofDescriptor(s);
      } else {
        ClassDesc r, p[];
        if (a.length < 3) {
          var m = methodOf(owner(a[0]),n);
          r = classDesc(m.getReturnType());
          p = classDesc(0,m.getParameterTypes());
        } else {
          r = classDesc(a[2] != null ? a[2] : Void.TYPE);
          p = a.length > 3 ? classDesc(3,a) : null;
        }
        t = MethodTypeDesc.of(r,p);
      }
      return new MemberRef(CONSTANT_Methodref,c,new NameAndType(n,t));
    }
    throw new IllegalArgumentException("invalid method specification");
  }

  static Method methodOf(Class<?> owner, String method) {
    for (var m:owner.getDeclaredMethods()) {
      if (m.getName().equals(method)) return m;
    }
    throw new IllegalArgumentException("method "+method+" not in class "+owner);
  }

  static Constable invocable(Constable...o) { // method or constructor
    return null; // TODO: // CONSTANT_InterfaceMethodref
  }

  static Constable interfaceMethod(Constable...o) { // method or interfaceMethod
    return null; // TODO:
  }

  static Constable callsite(Constable...o) {
    return null; // TODO:
  }

  static byte argumentCount(Constable...o) { // last Integer argument
    return -1; // TODO:
  }

  static Constable component(Constable c) {
    var d = classDesc(c);
    if (d.isArray()) throw new IllegalArgumentException("not a component type: "+c);
    return new ClassRef(d);
  }

  static Constable exception(Constable o) {
    return null;
  }

}
/*
  static Constable component(Constable o) {
    return switch (o) {
      case String s -> o;
      case Class<?> c -> { assert !c.isArray() : "not a component Type"; yield c; }
      default -> { assert false : "not a Type"; yield null; }
    };
  }

  static Constable exception(Constable o) {
    return switch (o) {
      case String s -> o;
      case Class<?> c -> { assert c.isAssignableFrom(Throwable.class) : o.toString()+" is not a Throwable"; yield c; }
      default -> { assert false : "not an Exception"; yield null; }
    };
  }
*/
