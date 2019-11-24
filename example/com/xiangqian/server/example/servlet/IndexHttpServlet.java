package com.xiangqian.server.example.servlet;

import com.xiangqian.server.Utils;
import com.xiangqian.server.connector.HttpServletRequest;
import com.xiangqian.server.connector.HttpServletResponse;
import com.xiangqian.server.container.HttpServlet;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author xiangqian
 * @date 15:56 2019/11/24
 */
@Slf4j
public class IndexHttpServlet extends HttpServlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        log.info(IndexHttpServlet.class.getSimpleName() + " init");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info(IndexHttpServlet.class.getSimpleName() + " doGet");
        Utils.copyInText(IndexHttpServlet.class.getResourceAsStream("/html/index.html"), response.getOutputStream(), true, false);
    }
}
