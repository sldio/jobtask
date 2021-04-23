package com.task.devices.services;

import com.task.devices.Templates;
import com.task.devices.controller.data.UpdateDeviceRequest;
import com.task.devices.domain.OperationSystem;
import com.task.devices.repository.DevicesRepository;
import com.task.devices.services.data.DeviceDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.task.devices.Templates.*;
import static org.junit.jupiter.api.Assertions.*;

class DevicesServiceTest {
    private DevicesService devicesService;
    private DevicesRepository devicesRepository;

    @BeforeEach
    void init() {
        devicesRepository = Templates.devicesRepository();
        devicesService = new DevicesService(devicesRepository);
    }

    @Test
    void getDeviceById_exists_success() {
        DeviceDto mockDevice = deviceDto();

        devicesRepository.save(mockDevice.toNewEntity());

        DeviceDto device = devicesService.getDeviceById(DEVICE_ID);

        Assertions.assertEquals(DEVICE_ID, device.getDeviceId());
        Assertions.assertEquals(USER_ID, device.getUserId());
        Assertions.assertEquals(OperationSystem.IOS, device.getOperationSystem());
        Assertions.assertEquals("some data", device.getUserData());
    }

    @Test
    void getDeviceById_noDevice_throwResourceNotFoundException() {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> devicesService.getDeviceById(DEVICE_ID));
        Assertions.assertEquals("Unknown device id IOS-12112", exception.getMessage());
    }

    @Test
    void addDeviceById_success() {
        DeviceDto result = devicesService.addDevice(deviceDto());

        assertEquals(USER_ID, result.getUserId());
    }

    @Test
    void updateDevice_unknownUser_throwResourceNotFoundException() {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> devicesService.updateDevice("someId", DEVICE_ID, new UpdateDeviceRequest()));

        assertEquals("There is no device IOS-12112 for user someId", exception.getMessage());
    }

    @Test
    void updateDevice_unknownDevice_throwResourceNotFoundException() {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> devicesService.updateDevice(USER_ID, "someId", new UpdateDeviceRequest()));

        assertEquals("There is no device someId for user ID-1111", exception.getMessage());
    }


    @Test
    void updateDevice_success() {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setUserData("updated user data");
        DeviceDto mockDevice = deviceDto();

        devicesRepository.save(mockDevice.toNewEntity());

        DeviceDto result = devicesService.updateDevice(USER_ID, DEVICE_ID, updateDeviceRequest);

        assertEquals(USER_ID, result.getUserId());
        assertEquals(DEVICE_ID, result.getDeviceId());
        assertEquals("updated user data", result.getUserData());
    }

    @Test
    void getUserDevices_success() {
        DeviceDto mockDevice1 = deviceDto("device1");
        DeviceDto mockDevice2 = deviceDto("device2");

        devicesRepository.save(mockDevice1.toNewEntity());
        devicesRepository.save(mockDevice2.toNewEntity());

        List<DeviceDto> device = devicesService.getDevices(USER_ID);

       assertEquals(2, device.size());
    }

    @Test
    void getUserDevices_noDevices_emptyList() {
        List<DeviceDto> device = devicesService.getDevices(USER_ID);

        assertEquals(0, device.size());
    }
}