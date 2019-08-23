package com.thinkgem.jeesite.modules.ips.service;

import com.fcibook.quick.http.QuickHttp;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Map;

/**
 * @Author zhangsy
 * @Description  互联网追溯系统相关调用的接口类
 * @Date 10:36 2019/7/31
 * @Param 
 * @return 
 * @Company 重庆尚渝网络科技
 * @version v1000
 **/
public class TracebackService {

    public final  static  String getkeyandurl="http://lab.uiho.com/cqmapdata/keyandweb/get";
    /**
    * 获取 互联网追溯系统的配置的关键字和url
     **/
    public  static  void  getkeyandurl(){
        String bodyContent="{\t\t\"reqHead\": {\t\t\t\"reqGUID\":\"5dc1d0a39eb14d7cb9385a373515e64e\",\t\t\t\"reqSign\":\"6e70e3098a5c3022ff5aa485e4e8cfdb\"\t\t},\t\t\"reqBody\": {\t\t\t\"reqContent\":\"\"\t\t}}";


        QuickHttp quickHttp=new QuickHttp();
        String json =quickHttp.post().url(getkeyandurl).setBodyContent(bodyContent).text();
        Gson gson=new Gson();
        Map map=gson.fromJson(json,Map.class);
        System.out.println(decrypt(map.get("object").toString(),"a06cfd1be4554009b627959bb24b9172"));
    }





    public static String encrypt(String content, String password) {
        try {
            if (StringUtils.isEmpty(content))
                return "";
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(password.getBytes());
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(byteContent);
            String str = base64in(result);
            return str;
        } catch (NoSuchAlgorithmException e) {
            // e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // e.printStackTrace();
        } catch (InvalidKeyException e) {
            // e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            // e.printStackTrace();
        } catch (BadPaddingException e) {
            // e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密算法
     * @param str  需解密的字符串
     * @param password  密钥pwdkey
     * @return
     */

    public static String decrypt(String str, String password) {
        try {
            byte[] content = base64out(str);
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(content);
            return new String(result,"UTF-8");
        } catch (NoSuchAlgorithmException e) {
            // e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // e.printStackTrace();
        } catch (InvalidKeyException e) {
            // e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            // e.printStackTrace();
        } catch (BadPaddingException e) {
            // e.printStackTrace();
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return "";
    }
    /**
     * MD5加密
     * @param str
     * @return
     */
    public static String encodeMD5(String str) {
        String strDigest = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] data = md5.digest(str.getBytes("utf-8"));
            strDigest = bytesToHexString(data);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return strDigest;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length<= 0) {
            return null;
        }
        for (int i = 0; i<src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

//    备注：如果运行环境是jdk1.7。在进行base64加解密时运用如下方案
    /**
     * BASE64编码   JDK1.7
     * @param bytes
     * @return
     */
    public static String base64in(byte[] bytes){
        return new sun.misc.BASE64Encoder().encode(bytes);
    }
    /**
     * BASE64解码   JDK1.7
     * @param str
     * @return
     */
    public static byte[] base64out(String str){
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        try {
            return decoder.decodeBuffer(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
