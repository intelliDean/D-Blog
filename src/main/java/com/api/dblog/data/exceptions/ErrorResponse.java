package com.api.dblog.data.exceptions;

import lombok.*;
import org.apache.camel.FallbackConverter;

import java.time.LocalDate;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private Integer statusCode;
    private String message;
    private String timeStamp;
}
