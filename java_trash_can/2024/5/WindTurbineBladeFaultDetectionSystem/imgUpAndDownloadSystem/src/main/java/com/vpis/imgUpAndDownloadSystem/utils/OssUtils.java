package com.vpis.imgUpAndDownloadSystem.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.vpis.imgUpAndDownloadSystem.pojo.img;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.aliyun.oss.model.*;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
public class OssUtils {

    private static final String endpoint = "https://oss-cn-shanghai.aliyuncs.com";

    private static final String bucketName = "web-tlias-hyperionml";

    private static final String region = "cn-shanghai";

    private static final OSS ossClient;

    static {
        // 创建OSSClient实例。
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        try {
            ossClient = OSSClientBuilder.create()
                    .endpoint(endpoint)
                    .credentialsProvider(CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider())
                    .clientConfiguration(clientBuilderConfiguration)
                    .region(region)
                    .build();
        } catch (com.aliyuncs.exceptions.ClientException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String userLocalName = "gdupt";


    public static void massUpload(ArrayList<img> urls) {

        for (img file : urls) {
            String objectName = userLocalName + "/";

            objectName = objectName + file.getName();
            String objectLocalName = "/root/TransitionalFiles/temp/" + file.getName();

            try {
                ossClient.putObject(new PutObjectRequest(bucketName, objectName, new File(objectLocalName)));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static void massDownload(String filePath,String... fileNameList) {
        String fileName;
        String filePathtemporary;

        try {
            for (String s : fileNameList) {
                fileName = s;
                filePathtemporary = filePath + "/" + fileName;

//             下载Object到本地文件，并保存到指定的本地路径中。如果指定的本地文件存在会覆盖，不存在则新建。
//             如果未指定本地路径，则下载后的文件默认保存到示例程序所属项目对应本地路径中。
                ossClient.getObject(new GetObjectRequest(bucketName, fileName), new File(filePathtemporary));
            }

        } catch (OSSException oe) {
            logErr(oe);
        } catch (ClientException ce) {
            log.error("Error Message:" + ce.getMessage());
        }

    }

    public static ArrayList<String> massGetImgDownloadUrl(ArrayList<img> imgs) {

        ArrayList<String> re = new ArrayList<>();

        final URL[] signedUrl = {null};
        try {
            imgs.forEach(img -> {
                // 填写Object完整路径，例如exampleobject.txt。Object完整路径中不能包含Bucket名称。
                String objectName = userLocalName + "/";
                objectName = objectName + img.getName();

                // 指定生成的签名URL过期时间，单位为毫秒。本示例以设置过期时间为1天。
                Date expiration = new Date(new Date().getTime() + 3600 * 1000L * 24);

                // 生成签名URL。
                GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.GET);
                // 设置过期时间。
                request.setExpiration(expiration);

                // 通过HTTP GET请求生成签名URL。
                signedUrl[0] = ossClient.generatePresignedUrl(request);
                re.add(signedUrl[0].toString());
            });

        } catch (OSSException oe) {
            oeTitle(oe);
            logErr(oe);
        } catch (ClientException ce) {
            logErrErr(ce);
        }
        return re;
    }

    public static int delete(String imgName){

        imgName = userLocalName + "/" + imgName;
        try {
            // 删除文件或目录。如果要删除目录，目录必须为空。
            ossClient.deleteObject(bucketName, imgName);
        } catch (OSSException oe) {
            oeTitle(oe);
            logErr(oe);
            return 0;
        } catch (ClientException ce) {
            logErrErr(ce);
            return 0;
        }

        return 1;
    }

    public static int massDelete(String[] imgNames){
        try {
            // 删除文件。
            // 填写需要删除的多个文件完整路径。文件完整路径中不能包含Bucket名称。
            List<String> keys = Arrays.asList(imgNames);

            DeleteObjectsResult deleteObjectsResult = ossClient
                    .deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys).withEncodingType("url"));
            List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
            for(String obj : deletedObjects) {
                String deleteObj =  URLDecoder.decode(obj, StandardCharsets.UTF_8);
                log.info(deleteObj);
            }
        } catch (OSSException oe) {
            oeTitle(oe);
            logErr(oe);
        } catch (ClientException ce) {
            logErrErr(ce);
        }
        return 1;
    }


    private static void logErr(OSSException oe){
        log.error("Error Message:" + oe.getErrorMessage());
        log.error("Error Code:{}", oe.getErrorCode());
        log.error("Request ID:{}", oe.getRequestId());
        log.error("Host ID:{}", oe.getHostId());
    }

    private static void logErrErr(ClientException ce){
        log.error("Caught an ClientException, which means the client encountered "
                + "a serious internal problem while trying to communicate with OSS, "
                + "such as not being able to access the network.");
        log.error("Error Message:" + ce.getMessage());
    }

    private static void oeTitle(OSSException oe){
        log.error("Caught an OSSException, which means your request made it to OSS, "
                + "but was rejected with an error response for some reason.");
    }

}
