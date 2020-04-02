package cn.hy.infoReport.common.service;

import cn.hy.infoReport.common.entity.*;
import cn.hy.infoReport.common.enums.SysOfficeType;
import cn.hy.infoReport.common.enums.SysRoleGroupType;
import cn.hy.infoReport.common.mapper.PmsStudentMapper;
import cn.hy.infoReport.common.rabbit.vo.MsgVo;
import cn.hy.infoReport.common.utils.IdUtils;
import cn.hy.infoReport.common.vo.PageInfo;
import cn.hy.infoReport.common.vo.StudentClassVo;
import cn.hy.pms.thrift.utils.ThriftUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 学生service
 */
@Service
public class PmsStudentService extends BaseService {

    @Autowired
    private PmsStudentMapper pmsStudentMapper;


    /**
     * 根据学校id同步全部数据
     * @param schoolId
     * @param timeMillis
     */
    @Transactional(propagation = Propagation.NEVER)
    public void syncAllBySchoolId(String schoolId, long timeMillis) throws TException {
        Date now = new Date(timeMillis);
        Map<String, Object> params = new HashMap<>(4);
        params.put("groupType", SysRoleGroupType.STUDENT.getCode());
        PageInfo<SysUser> studentPageInfo = ThriftUtils.findPageUserWithJsonParams(1, Integer.MAX_VALUE, schoolId, JSONObject.toJSONString(params));
        if (!CollectionUtils.isEmpty(studentPageInfo.getList())) {
            List<String> userIdList = studentPageInfo.getList().stream().map(SysUser::getId).collect(Collectors.toList());
            List<StudentClassVo> studentClassVoList = getStudentClassVos(schoolId, userIdList);


            List<PmsStudent> curPsList = findBySchoolIdAndUserIdIn(schoolId, userIdList);
            if (CollectionUtils.isEmpty(curPsList)) {
                for (SysUser suTmp : studentPageInfo.getList()) {
                    handleNewStudent(schoolId, timeMillis, now, studentClassVoList, suTmp);
                }
            } else {
                for (SysUser suTmp : studentPageInfo.getList()) {
                    PmsStudent curPs = null;
                    for (PmsStudent psTmp : curPsList) {
                        if (suTmp.getId().equals(psTmp.getUserId())) {
                            curPs = psTmp;
                            break;
                        }
                    }

                    if (curPs != null) {
                        curPs.setName(suTmp.getRealName());
                        curPs.setUpdateAt(now);
                        curPs.setSyncTime(timeMillis);
                        buildPmsStudentClassInfo(studentClassVoList, suTmp, curPs);
                        pmsStudentMapper.updateByPrimaryKey(curPs);
                    } else {
                        handleNewStudent(schoolId, timeMillis, now, studentClassVoList, suTmp);
                    }

                }
            }
        }
        deleteBySchoolIdAndSyncTimeNotEq(schoolId, timeMillis);
    }

    /**
     * 获取学生班级信息
     * @param schoolId
     * @param userIdList
     * @return
     * @throws TException
     */
    private List<StudentClassVo> getStudentClassVos(String schoolId, List<String> userIdList) throws TException {
        List<UserClassVo> userClassList = ThriftUtils.findUserOfficeBySchoolIdAndUserIdListAndOtherJsonParams(schoolId, userIdList, JSONObject.toJSONString(Collections.singletonMap("officeType", SysOfficeType.SCHOOL_CLASS.getCode())));

        List<StudentClassVo> studentClassVoList = new ArrayList<>(userIdList.size() * 2);
        if (!CollectionUtils.isEmpty(userClassList)) {
            userClassList.forEach(ucTmp -> {
                StudentClassVo studentClassVo = new StudentClassVo();
                studentClassVo.setStudentId(ucTmp.getUserId());
                studentClassVoList.add(studentClassVo);

                if (!CollectionUtils.isEmpty(ucTmp.getOfficeList())) {
                    studentClassVo.setSysOffice(ucTmp.getOfficeList().get(0));
                }
            });


            List<String> classIdList = studentClassVoList.stream().filter(scvTmp -> scvTmp.getSysOffice() != null).map(StudentClassVo::getSysOffice).map(SysOffice::getId).distinct().collect(Collectors.toList());
            List<BusiClass> busiClassList = ThriftUtils.findBusiClassBySchoolIdAndJsonParams(schoolId, JSONObject.toJSONString(Collections.singletonMap("officeIdList", classIdList)));
            if (!CollectionUtils.isEmpty(busiClassList)) {
                for (StudentClassVo scvTmp : studentClassVoList) {
                    if (scvTmp.getSysOffice() == null) {
                        continue;
                    }
                    for (BusiClass bcTmp : busiClassList) {
                        if (bcTmp.getOfficeId().equals(scvTmp.getSysOffice().getId())) {
                            scvTmp.setBusiClass(bcTmp);
                        }
                    }
                }
            }
        }
        return studentClassVoList;
    }

    /**
     * 根据学校id,同步时间
     * @param schoolId
     * @param timeMillis
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteBySchoolIdAndSyncTimeNotEq(String schoolId, long timeMillis) {
        PmsStudentExample example = new PmsStudentExample();
        example.createCriteria().andSchoolIdEqualTo(schoolId)
                .andSyncTimeNotEqualTo(timeMillis);
        pmsStudentMapper.deleteByExample(example);
    }

    /**
     * 处理新增pmsStudnet信息
     * @param schoolId
     * @param timeMillis
     * @param now
     * @param studentClassVoList
     * @param suTmp
     */
    public void handleNewStudent(String schoolId, long timeMillis, Date now, List<StudentClassVo> studentClassVoList, SysUser suTmp) {
        PmsStudent psNew = new PmsStudent();
        psNew.setId(IdUtils.newId());
        psNew.setUserId(suTmp.getId());
        psNew.setName(suTmp.getRealName());
        psNew.setSchoolId(schoolId);
        psNew.setUpdateAt(now);
        psNew.setSyncTime(timeMillis);

        buildPmsStudentClassInfo(studentClassVoList, suTmp, psNew);

        pmsStudentMapper.insert(psNew);
    }

    /**
     * 构建学生班级信息
     * @param studentClassVoList
     * @param suTmp
     * @param psNew
     */
    public void buildPmsStudentClassInfo(List<StudentClassVo> studentClassVoList, SysUser suTmp, PmsStudent psNew) {
        if (!CollectionUtils.isEmpty(studentClassVoList)) {
            for (StudentClassVo scvTmp : studentClassVoList) {
                if (scvTmp.getStudentId().equals(suTmp.getId())) {
                    if (scvTmp.getSysOffice() != null) {
                        psNew.setClassId(scvTmp.getSysOffice().getId());
                        psNew.setClassSort(scvTmp.getSysOffice().getTreeSort());
                        psNew.setClassName(scvTmp.getSysOffice().getOfficeName());
                    } else {
                        psNew.setClassId(null);
                        psNew.setClassSort(null);
                        psNew.setClassName(null);
                    }

                    if (scvTmp.getBusiClass() != null) {
                        psNew.setGrade(scvTmp.getBusiClass().getGrade());
                        psNew.setClassCode(scvTmp.getBusiClass().getClassCode());
                    } else {
                        psNew.setGrade(null);
                        psNew.setClassCode(null);
                    }


                    break;
                }
            }
        }
    }

    /**
     * 根据学校id删除
     * @param schoolId
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteBySchoolId(String schoolId) {
        PmsStudentExample example = new PmsStudentExample();
        example.createCriteria().andSchoolIdEqualTo(schoolId);
        pmsStudentMapper.deleteByExample(example);
    }

    /**
     * 根据学校id,用户id集合查询
     * @param schoolId
     * @param userIdList
     * @return
     */
    public List<PmsStudent> findBySchoolIdAndUserIdIn(String schoolId, List<String> userIdList) {
        PmsStudentExample example = new PmsStudentExample();
        example.createCriteria().andSchoolIdEqualTo(schoolId)
                .andUserIdIn(userIdList);
        return pmsStudentMapper.selectByExample(example);
    }

    /**
     * 根据
     * @param userIdList
     * @return
     */
    public List<PmsStudent> findByUserIdIn(List<String> userIdList) {
        PmsStudentExample example = new PmsStudentExample();
        example.createCriteria().andUserIdIn(userIdList);
        return pmsStudentMapper.selectByExample(example);
    }

    /**
     * 根据用户id集合，同步时间不等于删除
     * @param timeMillis
     */
    public void deleteByUserIdInAndSyncTimeNotEq(List<String> userIdList, long timeMillis) {
        PmsStudentExample example = new PmsStudentExample();
        example.createCriteria().andUserIdIn(userIdList)
                .andSyncTimeNotEqualTo(timeMillis);
        pmsStudentMapper.deleteByExample(example);
    }

    /**
     * 根据消息同步
     * @param msgVoList
     */
    @Transactional(propagation = Propagation.NEVER)
    public void syncByMsgList(List<MsgVo> msgVoList) {
        Set<String> schoolIdSet = new HashSet<>(2);
        for (MsgVo msgVo : msgVoList) {
            if (StringUtils.isNotBlank(msgVo.getSchoolId())) {
                schoolIdSet.add(msgVo.getSchoolId());
            }
        }
        long timeMillis = System.currentTimeMillis();

        if (!CollectionUtils.isEmpty(schoolIdSet)) {
            for (String schoolId : schoolIdSet) {
                List<String> userIdList = new ArrayList<>(msgVoList.size() * 2);
                for (MsgVo msgVo : msgVoList) {
                    if (schoolId.equals(msgVo.getSchoolId())) {
                        userIdList.add(msgVo.getId());
                    }
                }
                try {
                    syncBySchoolIdAndUserIdList(schoolId, timeMillis, userIdList);
                } catch (TException e) {
                    try {
                        syncBySchoolIdAndUserIdList(schoolId, timeMillis, userIdList);
                    } catch (TException e1) {
                        try {
                            syncBySchoolIdAndUserIdList(schoolId, timeMillis, userIdList);
                        } catch (TException e2) {
                            logger.error("根据学校id:{} 同步消息学生数据失败。错误提示, {}", schoolId, e2.getMessage());
                        }
                    }
                }
            }
        }

    }

    /**
     * 根据学校id,用户id集合同步
     * @param schoolId
     * @param timeMillis
     */
    @Transactional(propagation = Propagation.NEVER)
    public void syncBySchoolIdAndUserIdList(String schoolId, long timeMillis, List<String> syncUserIdList) throws TException {
        Date now = new Date(timeMillis);
        Map<String, Object> params = new HashMap<>(4);
        params.put("groupType", SysRoleGroupType.STUDENT.getCode());
        params.put("userIdList", syncUserIdList);
        PageInfo<SysUser> studentPageInfo = ThriftUtils.findPageUserWithJsonParams(1, Integer.MAX_VALUE, schoolId, JSONObject.toJSONString(params));
        if (!CollectionUtils.isEmpty(studentPageInfo.getList())) {
            List<String> userIdList = studentPageInfo.getList().stream().map(SysUser::getId).collect(Collectors.toList());

            List<StudentClassVo> studentClassVoList = getStudentClassVos(schoolId, userIdList);

            List<PmsStudent> curStudentList = findBySchoolIdAndUserIdIn(schoolId, userIdList);

            if (CollectionUtils.isEmpty(curStudentList)) {
                for (SysUser suTmp : studentPageInfo.getList()) {
                    handleNewStudent(schoolId, timeMillis, now, studentClassVoList, suTmp);
                }
            } else {
                for (SysUser suTmp : studentPageInfo.getList()) {
                    PmsStudent curPs = null;
                    for (PmsStudent psTmp : curStudentList) {
                        if (psTmp.getUserId().equals(suTmp.getId())) {
                            curPs = psTmp;
                            break;
                        }
                    }

                    if (curPs != null) {
                        curPs.setName(suTmp.getRealName());
                        curPs.setUpdateAt(now);
                        curPs.setSyncTime(timeMillis);
                        buildPmsStudentClassInfo(studentClassVoList, suTmp, curPs);
                        pmsStudentMapper.updateByPrimaryKey(curPs);
                    } else {
                        handleNewStudent(schoolId, timeMillis, now, studentClassVoList, suTmp);
                    }

                }
            }
        }
        deleteBySchoolIdAndUserIdInAndSyncTimeNotEq(schoolId, syncUserIdList, timeMillis);
    }

    /**
     * 根据学校id,用户id集合，同步时间不等于查询
     * @param schoolId
     * @param timeMillis
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteBySchoolIdAndUserIdInAndSyncTimeNotEq(String schoolId, List<String> userIdList, long timeMillis) {
        PmsStudentExample example = new PmsStudentExample();
        example.createCriteria().andSchoolIdEqualTo(schoolId)
                .andUserIdIn(userIdList)
                .andSyncTimeNotEqualTo(timeMillis);
        pmsStudentMapper.deleteByExample(example);
    }

    /**
     * 根据学校id查询学生信息，同时进行排序
     * @param schoolId
     * @return
     */
    public List<PmsStudent> findWithSortBySchoolId(String schoolId) {
        return pmsStudentMapper.findWithSortBySchoolId(schoolId);
    }

    /**
     * 根据学校id查询
     * @param schoolId
     * @return
     */
    public List<PmsStudent> findBySchoolId(String schoolId) {
        PmsStudentExample example = new PmsStudentExample();
        example.createCriteria().andSchoolIdEqualTo(schoolId);
        return pmsStudentMapper.selectByExample(example);
    }

    /**
     * 根据学校id,用户id查询
     * @param schoolId
     * @param userId
     * @return
     */
    public PmsStudent findBySchoolIdAndUserId(String schoolId, String userId) {
        PmsStudentExample example = new PmsStudentExample();
        example.createCriteria().andSchoolIdEqualTo(schoolId)
                .andUserIdEqualTo(userId);
        List<PmsStudent> studentList = pmsStudentMapper.selectByExample(example);
        return CollectionUtils.isEmpty(studentList) ? null : studentList.get(0);
    }

    /**
     * 根据学校id，年级，班号，姓名查询
     * @param schoolId
     * @param grade
     * @param classCode
     * @param name
     * @return
     */
    public PmsStudent findBySchoolIdAndGradeAndClassCodeAndName(String schoolId, Integer grade, String classCode, String name) {
        PmsStudentExample example = new PmsStudentExample();
        example.createCriteria().andSchoolIdEqualTo(schoolId)
                .andGradeEqualTo(grade)
                .andClassCodeEqualTo(classCode)
                .andNameEqualTo(name);
        List<PmsStudent> studentList = pmsStudentMapper.selectByExample(example);
        return CollectionUtils.isEmpty(studentList) ? null : studentList.get(0);
    }
}
