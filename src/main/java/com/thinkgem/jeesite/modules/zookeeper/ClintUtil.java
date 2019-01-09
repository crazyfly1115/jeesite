package com.thinkgem.jeesite.modules.zookeeper;

import com.fcibook.quick.http.QuickHttp;

import java.net.URLDecoder;

public class ClintUtil {
    public static String postClint(String url,String body){
        QuickHttp qk=new QuickHttp().post().url(url).setBodyContent(body).addHeader("Content-Type","application/json;charset=utf-8");
        return qk.text();
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
        System.out.println(URLDecoder.decode("http%3A%2F%2F47.93.234.168%3A8443%2Fcrawlnode%2Fcrawler%2Ftask_schedule"));

    }
}
