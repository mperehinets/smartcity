package com.smartcity.service;

import com.smartcity.dao.OrganizationDao;
import com.smartcity.domain.Organization;
import com.smartcity.dto.OrganizationDto;
import com.smartcity.mapperDto.OrganizationDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private OrganizationDao organizationDao;
    private OrganizationDtoMapper organizationDtoMapper;

    @Autowired
    public OrganizationServiceImpl(OrganizationDao organizationDao, OrganizationDtoMapper organizationDtoMapper) {
        this.organizationDao = organizationDao;
        this.organizationDtoMapper = organizationDtoMapper;
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

    private List<OrganizationDto> mapListDto(List<Organization> organizations) {
        return organizations.stream().map(
                organizationDtoMapper::organizationToOrganizationDto).collect(Collectors.toList());
    }
}
