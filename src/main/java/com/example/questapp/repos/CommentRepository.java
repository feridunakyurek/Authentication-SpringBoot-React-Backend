package com.example.questapp.repos;

import com.example.questapp.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>{
    List<Comment> findByUserIdAndPostId(Long userId, Long postId);

    List<Comment> findByPostId(Long postId);

    List<Comment> findByUserId(Long userId);
}
