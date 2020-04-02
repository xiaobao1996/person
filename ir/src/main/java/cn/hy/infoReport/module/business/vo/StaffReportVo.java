package cn.hy.infoReport.module.business.vo;

import java.util.List;

/**
 * 教职工上报统计vo
 */
public class StaffReportVo {
    private Long totalCount = 0L;
    private Long reportedCount = 0L;
    private List<String> notReportNameList;

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
}
