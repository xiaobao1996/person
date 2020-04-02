package cn.hy.infoReport.module.business.vo;

import cn.hy.infoReport.common.entity.PmsStaff;
import cn.hy.infoReport.common.entity.PmsStudent;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 人工测温记录vo
 */
@Data
@Setter
@Getter
public class HumanHealthCheckVo {

    @NotBlank(message = "请先选择被测温人")
    private String userId;
    private String name;
    @NotNull(message = "体温不能为空")
    private BigDecimal temperature;
    private String monitorPlace;
    @NotNull(message = "测温时间不能为空")
    private Date monitorTime;
    @NotBlank(message = "测温人不能为空")
    private String monitorUserId;
    private String monitorUsername;
    private String device;

    private PmsStudent pmsStudent;
    private PmsStaff pmsStaff;

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature == null ? null : temperature.stripTrailingZeros();
    }
}
