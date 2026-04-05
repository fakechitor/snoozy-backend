package org.snoozy.snoozygroup.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FileClient {

    @Value("${util.base-url}")
    private String baseUrl;

    private final RestClient restClient;


    public String uploadGroupAvatar(Long groupId, MultipartFile file) {
        try {
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

            body.add("file", new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            });

            Map<String, String> response = restClient.post()
                    .uri(baseUrl + "/api/v1/files/groups/{groupId}/avatar", groupId)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(body)
                    .retrieve()
                    .body(Map.class);

            return response != null ? baseUrl + response.get("avatarUrl") : null;

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload avatar", e);
        }
    }
}