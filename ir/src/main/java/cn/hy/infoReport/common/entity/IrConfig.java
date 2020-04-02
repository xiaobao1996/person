package cn.hy.infoReport.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 
 */
public class IrConfig implements Serializable {
    /**
     * 学校id
     */
    private String schoolId;

    /**
     * 异常体温下限
     */
    private BigDecimal temperatureLowLimit;

    /**
     * 禁止通行 0否 1是
     */
    private Byte noPass;

    /**
     * 设备报警 0否 1是
     */
    private Byte deviceAlarm;

    /**
     * 连续异常次数通知
     */
    private Integer notifyContinuousNumber;

    /**
     * 模式 0假期 1开学
     */
    private Byte mode;

    private static final long serialVersionUID = 1L;

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public BigDecimal getTemperatureLowLimit() {
        return temperatureLowLimit;
    }

    public void setTemperatureLowLimit(BigDecimal temperatureLowLimit) {
        this.temperatureLowLimit = temperatureLowLimit;
    }

    public Byte getNoPass() {
        return noPass;
    }

    public void setNoPass(Byte noPass) {
        this.noPass = noPass;
    }

    public Byte getDeviceAlarm() {
        return deviceAlarm;
    }

    public void setDeviceAlarm(Byte deviceAlarm) {
        this.deviceAlarm = deviceAlarm;
    }

    public Integer getNotifyContinuousNumber() {
        return notifyContinuousNumber;
    }

    public void setNotifyContinuousNumber(Integer notifyContinuousNumber) {
        this.notifyContinuousNumber = notifyContinuousNumber;
    }

    public Byte getMode() {
        return mode;
    }

    public void setMode(Byte mode) {
        this.mode = mode;
    }
}