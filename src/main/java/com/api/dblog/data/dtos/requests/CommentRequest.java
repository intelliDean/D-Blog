package com.api.dblog.data.dtos.requests;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequest {
    private Long postId;
    private String username;
    private String content;
}
