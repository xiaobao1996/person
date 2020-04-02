package cn.hy.infoReport.module.business.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.hy.infoReport.common.entity.*;
import cn.hy.infoReport.common.enums.CommonLogicalStatus;
import cn.hy.infoReport.common.enums.IrHealthMonitorHealthStatus;
import cn.hy.infoReport.common.enums.IrMsgLogOpeType;
import cn.hy.infoReport.common.enums.IrMsgLogTimeArea;
import cn.hy.infoReport.common.mapper.IrHealthMonitorMapper;
import cn.hy.infoReport.common.mapper.IrMsgLogMapper;
import cn.hy.infoReport.common.params.PageParams;
import cn.hy.infoReport.common.service.*;
import cn.hy.infoReport.common.utils.DateUtils;
import cn.hy.infoReport.common.utils.HttpUtils;
import cn.hy.infoReport.common.utils.ValidatorUtils;
import cn.hy.infoReport.common.vo.PageInfo;
import cn.hy.infoReport.common.vo.Result;
import cn.hy.infoReport.module.business.utils.HealthMonitorUtils;
import cn.hy.infoReport.module.business.vo.*;
import cn.hy.pms.thrift.utils.ThriftUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 健康监测controller
 */
@RequestMapping("/busi/healthMonitor")
@RestController
@Slf4j
public class IrHealthMonitorController {

    @Autowired
    private IrHealthMonitorService irHealthMonitorService;
    @Autowired
    private IrMonitorHistoryService irMonitorHistoryService;
    @Autowired
    private IrHealthMonitorMapper irHealthMonitorMapper;
    @Autowired
    private PmsStaffService pmsStaffService;
    @Autowired
    private PmsStudentService pmsStudentService;
    @Autowired
    private IrReportConfigService irReportConfigService;
    @Autowired
    private IrMsgLogMapper irMsgLogMapper;
    @Autowired
    private HealthMonitorUtils healthMonitorUtils;


    /**
     * 学生检测记录
     * @return
     */
    @RequestMapping("/studentList")
    public Result studentList(@RequestParam("schoolToken") String schoolId, PageParams pageParams, @RequestParam Date monitorDate, String name,
                              String classId, Byte healthStatus, Byte checkStatus) {
        monitorDate = DateUtils.getDayStart(monitorDate);
        PageInfo<StudentHealthMonitorVo> healthMonitorVoPageInfo = irHealthMonitorService.findStudentHealthMonitorPageBy(pageParams, schoolId, monitorDate, name, classId, healthStatus, checkStatus);
        if (!CollectionUtils.isEmpty(healthMonitorVoPageInfo.getList())) {
            for (StudentHealthMonitorVo hmvTmp : healthMonitorVoPageInfo.getList()) {
                if (hmvTmp.getMonitorDate() == null) {
                    hmvTmp.setMonitorDate(monitorDate);
                }
            }
        }
        return Result.success(healthMonitorVoPageInfo);
    }

    /**
     * 获取学生统计信息
     * @param schoolId
     * @param monitorDate
     * @return
     */
    @RequestMapping("/studentCalc")
    public Result studentCalc(@RequestParam("schoolToken") String schoolId, @RequestParam Date monitorDate) {
        monitorDate = DateUtils.getDayStart(monitorDate);
        CalcVo calcVo = irHealthMonitorService.studentCalc(schoolId, monitorDate);
        return Result.success(calcVo);
    }

    /**
     * 教职工检测记录
     * @return
     */
    @RequestMapping("/staffList")
    public Result staffList(@RequestParam("schoolToken") String schoolId, PageParams pageParams, @RequestParam Date monitorDate, String name,
                              String mobile, Byte healthStatus, Byte checkStatus) {
        monitorDate = DateUtils.getDayStart(monitorDate);
        PageInfo<StaffHealthMonitorVo> healthMonitorVoPageInfo = irHealthMonitorService.findStaffHealthMonitorPageBy(pageParams, schoolId, monitorDate, name, mobile, healthStatus, checkStatus);
        if (!CollectionUtils.isEmpty(healthMonitorVoPageInfo.getList())) {
            for (StaffHealthMonitorVo hmvTmp : healthMonitorVoPageInfo.getList()) {
                if (hmvTmp.getMonitorDate() == null) {
                    hmvTmp.setMonitorDate(monitorDate);
                }
            }
        }
        return Result.success(healthMonitorVoPageInfo);
    }

    /**
     * 获取学生统计信息
     * @param schoolId
     * @param monitorDate
     * @return
     */
    @RequestMapping("/staffCalc")
    public Result staffCalc(@RequestParam("schoolToken") String schoolId, @RequestParam Date monitorDate) {
        monitorDate = DateUtils.getDayStart(monitorDate);
        CalcVo calcVo = irHealthMonitorService.staffCalc(schoolId, monitorDate);
        return Result.success(calcVo);
    }


    /**
     * 添加测温记录（学生/教职工）
//     * @param type 0学生 1教职工
     * @return
     */
    @RequestMapping("/saveMonitorHistory")
    public Result save(@RequestParam("schoolToken") String schoolId, @RequestParam Byte type, @RequestParam String paramsStr) {
        if (StringUtils.isBlank(paramsStr)) {
            return Result.success(null);
        }
        List<HumanHealthCheckVo> healthCheckVoList = JSONObject.parseArray(paramsStr, HumanHealthCheckVo.class);
        for (HumanHealthCheckVo checkVo : healthCheckVoList) {
            ValidatorUtils.validateEntity(checkVo);
        }
        //校验用户是否是学校用户
        List<String> userIdList = healthCheckVoList.stream().map(HumanHealthCheckVo::getUserId).collect(Collectors.toList());
        boolean hasNotSchoolUser = false;
        if (type == 0) {
            //学生
            List<PmsStudent> studentList = pmsStudentService.findBySchoolIdAndUserIdIn(schoolId, userIdList);
            if (CollectionUtils.isEmpty(studentList)) {
                hasNotSchoolUser = true;
            } else {
                for (HumanHealthCheckVo checkVo : healthCheckVoList) {
                    boolean found = false;
                    for (PmsStudent sTmp : studentList) {
                        if (checkVo.getUserId().equals(sTmp.getUserId())) {
                            checkVo.setPmsStudent(sTmp);
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        hasNotSchoolUser = true;
                        break;
                    }
                }
            }
        } else if (type == 1) {
            //教职工
            List<PmsStaff> staffList = pmsStaffService.findBySchoolIdAndUserIdIn(schoolId, userIdList);
            if (CollectionUtils.isEmpty(staffList)) {
                hasNotSchoolUser = true;
            } else {
                for (HumanHealthCheckVo checkVo : healthCheckVoList) {
                    boolean found = false;
                    for (PmsStaff sTmp : staffList) {
                        if (checkVo.getUserId().equals(sTmp.getUserId())) {
                            found = true;
                            checkVo.setPmsStaff(sTmp);
                            break;
                        }
                    }

                    if (!found) {
                        hasNotSchoolUser = true;
                        break;
                    }
                }
            }
        } else {
            return Result.error("类型参数异常", null);
        }

        if (hasNotSchoolUser) {
            return Result.error("参数异常，存在非本校用户", null);
        }

        irHealthMonitorService.batchSaveHealthMonitorHistory(schoolId, type, healthCheckVoList);

        return Result.success(null);
    }


    /**
     * 获取用户测温记录
     * @return
     */
    @RequestMapping("/monitorHistory")
    public Result monitorHistory(@RequestParam("schoolToken") String schoolId, @RequestParam Date monitorDate, @RequestParam String userId) {
        monitorDate = DateUtils.getDayStart(monitorDate);
        List<IrMonitorHistory> historyList = irMonitorHistoryService.findBySchoolIdAndMonitorDateAndUserId(schoolId, monitorDate, userId);
        if (!CollectionUtils.isEmpty(historyList)) {
            List<String> monitorUserIdList = historyList.stream().map(IrMonitorHistory::getMonitorUserId).distinct().collect(Collectors.toList());
            List<PmsStaff> psList = pmsStaffService.findBySchoolIdAndUserIdIn(schoolId, monitorUserIdList);
            if (!CollectionUtils.isEmpty(psList)) {
                for (IrMonitorHistory hTmp : historyList) {
                    for (PmsStaff psTmp : psList) {
                        if (hTmp.getMonitorUserId().equals(psTmp.getUserId())) {
                            hTmp.setMonitorUserName(psTmp.getName());
                            break;
                        }
                    }
                }
            }
        }
        return Result.success(historyList);
    }


    /**
     * 学生健康监测数据导出
     * @param request
     * @param response
     * @param schoolId
     * @param startDate
     * @param endDate
     */
    @RequestMapping("/studentExport")
    public void studentExport(HttpServletRequest request, HttpServletResponse response, @RequestParam("schoolToken") String schoolId, Date startDate, Date endDate) {
        List<ExcelExportEntity> exportEntityList = new ArrayList<>();
        exportEntityList.add(new ExcelExportEntity("日期", "monitorDate", 16));
        exportEntityList.add(new ExcelExportEntity("姓名", "realName", 16));
        exportEntityList.add(new ExcelExportEntity("班级", "className", 16));
        exportEntityList.add(new ExcelExportEntity("上午体温", "amTemperature", 16));
        exportEntityList.add(new ExcelExportEntity("人工复测（上午）", "amRecheck", 16));
        exportEntityList.add(new ExcelExportEntity("下午体温", "pmTemperature", 16));
        exportEntityList.add(new ExcelExportEntity("人工复测（下午）", "pmRecheck", 16));

        List<Map<String, String>> monitorMapList = new ArrayList<>(100000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        while (calendar.getTime().compareTo(endDate) <= 0) {
            Date monitorDate = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
            List<StudentHealthMonitorVo> monitorVoList = irHealthMonitorMapper.findStudentHealthMonitorBy(schoolId, monitorDate, null, null, null, null);
            if (!CollectionUtils.isEmpty(monitorVoList)) {
                for (StudentHealthMonitorVo mvTmp : monitorVoList) {
                    Map<String, String> item = new HashMap<>(14);
                    item.put("monitorDate", dateFormat.format(Optional.ofNullable(mvTmp.getMonitorDate()).orElse(monitorDate)));
                    item.put("realName", mvTmp.getName());
                    item.put("className", mvTmp.getClassName());
                    item.put("amTemperature", mvTmp.getAmTemperature() == null ? "" : mvTmp.getAmTemperature().stripTrailingZeros().toPlainString());
                    item.put("amRecheck", mvTmp.getAmRecheck() != null && mvTmp.getAmRecheck() == CommonLogicalStatus.YES.getCode() ? "是" : "否");
                    item.put("pmTemperature", mvTmp.getPmTemperature() == null ? "" : mvTmp.getPmTemperature().stripTrailingZeros().toPlainString());
                    item.put("pmRecheck", mvTmp.getPmRecheck() != null && mvTmp.getPmRecheck() == CommonLogicalStatus.YES.getCode() ? "是" : "否");
                    monitorMapList.add(item);
                }
            }
        }


        Workbook sheets = ExcelExportUtil.exportExcel(new ExportParams(null, null, ExcelType.XSSF),
                exportEntityList, monitorMapList);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=" + HttpUtils.getEncodeFileName(request, "学生健康监测数据.xlsx"));
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            sheets.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 学生健康监测数据导出
     * @param request
     * @param response
     * @param schoolId
     * @param startDate
     * @param endDate
     */
    @RequestMapping("/staffExport")
    public void staffExport(HttpServletRequest request, HttpServletResponse response, @RequestParam("schoolToken") String schoolId, Date startDate, Date endDate) {
        List<ExcelExportEntity> exportEntityList = new ArrayList<>();
        exportEntityList.add(new ExcelExportEntity("日期", "monitorDate", 16));
        exportEntityList.add(new ExcelExportEntity("姓名", "realName", 16));
        exportEntityList.add(new ExcelExportEntity("手机", "mobile", 16));
        exportEntityList.add(new ExcelExportEntity("上午体温", "amTemperature", 16));
        exportEntityList.add(new ExcelExportEntity("人工复测（上午）", "amRecheck", 16));
        exportEntityList.add(new ExcelExportEntity("下午体温", "pmTemperature", 16));
        exportEntityList.add(new ExcelExportEntity("人工复测（下午）", "pmRecheck", 16));

        List<Map<String, String>> monitorMapList = new ArrayList<>(100000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        while (calendar.getTime().compareTo(endDate) <= 0) {
            Date monitorDate = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
            List<StaffHealthMonitorVo> monitorVoList = irHealthMonitorMapper.findStaffHealthMonitorBy(schoolId, monitorDate, null, null, null, null);
            if (!CollectionUtils.isEmpty(monitorVoList)) {
                for (StaffHealthMonitorVo mvTmp : monitorVoList) {
                    Map<String, String> item = new HashMap<>(14);
                    item.put("monitorDate", dateFormat.format(Optional.ofNullable(mvTmp.getMonitorDate()).orElse(monitorDate)));
                    item.put("realName", mvTmp.getName());
                    item.put("mobile", mvTmp.getMobile());
                    item.put("amTemperature", mvTmp.getAmTemperature() == null ? "" : mvTmp.getAmTemperature().stripTrailingZeros().toPlainString());
                    item.put("amRecheck", mvTmp.getAmRecheck() != null && mvTmp.getAmRecheck() == CommonLogicalStatus.YES.getCode() ? "是" : "否");
                    item.put("pmTemperature", mvTmp.getPmTemperature() == null ? "" : mvTmp.getPmTemperature().stripTrailingZeros().toPlainString());
                    item.put("pmRecheck", mvTmp.getPmRecheck() != null && mvTmp.getPmRecheck() == CommonLogicalStatus.YES.getCode() ? "是" : "否");
                    monitorMapList.add(item);
                }
            }
        }


        Workbook sheets = ExcelExportUtil.exportExcel(new ExportParams(null, null, ExcelType.XSSF),
                exportEntityList, monitorMapList);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=" + HttpUtils.getEncodeFileName(request, "教职工健康监测数据.xlsx"));
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            sheets.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取场所数据
     * @return
     */
    @RequestMapping("/placeList")
    public Result placeList(@RequestParam("schoolToken") String schoolId) throws TException {
        List<BusiPlace> placeList = ThriftUtils.findPlaceBySchoolIdAndJsonParams(schoolId, null);
        return Result.success(placeList);
    }

    /**
     * 通知用户(手动触发)
     * @param type 0学生 1教师
     * @return
     */
    @RequestMapping("/notify")
    public Result notifyUser(@RequestParam("schoolToken") String schoolId, @RequestParam String userId, @RequestParam Byte type, @RequestParam Date monitorDate) throws TException {
        monitorDate = DateUtils.getDayStart(monitorDate);
        IrHealthMonitor curMonitor = irHealthMonitorService.findBySchoolIdAndUserIdAndMonitorDate(schoolId, userId, monitorDate);
        if (curMonitor == null || curMonitor.getHealthStatus() == null
                || (curMonitor.getHealthStatus() != IrHealthMonitorHealthStatus.ABNORMAL.getCode() && curMonitor.getHealthStatus() != IrHealthMonitorHealthStatus.NEED_CONFIRM.getCode())) {
            return Result.error("该用户体温非异常，无需发送", null);
        }
        PmsStudent curPmsStudent = null;
        PmsStaff curPmsStaff = null;
        if (type == 0) {
            curPmsStudent = pmsStudentService.findBySchoolIdAndUserId(schoolId, userId);
            if (curPmsStudent == null) {
                return Result.error("当前用户不存在！", null);
            }
        } else if (type == 1) {
            curPmsStaff = pmsStaffService.findBySchoolIdAndUserId(schoolId, userId);
            if (curPmsStaff == null) {
                return Result.error("当前用户不存在！", null);
            }

        } else {
            return Result.error("类型参数异常", null);
        }

        ReportDetailVo detailVo = healthMonitorUtils.sendReportMsg(schoolId, userId, type, monitorDate,
                curMonitor, curPmsStudent, curPmsStaff, false, IrMsgLogOpeType.PERSON_OPE, DateUtils.isAm(new Date()) ? IrMsgLogTimeArea.AM : IrMsgLogTimeArea.PM);

        return Result.success(detailVo);
    }

    public static void main(String[] args) {
        Date now = new Date();
//        DateUtils.getDayStart()
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
    }
}
