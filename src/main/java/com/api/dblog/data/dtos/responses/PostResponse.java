package com.api.dblog.data.dtos.responses;

import com.api.dblog.data.dtos.entiies_dto.PostDto;
import lombok.*;


import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
    private String username;
    private PostDto posts;
}
