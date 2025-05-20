package com.vata.profile.infrastructure;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import java.io.InputStream;
import org.springframework.stereotype.Component;

@Component
public class MinioService {

    private final MinioClient minioClient;

    private static final String BUCKET_NAME = "vata";
    private static final String PUBLIC_URL = "http://localhost:9000";

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public String uploadFile(String path, InputStream inputStream, long contentLength, String contentType) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(BUCKET_NAME)
                            .object(path)
                            .stream(inputStream, contentLength, -1)
                            .contentType(contentType)
                            .build()
            );
            return getPublicUrl(path);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file to MinIO", e);
        }
    }

    public void deleteObject(String path) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(BUCKET_NAME)
                            .object(path)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete object from MinIO", e);
        }
    }

    private String getPublicUrl(String path) {
        return String.format("%s/%s/%s", PUBLIC_URL, BUCKET_NAME, path);
    }
}
