package com.socialmedia.mapper;

import com.socialmedia.dto.request.AuthDeleteRequestDto;
import com.socialmedia.dto.request.AuthUpdateRequestDto;
import com.socialmedia.dto.request.UserSaveRequestDto;
import com.socialmedia.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {

    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    UserProfile saveDtoToUserProfile(UserSaveRequestDto dto);

    @Mapping(target = "id", source = "authId")
    AuthUpdateRequestDto updateUserToAuthDto(UserProfile userProfile);

    @Mapping(target = "id", source = "authId")
    AuthDeleteRequestDto deleteUserToAuthDto(UserProfile userProfile);


}
