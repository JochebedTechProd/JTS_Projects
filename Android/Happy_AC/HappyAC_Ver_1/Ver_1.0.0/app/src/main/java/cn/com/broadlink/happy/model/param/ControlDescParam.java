package cn.com.broadlink.happy.model.param;

import cn.com.broadlink.happy.model.DevicePairInfo;

/**
 * Created by zhuxuyang on 15/12/3.
 */
public class ControlDescParam {
    private String command;
    private DevicePairInfo devicePairedInfo;
    private int version = 1;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }



    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public DevicePairInfo getDevicePairedInfo() {
        return devicePairedInfo;
    }

    public void setDevicePairedInfo(DevicePairInfo devicePairedInfo) {
        this.devicePairedInfo = devicePairedInfo;
    }
}
