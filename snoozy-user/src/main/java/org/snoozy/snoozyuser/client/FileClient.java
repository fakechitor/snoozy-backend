package org.snoozy.snoozyuser.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class FileClient {

    private final RestClient restClient;

    public byte[] getAvatar(String uri) {
        return restClient.get()
                .uri(uri)
                .retrieve()
                .body(byte[].class);
    }
}
