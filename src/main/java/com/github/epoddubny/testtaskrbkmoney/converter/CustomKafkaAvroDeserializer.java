package com.github.epoddubny.testtaskrbkmoney.converter;

import com.github.epoddubny.testtaskrbkmoney.avro.ReportV1;
import com.github.epoddubny.testtaskrbkmoney.avro.Transaction;
import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.avro.Schema;

/**
 * This is hack for mocking avro schema registry
 */
public class CustomKafkaAvroDeserializer extends KafkaAvroDeserializer {
    @Override
    public Object deserialize(String topic, byte[] bytes) {
        if (topic.equals("transactions")) {
            this.schemaRegistry = getMockClient(Transaction.SCHEMA$);
        }
        if (topic.equals("reports")) {
            this.schemaRegistry = getMockClient(ReportV1.SCHEMA$);
        }
        return super.deserialize(topic, bytes);
    }

    private static SchemaRegistryClient getMockClient(final Schema schema$) {
        return new MockSchemaRegistryClient() {
            @Override
            public synchronized Schema getById(int id) {
                return schema$;
            }
        };
    }
}
