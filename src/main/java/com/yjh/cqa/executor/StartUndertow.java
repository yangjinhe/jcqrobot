package com.yjh.cqa.executor;

import cn.hutool.core.util.StrUtil;
import com.yjh.cqa.App;
import com.yjh.cqa.servlet.SendMessageServlet;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;

/**
 * Created by yangjh on 2019/7/23.
 */
public class StartUndertow {

    public static final String MYAPP = "/myapp";

    public static Undertow server;

    public static void start() {
        try {
            DeploymentInfo servletBuilder = Servlets.deployment().setClassLoader(App.class.getClassLoader())
                    .setContextPath(MYAPP)
                    .setDeploymentName("myapp.war")
                    .addServlets(Servlets.servlet("MessageServlet", SendMessageServlet.class).addMappings("/messageServlet")
                            , Servlets.servlet("MyServlet", SendMessageServlet.class).addMappings("/myServlet"));
            DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
            manager.deploy();

            HttpHandler servletHandler = manager.start();
            PathHandler path = Handlers.path(Handlers.redirect(MYAPP)).addPrefixPath(MYAPP, servletHandler);

            server = Undertow.builder().addHttpListener(8081, "0.0.0.0").setHandler(path).build();
            server.start();
            App.CQ.logDebug("debug", "启动内部服务器成功");
        } catch (Exception e) {
            e.printStackTrace();
            App.CQ.logDebug("error", "启动内部服务器失败");
        }
    }

    public static void stop() {
        try {
            server.stop();
            App.CQ.logDebug("debug", "停止内部服务器成功");
        } catch (Exception e) {
            App.CQ.logDebug("error", "停止内部服务器失败");
            App.CQ.logDebug("error", StrUtil.sub(e.toString(), 0, 100));
        }

    }
}
