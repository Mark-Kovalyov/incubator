# Kafka C-client

## Check and install
```
$ apt list | grep kafka
golang-github-confluentinc-confluent-kafka-go-dev/focal 0.11.6-1 all
golang-github-segmentio-kafka-go-dev/focal 0.2.1-1 all
kafkacat/focal 1.5.0-1.1 amd64
librdkafka++1/focal 1.2.1-1ubuntu1 amd64
librdkafka-dev/focal 1.2.1-1ubuntu1 amd64
librdkafka1/focal 1.2.1-1ubuntu1 amd64
lua-nginx-kafka/focal 0.07-1 all
python-kafka-doc/focal 1.4.6-1 all
python3-confluent-kafka/focal 1.1.0-1build1 amd64
python3-kafka/focal 1.4.6-1 all
python3-pykafka/focal 2.7.0-1build2 amd64
rsyslog-kafka/focal-updates,focal-security 8.2001.0-1ubuntu1.3 amd64
```

```
$ sudo apt install librdkafka-dev/focal
```

```
$ sudo apt info librdkafka-dev/focal
Package: librdkafka-dev
Version: 1.2.1-1ubuntu1
Priority: optional
Section: universe/libdevel
Source: librdkafka
Origin: Ubuntu
Maintainer: Ubuntu Developers <ubuntu-devel-discuss@lists.ubuntu.com>
Original-Maintainer: Faidon Liambotis <paravoid@debian.org>
Bugs: https://bugs.launchpad.net/ubuntu/+filebug
Installed-Size: 3050 kB
Depends: librdkafka++1 (= 1.2.1-1ubuntu1), librdkafka1 (= 1.2.1-1ubuntu1)
Homepage: https://github.com/edenhill/librdkafka
Download-Size: 524 kB
APT-Manual-Installed: yes
APT-Sources: http://archive.ubuntu.com/ubuntu focal/universe amd64 Packages
Description: library implementing the Apache Kafka protocol (development headers)
 librdkafka is a C library implementation of the Apache Kafka protocol,
 containing both Producer and Consumer support. It was designed with message
 delivery reliability and high performance in mind, current figures exceed
 800000 msgs/second for the producer and 3 million msgs/second for the
 consumer.
 .
 More information about Apache Kafka can be found at http://kafka.apache.org/
 .
 This package contains the development headers.
```

```
$ dpkg -L librdkafka-dev
/.
/usr
/usr/include
/usr/include/librdkafka
/usr/include/librdkafka/rdkafka.h
/usr/include/librdkafka/rdkafkacpp.h
/usr/lib
/usr/lib/x86_64-linux-gnu
/usr/lib/x86_64-linux-gnu/librdkafka++.a
/usr/lib/x86_64-linux-gnu/librdkafka.a
/usr/lib/x86_64-linux-gnu/pkgconfig
/usr/lib/x86_64-linux-gnu/pkgconfig/rdkafka++.pc
/usr/lib/x86_64-linux-gnu/pkgconfig/rdkafka.pc
/usr/share
/usr/share/doc
/usr/share/doc/librdkafka-dev
/usr/share/doc/librdkafka-dev/copyright
/usr/share/doc/librdkafka-dev/examples
/usr/share/doc/librdkafka-dev/examples/rdkafka_example.c
/usr/share/doc/librdkafka-dev/examples/rdkafka_performance.c
/usr/lib/x86_64-linux-gnu/librdkafka++.so
/usr/lib/x86_64-linux-gnu/librdkafka.so
/usr/share/doc/librdkafka-dev/changelog.Debian.gz
```

## Getting started with Kafka anc C/C++

* https://developer.confluent.io/get-started/c/



