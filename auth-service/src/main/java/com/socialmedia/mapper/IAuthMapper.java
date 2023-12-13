package com.socialmedia.mapper;

import com.socialmedia.dto.request.AuthRegisterRequestDto;
import com.socialmedia.dto.request.UserSaveRequestDto;
import com.socialmedia.dto.response.AuthLoginResponseDto;
import com.socialmedia.dto.response.AuthRegisterResponseDto;
import com.socialmedia.entity.Auth;
import com.socialmedia.rabbitmq.model.MailModel;
import com.socialmedia.rabbitmq.model.RegisterModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {

    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);

    Auth registerDtoToAuth(AuthRegisterRequestDto dto);

    AuthRegisterResponseDto registerAuthToDto(Auth auth);


    @Mapping(target = "role" , expression = "java(auth.getRole().name())")
    @Mapping(target = "status" , expression = "java(auth.getStatus().name())")
    AuthLoginResponseDto loginAuthToDto(Auth auth);

    @Mapping(target = "authId", source = "id")
    UserSaveRequestDto registerAuthToUserDto(Auth auth);

    @Mapping(target = "authId", source = "id")
    RegisterModel registerAuthToModel(Auth auth);

    MailModel mailAuthToModel(Auth auth);


}
