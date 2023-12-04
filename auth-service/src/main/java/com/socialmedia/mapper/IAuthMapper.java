package com.socialmedia.mapper;

import com.socialmedia.dto.request.AuthRegisterRequestDto;
import com.socialmedia.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {

    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);

    Auth registerDtoToAuth(AuthRegisterRequestDto dto);

}
