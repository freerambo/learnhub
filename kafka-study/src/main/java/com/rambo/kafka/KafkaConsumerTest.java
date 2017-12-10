package com.rambo.kafka;

import java.util.Arrays;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class KafkaConsumerTest {  
    public static void main(String[] args) throws Exception{  
    	 KafkaConsumer<String, String> consumer = KafkaUtil.getConsumer();  
         consumer.subscribe(Arrays.asList("test"));  
         while(true) {  
             ConsumerRecords<String, String> records = consumer.poll(1000);  
             for(ConsumerRecord<String, String> record : records) {  
                 System.out.println("fetched from partition " + record.partition() + ", offset: " + record.offset() + ", message: " + record.value());  
             }  
         }  
    }
    
}