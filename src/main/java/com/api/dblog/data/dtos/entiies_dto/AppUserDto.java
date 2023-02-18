package com.api.dblog.data.dtos.entiies_dto;

import com.api.dblog.data.models.Address;
import com.api.dblog.data.models.Gender;
import com.api.dblog.data.models.Post;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUserDto {
    private Long id;
    private String name;
    private String username;
    private int age;
    private String phoneNumber;
    private String password;
    private String email;
    private Address address;
    private Gender gender;
    private MultipartFile profilePicture;
    private String registeredAt;
    private List<Post> post;
}
