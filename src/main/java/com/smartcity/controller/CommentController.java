package com.smartcity.controller;

import com.smartcity.dto.CommentDto;
import com.smartcity.dto.transfer.ExistingRecord;
import com.smartcity.dto.transfer.NewRecord;
import com.smartcity.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CommentDto createComment(@Validated(NewRecord.class) @RequestBody CommentDto commentDto) {
        return commentService.create(commentDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommentDto findById(@PathVariable("id") Long id) {
        return commentService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}")
    public CommentDto updateComment(
            @Validated(ExistingRecord.class)
            @PathVariable("id") Long id,
            @RequestBody CommentDto commentDto) {
        commentDto.setId(id);
        return commentService.update(commentDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{id}")
    public boolean deleteComment(@PathVariable("id") Long id) {
        return commentService.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/taskId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CommentDto> findByTaskId(@PathVariable("id") Long id) {
        return commentService.findByTaskId(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/userId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CommentDto> findByUserId(@PathVariable("id") Long id) {
        return commentService.findByUserId(id);
    }


}
