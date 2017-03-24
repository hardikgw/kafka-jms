package biz.channelit.kafka;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by hp on 3/24/17.
 */
@Component
public class Receiver {

    @Autowired
    Producer<String, String> kafkaProducer;

    @JmsListener(destination = "abc_source")
    public void receiveMessage(String message) throws JMSException {
        System.out.println("Received <" + message + ">");
        kafkaProducer.send(new ProducerRecord<String, String>("abc", message));
        System.out.println("Message sent successfully");
    }

}
