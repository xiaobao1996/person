package cn.hy.infoReport.module.business.vo;

/**
 * 学生上报VO
 */
public class StudentReportVo {
    private Long totalCount = 0L;
    private Long reportedCount = 0L;

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
}
