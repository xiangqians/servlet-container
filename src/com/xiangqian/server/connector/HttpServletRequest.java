package com.xiangqian.server.connector;

import com.xiangqian.server.Utils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiangqian
 * @date 20:45 2019/11/23
 */
@Slf4j
public class HttpServletRequest implements ServletRequestAdapter {

    private static final int CAPACITY = 1024;
    private static final String UTF_8 = "UTF-8";

    private InputStream is;
    private String context;
    private String url;
    private String uri;
    private Map<String, String> requestParamMap;

    public HttpServletRequest(InputStream is) throws IOException {
        this.is = is;
        initContext();
    }

    private void initContext() throws IOException {
        byte[] buffer = new byte[CAPACITY];
        int readLen = is.read(buffer, 0, CAPACITY);
        if (readLen != -1) {
            context = new String(buffer, 0, readLen, UTF_8);
        }
        log.debug("context=\n" + context);
        if (context == null) {
            return;
        }

        //
        String[] contextArr = context.split("\n");
        initURL(contextArr);
        analysisRequestParams(contextArr);
    }


    private void initURL(String[] contextArr) {
        url = contextArr[0].replace("GET", "").replace("POST", "").replace("HTTP/1.1", "").trim();
        log.debug("url=" + url);

        initURI();
    }

    private void initURI() {
        int index = url.indexOf("?");
        if (index > 0) {
            uri = url.substring(0, index);
        } else {
            uri = url;
        }
        log.debug("uri=" + uri);
    }

    private void analysisRequestParams(String[] contextArr) {

        String context0 = null;
        int index = url.indexOf("?");
        if (index > 0) {
            context0 = url.substring(index + 1);
        }

        String contextN = contextArr[contextArr.length - 1];

        String requestParamsStr = context0 == null ? contextN : (context0 + "&" + contextN);
        log.debug("requestParamsStr=" + requestParamsStr);

        requestParamMap = new HashMap<>();

        String[] requestParamsArr = requestParamsStr.split("&");
        String[] requestParamArr = null;
        String requestParamName = null;
        String requestParamValue = null;
        for (String requestParamStr : requestParamsArr) {
            requestParamArr = requestParamStr.split("=");
            requestParamName = Utils.trim(requestParamArr[0]);
            if ("".equals(requestParamName)) {
                continue;
            }

            if (requestParamArr.length > 1) {
                requestParamValue = Utils.trim(requestParamArr[1]);
            }
            requestParamMap.put(requestParamName, requestParamValue);

            requestParamValue = null;
        }

        log.debug("requestParamMap=" + requestParamMap);
    }

    public String getUri() {
        return uri;
    }

    @Override
    public String getParameter(String name) {
        if (requestParamMap == null) {
            return null;
        }
        return requestParamMap.get(name);
    }

    @Override
    public void close() throws Exception {
        is.close();
    }
}
