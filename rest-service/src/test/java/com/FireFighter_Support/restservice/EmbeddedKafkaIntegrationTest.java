package com.FireFighter_Support.restservice;


import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.concurrent.TimeUnit;

import com.FireFighter_Support.restservice.kafka.embedded.KafkaProducerTest;
import com.FireFighter_Support.restservice.kafka.embedded.KafkaConsumerTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
/*
@SpringBootTest
@DirtiesContext                                                             //192.168.160.18:19092
@EmbeddedKafka(partitions = 1  , brokerProperties = { "listeners=PLAINTEXT://192.168.160.18:19092", "port=19092" })
class EmbeddedKafkaIntegrationTest {

    @Autowired
    public KafkaTemplate<String, String> template;

    @Autowired
    private KafkaConsumerTest consumer;

    @Autowired
    private KafkaProducerTest producer;

    @Value("ESP33_SensorData")
    private String topic;
    @Value("ESP33_Alerts")
    private String topic2;


    @Test
    public void givenEmbeddedKafkaBroker_whenSendingtoSimpleProducer_thenMessageReceived() throws Exception {
        producer.send(topic, "{'firefighters': [{'CO': '36', 'temp': '23', 'hum': '38', 'bat': '0', 'lat': '40.06483992', 'longi': '-8.16039721', 'alt': '1132', 'hr': '101.525187124'}, {'CO': '0', 'temp': '23', 'hum': '39', 'bat': '93', 'lat': '40.06469093', 'longi': '-8.16050738', 'alt': '1139', 'hr': '2.152795005'}, {'CO': '0', 'temp': '25', 'hum': '31', 'bat': '96', 'lat': '40.06479192', 'longi': '-8.1604327', 'alt': '1142', 'hr': '1.95093927'}]}");
        //consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
        
        //assertThat(consumer.getLatch().getCount(), equalTo(0L));
        assertThat(consumer.getPayload(), containsString("FF #1 : Heart Rate is 101.525187124"));
    }

}*/