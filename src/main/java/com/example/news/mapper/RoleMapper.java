package com.example.news.mapper;

import com.example.news.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    @Mapping(target = "authority", source = "type")
    Role roleTypeToRole(Role.RoleType type);

    List<Role> roleListFromRoleTypeList(List<Role.RoleType> types);
}
