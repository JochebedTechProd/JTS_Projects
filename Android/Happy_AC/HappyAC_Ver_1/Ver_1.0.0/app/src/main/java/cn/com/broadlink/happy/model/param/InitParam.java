package cn.com.broadlink.happy.model.param;

/**
 * Created by zhuxuyang on 15/12/3.
 */
public class InitParam {
    private String packageName;
    private String license;
    private int loglevel;

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public int getLoglevel() {
        return loglevel;
    }

    public void setLoglevel(int loglevel) {
        this.loglevel = loglevel;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
