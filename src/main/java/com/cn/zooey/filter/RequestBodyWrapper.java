package com.cn.zooey.filter;

import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @Author Fengzl
 * @Date 2023/3/4 13:16
 * @Desc 解决post请求不能多次获取参数
 **/
public class RequestBodyWrapper extends HttpServletRequestWrapper {

    /**
     * 存储输入流
     */
    private byte[] requestBody;
    /**
     * 参数体
     */
    private final String jsonText;


    public RequestBodyWrapper(HttpServletRequest request) throws IOException {
        super(request);
        // 读取输入流里的请求参数,并保存到requestBody
        requestBody = StreamUtils.copyToByteArray(request.getInputStream());
        jsonText = new String(requestBody, StandardCharsets.UTF_8);
    }

    /**
     * 重写读取
     * @return
     */
    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    /**
     * 重写获取输入流
     * @return
     * @throws IOException
     */
    @Override
    public ServletInputStream getInputStream() {
        if (requestBody == null) {
            requestBody = new byte[0];
        }
        final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            /**
             * 读取requestBody, 配置可多次读取的关键
             * @return
             */
            @Override
            public int read() {
                return bais.read();
            }
        };
    }


    /**
     * 获取post参数
     */
    public boolean isEmpty() {
        return requestBody == null || requestBody.length == 0;
    }
    /**
     * 获取post参数
     */
    public String getRequestParams() {
        return jsonText;
    }
}
