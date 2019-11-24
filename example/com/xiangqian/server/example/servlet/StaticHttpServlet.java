package com.xiangqian.server.example.servlet;

import com.xiangqian.server.Utils;
import com.xiangqian.server.connector.HttpServletRequest;
import com.xiangqian.server.connector.HttpServletResponse;
import com.xiangqian.server.container.HttpServlet;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 静态资源处理器
 *
 * @author xiangqian
 * @date 16:07 2019/11/24
 */
@Slf4j
public class StaticHttpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info(StaticHttpServlet.class.getSimpleName() + " doGet");

        String filename = request.getUri().replace("/servlet/static/", "");
        InputStream is = StaticHttpServlet.class.getResourceAsStream("/static/" + filename);
        if (is != null) {
            Utils.copyInBinary(is, response.getOutputStream(), true, false);
        }
    }

}
