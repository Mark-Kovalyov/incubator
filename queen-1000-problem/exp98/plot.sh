#!/bin/bash

gnuplot

set dgrid3d 10,10
set hidden3d
splot "out.dat" matrix with lines

