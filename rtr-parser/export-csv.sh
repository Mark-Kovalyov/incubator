#!/bin/bash

psql -c "\copy torrent_info to '$BIGDATA_HOME/exp.csv'" csv header;