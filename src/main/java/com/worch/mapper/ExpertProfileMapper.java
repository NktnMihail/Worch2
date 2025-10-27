package com.worch.mapper;

import com.worch.model.dto.response.ExpertProfileResponse;
import com.worch.model.entity.ExpertProfile;
import com.worch.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExpertProfileMapper {

    @Mapping(target = "userName", expression = "java(buildUserName(user))")
    @Mapping(target = "id", source = "expertProfile.id")
    ExpertProfileResponse toDto(ExpertProfile expertProfile, User user);

    default String buildUserName(User user) {
        if (user == null) {
            return null;
        }
        return buildUserName(user.getFirstName(), user.getLastName());
    }

    default String buildUserName(String firstName, String lastName) {
        if (firstName == null && lastName == null) return null;
        if (firstName == null) return lastName;
        if (lastName == null) return firstName;
        return firstName + " " + lastName;
    }
}