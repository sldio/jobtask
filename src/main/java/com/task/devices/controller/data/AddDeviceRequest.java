package com.task.devices.controller.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddDeviceRequest {
    private String deviceId;
    private String userData;
    private String operationSystem;
}
