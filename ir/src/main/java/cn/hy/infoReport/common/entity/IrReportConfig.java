package cn.hy.infoReport.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class IrReportConfig implements Serializable {
    /**
     * 学校id
     */
    private String schoolId;

    /**
     * 学生通知身份
     */
    private String studentReportIdentify;

    /**
     * 学生通知用户id集合
     */
    private String studentReportUserIds;

    /**
     * 教职工通知身份
     */
    private String staffReportIdentify;

    /**
     * 教职工通知用户id集合
     */
    private String staffReportUserIds;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 更新时间
     */
    private Date updateAt;

    private static final long serialVersionUID = 1L;

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getStudentReportIdentify() {
        return studentReportIdentify;
    }

    public void setStudentReportIdentify(String studentReportIdentify) {
        this.studentReportIdentify = studentReportIdentify;
    }

    public String getStudentReportUserIds() {
        return studentReportUserIds;
    }

    public void setStudentReportUserIds(String studentReportUserIds) {
        this.studentReportUserIds = studentReportUserIds;
    }

    public String getStaffReportIdentify() {
        return staffReportIdentify;
    }

    public void setStaffReportIdentify(String staffReportIdentify) {
        this.staffReportIdentify = staffReportIdentify;
    }

    public String getStaffReportUserIds() {
        return staffReportUserIds;
    }

    public void setStaffReportUserIds(String staffReportUserIds) {
        this.staffReportUserIds = staffReportUserIds;
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