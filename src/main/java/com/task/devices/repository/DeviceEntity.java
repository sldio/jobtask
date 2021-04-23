package com.task.devices.repository;

import com.task.devices.domain.OperationSystem;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Entity
@Table(name = "devices")
public class DeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime updated;

    @Getter
    @Column(name = "operation_system", columnDefinition = "TINYINT")
    @Convert(converter = OperationSystem.EntityConverter.class)
    private OperationSystem operationSystem;

    @Getter
    @Column(name = "user_id")
    private String userId;

    @Getter
    @Column(name = "device_id", unique = true)
    private String deviceId;

    @Getter
    @Column(name = "user_data")
    private String userData;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceEntity that = (DeviceEntity) o;
        return operationSystem == that.operationSystem && userId.equals(that.userId) && deviceId.equals(that.deviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationSystem, userId, deviceId);
    }
}
