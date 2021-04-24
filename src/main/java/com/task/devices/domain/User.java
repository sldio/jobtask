package com.task.devices.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.task.devices.domain.api.IUser;
import com.task.devices.domain.api.UserRole;
import com.task.devices.services.IValidatable;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class User implements IUser, IValidatable {
    @NotNull
    private String sub;
    @NotNull
    private UserRole role;
    @NotNull
    private String iss;
    @NotNull
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime exp;
    @NotNull
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime iat;

    public User() {
    }

    public User(String sub, UserRole role, String iss, LocalDateTime exp, LocalDateTime iat) {
        this.sub = sub;
        this.role = role;
        this.iss = iss;
        this.exp = exp;
        this.iat = iat;
        this.validate();
    }
}
