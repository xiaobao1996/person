package cn.hy.infoReport.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author
 */
public class IrHealthMonitor implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 检测日期
     */
    private Date monitorDate;

    /**
     * 上午体温
     */
    private BigDecimal amTemperature;

    /**
     * 上午是否人工复测
     */
    private Byte amRecheck;

    /**
     * 上午监测数据的时间
     */
    private Date amCheckTime;

    /**
     * 上午是否是人工测试 0否 1是
     */
    private Byte amHumanCheck;

    /**
     * 下午体温
     */
    private BigDecimal pmTemperature;

    /**
     * 下午是否复测
     */
    private Byte pmRecheck;

    /**
     * 下午监测数据的时间
     */
    private Date pmCheckTime;

    /**
     * 下午是否是人工测试
     */
    private Byte pmHumanCheck;

    /**
     * 体温状态 2异常 1需确认 0正常
     */
    private Byte healthStatus;

    /**
     * 测温进展 2全天未测 1上午未测 0下午未测
     */
    private Byte checkStatus;

    /**
     * 学校id
     */
    private String schoolId;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 更新时间
     */
    private Date updateAt;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getMonitorDate() {
        return monitorDate;
    }

    public void setMonitorDate(Date monitorDate) {
        this.monitorDate = monitorDate;
    }

    public BigDecimal getAmTemperature() {
        return amTemperature;
    }

    public void setAmTemperature(BigDecimal amTemperature) {
        this.amTemperature = amTemperature == null ? null : amTemperature.stripTrailingZeros();
    }

    public Byte getAmRecheck() {
        return amRecheck;
    }

    public void setAmRecheck(Byte amRecheck) {
        this.amRecheck = amRecheck;
    }

    public Date getAmCheckTime() {
        return amCheckTime;
    }

    public void setAmCheckTime(Date amCheckTime) {
        this.amCheckTime = amCheckTime;
    }

    public Byte getAmHumanCheck() {
        return amHumanCheck;
    }

    public void setAmHumanCheck(Byte amHumanCheck) {
        this.amHumanCheck = amHumanCheck;
    }

    public BigDecimal getPmTemperature() {
        return pmTemperature;
    }

    public void setPmTemperature(BigDecimal pmTemperature) {
        this.pmTemperature = pmTemperature == null ? null : pmTemperature.stripTrailingZeros();
    }

    public Byte getPmRecheck() {
        return pmRecheck;
    }

    public void setPmRecheck(Byte pmRecheck) {
        this.pmRecheck = pmRecheck;
    }

    public Date getPmCheckTime() {
        return pmCheckTime;
    }

    public void setPmCheckTime(Date pmCheckTime) {
        this.pmCheckTime = pmCheckTime;
    }

    public Byte getPmHumanCheck() {
        return pmHumanCheck;
    }

    public void setPmHumanCheck(Byte pmHumanCheck) {
        this.pmHumanCheck = pmHumanCheck;
    }

    public Byte getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(Byte healthStatus) {
        this.healthStatus = healthStatus;
    }

    public Byte getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Byte checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}
