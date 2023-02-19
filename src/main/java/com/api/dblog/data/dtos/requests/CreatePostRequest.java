package com.api.dblog.data.dtos.requests;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePostRequest {
    private String title;
    private String content;
}
