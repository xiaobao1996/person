package cn.hy.infoReport.common.service;

import cn.hy.infoReport.common.entity.IrConfig;
import cn.hy.infoReport.common.entity.IrHealthMonitor;
import cn.hy.infoReport.common.entity.IrHealthMonitorExample;
import cn.hy.infoReport.common.mapper.IrHealthMonitorMapper;
import cn.hy.infoReport.common.params.PageParams;
import cn.hy.infoReport.common.vo.PageInfo;
import cn.hy.infoReport.module.business.vo.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 健康监测service
 */
@Service
public class IrHealthMonitorService extends BaseService {

    @Autowired
    private IrHealthMonitorMapper irHealthMonitorMapper;
    @Autowired
    private IrMonitorHistoryService irMonitorHistoryService;

    /**
     * 获取学生健康监测分页数据
     * @param pageParams
     * @param schoolId
     * @param monitorDate
     * @param name
     * @param classId
     * @param healthStatus
     * @param checkStatus
     * @return
     */
    public PageInfo<StudentHealthMonitorVo> findStudentHealthMonitorPageBy(PageParams pageParams, String schoolId, Date monitorDate, String name, String classId, Byte healthStatus, Byte checkStatus) {
        PageHelper.startPage(pageParams);
        PageInfo<StudentHealthMonitorVo> healthMonitorVoPageInfo = new PageInfo<>(irHealthMonitorMapper.findStudentHealthMonitorBy(schoolId, monitorDate, name, classId, healthStatus, checkStatus));
        return healthMonitorVoPageInfo;
    }

    /**
     * 批量保存测温记录
     * @param schoolId
     * @param type
     * @param healthCheckVoList
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchSaveHealthMonitorHistory(String schoolId, Byte type, List<HumanHealthCheckVo> healthCheckVoList) {
        Date now = new Date();
        for (HumanHealthCheckVo checkVoTmp : healthCheckVoList) {
            irMonitorHistoryService.save(schoolId, now, type, checkVoTmp);
        }

    }

    /**
     * 根据学校id,用户id,监测日期查询
     * @param schoolId
     * @param userId
     * @return
     */
    public IrHealthMonitor findBySchoolIdAndUserIdAndMonitorDate(String schoolId, String userId, Date monitorDate) {
        IrHealthMonitorExample example = new IrHealthMonitorExample();
        example.createCriteria().andSchoolIdEqualTo(schoolId)
                .andUserIdEqualTo(userId)
                .andMonitorDateEqualTo(monitorDate);
        List<IrHealthMonitor> monitorList = irHealthMonitorMapper.selectByExample(example);
        return CollectionUtils.isEmpty(monitorList) ? null : monitorList.get(0);
    }

    /**
     * 重新更新监测数据的健康状态
     * @param schoolId
     * @param irConfig
     */
    @Transactional(rollbackFor = Exception.class)
    public void rebuildHealthStatus(String schoolId, IrConfig irConfig) {
        irHealthMonitorMapper.updateHealthStatus(schoolId, irConfig.getTemperatureLowLimit());
    }

    /**
     * 获取教职工监测记录
     * @param pageParams
     * @param schoolId
     * @param monitorDate
     * @param name
     * @param mobile
     * @param healthStatus
     * @param checkStatus
     * @return
     */
    public PageInfo<StaffHealthMonitorVo> findStaffHealthMonitorPageBy(PageParams pageParams, String schoolId, Date monitorDate, String name, String mobile, Byte healthStatus, Byte checkStatus) {
        PageHelper.startPage(pageParams);
        PageInfo<StaffHealthMonitorVo> healthMonitorVoPageInfo = new PageInfo<>(irHealthMonitorMapper.findStaffHealthMonitorBy(schoolId, monitorDate, name, mobile, healthStatus, checkStatus));
        return healthMonitorVoPageInfo;
    }

    /**
     * 获取学生统计
     * @param schoolId
     * @param monitorDate
     * @return
     */
    public CalcVo studentCalc(String schoolId, Date monitorDate) {
        return irHealthMonitorMapper.studentCalc(schoolId, monitorDate);
    }

    /**
     * 获取教职工统计
     * @param schoolId
     * @param monitorDate
     * @return
     */
    public CalcVo staffCalc(String schoolId, Date monitorDate) {
        return irHealthMonitorMapper.staffCalc(schoolId, monitorDate);
    }

    /**
     * 根据学校id,用户id集合，监测日期介于，健康状态查询
     * @return
     */
    public List<IrHealthMonitor> findBySchoolIdAndUserIdInAndMonitorDateBetweenAndHealthStatus(String schoolId,
                                       List<String> userIdList, Date monitorDateStart, Date monitorDateEnd, byte healthStatus) {
        IrHealthMonitorExample example = new IrHealthMonitorExample();
        example.createCriteria().andSchoolIdEqualTo(schoolId)
                .andUserIdIn(userIdList)
                .andMonitorDateBetween(monitorDateStart, monitorDateEnd)
                .andHealthStatusEqualTo(healthStatus);
        return irHealthMonitorMapper.selectByExample(example);
    }

    /**
     * 根据学校id,用户id集合，监测日期介于，健康状态查询精简信息
     * @param schoolId
     * @param userIdList
     * @return
     */
    public List<IrHealthMonitorSimplifyVo> findAbnormalSimplifyInfo(String schoolId,
                                                                    List<String> userIdList, Date monitorDateStart, Date monitorDateEnd, byte healthStatus) {
        return irHealthMonitorMapper.findAbnormalSimplifyInfo(schoolId, userIdList, monitorDateStart, monitorDateEnd, healthStatus);
    }
}
