package com.xiangqian.server.example;

import com.xiangqian.server.HttpServer;
import com.xiangqian.server.container.HttpContainer;
import com.xiangqian.server.example.servlet.DefaultHttpServlet;
import com.xiangqian.server.example.servlet.IndexHttpServlet;
import com.xiangqian.server.example.servlet.LoginHttpServlet;
import com.xiangqian.server.example.servlet.StaticHttpServlet;
import com.xiangqian.server.service.HttpService;
import lombok.extern.slf4j.Slf4j;

/**
 * 启动服务，访问地址: http://localhost:8080/servlet/index
 *
 * @author xiangqian
 * @date 15:54 2019/11/24
 */
@Slf4j
public class AppMain {

    public static void main(String[] args) {
        try {
            HttpServer server = new HttpServer();
            HttpService service = server.getService();
            register(service);
            service.startup();
        } catch (Exception e) {
            log.error("", e);
            System.exit(1);
        }
    }

    public static void register(HttpService service) {
        //
//        HttpConnector connector = service.getConnector();
//        connector.setPort(8089);

        // register servlet
        HttpContainer container = service.getContainer();
        container.registerDefaultServlet("/servlet/default", DefaultHttpServlet.class);
        container.registerServlet("/servlet/index", IndexHttpServlet.class);
        container.registerServlet("/servlet/login", LoginHttpServlet.class);
        container.registerServlet("/servlet/static/*", StaticHttpServlet.class);
    }

}
