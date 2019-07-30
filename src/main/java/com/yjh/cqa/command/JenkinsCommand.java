package com.yjh.cqa.command;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.yjh.cqa.util.NetworkMonitor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yangjh on 2019/7/20.
 */
public class JenkinsCommand extends BaseCommand {

    @Override
    public void exec(Long fromGroup, long fromQQ, String msg) {
        try {
            if (msg.equals("help")) {
                sendMsg(fromGroup, fromQQ, "以下命令可用：\n" +
                        "build job_name #构建任务\n" +
                        "list-jobs [job_name] #展示所有的任务列表，job_name参数可选 \n" +
                        "enable-job job_name #启用任务\n" +
                        "disable-job job_name #禁用任务\n" +
                        "get-job job_name #展示任务的XML配置文件\n" +
                        "version #展示当前jenkins的版本\n" +
                        "更多命令请参考官方文档");
                return;
            }
            if (!NetworkMonitor.isNetworkAvailable()) {
                sendMsg(fromGroup, fromQQ, "svn连接失败，请检查网络！");
            }
            String resultLog = executeJenkinsCmd(msg, fromGroup, fromQQ);
            if (null != resultLog) {
                sendMsg(fromGroup, fromQQ, msg + " 命令提交完成" + (StrUtil.isBlank(resultLog) ? "" : "\n" + resultLog));
            }
        } catch (Exception e) {
            CQ.logDebug("debug", e.getMessage());
            sendMsg(fromGroup, fromQQ, msg + " 命令执行异常");
        }
    }

}
