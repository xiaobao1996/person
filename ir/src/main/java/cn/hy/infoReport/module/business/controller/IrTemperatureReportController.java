package cn.hy.infoReport.module.business.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.hy.infoReport.common.constant.ProjectConstant;
import cn.hy.infoReport.common.entity.*;
import cn.hy.infoReport.common.enums.IrConfigMode;
import cn.hy.infoReport.common.enums.IrTemperatureReportAbnormal;
import cn.hy.infoReport.common.enums.SysClassUserDetailType;
import cn.hy.infoReport.common.enums.SysRoleGroupType;
import cn.hy.infoReport.common.params.PageParams;
import cn.hy.infoReport.common.service.IrConfigService;
import cn.hy.infoReport.common.service.IrTemperatureReportService;
import cn.hy.infoReport.common.service.PmsClassService;
import cn.hy.infoReport.common.service.PmsStudentService;
import cn.hy.infoReport.common.utils.DateUtils;
import cn.hy.infoReport.common.utils.HttpUtils;
import cn.hy.infoReport.common.utils.UserUtils;
import cn.hy.infoReport.common.utils.ValidatorUtils;
import cn.hy.infoReport.common.vo.PageInfo;
import cn.hy.infoReport.common.vo.Result;
import cn.hy.infoReport.module.business.utils.VerificationUtils;
import cn.hy.infoReport.module.business.vo.*;
import cn.hy.pms.thrift.SysUserThrift;
import cn.hy.pms.thrift.utils.ThriftUtils;
import com.alibaba.fastjson.JSONObject;
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
 * @Author: JiuZhou
 * @Date: 2020/3/4 10:20
 * @Version 1.0
 */
@RestController
@RequestMapping("/busi/irTemperatureReport")
public class IrTemperatureReportController {

    @Autowired
    private IrTemperatureReportService irTemperatureReportService;
    @Autowired
    private IrConfigService irConfigService;
    @Autowired
    private PmsClassService pmsClassService;
    @Autowired
    private PmsStudentService pmsStudentService;

    /**
     * pc根据条件查询
     * @param schoolId
     * @param irTemperatureReport
     * @return
     */
    @RequestMapping("/list")
    public Result list(@RequestParam("schoolToken") String schoolId,PageParams pageParams, IrTemperatureReport irTemperatureReport) {
        ValidatorUtils.validateEntity(irTemperatureReport);
        if (irTemperatureReport.getIdentity() == null) {
            return Result.error("身份信息不能为空!");
        }
        PageInfo<IrTemperatureReport> pageInfo = irTemperatureReportService.list(pageParams, schoolId, irTemperatureReport);
        IrTemperatureReportListVo irTemperatureReportListVo = new IrTemperatureReportListVo();
        irTemperatureReportListVo.setPageInfo(pageInfo);
        irTemperatureReport.setAbnormal(IrTemperatureReportAbnormal.ABNORMAL.getCode());
        irTemperatureReportListVo.setUnusualNumber(irTemperatureReportService.findAbunusualCountByIdentityAndSchoolId(schoolId, irTemperatureReport));
        return Result.success(irTemperatureReportListVo);
    }

    /**
     * 获取学校的所有的在校班级
     * @param schoolId
     * @return
     */
    @RequestMapping("/findClass")
    public Result findClassBySchoolId(@RequestParam("schoolToken") String schoolId) throws TException{
        List<PmsClass> pmsClassList = pmsClassService.findClassBySchoolId(schoolId);
        return Result.success(pmsClassList);
    }

    /**
     * 根据家长id获取孩子信息
     * @param schoolId
     * @param parentId
     * @return
     */
    @RequestMapping("/findChild")
    public Result findChild(@RequestParam("schoolToken") String schoolId, @RequestParam String parentId) throws TException {
        List<String> parentIds = new ArrayList<>();
        parentIds.add(parentId);
        List<SysUser>  parentAndChilds = ThriftUtils.findChildBySchoolIdAndParentId(schoolId, parentIds);
        return Result.success(parentAndChilds);
    }

    /**
     * 根据学校id和用户id查询用户信息和用户所在的班级信息
     * @param schoolId
     * @param userId
     * @return
     * @throws TException
     */
    @RequestMapping("/findUserInfo")
    public Result findUserByUserId(@RequestParam("schoolToken") String schoolId,@RequestParam String userId) throws TException {
        SysUserThrift user = ThriftUtils.findUserByUserId(userId);
        if (user == null) {
            return Result.error("当前用户不存在!");
        }
        UserInfoVo userInfoVo = new UserInfoVo();
        //处理所在的机构
        PmsStudent pmsStudent = pmsStudentService.findBySchoolIdAndUserId(schoolId, userId);
        if (pmsStudent != null) {
            userInfoVo.setOfficeId(pmsStudent.getClassId());
            userInfoVo.setOfficeName(pmsStudent.getClassName());
        }
        userInfoVo.setUserId(user.getId());
        userInfoVo.setUserName(user.getRealName());
        userInfoVo.setMobile(user.getMobile());
        userInfoVo.setAvatar(user.getAvatar());
        return Result.success(userInfoVo);
    }

    /**
     * 保存用户信息
     *
     * @return
     */
    @RequestMapping("/save")
    public Result save(@RequestParam("schoolToken") String schoolId, IrTemperatureReport irTemperatureReport,String irUserArriveAreas) {
        List<IrUserArriveArea> list = JSONObject.parseArray(irUserArriveAreas, IrUserArriveArea.class);
        irTemperatureReport.setUserArriveAreaList(list);
        String opeUserId = UserUtils.getUserId();
        IrConfig config = irConfigService.findWithInitBySchoolId(schoolId);
        VerificationUtils.verificationIrTemperatureReport(irTemperatureReport,config.getMode());
        irTemperatureReportService.save(schoolId, opeUserId, irTemperatureReport);
        return Result.success(null);
    }

    /**
     * 获取学校信息
     * @param schoolId
     * @return
     * @throws TException
     */
    @RequestMapping("/schoolInfo")
    public Result getSchoolInfo(@RequestParam("schoolToken") String schoolId) throws TException{
        return Result.success(ThriftUtils.findSchoolBySchoolId(schoolId));
    }

    /**
     * 导出表格
     */
    @RequestMapping("export")
    public void export(HttpServletRequest request, HttpServletResponse response, @RequestParam("schoolToken") String schoolId, IrTemperatureReport irTemperatureReport) {
        PageParams pageParams = new PageParams();
        pageParams.setPageSize(Integer.MAX_VALUE);
        PageInfo<IrTemperatureReport> pageInfo = irTemperatureReportService.list(pageParams, schoolId, irTemperatureReport);
        IrConfig config = irConfigService.findWithInitBySchoolId(schoolId);
        Byte mode = config.getMode();
        List<ExcelExportEntity> exportEntityList = new ArrayList<>();
        exportEntityList.add(new ExcelExportEntity("填报时间", "time", 16));
        if (irTemperatureReport.getIdentity() == 0) {
            exportEntityList.add(new ExcelExportEntity("学生姓名", "userName", 16));
            exportEntityList.add(new ExcelExportEntity("所在班级", "officeName", 16));
        }else {
            exportEntityList.add(new ExcelExportEntity("教职工姓名", "userName", 16));
            exportEntityList.add(new ExcelExportEntity("手机号", "mobile", 16));
        }
        exportEntityList.add(new ExcelExportEntity("所在学校", "schoolName", 16));
        if (mode == IrConfigMode.HOLIDAY.getCode()) {
            exportEntityList.add(new ExcelExportEntity("当前所在地", "currentLocation", 16));
        }
        exportEntityList.add(new ExcelExportEntity("体温是否异常", "temperatureUnusual", 16));
        exportEntityList.add(new ExcelExportEntity("体温", "temperature", 16));
        exportEntityList.add(new ExcelExportEntity("其他症状", "otherSymptom", 16));
        if (mode == IrConfigMode.HOLIDAY.getCode()) {
            exportEntityList.add(new ExcelExportEntity("有无和重点疫情人员接触", "contact", 16));
            exportEntityList.add(new ExcelExportEntity("15天内有无去过其他区域", "arriveOtherArea", 16));
            exportEntityList.add(new ExcelExportEntity("15天内曾到过的区域", "arriveArea", 16));
        }
        exportEntityList.add(new ExcelExportEntity("其他信息", "otherInfo", 16));
        List<IrTemperatureReport> list = pageInfo.getList();
        List<Map<String, Object>> exportDataList = new ArrayList<>(CollectionUtils.isEmpty(list) ? 2 : list.size() * 2);
        SysOffice sysOffice = null;
        try {
            sysOffice = ThriftUtils.findSchoolBySchoolId(schoolId);
        } catch (TException e) {
            e.printStackTrace();
        }
        if (!CollectionUtils.isEmpty(list)) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
            for (IrTemperatureReport temperatureReport : list) {
                Map<String, Object> data = new HashMap<>();
                data.put("time", dateFormat.format(temperatureReport.getCreateAt()));
                data.put("userName", temperatureReport.getUserName());
                data.put("schoolName", sysOffice.getOfficeName());
                data.put("mobile", temperatureReport.getMobile());
                data.put("officeName", temperatureReport.getOfficeName());
                if (mode == IrConfigMode.HOLIDAY.getCode()) {
                    data.put("currentLocation", temperatureReport.getCurrentLocation() == null ? "" : temperatureReport.getCurrentLocation());
                    data.put("contact", temperatureReport.getContact() == null ? "" : temperatureReport.getContact() == 0 ? "无" : "有");
                    data.put("arriveOtherArea", temperatureReport.getArriveOtherArea() == null ? "" : temperatureReport.getArriveOtherArea() == 0 ? "无" : "有");
                }
                data.put("otherSymptom", temperatureReport.getOtherSymptom() == 0 ? "无" : "有咳嗽、呼吸不顺畅等现象");
                data.put("temperatureUnusual", temperatureReport.getTemperatureUnusual() ? "是" : "否");
                data.put("temperature", temperatureReport.getTemperature()+"℃");
                if (!CollectionUtils.isEmpty(temperatureReport.getUserArriveAreaList())) {
                    String result = "";
                    for (IrUserArriveArea irUserArriveArea : temperatureReport.getUserArriveAreaList()) {
                        result = result + irUserArriveArea.getProvince() + "  " + irUserArriveArea.getCity() + "  " + irUserArriveArea.getArea() + " \n";
                    }
                    data.put("arriveArea", result);
                }
                data.put("otherInfo", temperatureReport.getOtherInfo());
                exportDataList.add(data);
            }
        }
        Workbook sheets = ExcelExportUtil.exportExcel(new ExportParams(null, null, ExcelType.XSSF),
                exportEntityList, exportDataList);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=" + HttpUtils.getEncodeFileName(request, "上报记录.xlsx"));
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            sheets.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取多个用户今天上报
     * @return
     */
    @RequestMapping("/usersTodayReports")
    public Result usersTodayReports(@RequestParam String schoolId, String[] userIdArray) {
        if (userIdArray == null || userIdArray.length == 0) {
            return Result.success(null);
        }
        List<String> userIdList = Arrays.asList(userIdArray);

        List<IrTemperatureReport> reportedList = irTemperatureReportService.findValidBySchoolIdAndUserIdListAndDataTimeBetween(schoolId, userIdList, DateUtils.getTodayStart(), DateUtils.getTodayEnd());
        return Result.success(reportedList);
    }

    /**
     * 班主任对应班级上报统计
     * @return
     */
    @RequestMapping("/classReportCalc")
    public Result classReportCalc(@RequestParam String schoolId) throws TException {
        String userId = UserUtils.getUserId();
        List<SysClassUserDetail> scudList = ThriftUtils.findClassUserDetailBySchoolIdAndUserIdAndOtherParams(schoolId, userId, String.valueOf(SysClassUserDetailType.HEAD_TEACHER.getCode()));
        if (CollectionUtils.isEmpty(scudList)) {
            return Result.success(null);
        }

        List<String> classIdList = scudList.stream().map(SysClassUserDetail::getClassId).collect(Collectors.toList());
        List<SysOffice> soList = ThriftUtils.findOfficeBySchoolIdAndJsonParams(schoolId, JSONObject.toJSONString(Collections.singletonMap("officeIdList", classIdList)));

        List<ClassReportVo> classReportVoList = new ArrayList<>(scudList.size() * 2);
        Map<String, Object> params = new HashMap<>(4);
        params.put("groupType", SysRoleGroupType.STUDENT.getCode());
        for (SysClassUserDetail scudTmp : scudList) {
            ClassReportVo classReportVo = new ClassReportVo();
            classReportVoList.add(classReportVo);
            classReportVo.setClassId(scudTmp.getClassId());
            for (SysOffice soTmp : soList) {
                if (soTmp.getId().equals(scudTmp.getClassId())) {
                    classReportVo.setClassName(soTmp.getOfficeName());
                    break;
                }
            }
            params.put("officeId", scudTmp.getClassId());
            PageInfo<SysUser> suPageInfo = ThriftUtils.findPageUserWithJsonParams(1, 5000, schoolId, JSONObject.toJSONString(params));
            List<SysUser> suList = suPageInfo.getList();
            if (!CollectionUtils.isEmpty(suList)) {
                classReportVo.setTotalCount((long) suList.size());
                List<String> userIdList = suList.stream().map(SysUser::getId).collect(Collectors.toList());
                List<IrTemperatureReport> reportedList = irTemperatureReportService.findValidBySchoolIdAndUserIdListAndDataTimeBetween(schoolId, userIdList, DateUtils.getTodayStart(), DateUtils.getTodayEnd());

                classReportVo.setNotReportNameList(new ArrayList<>(suList.size() * 2));
                if (CollectionUtils.isEmpty(reportedList)) {
                    for (SysUser suTmp : suList) {
                        classReportVo.getNotReportNameList().add(suTmp.getRealName());
                    }
                } else {
                    for (SysUser suTmp : suList) {
                        boolean found = false;
                        for (IrTemperatureReport trTmp : reportedList) {
                            if (suTmp.getId().equals(trTmp.getUserId())) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            classReportVo.getNotReportNameList().add(suTmp.getRealName());
                        }
                    }
                    classReportVo.setReportedCount(classReportVo.getTotalCount() - classReportVo.getNotReportNameList().size());
                }
            }


        }
        return Result.success(classReportVoList);
    }


    /**
     * 学校统计
     * @return
     */
    @RequestMapping("/schoolCalc")
    public Result schoolCalc(@RequestParam String schoolId) throws TException {
        Map<String, Object> result = new HashMap<>(4);
        //处理教职工
        PageInfo<SysUser> staffPageInfo = ThriftUtils.findPageUserWithJsonParams(1, 5000, schoolId, JSONObject.toJSONString(Collections.singletonMap("groupTypeList", ProjectConstant.staffGroupTypeSet)));
        List<SysUser> staffList = staffPageInfo.getList();
        StaffReportVo staffReportVo = new StaffReportVo();
        if (!CollectionUtils.isEmpty(staffList)) {
            staffReportVo.setTotalCount((long) staffList.size());
            staffReportVo.setNotReportNameList(new ArrayList<>(staffList.size() * 2));
            List<String> staffIdList = staffList.stream().map(SysUser::getId).collect(Collectors.toList());
            List<IrTemperatureReport> trList = irTemperatureReportService.findValidBySchoolIdAndUserIdListAndDataTimeBetween(schoolId, staffIdList, DateUtils.getTodayStart(), DateUtils.getTodayEnd());

            for (SysUser suTmp : staffList) {
                boolean found = false;
                for (IrTemperatureReport trTmp : trList) {
                    if (suTmp.getId().equals(trTmp.getUserId())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    staffReportVo.getNotReportNameList().add(suTmp.getRealName());
                }
            }

            staffReportVo.setReportedCount(staffReportVo.getTotalCount() - staffReportVo.getNotReportNameList().size());


        }
        result.put("staffCalc", staffReportVo);

        //处理学生
        StudentReportVo studentReportVo = new StudentReportVo();

        List<String> studentIdList = ThriftUtils.findUserIdListWithJsonParams(schoolId, JSONObject.toJSONString(Collections.singletonMap("groupType", SysRoleGroupType.STUDENT.getCode())));
        if (!CollectionUtils.isEmpty(studentIdList)) {
            studentReportVo.setTotalCount((long) studentIdList.size());
            List<IrTemperatureReport> trList = irTemperatureReportService.findValidBySchoolIdAndUserIdListAndDataTimeBetween(schoolId, studentIdList, DateUtils.getTodayStart(), DateUtils.getTodayEnd());
            int notReportCount = 0;
            for (String sid : studentIdList) {
                boolean found = false;
                for (IrTemperatureReport trTmp : trList) {
                    if (sid.equals(trTmp.getUserId())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    notReportCount ++;
                }
            }
            studentReportVo.setReportedCount(studentReportVo.getTotalCount() - notReportCount);

        }

        result.put("studentCalc", studentReportVo);
        return Result.success(result);
    }

    /**
     * 所有班级上报统计
     * @return
     */
    @RequestMapping("/allClassReportCalc")
    public Result allClassReportCalc(@RequestParam String schoolId) throws TException {

        List<OfficeUserIdsVo> officeUserIdsVoList = ThriftUtils.findClassStudentUserIdsBySchoolId(schoolId);
        if (CollectionUtils.isEmpty(officeUserIdsVoList)) {
            return Result.success(null);
        }


        Set<String> userIdSet = new HashSet<>(4000);
        for (OfficeUserIdsVo ouTmp : officeUserIdsVoList) {
            if (!CollectionUtils.isEmpty(ouTmp.getUserIdList())) {
                userIdSet.addAll(ouTmp.getUserIdList());
            }
        }


        List<IrTemperatureReport> trList = irTemperatureReportService.findValidBySchoolIdAndUserIdListAndDataTimeBetween(schoolId, new ArrayList<>(userIdSet), DateUtils.getTodayStart(), DateUtils.getTodayEnd());
        List<ClassReportVo> classReportVoList = new ArrayList<>(officeUserIdsVoList.size() * 2);

        for (OfficeUserIdsVo ouTmp : officeUserIdsVoList) {
            ClassReportVo classReportVo = new ClassReportVo();
            classReportVoList.add(classReportVo);

            classReportVo.setClassName(ouTmp.getOfficeName());
            classReportVo.setGradeName(ouTmp.getGradeName());
            classReportVo.setClassCode(ouTmp.getClassCode());
            classReportVo.setClassId(ouTmp.getOfficeId());
            classReportVo.setOfficeName(ouTmp.getOfficeName());
            classReportVo.setDistanceYear(ouTmp.getDistanceYear());
            classReportVo.setTotalCount((long) (CollectionUtils.isEmpty(ouTmp.getUserIdList()) ? 0 : ouTmp.getUserIdList().size()));

            if (!CollectionUtils.isEmpty(trList) && !CollectionUtils.isEmpty(ouTmp.getUserIdList())) {
                int notReportCount = 0;
                for (String uid : ouTmp.getUserIdList()) {
                    boolean found = false;
                    for (IrTemperatureReport trTmp : trList) {
                        if (uid.equals(trTmp.getUserId())) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        notReportCount ++;
                    }
                }
                classReportVo.setReportedCount(classReportVo.getTotalCount() - notReportCount);
            } else {
                classReportVo.setReportedCount((long) 0);
            }

        }

        return Result.success(classReportVoList);

    }

}
