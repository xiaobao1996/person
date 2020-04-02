package cn.hy.infoReport.module.business.vo;

import cn.hy.infoReport.common.entity.IrTemperatureReport;
import cn.hy.infoReport.common.vo.PageInfo;

/**
 * @Author: JiuZhou
 * @Date: 2020/3/4 18:04
 * @Version 1.0
 */
public class IrTemperatureReportListVo {
    PageInfo<IrTemperatureReport> pageInfo = null;
    private long unusualNumber;

    public long getUnusualNumber() {
        return unusualNumber;
    }

    public void setUnusualNumber(long unusualNumber) {
        this.unusualNumber = unusualNumber;
    }

    public void setUnusualNumber(int unusualNumber) {
        this.unusualNumber = unusualNumber;
    }

    public PageInfo<IrTemperatureReport> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<IrTemperatureReport> pageInfo) {
        this.pageInfo = pageInfo;
    }
}
