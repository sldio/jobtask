package com.task.devices.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.devices.domain.User;
import com.task.devices.domain.api.IUser;
import com.task.devices.services.data.exceptions.AppException;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class TokenService {
    private static final Base64.Decoder DECODER = Base64.getDecoder();
    private final ObjectMapper mapper;

    public TokenService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Parses JWT toke to IUser domain instance
     *
     * @param jwt token
     * @return user instance
     */
    public IUser parseJwtToken(String jwt) {
        String[] tokenSections = jwt.split("\\.");

        String payload = new String(DECODER.decode(tokenSections[1]));
        return parseUser(payload);
    }

    /**
     * Converts JWT payload data to {@link IUser} instance.
     * Throws AppException in case of parsing errors
     *
     * @param data token payload data
     * @return IUser instance
     */
    private IUser parseUser(String data) {
        try {
            User user = mapper.readValue(data, User.class);
            user.validate();
            return user;
        } catch (JsonProcessingException e) {
            throw new AppException("Invalid JWT token data");
        }
    }
}
