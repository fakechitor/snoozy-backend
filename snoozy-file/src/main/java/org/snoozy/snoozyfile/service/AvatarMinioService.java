package org.snoozy.snoozyfile.service;

import io.minio.*;

import lombok.RequiredArgsConstructor;
import org.snoozy.snoozyfile.config.MinioProperties;
import org.snoozy.snoozyfile.exception.FileStorageException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AvatarMinioService {

        private static final List<String> ALLOWED_CONTENT_TYPES = List.of(
                MediaType.IMAGE_JPEG_VALUE,
                MediaType.IMAGE_PNG_VALUE,
                MediaType.IMAGE_GIF_VALUE,
                "image/webp"
        );

        private final MinioClient minioClient;

        private final MinioProperties minioProperties;

        public String uploadUserAvatar(Long userId, MultipartFile file) {
            validateFile(file);
            ensureBucketExists();

            String extension = resolveExtension(file.getContentType());
            String objectKey = "users/avatars/" + userId + "/" + UUID.randomUUID() + extension;

            try (InputStream inputStream = file.getInputStream()) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(minioProperties.bucket())
                                .object(objectKey)
                                .stream(inputStream, file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build()
                );

                return objectKey;
            } catch (Exception e) {
                throw new FileStorageException("Failed to upload user avatar", e);
            }
        }

        public String uploadGroupAvatar(Long groupId, MultipartFile file) {
            validateFile(file);
            ensureBucketExists();

            String extension = resolveExtension(file.getContentType());
            String objectKey = "groups/avatars/" + groupId + "/" + UUID.randomUUID() + extension;

            try (InputStream inputStream = file.getInputStream()) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(minioProperties.bucket())
                                .object(objectKey)
                                .stream(inputStream, file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build()
                );

                return objectKey;
            } catch (Exception e) {
                throw new FileStorageException("Failed to upload group avatar", e);
            }
        }

        public InputStream getAvatar(String objectKey) {
            try {
                return minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(minioProperties.bucket())
                                .object(objectKey)
                                .build()
                );
            } catch (Exception e) {
                throw new FileStorageException("Failed to get avatar", e);
            }
        }

        public void deleteAvatar(String objectKey) {
            try {
                minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(minioProperties.bucket())
                                .object(objectKey)
                                .build()
                );
            } catch (Exception e) {
                throw new FileStorageException("Failed to delete avatar", e);
            }
        }

        private void ensureBucketExists() {
            try {
                boolean exists = minioClient.bucketExists(
                        BucketExistsArgs.builder()
                                .bucket(minioProperties.bucket())
                                .build()
                );

                if (!exists) {
                    minioClient.makeBucket(
                            MakeBucketArgs.builder()
                                    .bucket(minioProperties.bucket())
                                    .build()
                    );
                }
            } catch (Exception e) {
                throw new FileStorageException("Failed to initialize bucket", e);
            }
        }

        private void validateFile(MultipartFile file) {
            if (file == null || file.isEmpty()) {
                throw new FileStorageException("File is empty");
            }

            String contentType = file.getContentType();
            if (!ALLOWED_CONTENT_TYPES.contains(contentType)) {
                throw new FileStorageException("Unsupported file type: " + contentType);
            }
        }

        private String resolveExtension(String contentType) {
            return switch (Objects.requireNonNullElse(contentType, "")) {
                case MediaType.IMAGE_JPEG_VALUE -> ".jpg";
                case MediaType.IMAGE_PNG_VALUE -> ".png";
                case MediaType.IMAGE_GIF_VALUE -> ".gif";
                case "image/webp" -> ".webp";
                default -> "";
            };
        }
    }