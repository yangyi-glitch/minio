package com.minio.console.service.file;

import cn.hutool.core.text.StrBuilder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minio.console.entity.FileDTO;
import com.minio.console.mapper.FileMapper;
import com.minio.console.service.minio.MinioService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.util.Objects;

import static com.minio.console.util.UniqueIdentifierUtils.getUrl;

@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FileDTO> implements FileService {
    @Resource
    private MinioService minioService;
    @Resource
    private FileMapper fileMapper;

    @Override
    public boolean upload(MultipartFile file) {
        StrBuilder url = new StrBuilder();
        url.append(getUrl());
        url.append(file.getOriginalFilename());
        boolean b = minioService.handleFileUpload(url.toString(), file);
        if (b){
            FileDTO fileDTO = new FileDTO();
            fileDTO.setFileName(file.getOriginalFilename());
            fileDTO.setFileUrl(url.toString());
            String[] split = file.getOriginalFilename().split("\\.");
            fileDTO.setFileType(split[split.length-1]);
            fileMapper.insert(fileDTO);
        }
        return true;
    }

    @Override
    public boolean preview(HttpServletResponse response,Long id) {
        FileDTO fileDTO = fileMapper.selectById(id);
        if (Objects.isNull(fileDTO)){
            return false;
        }
        return minioService.perviewObject(response, fileDTO.getFileUrl());
    }

    @Override
    public String previewUrl(Long id) {
        FileDTO fileDTO = fileMapper.selectById(id);
        if (Objects.isNull(fileDTO)){
            return "";
        }
        return minioService.getpresignedUrl(fileDTO.getFileUrl());
    }
}
