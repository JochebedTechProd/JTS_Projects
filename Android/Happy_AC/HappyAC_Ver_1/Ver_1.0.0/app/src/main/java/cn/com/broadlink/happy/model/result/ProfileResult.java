package cn.com.broadlink.happy.model.result;

import java.util.List;
import java.util.Map;

/**
 * Created by zhuxuyang on 15/12/8.
 */
public class ProfileResult {
    private Profile profile;
    private int status;
    private String msg;

    public static class Profile {
        private String ver;
        private DescEntity desc;
        private List<String> srvs;
        private List<SuidsEntity> suids;

        public void setVer(String ver) {
            this.ver = ver;
        }

        public void setDesc(DescEntity desc) {
            this.desc = desc;
        }

        public void setSrvs(List<String> srvs) {
            this.srvs = srvs;
        }

        public void setSuids(List<SuidsEntity> suids) {
            this.suids = suids;
        }

        public String getVer() {
            return ver;
        }

        public DescEntity getDesc() {
            return desc;
        }

        public List<String> getSrvs() {
            return srvs;
        }

        public List<SuidsEntity> getSuids() {
            return suids;
        }

        public static class DescEntity {
            private String pid;
            private String vendor;
            private String cat;
            private String model;

            public void setPid(String pid) {
                this.pid = pid;
            }

            public void setVendor(String vendor) {
                this.vendor = vendor;
            }

            public void setCat(String cat) {
                this.cat = cat;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public String getPid() {
                return pid;
            }

            public String getVendor() {
                return vendor;
            }

            public String getCat() {
                return cat;
            }

            public String getModel() {
                return model;
            }
        }

        public static class SuidsEntity {
            private String suid;

            public Map<String, Object> getIntfs() {
                return intfs;
            }

            public void setIntfs(Map<String, Object> intfs) {
                this.intfs = intfs;
            }

            private Map<String, Object> intfs;

            public void setSuid(String suid) {
                this.suid = suid;
            }

            public String getSuid() {
                return suid;
            }

            public static class IntfsEntity {
            }
        }
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

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
}
