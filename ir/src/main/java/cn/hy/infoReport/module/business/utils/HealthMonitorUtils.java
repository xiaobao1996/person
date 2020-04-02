package cn.hy.infoReport.module.business.utils;

import cn.hy.infoReport.common.busiConstant.InnerIdentify;
import cn.hy.infoReport.common.constant.ProjectConstant;
import cn.hy.infoReport.common.entity.*;
import cn.hy.infoReport.common.enums.IrHealthMonitorHealthStatus;
import cn.hy.infoReport.common.enums.IrMsgLogOpeType;
import cn.hy.infoReport.common.enums.IrMsgLogTimeArea;
import cn.hy.infoReport.common.enums.SysClassUserDetailType;
import cn.hy.infoReport.common.mapper.IrMsgLogMapper;
import cn.hy.infoReport.common.service.IrReportConfigService;
import cn.hy.infoReport.common.service.PmsStaffService;
import cn.hy.infoReport.common.utils.HttpClientUtils;
import cn.hy.infoReport.common.utils.IdUtils;
import cn.hy.infoReport.common.utils.UserUtils;
import cn.hy.infoReport.module.business.vo.ReportDetailVo;
import cn.hy.pms.thrift.utils.ThriftUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 健康监测业务utils
 */
@Component
@Slf4j
public class HealthMonitorUtils {
    /**
     * 构造教职工职务类型信息
     * @param schoolId
     * @param staffList
     * @throws TException
     */
    public static void buildStaffJobType(String schoolId, List<PmsStaff> staffList) throws TException {
       /* List<String> staffIdList = staffList.stream().map(PmsStaff::getUserId).collect(Collectors.toList());
        List<UserRoleVo> userRoleList = ThriftUtils.findUserJobTypeBySchoolIdAndUserIdListAndJsonParams(schoolId, staffIdList, null);
        if (!CollectionUtils.isEmpty(userRoleList)) {
            for (UserRoleVo urvTmp : userRoleList) {
                for (PmsStaff psTmp : staffList) {
                    if (psTmp.getUserId().equals(urvTmp.getUserId()) && !CollectionUtils.isEmpty(urvTmp.getRoleList())) {
                        if (psTmp.getJobTypeNameList() == null) {
                            psTmp.setJobTypeNameList(new ArrayList<>(4));
                        }

                        if (psTmp.getJobTypeIdSet() == null) {
                            psTmp.setJobTypeIdSet(new HashSet<>(4));
                        }
                        urvTmp.getRoleList().forEach(rTmp -> {
                            psTmp.getJobTypeNameList().add(rTmp.getRoleName());
                            psTmp.getJobTypeIdSet().add(rTmp.getId());
                        });
                        break;
                    }
                }
            }
        }*/
    }


    @Autowired
    private IrReportConfigService irReportConfigService;
    @Autowired
    private PmsStaffService pmsStaffService;
    @Autowired
    private IrMsgLogMapper irMsgLogMapper;

    /**
     * 发送模板消息
     * @param schoolId
     * @param userId
     * @param type 0学生 1教职工
     * @param monitorDate
     * @param curMonitor
     * @param curPmsStudent
     * @param curPmsStaff
     * @return
     * @throws TException
     */
    public ReportDetailVo sendReportMsg(@RequestParam("schoolToken") String schoolId, @RequestParam String userId, @RequestParam Byte type, @RequestParam Date monitorDate,
                                         IrHealthMonitor curMonitor, PmsStudent curPmsStudent, PmsStaff curPmsStaff, boolean ignoreParents, IrMsgLogOpeType opeType, IrMsgLogTimeArea timeArea) throws TException {
        ReportDetailVo detailVo = new ReportDetailVo();

        IrReportConfig reportConfig = irReportConfigService.findWithInitBySchoolId(schoolId);

        if ((type == 0 && StringUtils.isBlank(reportConfig.getStudentReportIdentify()) && StringUtils.isBlank(reportConfig.getStudentReportUserIds()))
                || (type == 1 && StringUtils.isBlank(reportConfig.getStaffReportIdentify()) && StringUtils.isBlank(reportConfig.getStaffReportUserIds()))) {
            return detailVo;
        }

        List<PmsStaff> staffList = pmsStaffService.findBySchoolId(schoolId);
        List<SysRole> jobTypeList = ThriftUtils.findJobTypeBySchoolIdAndJsonParams(schoolId, null);
        if (jobTypeList == null) {
            jobTypeList = new ArrayList<>(4);
        }

        boolean sendToParents = false, hasHeadTeacher = false;
        if (type == 0) {
            //学生
            if (!ignoreParents && curMonitor.getHealthStatus() == IrHealthMonitorHealthStatus.ABNORMAL.getCode() && reportConfig.getStudentReportIdentify().contains(InnerIdentify.PARENTS.getCode())) {
                sendToParents = true;
                SysRole sr = new SysRole();
                sr.setId(InnerIdentify.PARENTS.getCode());
                sr.setRoleName(InnerIdentify.PARENTS.getMsg());
                jobTypeList.add(sr);
            }

            if (reportConfig.getStudentReportIdentify().contains(InnerIdentify.HEAD_TEACHER.getCode())) {
                hasHeadTeacher = true;
                SysRole sr = new SysRole();
                sr.setId(InnerIdentify.HEAD_TEACHER.getCode());
                sr.setRoleName(InnerIdentify.HEAD_TEACHER.getMsg());
                jobTypeList.add(sr);
            }


            detailVo.fromIdentify(reportConfig.getStudentReportIdentify(), jobTypeList);

        } else {
            if (jobTypeList.size() > 0) {
                detailVo.fromIdentify(reportConfig.getStaffReportIdentify(), jobTypeList);
            }
        }
        if (!CollectionUtils.isEmpty(staffList)) {
            HealthMonitorUtils.buildStaffJobType(schoolId, staffList);
            if (type == 0) {
                detailVo.fromPerson(reportConfig.getStudentReportUserIds(), staffList);
            } else if (type == 1) {
                detailVo.fromPerson(reportConfig.getStaffReportUserIds(), staffList);
            }

        }

        Set<String> notifyUserIdSet = new HashSet<>(20);
        //处理家长
        if (sendToParents) {
            List<SysUser> parents = ThriftUtils.findParentBySchoolIdAndUserId(schoolId, userId);
            if (!CollectionUtils.isEmpty(parents)) {
                notifyUserIdSet.addAll(parents.stream().map(SysUser::getId).collect(Collectors.toList()));
            }
        }

        if (!CollectionUtils.isEmpty(staffList)) {
            if (hasHeadTeacher) {
                Map<String, Object> params = new HashMap<>(4);
                params.put("classId", curPmsStudent.getClassId());
                params.put("typeList", Collections.singletonList(SysClassUserDetailType.HEAD_TEACHER.getCode()));
                List<SysClassUserDetail> scudList = ThriftUtils.findClassUserDetailBySchoolIdAndJsonParams(schoolId, JSONObject.toJSONString(params));
                if (!CollectionUtils.isEmpty(scudList)) {
                    List<String> headTeacherIdList = scudList.stream().map(SysClassUserDetail::getUserId).collect(Collectors.toList());
                    for (String htid : headTeacherIdList) {
                        for (PmsStaff pmsStaff : staffList) {
                            if (pmsStaff.getUserId().equals(htid)) {
                                notifyUserIdSet.add(htid);
                                break;
                            }
                        }
                    }
                }
            }

            if (!CollectionUtils.isEmpty(detailVo.getIdentifyList())) {
                for (ReportDetailVo.ReportDetailItemVo identifyItemTmp : detailVo.getIdentifyList()) {
                    if (identifyItemTmp.getCode().equals(InnerIdentify.PARENTS.getCode()) || identifyItemTmp.getCode().equals(InnerIdentify.HEAD_TEACHER.getCode())) {
                        continue;
                    }
                    for (PmsStaff psTmp : staffList) {
                        if (CollectionUtils.isEmpty(psTmp.getJobTypeIdSet())) {
                            continue;
                        }
                        if (psTmp.getJobTypeIdSet().contains(identifyItemTmp.getCode())) {
                            notifyUserIdSet.add(psTmp.getUserId());
                        }
                    }
                }
            }

            if (!CollectionUtils.isEmpty(detailVo.getPersonList())) {
                notifyUserIdSet.addAll(detailVo.getPersonList().stream().map(ReportDetailVo.ReportDetailItemVo::getCode).collect(Collectors.toList()));
            }

        }

        if (!CollectionUtils.isEmpty(notifyUserIdSet)) {
            //调用msg组件的消息发送
            Map<String, String> msgData = new HashMap<>(8);
            msgData.put("first", type == 0 ? "学生（" + curPmsStudent.getName() + "）体温异常，请密切关注" : "教职工（" + curPmsStaff.getName() + "）体温异常，请密切关注");
            BigDecimal temperature = null;
            boolean isAmTemperature = false;
            if (curMonitor.getAmTemperature() == null) {
                temperature = curMonitor.getPmTemperature();
            } else if (curMonitor.getPmTemperature() == null) {
                isAmTemperature = true;
                temperature = curMonitor.getAmTemperature();
            } else {
                if (curMonitor.getAmTemperature().compareTo(curMonitor.getPmTemperature()) > 0) {
                    isAmTemperature = true;
                    temperature = curMonitor.getAmTemperature();
                } else {
                    temperature = curMonitor.getPmTemperature();
                }

              /*  boolean amHumanCheck = curMonitor.getAmHumanCheck() != null && curMonitor.getAmHumanCheck() == CommonLogicalStatus.YES.getCode();
                boolean pmHumanCheck = curMonitor.getPmHumanCheck() != null && curMonitor.getPmHumanCheck() == CommonLogicalStatus.YES.getCode();
                if (amHumanCheck && pmHumanCheck) {
                    temperature = curMonitor.getAmTemperature().compareTo(curMonitor.getPmTemperature()) > 0 ? curMonitor.getAmTemperature() : curMonitor.getPmTemperature();
                } else if (amHumanCheck && !pmHumanCheck) {

                }
*/
            }
            msgData.put("keyword1", temperature.stripTrailingZeros().toPlainString() + "度");
            msgData.put("keyword2", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(isAmTemperature ? curMonitor.getAmCheckTime() : curMonitor.getPmCheckTime()));

            Map<String, String> params = new HashMap<>(8);
            params.put("schoolId", schoolId);
            params.put("templateCommonId", ProjectConstant.templateMsgCommonId);
            params.put("userIds", String.join(",", notifyUserIdSet));
            params.put("data", JSONObject.toJSONString(msgData));
            String result = HttpClientUtils.post(ProjectConstant.templateMsgHost + "/pub/wechat/message/template", params, "utf-8");
            log.warn("发送消息结果:{}", result);

            //记录发送日志
            try {
                Date now = new Date();
                IrMsgLog msgLog = new IrMsgLog();
                msgLog.setId(IdUtils.newId());
                msgLog.setDate(monitorDate);
                msgLog.setUserId(userId);
                msgLog.setTimeArea(timeArea.getCode());
                msgLog.setReceiver(String.join(",", notifyUserIdSet));
                msgLog.setOpeType(opeType.getCode());
                msgLog.setSchoolId(schoolId);
                msgLog.setContent(JSONObject.toJSONString(msgData));
                msgLog.setResult(result);
                msgLog.setCreateAt(now);
                msgLog.setCreateBy(IrMsgLogOpeType.PERSON_OPE.equals(opeType) ? UserUtils.getUserId() : "system_device");
                irMsgLogMapper.insert(msgLog);
            } catch (Exception e) {}
        }
        return detailVo;
    }
}
