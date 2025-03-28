package com.poly.app.domain.client.repository;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private String fullName;
    private String comment;
    private Long createdAt;
}