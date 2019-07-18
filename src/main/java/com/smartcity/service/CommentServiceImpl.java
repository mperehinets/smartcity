package com.smartcity.service;

import com.smartcity.dao.CommentDao;
import com.smartcity.dao.TaskDao;
import com.smartcity.dao.UserDao;
import com.smartcity.dao.UserOrganizationDao;
import com.smartcity.domain.Task;
import com.smartcity.dto.CommentDto;
import com.smartcity.dto.CommentNotificationDto;
import com.smartcity.dto.UserDto;
import com.smartcity.mapperDto.CommentDtoMapper;
import com.smartcity.mapperDto.UserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentDao commentDao;
    private CommentDtoMapper mapper;
    private UserOrganizationDao userOrganizationDao;
    private UserDtoMapper userMapper;
    private UserDao userDao;
    private TaskDao taskDao;
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao, CommentDtoMapper mapper, UserDtoMapper userMapper, UserDao userDao, TaskDao taskDao, UserOrganizationDao userOrganizationDao) {
        this.commentDao = commentDao;
        this.mapper = mapper;
        this.userMapper = userMapper;
        this.userDao = userDao;
        this.taskDao = taskDao;
        this.userOrganizationDao = userOrganizationDao;
    }

    @Override
    public CommentDto create(CommentDto commentDto) {
        CommentNotificationDto notification = new CommentNotificationDto();
        CommentDto result = mapper.commentToCommentDto(commentDao.create(mapper.commentDtoToComment(commentDto)));
        notification.setDescription(result.getDescription());
        UserDto userDto = userMapper.convertUserIntoUserDto(userDao.findById(result.getUserId()));
        Task task = taskDao.findById(result.getTaskId());
        Long organization = userOrganizationDao.findOrgIdById(task.getUsersOrganizationsId());
        notification.setOrganizationId(organization);
        notification.setUser(userDto.getSurname() + " " + userDto.getName());
        notification.setTask(task.getTitle());
        notification.setId(result.getId());
        notification.setUserId(result.getUserId());
        notification.setOrganizationId(userOrganizationDao.findOrgIdById(task.getUsersOrganizationsId()));
        simpMessagingTemplate.convertAndSend("/topic/comment.create", notification);
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
        return result.stream().map(comm -> setUserSeen(comm)).collect(Collectors.toList());
    }

    private CommentDto setUserSeen(CommentDto result) {
        List<Long> userSeen = userDao.findUserByCommentId(result.getId()).stream()
                .map(t -> userMapper.convertUserIntoUserDto(t).getId())
                .collect(Collectors.toList());
        result.setUserSeen(userSeen);
        return result;
    }

    @Autowired
    public void setSimpMessagingTemplate(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }
}
