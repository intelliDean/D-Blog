package com.api.dblog.data.dtos.responses;


import com.api.dblog.data.models.Post;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
    private String username;
    private Post post;
}
