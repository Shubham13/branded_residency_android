package com.digivalet.brandresidential.helpers;

public enum GuestType {
    ONE_TIME_GUEST("One-Time Guest"),
    FREQUENT_GUEST("Frequent Guest"),
    SHORT_STAYING_GUEST("Short Staying");

    private final String value;

    GuestType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
