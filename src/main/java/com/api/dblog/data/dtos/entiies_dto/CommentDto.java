package com.api.dblog.data.dtos.entiies_dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private String username;
    private String content;
    private String localDateTime;
}
