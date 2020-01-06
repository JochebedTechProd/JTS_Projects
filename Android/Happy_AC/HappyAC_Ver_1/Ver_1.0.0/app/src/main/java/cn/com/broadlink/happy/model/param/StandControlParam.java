package cn.com.broadlink.happy.model.param;

import java.util.List;

/**
 * Created by zhuxuyang on 15/12/8.
 */
public class StandControlParam {
    private String prop = "stdctrl";
    private String act = "get";

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    private List<String> params;
}
