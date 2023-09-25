package com.generic.khatabook.rating.services.impl;

import com.generic.khatabook.rating.model.CommentDTO;
import com.generic.khatabook.rating.repository.CommentRepository;
import com.generic.khatabook.rating.services.CommentService;
import com.generic.khatabook.rating.services.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDTO get(final String commentId) {
        return commentMapper.mapToDTO(commentRepository.findById(commentId));
    }

    @Override
    public CommentDTO save(final CommentDTO comment) {
        return commentMapper.mapToDTO(commentRepository.save(commentMapper.mapToEntity(comment)));
    }

    @Override
    public void update(final CommentDTO oldComment, final CommentDTO comment) {
        oldComment.setCommentDetails(comment.getCommentDetails());
        oldComment.setUpdatedDate(LocalDateTime.now(Clock.systemDefaultZone()));
        oldComment.setUpdatedBy("By Update System");
        save(oldComment);
    }

    private CommentDTO mixme(final CommentDTO oldCommentq, final CommentDTO commentq) {
        oldCommentq.setCommentDetails(commentq.getCommentDetails());
        oldCommentq.setUpdatedDate(LocalDateTime.now(Clock.systemDefaultZone()));
        oldCommentq.setUpdatedBy("By Update System");

        return oldCommentq;
    }
}
