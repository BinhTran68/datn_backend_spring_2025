package com.poly.app.domain.client.controller;

import com.poly.app.domain.client.repository.CommentDTO;
import com.poly.app.domain.client.response.CommentMessage;
import com.poly.app.domain.client.service.CommentService;
import com.poly.app.domain.common.ApiResponse;
import com.poly.app.domain.model.Comments;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CommentController {
    CommentService commentService;
    SimpMessagingTemplate messagingTemplate;

    // API lấy tất cả bình luận
    @GetMapping("/all")
    public ApiResponse<List<CommentDTO>> getAllComments() {
        List<CommentDTO> comments = commentService.getAllComments();
        return ApiResponse.<List<CommentDTO>>builder()
                .message("Lấy danh sách bình luận thành công")
                .data(comments)
                .build();
    }

    @PutMapping("/reply")
    public ApiResponse<CommentDTO> replyToComment(@RequestBody CommentMessage message) {
        if (message == null || message.getParentId() == null) {
            throw new IllegalArgumentException("Request body không hợp lệ hoặc thiếu Parent ID!");
        }

        // Gọi service để xử lý phản hồi
        CommentDTO reply = commentService.replyToComment(message);

        // Trả về response cho admin
        return ApiResponse.<CommentDTO>builder()
                .message("Trả lời bình luận thành công")
                .data(reply)
                .build();
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse<String> deleteComment(@PathVariable Integer commentId) {
        commentService.deleteCommentByAdmin(commentId);
        return ApiResponse.<String>builder()
                .message("Xóa bình luận thành công")
                .data("Deleted Comment ID: " + commentId)
                .build();
    }


}
