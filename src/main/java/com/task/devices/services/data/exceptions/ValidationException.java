package com.task.devices.services.data.exceptions;

import com.task.devices.services.data.exceptions.AppException;

public class ValidationException extends AppException {
    public ValidationException(String message) {
        super(message);
    }
}
