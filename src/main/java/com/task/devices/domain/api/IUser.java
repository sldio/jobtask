package com.task.devices.domain.api;

import java.time.LocalDateTime;

public interface IUser {
    String getSub();
    UserRole getRole();
    String getIss();
    LocalDateTime getExp();
    LocalDateTime getIat();
}
