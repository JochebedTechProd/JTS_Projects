package cn.com.broadlink.happy.model.param;

/**
 * Created by zhuxuyang on 15/12/4.
 */
public class GetResourceParam {
    private String account_id;
    private String account_session;
    private String product_pid;

    public GetResourceParam() {
    }

    public String getAccount_id() {
        return this.account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getAccount_session() {
        return this.account_session;
    }

    public void setAccount_session(String account_session) {
        this.account_session = account_session;
    }

    public String getProduct_pid() {
        return this.product_pid;
    }

    public void setProduct_pid(String product_pid) {
        this.product_pid = product_pid;
    }
}
