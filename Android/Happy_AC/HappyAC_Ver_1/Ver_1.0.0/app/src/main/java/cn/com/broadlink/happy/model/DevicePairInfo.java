package cn.com.broadlink.happy.model;

/**
 * Created by zhujunjie on 2018/3/5.
 */

public class DevicePairInfo {
    private String did;
    private String mac;
    private String pid;
    private String cookie;

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
