package cn.hy.infoReport.module.business.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String userId;
    private Date   dutyDate;
    private String userName;
    private String mobile;

}
