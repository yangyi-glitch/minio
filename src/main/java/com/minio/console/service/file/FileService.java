package com.minio.console.service.file;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minio.console.entity.FileDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface FileService extends IService<FileDTO> {
    boolean upload(MultipartFile file);

    boolean preview(HttpServletResponse response,Long id);

    String previewUrl(Long id);
}
