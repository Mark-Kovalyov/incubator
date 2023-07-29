mvn compile exec:java ^
 -Dexec.mainClass=mayton.bigdata.Main ^
 -Dmayton.libs.bigdata.ip2locationservice.path=C:/db/ip2loc/ip2location-lite-db3.csv ^
 -Dmayton.libs.bigdata.emuleguarding.path=C:/db/ip-filters/guarding.p2p
