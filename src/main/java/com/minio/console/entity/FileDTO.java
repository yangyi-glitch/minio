package com.minio.console.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("file")
public class FileDTO {
    /**
     * 文件名称
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件url
     */
    private String fileUrl;
    /**
     * 文件url
     */
    private String fileType;
}
