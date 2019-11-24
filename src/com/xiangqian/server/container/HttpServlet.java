package com.xiangqian.server.container;

import com.xiangqian.server.connector.HttpServletRequest;
import com.xiangqian.server.connector.HttpServletResponse;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author xiangqian
 * @date 21:52 2019/11/23
 */
public abstract class HttpServlet implements Servlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        doGet((HttpServletRequest) request, (HttpServletResponse) response);
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
