package cn.com.broadlink.happy.model.result;

import java.util.List;

import cn.com.broadlink.happy.model.DeviceInfo;

/**
 * Created by zhuxuyang on 15/12/3.
 */
public class ProbeResult {
    private int status;
    private String msg;
    private List<DeviceInfo> list;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DeviceInfo> getList() {
        return list;
    }

    public void setList(List<DeviceInfo> list) {
        this.list = list;
    }
}
