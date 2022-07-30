package com.example.zeissdemo.client;


import com.example.zeissdemo.service.Syncing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

@Slf4j
public class MySessionHandler extends StompSessionHandlerAdapter {
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe("/topic/syncing", this);
        session.send("/app/getData", "{\"name\":\"Client\"}".getBytes());

        log.info("New session: {}", session.getSessionId());
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        exception.printStackTrace();
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Syncing.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        log.info("Received: {}", ((Syncing) payload).getContent());
    }
}
