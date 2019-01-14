package com.thinkgem.jeesite.modules.zookeeper;

import com.fcibook.quick.http.QuickHttp;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.entity.ContentType;

import java.io.*;
import java.net.*;

public class ClintUtil {
    public static String postClint(String url,String body){
        QuickHttp qk=new QuickHttp().post().url(url).setBodyContent(body).addHeader("Content-Type","application/json;charset=utf-8").setContentType(ContentType.create("application/json","utf-8"));
        return qk.text();
//        return sendPost(url,body);
    }

    public static void main(String[] args) {
        String body="{\n" +
                "    \"proxy_ips\": [\n" +
                "        \"19.168.13.2:5825\",\n" +
                "        \"192.168.42.258:8080\"\n" +
                "    ],\n" +
                "    \"update_py\": {\n" +
                "        \"version\": \"v2_1_3\",\n" +
                "        \"ftp_path\": \"/crawler/parser/py/htmlparser.py\"\n" +
                "    },\n" +
                "    \"ftp_ip\": \"192.168.25.35\",\n" +
                "    \"ftp_port\": 22,\n" +
                "    \"ftp_uname\": \"ftp_uname\",\n" +
                "    \"ftp_upwd\": \"ftp_upwd\"\n" +
                "}";
      //  System.out.println(postClint("http://47.93.234.168:8443/crawlnode/crawler/service_config",body));
//        System.out.println(URLDecoder.decode("http%3A%2F%2F47.93.234.168%3A8443%2Fcrawlnode%2Fcrawler%2Ftask_schedule"));
        System.out.println(postClint("https://wx.vitco.cn:443/crawlnode/crawler/test","中文"));
        System.out.println(System.getProperty("file.encoding"));
        System.out.println(sendPost("https://wx.vitco.cn:443/crawlnode/crawler/test","中文"));
    }
    public static String sendPost(String path, String param) {
        String result="";
        String encoding="UTF-8";

        try {
            byte[] data=param.getBytes(encoding);
            URL url=new URL(path);
            HttpURLConnection conn=(HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "text/plain;charset="+encoding);
            conn.setRequestProperty("Contcent-Length", String.valueOf(data.length));
            conn.setConnectTimeout(5*10000);
            conn.setReadTimeout(50000);
            OutputStream out=conn.getOutputStream();
            out.write(data);
            out.flush();//可能有问题出现死锁
            if(conn.getResponseCode()==200){
                out.close();
                InputStream in=conn.getInputStream();
                result=readInputstrem(in);
            }
            conn.disconnect();
        } catch (UnsupportedEncodingException e) {
            result="";
            e.printStackTrace();

        } catch (MalformedURLException e) {//url 路径问题的异常
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }
    /**
     * 读取流
     * @param in
     * @return
     * @throws IOException
     */
    public static String readInputstrem(InputStream in) throws IOException{
        Reader reader = new InputStreamReader(in, "UTF-8");
        //增加缓冲功能
        String line;
        StringBuffer buff=new StringBuffer();
        BufferedReader bufferedReader = new BufferedReader(reader);
        synchronized(bufferedReader){
            while ((line = bufferedReader.readLine()) != null) {
                buff.append(line);
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
        return buff.toString();
    }
}
