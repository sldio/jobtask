package com.task.devices.repository;

import com.task.devices.services.data.DeviceDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DevicesRepository extends CrudRepository<DeviceEntity, Integer> {
    Optional<DeviceEntity> findByDeviceId(String deviceId);
    Optional<DeviceEntity> findByDeviceIdAndUserId(String deviceId, String userId);
    List<DeviceEntity> findAllByUserId(String userId);
}
