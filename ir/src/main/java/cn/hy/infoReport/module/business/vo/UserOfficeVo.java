package cn.hy.infoReport.module.business.vo;

import cn.hy.infoReport.common.entity.SysOffice;

import java.util.List;

public class UserOfficeVo {
    private String userId;
    private List<SysOffice> officeList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<SysOffice> getOfficeList() {
        return officeList;
    }

    public void setOfficeList(List<SysOffice> officeList) {
        this.officeList = officeList;
    }
}
