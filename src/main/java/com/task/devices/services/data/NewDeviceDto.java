package com.task.devices.services.data;

import com.task.devices.controller.data.AddDeviceRequest;
import com.task.devices.domain.OperationSystem;
import com.task.devices.repository.DeviceEntity;
import com.task.devices.services.IValidatable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
public class NewDeviceDto implements IValidatable {
    @NotEmpty
    private final String userId;
    @NotNull
    private final OperationSystem operationSystem;
    @NotEmpty
    private final String userData;

    public NewDeviceDto(String userId, OperationSystem operationSystem, String userData) {
        this.userId = userId;
        this.operationSystem = operationSystem;
        this.userData = userData;
        this.validate();
    }

    public NewDeviceDto(String userId, AddDeviceRequest newDevice) {
        this(userId, OperationSystem.valueOf(newDevice.getOperationSystem().toUpperCase()), newDevice.getUserData());
    }

    public DeviceEntity toNewEntity(String deviceId){
        DeviceEntity deviceEntity = new DeviceEntity();
        deviceEntity.setOperationSystem(operationSystem);
        deviceEntity.setUserData(userData);
        deviceEntity.setUserId(userId);
        deviceEntity.setDeviceId(deviceId);
        return deviceEntity;
    }
}
