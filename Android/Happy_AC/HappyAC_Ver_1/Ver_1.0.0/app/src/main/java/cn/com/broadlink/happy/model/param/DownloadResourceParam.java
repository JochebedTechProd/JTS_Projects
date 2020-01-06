package cn.com.broadlink.happy.model.param;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhuxuyang on 15/12/4.
 */
public class DownloadResourceParam {
    private String action;

    private String type;

    private Map<String, String> extrainfo = new HashMap<>();

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getExtrainfo() {
        return extrainfo;
    }

    public void setExtrainfo(Map<String, String> extrainfo) {
        this.extrainfo = extrainfo;
    }
}
