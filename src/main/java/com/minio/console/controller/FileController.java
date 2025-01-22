package com.minio.console.controller;

import com.minio.console.service.file.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/file")
@RestController
public class FileController {
    @Resource
    private FileService fileService;
    @PostMapping("/upload")
    public boolean upload(@RequestParam("file") MultipartFile file) {
        return fileService.upload(file);
    }

    @GetMapping("/preview")
    public boolean preview(HttpServletResponse response,@RequestParam("id") Long id) {
        return fileService.preview(response,id);
    }

    @GetMapping("/previewUrl")
    public String previewUrl(@RequestParam("id") Long id) {
        return fileService.previewUrl(id);
    }
}
