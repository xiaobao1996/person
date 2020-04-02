package cn.hy.infoReport.module.business.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 通知用户统计vo
 */
@Setter
@Getter
public class ReportUserCountVo {

    private Long studentConfigUserCount = 0L;
    private Long staffConfigUserCount = 0L;

}
