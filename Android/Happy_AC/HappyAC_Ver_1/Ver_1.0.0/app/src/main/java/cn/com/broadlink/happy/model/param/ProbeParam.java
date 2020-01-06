package cn.com.broadlink.happy.model.param;

import java.util.List;

/**
 * Created by zhuxuyang on 15/12/3.
 */
public class ProbeParam {
    /**
     * scantime : 3000
     * pids : ["设备的pid"]
     *
     */

    private int scantime;
    private int version;
    private List<String> pids;

    public void setScantime(int scantime) {
        this.scantime = scantime;
    }

    public int getScantime() {
        return scantime;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<String> getPids() {
        return pids;
    }

    public void setPids(List<String> pids) {
        this.pids = pids;
    }
}
