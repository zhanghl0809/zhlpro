package com.example.Utiles;


import com.example.exception.BizException;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class JianQuan {

    public static void main(String[] args) {

        // String re = null;
        // try {
        //     re = requestAliApi("13012419900123122X", "于青青", "18132071819", "6230730030669718");
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        // System.out.println(re);
        BigDecimal bigDecimal = new BigDecimal("0.0004");

        System.out.println(BigDecimal.ZERO.setScale(1));

    }


    private static String requestAliApi(String idNumber, String name, String mobile, String cardNo) throws Exception {
        Map<String, String> headers = new HashMap<>(2);
        String method = "POST";
        headers.put("Authorization", "APPCODE " +"9db838a9da364b0dbd6234968d55f5c5");
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<>(2);
        Map<String, String> bodys = new HashMap<>(5);
        bodys.put("ReturnBankInfo", "YES");
        bodys.put("cardNo", cardNo);
        bodys.put("idNo", idNumber);
        bodys.put("name", name);
        bodys.put("phoneNo", mobile);

        HttpResponse response = AliHttpUtils.doPost("https://yunyidata.market.alicloudapi.com", "/bankAuthenticate4",
                method, headers, querys, bodys);
        // 获取response的body

        return EntityUtils.toString(response.getEntity());

    }
}
