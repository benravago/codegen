#
set -x

ow2() {
  C=$1
  V=$2
  J="$C-$V-sources.jar"
  U="https://repo1.maven.org/maven2/org/ow2/asm/$C/$V/$J"
  wget -q $U -o $J
  unzip -q $J -d src
  rm -fr src/META-INF
  rm -f $J
}

module_info() {
cat << EOF > src/module-info.java 
module codegen {
  exports org.objectweb.asm;
  exports org.objectweb.asm.commons;
}
EOF
}

if [ ! -d src ]; then
  mkdir -p src
  module_info
  ow2 asm 8.0.1
  ow2 asm-commons 8.0.1
fi

rm -fr bin
mkdir -p bin

/opt/jdk15/bin/javac \
  -d bin -sourcepath src \
  src/org/objectweb/asm/commons/InstructionAdapter.java

rm -f codegen-asm-8.0.1.jar

/opt/jdk15/bin/jar \
  -c -f codegen-asm-8.0.1.jar -C bin .

