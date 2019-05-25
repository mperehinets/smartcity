package com.smartcity.service;

import com.smartcity.dto.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    CommentDto create(CommentDto commentDto);

    CommentDto findById(Long id);

    CommentDto update(CommentDto commentDto);

    boolean delete(Long id);

    List<CommentDto> findByTaskId(Long id);

    List<CommentDto> findByUserId(Long id);

}
