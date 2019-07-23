package com.yjh.cqa.command;

import cn.hutool.core.util.CharsetUtil;

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
    public void exec(long fromGroup, long fromQQ, String msg) {
        try {
            if (msg.equals("help")) {
                CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + "以下命令可用：\n" +
                        "build job_name #构建任务\n" +
                        "list-jobs [job_name] #展示所有的任务列表，job_name参数可选 \n" +
                        "enable-job job_name #启用任务\n" +
                        "disable-job job_name #禁用任务\n" +
                        "get-job job_name #展示任务的XML配置文件\n" +
                        "version #展示当前jenkins的版本\n" +
                        "更多命令请参考官方文档");
                return;
            }
            String command = JAVA_EXEC + " -jar " + JAVA_HOME + "\\jenkins-cli.jar -s http://172.17.0.1:8080/jenkins -auth admin:116726e6ed9b4dab1295c018c790d6f9a1 " + msg;
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
                CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + " " + msg + " 命令执行失败");
                return;
            }
            String resultLog = "";
            String line;
            while ((line = bs.readLine()) != null) {
                line = CharsetUtil.convert(line, "GBK", "UTF-8");
                resultLog += line + "\n";
            }
            CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + " " + msg + " 命令提交完成\n" + resultLog);
        } catch (Exception e) {
            CQ.logDebug("debug", e.getMessage());
            CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + " " + msg + " 命令执行异常");
        }
    }

}
