package com.example.backend.mappers;

import com.example.backend.dtos.RegisterRequest;
import com.example.backend.dtos.UserResponse;
import com.example.backend.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "passwordHash",ignore = true)
    @Mapping(target = "role",ignore = true)
    @Mapping(target = "createdAt",ignore = true)
    User toEntity(RegisterRequest request);

    UserResponse toResponse(User user);
}
