package com.api.dblog.data.dtos.requests;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequest {
    private String content;
}
