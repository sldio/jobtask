package com.task.devices.services;

import com.task.devices.controller.data.UpdateDeviceRequest;
import com.task.devices.domain.OperationSystem;
import com.task.devices.repository.DeviceEntity;
import com.task.devices.repository.DevicesRepository;
import com.task.devices.services.data.DeviceDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DevicesService {
    private final DevicesRepository devicesRepository;

    public DevicesService(DevicesRepository devicesRepository) {
        this.devicesRepository = devicesRepository;
    }

    public DeviceDto getDeviceById(String deviceId) {
        DeviceEntity device = devicesRepository.findByDeviceId(deviceId).orElseThrow(() -> new ResourceNotFoundException("Unknown device id " + deviceId));
        return DeviceDto.fromEntity(device);
    }

    public DeviceDto addDevice(DeviceDto newDeviceDto) {
        DeviceEntity savedDevice = devicesRepository.save(newDeviceDto.toNewEntity());
        return DeviceDto.fromEntity(savedDevice);
    }

    public DeviceDto updateDevice(String userId, String deviceId, UpdateDeviceRequest updateDeviceRequest) {
        DeviceEntity updatedDevice = devicesRepository.findByDeviceIdAndUserId(deviceId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no device " + deviceId + " for user " + userId));
        updatedDevice.setUserData(updateDeviceRequest.getUserData() == null ? updatedDevice.getUserData() : updateDeviceRequest.getUserData());
        updatedDevice.setOperationSystem(updateDeviceRequest.getOperationSystem() == null
                ? updatedDevice.getOperationSystem()
                : OperationSystem.valueOf(updateDeviceRequest.getOperationSystem().toUpperCase()));
        return DeviceDto.fromEntity(devicesRepository.save(updatedDevice));
    }

    public List<DeviceDto> getDevices(String userId) {
        return devicesRepository.findAllByUserId(userId)
                .stream()
                .map(DeviceDto::fromEntity)
                .collect(Collectors.toList());
    }
}
