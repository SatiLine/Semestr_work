package org.client;

import lombok.Data;

@Data
public final class Message {
    private final User user;
    private final String text;
}
