package cn.hy.infoReport.module.business.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 体温监测精简vo
 */
@Setter
@Getter
public class IrHealthMonitorSimplifyVo {

    private String userId;
    private Date monitorDate;

}
