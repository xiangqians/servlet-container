package com.xiangqian.server.connector;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author xiangqian
 * @date 20:47 2019/11/23
 */
@Slf4j
public class HttpServletResponse implements ServletResponseAdapter {

    private static final String UTF_8 = "UTF-8";

    private OutputStream os;
    private PrintWriter printWriter;
    private ServletOutputStream servletOutputStream;

    public HttpServletResponse(OutputStream os) {
        this.os = os;
    }

    private String getHeader() {
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append("HTTP/1.1");
        headerBuilder.append(" ");
        headerBuilder.append(200);
        headerBuilder.append(" ");
        headerBuilder.append("OK");
        headerBuilder.append("\r\n\r\n");
        return headerBuilder.toString();
    }

    @Override
    public PrintWriter getWriter() {
        if (printWriter == null) {
            printWriter = new PrintWriter(os);
        }
        printWriter.write(getHeader());
        return printWriter;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (servletOutputStream == null) {
            servletOutputStream = new CustomServletOutputStream(os);
        }
        servletOutputStream.write(getHeader().getBytes());
        return servletOutputStream;
    }

    @Override
    public void close() throws Exception {
        if (printWriter != null) {
            try {
                printWriter.close();
            } catch (Exception e) {
                log.error("PrintWriter closes exception!", e);
            }
        }

        if (servletOutputStream != null) {
            try {
                servletOutputStream.close();
            } catch (Exception e) {
                log.error("ServletOutputStream closes exception!", e);
            }
        }

        try {
            os.close();
        } catch (Exception e) {
            log.error("OutputStream closes exception!", e);
        }
    }

    @Override
    public void flush() throws IOException {
        if (printWriter != null) {
            printWriter.flush();
        }

        if (servletOutputStream != null) {
            servletOutputStream.flush();
        }
    }

    /**
     * @author xiangqian
     * @date 15:49 2019/11/24
     */
    public static class CustomServletOutputStream extends ServletOutputStream {
        private OutputStream os;

        public CustomServletOutputStream(OutputStream os) {
            this.os = os;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
        }

        @Override
        public void write(int b) throws IOException {
            os.write(b);
        }
    }

}
