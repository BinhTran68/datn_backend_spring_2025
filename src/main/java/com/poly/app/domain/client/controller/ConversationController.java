package com.poly.app.domain.client.controller;

import com.poly.app.domain.client.request.ConversationRequest;
import com.poly.app.domain.model.Conversation;
import com.poly.app.domain.model.Customer;
import com.poly.app.domain.model.Message;
import com.poly.app.domain.model.Staff;
import com.poly.app.domain.repository.ConversationRepository;
import com.poly.app.domain.repository.CustomerRepository;
import com.poly.app.domain.repository.MessageRepository;
import com.poly.app.domain.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    MessageRepository messageRepository;


    @PostMapping
    public ResponseEntity<Conversation> createOrGetConversation(
            @RequestBody ConversationRequest request) {
        Optional<Conversation> existingConversation = conversationRepository
                .findByCustomerIdAndStaffId(request.getCustomerId(), request.getStaffId());

        if (existingConversation.isPresent()) {
            return ResponseEntity.ok(existingConversation.get());
        }

        Conversation conversation = Conversation.builder()
                .customer(customerRepository.findById(request.getCustomerId()).get())
                .staff(staffRepository.findById(request.getStaffId()).get())
                .build();

        conversation = conversationRepository.save(conversation);
        return ResponseEntity.ok(conversation);
    }
    @GetMapping("/staff/{staffId}")
    public ResponseEntity<List<Conversation>> getConversationsByStaffId(@PathVariable Long staffId) {
        List<Conversation> conversations = conversationRepository.findByStaffId(staffId);
        // Lấy tin nhắn gần nhất cho mỗi cuộc trò chuyện (tùy chọn)
        conversations.forEach(conv -> {
            List<Message> messages = messageRepository.findByConversationId(Long.valueOf(conv.getId()));
            if (!messages.isEmpty()) {
                conv.setLastMessage(messages.get(messages.size() - 1).getContent()); // Tin nhắn gần nhất
            }
        });
        return ResponseEntity.ok(conversations);
    }

    @GetMapping("/staff")
    public ResponseEntity<List<Staff>> getAllStaff() {
        List<Staff> staffList = staffRepository.findAll();
        return ResponseEntity.ok(staffList);
    }
}

