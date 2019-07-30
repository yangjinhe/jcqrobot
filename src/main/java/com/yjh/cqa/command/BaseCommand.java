package com.yjh.cqa.command;

import cn.hutool.core.util.CharsetUtil;
import com.sobte.cqp.jcq.entity.CoolQ;
import com.sobte.cqp.jcq.message.CQCode;
import com.yjh.cqa.App;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yangjh on 2019/7/20.
 */
public abstract class BaseCommand {

    String JAVA_EXEC = App.JAVA_EXEC;
    String JAVA_HOME = App.JAVA_HOME;
    CoolQ CQ = App.CQ;
    CQCode CC = App.CC;

    static String jenkinsBaseUrl = "-s http://172.17.0.1:8080/jenkins -auth admin:116726e6ed9b4dab1295c018c790d6f9a1";

    public abstract void exec(Long fromGroup, long fromQQ, String msg);

    void sendMsg(Long fromGroup, long fromQQ, String msg) {
        if (null != fromGroup) {
            CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + " " + msg);
        } else {
            CQ.sendPrivateMsg(fromQQ, msg);
        }
    }

    String executeJenkinsCmd(String msg, Long fromGroup, Long fromQQ) throws Exception {
        String command = JAVA_EXEC + " -jar " + JAVA_HOME + "\\jenkins-cli.jar " + jenkinsBaseUrl + " " + msg;
        return executeCmd(command, msg, fromGroup, fromQQ);
    }

    String executeCmd(String command, String msg, Long fromGroup, Long fromQQ) throws Exception {
        String[] split = msg.split(" ");
        String[] commandSplit = command.split(" ");
        List<String> lcommand = new ArrayList<String>();
        Collections.addAll(lcommand, commandSplit);
        CQ.logDebug("debug", msg + " 命令开始执行");
        //CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + " " + param + " 命令开始执行");

        ProcessBuilder processBuilder = new ProcessBuilder(lcommand);
        processBuilder.redirectErrorStream(true);
        Process p = processBuilder.start();
        InputStream is = p.getInputStream();
        BufferedReader bs = new BufferedReader(new InputStreamReader(is));

        p.waitFor();
        if (p.exitValue() != 0) {
            //说明命令执行失败
            //可以进入到错误处理步骤中
            sendMsg(fromGroup, fromQQ, msg + " 命令执行失败");
            return null;
        }
        String resultLog = "";
        String line;
        while ((line = bs.readLine()) != null) {
            if (split[0].equals("get-job")) {
                line = CharsetUtil.convert(line, "GBK", "UTF-8");
            }
            resultLog += line + "\n";
        }
        return resultLog;
    }
}
