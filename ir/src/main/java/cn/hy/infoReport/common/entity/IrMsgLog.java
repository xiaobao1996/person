package cn.hy.infoReport.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class IrMsgLog implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 日期
     */
    private Date date;

    /**
     * 被监测人用户id
     */
    private String userId;

    /**
     * 时间范围 0上午 1下午
     */
    private Byte timeArea;

    /**
     * 接收人 ,分割
     */
    private String receiver;

    /**
     * 操作类型 0自动发送 1手工发送
     */
    private Byte opeType;

    /**
     * 学校id
     */
    private String schoolId;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 发送结果
     */
    private String result;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 创建人
     */
    private String createBy;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Byte getTimeArea() {
        return timeArea;
    }

    public void setTimeArea(Byte timeArea) {
        this.timeArea = timeArea;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Byte getOpeType() {
        return opeType;
    }

    public void setOpeType(Byte opeType) {
        this.opeType = opeType;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}