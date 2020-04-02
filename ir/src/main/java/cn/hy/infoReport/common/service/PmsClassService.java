package cn.hy.infoReport.common.service;

import cn.hy.infoReport.common.entity.PmsClass;
import cn.hy.infoReport.common.entity.PmsClassExample;
import cn.hy.infoReport.common.entity.SysOffice;
import cn.hy.infoReport.common.mapper.PmsClassMapper;
import cn.hy.infoReport.common.rabbit.vo.MsgVo;
import cn.hy.infoReport.common.utils.IdUtils;
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
 * 班级service
 */
@Service
public class PmsClassService extends BaseService {

    @Autowired
    private PmsClassMapper pmsClassMapper;


    /**
     * 根据学校id同步全部班级数据
     * @param schoolId
     * @param timeMillis
     */
    @Transactional(propagation = Propagation.NEVER)
    public void syncAllBySchoolId(String schoolId, long timeMillis) throws TException {
        Date now = new Date(timeMillis);
        Map<String, Object> params = new HashMap<>(6);
        params.put("classDetail", 1);
        params.put("formatOfficeName", 1);
        params.put("grade", 1);
        List<SysOffice> soList = ThriftUtils.findClassBySchoolIdAndJsonParams(schoolId, JSONObject.toJSONString(params));
        if (!CollectionUtils.isEmpty(soList)) {
            List<String> soIdList = soList.stream().map(SysOffice::getId).collect(Collectors.toList());
            List<PmsClass> curClassList = findBySchoolIdAndOfficeIdIn(schoolId, soIdList);
            if (CollectionUtils.isEmpty(curClassList)) {
                for (SysOffice soTmp : soList) {
                    handleNewClass(schoolId, timeMillis, now, soTmp);
                }
            } else {
                for (SysOffice soTmp : soList) {
                    PmsClass curPc = null;
                    for (PmsClass pcTmp : curClassList) {
                        if (pcTmp.getOfficeId().equals(soTmp.getId())) {
                            curPc = pcTmp;
                            break;
                        }
                    }

                    if (curPc != null) {
                        curPc.setClassName(soTmp.getOfficeName());
                        curPc.setGrade(soTmp.getBusiClass() == null ? null : soTmp.getBusiClass().getGrade());
                        curPc.setClassCode(soTmp.getBusiClass() == null ? null : soTmp.getBusiClass().getClassCode());
                        curPc.setUpdateAt(now);
                        curPc.setSyncTime(timeMillis);
                        pmsClassMapper.updateByPrimaryKey(curPc);
                    } else {
                        handleNewClass(schoolId, timeMillis, now, soTmp);
                    }

                }
            }
        }
        deleteBySchoolIdAndSyncTimeNotEq(schoolId, timeMillis);
    }

    /**
     * 根据学校id,机构id集合查询
     * @param schoolId
     * @return
     */
    public List<PmsClass> findBySchoolIdAndOfficeIdIn(String schoolId, List<String> officeIdList) {
        PmsClassExample example = new PmsClassExample();
        example.createCriteria().andSchoolIdEqualTo(schoolId)
                .andOfficeIdIn(officeIdList);
        return pmsClassMapper.selectByExample(example);
    }

    /**
     * 根据学校id,同步时间不等于查询
     * @param schoolId
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteBySchoolIdAndSyncTimeNotEq(String schoolId, long syncTime) {
       PmsClassExample example = new PmsClassExample();
       example.createCriteria().andSchoolIdEqualTo(schoolId)
               .andSyncTimeNotEqualTo(syncTime);
       pmsClassMapper.deleteByExample(example);
    }

    /**
     * 新增教职工
     * @param schoolId
     * @param timeMillis
     * @param now
     */
    private void handleNewClass(String schoolId, long timeMillis, Date now, SysOffice soTmp) {
        PmsClass pcNew = new PmsClass();
        pcNew.setId(IdUtils.newId());
        pcNew.setOfficeId(soTmp.getId());
        pcNew.setUpdateAt(now);
        pcNew.setClassName(soTmp.getOfficeName());
        pcNew.setGrade(soTmp.getBusiClass() == null ? null : soTmp.getBusiClass().getGrade());
        pcNew.setClassCode(soTmp.getBusiClass() == null ? null : soTmp.getBusiClass().getClassCode());
        pcNew.setSchoolId(schoolId);
        pcNew.setSyncTime(timeMillis);
        pmsClassMapper.insert(pcNew);
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
                List<String> officeIdList = new ArrayList<>(msgVoList.size() * 2);
                for (MsgVo msgVo : msgVoList) {
                    if (schoolId.equals(msgVo.getSchoolId())) {
                        officeIdList.add(msgVo.getId());
                    }
                }
                try {
                    syncBySchoolIdAndOfficeIdList(schoolId, timeMillis, officeIdList);
                } catch (TException e) {
                    try {
                        syncBySchoolIdAndOfficeIdList(schoolId, timeMillis, officeIdList);
                    } catch (TException ex) {
                        try {
                            syncBySchoolIdAndOfficeIdList(schoolId, timeMillis, officeIdList);
                        } catch (TException exc) {
                            logger.error("根据学校id:{} 同步消息班级数据失败。错误提示, {}", schoolId, exc.getMessage());
                        }
                    }

                }
            }
        }
    }

    /**
     * 根据机构id集合，同步时间不等于删除
     * @param timeMillis
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteByOfficeIdInAndSyncTimeNotEq(List<String> officeIdList, long timeMillis) {
        PmsClassExample example = new PmsClassExample();
        example.createCriteria().andOfficeIdIn(officeIdList)
                .andSyncTimeNotEqualTo(timeMillis);
        pmsClassMapper.deleteByExample(example);
    }

    /**
     * 根据学校id,用户id集合同步
     * @param schoolId
     * @param timeMillis
     */
    @Transactional(propagation = Propagation.NEVER)
    public void syncBySchoolIdAndOfficeIdList(String schoolId, long timeMillis, List<String> syncOfficeIdList) throws TException {
        Date now = new Date(timeMillis);
        Map<String, Object> params = new HashMap<>(8);
        params.put("classDetail", 1);
        params.put("formatOfficeName", 1);
        params.put("grade", 1);
        params.put("officeIdList", syncOfficeIdList);


        List<SysOffice> soList = ThriftUtils.findClassBySchoolIdAndJsonParams(schoolId, JSONObject.toJSONString(params));

        if (!CollectionUtils.isEmpty(soList)) {
            List<String> officeIdList = soList.stream().map(SysOffice::getId).collect(Collectors.toList());
            List<PmsClass> curClassList = findBySchoolIdAndOfficeIdIn(schoolId, officeIdList);
            if (CollectionUtils.isEmpty(curClassList)) {
                for (SysOffice soTmp : soList) {
                    handleNewClass(schoolId, timeMillis, now, soTmp);
                }
            } else {
                for (SysOffice soTmp : soList) {
                    PmsClass curPc = null;
                    for (PmsClass pcTmp : curClassList) {
                        if (pcTmp.getOfficeId().equals(soTmp.getId())) {
                            curPc = pcTmp;
                            break;
                        }
                    }

                    if (curPc != null) {
                        curPc.setClassName(soTmp.getOfficeName());
                        curPc.setGrade(soTmp.getBusiClass() == null ? null : soTmp.getBusiClass().getGrade());
                        curPc.setClassCode(soTmp.getBusiClass() == null ? null : soTmp.getBusiClass().getClassCode());
                        curPc.setUpdateAt(now);
                        curPc.setSyncTime(timeMillis);
                        pmsClassMapper.updateByPrimaryKey(curPc);
                    } else {
                        handleNewClass(schoolId, timeMillis, now, soTmp);
                    }

                }
            }
        }
        deleteBySchoolIdAndOfficeInAndSyncTimeNotEq(schoolId, syncOfficeIdList, timeMillis);
    }

    /**
     * 根据学校id,机构id集合，同步时间不等于查询
     * @param schoolId
     * @param syncTime
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteBySchoolIdAndOfficeInAndSyncTimeNotEq(String schoolId, List<String> officeIdList, long syncTime) {
        PmsClassExample example = new PmsClassExample();
        example.createCriteria().andSchoolIdEqualTo(schoolId)
                .andOfficeIdIn(officeIdList)
                .andSyncTimeNotEqualTo(syncTime);
        pmsClassMapper.deleteByExample(example);
    }

    /**
     * 根据学校查询学校下的所有机构
     * @param schoolId
     * @return
     */
    public List<PmsClass> findClassBySchoolId(String schoolId) {
        return pmsClassMapper.findClassBySchoolId(schoolId);
    }

    /**
     * 根据学校id查询班级信息
     * @param schoolId
     * @return
     */
    public List<PmsClass> findBySchoolId(String schoolId) {
        PmsClassExample example = new PmsClassExample();
        example.createCriteria().andSchoolIdEqualTo(schoolId);
        return pmsClassMapper.selectByExample(example);
    }
}
