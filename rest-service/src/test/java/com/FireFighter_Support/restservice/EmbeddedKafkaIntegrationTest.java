package com.FireFighter_Support.restservice;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;

import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;



@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = {"ESP33_SensorData"}, brokerProperties = { "listeners=PLAINTEXT://localhost:19092", "port=19092" })
@SpringBootTest
public class EmbeddedKafkaIntegrationTest {

    private static String TEST_TOPIC = "ESP33_SensorData";
    private String testMsg = "{'firefighters': [{'CO': '36', 'temp': '23', 'hum': '38', 'bat': '0', 'lat': '40.06483992', 'longi': '-8.16039721', 'alt': '1132', 'hr': '101.525187124'}, {'CO': '0', 'temp': '23', 'hum': '39', 'bat': '93', 'lat': '40.06469093', 'longi': '-8.16050738', 'alt': '1139', 'hr': '2.152795005'}, {'CO': '0', 'temp': '25', 'hum': '31', 'bat': '96', 'lat': '40.06479192', 'longi': '-8.1604327', 'alt': '1142', 'hr': '1.95093927'}]}";

    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;
    

    @Test
    public void testReceivingKafkaEvents() {
        
        Producer<Integer, String> producer = configureProducer();

        producer.send(new ProducerRecord<>(TEST_TOPIC, 123, testMsg));

        Consumer<Integer, String> consumer = configureConsumer();

        ConsumerRecord<Integer, String> singleRecord = KafkaTestUtils.getSingleRecord(consumer, TEST_TOPIC);
        Assertions.assertNotNull(singleRecord);
        Assertions.assertEquals(singleRecord.key(), 123);
        System.out.println("VALOR_: "+singleRecord.value());
        Assertions.assertTrue(singleRecord.value().contains("'hr': '101.525187124'")); //("FF #1 : Heart Rate is 101.525187124"));
        consumer.close();
        producer.close();
    }

    private Consumer<Integer, String> configureConsumer() {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("FireFighter_2", "true", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        Consumer<Integer, String> consumer = new DefaultKafkaConsumerFactory<Integer, String>(consumerProps)
                .createConsumer();
        consumer.subscribe(Collections.singleton(TEST_TOPIC));
        return consumer;
    }

    private Producer<Integer, String> configureProducer() {
        Map<String, Object> producerProps = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
        return new DefaultKafkaProducerFactory<Integer, String>(producerProps).createProducer();
    }
}