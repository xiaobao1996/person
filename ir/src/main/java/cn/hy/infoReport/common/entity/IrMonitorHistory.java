package cn.hy.infoReport.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author
 */
public class IrMonitorHistory implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 测温日期
     */
    private Date monitorDate;

    /**
     * 测试时间
     */
    private Date monitorDatetime;

    /**
     * 测温人id
     */
    private String monitorUserId;

    /**
     * 测温人姓名
     */
    private String monitorUserName;

    /**
     * 测温设备
     */
    private String monitorDevice;

    /**
     * 测温地点
     */
    private String monitorPlace;

    /**
     * 体温
     */
    private BigDecimal temperature;

    /**
     * 数据来源 0人工监测 1机器监测
     */
    private Byte source;

    /**
     * 学校id
     */
    private String schoolId;

    /**
     * 创建时间
     */
    private Date createAt;

    /***  业务字段  ***/
    /**
     * 被测温人姓名
     */
    private String username;

    /**
     * 班级名称
     */
    private String className;


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

    public Date getMonitorDatetime() {
        return monitorDatetime;
    }

    public void setMonitorDatetime(Date monitorDatetime) {
        this.monitorDatetime = monitorDatetime;
    }

    public String getMonitorUserId() {
        return monitorUserId;
    }

    public void setMonitorUserId(String monitorUserId) {
        this.monitorUserId = monitorUserId;
    }

    public String getMonitorUserName() {
        return monitorUserName;
    }

    public void setMonitorUserName(String monitorUserName) {
        this.monitorUserName = monitorUserName;
    }

    public String getMonitorDevice() {
        return monitorDevice;
    }

    public void setMonitorDevice(String monitorDevice) {
        this.monitorDevice = monitorDevice;
    }

    public String getMonitorPlace() {
        return monitorPlace;
    }

    public void setMonitorPlace(String monitorPlace) {
        this.monitorPlace = monitorPlace;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature == null ? null : temperature.stripTrailingZeros();
    }

    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
