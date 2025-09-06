#!/bin/bash


sbt clean compile

if [ $? -ne 0 ]; then
    echo "Compillation error. Aborted"
    exit 1
fi

sbt "run scott/tiger@127.0.0.1:1521/XE"

