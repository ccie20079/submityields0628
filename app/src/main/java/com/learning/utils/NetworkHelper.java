package com.learning.utils;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

/**
 * Package_name:   com.learning.utils
 * user:           草中斗鬼
 * date:           2020/7/30
 * email:          ccie20079@126.com
 */
public class NetworkHelper {
    /**
     *
     * @return 获取设备MACAddress
     */
    public static String getMacAddress() throws SocketException {
        List<NetworkInterface> interfaces = null;
        try {
            interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface ni : interfaces) {
                if (null == ni) continue;
                String niName = ni.getName();
                if (niName.isEmpty()) continue;
                if (!"wlan0".equals(niName)) continue;
                byte[] macBytes = ni.getHardwareAddress();
                if (null == macBytes || macBytes.length <= 0) continue;
                StringBuffer sBuffer = new StringBuffer();
                for (byte b : macBytes) {
                    sBuffer.append(String.format("%02X:", b));
                }
                sBuffer.deleteCharAt(sBuffer.length() - 1);
                return sBuffer.toString();
            }
        } catch (SocketException e) {
            LogUtil.d(NetworkHelper.class.getSimpleName(), e.toString());
            throw e;
        }
        return "unknown";
    }

}
