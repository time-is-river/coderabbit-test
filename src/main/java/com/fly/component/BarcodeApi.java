package com.fly.component;

import com.alibaba.fastjson.JSONObject;
import com.fly.config.TencentConfig;
import com.fly.entity.barcode.BarcodeFreeResponse;
import com.fly.response.ResponseUtils;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.QueryBarCodeRequest;
import com.tencentcloudapi.ocr.v20181119.models.QueryBarCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 根据条码获取商品信息
 * @author
 * @date 2021/6/3 11:18
 */
@Slf4j
@Component
public class BarcodeApi {

    @Autowired
    private RestTemplate restTemplate;

    final String urlForFree = "https://www.mxnzp.com/api/barcode/goods/details?barcode={barcode}&app_id={appId}&app_secret={appSecret}";
    final String appId ="okltyjknionkrifq";// "oglersfmprxq8gcm";
    final String appSecret = "V3VkVGorZUhiYnU1a3dMYmNVMzBYZz09"; //""WDRlRGdCK1ZMS00ySlFjMEhOMVJXUT09";

    /**
     * github 免费查询商品信息接口
     * @param barcode
     * @return
     */
    public BarcodeFreeResponse queryGoodsInfoByBarcodeForFree(String barcode) {
        BarcodeFreeResponse result = restTemplate.getForObject(
                urlForFree,
                BarcodeFreeResponse.class,
                barcode,
                appId,
                appSecret);

        return result;
    }


    /**
     * 阿里平台的查询商品信息接口(接口地址有误 文档不清楚 已经弃用)
     * @param barcode
     * @return
     */
    public String queryGoodsInfoByBarcodeFromAli(String barcode) {
        String url = "https://jisutmscsb.market.alicloudapi.com/barcode/read?barcode=6954767417684";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        // 以表单的方式提交
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        headers.add("Authorization", "APPCODE c6a8f29593914e4e856eb175cb1549f8");
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("barcode", "6907992514970");
        //将请求头部和参数合成一个请求

        //HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);
        //执行HTTP请求，将返回的结构使用ResultVO类格式化
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(map,headers);

        ResponseEntity<String> response = restTemplate.exchange(url, method, httpEntity, String.class);

        JSONObject jsonObject = JSONObject.parseObject(response.getBody());

        return jsonObject.toJSONString();
    }

    /**
     * 腾讯条形码查询接口
     * @param barcode
     * @return
     */
    public QueryBarCodeResponse queryGoodsInfoByBarcodeFromTencent(String barcode) {
        try {
            Credential cred = new Credential(TencentConfig.secretId, TencentConfig.secretKey);
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(TencentConfig.url);

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            OcrClient client = new OcrClient(cred, "ap-shanghai", clientProfile);

            QueryBarCodeRequest req = new QueryBarCodeRequest();
            req.setBarCode(barcode);

            QueryBarCodeResponse resp = client.QueryBarCode(req);

            System.out.println(QueryBarCodeResponse.toJsonString(resp));
            return resp;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
