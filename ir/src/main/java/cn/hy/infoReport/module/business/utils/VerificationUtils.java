package cn.hy.infoReport.module.business.utils;

import cn.hy.infoReport.common.entity.IrTemperatureReport;
import cn.hy.infoReport.common.enums.ArriveOtherArea;
import cn.hy.infoReport.common.exception.MessageException;
import cn.hy.infoReport.common.utils.AccountValidatorUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

/**
 * @Author: JiuZhou
 * @Date: 2020/3/4 15:06
 * @Version 1.0
 */
public class VerificationUtils {


    public static void verificationIrTemperatureReport(IrTemperatureReport irTemperatureReport,Byte mode) {
        if (StringUtils.isBlank(irTemperatureReport.getUserName())) {
            throw new MessageException("姓名不能为空!");
        }
        if (mode == 0 && StringUtils.isBlank(irTemperatureReport.getCurrentLocation())) {
            throw new MessageException("当前所在地不能为空!");
        }
        if (irTemperatureReport.getTemperature()==null) {
            throw new MessageException("体温不能为空!");
        }
        if (irTemperatureReport.getOtherSymptom()==null) {
            throw new MessageException("有无其他症状不能为空!");
        }
        if (mode == 0 && irTemperatureReport.getContact() == null) {
            throw new MessageException("有无接触过重点疫情区人员不能为空!");
        }
        if (mode == 0 && irTemperatureReport.getArriveOtherArea()==null) {
            throw new MessageException("15天内有无去过其他区域不能为空!");
        }else {
            //到过的区域
            if (mode == 0 && irTemperatureReport.getArriveOtherArea()!= ArriveOtherArea.NO.getCode() && CollectionUtils.isEmpty(irTemperatureReport.getUserArriveAreaList())) {
                throw new MessageException("请填写15天内到过的其他区域");
            }
        }
        if (irTemperatureReport.getIdentity() == null) {
            throw new MessageException("身份信息不能为空!");
        }else {
            if (irTemperatureReport.getIdentity() == 0) {
                if (StringUtils.isBlank(irTemperatureReport.getOfficeId()) || StringUtils.isBlank(irTemperatureReport.getOfficeName())) {
                    throw new MessageException("班级信息不能为空");
                }
            }else {
                if (StringUtils.isBlank(irTemperatureReport.getMobile())) {
                    throw new MessageException("手机号不能为空");
                }
                if (!AccountValidatorUtil.isMobile(irTemperatureReport.getMobile())) {
                    throw new MessageException("手机号格式不正确");
                }
            }
        }
    }
}
