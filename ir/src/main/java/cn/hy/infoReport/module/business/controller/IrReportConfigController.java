package cn.hy.infoReport.module.business.controller;

import cn.hy.infoReport.common.busiConstant.InnerIdentify;
import cn.hy.infoReport.common.entity.IrReportConfig;
import cn.hy.infoReport.common.entity.PmsStaff;
import cn.hy.infoReport.common.entity.SysRole;
import cn.hy.infoReport.common.service.IrReportConfigService;
import cn.hy.infoReport.common.service.PmsStaffService;
import cn.hy.infoReport.common.vo.Result;
import cn.hy.infoReport.module.business.utils.HealthMonitorUtils;
import cn.hy.infoReport.module.business.vo.IrReportConfigItemVo;
import cn.hy.infoReport.module.business.vo.ReportDetailVo;
import cn.hy.infoReport.module.business.vo.ReportUserCountVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 通知配置controller
 */
@RequestMapping("/busi/reportConfig")
@RestController
public class IrReportConfigController {

    @Autowired
    private IrReportConfigService irReportConfigService;
    @Autowired
    private PmsStaffService pmsStaffService;


    /**
     * 获取身份列表(学生)
     * @return
     */
    @RequestMapping("/identifyList")
    public Result identifyList(@RequestParam("schoolToken") String schoolId) throws TException {
        List<IrReportConfigItemVo> configItemVoList = new ArrayList<>(4);

        for (InnerIdentify idTmp : InnerIdentify.values()) {
            configItemVoList.add(new IrReportConfigItemVo(idTmp.getMsg(), idTmp.getCode()));
        }

        return Result.success(configItemVoList);
    }


    /**
     * 获取配置详情
     * @return
     */
    @RequestMapping("/detail")
    public Result detail(@RequestParam("schoolToken") String schoolId, @RequestParam Byte type) throws TException {
        if (type != 0 && type != 1) {
            return Result.error("类型参数异常", null);
        }

        ReportDetailVo detailVo = new ReportDetailVo();
        IrReportConfig config = irReportConfigService.findWithInitBySchoolId(schoolId);
        List<PmsStaff> staffList = pmsStaffService.findBySchoolId(schoolId);
        List<SysRole> jobTypeList = new ArrayList<>(4);

        if (type == 0) {
            //学生
            for (InnerIdentify idTmp : InnerIdentify.values()) {
                SysRole sr = new SysRole();
                sr.setId(idTmp.getCode());
                sr.setRoleName(idTmp.getMsg());
                jobTypeList.add(sr);
            }
            detailVo.fromIdentify(config.getStudentReportIdentify(), jobTypeList);

        }

        if (!CollectionUtils.isEmpty(staffList)) {
            HealthMonitorUtils.buildStaffJobType(schoolId, staffList);
            detailVo.fromPerson(type == 0 ? config.getStudentReportUserIds() : config.getStaffReportUserIds(), staffList);
        }

        return Result.success(detailVo);
    }

    /**
     * 获取配置人员数量统计
     * @return
     */
    @RequestMapping("/countDetail")
    public Result countDetail(@RequestParam("schoolToken") String schoolId) {
        ReportUserCountVo countVo = new ReportUserCountVo();
        IrReportConfig config = irReportConfigService.findWithInitBySchoolId(schoolId);
        if (StringUtils.isBlank(config.getStudentReportUserIds()) && StringUtils.isBlank(config.getStaffReportUserIds())) {
            return Result.success(countVo);
        }
        List<PmsStaff> staffList = pmsStaffService.findBySchoolId(schoolId);
        if (CollectionUtils.isEmpty(staffList)) {
            return Result.success(countVo);
        }

        if (StringUtils.isNotBlank(config.getStudentReportUserIds())) {
            List<String> personIdList = Arrays.stream(config.getStudentReportUserIds().split(",")).distinct().collect(Collectors.toList());
            for (String piTmp : personIdList) {
                for (PmsStaff psTmp : staffList) {
                    if (psTmp.getUserId().equals(piTmp)) {
                        countVo.setStudentConfigUserCount(countVo.getStudentConfigUserCount() + 1);
                        break;
                    }
                }
            }
        }

        if (StringUtils.isNotBlank(config.getStaffReportUserIds())) {
            List<String> personIdList = Arrays.stream(config.getStaffReportUserIds().split(",")).distinct().collect(Collectors.toList());
            for (String piTmp : personIdList) {
                for (PmsStaff psTmp : staffList) {
                    if (psTmp.getUserId().equals(piTmp)) {
                        countVo.setStaffConfigUserCount(countVo.getStaffConfigUserCount() + 1);
                        break;
                    }
                }
            }
        }


        return Result.success(countVo);
    }

    /**
     * 保存配置
     * @param type 0学生 1教职工
     * @return
     */
    @RequestMapping("/save")
    public Result saveConfig(@RequestParam("schoolToken") String schoolId, @RequestParam Byte type, String[] identifyArray, String[] personArray) {
        if (type != 0 && type != 1) {
            return Result.error("类型参数异常", null);
        }
        List<String> identifyList = null, personList = null;
        if (identifyArray != null && identifyArray.length > 0) {
            identifyList = Arrays.asList(identifyArray);
        }

        if (personArray != null && personArray.length > 0) {
            personList = Arrays.asList(personArray);
        }
        irReportConfigService.save(schoolId, type, identifyList, personList);
        return Result.success(null);
    }

}
