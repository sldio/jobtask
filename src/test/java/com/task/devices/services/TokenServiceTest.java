package com.task.devices.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.devices.domain.api.IUser;
import com.task.devices.domain.api.UserRole;
import com.task.devices.services.data.exceptions.AppException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TokenServiceTest {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    private final TokenService tokenService = new TokenService(new ObjectMapper());

    @Test
    void parseToken_success(){
        IUser tokenUser = tokenService.parseJwtToken(JWT);

        assertEquals("ff970cd1-e9d1-468a-ae71-bdb5a115c5aa", tokenUser.getSub());
        assertEquals(UserRole.CLIENT, tokenUser.getRole());
        assertEquals("bootcamp", tokenUser.getIss());
        assertEquals(LocalDateTime.parse("2026-04-15T00:00:00.001Z", formatter), tokenUser.getExp());
        assertEquals(LocalDateTime.parse("2021-04-01T00:00:00.001Z", formatter), tokenUser.getIat());
    }

    @Test
    void parseToken_invalid_throwException(){
        assertThrows(AppException.class, () -> tokenService.parseJwtToken(INVALID_JWT));
    }

    private static final String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmZjk3MGNkMS1lOWQxLTQ2OGEtYWU3MS1iZGI1YTExNWM1" +
            "YWEiLCJyb2xlIjoiQ0xJRU5UIiwiaXNzIjoiYm9vdGNhbXAiLCJleHAiOiIyMDI2LTA0LTE1VDAwOjAwOjAwLjAwMVoiLCJpYXQiOiIyMD" +
            "IxLTA0LTAxVDAwOjAwOjAwLjAwMVoifQ.BzDeCsEdaOuMAQQYnLxUQYsly9bszt7HzW_nxMmz_bo";
    private static final String INVALID_JWT = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiMjAyMS0wNC0yNFQwNzoyNDowOS42MTRaIiwia" +
            "XNzIjoiMjAyMS0wNC0yNFQwNzoyNDowOS42MTVaIiwic3ViTmV3Ijoic3ViIiwiZXhwIjoiSmF2YUluVXNlIiwiaWF0IjoiQWRtaW4ifQ." +
            "vDGgz7Mb2RLdv03Lqm135nJySXDg4AIBWMH45IFmp7M";
}