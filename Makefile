
JDK = "/opt/jdk15"
JAVAC = "$(JDK)/bin/javac"

OW2ASM = "https://repo1.maven.org/maven2/org/ow2/asm"

define SOURCE
	mkdir -p src
	curl -sS $(OW2ASM)/$(1)/$(2)/$(1)-$(2)-sources.jar -o src/o
	unzip -q src/o -d src
	rm -fr src/{o,META-INF}
endef

bin: src/org/objectweb/asm src/org/objectweb/asm/commons
	mkdir -p bin
	$(JAVAC) -d bin -sourcepath src src/org/objectweb/asm/commons/InstructionAdapter.java

src/org/objectweb/asm/commons: src/org/objectweb/asm
	$(call SOURCE,asm-commons,8.0.1)

src/org/objectweb/asm:
	$(call SOURCE,asm,8.0.1)

clean:
	rm -fr bin src

