package cn.com.broadlink.happy.model.param;

/**
 * Created by zhuxuyang on 15/12/3.
 */
public class ConfigParam {
    /**
     * ssid : 设备将要接入的无线网络名称
     * password : 无线网络密码,空字符串或者无该字段时表示密码为空
     * timeout : 配置超时时间，可选字段，默认超时时间75s
     * cfgversion : 配网版本，可选字段,默认是2
     */

    private String ssid;
    private String password;
    private int timeout = 75;
    private int cfgversion = 2;

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getSsid() {
        return ssid;
    }

    public String getPassword() {
        return password;
    }

    public int getTimeout() {
        return timeout;
    }

    public int getCfgversion() {
        return cfgversion;
    }

    public void setCfgversion(int cfgversion) {
        this.cfgversion = cfgversion;
    }
}
