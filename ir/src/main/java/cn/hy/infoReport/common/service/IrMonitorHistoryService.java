package cn.hy.infoReport.common.service;

import cn.hy.infoReport.common.entity.*;
import cn.hy.infoReport.common.enums.*;
import cn.hy.infoReport.common.mapper.IrHealthMonitorMapper;
import cn.hy.infoReport.common.mapper.IrMonitorHistoryMapper;
import cn.hy.infoReport.common.params.PageParams;
import cn.hy.infoReport.common.utils.DateUtils;
import cn.hy.infoReport.common.utils.IdUtils;
import cn.hy.infoReport.common.vo.PageInfo;
import cn.hy.infoReport.module.business.utils.HealthMonitorUtils;
import cn.hy.infoReport.module.business.vo.HumanHealthCheckVo;
import cn.hy.infoReport.module.business.vo.TbImportVo;
import cn.hy.infoReport.module.device.vo.DeviceMonitorDataVo;
import com.github.pagehelper.PageHelper;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 监测记录service
 */
@Service
public class IrMonitorHistoryService extends BaseService {

    @Autowired
    private IrMonitorHistoryMapper irMonitorHistoryMapper;
    @Autowired
    private IrHealthMonitorService irHealthMonitorService;
    @Autowired
    private IrConfigService irConfigService;
    @Autowired
    private IrHealthMonitorMapper irHealthMonitorMapper;
    @Autowired
    private PmsStaffService pmsStaffService;
    @Autowired
    private PmsStudentService pmsStudentService;
    @Autowired
    private IrMsgLogService irMsgLogService;
    @Autowired
    private HealthMonitorUtils healthMonitorUtils;

    /**
     * 保存监测记录
     *  @param schoolId
     * @param now
     * @param type
     * @param checkVoTmp
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(String schoolId, Date now, Byte type, HumanHealthCheckVo checkVoTmp) {
        IrMonitorHistory mh = new IrMonitorHistory();
        mh.setId(IdUtils.newId());
        mh.setUserId(checkVoTmp.getUserId());
        mh.setMonitorDate(DateUtils.getDayStart(checkVoTmp.getMonitorTime()));
        mh.setMonitorDatetime(checkVoTmp.getMonitorTime());
        mh.setMonitorUserId(checkVoTmp.getMonitorUserId());
        mh.setMonitorUserName(checkVoTmp.getMonitorUsername());
        mh.setMonitorDevice(checkVoTmp.getDevice());
        mh.setMonitorPlace(checkVoTmp.getMonitorPlace());
        mh.setTemperature(checkVoTmp.getTemperature());
        mh.setSchoolId(schoolId);
        mh.setCreateAt(now);
        mh.setSource(IrMonitorHistorySource.HUMAN.getCode());
        irMonitorHistoryMapper.insert(mh);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        synchronized (("ir-" + mh.getUserId() + dateFormat.format(mh.getMonitorDate())).intern()) {
            handleMonitorData(schoolId, now, type, mh, IrMonitorHistorySource.HUMAN, checkVoTmp.getPmsStaff(), checkVoTmp.getPmsStudent());
        }
    }

    /**
     * 处理监控数据
     * @param schoolId
     * @param now
     * @param type
     * @param mh
     * @param pmsStaff
     * @param pmsStudent
     */
    private void handleMonitorData(String schoolId, Date now, Byte type, IrMonitorHistory mh, IrMonitorHistorySource source, PmsStaff pmsStaff, PmsStudent pmsStudent) {
        IrConfig irConfig = irConfigService.findWithInitBySchoolId(schoolId);

        //处理用户每天的监测记录
        IrHealthMonitor curMonitor = irHealthMonitorService.findBySchoolIdAndUserIdAndMonitorDate(schoolId, mh.getUserId(), mh.getMonitorDate());
        if (curMonitor == null) {
            //若没有初始化，则进行初始化
            curMonitor = new IrHealthMonitor();
            curMonitor.setId(IdUtils.newId());
            curMonitor.setUserId(mh.getUserId());
            curMonitor.setMonitorDate(mh.getMonitorDate());
            if (DateUtils.isAm(mh.getMonitorDatetime())) {
                curMonitor.setAmTemperature(mh.getTemperature());
                curMonitor.setCheckStatus(IrHealthMonitorCheckStatus.PM_NOT.getCode());
                curMonitor.setAmCheckTime(mh.getMonitorDatetime());
                if (source.equals(IrMonitorHistorySource.HUMAN)) {
                    //人工监测
                    curMonitor.setAmRecheck(CommonLogicalStatus.YES.getCode());
                    curMonitor.setAmHumanCheck(CommonLogicalStatus.YES.getCode());
                } else {
                    //机器监测
                    curMonitor.setAmRecheck(CommonLogicalStatus.NO.getCode());
                    curMonitor.setAmHumanCheck(CommonLogicalStatus.NO.getCode());
                }
            } else {
                curMonitor.setPmTemperature(mh.getTemperature());
                curMonitor.setPmRecheck(CommonLogicalStatus.YES.getCode());
                curMonitor.setCheckStatus(IrHealthMonitorCheckStatus.AM_NOT.getCode());
                curMonitor.setPmCheckTime(mh.getMonitorDatetime());
                curMonitor.setPmHumanCheck(CommonLogicalStatus.YES.getCode());
                if (source.equals(IrMonitorHistorySource.HUMAN)) {
                    //人工监测
                    curMonitor.setPmRecheck(CommonLogicalStatus.YES.getCode());
                    curMonitor.setPmHumanCheck(CommonLogicalStatus.YES.getCode());
                } else {
                    //机器监测
                    curMonitor.setPmRecheck(CommonLogicalStatus.NO.getCode());
                    curMonitor.setPmHumanCheck(CommonLogicalStatus.NO.getCode());
                }
            }
            if (source.equals(IrMonitorHistorySource.HUMAN)) {
                curMonitor.setHealthStatus(mh.getTemperature().compareTo(irConfig.getTemperatureLowLimit()) >= 0 ? IrHealthMonitorHealthStatus.ABNORMAL.getCode() : IrHealthMonitorHealthStatus.NORMAL.getCode());
            } else {
                curMonitor.setHealthStatus(mh.getTemperature().compareTo(irConfig.getTemperatureLowLimit()) >= 0 ? IrHealthMonitorHealthStatus.NEED_CONFIRM.getCode() : IrHealthMonitorHealthStatus.NORMAL.getCode());
            }
            curMonitor.setSchoolId(schoolId);
            curMonitor.setCreateAt(now);
            curMonitor.setUpdateAt(now);
            irHealthMonitorMapper.insert(curMonitor);
        } else {
            //已经存在数据
            if (source.equals(IrMonitorHistorySource.HUMAN)) {
                //数据是人工输入的
                if (DateUtils.isAm(mh.getMonitorDatetime())) {
                    //上午是人工监测的，取最新的测试时间
                    if (curMonitor.getAmHumanCheck() != null && curMonitor.getAmHumanCheck() == CommonLogicalStatus.YES.getCode()) {
                        if (curMonitor.getAmCheckTime() == null || curMonitor.getAmCheckTime().compareTo(mh.getMonitorDatetime()) < 0) {
                            curMonitor.setAmCheckTime(mh.getMonitorDatetime());
                            curMonitor.setAmTemperature(mh.getTemperature());
                            curMonitor.setAmRecheck(CommonLogicalStatus.YES.getCode());
                        }
                    } else {
                        //上午不是人工测试的，取人工测试的数据
                        curMonitor.setAmTemperature(mh.getTemperature());
                        curMonitor.setAmRecheck(CommonLogicalStatus.YES.getCode());
                        curMonitor.setAmHumanCheck(CommonLogicalStatus.YES.getCode());
                        curMonitor.setAmCheckTime(mh.getMonitorDatetime());
                    }

                    //更新测温进展状态
                    if (curMonitor.getCheckStatus() == null || curMonitor.getCheckStatus() != IrHealthMonitorCheckStatus.FINISHED.getCode()) {
                        if (curMonitor.getCheckStatus() == null || curMonitor.getCheckStatus() == IrHealthMonitorCheckStatus.ALL_NOT.getCode()) {
                            curMonitor.setCheckStatus(IrHealthMonitorCheckStatus.PM_NOT.getCode());
                        } else if (curMonitor.getCheckStatus() == IrHealthMonitorCheckStatus.AM_NOT.getCode()) {
                            curMonitor.setCheckStatus(IrHealthMonitorCheckStatus.FINISHED.getCode());
                        }
                    }
                } else {
                    //下午监测数据处理
                    if (curMonitor.getPmHumanCheck() != null && curMonitor.getPmHumanCheck() == CommonLogicalStatus.YES.getCode()) {
                        if (curMonitor.getPmCheckTime() == null || curMonitor.getPmCheckTime().compareTo(mh.getMonitorDatetime()) < 0) {
                            curMonitor.setPmCheckTime(mh.getMonitorDatetime());
                            curMonitor.setPmTemperature(mh.getTemperature());
                            curMonitor.setPmRecheck(CommonLogicalStatus.YES.getCode());
                        }
                    } else {
                        //下午不是人工测试的，取人工测试的数据
                        curMonitor.setPmTemperature(mh.getTemperature());
                        curMonitor.setPmRecheck(CommonLogicalStatus.YES.getCode());
                        curMonitor.setPmHumanCheck(CommonLogicalStatus.YES.getCode());
                        curMonitor.setPmCheckTime(mh.getMonitorDatetime());
                    }

                    //更新测温进展状态
                    if (curMonitor.getCheckStatus() == null || curMonitor.getCheckStatus() != IrHealthMonitorCheckStatus.FINISHED.getCode()) {
                        if (curMonitor.getCheckStatus() == null || curMonitor.getCheckStatus() == IrHealthMonitorCheckStatus.ALL_NOT.getCode()) {
                            curMonitor.setCheckStatus(IrHealthMonitorCheckStatus.AM_NOT.getCode());
                        } else if (curMonitor.getCheckStatus() == IrHealthMonitorCheckStatus.PM_NOT.getCode()) {
                            curMonitor.setCheckStatus(IrHealthMonitorCheckStatus.FINISHED.getCode());
                        }
                    }
                }

                //更新健康状态
                buildHealthStatus(irConfig, curMonitor);

                irHealthMonitorMapper.updateByPrimaryKey(curMonitor);
            }



            if (source.equals(IrMonitorHistorySource.MACHINE)) {
                //数据是来源于机器监测
                if (DateUtils.isAm(mh.getMonitorDatetime())) {
                    //上午非人工监测，取最大值
                    if (curMonitor.getAmHumanCheck() == null || curMonitor.getAmHumanCheck() == CommonLogicalStatus.NO.getCode()) {
                        if (curMonitor.getAmTemperature() == null || curMonitor.getAmTemperature().compareTo(mh.getTemperature()) < 0) {
                            curMonitor.setAmTemperature(mh.getTemperature());
                            curMonitor.setAmCheckTime(mh.getMonitorDatetime());
                            curMonitor.setAmHumanCheck(CommonLogicalStatus.NO.getCode());
                            curMonitor.setAmRecheck(CommonLogicalStatus.NO.getCode());
                        }
                    }
                    //更新测温进展状态
                    if (curMonitor.getCheckStatus() == null || curMonitor.getCheckStatus() != IrHealthMonitorCheckStatus.FINISHED.getCode()) {
                        if (curMonitor.getCheckStatus() == null || curMonitor.getCheckStatus() == IrHealthMonitorCheckStatus.ALL_NOT.getCode()) {
                            curMonitor.setCheckStatus(IrHealthMonitorCheckStatus.PM_NOT.getCode());
                        } else if (curMonitor.getCheckStatus() == IrHealthMonitorCheckStatus.AM_NOT.getCode()) {
                            curMonitor.setCheckStatus(IrHealthMonitorCheckStatus.FINISHED.getCode());
                        }
                    }
                } else {
                    //下午非人工监测，取最大值
                    if (curMonitor.getPmHumanCheck() == null || curMonitor.getPmHumanCheck() == CommonLogicalStatus.NO.getCode()) {
                        if (curMonitor.getPmTemperature() == null || curMonitor.getPmTemperature().compareTo(mh.getTemperature()) < 0) {
                            curMonitor.setPmTemperature(mh.getTemperature());
                            curMonitor.setPmCheckTime(mh.getMonitorDatetime());
                            curMonitor.setPmHumanCheck(CommonLogicalStatus.NO.getCode());
                            curMonitor.setPmRecheck(CommonLogicalStatus.NO.getCode());
                        }
                    }

                    //更新测温进展状态
                    if (curMonitor.getCheckStatus() == null || curMonitor.getCheckStatus() != IrHealthMonitorCheckStatus.FINISHED.getCode()) {
                        if (curMonitor.getCheckStatus() == null || curMonitor.getCheckStatus() == IrHealthMonitorCheckStatus.ALL_NOT.getCode()) {
                            curMonitor.setCheckStatus(IrHealthMonitorCheckStatus.AM_NOT.getCode());
                        } else if (curMonitor.getCheckStatus() == IrHealthMonitorCheckStatus.PM_NOT.getCode()) {
                            curMonitor.setCheckStatus(IrHealthMonitorCheckStatus.FINISHED.getCode());
                        }
                    }
                }

                //更新健康状态
                buildHealthStatus(irConfig, curMonitor);

                irHealthMonitorMapper.updateByPrimaryKey(curMonitor);


                //处理消息发送
                if (mh.getTemperature().compareTo(irConfig.getTemperatureLowLimit()) < 0) {
                    return;
                }
                Integer sendNum = irConfig.getNotifyContinuousNumber();
                if (sendNum == null || sendNum <= 0) {
                    return;
                }


                IrMsgLog curMsgLog = irMsgLogService.findBySchoolIdAndUserIdAndDateAndTimeAreaAndOpeType(schoolId, mh.getUserId(), mh.getMonitorDate(),
                        DateUtils.isAm(mh.getMonitorDatetime()) ? IrMsgLogTimeArea.AM.getCode() : IrMsgLogTimeArea.PM.getCode(), IrMsgLogOpeType.AUTO.getCode());
                if (curMsgLog != null) {
                    return;
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(mh.getMonitorDatetime());
                calendar.add(Calendar.MINUTE, -1);
                Date startTime = calendar.getTime();
                List<IrMonitorHistory> monitorHistoryList = findBySchoolIdAndMonitorDateAndUserIdAndMonitorDatetimeBetweenAndSourceAndTemperatureGte(
                        schoolId, mh.getMonitorDate(), mh.getUserId(), startTime, mh.getMonitorDatetime(), IrMonitorHistorySource.MACHINE.getCode(), irConfig.getTemperatureLowLimit());
                if (monitorHistoryList == null || monitorHistoryList.size() < 3) {
                    return;
                }

                //发送消息
                try {
                    healthMonitorUtils.sendReportMsg(schoolId, mh.getUserId(), type, mh.getMonitorDate(), curMonitor, pmsStudent, pmsStaff,
                            true, IrMsgLogOpeType.AUTO, DateUtils.isAm(mh.getMonitorDatetime()) ? IrMsgLogTimeArea.AM : IrMsgLogTimeArea.PM);
                } catch (TException e) {
                }
            }
        }
    }

    /**
     * 更新体温状态
     * @param irConfig
     * @param curMonitor
     */
    private void buildHealthStatus(IrConfig irConfig, IrHealthMonitor curMonitor) {
        boolean amOverLimit = curMonitor.getAmTemperature() != null && curMonitor.getAmTemperature().compareTo(irConfig.getTemperatureLowLimit()) >= 0;
        boolean amRechecked = curMonitor.getAmRecheck() != null && curMonitor.getAmRecheck() == CommonLogicalStatus.YES.getCode();
        boolean pmOverLimit = curMonitor.getPmTemperature() != null && curMonitor.getPmTemperature().compareTo(irConfig.getTemperatureLowLimit()) >= 0;
        boolean pmRechecked = curMonitor.getPmRecheck() != null && curMonitor.getPmRecheck() == CommonLogicalStatus.YES.getCode();

        if (amOverLimit) {
            if (amRechecked) {
                curMonitor.setHealthStatus(IrHealthMonitorHealthStatus.ABNORMAL.getCode());
            } else {
                if (pmOverLimit) {
                    if (pmRechecked) {
                        curMonitor.setHealthStatus(IrHealthMonitorHealthStatus.ABNORMAL.getCode());
                    } else {
                        curMonitor.setHealthStatus(IrHealthMonitorHealthStatus.NEED_CONFIRM.getCode());
                    }
                } else {
                    curMonitor.setHealthStatus(IrHealthMonitorHealthStatus.NEED_CONFIRM.getCode());
                }
            }
        } else {
            if (pmOverLimit) {
                if (pmRechecked) {
                    curMonitor.setHealthStatus(IrHealthMonitorHealthStatus.ABNORMAL.getCode());
                } else {
                    curMonitor.setHealthStatus(IrHealthMonitorHealthStatus.NEED_CONFIRM.getCode());
                }
            } else {
                curMonitor.setHealthStatus(IrHealthMonitorHealthStatus.NORMAL.getCode());
            }
        }
    }

    /**
     * 根据条件查询
     * @param schoolId
     * @param monitorDate
     * @param userId
     * @param startTime
     * @return
     */
    public List<IrMonitorHistory> findBySchoolIdAndMonitorDateAndUserIdAndMonitorDatetimeBetweenAndSourceAndTemperatureGte(String schoolId, Date monitorDate,
                                                                                           String userId, Date startTime, Date endTime, byte source, BigDecimal lowLimit) {
        IrMonitorHistoryExample example = new IrMonitorHistoryExample();
        example.createCriteria().andSchoolIdEqualTo(schoolId)
                .andMonitorDateEqualTo(monitorDate)
                .andUserIdEqualTo(userId)
                .andMonitorDatetimeBetween(startTime, endTime)
                .andSourceEqualTo(source)
                .andTemperatureGreaterThanOrEqualTo(lowLimit);
        return irMonitorHistoryMapper.selectByExample(example);
    }

    /**
     * 根据学校id,监测日期，用户id查询
     *
     * @param schoolId
     * @param monitorDate
     * @param userId
     * @return
     */
    public List<IrMonitorHistory> findBySchoolIdAndMonitorDateAndUserId(String schoolId, Date monitorDate, String userId) {
        IrMonitorHistoryExample example = new IrMonitorHistoryExample();
        example.setOrderByClause("source, monitor_datetime DESC");
        example.createCriteria().andSchoolIdEqualTo(schoolId)
                .andMonitorDateEqualTo(monitorDate)
                .andUserIdEqualTo(userId);
        return irMonitorHistoryMapper.selectByExample(example);
    }

    /**
     * 保存设备数据
     * @param schoolId
     * @param type
     * @param monitorDataVo
     * @param pmsStaff
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveDeviceData(String schoolId, Byte type, DeviceMonitorDataVo monitorDataVo, PmsStaff pmsStaff, PmsStudent pmsStudent) {
        Date now = new Date();
        IrMonitorHistory mh = new IrMonitorHistory();
        mh.setId(IdUtils.newId());
        mh.setUserId(monitorDataVo.getUserId());
        mh.setMonitorDate(DateUtils.getDayStart(monitorDataVo.getTime()));
        mh.setMonitorDatetime(monitorDataVo.getTime());
        mh.setMonitorUserId(monitorDataVo.getUserId());
        mh.setMonitorUserName(monitorDataVo.getUsername());
        mh.setMonitorDevice(monitorDataVo.getDeviceName());
        mh.setMonitorPlace(monitorDataVo.getLocation());
        mh.setTemperature(monitorDataVo.getTemperature());
        mh.setSchoolId(schoolId);
        mh.setCreateAt(now);
        mh.setSource(IrMonitorHistorySource.MACHINE.getCode());
        irMonitorHistoryMapper.insert(mh);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        synchronized (("ir-" + mh.getUserId() + dateFormat.format(mh.getMonitorDate())).intern()) {
            handleMonitorData(schoolId, now, type, mh, IrMonitorHistorySource.MACHINE, pmsStaff, pmsStudent);
        }
    }

    /**
     * 导入监测数据
     * @param schoolId
     * @param opeUserId
     * @param importVo
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveStudentHealthMonitorHistoryByImport(String schoolId, String opeUserId, TbImportVo importVo) {
        List<TbImportVo.Item> monitorItemList = importVo.getTemperatureDetailList();
        if (CollectionUtils.isEmpty(monitorItemList)) {
            return;
        }

        PmsStudent pmsStudent = importVo.getPmsStudent();

        List<Date> monitorDatetimeList = monitorItemList.stream().map(TbImportVo.Item::getMonitorDateTime).collect(Collectors.toList());
        List<IrMonitorHistory> monitorHistoryList = findBySchoolIdAndUserIdAndSourceAndMonitorDatetimeIn(schoolId, pmsStudent.getUserId(), IrMonitorHistorySource.TB_IMPORT.getCode(), monitorDatetimeList);
        IrMonitorHistory amMaxTemperatureMh = null, pmMaxTemperatureMh = null;
        Date now = new Date();
        if (CollectionUtils.isEmpty(monitorHistoryList)) {

            for (TbImportVo.Item item : monitorItemList) {
                IrMonitorHistory mh = handleNewMhByTbImport(schoolId, importVo, pmsStudent, now, item);
                if (DateUtils.isAm(item.getMonitorDateTime())) {
                    if (amMaxTemperatureMh == null || amMaxTemperatureMh.getTemperature().compareTo(mh.getTemperature()) < 0) {
                        amMaxTemperatureMh = mh;
                    }
                } else {
                    if (pmMaxTemperatureMh == null || pmMaxTemperatureMh.getTemperature().compareTo(mh.getTemperature()) < 0) {
                        pmMaxTemperatureMh = mh;
                    }
                }
            }
        } else {
            for (TbImportVo.Item item : monitorItemList) {
                boolean found = false;
                for (IrMonitorHistory mhTmp : monitorHistoryList) {
                    if (item.getMonitorDateTime().compareTo(mhTmp.getMonitorDatetime()) == 0) {
                        found = true;
                        break;
                    }
                }

                if (found) {
                    continue;
                }

                IrMonitorHistory mh = handleNewMhByTbImport(schoolId, importVo, pmsStudent, now, item);

                if (DateUtils.isAm(item.getMonitorDateTime())) {
                    if (amMaxTemperatureMh == null || amMaxTemperatureMh.getTemperature().compareTo(mh.getTemperature()) < 0) {
                        amMaxTemperatureMh = mh;
                    }
                } else {
                    if (pmMaxTemperatureMh == null || pmMaxTemperatureMh.getTemperature().compareTo(mh.getTemperature()) < 0) {
                        pmMaxTemperatureMh = mh;
                    }
                }

            }
        }

        IrConfig irConfig = irConfigService.findWithInitBySchoolId(schoolId);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        synchronized (("ir-" + pmsStudent.getUserId() + dateFormat.format(importVo.getMonitorDate())).intern()) {
            IrHealthMonitor curMonitor = irHealthMonitorService.findBySchoolIdAndUserIdAndMonitorDate(schoolId, pmsStudent.getUserId(), importVo.getMonitorDate());
            if (curMonitor == null) {
                curMonitor = new IrHealthMonitor();
                curMonitor.setId(IdUtils.newId());
                curMonitor.setUserId(pmsStudent.getUserId());
                curMonitor.setMonitorDate(importVo.getMonitorDate());
                curMonitor.setSchoolId(schoolId);
                curMonitor.setCreateAt(now);
                curMonitor.setUpdateAt(now);
                if (amMaxTemperatureMh != null) {
                    curMonitor.setAmTemperature(amMaxTemperatureMh.getTemperature());
                    curMonitor.setAmRecheck(CommonLogicalStatus.NO.getCode());
                    curMonitor.setAmCheckTime(amMaxTemperatureMh.getMonitorDatetime());
                    curMonitor.setAmHumanCheck(CommonLogicalStatus.NO.getCode());
                }

                if (pmMaxTemperatureMh != null) {
                    curMonitor.setPmTemperature(pmMaxTemperatureMh.getTemperature());
                    curMonitor.setPmRecheck(CommonLogicalStatus.NO.getCode());
                    curMonitor.setPmCheckTime(pmMaxTemperatureMh.getMonitorDatetime());
                    curMonitor.setPmHumanCheck(CommonLogicalStatus.NO.getCode());
                }

                //更新健康状态
                buildHealthStatus(irConfig, curMonitor);
                //更新测温进展
                buildCheckStatus(curMonitor);

                irHealthMonitorMapper.insert(curMonitor);
            } else {
                if (amMaxTemperatureMh != null) {
                    //上午非人工监测，取最大值
                    if (curMonitor.getAmHumanCheck() == null || curMonitor.getAmHumanCheck() == CommonLogicalStatus.NO.getCode()) {
                        if (curMonitor.getAmTemperature() == null || curMonitor.getAmTemperature().compareTo(amMaxTemperatureMh.getTemperature()) < 0) {
                            curMonitor.setAmTemperature(amMaxTemperatureMh.getTemperature());
                            curMonitor.setAmCheckTime(amMaxTemperatureMh.getMonitorDatetime());
                            curMonitor.setAmHumanCheck(CommonLogicalStatus.NO.getCode());
                            curMonitor.setAmRecheck(CommonLogicalStatus.NO.getCode());
                        }
                    }
                }

                if (pmMaxTemperatureMh != null) {
                    //下午非人工监测，取最大值
                    if (curMonitor.getPmHumanCheck() == null || curMonitor.getPmHumanCheck() == CommonLogicalStatus.NO.getCode()) {
                        if (curMonitor.getPmTemperature() == null || curMonitor.getPmTemperature().compareTo(pmMaxTemperatureMh.getTemperature()) < 0) {
                            curMonitor.setPmTemperature(pmMaxTemperatureMh.getTemperature());
                            curMonitor.setPmCheckTime(pmMaxTemperatureMh.getMonitorDatetime());
                            curMonitor.setPmHumanCheck(CommonLogicalStatus.NO.getCode());
                            curMonitor.setPmRecheck(CommonLogicalStatus.NO.getCode());
                        }
                    }
                }

                //更新健康状态
                buildHealthStatus(irConfig, curMonitor);
                //更新测温进展
                buildCheckStatus(curMonitor);

                irHealthMonitorMapper.updateByPrimaryKey(curMonitor);
            }
        }

    }

    /**
     * 新增天波导入监控流水
     * @param schoolId
     * @param importVo
     * @param pmsStudent
     * @param now
     * @param item
     * @return
     */
    private IrMonitorHistory handleNewMhByTbImport(String schoolId, TbImportVo importVo, PmsStudent pmsStudent, Date now, TbImportVo.Item item) {
        IrMonitorHistory mh = new IrMonitorHistory();
        mh.setId(IdUtils.newId());
        mh.setUserId(pmsStudent.getUserId());
        mh.setMonitorDate(importVo.getMonitorDate());
        mh.setMonitorDatetime(item.getMonitorDateTime());
        mh.setTemperature(item.getTemperature());
        mh.setSource(IrMonitorHistorySource.TB_IMPORT.getCode());
        mh.setSchoolId(schoolId);
        mh.setCreateAt(now);
        irMonitorHistoryMapper.insert(mh);
        return mh;
    }

    private void buildCheckStatus(IrHealthMonitor curMonitor) {
        //更新测温进展
        if (curMonitor.getAmTemperature() != null) {
            if (curMonitor.getPmTemperature() != null) {
                curMonitor.setCheckStatus(IrHealthMonitorCheckStatus.FINISHED.getCode());
            } else {
                curMonitor.setCheckStatus(IrHealthMonitorCheckStatus.PM_NOT.getCode());
            }
        } else {
            if (curMonitor.getPmTemperature() != null) {
                curMonitor.setCheckStatus(IrHealthMonitorCheckStatus.AM_NOT.getCode());
            } else {
                curMonitor.setCheckStatus(IrHealthMonitorCheckStatus.ALL_NOT.getCode());
            }
        }
    }

    /**
     * 根据学校id,用户id,source,测试时间查询
     * @param schoolId
     * @param userId
     * @param monitorDatetimeList
     * @return
     */
    public List<IrMonitorHistory> findBySchoolIdAndUserIdAndSourceAndMonitorDatetimeIn(String schoolId, String userId, byte source, List<Date> monitorDatetimeList) {
        IrMonitorHistoryExample example = new IrMonitorHistoryExample();
        example.createCriteria().andSchoolIdEqualTo(schoolId)
                .andUserIdEqualTo(userId)
                .andSourceEqualTo(source)
                .andMonitorDatetimeIn(monitorDatetimeList);
        return irMonitorHistoryMapper.selectByExample(example);
    }

    /**
     * 获取体温枪测温分页数据
     * @param pageParams
     * @param schoolId
     * @param startDate
     * @param endDate
     * @param name
     * @param classId
     * @return
     */
    public PageInfo<IrMonitorHistory> findTbPageBy(PageParams pageParams, String schoolId, Date startDate, Date endDate, String name, String classId) {
        PageHelper.startPage(pageParams);
        PageInfo<IrMonitorHistory> mhPageInfo = new PageInfo<>(irMonitorHistoryMapper.findTbListBy(schoolId, startDate, endDate, name, classId));
        return mhPageInfo;
    }
}
