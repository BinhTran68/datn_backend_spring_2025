package com.poly.app.domain.repository;

import com.poly.app.domain.model.Announcement;
import com.poly.app.domain.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments,Integer> {
List<Comments> findByProductIdOrderByCreatedAtAsc(Integer productId);
}
