package cn.hy.infoReport.module.business.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 体温上报精简vo
 */
@Setter
@Getter
public class IrTemperatureReportSimplifyVo {

    private String userId;
    private Date dataTime;

}
