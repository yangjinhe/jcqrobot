package com.yjh.cqa.util;

import cn.hutool.core.convert.Convert;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CheckAppHealthUtil {

    public static String check(String url, String param) throws IOException {
        //String url = "http://10.183.7.25/efmp-common-web/pub/qryArea";
        String contentType = "application/x-www-form-urlencoded; charset=UTF-8";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json, text/plain, */*");
        headers.put("Accept-Encoding", "gzip, deflate");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");
        headers.put("Cache-Control", "no-cache");
        headers.put("Connection", "keep-alive");
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headers.put("Cookie", "JSESSIONID=2845F60105306B16E62255937A04C789");
        headers.put("Host", "10.183.7.25");
        headers.put("Origin", "http://10.183.7.25");
        headers.put("Pragma", "no-cache");
        headers.put("Referer", "http://10.183.7.25/psm");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.80 Safari/537.36");
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS) //连接超时
                .readTimeout(5, TimeUnit.SECONDS) //读取超时
                .writeTimeout(5, TimeUnit.SECONDS).build(); //写超时

        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("param", param);

        Request.Builder reqBuilder = new Request.Builder()
                .url(url)
                .post(formBuilder.build())
                .addHeader("content-type", contentType)
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "cefba91a-503b-0bf6-542f-e6d777ee7cc1");
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            reqBuilder.addHeader(entry.getKey(), entry.getValue());
        }
        Request request = reqBuilder.build();
        Response response = client.newCall(request).execute();
        ResponseBody respBody = response.body();
        if (null != respBody) {
            String string = respBody.string();
            Map<String, Object> map = JsonUtil.getMap(string);
            boolean isSuccess = Convert.toBool(map.get("success"));
            if (!isSuccess) {
                return url + "访问出错:" + map.get("msg");
            }
        } else {
            return "网络错误";
        }
        return "0";
    }
}