package com.darshan.springsingleflight.demo.mappers;

import com.darshan.springsingleflight.demo.entity.MockBlogEntity;
import com.darshan.springsingleflight.demo.dto.ResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BlogResponseMapper {
    ResponseDTO toDto(MockBlogEntity entity);
}
