package cn.hy.infoReport.module.business.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 学生健康监测vo
 */
@Data
@Setter
@Getter
public class StudentHealthMonitorVo {

    private String userId;
    private String name;
    private String schoolId;

    private Integer grade;
    private String classCode;
    private String classId;
    private String className;

    private BigDecimal amTemperature;
    private Byte amRecheck;
    private BigDecimal pmTemperature;
    private Byte pmRecheck;
    private Byte healthStatus;
    private Byte checkStatus;
    private Date monitorDate;


    public void setAmTemperature(BigDecimal amTemperature) {
        this.amTemperature = amTemperature == null ? null : amTemperature.stripTrailingZeros();
    }

    public void setPmTemperature(BigDecimal pmTemperature) {
        this.pmTemperature = pmTemperature == null ? null : pmTemperature.stripTrailingZeros();
    }
}
