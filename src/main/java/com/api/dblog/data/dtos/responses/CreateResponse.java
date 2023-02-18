package com.api.dblog.data.dtos.responses;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateResponse {
    private Long id;
    private int code;
    private String message;
    private boolean isPosted;
}
