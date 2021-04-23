package com.task.devices.controller;

import com.task.devices.controller.data.AddDeviceRequest;
import com.task.devices.controller.data.UpdateDeviceRequest;
import com.task.devices.services.DevicesService;
import com.task.devices.services.data.DeviceDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class DevicesController {
    private final DevicesService devicesService;

    @GetMapping("devices/{deviceId}")
    public ResponseEntity<DeviceDto> getDeviceById(String deviceId){
        DeviceDto result = devicesService.getDeviceById(deviceId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("devices/{userId}")
    public ResponseEntity<DeviceDto> addDevice(@PathVariable String userId, @RequestBody AddDeviceRequest newDevice){
        DeviceDto newDeviceDto = new DeviceDto(userId, newDevice);
        DeviceDto result = devicesService.addDevice(newDeviceDto);
        return ResponseEntity.ok(result);
    }


    @GetMapping("devices/{userId}")
    public ResponseEntity<List<DeviceDto>> getDevices(@PathVariable String userId){
        List<DeviceDto> result = devicesService.getDevices(userId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("devices/{userId}/{deviceId}")
    public ResponseEntity<DeviceDto> updateDeviceById(@PathVariable String userId, @PathVariable String deviceId, @RequestBody UpdateDeviceRequest updates){
        DeviceDto result = devicesService.updateDevice(userId, deviceId, updates);
        return ResponseEntity.ok(result);
    }
}
