package com.smartcity.service;

import com.smartcity.dao.CommentDao;
import com.smartcity.dao.UserDao;
import com.smartcity.dto.CommentDto;
import com.smartcity.dto.UserDto;
import com.smartcity.mapperDto.CommentDtoMapper;
import com.smartcity.mapperDto.UserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentDao commentDao;
    private CommentDtoMapper mapper;
    private UserDtoMapper userMapper;
    private UserDao userDao;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao, CommentDtoMapper mapper, UserDtoMapper userMapper, UserDao userDao) {
        this.commentDao = commentDao;
        this.mapper = mapper;
        this.userMapper = userMapper;
        this.userDao = userDao;
    }

    @Override
    public CommentDto create(CommentDto commentDto) {

        CommentDto result = mapper.commentToCommentDto(commentDao.create(mapper.commentDtoToComment(commentDto)));
        return setUserSeen(result);
    }

    @Override
    public CommentDto findById(Long id) {
        CommentDto result = mapper.commentToCommentDto(commentDao.findById(id));
        return setUserSeen(result);
    }

    @Override
    public CommentDto update(CommentDto commentDto) {

        CommentDto result = mapper.commentToCommentDto(commentDao.update(mapper.commentDtoToComment(commentDto)));
        return setUserSeen(result);
    }

    @Override
    public boolean delete(Long id) {
        return commentDao.delete(id);
    }

    @Override
    public List<CommentDto> findByTaskId(Long id) {
        List<CommentDto> result = commentDao.findByTaskId(id).stream()
                .map(t -> mapper.commentToCommentDto(t)).collect(Collectors.toList());
        return setUserSeen(result);
    }

    @Override
    public List<CommentDto> findByUserId(Long id) {
        List<CommentDto> result = commentDao.findByUserId(id).stream()
                .map(t -> mapper.commentToCommentDto(t)).collect(Collectors.toList());
        return setUserSeen(result);
    }



    @Override
    public boolean addUserToCommentSeen(CommentDto comment, UserDto user) {
        return commentDao.addUserToCommentSeen(mapper.commentDtoToComment(comment), userMapper.convertUserDtoIntoUser(user));
    }

    private List<CommentDto> setUserSeen(List<CommentDto> result) {
        return  result.stream().map(comm -> setUserSeen(comm)).collect(Collectors.toList());
    }

    private CommentDto setUserSeen(CommentDto result) {
        List<Long> userSeen = userDao.findUserByCommentId(result.getId()).stream()
                .map(t -> userMapper.convertUserIntoUserDto(t).getId())
                .collect(Collectors.toList());
        result.setUserSeen(userSeen);
        return result;
    }
}
