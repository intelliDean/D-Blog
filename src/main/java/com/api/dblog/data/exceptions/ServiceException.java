package com.api.dblog.data.exceptions;

import java.io.Serial;

public class ServiceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1;

    public ServiceException(String message) {
        super(message);
    }
}
