package com.poly.app.domain.client.controller;

import com.poly.app.domain.client.repository.CommentDTO;


import com.poly.app.domain.client.response.CommentMessage;
import com.poly.app.domain.client.service.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebSocketControllerClient {
    SimpMessagingTemplate messagingTemplate;
    CommentService commentService;

    // Test gửi tin nhắn qua WebSocket bằng Postman
    @PostMapping("/send-message")
    public String sendMessageViaRest() {
        String destination = "/topic/global-notifications"; // Gửi đến tất cả user

        messagingTemplate.convertAndSend(destination, "Hello tất cả mọi người!");
        System.out.println("Đã gửi tin nhắn tới: " + destination);

        return "Đã gửi tin nhắn thành công!";
    }

    @GetMapping("/user-principal")
    public Principal getPrincipal(Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("User Principal: " + authentication.getPrincipal());
        return principal;
    }


    @MessageMapping("/comment/{productId}")
    public void handleComment(@DestinationVariable Integer productId, CommentMessage message) {
        commentService.saveComment(productId, message.getCustomerId(), message.getComment());
    }

    @GetMapping("/comments/{productId}")
    public List<CommentDTO> getComments(@PathVariable Integer productId) {
        return commentService.getCommentsByProductId(productId);
    }

}