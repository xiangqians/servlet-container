package com.xiangqian.server.container;

import com.xiangqian.server.connector.HttpServletRequest;
import com.xiangqian.server.connector.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.Servlet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiangqian
 * @date 12:07 2019/11/24
 */
@Slf4j
public class HttpContainer {

    private List<ServletRegistrar> servletRegistrarList;
    private ServletRegistrar defaultServletRegistrar;

    public HttpContainer() {
        servletRegistrarList = new ArrayList<>();
    }

    public void registerServlet(String uri, Class<? extends Servlet> servletClass) {
        servletRegistrarList.add(new ServletRegistrar(uri, servletClass));
    }

    public void registerDefaultServlet(String uri, Class<? extends Servlet> servletClass) {
        defaultServletRegistrar = new ServletRegistrar(uri, servletClass);
    }

    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletRegistrar registrar = null;
        if (request.getUri() != null) {
            int size = servletRegistrarList.size();
            for (int i = 0; i < size; i++) {
                registrar = servletRegistrarList.get(i);
                if (registrar.getUri().endsWith("*")) {
                    if (request.getUri().startsWith(registrar.getUri().substring(0, registrar.getUri().length() - 1))) {
                        break;
                    }
                } else if (registrar.getUri().equals(request.getUri())) {
                    break;
                }

                registrar = null;
            }
        }

        if (registrar == null) {
            registrar = defaultServletRegistrar;
        }

        if (registrar == null) {
            StringBuilder builder = new StringBuilder();
            builder.append("No mapped default servlet!");
            builder.append(" ");
            builder.append("at");
            builder.append(" ");
            builder.append(new Date().toString());
            response.getWriter().write(builder.toString());
            return;
        }

        Servlet servlet = registrar.getInstance();
        if (servlet == null) {
            servlet = registrar.getClazz().newInstance();
            registrar.setInstance(servlet);
            servlet.init(null);
        }

        servlet.service(request, response);
    }

}
