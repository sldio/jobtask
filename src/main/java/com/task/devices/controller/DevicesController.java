package com.task.devices.controller;

import com.task.devices.controller.data.AddDeviceRequest;
import com.task.devices.controller.data.ApiMapper;
import com.task.devices.controller.data.UpdateDeviceRequest;
import com.task.devices.domain.api.IUser;
import com.task.devices.services.DevicesService;
import com.task.devices.services.TokenService;
import com.task.devices.services.data.DeviceDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class DevicesController {
    private final DevicesService devicesService;
    private final TokenService tokenService;

    @GetMapping("devices")
    public ResponseEntity<List<DeviceDto>> getDevices(@RequestHeader(value="Access Token") String token){
        IUser user = tokenService.parseJwtToken(token);
        List<DeviceDto> result = devicesService.getDevices(user);
        return ResponseEntity.ok(result);
    }

    @GetMapping("devices/{deviceId}")
    public ResponseEntity<DeviceDto> getDevice(@RequestHeader(value="Access Token") String token,
                                               @PathVariable String deviceId){
        IUser user = tokenService.parseJwtToken(token);
        DeviceDto result = devicesService.getDevice(user, deviceId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("devices")
    public ResponseEntity<DeviceDto> addDevice(@RequestHeader(value="Access Token") String token,
                                               @RequestBody AddDeviceRequest newDevice){
        IUser user = tokenService.parseJwtToken(token);
        DeviceDto result = devicesService.addDevice(ApiMapper.requestToNewDevice(user, newDevice));
        return ResponseEntity.ok(result);
    }

    @PutMapping("devices/{deviceId}")
    public ResponseEntity<DeviceDto> updateDevice(@RequestHeader(value="Access Token") String token,
                                                  @PathVariable String userId,
                                                  @PathVariable String deviceId,
                                                  @RequestBody UpdateDeviceRequest updates){
        IUser user = tokenService.parseJwtToken(token);
        DeviceDto result = devicesService.updateDevice(user, deviceId, updates);
        return ResponseEntity.ok(result);
    }
}
