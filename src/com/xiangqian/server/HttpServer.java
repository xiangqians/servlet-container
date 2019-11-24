package com.xiangqian.server;

import com.xiangqian.server.service.HttpService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiangqian
 * @date 11:48 2019/11/24
 */
@Slf4j
public class HttpServer {

    private HttpService service;

    public HttpServer() {
        service = new HttpService();
    }

    public HttpService getService() {
        return service;
    }

}
