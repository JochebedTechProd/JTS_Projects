package cn.com.broadlink.happy.model.result;

/**
 * Created by zhuxuyang on 15/12/4.
 */
public class GetResourceResult {
    private int status;
    private String msg;
    private String url;

    public GetResourceResult() {
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
