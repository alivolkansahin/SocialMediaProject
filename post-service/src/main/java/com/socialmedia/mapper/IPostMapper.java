package com.socialmedia.mapper;

import com.socialmedia.dto.response.PostSaveResponseDto;
import com.socialmedia.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IPostMapper {
    IPostMapper INSTANCE = Mappers.getMapper(IPostMapper.class);

    @Mapping(target = "created", expression = "java(post.getCreated().toString())")
    PostSaveResponseDto savePostToDto(Post post);


}
