package cn.hy.infoReport.common.busiConstant;

import cn.hy.infoReport.common.enums.CommonLogicalStatus;
import cn.hy.infoReport.common.enums.IrConfigMode;

import java.math.BigDecimal;

/**
 * 默认配置
 */
public class IrDefaultConfig {

    public static BigDecimal temperatureLowLimit = BigDecimal.valueOf(37.3);

    public static Byte noPass = CommonLogicalStatus.NO.getCode();
    public static Byte deviceAlarm = CommonLogicalStatus.NO.getCode();
    public static Integer notifyContinuousNumber = 3;
    public static Byte mode = IrConfigMode.HOLIDAY.getCode();

}
