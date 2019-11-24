package com.xiangqian.server.example.servlet;

import com.xiangqian.server.Utils;
import com.xiangqian.server.connector.HttpServletRequest;
import com.xiangqian.server.connector.HttpServletResponse;
import com.xiangqian.server.container.HttpServlet;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author xiangqian
 * @date 16:04 2019/11/24
 */
@Slf4j
public class DefaultHttpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info(DefaultHttpServlet.class.getSimpleName() + " doGet");
        Utils.copyInText(DefaultHttpServlet.class.getResourceAsStream("/html/404.html"), response.getOutputStream(), true, false);
    }

}
