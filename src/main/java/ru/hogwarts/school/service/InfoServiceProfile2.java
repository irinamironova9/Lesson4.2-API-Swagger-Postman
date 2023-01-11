package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("profile2")
public class InfoServiceProfile2 implements InfoService {

    @Value("${server.port}")
    Integer port;

    @Override
    public Integer getPort() {
        return port;
    }
}