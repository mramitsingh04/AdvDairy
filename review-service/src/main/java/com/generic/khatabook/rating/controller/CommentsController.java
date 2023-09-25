package com.generic.khatabook.rating.controller;

import com.generic.khatabook.rating.model.CommentDTO;
import com.generic.khatabook.rating.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/comment-service")
public class CommentsController {
    @Autowired
    private CommentService commentService;
    @GetMapping(path = "{commentId}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable("commentId") String commentId)
    {
        return ResponseEntity.ok(commentService.get(commentId));
    }

    @PostMapping(consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO comment)
    {
        comment.setCreatedDate(LocalDateTime.now(Clock.systemDefaultZone()));
        comment.setCreatedBy("amit.singh@myemail.com");
        return new ResponseEntity<>(commentService.save(comment), HttpStatus.CREATED);
    }

    @PutMapping(path = ("/{commentId}"))
    public ResponseEntity<CommentDTO> updateComment(@PathVariable String commentId, @RequestBody CommentDTO comment)
    {
        final CommentDTO oldComment = commentService.get(commentId);
        commentService.update(oldComment, comment);
        return new ResponseEntity<>(oldComment, HttpStatus.ACCEPTED);
    }
}