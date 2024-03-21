package com.cn.zooey.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.TransportUtils;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

/**
 * @author Fengzl
 * @date 2024/3/19 13:32
 * @desc
 **/
@Slf4j
@Configuration
public class ElasticSearchConfig {

    // 没有生效
    // @Resource
    // ObjectMapper objectMapper;

    @Value("${elasticsearch.ip}")
    private String clientIp;
    @Value("${elasticsearch.port}")
    private Integer clientPort;
    @Value("${elasticsearch.username}")
    private String username;

    @Value("${elasticsearch.password}")
    private String password;


    @Bean
    public ElasticsearchClient elasticsearchClient() {

        // crt证书方式连接
        ClassPathResource resource = new ClassPathResource("es/ca.crt");
        InputStream ism;
        try {
            ism = resource.getInputStream();

            SSLContext sslContext = TransportUtils.sslContextFromHttpCaCrt(ism);
            // 信任全部
            sslContext.init(null, new TrustManager[]{new MyTrustManager()}, new SecureRandom());
            BasicCredentialsProvider credsProv = new BasicCredentialsProvider();
            credsProv.setCredentials(
                    AuthScope.ANY, new UsernamePasswordCredentials(username, password)
            );
            RestClient restClient = RestClient
                    .builder(new HttpHost(clientIp, clientPort, "https"))
                    .setHttpClientConfigCallback(hc -> hc
                            // .disableAuthCaching()
                            .setSSLContext(sslContext)
                            // 忽略证书验证
                            .setSSLHostnameVerifier(new MyHostnameVerifier())
                            .setDefaultCredentialsProvider(credsProv)
                    )
                    .build();
            // ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper(objectMapper));
            ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
            return new ElasticsearchClient(transport);
        } catch (IOException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 自定义验证器,忽略证书校验
     */
    private static class MyHostnameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }
    }


    /**
     * 自定义管理器,信任所有
     */
    private static class MyTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}
