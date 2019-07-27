package com.yjh.cqa.servlet;

import cn.hutool.core.convert.Convert;
import com.sobte.cqp.jcq.entity.CoolQ;
import com.sobte.cqp.jcq.message.CQCode;
import com.yjh.cqa.App;
import com.yjh.cqa.vo.RspResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by yangjh on 2019/7/22.
 */
public class SendMessageServlet extends HttpServlet {

    private CoolQ CQ = App.CQ;
    private CQCode CC = App.CC;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        Long qq = Convert.toLong(request.getParameter("qq"));
        Long groupId = Convert.toLong(request.getParameter("groupId"));
        String message = request.getParameter("message");
        execute(type, message, qq, groupId, request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        Long qq = Convert.toLong(request.getParameter("qq"));
        Long groupId = Convert.toLong(request.getParameter("groupId"));
        String message = new String(Convert.toStr(request.getParameter("message")).getBytes("ISO-8859-1"), "utf-8");
        execute(type, message, qq, groupId, request, response);

    }

    private void execute(String type, String message, Long qq, Long groupId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        RspResult result = null;
        if (null == type || null == message) {
            CQ.logDebug("error", "参数不正确");
            result = RspResult.getFailtResult("参数不正确");
        } else {
            int i = 0;
            switch (type) {
                case "private":
                    i = CQ.sendPrivateMsg(qq, message);
                    break;
                case "group":
                    if (null != qq) {
                        message = CC.at(qq) + " " + message;
                    }
                    i = CQ.sendGroupMsg(groupId, message);
                    break;
                case "dis_cuss_msg":
                    if (null != qq) {
                        message = CC.at(qq) + " " + message;
                    }
                    i = CQ.sendDiscussMsg(groupId, message);
                    break;
                default:
                    break;

            }
            if (i > 0) {
                result = RspResult.getSuccessResult().setMsg("发送消息成功").setData(message).setCode(i + "");
            } else {
                result = RspResult.getFailtResult("发送消息失败").setData(message).setCode(i + "");
            }
        }
        response.reset();
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter writer = response.getWriter();
        writer.println(result.toString());
        writer.flush();
    }
}
