package com.paragon.sensonic.auth;

enum EndPoints {

    PASSWORD_LESS_LOGIN("passwordless-login"),
    VERIFY_CHALLANGE("verify-challange");

    private final String value;

    EndPoints(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
