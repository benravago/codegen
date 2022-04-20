package bc.parser;

import bc.parser.Bytecode.Span;
import bc.ClassFile.Deprecated;
import bc.ClassFile.Record;
import bc.ClassFile.Module;
import static bc.ClassFile.*;
import static bc.JVMS.*;

class Attribute {

  final Bytecode bc;
  final String[] names;

  Attribute(Bytecode bc) {
    this.bc = bc;
    this.names = new String[bc.constant_pool_count];
  }

  /**
   * returns 'attribute' index
   * where: i[0] is start of 'attribute' section
   *        i[n] is end of 'attribute' #n-1
   */
  static int[] index(Span a, int n) {
    var z = new int[++n];
    z[0] = a.p;
    for (var i = 1; i < n; i++) {
      a.p += 2;
      a.p += a.u4() + 4; // tricky !!
      z[i] = a.p;
    }
    return z;
  }

  CP.info cp(Span a)  {
    var i = a.u2();
    return i > 0 ? bc.cp_info(i) : null;
  }

  String name(short i) {
    var c = bc.cp_info(i);
    if (c instanceof CP.Utf8 u) {
      var n = names[u.index()];
      if (n == null) {
        n = names[u.index()] = bc.chars(u.offset(),u.length()).toString();
      }
      return n;
    }
    throw new IllegalArgumentException(""+c);
  }

  /**
   *  attribute_info {
   *    u2 attribute_name_index;
   *    u4 attribute_length;
   *    u1 info[attribute_length];
   *  }
   */
  AttributeInfo info(Span a) {
    var i = a.u2();
    a.p += 4; // skip length
    return switch(name(i)) { // on case, a.p is at info[]

      case "ConstantValue" -> ConstantValue_attribute(a);
      case "Code" -> Code_attribute(a);
      case "StackMapTable" -> StackMapTable_attribute(a);
      case "Exceptions" -> Exceptions_attribute(a);
      case "InnerClasses" -> InnerClasses_attribute(a);
      case "EnclosingMethod" -> EnclosingMethod_attribute(a);
      case "Synthetic" -> Synthetic_attribute(a);
      case "Signature" -> Signature_attribute(a);
      case "SourceFile" -> SourceFile_attribute(a);
      case "SourceDebugExtension" -> SourceDebugExtension_attribute(a);
      case "LineNumberTable" -> LineNumberTable_attribute(a);
      case "LocalVariableTable" -> LocalVariableTable_attribute(a);
      case "LocalVariableTypeTable" -> LocalVariableTypeTable_attribute(a);
      case "Deprecated" -> Deprecated_attribute(a);
      case "RuntimeVisibleAnnotations" -> RuntimeVisibleAnnotations_attribute(a);
      case "RuntimeInvisibleAnnotations" -> RuntimeInvisibleAnnotations_attribute(a);
      case "RuntimeVisibleParameterAnnotations" -> RuntimeVisibleParameterAnnotations_attribute(a);
      case "RuntimeInvisibleParameterAnnotations" -> RuntimeInvisibleParameterAnnotations_attribute(a);
      case "RuntimeVisibleTypeAnnotations" -> RuntimeVisibleTypeAnnotations_attribute(a);
      case "RuntimeInvisibleTypeAnnotations" -> RuntimeInvisibleTypeAnnotations_attribute(a);
      case "AnnotationDefault" -> AnnotationDefault_attribute(a);
      case "BootstrapMethods" -> BootstrapMethods_attribute(a);
      case "MethodParameters" -> MethodParameters_attribute(a);
      case "Module" -> Module_attribute(a);
      case "ModulePackages" -> ModulePackages_attribute(a);
      case "ModuleMainClass" -> ModuleMainClass_attribute(a);
      case "NestHost" -> NestHost_attribute(a);
      case "NestMembers" -> NestMembers_attribute(a);
      case "Record" -> Record_attribute(a);
      case "PermittedSubclasses" -> PermittedSubclasses_attribute(a);

      default -> throw new IllegalArgumentException("attr '"+a+"'");
    };
  }

  /**
   *  ConstantValue_attribute :
   *    u2 constantvalue_index
   */
  ConstantValue ConstantValue_attribute(Span a) {
    return new ConstantValue(ATTRIBUTE_ConstantValue, cp(a) );
  }

  /**
   *  Exceptions_attribute :
   *    u2 number_of_exceptions
   *    u2 exception_index_table[number_of_exceptions]
   */
  Exceptions Exceptions_attribute(Span span) {
    return new Exceptions(ATTRIBUTE_Exceptions,
      Iter.of( span.u2(), span, a -> cp(a) )
    );
  }

  /**
   *  InnerClasses_attribute :
   *    u2 number_of_classes
   *    { u2 inner_class_info_index
   *      u2 outer_class_info_index
   *      u2 inner_name_index
   *      u2 inner_class_access_flags
   *    } classes[number_of_classes]
   */
  InnerClasses InnerClasses_attribute(Span span) {
    return new InnerClasses(ATTRIBUTE_InnerClasses,
      Iter.of( span.u2(), span, a -> new InnerClass( cp(a), cp(a), cp(a), a.u2()) )
    );
  }

  /**
   *  EnclosingMethod_attribute :
   *    u2 class_index
   *    u2 method_index
   */
  EnclosingMethod EnclosingMethod_attribute(Span a) {
    return new EnclosingMethod(ATTRIBUTE_EnclosingMethod, cp(a), cp(a) );
  }

  /**
   *  Synthetic_attribute :
   */
  Synthetic Synthetic_attribute(Span a) {
    return new Synthetic(ATTRIBUTE_Synthetic);
  }

  /**
   *  Signature_attribute :
   *    u2 signature_index
   */
  Signature Signature_attribute(Span a) {
    return new Signature(ATTRIBUTE_Signature, cp(a) );
  }

  /**
   *  SourceFile_attribute :
   *    u2 sourcefile_index
   */
  SourceFile SourceFile_attribute(Span a) {
    return new SourceFile(ATTRIBUTE_SourceFile, cp(a) );
  }

  /**
   *  SourceDebugExtension_attribute :
   *    u1 debug_extension[attribute_length]
   */
  SourceDebugExtension SourceDebugExtension_attribute(Span a) {
    var dest = new byte[a.m - a.p];
    System.arraycopy( bc.b, a.p, dest, 0, dest.length );
    return new SourceDebugExtension(ATTRIBUTE_SourceDebugExtension, dest );
  }

  /**
   *  LineNumberTable_attribute :
   *    u2 line_number_table_length
   *    { u2 start_pc
   *      u2 line_number
   *    } line_number_table[line_number_table_length]
   */
  LineNumberTable LineNumberTable_attribute(Span span) {
    return new LineNumberTable(ATTRIBUTE_LineNumberTable,
      Iter.of( span.u2(), span, a -> new LineNumber(a.u2(),a.u2()) )
    );
  }

  /**
   *  LocalVariableTable_attribute :
   *    u2 local_variable_table_length
   *    { u2 start_pc
   *      u2 length
   *      u2 name_index
   *      u2 descriptor_index
   *      u2 index
   *    } local_variable_table[local_variable_table_length]
   */
  LocalVariableTable LocalVariableTable_attribute(Span span) {
    return new LocalVariableTable(ATTRIBUTE_LocalVariableTable,
      Iter.of( span.u2(), span, a ->
        new LocalVariable( a.u2(), a.u2(), cp(a), cp(a), a.u2() )
    ));
  }

  /**
   *  LocalVariableTypeTable_attribute :
   *    u2 local_variable_type_table_length
   *    { u2 start_pc
   *      u2 length
   *      u2 name_index
   *      u2 signature_index
   *      u2 index
   *    } local_variable_type_table[local_variable_type_table_length]
   */
  LocalVariableTypeTable LocalVariableTypeTable_attribute(Span span) {
    return new LocalVariableTypeTable(ATTRIBUTE_LocalVariableTypeTable,
      Iter.of( span.u2(), span, a ->
        new LocalVariableType( a.u2(), a.u2(), cp(a), cp(a), a.u2() )
    ));
  }

  /**
   *  Deprecated_attribute :
   */
  Deprecated Deprecated_attribute(Span a) {
    return new Deprecated(ATTRIBUTE_Deprecated);
  }

  /**
   *  BootstrapMethods_attribute :
   *    u2 num_bootstrap_methods
   *    { u2 bootstrap_method_ref
   *      u2 num_bootstrap_arguments
   *      u2 bootstrap_arguments[num_bootstrap_arguments]
   *    } bootstrap_methods[num_bootstrap_methods]
   */
  BootstrapMethods BootstrapMethods_attribute(Span span) {
    return new BootstrapMethods(ATTRIBUTE_BootstrapMethods,
      Iter.of( span.u2(), span, m -> {
        var bootstrap_method_ref = m.u2();
        var num_bootstrap_arguments = m.u2();
        return new BootstrapMethod(
          bc.cp_info(bootstrap_method_ref),
          Iter.of( num_bootstrap_arguments, m.dup(), a -> cp(a) )
        );
      })
    );
  }

  /**
   *  MethodParameters_attribute :
   *    u1 parameters_count
   *    { u2 name_index
   *      u2 access_flags
   *    } parameters[parameters_count]
   */
  MethodParameters MethodParameters_attribute(Span span) {
    return new MethodParameters(ATTRIBUTE_MethodParameters,
      Iter.of( span.u1(), span, a -> new MethodParameter( cp(a), a.u2() )
    ));
  }

  /**
   *  NestHost_attribute :
   *    u2 host_class_index
   */
  NestHost NestHost_attribute(Span a) {
    return new NestHost(ATTRIBUTE_NestHost, cp(a));
  }

  /**
   *  NestMembers_attribute :
   *    u2 number_of_classes
   *    u2 classes[number_of_classes]
   */
  NestMembers NestMembers_attribute(Span span) {
    return new NestMembers(ATTRIBUTE_NestMembers,
      Iter.of( span.u2(), span, a -> cp(a) )
    );
  }

  /**
   *  PermittedSubclasses_attribute :
   *    u2 number_of_classes
   *    u2 classes[number_of_classes]
   */
  PermittedSubclasses PermittedSubclasses_attribute(Span span) {
    return new PermittedSubclasses(ATTRIBUTE_PermittedSubclasses,
      Iter.of( span.u2(), span, a -> cp(a) )
    );
  }

  /**
   *  Record_attribute :
    u2 components_count
    record_component_info components[components_count]

  record_component_info :
    u2             name_index
    u2             descriptor_index
    u2             attributes_count
    attribute_info attributes_index[attributes_count]
   */
  Record Record_attribute(Span span) {
    return new Record(ATTRIBUTE_Record,
      Iter.of( span.u2(), span, a -> {
        var name = a.u2();
        var descriptor = a.u2();
        var attributes_count = a.u2();
        return new RecordComponent(
          bc.cp_info(name),
          bc.cp_info(descriptor),
          attributes(attributes_count, a.dup())
        );
      })
    );
  }

  /**
   *  Module_attribute :
   *    u2 module_name_index
   *    u2 module_flags
   *    u2 module_version_index
   *    u2 requires_count
   *    { u2 requires_index
   *      u2 requires_flags
   *      u2 requires_version_index
   *    } requires[requires_count]
   *    u2 exports_count
   *    { u2 exports_index
   *      u2 exports_flags
   *      u2 exports_to_count
   *      u2 exports_to_index[exports_to_count]
   *    } exports[exports_count]
   *    u2 opens_count
   *    { u2 opens_index
   *      u2 opens_flags
   *      u2 opens_to_count
   *      u2 opens_to_index[opens_to_count]
   *    } opens[opens_count]
   *    u2 uses_count
   *    u2 uses_index[uses_count]
   *    u2 provides_count
   *    { u2 provides_index
   *      u2 provides_with_count
   *      u2 provides_with_index[provides_with_count]
   *    } provides[provides_count]
   */
  Module Module_attribute(Span a) {
    var name = a.u2();
    var flags = a.u2();
    var version = a.u2();
    var requires_count = a.u2();
    var requires_span = skip(a, 6 * requires_count);
    var exports_count = a.u2();
    var exports_span = skip(a, exports_count, 4);
    var opens_count = a.u2();
    var opens_span = skip(a, opens_count, 4);
    var uses_count = a.u2();
    var uses_span = skip(a, 2 * uses_count);
    var provides_count = a.u2();
    var provides_span = skip(a, provides_count, 2);
    return new Module(ATTRIBUTE_Module,
      bc.cp_info(name),
      flags,
      bc.cp_info(version),
      requires(requires_count, requires_span),
      exports(exports_count, exports_span),
      opens(opens_count, opens_span),
      uses(uses_count, uses_span),
      provides(provides_count, provides_span)
    );
  }

  Span skip(Span a, int length) {
    var start = a.p;
    var end = (a.p += length);
    return bc.span(start,end);
  }

  Span skip(Span a, short count, int gap) {
    var start = a.p;
    for (var i = 0; i < count; i++) {
      a.p += gap;
      var n = a.u2();
      a.p += (n * 2);
    }
    return bc.span(start,a.p);
  }

  Iterable<CP.info> uses(int count, Span span) {
    return Iter.of(count, span, a -> cp(a) );
  }

  Iterable<ModuleRequires> requires(int count, Span span) {
    return Iter.of(count, span, r ->
      new ModuleRequires( cp(r), r.u2(), cp(r) ));
  }

  Iterable<ModuleExports> exports(int count, Span span) {
    return Iter.of(count, span, e ->
      new ModuleExports( cp(e), e.u2(),
        Iter.of(e.u2(), e.dup(), a -> cp(a) )
    ));
  }

  Iterable<ModuleOpens> opens(int count, Span span) {
    return Iter.of(count, span, o ->
      new ModuleOpens( cp(o), o.u2(),
        Iter.of( o.u2(), o.dup(), a -> cp(a) )
    ));
  }

  Iterable<ModuleProvides> provides(int count, Span span) {
    return Iter.of(count, span, p ->
      new ModuleProvides( cp(p),
        Iter.of( p.u2(), p.dup(), a -> cp(a) )
    ));
  }

  /**
   *  ModulePackages_attribute :
   *    u2 package_count
   *    u2 package_index[package_count]
   */
  ModulePackages ModulePackages_attribute(Span span) {
    return new ModulePackages(ATTRIBUTE_ModulePackages,
      Iter.of( span.u2(), span, a -> cp(a) )
    );
  }

  /**
   *  ModuleMainClass_attribute :
   *    u2 main_class_index
   */
  ModuleMainClass ModuleMainClass_attribute(Span a) {
    return new ModuleMainClass(ATTRIBUTE_ModuleMainClass, cp(a));
  }

  /**
   *  Code_attribute :
    u2 max_stack
    u2 max_locals
    u4 code_length
    u1 code[code_length]
    u2 exception_table_length
    { u2 start_pc
      u2 end_pc
      u2 handler_pc
      u2 catch_type
    } exception_table[exception_table_length]
    u2 attributes_count
    attribute_info attributes_index[attributes_count]
   */
  Code Code_attribute(Span a) {
    var max_stack = a.u2();
    var max_locals = a.u2();
    var code_length = a.u4();
    var code_start = a.p;
    var code_end = ( a.p += code_length );
    var exception_table_length = a.u2();
    var exception_table_start = a.p;
    var exception_table_end = ( a.p += (exception_table_length * 8) );
    var attributes_count = a.u2();
    var attributes_span = a; // remainder
    var code_span = bc.span(code_start,code_end);
    var exception_table_span = bc.span(exception_table_start,exception_table_end);
    return new Code(ATTRIBUTE_Code,
      max_stack,
      max_locals,
      codes( code_span ),
      exceptions( exception_table_length, exception_table_span ),
      attributes( attributes_count, attributes_span )
    );
  }

  Iterable<Opcode> codes(Span code) {
    var base = code.p;
    return Iter.of( code, a -> Disasm.decode(a,base) );
  }

  Iterable<Except> exceptions(int count, Span span) {
    return Iter.of(count, span, a -> {
      var start_pc = a.u2();
      var end_pc = a.u2();
      var handler_pc = a.u2();
      var c = a.u2();
      return new Except( start_pc, end_pc, handler_pc, (c != 0 ? bc.cp_info(c) : null) );
    });
  }

  Iterable<AttributeInfo> attributes(int count, Span span) {
    return bc.attribute_list(index(span,count));
  }

  /**
   *  StackMapTable_attribute :
   *    u2 number_of_entries
   *    stack_map_frame entries[number_of_entries]
   *
   *  union stack_map_frame :
   *    same_frame
   *    same_locals_1_stack_item_frame
   *    same_locals_1_stack_item_frame_extended
   *    chop_frame
   *    same_frame_extended
   *    append_frame
   *    full_frame
   *
   *  same_frame :
   *    u1 frame_type = SAME // 0-63
   *
   *  same_locals_1_stack_item_frame :
   *    u1 frame_type = SAME_LOCALS_1_STACK_ITEM // 64-127
   *    verification_type_info stack[1]
   *
   *  same_locals_1_stack_item_frame_extended :
   *    u1 frame_type = SAME_LOCALS_1_STACK_ITEM_EXTENDED // 247
   *    u2 offset_delta
   *    verification_type_info stack[1]
   *
   *  chop_frame :
   *    u1 frame_type = CHOP // 248-250
   *    u2 offset_delta
   *
   *  same_frame_extended :
   *    u1 frame_type = SAME_FRAME_EXTENDED // 251
   *    u2 offset_delta
   *
   *  append_frame :
   *    u1 frame_type = APPEND // 252-254
   *    u2 offset_delta
   *    verification_type_info locals[frame_type - 251]
   *
   *  full_frame :
   *    u1 frame_type = FULL_FRAME // 255
   *    u2 offset_delta
   *    u2 number_of_locals
   *    verification_type_info locals[number_of_locals]
   *    u2 number_of_stack_items
   *    verification_type_info stack[number_of_stack_items]
   */
  StackMapTable StackMapTable_attribute(Span span) {
    return new StackMapTable(ATTRIBUTE_StackMapTable,
      Iter.of( span.u2(), span, a -> {
        var type = a.u1();
        var frame_type = type & 0x0ff; // get as int
        if (frame_type == 255) { // FULL_FRAME = 255
          var offset_delta = a.u2();
          var number_of_locals = a.u2();
          var locals_span = verification_type_span(a, number_of_locals);
          var number_of_stack_items = a.u2();
          var stack_span = verification_type_span(a, number_of_stack_items);
          return new StackMapFrame.Full(type, offset_delta,
            verification_types( number_of_locals, locals_span ),
            verification_types( number_of_stack_items, stack_span )
          );
        } else if (frame_type >= 252) { // APPEND = 252-254
          var offset_delta = a.u2();
          var number_of_locals = (short)( frame_type - 251);
          var locals_span = verification_type_span(a, number_of_locals);
          return new StackMapFrame.Append(type, offset_delta,
            verification_types( number_of_locals, locals_span )
          );
        } else if (frame_type == 251) { // SAME_FRAME_EXTENDED = 251
          return new StackMapFrame.SameExtended(type, a.u2());
        } else if (frame_type >= 248) { // CHOP = 248-250
          return new StackMapFrame.Chop(type, a.u2());
        } else if (frame_type == 247) { // SAME_LOCALS_1_STACK_ITEM_EXTENDED = 247
          return new StackMapFrame.SameLocals1StackItemExtended(type, a.u2(), verification_type_info(a));
        } else if (frame_type >= 64)  { // SAME_LOCALS_1_STACK_ITEM = 64-127
          return new StackMapFrame.SameLocals1StackItem(type, verification_type_info(a));
        } else { // SAME = 0-63
          return new StackMapFrame.Same(type);
        }
      })
    );
  }

  /**
   *  union verification_type_info :
   *    Top_variable_info
   *    Integer_variable_info
   *    Float_variable_info
   *    Long_variable_info
   *    Double_variable_info
   *    Null_variable_info
   *    UninitializedThis_variable_info
   *    Object_variable_info
   *    Uninitialized_variable_info
   *
   *  Top_variable_info :
   *    u1 tag = ITEM_Top; // 0
   *
   *  Integer_variable_info :
   *    u1 tag = ITEM_Integer // 1
   *
   *  Float_variable_info :
   *    u1 tag = ITEM_Float // 2
   *
   *  Null_variable_info :
   *    u1 tag = ITEM_Null // 5
   *
   *  UninitializedThis_variable_info :
   *    u1 tag = ITEM_UninitializedThis // 6
   *
   *  Object_variable_info :
   *    u1 tag = ITEM_Object // 7
   *    u2 cpool_index
   *
   *  Uninitialized_variable_info :
   *    u1 tag = ITEM_Uninitialized // 8
   *    u2 offset
   *
   *  Long_variable_info :
   *      u1 tag = ITEM_Long // 4
   *
   *  Double_variable_info :
   *      u1 tag = ITEM_Double // 3
   */
  VerificationType verification_type_info(Span a) {
    var tag = a.u1();
    return switch (tag) {

      case 0 -> new VerificationType.Top(tag);                  // ITEM_Top
      case 1 -> new VerificationType.Integer(tag);              // ITEM_Integer
      case 2 -> new VerificationType.Float(tag);                // ITEM_Float
      case 3 -> new VerificationType.Double(tag);               // ITEM_Double
      case 4 -> new VerificationType.Long(tag);                 // ITEM_Long
      case 5 -> new VerificationType.Null(tag);                 // ITEM_Null
      case 6 -> new VerificationType.UninitializedThis(tag);    // ITEM_UninitializedThis
      case 7 -> new VerificationType.Object(tag,a.u2());        // ITEM_Object
      case 8 -> new VerificationType.Uninitialized(tag,a.u2()); // ITEM_Uninitialized

      default -> throw new IllegalArgumentException("verification_type_info.tag = "+tag);
    };
  }

  Iterable<VerificationType> verification_types(short count, Span span) {
    return Iter.of(count, span, this::verification_type_info);
  }

  Span verification_type_span(Span a, short count) { // OPTIMISTIC
    var start = a.p;
    while (count-- > 0) {
      var tag = a.u1();
      if (tag == 7 || tag == 8) {
        a.p += 2; // ITEM_{Object,Uninitialized}
      }
    }
    return bc.span(start,a.p);
  }

  /**
   *  RuntimeVisibleAnnotations_attribute :
   *    u2 num_annotations
   *    annotation annotations[num_annotations]
   */
  RuntimeVisibleAnnotations RuntimeVisibleAnnotations_attribute(Span a) {
    return new RuntimeVisibleAnnotations(ATTRIBUTE_RuntimeVisibleAnnotations, annotations(a));
  }

  /**
   *  RuntimeInvisibleAnnotations_attribute :
   *    u2 num_annotations
   *    annotation annotations[num_annotations]
   */
  RuntimeInvisibleAnnotations RuntimeInvisibleAnnotations_attribute(Span a) {
    return new RuntimeInvisibleAnnotations(ATTRIBUTE_RuntimeInvisibleAnnotations, annotations(a));
  }

  /**
   *  RuntimeVisibleParameterAnnotations_attribute :
   *    u1 num_parameters
   *    { u2 num_annotations
   *      annotation annotations[num_annotations]
   *    } parameter_annotations[num_parameters]
   *  }
   */
  RuntimeVisibleParameterAnnotations RuntimeVisibleParameterAnnotations_attribute(Span a) {
    return new RuntimeVisibleParameterAnnotations(ATTRIBUTE_RuntimeVisibleParameterAnnotations, parameter_annotations(a));
  }

  /**
   *  RuntimeInvisibleParameterAnnotations_attribute :
   *    u1 num_parameters
   *    { u2 num_annotations
   *      annotation annotations[num_annotations]
   *    } parameter_annotations[num_parameters]
   *  }
   */
  RuntimeInvisibleParameterAnnotations RuntimeInvisibleParameterAnnotations_attribute(Span a) {
    return new RuntimeInvisibleParameterAnnotations(ATTRIBUTE_RuntimeInvisibleParameterAnnotations, parameter_annotations(a));
  }

  /**
   *  RuntimeVisibleTypeAnnotations_attribute :
   *    u2 num_annotations
   *    type_annotation annotations[num_annotations]
   */
  RuntimeVisibleTypeAnnotations RuntimeVisibleTypeAnnotations_attribute(Span a) {
    return new RuntimeVisibleTypeAnnotations(ATTRIBUTE_RuntimeVisibleTypeAnnotations, type_annotations(a));
  }

  /**
   *  RuntimeInvisibleTypeAnnotations_attribute :
   *    u2 num_annotations
   *    type_annotation annotations[num_annotations]
   */
  RuntimeInvisibleTypeAnnotations RuntimeInvisibleTypeAnnotations_attribute(Span a) {
    return new RuntimeInvisibleTypeAnnotations(ATTRIBUTE_RuntimeInvisibleTypeAnnotations, type_annotations(a));
  }

  /**
   *  AnnotationDefault_attribute :
   *    element_value default_value
   */
  AnnotationDefault AnnotationDefault_attribute(Span a) {
    return new AnnotationDefault(ATTRIBUTE_AnnotationDefault, element_value(a) );
  }

  /**
   *  element_value :
   *    u1 tag
   *    union {
   *     1. u2 const_value_index
   *     2. { u2 type_name_index
   *          u2 const_name_index
   *        } enum_const_value
   *     3. u2 class_info_index
   *     4. annotation annotation_value
   *     5. { u2 num_values
   *          element_value values[num_values]
   *        } array_value
   *    } value
   */
  ElementValue element_value(Span a) {
    var tag = a.u1();
    return switch (tag) {

      case E_byte, E_char,
           E_double, E_float,
           E_int, E_long,
           E_short, E_boolean,
           E_String
           -> new ElementValue.Const(tag, cp(a) );
      case E_Enum
           -> new ElementValue.EnumConst(tag, cp(a), cp(a) );
      case E_Class
           -> new ElementValue.Class(tag, cp(a) );
      case E_Annotation
           -> new ElementValue.Annotated(tag, annotation(a) );
      case E_Array
           -> new ElementValue.Array(tag, element_values(a) );

      default -> throw new IllegalArgumentException("element_value.tag="+tag);
    };
  }

  /**
   *  u2 num_values
   *  element_value values[num_values]
   */
  Iterable<ElementValue> element_values(Span a) {
    return Iter.of( a.u2(), a, this::element_value);
  }

  // Table 4.7.16.1-A. Interpretation of tag values as types

  final static byte     // tag  type                 value               constant type

    E_byte       =  66, //  B   byte                 const_value_index   CONSTANT_Integer
    E_char       =  67, //  C   char                 const_value_index   CONSTANT_Integer
    E_double     =  68, //  D   double               const_value_index   CONSTANT_Double
    E_float      =  70, //  F   float                const_value_index   CONSTANT_Float
    E_int        =  73, //  I   int                  const_value_index   CONSTANT_Integer
    E_long       =  74, //  J   long                 const_value_index   CONSTANT_Long
    E_short      =  83, //  S   short                const_value_index   CONSTANT_Integer
    E_boolean    =  90, //  Z   boolean              const_value_index   CONSTANT_Integer
    E_String     = 115, //  s   String               const_value_index   CONSTANT_Utf8

    E_Enum       = 101, //  e   Enum class           enum_const_value
    E_Class      =  99, //  c   Class                class_info_index
    E_Annotation =  64, //  @   Annotation interface annotation_value
    E_Array      =  91; //  [   Array type           array_value

  /**
   * u2 num_annotations
   * annotation annotations[num_annotations]
   */
  Iterable<Annotation> annotations(Span a) {
    return Iter.of( a.u2(), a, this::annotation);
  }

  /**
   *  annotation :
   *    u2 type_index
   *    u2 num_element_value_pairs
   *    { u2 element_name_index
   *      element_value value
   *    } element_value_pairs[num_element_value_pairs]
   */
  Annotation annotation(Span a) {
    return new Annotation( cp(a), element_value_pairs(a));
  }

  Iterable<ElementValuePair> element_value_pairs(Span span) {
    return Iter.of( span.u2(), span, a -> new ElementValuePair( cp(a), element_value(a) ));
  }

  /**
   *  u1 num_parameters
   *  { u2 num_annotations
   *    annotation annotations[num_annotations]
   *  } parameter_annotations[num_parameters]
   */
  Iterable<ParameterAnnotation> parameter_annotations(Span span) {
    return Iter.of( span.u2(), span.dup(), p -> new ParameterAnnotation(annotations(p)) );
  }

  /**
   *  u2 num_annotations
   *  type_annotation annotations[num_annotations]
   */
  Iterable<TypeAnnotation> type_annotations(Span a) {
    return Iter.of( a.u2(), a, this::type_annotation);
  }

  /**
   * type_annotation :
   *   u1 target_type
   *   union {
   *     type_parameter_target
   *     supertype_target
   *     type_parameter_bound_target
   *     empty_target
   *     formal_parameter_target
   *     throws_target
   *     localvar_target
   *     catch_target
   *     offset_target
   *     type_argument_target
   *   } target_info
   *   type_path target_path
   *   u2 type_index
   *   u2 num_element_value_pairs
   *   { u2 element_name_index
   *     element_value value
   *   } element_value_pairs[num_element_value_pairs]
   */
  TypeAnnotation type_annotation(Span a) {
    return new TypeAnnotation(target_info(a), type_path(a), a.u2(), element_value_pairs(a));
  }

  /**
   * type_parameter_target :
   *   u1 type_parameter_index
   *
   * supertype_target :
   *   u2 supertype_index
   *
   * type_parameter_bound_target :
   *   u1 type_parameter_index
   *   u1 bound_index
   *
   * empty_target :
   *
   * formal_parameter_target :
   *   u1 formal_parameter_index
   *
   * throws_target :
   *   u2 throws_type_index
   *
   * catch_target :
   *   u2 exception_table_index
   *
   * offset_target :
   *   u2 offset
   *
   * type_argument_target :
   *   u2 offset
   *   u1 type_argument_index
   */
  Target target_info(Span a) {
    var type = a.u1();
    return switch(type) {

      case 0x00, 0x01 -> new Target.TypeParameter(type,a.u1());
      case 0x10 -> new Target.Supertype(type,a.u2());
      case 0x11, 0x12 -> new Target.TypeParameterBound(type,a.u1(),a.u1());
      case 0x13, 0x14, 0x15 -> new Target.Empty(type);
      case 0x16 -> new Target.FormalParameter(type,a.u1());
      case 0x17 -> new Target.Throws(type,a.u2());
      case 0x40, 0x41 -> localvar_target(a,type);
      case 0x42 -> new Target.Catch(type,a.u2());
      case 0x43, 0x44, 0x45, 0x46 -> new Target.Offset(type,a.u2());
      case 0x47, 0x48, 0x49, 0x4A, 0x4B -> new Target.TypeArgument(type,a.u2(),a.u1());

      default -> throw new IllegalArgumentException("target_type="+type);
    };
  }

  /**
   *  localvar_target :
   *    u2 table_length
   *    {   u2 start_pc
   *        u2 length
   *        u2 index
   *    } table[table_length]
   */
  Target localvar_target(Span a, byte type) {
    var table_length = a.u2();
    var start = a.p;
    a.p += (table_length * 6);
    var table = bc.span(start,a.p);
    return new Target.Localvars(type,
      Iter.of(table_length, table, t -> new Target.Localvar(t.u2(),t.u2(),t.u2()) )
    );
  }

  /**
   *  type_path :
   *    u1 path_length
   *    { u1 type_path_kind
   *      u1 type_argument_index
   *    } path[path_length]
   */
  TypePath type_path(Span span) {
    return new TypePath( Iter.of(span.u2(), span, a -> new Part(a.u1(),a.u1()) ));
  }

}
