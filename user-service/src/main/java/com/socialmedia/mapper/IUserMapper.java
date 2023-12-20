package com.socialmedia.mapper;

import com.socialmedia.dto.request.AuthDeleteRequestDto;
import com.socialmedia.dto.request.AuthUpdateRequestDto;
import com.socialmedia.dto.request.UserSaveRequestDto;
import com.socialmedia.dto.response.UserProfileResponseDto;
import com.socialmedia.entity.UserProfile;
import com.socialmedia.rabbitmq.model.RegisterElasticModel;
import com.socialmedia.rabbitmq.model.RegisterModel;
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

    UserProfile saveModelToUser(RegisterModel registerModel);

//    @Mapping(target = "userProfileId", source = "id")   // before mongoDb
    UserProfileResponseDto toUserProfileResponseDto(UserProfile userProfile);

//    @Mapping(target = "userProfileId", source = "id")   // before mongoDb
    RegisterElasticModel UserProfileToModel(UserProfile userProfile);

}
