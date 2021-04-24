package com.task.devices.services;

import com.task.devices.controller.data.UpdateDeviceRequest;
import com.task.devices.domain.OperationSystem;
import com.task.devices.domain.api.IUser;
import com.task.devices.repository.DeviceEntity;
import com.task.devices.repository.DevicesRepository;
import com.task.devices.services.data.DeviceDto;
import com.task.devices.services.data.NewDeviceDto;
import com.task.devices.services.data.exceptions.DeviceAccessException;
import com.task.devices.services.data.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DevicesService {
    private final DevicesRepository devicesRepository;

    public DevicesService(DevicesRepository devicesRepository) {
        this.devicesRepository = devicesRepository;
    }

    public DeviceDto getDevice(IUser user, String deviceId) {
        DeviceEntity device = devicesRepository.findByDeviceId(deviceId).orElseThrow(() -> new ResourceNotFoundException("Unknown device id " + deviceId));
        if (!device.getUserId().equals(user.getSub())){
            throw new DeviceAccessException("User " + user.getSub() + " doesn't have access to device " + deviceId);
        }
        return DeviceDto.fromEntity(device);
    }

    public List<DeviceDto> getDevices(IUser user) {
        if (user.getRole().isAdmin()) {
            return readAll();
        } else {
            return readUserDevices(user);
        }
    }

    public DeviceDto addDevice(NewDeviceDto newDeviceDto) {
        DeviceEntity newDeviceEntity = newDeviceDto.toNewEntity(generateId(newDeviceDto));
        newDeviceEntity = devicesRepository.save(newDeviceEntity);
        return DeviceDto.fromEntity(newDeviceEntity);
    }

    public DeviceDto updateDevice(IUser user, String deviceId, UpdateDeviceRequest updateDeviceRequest) {
        DeviceEntity updatedDevice = devicesRepository.findByDeviceIdAndUserId(deviceId, user.getSub())
                .orElseThrow(() -> new ResourceNotFoundException("There is no device " + deviceId + " for user " + user.getSub()));
        updatedDevice.setUserData(updateDeviceRequest.getUserData() == null ? updatedDevice.getUserData() : updateDeviceRequest.getUserData());
        updatedDevice.setOperationSystem(updateDeviceRequest.getOperationSystem() == null
                ? updatedDevice.getOperationSystem()
                : OperationSystem.valueOf(updateDeviceRequest.getOperationSystem().toUpperCase()));
        return DeviceDto.fromEntity(devicesRepository.save(updatedDevice));
    }

    private List<DeviceDto> readUserDevices(IUser user) {
        return devicesRepository.findAllByUserId(user.getSub())
                .stream()
                .map(DeviceDto::fromEntity)
                .collect(Collectors.toList());
    }

    private List<DeviceDto> readAll() {
        return Collections.emptyList();
    }

    private String generateId(NewDeviceDto newDeviceDto) {
        return newDeviceDto.getOperationSystem() + "-" + newDeviceDto.getUserId() + Math.random() * 13317;
    }
}
