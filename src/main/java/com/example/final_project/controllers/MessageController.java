package com.example.final_project.controllers;

import com.example.final_project.models.Message1;
import com.example.final_project.models.OutputMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MessageController {
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessage send(Message1 message) {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(message.getFrom(), message.getText(), time);
    }
}
