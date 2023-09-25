package com.generic.khatabook.rating.repository;

import com.generic.khatabook.rating.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
}
