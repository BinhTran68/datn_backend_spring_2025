package com.poly.app.domain.client.service;

import com.poly.app.domain.client.repository.CommentDTO;
import com.poly.app.domain.model.Comments;
import com.poly.app.domain.model.Customer;
import com.poly.app.domain.model.ProductDetail;
import com.poly.app.domain.repository.CommentsRepository;
import com.poly.app.domain.repository.CustomerRepository;
import com.poly.app.domain.repository.ProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentsRepository commentRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public Comments saveComment(Integer productId, Integer customerId, String commentText) {
        ProductDetail product = productDetailRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Comments comment = new Comments();
        comment.setProduct(product);
        comment.setCustomer(customer);
        comment.setComment(commentText);
        comment.setCreatedAt(Calendar.getInstance().getTimeInMillis());

        Comments savedComment = commentRepository.save(comment);

        // Gửi bình luận mới qua WebSocket
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setFullName(customer.getFullName());
        commentDTO.setComment(commentText);
        commentDTO.setCreatedAt(savedComment.getCreatedAt());
        messagingTemplate.convertAndSend("/topic/comments/" + productId, commentDTO);

        return savedComment;
    }

    public List<CommentDTO> getCommentsByProductId(Integer productId) {
        return commentRepository.findByProductIdOrderByCreatedAtAsc(productId)
                .stream()
                .map(comment -> {
                    CommentDTO dto = new CommentDTO();
                    dto.setFullName(comment.getCustomer().getFullName());
                    dto.setComment(comment.getComment());
                    dto.setCreatedAt(comment.getCreatedAt());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}