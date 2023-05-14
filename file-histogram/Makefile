kafka:
	sbt "run kafka /kafka scala|java|py"

spark:
	sbt "run spark /spark scala|java|py|c|cpp|sh"

linux:
	sbt "run linux /linux-master rs|c|cpp|h|hpp"

hadoop:
	sbt "run hadoop /hadoop java|py"

all: kafka-scala spark-scala linux-rust hadoop

clean:
	rm -f report/*

gen-kafka-git-log:
	echo "hash;unixtime;comment" > git-logs/kafka/git.csv
	cd /kafka &&	git log --pretty=format:"%H;%ad;%s" --date=unix > git.csv
	cat /kafka/git.csv >> git-logs/kafka/git.csv

demo-color:
	sbt run