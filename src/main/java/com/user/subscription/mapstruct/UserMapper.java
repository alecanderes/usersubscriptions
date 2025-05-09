package com.user.subscription.mapstruct;

import com.user.subscription.domain.User;
import com.user.subscription.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}