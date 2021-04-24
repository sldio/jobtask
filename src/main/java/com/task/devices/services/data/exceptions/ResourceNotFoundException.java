package com.task.devices.services.data.exceptions;

import com.task.devices.services.data.exceptions.AppException;

public class ResourceNotFoundException extends AppException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
