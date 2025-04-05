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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CommentController {
    CommentService commentService;
    SimpMessagingTemplate messagingTemplate;

    // API lấy tất cả bình luận
//    @GetMapping("/all")
//    public ApiResponse<List<CommentDTO>> getAllComments() {
//        List<CommentDTO> comments = commentService.getAllComments();
//        return ApiResponse.<List<CommentDTO>>builder()
//                .message("Lấy danh sách bình luận thành công")
//                .data(comments)
//                .build();
//    }
    @GetMapping("/all")
    public ApiResponse<List<CommentDTO>> getAllComments(
            @RequestParam(value = "sortOrder", defaultValue = "desc") String sortOrder) {

        // Kiểm tra và đặt lại sortOrder hợp lệ
        if (!sortOrder.equalsIgnoreCase("asc") && !sortOrder.equalsIgnoreCase("desc")) {
            return ApiResponse.<List<CommentDTO>>builder()
                    .message("Tham số sortOrder không hợp lệ. Chỉ có thể là 'asc' hoặc 'desc'.")
                    .build();
        }

        // Lấy danh sách bình luận từ service
        List<CommentDTO> comments = commentService.getAllComments();

        // Sắp xếp bình luận theo thời gian
        comments.sort((c1, c2) -> {
            if (sortOrder.equalsIgnoreCase("asc")) {
                return c1.getCreatedAt().compareTo(c2.getCreatedAt());
            } else {
                return c2.getCreatedAt().compareTo(c1.getCreatedAt());
            }
        });

        return ApiResponse.<List<CommentDTO>>builder()
                .message("Lấy danh sách bình luận thành công")
                .data(comments)
                .build();
    }
    // API lấy tất cả tên sản phẩm từ bình luận
// API lấy tất cả tên sản phẩm từ bình luận
    @GetMapping("/productName")
    public ApiResponse<List<Map<String, String>>> getProductNames() {
        List<String> productNames = commentService.getProductNames();

        // Convert list of strings to list of maps with productName key
        List<Map<String, String>> formattedProductNames = productNames.stream()
                .map(name -> {
                    Map<String, String> productMap = new HashMap<>();
                    productMap.put("productName", name);
                    return productMap;
                })
                .collect(Collectors.toList());

        return ApiResponse.<List<Map<String, String>>>builder()
                .message("Lấy danh sách tên sản phẩm thành công")
                .data(formattedProductNames)
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
//    //tìm kiếm
@GetMapping("/search")
public ApiResponse<List<CommentDTO>> searchComments(
        @RequestParam(required = false) String productName,
        @RequestParam(required = false) Long createdAt
) {
    List<CommentDTO> comments = commentService.searchCommentsByProductNameAndCreatedAt(productName, createdAt);
    return ApiResponse.<List<CommentDTO>>builder()
            .message("Tìm kiếm bình luận thành công")
            .data(comments)
            .build();
}


}
