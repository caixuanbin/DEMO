/**
 * projectName: xxxx
 * fileName: IpUtils.java
 * packageName: com.xxxx.logs.utils
 * date: 2018-05-31 10:48
 * copyright(c) xxxx
 */
package com.xbcai.myweb.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @version: V1.0
 * @author: fendo
 * @className: IpUtils
 * @packageName: com.xxxx.logs.utils
 * @description: IP工具类
 * @data: 2018-05-31 10:48
 **/
public class IpUtils {
    /**
     * 获取客户端IP地址
     * @param request
     * @return
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return StringUtils.split(ObjectUtils.toString(ip), ",")[0];
    }

}
