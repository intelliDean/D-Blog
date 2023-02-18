package com.api.dblog.data.dtos.entiies_dto;

import com.api.dblog.data.models.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String createdAt;
    private List<Comment> comment;
    private AppUser appUser;
}
