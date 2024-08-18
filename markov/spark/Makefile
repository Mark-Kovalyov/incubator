SBT_OPTS := "-Xmx4G"

#SBT_OPTS := "-XX:+UseZGC"         # default = 1 073 741 824 for ZGC ?

export SBT_OPTS

TAG=rockyou

#FILE=elvens.csv.bz2

FILE=rockyou-train.csv.bz2

run: graph-processor markov-hist-gen markov-pw-gen

graph-processor:
	sbt "runMain mayton.MarkovGraphGen $(TAG) data/stage-01/$(FILE)"

markov-hist-gen:
	sbt "runMain mayton.MarkovHistGen $(TAG)"

markov-pw-gen:
	sbt "runMain mayton.MarkovPwGen $(TAG) 500 16"
