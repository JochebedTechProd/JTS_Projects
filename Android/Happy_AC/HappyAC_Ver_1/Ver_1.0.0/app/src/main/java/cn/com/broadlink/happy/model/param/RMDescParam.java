package cn.com.broadlink.happy.model.param;

import cn.com.broadlink.happy.model.DevicePairInfo;

/**
 * Created by zhujunjie on 2018/4/4.
 */

public class RMDescParam {
    private DevicePairInfo devicePairedInfo;
    private RMDataParam data = new RMDataParam();
    private int version = 1;

    public class RMDataParam {
        private String pid;
        private String ircodeid;
        private int timestamp;
        private IrcodeParam ircode = new IrcodeParam();

        public IrcodeParam getIrcode() {
            return ircode;
        }

        public void setIrcode(IrcodeParam ircode) {
            this.ircode = ircode;
        }

        public String getIrcodeid() {
            return ircodeid;
        }

        public void setIrcodeid(String ircodeid) {
            this.ircodeid = ircodeid;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public class IrcodeParam {
            private String name;
            private String function;
            private String desc;
            private String code;


            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getFunction() {
                return function;
            }

            public void setFunction(String function) {
                this.function = function;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

    }


    public DevicePairInfo getDevicePairedInfo() {
        return devicePairedInfo;
    }

    public void setDevicePairedInfo(DevicePairInfo devicePairedInfo) {
        this.devicePairedInfo = devicePairedInfo;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public RMDataParam getData() {
        return data;
    }

    public void setData(RMDataParam data) {
        this.data = data;
    }


}
