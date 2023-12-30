package com.generic.khatabook.rating.services.mapper;

import com.generic.khatabook.rating.entity.Comment;
import com.generic.khatabook.rating.model.CommentDTO;
import com.generic.khatabook.rating.model.Container;
import com.generic.khatabook.rating.model.Mapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CommentMapper implements Mapper<Comment, CommentDTO, Void> {
    @Override
    public Comment mapToEntity(final CommentDTO commentDTO) {
        return Comment.builder()
                .commentId(commentDTO.getCommentId())
                .commentDetails(commentDTO.getCommentDetails())
                .createdBy(commentDTO.getCreatedBy())
                .createdDate(commentDTO.getCreatedDate())
                .updatedDate(commentDTO.getUpdatedDate())
                .updatedDate(commentDTO.getUpdatedDate())
                .build();
    }

    @Override
    public Container<CommentDTO, Void> mapToContainer(final Comment comment) {
        if (Objects.isNull(comment)) {
            return Container.empty();
        }

        return Container.of(mapToDTO(comment));
    }
    @Override
    public CommentDTO mapToDTO(final Comment comment) {
        return CommentDTO.builder()
                .commentId(comment.getCommentId())
                .commentDetails(comment.getCommentDetails())
                .createdBy(comment.getCreatedBy())
                .createdDate(comment.getCreatedDate())
                .updatedDate(comment.getUpdatedDate())
                .updatedDate(comment.getUpdatedDate())
                .build();
    }
}
