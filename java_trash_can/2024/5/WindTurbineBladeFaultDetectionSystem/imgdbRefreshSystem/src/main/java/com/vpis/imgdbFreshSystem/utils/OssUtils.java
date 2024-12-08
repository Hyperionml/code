package com.vpis.imgdbFreshSystem.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.vpis.imgdbFreshSystem.pojo.img;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

@Component
public class OssUtils {

    // 以华东1（杭州）的外网Endpoint为例，其它Region请按实际情况填写。
    String endpoint = "https://oss-cn-shanghai.aliyuncs.com";
    // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
    EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
    // 填写Bucket名称。
    String bucketName = "web-tlias-hyperionml";

    @Value("${UserLocalName}")
    private String userLocalName;

    public OssUtils() throws com.aliyuncs.exceptions.ClientException {
    }

    public ArrayList<String> FreshAllImgUrl(ArrayList<img> imgs) throws Throwable {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);

        ArrayList<String> re = new ArrayList<>();

        imgs.forEach(img -> {
            // 填写Object完整路径，例如exampleobject.txt。Object完整路径中不能包含Bucket名称。
            String objectName = userLocalName + "/" + img.getUptime() + "/" + img.getName();

            URL signedUrl;
            try {
                // 指定生成的签名URL过期时间，单位为毫秒。本示例以设置过期时间为31天为例。
                Date expiration = new Date(new Date().getTime() + 3600 * 1000L * 24 * 31);

                // 生成签名URL。
                GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.GET);
                // 设置过期时间。
                request.setExpiration(expiration);

                // 通过HTTP GET请求生成签名URL。
                signedUrl = ossClient.generatePresignedUrl(request);
                // 打印签名URL。
                re.add(signedUrl.toString());
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
        });

        return re;
    }

}
