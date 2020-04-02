package cn.hy.infoReport.module.business.controller;

import cn.hy.infoReport.common.entity.PmsStaff;
import cn.hy.infoReport.common.entity.PmsStudent;
import cn.hy.infoReport.common.enums.IrHealthMonitorHealthStatus;
import cn.hy.infoReport.common.service.*;
import cn.hy.infoReport.common.utils.DateUtils;
import cn.hy.infoReport.common.vo.Result;
import cn.hy.infoReport.module.business.vo.DailyMonitorCalcVo;
import cn.hy.infoReport.module.business.vo.IrHealthMonitorSimplifyVo;
import cn.hy.infoReport.module.business.vo.IrTemperatureReportSimplifyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 监测统计controller
 */
@RequestMapping("/busi/monitorCalc")
@RestController
public class IrHealthMonitorCalcController {


    @Autowired
    private IrHealthMonitorService irHealthMonitorService;
    @Autowired
    private PmsStaffService pmsStaffService;
    @Autowired
    private IrTemperatureReportService irTemperatureReportService;
    @Autowired
    private IrConfigService irConfigService;
    @Autowired
    private PmsStudentService pmsStudentService;

    /**
     * 教职工统计信息
     * @return
     */
    @RequestMapping("/staff")
    public Result staffCalc(@RequestParam("schoolToken") String schoolId) {
        List<PmsStaff> staffList = pmsStaffService.findBySchoolId(schoolId);

        DailyMonitorCalcVo monitorCalcVo = new DailyMonitorCalcVo();
        monitorCalcVo.init14DayItemList();

        if (CollectionUtils.isEmpty(staffList)) {
            return Result.success(monitorCalcVo);
        }

        monitorCalcVo.setTotalCount((long) staffList.size());

        List<String> userIdList = staffList.stream().map(PmsStaff::getUserId).collect(Collectors.toList());

        buildCalcVo(schoolId, monitorCalcVo, userIdList);

        return Result.success(monitorCalcVo);
    }

    /**
     * 学生统计信息
     * @return
     */
    @RequestMapping("/student")
    public Result studentCalc(@RequestParam("schoolToken") String schoolId) {
        List<PmsStudent> studentList = pmsStudentService.findBySchoolId(schoolId);

        DailyMonitorCalcVo monitorCalcVo = new DailyMonitorCalcVo();
        monitorCalcVo.init14DayItemList();

        if (CollectionUtils.isEmpty(studentList)) {
            return Result.success(monitorCalcVo);
        }

        monitorCalcVo.setTotalCount((long) studentList.size());

        List<String> userIdList = studentList.stream().map(PmsStudent::getUserId).collect(Collectors.toList());

        buildCalcVo(schoolId, monitorCalcVo, userIdList);

        return Result.success(monitorCalcVo);
    }

    private void buildCalcVo(@RequestParam("schoolToken") String schoolId, DailyMonitorCalcVo monitorCalcVo, List<String> userIdList) {
        Date startDate = DateUtils.getBefore14DayStart(), endDate = DateUtils.getTodayEnd();

        //处理体温监测
        List<IrHealthMonitorSimplifyVo> healthMonitorList = irHealthMonitorService.findAbnormalSimplifyInfo(schoolId,
                                                        userIdList, startDate, endDate, IrHealthMonitorHealthStatus.ABNORMAL.getCode());
        if (!CollectionUtils.isEmpty(healthMonitorList)) {
            for (IrHealthMonitorSimplifyVo hmTmp : healthMonitorList) {
                for (DailyMonitorCalcVo.DailyMonitorCalcItemVo ciTmp : monitorCalcVo.getCalcItemList()) {
                    if (hmTmp.getMonitorDate().equals(ciTmp.getMonitorDate())) {
                        if (ciTmp.getAbnormalUserIdSet() == null) {
                            ciTmp.setAbnormalUserIdSet(new HashSet<>(500));
                        }
                        ciTmp.getAbnormalUserIdSet().add(hmTmp.getUserId());
                        break;
                    }
                }
            }
        }

//        IrConfig config = irConfigService.findWithInitBySchoolId(schoolId);
        //处理用户自己上报
        List<IrTemperatureReportSimplifyVo> trList = irTemperatureReportService.findAbnormalSimplifyInfo(schoolId, userIdList, startDate, endDate);
//        List<IrTemperatureReportSimplifyVo> trList = irTemperatureReportService.findNormalBySchoolIdAndUserIdListAndDataTimeBetweenAndAbnormal(schoolId, userIdList, startDate, endDate, CommonLogicalStatus.YES.getCode());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (!CollectionUtils.isEmpty(trList)) {
            for (IrTemperatureReportSimplifyVo trTmp : trList) {
                String dateStr = dateFormat.format(trTmp.getDataTime());
                for (DailyMonitorCalcVo.DailyMonitorCalcItemVo ciTmp : monitorCalcVo.getCalcItemList()) {
                    if (dateStr.equals(ciTmp.getMonitorDateStr())) {
                        if (ciTmp.getAbnormalUserIdSet() == null) {
                            ciTmp.setAbnormalUserIdSet(new HashSet<>(500));
                        }
                        ciTmp.getAbnormalUserIdSet().add(trTmp.getUserId());
                        break;
                    }
                }
            }
        }

        //重新构造vo
        monitorCalcVo.rebuildVo();
    }

}
