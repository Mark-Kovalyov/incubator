#!/bin/bash -v

# We need to compile our library source code into position-independent code (PIC):
gcc -c -Wall -Werror -fpic gcd-lcm.c

gcc -S gcd-lcm.c -o gcd-lcm.asm

# Now we need to actually turn this object file into a shared library. We will call it libgcd-lcm.so
gcc -shared -o libgcd-lcm.so gcd-lcm.o

for binfile in gcd-lcm.o libgcd-lcm.so
do
  hexdump -C "$binfile" > "$binfile-dump.txt"
  nm "$binfile"         > "$binfile-symboltable.txt"
  readelf -a "$binfile" > "$binfile-readelf.txt"
  objdump -S --disassemble "$binfile" > "$binfile-disassemble.txt"
done

gcc main.c -o main.exe -lgcd-lcm
