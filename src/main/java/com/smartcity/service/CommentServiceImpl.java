package com.smartcity.service;

import com.smartcity.dao.CommentDao;
import com.smartcity.dto.CommentDto;
import com.smartcity.dto.UserDto;
import com.smartcity.mapperDto.CommentDtoMapper;
import com.smartcity.mapperDto.UserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentDao commentDao;
    private CommentDtoMapper mapper;
    private UserDtoMapper userMapper;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao, CommentDtoMapper mapper, UserDtoMapper userMapper) {
        this.commentDao = commentDao;
        this.mapper = mapper;
        this.userMapper = userMapper;
    }

    @Override
    public CommentDto create(CommentDto commentDto) {
        return mapper.commentToCommentDto(commentDao.create(mapper.commentDtoToComment(commentDto)));
    }

    @Override
    public CommentDto findById(Long id) {
        return mapper.commentToCommentDto(commentDao.findById(id));
    }

    @Override
    public CommentDto update(CommentDto commentDto) {
        return mapper.commentToCommentDto(commentDao.update(mapper.commentDtoToComment(commentDto)));
    }

    @Override
    public boolean delete(Long id) {
        return commentDao.delete(id);
    }

    @Override
    public List<CommentDto> findByTaskId(Long id) {
        return commentDao.findByTaskId(id).stream()
                .map(t -> mapper.commentToCommentDto(t)).collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> findByUserId(Long id) {
        return commentDao.findByUserId(id).stream()
                .map(t -> mapper.commentToCommentDto(t)).collect(Collectors.toList());
    }

    @Override
    public boolean addUserToCommentSeen(CommentDto comment, UserDto user) {
        return commentDao.addUserToCommentSeen(mapper.commentDtoToComment(comment), userMapper.convertUserDtoIntoUser(user));
    }

}
