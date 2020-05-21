At the moment, this project just contains script to build the minimal subset of the [ASM](https://asm.ow2.io/) library needed to support the Nashorn javascript engine in [NinJa](https://github.com/benravago/ninja).
I would, eventually, like to put together an alternative with a Fluent API; maybe like [jitescript](https://github.com/qmx/jitescript).

The `build.sh` script downloads the required ASM source jars from maven repositories and uses the standard `javac` and `jar` JDK tools to build the required jar file for the NinJa build.
