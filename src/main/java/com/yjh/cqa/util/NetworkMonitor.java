package com.yjh.cqa.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.http.HttpUtil;
import com.yjh.cqa.App;

import java.net.URL;
import java.net.URLConnection;

public class NetworkMonitor implements Runnable {

    private String m_strUrl = "http://192.168.74.15:8866";
    private static boolean m_bNetworkAvailable = false;

    public NetworkMonitor() {
    }

    public NetworkMonitor(String strUrl) {
        this.m_strUrl = strUrl;
    }

    public static boolean isNetworkAvailable() {
        return m_bNetworkAvailable;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(m_strUrl);
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(2000);
            String headerField = connection.getHeaderField(0);
            m_bNetworkAvailable = headerField.contains("OK");
            // App.CQ.logDebug("SVN_STATUS", Convert.toStr(m_bNetworkAvailable));
        } catch (Exception e) {
            m_bNetworkAvailable = false;
        }
    }


}
