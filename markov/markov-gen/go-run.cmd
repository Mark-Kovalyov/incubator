@rem set SBT_OPTS=-XX:+UseShenandoahGC -Xmx6G
@rem set SBT_OPTS=-XX:+UseZGC -Xmx6G
@rem set SBT_OPTS=-XX:+UseG1GC -Xmx6G

java -XX:+UseG1GC -Xmx12G -jar bin/markovdemo_3-0.1.0-SNAPSHOT.jar ^
 rock-you ^
 data/rockyou-train.csv.bz2 ^
 ISO_8859_1 ^
 . ^
 50000 30


