package com.api.dblog.controllers;

import com.api.dblog.data.dtos.entiies_dto.AppUserDto;
import com.api.dblog.data.dtos.requests.RegisterRequest;
import com.api.dblog.data.dtos.responses.RegisterResponse;
import com.api.dblog.data.dtos.responses.UpdateResponse;
import com.api.dblog.data.models.AppUser;
import com.api.dblog.services.appUser_services.AppUserService;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class AppUserController {
    private final AppUserService appUserService;
    private final ModelMapper mapper;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        RegisterResponse registerResponse = appUserService.register(registerRequest);

        return ResponseEntity.status(registerResponse.getCode()).body(registerResponse);
    }


    @GetMapping("{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        AppUser user = appUserService.getUserById(userId);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(appUserService.getAllUsers());
    }

    @PatchMapping(value = "{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateUserField(@PathVariable Long id, @RequestBody JsonPatch updatePatch) {
        try {
            UpdateResponse updateResponse = appUserService.updateField(id, updatePatch);
            return ResponseEntity.status(HttpStatus.OK).body(updateResponse);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody AppUserDto userDto) {
        UpdateResponse response = appUserService.updateUser(id, userDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long userId) {
        UpdateResponse response = appUserService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("deleteAll")
    public ResponseEntity<?> deleteAllUsers() {
        UpdateResponse response = appUserService.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
