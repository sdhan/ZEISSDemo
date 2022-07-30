package com.example.zeissdemo.service;

import com.example.zeissdemo.common.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class SyncingController {
    @MessageMapping("/getData")
    @SendTo("/topic/syncing")
    public Syncing syncing(HelloMessage message) throws Exception {
        log.info("Received hello: {}", message.getName());

        Message mess=  JacksonUtil.json2Bean(message.getName(),Message.class);

        String status = mess.getPayload().getStatus();

        if("errored".equals(status)){
            throw new RuntimeException(String.format("'%s' is rejected", message.getName()));
        }

        if("finished".equals(status)){

        }

        return new Syncing("status, " +status + "!");
//        return new Greeting(  message.getName() );
    }

    @MessageExceptionHandler
    @SendTo("/topic/errors")
    public Syncing handleException(Throwable exception) {
        return new Syncing("server exception:, " + exception.getMessage() + "!");
    }


    @SendTo("/topic/finished")
    public Syncing finish(Throwable exception) {
        return new Syncing("server 22:, " + exception.getMessage() + "!");
    }

}
