package com.test.apply.net;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        var macAddressSeparator = ":";
        var macAddressFormat = "%02X";

        try {
            // 获取所有网络接口
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                // 过滤掉环回接口并获取硬件地址
                if (!networkInterface.isLoopback() && networkInterface.getHardwareAddress() != null) {
                    byte[] mac = networkInterface.getHardwareAddress();
                    if (mac != null) {
                        // 格式化MAC地址并返回
                        StringBuilder macAddress = new StringBuilder();
                        for (int i = 0; i < mac.length; i++) {
                            macAddress.append(String.format(macAddressFormat, mac[i]));
                            if (i != mac.length - 1) {
                                macAddress.append(macAddressSeparator);
                            }
                        }
                        return macAddress.toString();
                    }
                }
            }
            throw new RuntimeException("无法获取有效的MAC地址");
        } catch (Exception e) {
            throw new RuntimeException("获取MAC地址失败", e);
        }
    }

    public static List<String> getAllMacAddresses() {
        var macAddressSeparator = ":";
        var macAddressFormat = "%02X";
        List<String> macAddresses = new ArrayList<>();

        try {
            // 获取所有网络接口
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                // 过滤掉环回接口并获取硬件地址
                if (!networkInterface.isLoopback() && networkInterface.getHardwareAddress() != null) {
                    byte[] mac = networkInterface.getHardwareAddress();
                    if (mac != null) {
                        // 格式化MAC地址并添加到列表
                        StringBuilder macAddress = new StringBuilder();
                        for (int i = 0; i < mac.length; i++) {
                            macAddress.append(String.format(macAddressFormat, mac[i]));
                            if (i != mac.length - 1) {
                                macAddress.append(macAddressSeparator);
                            }
                        }
                        macAddresses.add(macAddress.toString());
                    }
                }
            }
            if (macAddresses.isEmpty()) {
                throw new RuntimeException("无法获取有效的MAC地址");
            }
            return macAddresses;
        } catch (Exception e) {
            throw new RuntimeException("获取MAC地址失败", e);
        }
    }

    void main() {
        System.out.println(getMacAddress());
        System.out.println(getAllMacAddresses());
    }
}
