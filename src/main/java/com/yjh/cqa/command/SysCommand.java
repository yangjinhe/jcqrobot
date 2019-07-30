package com.yjh.cqa.command;

import cn.hutool.core.util.StrUtil;
import com.yjh.cqa.util.NetworkMonitor;

/**
 * Created by yangjh on 2019/7/28.
 */
public class SysCommand extends BaseCommand {

    @Override
    public void exec(Long fromGroup, long fromQQ, String msg) {
        String[] split = msg.split(" ");
        switch (split[0]) {
            case "get_vpn_status":
                String status;
                if (NetworkMonitor.isNetworkAvailable()) {
                    status = "vpn正常";
                } else {
                    status = "vpn断开，请执行 #sys:conn_asiainfo_vpn userName password";
                }
                sendMsg(fromGroup, fromQQ, status);
                break;
            case "conn_asiainfo_vpn":
                try {
                    String cmd = "build conn-asiainfo-vpn -p vpn_username=" + split[1] + " -p vpn_password=" + split[2];
                    String resultLog = executeJenkinsCmd(cmd, fromGroup, fromQQ);
                    if (null != resultLog) {
                        sendMsg(fromGroup, fromQQ, msg + " 命令提交完成" + (StrUtil.isBlank(resultLog) ? "" : "\n" + resultLog));
                    }
                } catch (Exception e) {
                    CQ.logDebug("error", "连接vpn失败");
                    sendMsg(fromGroup, fromQQ, "连接vpn失败: " + StrUtil.sub(e.toString(), 0, 100));
                }
                break;
            default:
        }
    }
}
