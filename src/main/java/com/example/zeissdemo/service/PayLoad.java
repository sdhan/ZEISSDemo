package com.example.zeissdemo.service;

import lombok.Data;

import java.util.Date;

@Data
public class PayLoad {
    private String machine_id;
    private String id;
    private String status;
    private Date timestamp;
}

