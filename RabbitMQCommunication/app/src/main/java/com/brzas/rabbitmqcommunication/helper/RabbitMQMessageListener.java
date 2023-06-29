package com.brzas.rabbitmqcommunication.helper;

public interface RabbitMQMessageListener {
    void onMessageReceived(String message);
}
