package com.smartcity.service;

import com.smartcity.dao.OrganizationDao;
import com.smartcity.domain.Organization;
import com.smartcity.dto.OrganizationDto;
import com.smartcity.dto.UserDto;
import com.smartcity.mapperDto.OrganizationDtoMapper;
import com.smartcity.mapperDto.UserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private OrganizationDao organizationDao;
    private OrganizationDtoMapper organizationDtoMapper;
    private UserDtoMapper userDtoMapper;

    @Autowired
    public OrganizationServiceImpl(OrganizationDao organizationDao, OrganizationDtoMapper organizationDtoMapper,
                                   UserDtoMapper userDtoMapper) {
        this.organizationDao = organizationDao;
        this.organizationDtoMapper = organizationDtoMapper;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public OrganizationDto create(OrganizationDto organizationDto) {
        return organizationDtoMapper.organizationToOrganizationDto(organizationDao.create(
                organizationDtoMapper.organizationDtoToOrganization(organizationDto)));
    }

    @Override
    public OrganizationDto findById(Long id) {
        return organizationDtoMapper.organizationToOrganizationDto(organizationDao.findById(id));
    }

    @Override
    public OrganizationDto update(OrganizationDto organizationDto) {
        return organizationDtoMapper.organizationToOrganizationDto(organizationDao.update(
                organizationDtoMapper.organizationDtoToOrganization(organizationDto)));
    }

    @Override
    public boolean delete(Long id) {
        return organizationDao.delete(id);
    }

    @Override
    public List<OrganizationDto> findAll() {
        return mapListDto(organizationDao.findAll());
    }

    @Override
    public boolean addUserToOrganization(OrganizationDto organizationDto, UserDto userDto) {
        return organizationDao.addUserToOrganization(
                organizationDtoMapper.organizationDtoToOrganization(organizationDto),
                userDtoMapper.convertUserDtoIntoUser(userDto));
    }

    @Override
    public boolean removeUserFromOrganization(OrganizationDto organizationDto, UserDto userDto) {
        return organizationDao.removeUserFromOrganization(organizationDtoMapper.organizationDtoToOrganization(organizationDto),
                userDtoMapper.convertUserDtoIntoUser(userDto));
    }

    private List<OrganizationDto> mapListDto(List<Organization> organizations) {
        return organizations.stream().map(
                organizationDtoMapper::organizationToOrganizationDto).collect(Collectors.toList());
    }
}
