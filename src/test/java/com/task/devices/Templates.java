package com.task.devices;

import com.task.devices.domain.OperationSystem;
import com.task.devices.domain.User;
import com.task.devices.domain.api.IUser;
import com.task.devices.domain.api.UserRole;
import com.task.devices.repository.DevicesRepository;
import com.task.devices.services.data.DeviceDto;
import com.task.devices.services.data.NewDeviceDto;
import com.task.devices.templates.DevicesTestRepository;

import java.time.LocalDateTime;

public class Templates {
    public static final String USER_ID = "ff970cd1-e9d1-468a-ae71-bdb5a115c5aa";
    public static final String DEVICE_ID = "IOS-12112";

    public static DevicesRepository devicesRepository() {
        return new DevicesTestRepository();
    }

    public static IUser user(UserRole userRole) {
        return new User(USER_ID, userRole, "bootcamp", LocalDateTime.now(), LocalDateTime.now());
    }

    public static NewDeviceDto newDeviceDto() {
        return NewDeviceDto.builder()
                .operationSystem(OperationSystem.IOS)
                .userId(USER_ID)
                .userData("some data")
                .build();
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
