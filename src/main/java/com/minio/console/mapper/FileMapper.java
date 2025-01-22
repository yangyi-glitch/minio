package com.minio.console.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minio.console.entity.FileDTO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface FileMapper extends BaseMapper<FileDTO> {
}
