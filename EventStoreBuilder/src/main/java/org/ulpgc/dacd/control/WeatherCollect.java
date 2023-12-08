package org.ulpgc.dacd.control;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.ulpgc.dacd.exceptions.CollectFromBrokerException;
import org.ulpgc.dacd.exceptions.CreateDirectoryException;
import org.ulpgc.dacd.exceptions.SaveEventFileException;

import javax.jms.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class WeatherCollect {
    public final String brokerURL = "tcp://localhost:61616";
    public final String topicName = "prediction.Weather";
    public final String ss = "OpenWeatherMap";
    public final String path;

    public WeatherCollect(String path) {
        this.path = path + "/" + ss;
    }

    public void execute() {
        try{
            MessageConsumer subscriber = initialiseSubscriber();
            subscriber.setMessageListener(message -> {
                TextMessage textMessage = (TextMessage) message;
                String text = null;
                try {
                    text = textMessage.getText();
                }catch (Exception e) {
                    throw new CollectFromBrokerException("Error collecting from broker", e);
                }
                String dateEvent = dateEventCollect(text);
                toFile(dateEvent, text);

            });
            Thread.sleep(Long.MAX_VALUE);

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void toFile(String dateEvent, String text) {
        String file = dateEvent.split("-")[0] + dateEvent.split("-")[1] + dateEvent.split("-")[2] + ".events";
        Path path = Paths.get(this.path, file);
        try{
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
                Files.write(path, (text + "\n").getBytes(), java.nio.file.StandardOpenOption.APPEND);
            }else {
                Files.write(path, (text + "\n").getBytes(), java.nio.file.StandardOpenOption.APPEND);
            }
        }catch (IOException e){
            throw new SaveEventFileException("Error saving events", e);
        }
    }

    private static String dateEventCollect(String text) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(text).getAsJsonObject();
        String ts = json.get("ts").getAsString();
        Instant instant = Instant.parse(ts);
        LocalDate date = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        String dateString = date.toString();
        return dateString;

    }

    private MessageConsumer initialiseSubscriber() throws JMSException {
        ConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = factory.createConnection();
        connection.setClientID("EventStoreBuilder" + ss);
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(topicName);
        MessageConsumer subscriber = session.createDurableSubscriber(topic, "EventStoreBuilder" + ss);
        return subscriber;
    }

    public void createDirectory() {
        Path directory = Paths.get(path);
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                throw new CreateDirectoryException("Error creating directory: " + path, e);
            }
        }
    }
}
