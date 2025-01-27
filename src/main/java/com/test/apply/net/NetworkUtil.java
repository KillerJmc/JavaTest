package com.test.apply.net;

import java.net.NetworkInterface;
import java.net.InetAddress;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 网络工具类
 * @author Jmc
 */
public class NetworkUtil {
    /**
     * 获取本机Mac地址
     * @return 本机Mac地址字符串
     */
    public static String getMacAddress() {
        String delimiter = ":";
        String hexFormat = "%02X";

        try {
            byte[] mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
            return IntStream.range(0, mac.length)
                    .mapToObj(i -> String.format(hexFormat, mac[i]))
                    .collect(Collectors.joining(delimiter));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void main() {
        System.out.println(getMacAddress());
    }
}
