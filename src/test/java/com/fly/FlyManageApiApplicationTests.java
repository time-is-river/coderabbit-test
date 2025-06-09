package com.fly;

import com.fly.component.BarcodeApi;
import com.fly.entity.Commodity;
import com.fly.entity.SysUser;
import com.fly.entity.barcode.BarcodeFreeResponse;
import com.fly.entity.barcode.BarcodeInfo;
import com.fly.mapper.CommodityMapper;
import com.fly.mapper.SysUserMapper;
import com.fly.request.CommodityPageRequest;
import com.fly.response.ResponseUtils;
import com.fly.service.CommodityService;
import com.fly.service.impl.CommodityServiceImpl;
import com.fly.utils.Constants;
import com.github.pagehelper.PageInfo;
import com.tencentcloudapi.ocr.v20181119.models.QueryBarCodeResponse;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;
import java.util.UUID;

@SpringBootTest
class FlyManageApiApplicationTests {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BarcodeApi barcodeApi;

    @Autowired
    private CommodityServiceImpl commodityService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private CommodityMapper commodityMapper;

    @Test
    void contextLoads() {
        /*String encode1 = passwordEncoder.encode("admin");
        String encode2 = passwordEncoder.encode("admin");
        System.out.println(encode1);
        System.out.println(encode2);
        System.out.println("ƥ����encode1��"+passwordEncoder.matches("admin",encode1));
        System.out.println("ƥ����encode2��"+passwordEncoder.matches("admin",encode2));

        System.out.println("ƥ������"+passwordEncoder.matches("123456","$2a$10$5T851lZ7bc2U87zjt/9S6OkwmLW62tLeGLB2aCmq3XRZHA7OI7Dqa"));
        System.out.println("ƥ������"+passwordEncoder.matches("123456","$2a$10$szHoqQ64g66PymVJkip98.Fap21Csy8w.RD8v5Dhq08BMEZ9KaSmS"));
        System.out.println("ƥ������"+passwordEncoder.matches("123456","$2a$10$Z6a7DSehk58ypqyWzfFAbOR0gaqpwVzY9aNXKqf4UhDCSJxsbDqDK"));*/

        //get barcode for free
        String barcode = "6901294204492";
        BarcodeFreeResponse result = barcodeApi.queryGoodsInfoByBarcodeForFree(barcode);
        System.out.println("查询结果" + result);
        QueryBarCodeResponse resultFormTencent = barcodeApi.queryGoodsInfoByBarcodeFromTencent(barcode);
        System.out.println("腾讯查询结果:" + resultFormTencent);

    }

    public ResponseUtils queryGoodsInfoByBarcode(String barcode) {
        Commodity commodity = commodityMapper.queryCommodityByBarcode(barcode);
        if (commodity == null) {
            //数据库不存在商品信息 调取免费的条码查询接口获取商品信息
            BarcodeFreeResponse result = barcodeApi.queryGoodsInfoByBarcodeForFree(barcode);
            if (result!=null && Constants.ApiResultCode.SUCCESS.equals(result.getCode())) {
                BarcodeInfo barcodeInfo = result.getData();
                commodity = new Commodity();
                commodity.setBarcode(barcode);
                commodity.setName(barcodeInfo.getGoodsName());
                commodity.setManufacturerName(barcodeInfo.getSupplier());
                return ResponseUtils.response(200, "通过外部接口查询商品信息成功！", commodity);
            } else {
                return ResponseUtils.response(200, "数据库和外部接口均未查询到商品信息！", null);
            }

        } else {
            return ResponseUtils.response(203, "已存在该商品信息", commodity);
        }
    }


    @Test
    void queryCommodity() {
        SysUser user = sysUserMapper.selectUserByUserName("admin");
        CommodityPageRequest request = new CommodityPageRequest();
        request.setPage(1);
        request.setSize(5);
        PageInfo<Commodity> pageInfo = commodityService.pageQueryCommodity(request);
        System.out.println(pageInfo);
    }

    @Test
    void findByBinary(){
        int key = 268;// 1 2 3 4  5   6 7   8  9   10
        int[] ints =   {2,4,7,13,16,89,99,120,222,260,268};
        int low = 0,height = ints.length -1;
        while(low <= height) {
            int meddle = (low + height) / 2;
            System.out.println("init position:" + meddle);
            if (ints[meddle] == key) {
                System.out.println("position:" + meddle);
                break;
            }

            if (ints[meddle] > key) {
                height = meddle - 1;
            }
            if (ints[meddle] < key) {
                low = meddle + 1;
            }
        }
    }

    @Test
    void uuidTest() {
        String filename = "ewed3435355.123add";
        // �Ȳ���
        int index = filename.lastIndexOf(".");
        // ��ȡ
        String lastname = filename.substring(index, filename.length());
        // Ψһ �ַ���  fsd-sfsdf-sfsd-sdfsd
        String uuid = UUID.randomUUID().toString().replace("-", "");
        System.out.println("���ƣ�" + uuid+lastname);

        String ext = "dat";
        String name = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), ext);
        System.out.println("name:" + name);

        Long milliSecond = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        Random random = new Random();
        System.out.println("---"+milliSecond + random.nextInt(1000));

    }
}
