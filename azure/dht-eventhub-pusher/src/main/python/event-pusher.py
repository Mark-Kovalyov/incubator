from azure.eventhub import EventHubProducerClient, EventData

# Connection string of Event Hub namespace + Event Hub name
CONNECTION_STR = "Endpoint=sb://<NAMESPACE>.servicebus.windows.net/;SharedAccessKeyName=<KEYNAME>;SharedAccessKey=<KEY>"
EVENT_HUB_NAME = "<EVENT_HUB_NAME>"

producer = EventHubProducerClient.from_connection_string(
    conn_str=CONNECTION_STR,
    eventhub_name=EVENT_HUB_NAME
)

with producer:
    event_batch = producer.create_batch()
    event_batch.add(EventData("Hello from Python!"))
    event_batch.add(EventData("Second message"))
    producer.send_batch(event_batch)

print("Messages sent!")
