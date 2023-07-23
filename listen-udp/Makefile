install: compile-release
	cp -f listen-udp ~/bin/listen-udp

install-debug: compile
		cp -f listen-udp ~/bin/listen-udp

compile:
	gcc -Wno-incompatible-pointer-types -g -std=c99 listen-udp.c -o listen-udp

compile-release:
	gcc -Wno-incompatible-pointer-types -std=c99 listen-udp.c -o listen-udp
