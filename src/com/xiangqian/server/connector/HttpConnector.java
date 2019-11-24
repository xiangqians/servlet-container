package com.xiangqian.server.connector;

import com.xiangqian.server.Utils;
import com.xiangqian.server.container.HttpContainer;
import lombok.extern.slf4j.Slf4j;

import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author xiangqian
 * @date 16:22 2019/11/23
 */
@Slf4j
public class HttpConnector {

    private String hostname;
    private int port;

    private HttpContainer container;

    public HttpConnector(HttpContainer container) {
        this.container = container;
        this.hostname = "localhost";
        this.port = 8080;
    }

    public void startup() throws Exception {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName(hostname));
            log.info("Server is starting, listener port {}", port);

            Socket socket = null;
            while (true) {
                try {
                    socket = serverSocket.accept();
                    process(socket);
                } catch (Exception e) {
                    log.error("", e);
                } finally {
                    Utils.quietlyClosed(socket);
                }
            }
        } finally {
            Utils.quietlyClosed(serverSocket);
        }
    }

    private void process(Socket socket) throws Exception {
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        try {
            log.debug("receive a request from " + socket.getRemoteSocketAddress().toString());

            //
            request = new HttpServletRequest(socket.getInputStream());
            response = new HttpServletResponse(socket.getOutputStream());
            container.execute(request, response);
            response.flush();
            socket.shutdownOutput();

            log.debug("responded to " + socket.getRemoteSocketAddress().toString());
            log.debug("\n");

        } finally {
            Utils.quietlyClosed(response, request, socket);
        }
    }


    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
