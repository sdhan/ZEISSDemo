package com.example.zeissdemo.service;

import lombok.Data;

@Data
public class Message{
    private String topic;
    private String ref;
    private PayLoad payload;
    private String event;
}