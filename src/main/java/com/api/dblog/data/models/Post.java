package com.api.dblog.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String createdAt;
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Comment> comments;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private AppUser appUser;
}
