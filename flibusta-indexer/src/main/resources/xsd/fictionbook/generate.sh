#!/usr/bin/env bash

java -version

xjc -version

xjc -p mayton.elastic.jaxb -verbose -d ../../../java -b binding.xjb FictionBook.xsd
