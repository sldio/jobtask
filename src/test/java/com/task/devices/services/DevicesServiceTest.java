package com.task.devices.services;

import com.task.devices.Templates;
import com.task.devices.controller.data.UpdateDeviceRequest;
import com.task.devices.domain.OperationSystem;
import com.task.devices.domain.api.UserRole;
import com.task.devices.repository.DevicesRepository;
import com.task.devices.services.data.DeviceDto;
import com.task.devices.services.data.exceptions.DeviceAccessException;
import com.task.devices.services.data.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.task.devices.Templates.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DevicesServiceTest {
    private DevicesService devicesService;
    private DevicesRepository devicesRepository;

    @BeforeEach
    void init() {
        devicesRepository = Templates.devicesRepository();
        devicesService = new DevicesService(devicesRepository);
    }

    @Test
    void getDevice_exists_success() {
        DeviceDto mockDevice = deviceDto();

        devicesRepository.save(mockDevice.toNewEntity());

        DeviceDto device = devicesService.getDevice(user(UserRole.CLIENT), DEVICE_ID);

        Assertions.assertEquals(DEVICE_ID, device.getDeviceId());
        Assertions.assertEquals(USER_ID, device.getUserId());
        Assertions.assertEquals(OperationSystem.IOS, device.getOperationSystem());
        Assertions.assertEquals("some data", device.getUserData());
    }

    @Test
    void getDevice_noDevice_throwResourceNotFoundException() {
        ResourceNotFoundException exception =
                assertThrows(ResourceNotFoundException.class, () -> devicesService.getDevice(user(UserRole.CLIENT), DEVICE_ID));
        Assertions.assertEquals("Unknown device id IOS-12112", exception.getMessage());
    }

    @Test
    void addDevice_success() {
        DeviceDto result = devicesService.addDevice(newDeviceDto());

        assertEquals(USER_ID, result.getUserId());
    }

    @Test
    void updateDevice_unknownUser_throwResourceNotFoundException() {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> devicesService.updateDevice(user(UserRole.CLIENT), DEVICE_ID, new UpdateDeviceRequest()));

        assertEquals("There is no device " + DEVICE_ID + " for user " + USER_ID, exception.getMessage());
    }

    @Test
    void updateDevice_unknownDevice_throwResourceNotFoundException() {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> devicesService.updateDevice(user(UserRole.CLIENT), DEVICE_ID, new UpdateDeviceRequest()));

        assertEquals("There is no device " + DEVICE_ID + " for user " + USER_ID, exception.getMessage());
    }


    @Test
    void updateDevice_success() {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setUserData("updated user data");
        DeviceDto mockDevice = deviceDto();

        devicesRepository.save(mockDevice.toNewEntity());

        DeviceDto result = devicesService.updateDevice(user(UserRole.CLIENT), DEVICE_ID, updateDeviceRequest);

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

        List<DeviceDto> device = devicesService.getDevices(user(UserRole.CLIENT));

       assertEquals(2, device.size());
    }

    @Test
    void getUserDevice_notValidUserId_throwException() {
        DeviceDto mockDevice = new DeviceDto("wrong_user", DEVICE_ID, OperationSystem.IOS, "some_data");

        devicesRepository.save(mockDevice.toNewEntity());

        DeviceAccessException ex =
                assertThrows(DeviceAccessException.class, () -> devicesService.getDevice(user(UserRole.CLIENT), DEVICE_ID));
        assertEquals("User " + USER_ID + " doesn't have access to device " + DEVICE_ID, ex.getMessage());
    }

    @Test
    void getUserDevices_noDevices_emptyList() {
        List<DeviceDto> device = devicesService.getDevices(user(UserRole.CLIENT));

        assertEquals(0, device.size());
    }
}