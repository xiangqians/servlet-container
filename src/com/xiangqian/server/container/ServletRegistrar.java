package com.xiangqian.server.container;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.Servlet;

/**
 * Servlet注册器
 *
 * @author xiangqian
 * @date 12:04 2019/11/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServletRegistrar {

    private String uri;
    private Class<? extends Servlet> clazz;
    private Servlet instance;

    public ServletRegistrar(String uri, Class<? extends Servlet> clazz) {
        this.uri = uri;
        this.clazz = clazz;
    }
}
