package com.task.devices.services.data;

import com.task.devices.domain.OperationSystem;
import com.task.devices.repository.DeviceEntity;
import com.task.devices.services.IValidatable;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class DeviceDto implements IValidatable {
    @Getter
    @NotEmpty
    private final String deviceId;
    @Valid
    @NotNull
    private final NewDeviceDto newDeviceDto;

    public DeviceDto(String userId, String deviceId, OperationSystem operationSystem, String userData) {
        newDeviceDto = new NewDeviceDto(userId, operationSystem, userData);
        this.deviceId = deviceId;
        this.validate();
    }

    public String getUserId() {
        return newDeviceDto.getUserId();
    }

    public OperationSystem getOperationSystem() {
        return newDeviceDto.getOperationSystem();
    }

    public String getUserData() {
        return newDeviceDto.getUserData();
    }

    public static DeviceDto fromEntity(DeviceEntity device) {
        return new DeviceDto(device.getUserId(), device.getDeviceId(), device.getOperationSystem(), device.getUserData());
    }
    public DeviceEntity toNewEntity(){
        DeviceEntity deviceEntity = new DeviceEntity();
        deviceEntity.setDeviceId(deviceId);
        deviceEntity.setOperationSystem(newDeviceDto.getOperationSystem());
        deviceEntity.setUserData(newDeviceDto.getUserData());
        deviceEntity.setUserId(newDeviceDto.getUserId());
        return deviceEntity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userId;
        private OperationSystem operationSystem;
        private String userData;
        private String deviceId;

        private Builder() {
        }

        public Builder deviceId(String deviceId){
            this.deviceId = deviceId;
            return this;
        }

        public Builder userId(String userId){
            this.userId = userId;
            return this;
        }
        public Builder operationSystem(String operationSystem){
            this.operationSystem = OperationSystem.valueOf(operationSystem.toUpperCase());
            return this;
        }

        public Builder operationSystem(OperationSystem operationSystem){
            this.operationSystem = operationSystem;
            return this;
        }

        public Builder userData(String userData){
            this.userData = userData;
            return this;
        }

        public DeviceDto build(){
            return new DeviceDto(userId, deviceId, operationSystem, userData);
        }
    }
}
