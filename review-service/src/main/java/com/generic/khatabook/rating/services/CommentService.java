package com.generic.khatabook.rating.services;

import com.generic.khatabook.rating.model.CommentDTO;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    CommentDTO get(String commentId);

    CommentDTO save(CommentDTO comment);

    void update(CommentDTO oldComment, CommentDTO comment);
}

