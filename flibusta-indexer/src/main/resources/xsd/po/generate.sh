#!/usr/bin/env bash

java -version

xjc -version

xjc -p mayton.elastic.jaxb.po -verbose -d ../../../java po.xsd
