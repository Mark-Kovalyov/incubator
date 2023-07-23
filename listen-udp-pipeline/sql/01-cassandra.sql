CREATE KEYSPACE IF NOT EXISTS dht WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'};

CREATE TABLE IF NOT EXISTS dht.udps (
    ip  text,
    cnt int,
    PRIMARY KEY(ip)
);
