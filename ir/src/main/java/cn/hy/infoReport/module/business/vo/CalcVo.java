package cn.hy.infoReport.module.business.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 统计vo
 */
@Setter
@Getter
public class CalcVo {

    private Long confirmedAbnormalCount;
    private Long amNotMonitorCount;
    private Long pmNotMonitorCount;
    private Long needRecheckCount;
}
