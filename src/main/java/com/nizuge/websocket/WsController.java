package com.nizuge.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WsController {

    private static final Logger logger = LoggerFactory.getLogger(WsController.class);

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String hello(String helloMessage){
        return "reply:"+helloMessage;
    }
}
