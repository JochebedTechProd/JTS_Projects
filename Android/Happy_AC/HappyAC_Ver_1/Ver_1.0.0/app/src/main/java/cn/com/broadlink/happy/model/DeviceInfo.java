package cn.com.broadlink.happy.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by zhuxuyang on 15/12/3.
 */
public class DeviceInfo implements Parcelable, Serializable {
    private String did;
    private String mac;
    private String pid;
    private String name;
    private int type;
    private int subdevice;
    private boolean lock;
    private boolean newconfig;
    private int password;
    private String lanaddr;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public boolean isNewconfig() {
        return newconfig;
    }

    public void setNewconfig(boolean newconfig) {
        this.newconfig = newconfig;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public String getLanaddr() {
        return lanaddr;
    }

    public void setLanaddr(String lanaddr) {
        this.lanaddr = lanaddr;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSubdevice() {
        return subdevice;
    }

    public void setSubdevice(int subdevice) {
        this.subdevice = subdevice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.did);
        dest.writeString(this.mac);
        dest.writeString(this.pid);
        dest.writeString(this.name);
        dest.writeInt(this.type);
        dest.writeByte(this.lock ? (byte) 1 : (byte) 0);
        dest.writeByte(this.newconfig ? (byte) 1 : (byte) 0);
        dest.writeInt(this.password);
        dest.writeString(this.lanaddr);
    }

    public DeviceInfo() {
    }

    protected DeviceInfo(Parcel in) {

        this.did = in.readString();
        this.mac = in.readString();
        this.pid = in.readString();
        this.name = in.readString();
        this.type = in.readInt();
        this.lock = in.readByte() != 0;
        this.newconfig = in.readByte() != 0;
        this.password = in.readInt();
        this.lanaddr = in.readString();
    }

    public static final Creator<DeviceInfo> CREATOR = new Creator<DeviceInfo>() {
        @Override
        public DeviceInfo createFromParcel(Parcel source) {
            return new DeviceInfo(source);
        }

        @Override
        public DeviceInfo[] newArray(int size) {
            return new DeviceInfo[size];
        }
    };
}
