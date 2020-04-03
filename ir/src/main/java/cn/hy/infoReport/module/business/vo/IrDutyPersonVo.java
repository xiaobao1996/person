package cn.hy.infoReport.module.business.vo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author hu
 * @version 1.0
 * @date 2020/4/2 13:35
 * @description 值班表Vo
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IrDutyPersonVo implements Serializable {
    private String id;

    @NotNull(message = "请选择值班人员")
    private String userId;
    @NotNull(message = "请选择值班日期")
    private Date   dutyDate;
    private String userName;
    private String mobile;

}
