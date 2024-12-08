package com.hyperionml.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.hyperionml.pojo.img;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.aliyun.oss.model.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Component
public class OssUtils {

    // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
    String endpoint = "https://oss-cn-shanghai.aliyuncs.com";
    // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
    EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
    // 填写Bucket名称，例如examplebucket。
    String bucketName = "web-tlias-hyperionml";
    // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。

    @Value("${UserLocalName}")
    private String userLocalName;


    public OssUtils() throws com.aliyuncs.exceptions.ClientException {
    }

    public String getNowDate(){
        return LocalDate.now().toString();
    }

    public void upload(String url) {

        String objectName = userLocalName + "/" + getNowDate() + "/";

        int index = url.lastIndexOf('\\');
        if (index > 0) {
            objectName = objectName + url.substring(index + 1);
        }else {
            objectName = objectName + url;
        }

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);

        try {
            ossClient.putObject(new PutObjectRequest(bucketName, objectName, new File(url)));
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    public void massUpload(ArrayList<img> urls) {
        //Path startPath = Paths.get(url);

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);

        for (img file : urls) {
            String objectName = userLocalName + "/" + getNowDate() + "/";

            objectName = objectName + file.getName();
            String objectLocalName = "D:\\temp\\" + file.getName();

            try {
                ossClient.putObject(new PutObjectRequest(bucketName, objectName, new File(objectLocalName)));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if (ossClient != null) {
            ossClient.shutdown();
        }
    }

    public void massDownload(String filePath,String... fileNameList) {
        String fileName;
        String filePathtemporary;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);
        try {
            for (String s : fileNameList) {
                fileName = s;
                filePathtemporary = filePath + "/" + fileName;

//             下载Object到本地文件，并保存到指定的本地路径中。如果指定的本地文件存在会覆盖，不存在则新建。
//             如果未指定本地路径，则下载后的文件默认保存到示例程序所属项目对应本地路径中。
                ossClient.getObject(new GetObjectRequest(bucketName, fileName), new File(filePathtemporary));
            }

        } catch (OSSException oe) {
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Error Message:" + ce.getMessage());
        }

        if (ossClient != null) {
            ossClient.shutdown();
        }
    }

    public ArrayList<String> massGetImgDownloadUrl(ArrayList<img> imgs) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);

        ArrayList<String> re = new ArrayList<>();

        final URL[] signedUrl = {null};
        try {
            imgs.forEach(img -> {
                // 填写Object完整路径，例如exampleobject.txt。Object完整路径中不能包含Bucket名称。
                String objectName = userLocalName + "/" + getNowDate() + "/";
                objectName = objectName + img.getName();

                // 指定生成的签名URL过期时间，单位为毫秒。本示例以设置过期时间为1天。
                Date expiration = new Date(new Date().getTime() + 3600 * 1000L * 24);

                // 生成签名URL。
                GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.GET);
                // 设置过期时间。
                request.setExpiration(expiration);

                // 通过HTTP GET请求生成签名URL。
                signedUrl[0] = ossClient.generatePresignedUrl(request);
                // 打印签名URL。
                //System.out.println("signed url for getObject: " + signedUrl[0]);
                re.add(signedUrl[0].toString());
            });

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        }
        return re;
    }

    private void testMass(String url){

        Path startPath = Paths.get(url); // 替换为你的文件夹路径

        try {
            Files.walkFileTree(startPath, new SimpleFileVisitor<>() {
/*                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    System.out.println("目录：" + dir.toString());
                    return FileVisitResult.CONTINUE;
                }*/

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    System.out.println("文件：" + file.toString());
                    return FileVisitResult.CONTINUE;
                }

              /*  @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    // 在遍历完一个目录的所有子目录和文件后调用
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    System.err.println("无法访问文件：" + file.toString());
                    exc.printStackTrace();
                    return FileVisitResult.CONTINUE;
                }*/
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void testDownload(){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);

        try {
            // 构造ListObjectsRequest请求。
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
            // 设置prefix参数来获取fun目录下的所有文件。
            listObjectsRequest.setPrefix("gdupt/2024-05-08");

            // 递归列举fun目录下的所有文件。
            ObjectListing listing = ossClient.listObjects(listObjectsRequest);

            // 遍历所有文件。
            System.out.println("Objects:");
            for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
                System.out.println(objectSummary.getKey());
            }

            // 遍历所有commonPrefix。
            System.out.println("\nCommonPrefixes:");
            for (String commonPrefix : listing.getCommonPrefixes()) {
                System.out.println(commonPrefix);
            }
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

}
