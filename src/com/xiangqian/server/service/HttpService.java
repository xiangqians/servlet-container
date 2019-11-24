package com.xiangqian.server.service;

import com.xiangqian.server.connector.HttpConnector;
import com.xiangqian.server.container.HttpContainer;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiangqian
 * @date 11:48 2019/11/24
 */
@Slf4j
public class HttpService {

    private HttpConnector connector;
    private HttpContainer container;

    public HttpService() {
        container = new HttpContainer();
        connector = new HttpConnector(container);
    }

    public void startup() throws Exception {
        connector.startup();
    }

    public HttpContainer getContainer() {
        return container;
    }

    public HttpConnector getConnector() {
        return connector;
    }

}
