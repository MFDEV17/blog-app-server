package com.mfdev.blogapp.mapper.user;

import com.mfdev.blogapp.dto.requestdto.user.RegistrationDto;
import com.mfdev.blogapp.dto.requestdto.user.UpdateAccountInfoDto;
import com.mfdev.blogapp.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(nullValuePropertyMappingStrategy = IGNORE)
public abstract class UserMapper {

  @Mapping(target = "dateCreate", ignore = true)
  @Mapping(target = "role", ignore = true)
  @Mapping(target = "isEnable", ignore = true)
  @Mapping(target = "id", ignore = true)
  public abstract User registrationDtoToUser(RegistrationDto dto);

  @Mapping(target = "password", source = "newPassword")
  @Mapping(target = "username", source = "newUsername")
  @Mapping(target = "dateCreate", ignore = true)
  @Mapping(target = "isEnable", ignore = true)
  @Mapping(target = "role", ignore = true)
  public abstract void updateUserEntityByUpdateAccountInfoDto(@MappingTarget User user, UpdateAccountInfoDto dto);
}
