SRC_HOME=src/main
BIN_HOME=bin/

run-release: build-all
	cd bin && java --enable-preview --enable-native-access=ALL-UNNAMED -jar demo-jffi.jar

build-all: comp-nasm comp-c pack-java

comp-c:
	mkdir -p bin
	cd $(SRC_HOME)/c && gcc -g -c prime.c -o prime.o
	cd $(SRC_HOME)/c && gcc -shared prime.o -L/usr/local/lib -o prime-amd64.so
	cp -f src/main/c/*.so $(BIN_HOME)

comp-nasm:
	mkdir -p bin
	cd $(SRC_HOME)/asm && nasm -f elf64 -o sumlib.o sumlib.asm
	cd $(SRC_HOME)/asm && gcc -shared -o sumlib-amd64.so sumlib.o
	cd $(SRC_HOME)/asm && execstack -c sumlib-amd64.so
	cp -f $(SRC_HOME)/asm/*.so $(BIN_HOME)

comp-nasm-win:
	mkdir -p bin
	cd $(SRC_HOME)/asm && nasm -f win64 -o sumlib.obj sumlib.asm
	cd $(SRC_HOME)/asm && gcc -shared -o sumlib.dll sumlib.obj
	cp -f $(SRC_HOME)/asm/*.dll $(BIN_HOME)

pack-java:
	mvn clean package
	cp -f target/demo-jffi.jar $(BIN_HOME)
