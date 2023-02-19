package com.api.dblog.services.appUser_services;

import com.api.dblog.data.dtos.entiies_dto.AppUserDto;
import com.api.dblog.data.dtos.requests.RegisterRequest;
import com.api.dblog.data.dtos.responses.RegisterResponse;
import com.api.dblog.data.dtos.responses.UpdateResponse;
import com.api.dblog.data.exceptions.ServiceException;
import com.api.dblog.data.models.AppUser;
import com.api.dblog.data.repositories.AppUserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final ModelMapper modelMapper;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        AppUser appUser = AppUser.builder()
                .name(registerRequest.getName())
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .registeredAt(LocalDateTime.now().toString())
                .build();

        AppUser savedAppUser = appUserRepository.save(appUser);

        return RegisterResponse.builder()
                .id(appUser.getId())
                .code(HttpStatus.CREATED.value())
                .message("AppUser Registered successfully")
                .isSuccess(true)
                .build();
    }

    @Override
    public AppUser getUserById(Long userId) {
        return appUserRepository.findById(userId)
                .orElseThrow(()->new ServiceException("AppUser not found"));

    }

    @Override
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll()
                .stream()
                .map(x->modelMapper.map(x, AppUser.class))
                .toList();
    }

    @Override
    public UpdateResponse updateField(Long id, JsonPatch patchUpdate) {
        ObjectMapper mapper = new ObjectMapper();
        AppUser user = getUserById(id);// patch will have issue cos of wrong mapping brb
        JsonNode node = mapper.convertValue(user, JsonNode.class);
        try {
            JsonNode updatedNode = patchUpdate.apply(node);
            AppUser updatedUser = mapper.convertValue(updatedNode, AppUser.class);
            updatedUser.setAddress(user.getAddress());  //JsonNode makes object null hence I reset the object Address

            AppUser savedUser = appUserRepository.save(updatedUser);
            return UpdateResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message(String.format("User with ID %d updated successfully", savedUser.getId()))
                    .build();
        } catch (JsonPatchException ex) {
            log.error(ex.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public UpdateResponse updateUser(Long userId, AppUserDto userDto) {
        AppUser user = getUserById(userId);

        AppUser newUser = updateMap(userDto, user);

        AppUser updatedUser = appUserRepository.save(newUser);

        return UpdateResponse.builder()
                .code(HttpStatus.OK.value())
                .message(String.format("User with ID %d updated successfully",
                        updatedUser.getId())).build();
    }

    private static AppUser updateMap(AppUserDto userDto, AppUser user) {
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        user.setGender(userDto.getGender());
        user.setUsername(userDto.getUsername());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }


    @Override
    public UpdateResponse deleteUser(Long userId) {
        appUserRepository.deleteById(userId);

        return UpdateResponse.builder()
                .code(HttpStatus.OK.value())
                .message("User deleted successfully").build();
    }

    @Override
    public UpdateResponse deleteAll() {
        appUserRepository.deleteAll();
        return UpdateResponse.builder()
                .code(HttpStatus.OK.value())
                .message("All users deleted successfully").build();
    }
}
