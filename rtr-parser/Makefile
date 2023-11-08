run:
	sbt "run 0 1"

export:
	psql -c "\copy torrent_info to '$BIGDATA_HOME/exp.csv'" csv header;
