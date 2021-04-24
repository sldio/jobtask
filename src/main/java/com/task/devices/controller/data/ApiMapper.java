package com.task.devices.controller.data;

import com.task.devices.domain.api.IUser;
import com.task.devices.services.data.NewDeviceDto;

public class ApiMapper {

    public static NewDeviceDto requestToNewDevice(IUser user, AddDeviceRequest request){
        return new NewDeviceDto(user.getSub(), request);
    }
}
