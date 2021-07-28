package com.tsf.demo.provider.untils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author 程刘德
 * @version 1.0
 * @Description TODO
 * @date 2021/5/11
 */
public class IpUntil {
    public static  String getIp() {
        InetAddress localHost = null;
        try {
            localHost = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String ip = localHost.getHostAddress();  // 返回格式为：xxx.xxx.xxx
        return ip;
    }
}
