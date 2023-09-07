package com.cn.zooey.common.util;


import com.cn.zooey.common.base.util.Region;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Fengzl
 * @date 2023/8/29 16:26
 * @desc
 **/
@Slf4j
public class IPUtils {

    private static final String IP_UTILS_FLAG = ",";
    private static final String UNKNOWN = "unknown";
    private static final String LOCALHOST_IP = "0:0:0:0:0:0:0:1";
    private static final String LOCALHOST_IP1 = "127.0.0.1";
    private static final String DEFAULT_IP = "0.0.0.0";
    private static final String HTTPS = "https://";
    private static final String HTTP = "http://";

    private static Searcher SEARCHER;

    static {
        try {
            URL url = ResourceUtils.getURL("classpath:ip/ip2region.xdb");
            String dbPath = url.getPath();

            byte[] bytes = Searcher.loadContentFromFile(dbPath);
            log.error("初始化ip2region , SEARCHER");
            SEARCHER = Searcher.newWithBuffer(bytes);
        } catch (Exception e) {
            log.error("初始化ip2region.xdb文件失败,报错信息:[{}]", e.getMessage(), e);
        }
    }


    /**
     * 获取IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
     */
    public static String getClientIp(HttpServletRequest request) {
        // 以下两个获取在k8s中，将真实的客户端IP，放到了x-Original-Forwarded-For。而将WAF的回源地址放到了 x-Forwarded-For了。
        String ip = request.getHeader("X-Original-Forwarded-For");
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }

        // 获取nginx等代理的ip
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (LOCALHOST_IP1.equalsIgnoreCase(ip) || LOCALHOST_IP.equalsIgnoreCase(ip)) {

                ip = DEFAULT_IP;

                // 兼容k8s集群获取ip
                // 根据网卡取本机配置的IP
                // InetAddress iNet = null;
                // try {
                //     iNet = InetAddress.getLocalHost();
                // } catch (Exception e) {
                //     log.error("getClientIp error: {}", e.getMessage());
                // }
                // if (null != iNet) {
                //     ip = iNet.getHostAddress();
                // }
            }
        }

        // 使用代理，则获取第一个IP地址
        if (!StringUtils.isEmpty(ip) && ip.indexOf(IP_UTILS_FLAG) > 0) {
            ip = ip.substring(0, ip.indexOf(IP_UTILS_FLAG));
        }

        return ip;
    }

    /**
     * 根据Url获取ip所属地, 在不确定参数是否是 纯ip的情况下使用
     * @param strUrl
     * @return
     */
    public static Region domain2region(String strUrl) {
        String ip = "";
        // 处理url, 用于方便 Url解析出IP或域名
        if (!(strUrl.startsWith(HTTPS) || strUrl.startsWith(HTTP))) {
            strUrl = HTTPS + strUrl;
        }
        try {
            URL url = new URL(strUrl);
            ip = url.getHost();
        } catch (MalformedURLException e) {
            log.error("url解析失败: {}", e.getMessage());
        }
        // 如果不是ip, 按域名去解析
        if (!isIp(ip)) {
            ip = parseDomainName(ip);
        }
        return ip2region(ip);
    }


    /**
     * 根据iP获取所属地
     * @param ip
     * @return
     */
    public static Region ip2region(String ip) {
        Region res = new Region();

        if (!isIp(ip) || Objects.isNull(SEARCHER)) {
            return res;
        }
        try {
            // 国家|区域|省份|城市|ISP_
            String region = SEARCHER.search(ip);
            String[] split = region.split("\\|");
            res.setIp(ip);
            res.setCountry(split[0]);
            res.setProvince(split[2]);
            res.setCity(split[3]);
            res.setISP_(split[4]);
        } catch (Exception e) {
            log.error("ip2region error : {}", e.getMessage());
        }
        return res;
    }


    /**
     * 解析域名
     * @param domain
     * @return
     */
    private static String parseDomainName(String domain) {
        String ip = "";
        if (isDomain(domain)) {
            InetAddress ia;
            try {
                ia = InetAddress.getByName(domain);
                ip = ia.getHostAddress();
                log.info("域名解析ip: {} -> {}", domain, ip);
            } catch (UnknownHostException e) {
                log.warn("域名解析ip失败: {}", e.getMessage());
            }
        }

        return ip;
    }

    /**
     * 是否是ip
     * @param str
     * @return
     */
    public static boolean isIp(String str) {
        String pattern = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);

        return m.matches();
    }

    /**
     * 是否是域名
     * @param domain
     * @return
     */
    public static boolean isDomain(String domain) {
        String regex = "^(?:[a-zA-Z0-9](?:[a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(domain);
        return matcher.matches();
    }


}
