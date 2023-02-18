package com.api.dblog.services.appUser_services;

import com.api.dblog.data.dtos.entiies_dto.AppUserDto;
import com.api.dblog.data.dtos.requests.RegisterRequest;
import com.api.dblog.data.dtos.responses.RegisterResponse;
import com.api.dblog.data.dtos.responses.UpdateResponse;
import com.api.dblog.data.models.AppUser;
import com.github.fge.jsonpatch.JsonPatch;

import java.util.List;

public interface AppUserService {
    RegisterResponse register(RegisterRequest registerRequest);
    AppUser getUserById(Long userId);
    List<AppUser> getAllUsers();
    UpdateResponse updateField(Long userId, JsonPatch patchUpdate);
    UpdateResponse updateUser(Long userId, AppUserDto userDto);
    UpdateResponse deleteUser(Long userId);
    UpdateResponse deleteAll();

}
