package cn.hy.infoReport.module.business.controller;

import cn.hy.infoReport.common.constant.ProjectConstant;
import cn.hy.infoReport.common.entity.*;
import cn.hy.infoReport.common.enums.SysClassUserDetailType;
import cn.hy.infoReport.common.enums.SysRoleGroupType;
import cn.hy.infoReport.common.service.IrConfigService;
import cn.hy.infoReport.common.service.IrTemperatureReportService;
import cn.hy.infoReport.common.service.PmsStaffService;
import cn.hy.infoReport.common.service.PmsStudentService;
import cn.hy.infoReport.common.utils.DateUtils;
import cn.hy.infoReport.common.utils.UserUtils;
import cn.hy.infoReport.common.vo.Result;
import cn.hy.infoReport.module.business.utils.HealthMonitorUtils;
import cn.hy.infoReport.module.business.vo.UserInfoVo;
import cn.hy.pms.thrift.SysMenuPermissionThrift;
import cn.hy.pms.thrift.SysRoleThrift;
import cn.hy.pms.thrift.SysUserThrift;
import cn.hy.pms.thrift.utils.ThriftUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户信息controller
 */
@RequestMapping("/busi/user")
@RestController
public class UserInfoController {

    @Autowired
    private IrTemperatureReportService irTemperatureReportService;
    @Autowired
    private PmsStudentService pmsStudentService;
    @Autowired
    private PmsStaffService pmsStaffService;
    @Autowired
    private IrConfigService irConfigService;

    /**
     * 获取用户身份信息
     * @return
     */
    @RequestMapping("/identify")
    public Result userIdentify(@RequestParam String schoolId) throws TException {
        String userId = UserUtils.getUserId();
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setUserId(userId);
        List<SysRoleThrift> roleList = ThriftUtils.findRoleByUserIdAndSchoolId(userId, schoolId);
        if (!CollectionUtils.isEmpty(roleList)) {
            for (SysRoleThrift srTmp : roleList) {
                if (srTmp.getGroupType() == SysRoleGroupType.STUDENT.getCode()) {
                    userInfoVo.setIsStudent(true);
                } else if (srTmp.getGroupType() == SysRoleGroupType.PARENTS.getCode()) {
                    userInfoVo.setIsParent(true);
                } if (ProjectConstant.staffGroupTypeSet.contains(srTmp.getGroupType())) {
                    userInfoVo.setIsStaff(true);
                }
            }
        }

        //判断是不是班主任
        try {
            List<SysClassUserDetail> scudList = ThriftUtils.findClassUserDetailBySchoolIdAndUserIdAndOtherParams(schoolId, userId, String.valueOf(SysClassUserDetailType.HEAD_TEACHER.getCode()));
            if (!CollectionUtils.isEmpty(scudList)) {
                List<String> classIdList = scudList.stream().map(SysClassUserDetail::getClassId).collect(Collectors.toList());
                List<SysOffice> soList = ThriftUtils.findOfficeBySchoolIdAndJsonParams(schoolId, JSONObject.toJSONString(Collections.singletonMap("officeIdList", classIdList)));
                if (!CollectionUtils.isEmpty(soList)) {
                    userInfoVo.setIsHeadTeacher(true);
                }
            }


        } catch (Exception e) {}


        try {
            List<SysMenuPermissionThrift> mpList = ThriftUtils.findMenuPermByUserIdAndSchoolIdAndAppCode(userId, schoolId, ProjectConstant.APP_CODE);
            if (!CollectionUtils.isEmpty(mpList)) {
                for (SysMenuPermissionThrift mpTmp : mpList) {
                    if (ProjectConstant.MANAGER_PERMISSION.equals(mpTmp.getPermission())) {
                        userInfoVo.setIsManager(true);
                        break;
                    }
                }

            }
        } catch (Exception e) {}

        IrConfig config = irConfigService.findWithInitBySchoolId(schoolId);
        if (config != null) {
            userInfoVo.setMode(config.getMode());
        }
        return Result.success(userInfoVo);
    }


    /**
     * 获取孩子列表
     * @return
     */
    @RequestMapping("/getReportUser")
    public Result getReportUser(@RequestParam String schoolId, @RequestParam String idTypes[]) throws TException {
        if (idTypes == null || idTypes.length == 0) {
            return Result.success(null);
        }
        Set<String> idTypeSet = Arrays.stream(idTypes).collect(Collectors.toSet());

        String userId = UserUtils.getUserId();
        List<SysUser> reportUserList = new ArrayList<>(4);

        if (idTypeSet.contains("staff")) {
            SysUserThrift sut = ThriftUtils.findUserByUserId(userId);
            if (sut != null) {
                SysUser sysUser = new SysUser();
                sysUser.setId(sut.getId());
                sysUser.setRealName(sut.getRealName());
                reportUserList.add(sysUser);
            }
        } else if (idTypeSet.contains("parent")) {
            List<SysUser> childList = ThriftUtils.findNormalUserChildByParentIdAndSchoolId(userId, schoolId);
            if (!CollectionUtils.isEmpty(childList)) {
                reportUserList.addAll(childList);
            }
        }

        if (!CollectionUtils.isEmpty(reportUserList)) {
            List<String> userIdList = reportUserList.stream().map(SysUser::getId).collect(Collectors.toList());
            List<IrTemperatureReport> trList = irTemperatureReportService.findValidBySchoolIdAndUserIdListAndDataTimeBetween(schoolId, userIdList, DateUtils.getTodayStart(), DateUtils.getTodayEnd());
            if (!CollectionUtils.isEmpty(trList)) {
//                List<IrUserArriveArea> areaList = irUserArriveAreaService.findByReportIds(trList.stream().map(IrTemperatureReport::getId).collect(Collectors.toList()));
//                if (!CollectionUtils.isEmpty(areaList)) {
//                    for (IrUserArriveArea areaTmp : areaList) {
//                        for (IrTemperatureReport trTmp : trList) {
//                            if (areaTmp.getReportId().equals(trTmp.getId())) {
//                                if (trTmp.getUserArriveAreaList() == null) {
//                                    trTmp.setUserArriveAreaList(new ArrayList<>(16));
//                                }
//
//                                trTmp.getUserArriveAreaList().add(areaTmp);
//                                break;
//                            }
//                        }
//
//                    }
//                }
                for (IrTemperatureReport trTmp : trList) {
                    for (SysUser suTmp : reportUserList) {
                        if (suTmp.getId().equals(trTmp.getUserId())) {
                            suTmp.setReported(true);
                            suTmp.setTemperatureReport(trTmp);
                            break;
                        }
                    }
                }
            }

        }

        return Result.success(reportUserList);
    }


    /**
     * 获取学生家长信息
     * @return
     */
    @RequestMapping("/parents")
    public Result getParents(@RequestParam("schoolToken") String schoolId, @RequestParam String userId) throws TException {
        List<SysUser> parentList = ThriftUtils.findParentBySchoolIdAndUserId(schoolId, userId);
        return Result.success(parentList);
    }


    /**
     * 获取学生列表
     * @return
     */
    @RequestMapping("/studentList")
    public Result studentList(@RequestParam("schoolToken") String schoolId) {
        List<PmsStudent> studentList = pmsStudentService.findWithSortBySchoolId(schoolId);
        return Result.success(studentList);
    }

    /**
     * 获取教职工列表
     * @param jobType 是否显示职务类型
     * @return
     */
    @RequestMapping("/staffList")
    public Result staffList(@RequestParam("schoolToken") String schoolId, Boolean jobType) throws TException {
        List<PmsStaff> staffList = pmsStaffService.findWithSortBySchoolId(schoolId);
        if (jobType != null && jobType && !CollectionUtils.isEmpty(staffList)) {
            HealthMonitorUtils.buildStaffJobType(schoolId, staffList);
        }
        return Result.success(staffList);
    }
}
