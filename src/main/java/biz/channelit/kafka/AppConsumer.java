package biz.channelit.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hp on 4/14/17.
 */

public class AppConsumer implements Runnable {

    private Consumer<String, String> kafkaConsumer;

    public AppConsumer(Consumer<String, String> consumer) {
       this.kafkaConsumer = consumer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(1000);
                for (ConsumerRecord<String, String> record : records) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("partition", record.partition());
                    data.put("offset", record.offset());
                    data.put("value", record.value());
                    System.out.println(">>>" + ": " + data);
                }
            }
        } catch (WakeupException e) {
            // ignore for shutdown
        } finally {
            kafkaConsumer.close();
        }
    }
}
