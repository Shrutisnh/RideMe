package container.common;

import java.util.Date;

/**
 * Created by G09561636 on 8/12/2016.
 */
public interface DeviceData {
    public static final String OS_ANDROID = "Android";

    public abstract String getIMEI();
    public abstract String getIMSI();
    public abstract String getOSName();
    public abstract String getUniqueId();
    public abstract String getDeviceId();
    public abstract String getModelName();
    public abstract String getAppVersion();
    public abstract String getRootedType();
    public abstract Date parseDate(String s);
    public abstract String getEmulatorFlag();
    public abstract void setDeviceId(String Id);
    public abstract String getSoftwareVersion();
    public abstract String getDeviceIntegrityFlag();
}
