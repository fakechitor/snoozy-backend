package org.snoozy.snoozyauth.util;

import lombok.Getter;

@Getter
public enum AuthType {
    BASIC("basic"),
    GOOGLE("google");

    private final String value;

    AuthType(String value) {
        this.value = value;
    }
}
