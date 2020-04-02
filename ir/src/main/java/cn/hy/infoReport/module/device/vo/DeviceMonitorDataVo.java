package cn.hy.infoReport.module.device.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 设备监控参数vo
 */
@Setter
@Getter
public class DeviceMonitorDataVo {

    @NotBlank(message = "用户id不能为空")
    private String userId;
    private String username;
    @NotNull(message = "时间不能为空")
    private Date time;
    @NotBlank(message = "设备名称不能为空")
    private String deviceName;
    @NotBlank(message = "地点不能为空")
    private String location;
    @NotNull(message = "体温数据不能为空")
    private BigDecimal temperature;
}
