package com.brzas.rabbitmqcommunication.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import android.util.Log;

import com.brzas.rabbitmqcommunication.helper.RabbitMQMessageListener;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RabbitMQService extends Service {

    /*
    If you want to listen and send to different queues, you need to create queues_name and etc for all, unless the password and the username are the same, and when you start the service you need to start these new connections, because it will be a connection to publish and another to listen, remembering that this is implementation to listen and publish from different queues
     */
    private static final String QUEUE_NAME = "test_rabbitmq";
    //your ip address
    private static final String HOST_NAME = "your_address";
    private static final int PORT = 5672;
    private static final String USERNAME = "username";
    private static final String PASSWORD = "passwaord";
    private static final String VIRTUAL_HOST = "/";

    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;
    private List<RabbitMQMessageListener> messageListeners = new ArrayList<>();
    private ExecutorService executorService;

    public void addMessageListener(RabbitMQMessageListener listener) {
        if (!messageListeners.contains(listener)) {
            messageListeners.add(listener);
        }
    }

    public void removeMessageListener(RabbitMQMessageListener listener) {
        messageListeners.remove(listener);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("RabbitMQService", "Service onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("RabbitMQService", "Service onStartCommand");

        // Inicie o serviço RabbitMQ aqui
        startRabbitMQService();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("RabbitMQService", "Service onDestroy");

        // Encerre o serviço RabbitMQ aqui
        stopRabbitMQService();
    }

    // Binder para vincular o serviço à Activity
    public class LocalBinder extends Binder {
        public RabbitMQService getService() {
            return RabbitMQService.this;
        }
    }

    // ...

    @Override
    public IBinder onBind(Intent intent) {
        // Retorna o binder do LocalBinder para vincular o serviço à Activity
        return new LocalBinder();
    }

    private void startRabbitMQService() {
        executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> {
            try {
                factory = new ConnectionFactory();
                factory.setHost(HOST_NAME);
                factory.setPort(PORT);
                factory.setUsername(USERNAME);
                factory.setPassword(PASSWORD);
                factory.setVirtualHost(VIRTUAL_HOST);

                connection = factory.newConnection();
                channel = connection.createChannel();

                channel.queueDeclare(QUEUE_NAME, true, false, false, null);

                Log.d("RabbitMQService", " waiting message");

                listenToQueue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    private void listenToQueue() throws IOException {
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                Log.d("RabbitMQService", " message receive " + message);
                // Faça algo com a mensagem recebida
                notifyMessageReceived(message);
            }
        };

        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
    public void sendToQueue(final String message) {
        executorService.execute(() -> {
            try {
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                Log.d("RabbitMQService", " message sent to quenue" + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void stopRabbitMQService() {
        try {
            if (channel != null && channel.isOpen()) {
                channel.close();
            }
            if (connection != null && connection.isOpen()) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void notifyMessageReceived(String message) {
        for (RabbitMQMessageListener listener : messageListeners) {
            listener.onMessageReceived(message);
        }
    }
}

