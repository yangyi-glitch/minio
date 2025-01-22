package com.minio.console.service.minio;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class MinioServiceImpl implements MinioService {

    @Resource
    private MinioClient minioClient;

    private static String BUCKTERTNAME = "localhost-file";

    @Override
    public void bucketExists() {
        try {
            //判断bucker存在与否
            boolean b = minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKTERTNAME).build());
            System.out.println("桶是否存在:" + b);
            if (!b) {
                //创建bucker桶
                //minioClient.makeBucket(MakeBucketArgs.builder().bucket(name).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void listObjects() {
        try {
            //获取minio桶里所有文件的属性
            Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(BUCKTERTNAME).build());
            Iterator<Result<Item>> iterator = results.iterator();
            List<Object> list = new ArrayList<>();
            while (iterator.hasNext()) {
                Item item = iterator.next().get();
                list.add(item.objectName());
                list.add(item.size());
            }
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uploadObject() {
        try {
            //本地上传文件
/*        minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket(name)
                        .object("1111.pdf")
                        .filename("C:\\Users\\19659\\Desktop\\JAVA开发工程师-杨溢 .pdf")
                        .build());*/
            System.out.println("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //下载到本地
    @Override
    public void downloadObject() {
        try {
            //minio下载文件
            minioClient.downloadObject(DownloadObjectArgs.builder()
                    .bucket(BUCKTERTNAME)
                    .object("1111.pdf")
                    .filename("C:\\Users\\19659\\Desktop\\JAVA开发工程师1-杨溢 .pdf")
                    .build());
            System.out.println("下载成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeObject() {
        try {
            //minio下载文件
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(BUCKTERTNAME)
                    .object("1111.pdf")
                    .build());
            System.out.println("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getObject(HttpServletResponse response) {
        try {
            InputStream in;
            StatObjectResponse statObjectResponse = minioClient.statObject(StatObjectArgs.builder()
                    .bucket(BUCKTERTNAME)
                    .object("1111.pdf")
                    .build());
            response.setContentType(statObjectResponse.contentType());
            response.setHeader("Content-DisPosition", "attachment;filename=" + URLEncoder.encode("1111.pdf", "UTF-8"));
            //minio下载文件
            in = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(BUCKTERTNAME)
                    .object("1111.pdf")
                    .build());
            IOUtils.copy(in, response.getOutputStream());
            log.info("浏览器下载成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //预览
    @Override
    public boolean perviewObject(HttpServletResponse response, String url) {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-DisPosition", "inline");

            InputStream in = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(BUCKTERTNAME)
                    .object(url)
                    .build());
            OutputStream out = response.getOutputStream();
            int len = 0;
            byte[] bs = new byte[1024];
            while ((len = in.read(bs)) > 0) {
                out.write(bs, 0, len);
            }
            in.close();
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    //上传文件
    @Override
    public boolean handleFileUpload(String url, MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(BUCKTERTNAME)
                    .object(url)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public String getpresignedUrl(String url) {
        String presignedObjectUrl = "";
        try {
            presignedObjectUrl = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(BUCKTERTNAME)
                            .object(url)
                            .method(Method.GET)
                            .expiry(1, TimeUnit.HOURS)
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return presignedObjectUrl;
    }
}
