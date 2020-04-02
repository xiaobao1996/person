package cn.hy.infoReport.common.service;

import cn.hy.infoReport.common.constant.ProjectConstant;
import cn.hy.infoReport.common.entity.PmsStaff;
import cn.hy.infoReport.common.entity.PmsStaffExample;
import cn.hy.infoReport.common.entity.PmsStudent;
import cn.hy.infoReport.common.entity.SysUser;
import cn.hy.infoReport.common.mapper.PmsStaffMapper;
import cn.hy.infoReport.common.mapper.PmsStudentMapper;
import cn.hy.infoReport.common.rabbit.vo.MsgVo;
import cn.hy.infoReport.common.utils.IdUtils;
import cn.hy.infoReport.common.vo.PageInfo;
import cn.hy.pms.thrift.SysUserThrift;
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
 * 教职工service
 */
@Service
public class PmsStaffService extends BaseService {

    @Autowired
    private PmsStaffMapper pmsStaffMapper;
    @Autowired
    private PmsStudentService pmsStudentService;
    @Autowired
    private PmsStudentMapper pmsStudentMapper;

    /**
     * 根据学校id同步全部教职工数据
     * @param schoolId
     * @param timeMillis
     */
    @Transactional(propagation = Propagation.NEVER)
    public void syncAllBySchoolId(String schoolId, long timeMillis) throws TException {
        Date now = new Date(timeMillis);
        Map<String, Object> params = new HashMap<>(4);
        params.put("groupTypeList", ProjectConstant.staffGroupTypeSet);
        PageInfo<SysUser> staffPageInfo = ThriftUtils.findPageUserWithJsonParams(1, Integer.MAX_VALUE, schoolId, JSONObject.toJSONString(params));
        if (!CollectionUtils.isEmpty(staffPageInfo.getList())) {
            List<String> userIdList = staffPageInfo.getList().stream().map(SysUser::getId).collect(Collectors.toList());
            List<PmsStaff> curStaffList = findBySchoolIdAndUserIdIn(schoolId, userIdList);
            if (CollectionUtils.isEmpty(curStaffList)) {
                for (SysUser suTmp : staffPageInfo.getList()) {
                    handleNewStaff(schoolId, timeMillis, now, suTmp);
                }
            } else {
                for (SysUser suTmp : staffPageInfo.getList()) {
                    PmsStaff curPs = null;
                    for (PmsStaff psTmp : curStaffList) {
                        if (psTmp.getUserId().equals(suTmp.getId())) {
                            curPs = psTmp;
                            break;
                        }
                    }

                    if (curPs != null) {
                        curPs.setMobile(suTmp.getMobile());
                        curPs.setName(suTmp.getRealName());
                        curPs.setUpdateAt(now);
                        curPs.setSyncTime(timeMillis);
                        pmsStaffMapper.updateByPrimaryKey(curPs);
                    } else {
                        handleNewStaff(schoolId, timeMillis, now, suTmp);
                    }

                }
            }
        }
        deleteBySchoolIdAndSyncTimeNotEq(schoolId, timeMillis);
    }

    /**
     * 根据学校id,同步时间不等于查询
     * @param schoolId
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteBySchoolIdAndSyncTimeNotEq(String schoolId, long syncTime) {
       PmsStaffExample example = new PmsStaffExample();
       example.createCriteria().andSchoolIdEqualTo(schoolId)
               .andSyncTimeNotEqualTo(syncTime);
       pmsStaffMapper.deleteByExample(example);
    }

    /**
     * 新增教职工
     * @param schoolId
     * @param timeMillis
     * @param now
     * @param suTmp
     */
    private void handleNewStaff(String schoolId, long timeMillis, Date now, SysUser suTmp) {
        PmsStaff psNew = new PmsStaff();
        psNew.setId(IdUtils.newId());
        psNew.setUserId(suTmp.getId());
        psNew.setUpdateAt(now);
        psNew.setName(suTmp.getRealName());
        psNew.setUserId(suTmp.getId());
        psNew.setMobile(suTmp.getMobile());
        psNew.setSchoolId(schoolId);
        psNew.setSyncTime(timeMillis);
        pmsStaffMapper.insert(psNew);
    }

    /**
     * 根据学校id,用户id集合查询
     * @param schoolId
     * @param userIdList
     * @return
     */
    public List<PmsStaff> findBySchoolIdAndUserIdIn(String schoolId, List<String> userIdList) {
        PmsStaffExample example = new PmsStaffExample();
        example.createCriteria().andSchoolIdEqualTo(schoolId)
                .andUserIdIn(userIdList);
        return pmsStaffMapper.selectByExample(example);
    }

    /**
     * 根据学校id删除
     * @param schoolId
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteBySchoolId(String schoolId) {
        PmsStaffExample example = new PmsStaffExample();
        example.createCriteria().andSchoolIdEqualTo(schoolId);
        pmsStaffMapper.deleteByExample(example);
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
                    } catch (TException ex) {
                        try {
                            syncBySchoolIdAndUserIdList(schoolId, timeMillis, userIdList);
                        } catch (TException exc) {
                            logger.error("根据学校id:{} 同步消息教职工/用户数据失败。错误提示, {}", schoolId, exc.getMessage());
                        }
                    }

                }
            }
        }

        //处理全部删除数据
        List<String> userIdList = new ArrayList<>(msgVoList.size() * 2);
        for (MsgVo msgVo : msgVoList) {
            if (StringUtils.isBlank(msgVo.getSchoolId())) {
                userIdList.add(msgVo.getId());
            }
        }

        if (!CollectionUtils.isEmpty(userIdList)) {
            try {
                syncByUserIdList(userIdList, timeMillis);
            } catch (TException e) {
                try {
                    syncByUserIdList(userIdList, timeMillis);
                } catch (TException e1) {
                    try {
                        syncByUserIdList(userIdList, timeMillis);
                    } catch (TException e2) {
                        logger.error("同步消息教职工/用户数据失败。错误提示, {}", e2.getMessage());
                    }
                }
            }

        }
    }

    /**
     * 根据用户id集合同步
     * @param timeMillis
     */
    @Transactional(propagation = Propagation.NEVER)
    public void syncByUserIdList(List<String> syncUserIdList, long timeMillis) throws TException {
        List<SysUserThrift> suList = ThriftUtils.findUserByUserIdList(syncUserIdList);
        if (!CollectionUtils.isEmpty(suList)) {
            Date now = new Date(timeMillis);
            //处理教职工信息
            List<String> userIdList = suList.stream().map(SysUserThrift::getId).collect(Collectors.toList());
            List<PmsStaff> curPsList = findByUserIdIn(userIdList);
            if (!CollectionUtils.isEmpty(curPsList)) {
                for (PmsStaff psTmp : curPsList) {
                    for (SysUserThrift suTmp : suList) {
                        if (psTmp.getUserId().equals(suTmp.getId())) {
                            psTmp.setName(suTmp.getRealName());
                            psTmp.setMobile(suTmp.getMobile());
                            psTmp.setSyncTime(timeMillis);
                            psTmp.setUpdateAt(now);
                            pmsStaffMapper.updateByPrimaryKey(psTmp);
                            break;
                        }
                    }
                }
            }
            //处理学生信息
            List<PmsStudent> curStuList = pmsStudentService.findByUserIdIn(userIdList);
            if (!CollectionUtils.isEmpty(curStuList)) {
                for (PmsStudent psTmp : curStuList) {
                    for (SysUserThrift suTmp : suList) {
                        if (psTmp.getUserId().equals(suTmp.getId())) {
                            psTmp.setName(suTmp.getRealName());
                            psTmp.setSyncTime(timeMillis);
                            psTmp.setUpdateAt(now);
                            pmsStudentMapper.updateByPrimaryKey(psTmp);
                            break;
                        }
                    }
                }
            }
        }
        deleteByUserIdInAndSyncTimeNotEq(syncUserIdList, timeMillis);

        pmsStudentService.deleteByUserIdInAndSyncTimeNotEq(syncUserIdList, timeMillis);
    }

    /**
     * 根据用户id集合查询
     * @param userIdList
     * @return
     */
    public List<PmsStaff> findByUserIdIn(List<String> userIdList) {
        PmsStaffExample example = new PmsStaffExample();
        example.createCriteria().andUserIdIn(userIdList);
        return pmsStaffMapper.selectByExample(example);
    }

    /**
     * 根据用户id集合，同步时间不等于删除
     * @param userIdList
     * @param timeMillis
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteByUserIdInAndSyncTimeNotEq(List<String> userIdList, long timeMillis) {
        PmsStaffExample example = new PmsStaffExample();
        example.createCriteria().andUserIdIn(userIdList)
                .andSyncTimeNotEqualTo(timeMillis);
        pmsStaffMapper.deleteByExample(example);
    }

    /**
     * 根据学校id,用户id集合同步
     * @param schoolId
     * @param timeMillis
     * @param syncUserIdList
     */
    @Transactional(propagation = Propagation.NEVER)
    public void syncBySchoolIdAndUserIdList(String schoolId, long timeMillis, List<String> syncUserIdList) throws TException {
        Date now = new Date(timeMillis);
        Map<String, Object> params = new HashMap<>(4);
        params.put("groupTypeList", ProjectConstant.staffGroupTypeSet);
        params.put("userIdList", syncUserIdList);
        PageInfo<SysUser> staffPageInfo = ThriftUtils.findPageUserWithJsonParams(1, Integer.MAX_VALUE, schoolId, JSONObject.toJSONString(params));
        if (!CollectionUtils.isEmpty(staffPageInfo.getList())) {
            List<String> userIdList = staffPageInfo.getList().stream().map(SysUser::getId).collect(Collectors.toList());
            List<PmsStaff> curStaffList = findBySchoolIdAndUserIdIn(schoolId, userIdList);
            if (CollectionUtils.isEmpty(curStaffList)) {
                for (SysUser suTmp : staffPageInfo.getList()) {
                    handleNewStaff(schoolId, timeMillis, now, suTmp);
                }
            } else {
                for (SysUser suTmp : staffPageInfo.getList()) {
                    PmsStaff curPs = null;
                    for (PmsStaff psTmp : curStaffList) {
                        if (psTmp.getUserId().equals(suTmp.getId())) {
                            curPs = psTmp;
                            break;
                        }
                    }

                    if (curPs != null) {
                        curPs.setMobile(suTmp.getMobile());
                        curPs.setName(suTmp.getRealName());
                        curPs.setUpdateAt(now);
                        curPs.setSyncTime(timeMillis);
                        pmsStaffMapper.updateByPrimaryKey(curPs);
                    } else {
                        handleNewStaff(schoolId, timeMillis, now, suTmp);
                    }

                }
            }
        }
        deleteBySchoolIdAndUserIdInAndSyncTimeNotEq(schoolId, syncUserIdList, timeMillis);
    }

    /**
     * 根据学校id,用户id集合，同步时间不等于查询
     * @param schoolId
     * @param syncTime
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteBySchoolIdAndUserIdInAndSyncTimeNotEq(String schoolId, List<String> userIdList, long syncTime) {
        PmsStaffExample example = new PmsStaffExample();
        example.createCriteria().andSchoolIdEqualTo(schoolId)
                .andUserIdIn(userIdList)
                .andSyncTimeNotEqualTo(syncTime);
        pmsStaffMapper.deleteByExample(example);
    }

    /**
     * 根据学校id查询，同时进行排序
     * @param schoolId
     * @return
     */
    public List<PmsStaff> findWithSortBySchoolId(String schoolId) {
        PmsStaffExample example = new PmsStaffExample();
        example.setOrderByClause("CONVERT(name USING gbk)");
        example.createCriteria().andSchoolIdEqualTo(schoolId);
        return pmsStaffMapper.selectByExample(example);
    }

    /**
     * 根据学校id查询
     * @param schoolId
     * @return
     */
    public List<PmsStaff> findBySchoolId(String schoolId) {
        PmsStaffExample example = new PmsStaffExample();
        example.createCriteria().andSchoolIdEqualTo(schoolId);
        return pmsStaffMapper.selectByExample(example);
    }

    /**
     * 根据学校id,用户id查询
     * @param schoolId
     * @param userId
     * @return
     */
    public PmsStaff findBySchoolIdAndUserId(String schoolId, String userId) {
        PmsStaffExample example = new PmsStaffExample();
        example.createCriteria().andSchoolIdEqualTo(schoolId)
                .andUserIdEqualTo(userId);
        List<PmsStaff> staffList = pmsStaffMapper.selectByExample(example);
        return CollectionUtils.isEmpty(staffList) ? null : staffList.get(0);
    }
}
