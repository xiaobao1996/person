package cn.hy.infoReport.common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author
 */
public class PmsStaff implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 教职工姓名
     */
    private String name;

    /**
     * 工号
     */
    private String staffNo;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 学校id
     */
    private String schoolId;

    /**
     * 更新时间
     */
    private Date updateAt;

    /**
     * 同步时间
     */
    private Long syncTime;

    private List<String> jobTypeNameList;

    private Set<String> jobTypeIdSet;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Long getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Long syncTime) {
        this.syncTime = syncTime;
    }

    public List<String> getJobTypeNameList() {
        return jobTypeNameList;
    }

    public void setJobTypeNameList(List<String> jobTypeNameList) {
        this.jobTypeNameList = jobTypeNameList;
    }

    public Set<String> getJobTypeIdSet() {
        return jobTypeIdSet;
    }

    public void setJobTypeIdSet(Set<String> jobTypeIdSet) {
        this.jobTypeIdSet = jobTypeIdSet;
    }
}
