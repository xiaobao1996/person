package cn.hy.infoReport.common.entity;

import java.util.List;

/**
 * 机构用户id集合vo
 */
public class OfficeUserIdsVo {

    private String officeId;
    private String officeName;
    private List<String> userIdList;
    private String gradeName;
    private String classCode;
    private Integer distanceYear;

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public Integer getDistanceYear() {
        return distanceYear;
    }

    public void setDistanceYear(Integer distanceYear) {
        this.distanceYear = distanceYear;
    }
}
