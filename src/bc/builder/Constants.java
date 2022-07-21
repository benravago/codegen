package bc.builder;


public interface Constants {


}

/*
 Constants {

   String C(Class<?> c) -> Class_info                -> Type
   String F(Field f)    -> Fieldref_info             -> Member or Type+String field_name
   String M(Method m)   -> Methodref_info            -> Member or Type+String method_name
   String IM(Method m)  -> InterfaceMethodref_info   -> Member or Type+String method_name
   String NT(String name, Type type) -> NameAndType  -> String + String descriptor

   String V(

cp_info {
    u1 tag;
    u1 info[];
}

CONSTANT_Utf8_info {        cp(String)
    u1 tag = 1;
    u2 length;
    u1 bytes[length];
}
CONSTANT_Integer_info {     cp(Integer)
    u1 tag = 3;
    u4 bytes;
}
CONSTANT_Float_info {       cp(Float)
    u1 tag = 4;
    u4 bytes;
}
CONSTANT_Long_info {        cp(Long)
    u1 tag = 5;
    u4 high_bytes;
    u4 low_bytes;
}
CONSTANT_Double_info {      cp(Double)
    u1 tag = 6;
    u4 high_bytes;
    u4 low_bytes;
}

CONSTANT_Class_info {       cp(Class<?>)
    u1 tag = 7;
    u2 name_index;
}
CONSTANT_String_info {      cp(String)
    u1 tag = 8;
    u2 string_index;
}
CONSTANT_Fieldref_info {    cp(Field)
    u1 tag = 9;
    u2 class_index;
    u2 name_and_type_index;
}
CONSTANT_Methodref_info {   cp(Method)
    u1 tag = 10;
    u2 class_index;
   u2 name_and_type_index;
}
CONSTANT_InterfaceMethodref_info { cp(Method)
    u1 tag = 11;
    u2 class_index;
    u2 name_and_type_index;
}
CONSTANT_NameAndType_info {  cp(String,String)
    u1 tag = 12;
    u2 name_index;
    u2 descriptor_index;
}

CONSTANT_MethodType_info {   cp(String)
    u1 tag = 16;
    u2 descriptor_index;
}
CONSTANT_Module_info {       cp(Module)
    u1 tag = 19;
    u2 name_index;
}
CONSTANT_Package_info {      cp(Package)
    u1 tag = 20;
    u2 name_index;
}

CONSTANT_MethodHandle_info {
    u1 tag = 15;
    u1 reference_kind;
    u2 reference_index;
}

Kind    Description     Interpretation
1       REF_getField    getfield C.f:T
2       REF_getStatic   getstatic C.f:T
3       REF_putField    putfield C.f:T
4       REF_putStatic   putstatic C.f:T
5       REF_invokeVirtual       invokevirtual C.m:(A*)T
6       REF_invokeStatic        invokestatic C.m:(A*)T
7       REF_invokeSpecial       invokespecial C.m:(A*)T
8       REF_newInvokeSpecial    new C; dup; invokespecial C.<init>:(A*)V
9       REF_invokeInterface     invokeinterface C.m:(A*)T

reference_kind

    The value of the reference_kind item must be in the range 1 to 9. The value denotes the kind of this method handle, which characterizes its bytecode behavior (§5.4.3.5).
reference_index

    The value of the reference_index item must be a valid index into the constant_pool table. The constant_pool entry at that index must be as follows:

        If the value of the reference_kind item is 1 (REF_getField), 2 (REF_getStatic), 3 (REF_putField), or 4 (REF_putStatic), then the constant_pool entry at that index must be a CONSTANT_Fieldref_info structure (§4.4.2) representing a field for which a method handle is to be created.

        If the value of the reference_kind item is 5 (REF_invokeVirtual) or 8 (REF_newInvokeSpecial), then the constant_pool entry at that index must be a CONSTANT_Methodref_info structure (§4.4.2) representing a class's method or constructor (§2.9.1) for which a method handle is to be created.

        If the value of the reference_kind item is 6 (REF_invokeStatic) or 7 (REF_invokeSpecial), then if the class file version number is less than 52.0, the constant_pool entry at that index must be a CONSTANT_Methodref_info structure representing a class's method for which a method handle is to be created; if the class file version number is 52.0 or above, the constant_pool entry at that index must be either a CONSTANT_Methodref_info structure or a CONSTANT_InterfaceMethodref_info structure (§4.4.2) representing a class's or interface's method for which a method handle is to be created.

        If the value of the reference_kind item is 9 (REF_invokeInterface), then the constant_pool entry at that index must be a CONSTANT_InterfaceMethodref_info structure representing an interface's method for which a method handle is to be created.

    If the value of the reference_kind item is 5 (REF_invokeVirtual), 6 (REF_invokeStatic), 7 (REF_invokeSpecial), or 9 (REF_invokeInterface), the name of the method represented by a CONSTANT_Methodref_info structure or a CONSTANT_InterfaceMethodref_info structure must not be <init> or <clinit>.

    If the value is 8 (REF_newInvokeSpecial), the name of the method represented by a CONSTANT_Methodref_info structure must be <init>.



CONSTANT_Dynamic_info {
    u1 tag = 17;
    u2 bootstrap_method_attr_index;
    u2 name_and_type_index;
}
CONSTANT_InvokeDynamic_info {
    u1 tag = 18;
    u2 bootstrap_method_attr_index;
    u2 name_and_type_index;
}
BootstrapMethods_attribute {
    u2 attribute_name_index;
    u4 attribute_length;
    u2 num_bootstrap_methods;
    {   u2 bootstrap_method_ref;
        u2 num_bootstrap_arguments;
        u2 bootstrap_arguments[num_bootstrap_arguments]; -> constant pool index
    } bootstrap_methods[num_bootstrap_methods];
}

*/
