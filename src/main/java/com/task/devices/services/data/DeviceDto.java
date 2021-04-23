package com.task.devices.services.data;

import com.task.devices.controller.data.AddDeviceRequest;
import com.task.devices.domain.OperationSystem;
import com.task.devices.repository.DeviceEntity;
import com.task.devices.services.IValidatable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Getter
@Builder
public class DeviceDto implements IValidatable {
    @NotEmpty
    private final String userId;
    @NotEmpty
    private final String deviceId;
    @NotNull
    private final OperationSystem operationSystem;
    @NotEmpty
    private final String userData;

    public DeviceDto(String userId, String deviceId, OperationSystem operationSystem, String userData) {
        this.userId = userId;
        this.deviceId = deviceId;
        this.operationSystem = operationSystem;
        this.userData = userData;
        this.validate();
    }

    public DeviceDto(String userId, AddDeviceRequest newDevice) {
        this.userId = userId;
        this.deviceId = newDevice.getDeviceId();
        this.operationSystem = OperationSystem.valueOf(newDevice.getOperationSystem().toUpperCase());
        this.userData = newDevice.getUserData();
        this.validate();
    }

    public static DeviceDto fromEntity(DeviceEntity device) {
        return new DeviceDto(device.getUserId(), device.getDeviceId(), device.getOperationSystem(), device.getUserData());
    }

    public DeviceEntity toNewEntity(){
        DeviceEntity deviceEntity = new DeviceEntity();
        deviceEntity.setDeviceId(deviceId);
        deviceEntity.setOperationSystem(operationSystem);
        deviceEntity.setUserData(userData);
        deviceEntity.setUserId(userId);
        return deviceEntity;
    }
}
