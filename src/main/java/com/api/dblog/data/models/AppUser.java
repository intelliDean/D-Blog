package com.api.dblog.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String username;
    private int age;
    private String phoneNumber;
    private String password;
    private String email;
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private Address address;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Transient
    private MultipartFile profilePicture;
    private String registeredAt;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "appuser_id")
    private List<Post> posts;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Comment> comments;

}
