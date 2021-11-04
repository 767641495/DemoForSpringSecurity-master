package com.example.demo.exception;

import lombok.extern.slf4j.Slf4j;

import javax.security.sasl.AuthenticationException;

@Slf4j
public class CaptchaException extends AuthenticationException {
    public CaptchaException(String detail) {
        log.info(detail);
    }
}
