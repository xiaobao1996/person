package cn.hy.infoReport.module.business.vo;

import java.util.List;

/**
 * 班级提报VO
 */
public class ClassReportVo {

    private String classId;
    private String className;
    private Long totalCount = 0L;
    private Long reportedCount = 0L;
    private List<String> notReportNameList;
    private String officeName;
    private String gradeName;
    private String classCode;
    private Integer distanceYear;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Long getReportedCount() {
        return reportedCount;
    }

    public void setReportedCount(Long reportedCount) {
        this.reportedCount = reportedCount;
    }

    public List<String> getNotReportNameList() {
        return notReportNameList;
    }

    public void setNotReportNameList(List<String> notReportNameList) {
        this.notReportNameList = notReportNameList;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
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
