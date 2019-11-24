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
 * @date 15:59 2019/11/24
 */
@Slf4j
public class LoginHttpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info(LoginHttpServlet.class.getSimpleName() + " doGet | username: " + username + ", password: " + password);
        Utils.copyInText(LoginHttpServlet.class.getResourceAsStream("/html/success.html"), response.getOutputStream(), true, false);
    }

}
