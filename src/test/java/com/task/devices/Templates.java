package com.task.devices;

import com.task.devices.domain.OperationSystem;
import com.task.devices.repository.DevicesRepository;
import com.task.devices.services.data.DeviceDto;
import com.task.devices.templates.DevicesTestRepository;

public class Templates {
    public static final String USER_ID = "ID-1111";
    public static final String DEVICE_ID = "IOS-12112";

    public static DevicesRepository devicesRepository() {
        return new DevicesTestRepository();
    }

    public static DeviceDto deviceDto() {
        return DeviceDto.builder()
                .deviceId(DEVICE_ID)
                .operationSystem(OperationSystem.IOS)
                .userId(USER_ID)
                .userData("some data")
                .build();
    }
    public static DeviceDto deviceDto(String deviceId) {
        return DeviceDto.builder()
                .deviceId(deviceId)
                .operationSystem(OperationSystem.IOS)
                .userId(USER_ID)
                .userData("some data")
                .build();
    }
}
