all: producer
		CFLAGS=-Wall $(shell pkg-config --cflags glib-2.0 rdkafka)
		LDLIBS=$(shell pkg-config --libs glib-2.0 rdkafka)

producer:

go:
	./producer getting-started.ini
