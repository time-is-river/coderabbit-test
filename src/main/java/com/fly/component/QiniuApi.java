package com.fly.component;

import com.fly.config.QiniuConfig;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

/**
 * @author
 * @date 2021/6/15 13:51
 */
@Slf4j
@Component
public class QiniuApi {

    /**
     * 上传凭证
     */
    //final Auth auth = Auth.create(QiniuConfig.accessKey, QiniuConfig.secretKey);

    /**
     * 上传图片到七牛云
     * @param file
     * @param key
     * @return
     */
    public String uploadImg(FileInputStream file) {
        String returnPath = "";
        Auth auth = Auth.create(QiniuConfig.accessKey, QiniuConfig.secretKey);
        Configuration cfg = new Configuration(Region.region0());
        // 其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        try {
            String upToken = auth.uploadToken(QiniuConfig.bucket);
            try {
                //获取毫秒数
                String key = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(12), "jpg");
                Response response = uploadManager.put(file, key, upToken,null, null);
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                //拼接图片访问地址
                returnPath = QiniuConfig.path + "/" + putRet.key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                    ex2.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return returnPath;
    }
}
