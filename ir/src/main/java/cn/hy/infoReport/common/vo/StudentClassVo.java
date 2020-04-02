package cn.hy.infoReport.common.vo;

import cn.hy.infoReport.common.entity.BusiClass;
import cn.hy.infoReport.common.entity.SysOffice;

/**
 * 学生班级vo
 */
public class StudentClassVo {

    private String studentId;
    private SysOffice sysOffice;
    private BusiClass busiClass;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public SysOffice getSysOffice() {
        return sysOffice;
    }

    public void setSysOffice(SysOffice sysOffice) {
        this.sysOffice = sysOffice;
    }

    public BusiClass getBusiClass() {
        return busiClass;
    }

    public void setBusiClass(BusiClass busiClass) {
        this.busiClass = busiClass;
    }
}
