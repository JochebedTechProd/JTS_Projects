package cn.com.broadlink.happy.model.result;

import cn.com.broadlink.happy.model.DevicePairInfo;

/**
 * Created by zhuxuyang on 15/12/3.
 */
public class PairResult {
    private int status;
    private String msg;
    private DevicePairInfo devicePairedInfo;


    public void setStatus(int status) {
        this.status = status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }


    public DevicePairInfo getDevicePairedInfo() {
        return devicePairedInfo;
    }

    public void setDevicePairedInfo(DevicePairInfo devicePairedInfo) {
        this.devicePairedInfo = devicePairedInfo;
    }
}
