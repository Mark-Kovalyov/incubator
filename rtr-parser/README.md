## RTR parser

### Kafka

Magic words:
```
Kafka topic: torrents

```

```
bin/kafka-topics.sh --bootstrap-server=localhost:9092 --create --topic TORRENTS 
```

message format (example)
```
{
  "site" : "rutor.info",
  "btih" : "",
  "name" : "",
  "filesize" : 193284789,
  "magnetprops" : {
    
  }
}
```

### Usage

```sh
$ sbt run 
```