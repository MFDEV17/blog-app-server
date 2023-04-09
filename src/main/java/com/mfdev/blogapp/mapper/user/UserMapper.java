package com.mfdev.blogapp.mapper.user;

import com.mfdev.blogapp.dto.user.RegistrationDto;
import com.mfdev.blogapp.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(nullValuePropertyMappingStrategy = IGNORE)
public abstract class UserMapper {

  @Mapping(target = "dateCreate", ignore = true)
  @Mapping(target = "role", ignore = true)
  @Mapping(target = "isEnable", ignore = true)
  @Mapping(target = "id", ignore = true)
  public abstract User registrationDtoToUser(RegistrationDto dto);
}
