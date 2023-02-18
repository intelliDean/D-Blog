package com.api.dblog.data.dtos.responses;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateResponse {
    private int code;
    private String message;
}
