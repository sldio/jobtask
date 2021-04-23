package com.task.devices.domain;

import com.task.devices.services.AppException;
import lombok.Getter;

import javax.persistence.AttributeConverter;
import java.util.EnumSet;

public enum OperationSystem {
    ANDROID(1),
    IOS(2);

    @Getter
    private final int systemId;

    OperationSystem(int id) {
        systemId = id;
    }

    private static OperationSystem ofSystemId(Integer id) {
        return EnumSet.allOf(OperationSystem.class)
                .stream()
                .filter(operationSystem -> operationSystem.getSystemId() == id)
                .findFirst()
                .orElseThrow(() -> new AppException("Unknown operation system id " + id));
    }

    public static class EntityConverter implements AttributeConverter<OperationSystem, Integer> {
        @Override
        public Integer convertToDatabaseColumn(OperationSystem operationSystem) {
            return operationSystem.getSystemId();
        }

        @Override
        public OperationSystem convertToEntityAttribute(Integer id) {
            return OperationSystem.ofSystemId(id);
        }
    }
}
