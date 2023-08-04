package com.brzas.rabbitmqcommunication.models;

import java.time.LocalDateTime;

public class Message {

    private String message;
    private LocalDateTime tmst;

    public Message() {
    }

    public Message(String message, LocalDateTime tmst) {
        this.message = message;
        this.tmst = tmst;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTmst() {
        return tmst;
    }

    public void setTmst(LocalDateTime tmst) {
        this.tmst = tmst;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", tmst=" + tmst +
                '}';
    }
}
