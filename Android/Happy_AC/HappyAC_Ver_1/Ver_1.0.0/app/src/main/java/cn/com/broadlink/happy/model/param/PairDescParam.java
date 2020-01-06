package cn.com.broadlink.happy.model.param;

import cn.com.broadlink.happy.model.DeviceInfo;

/**
 * Created by zhuxuyang on 15/12/3.
 */
public class PairDescParam {
    private int timeout = 1500;
    private int version = 1;
    private DeviceInfo deviceInfo;


    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
