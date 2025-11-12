@rem set SBT_OPTS=-XX:+UseShenandoahGC -Xmx6G
@rem set SBT_OPTS=-XX:+UseZGC -Xmx6G
@rem set SBT_OPTS=-XX:+UseG1GC -Xmx6G

java -XX:+UseG1GC -Xmx12G -jar bin/markovdemo_3-0.1.0-SNAPSHOT.jar ^
 russian-mat ^
 data/words.txt.bz2 ^
 utf-8 ^
 . ^
 50000 20


