package com.yjh.cqa.util;

import cn.hutool.core.convert.Convert;
import com.yjh.cqa.App;

import java.net.URL;
import java.net.URLConnection;

public class NetworkMonitor implements Runnable {

    private String m_strUrl = "http://192.168.74.15:8866";
    private static boolean m_bNetworkAvailable = false;
    public static boolean NetworkMonitorFlag = true;

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
        System.out.println("开始执行网络检查");
        System.out.println("NetworkMonitorFlag：　" +NetworkMonitorFlag);
        while (NetworkMonitorFlag) {
            try {
                URL url = new URL(m_strUrl);
                URLConnection connection = url.openConnection();
                String headerField = connection.getHeaderField(0);
                m_bNetworkAvailable = headerField.contains("OK");
                App.CQ.logDebug("SVN_STATUS", Convert.toStr(m_bNetworkAvailable));
                Thread.sleep(2000);
            } catch (Exception e) {
                m_bNetworkAvailable = false;
            }
        }
    }


}
