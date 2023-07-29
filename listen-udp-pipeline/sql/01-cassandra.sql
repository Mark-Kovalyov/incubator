CREATE KEYSPACE IF NOT EXISTS dht WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'};


 ts, ip, source_port, tag, packet_size, packet_body

CREATE TABLE IF NOT EXISTS dht.udp (
    ts text,
    ip text,
    source_port int,
    tag text,
    packet_size int,
    packet_body text,
    PRIMARY KEY((ip,tag), ts)
);
