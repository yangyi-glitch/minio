package com.minio.console.service.minio;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface MinioService {
    void bucketExists();
    void listObjects();
    void uploadObject();
    void downloadObject();
    void removeObject();
    void getObject(HttpServletResponse response);
    boolean perviewObject(HttpServletResponse response,String url);
    boolean handleFileUpload(String url,MultipartFile file);
    String getpresignedUrl(String url);
}
