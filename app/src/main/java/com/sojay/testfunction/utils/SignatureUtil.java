package com.sojay.testfunction.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class SignatureUtil {

    public static String getUrlParam(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(){
            {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    add(entry.getKey());
                }
            }
        };
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            String value = params.get(key) + "&";
            sb.append(key).append("=").append(value);
        }
        return sb.toString();
    }

    public static String getSignature(Map<String, String> params, String appSecret) {
        List<String> keys = new ArrayList();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            keys.add(entry.getKey());
        }

        // 将所有参数按key的升序排序
        Collections.sort(keys, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        // 构造签名的源字符串
        StringBuilder contents=new StringBuilder("");
        for (String key : keys) {
            if (key=="_w_signature"){
                continue;
            }
            contents.append(key).append("=").append(params.get(key));
            System.out.println("key:"+key+",value:"+params.get(key));
        }
        contents.append("_w_secretkey=").append(appSecret);

        // 进行hmac sha1 签名
        byte[] bytes= hmacSha1(appSecret.getBytes(),contents.toString().getBytes());

        //字符串经过Base64编码
        String sign = Base64.encodeToString(bytes, Base64.DEFAULT);
        System.out.println("###########   base64 = " + sign);

//        try {
//            sign = URLEncoder.encode(sign, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        return sign;

    }

    public static byte[] hmacSha1(byte[] key, byte[] data) {
        try {
            //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
            SecretKey secretKey = new SecretKeySpec(key, "HmacSHA1");
            //生成一个指定 Mac 算法 的 Mac 对象
            Mac mac = Mac.getInstance("HmacSHA1");
            //用给定密钥初始化 Mac 对象
            mac.init(secretKey);
            //完成 Mac 操作
            return mac.doFinal(data);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }
}