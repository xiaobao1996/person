package cn.hy.infoReport.common.service;

import cn.hy.infoReport.common.entity.IrConfig;
import cn.hy.infoReport.common.entity.IrTemperatureReport;
import cn.hy.infoReport.common.entity.IrTemperatureReportExample;
import cn.hy.infoReport.common.entity.IrUserArriveArea;
import cn.hy.infoReport.common.enums.*;
import cn.hy.infoReport.common.mapper.IrConfigMapper;
import cn.hy.infoReport.common.mapper.IrTemperatureReportMapper;
import cn.hy.infoReport.common.mapper.IrUserArriveAreaMapper;
import cn.hy.infoReport.common.params.PageParams;
import cn.hy.infoReport.common.utils.IdUtils;
import cn.hy.infoReport.common.vo.PageInfo;
import cn.hy.infoReport.module.business.vo.IrTemperatureReportSimplifyVo;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: JiuZhou
 * @Date: 2020/3/4 10:19
 * @Version 1.0
 */
@Service
public class IrTemperatureReportService extends BaseService {

    @Autowired
    private IrTemperatureReportMapper irTemperatureReportMapper;
    @Autowired
    private IrUserArriveAreaMapper irUserArriveAreaMapper;
    @Autowired
    private IrUserArriveAreaService irUserArriveAreaService;
    @Autowired
    private IrConfigService irConfigService;
    @Autowired
    private IrConfigMapper irConfigMapper;


    /**
     * 根据条件查询list
     * @param schoolId
     * @param irTemperatureReport
     * @return
     */
    public PageInfo<IrTemperatureReport> list(PageParams pageParams,String schoolId, IrTemperatureReport irTemperatureReport)  {
        PageHelper.startPage(pageParams.getPageNum(), pageParams.getPageSize());
        PageInfo<IrTemperatureReport> pageInfo = new PageInfo<>(irTemperatureReportMapper.list(irTemperatureReport.getUserName(),
                irTemperatureReport.getOfficeId(),
                schoolId,
                irTemperatureReport.getTemperature(),
                irTemperatureReport.getOtherSymptom(),
                irTemperatureReport.getCurrentLocation(),
                irTemperatureReport.getContact(),
                irTemperatureReport.getIdentity(),
                irTemperatureReport.getProvince(),
                irTemperatureReport.getCity(),
                irTemperatureReport.getArea(),
                irTemperatureReport.getMobile(),
                irTemperatureReport.getStartTime(),
                irTemperatureReport.getEndTime(),
                irTemperatureReport.getArriveOtherArea()));
        List<IrTemperatureReport> irTemperatureReports = pageInfo.getList();
        if (!CollectionUtils.isEmpty(irTemperatureReports)) {

            List<String> temperatureIds = irTemperatureReports.stream().map(IrTemperatureReport::getId).collect(Collectors.toList());
            List<IrUserArriveArea> irUserArriveAreaList = irUserArriveAreaService.findByReportIds(temperatureIds);
            IrConfig config = irConfigService.findWithInitBySchoolId(schoolId);
            for (IrTemperatureReport temperatureReport : irTemperatureReports) {
                //处理15天内曾到过的区域
                if (!CollectionUtils.isEmpty(irUserArriveAreaList)) {
                    for (IrUserArriveArea irUserArriveArea : irUserArriveAreaList) {
                        if (temperatureReport.getId().equals(irUserArriveArea.getReportId())) {
                            List<IrUserArriveArea> userArriveAreaList = temperatureReport.getUserArriveAreaList();
                            if (CollectionUtils.isEmpty(userArriveAreaList)) {
                                userArriveAreaList = new ArrayList<>();
                                userArriveAreaList.add(irUserArriveArea);
                                temperatureReport.setUserArriveAreaList(userArriveAreaList);
                            }else {
                                userArriveAreaList.add(irUserArriveArea);
                            }
                        }
                    }
                }
                //判断温度是否异常
                if (config != null && temperatureReport.getTemperature().compareTo(config.getTemperatureLowLimit()) >= 0) {
                    temperatureReport.setTemperatureUnusual(true);
                } else {
                    temperatureReport.setTemperatureUnusual(false);
                }
            }
        }
        return pageInfo;
    }

    /**
     * 保存上报的体温信息
     * @param schoolId
     * @param opeUserId
     * @param irTemperatureReport
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(String schoolId, String opeUserId, IrTemperatureReport irTemperatureReport) {
        IrConfig config = irConfigService.findWithInitBySchoolId(schoolId);
        Date date = new Date();
        irTemperatureReport.setDataTime(date);
        irTemperatureReport.setId(IdUtils.newId());
        //判断是否属于异常情况
        if (irTemperatureReport.getTemperature().compareTo(config.getTemperatureLowLimit()) >= 0
                || irTemperatureReport.getOtherSymptom() == OtherSymptom.YES.getCode()
                || (config.getMode() == IrConfigMode.HOLIDAY.getCode() && irTemperatureReport.getContact() != null && irTemperatureReport.getContact() == Contact.YES.getCode())) {
            irTemperatureReport.setAbnormal(IrTemperatureReportAbnormal.ABNORMAL.getCode());
        } else {
            irTemperatureReport.setAbnormal(IrTemperatureReportAbnormal.NORMAL.getCode());
        }
        irTemperatureReport.setSchoolId(schoolId);
        irTemperatureReport.setCreateBy(opeUserId);
        irTemperatureReport.setCreateAt(date);
        irTemperatureReport.setUpdateAt(date);
        irTemperatureReport.setUpdateBy(opeUserId);
        irTemperatureReport.setStatus(DataStatus.NORMAL.getCode());
        irTemperatureReportMapper.insert(irTemperatureReport);
        if (irTemperatureReport.getArriveOtherArea() != null && irTemperatureReport.getArriveOtherArea() != ArriveOtherArea.NO.getCode() && !CollectionUtils.isEmpty(irTemperatureReport.getUserArriveAreaList())) {
            for (IrUserArriveArea irUserArriveArea : irTemperatureReport.getUserArriveAreaList()) {
                irUserArriveArea.setReportId(irTemperatureReport.getId());
                irUserArriveArea.setCreateAt(date);
                irUserArriveArea.setCreateBy(opeUserId);
                irUserArriveArea.setId(IdUtils.newId());
                irUserArriveAreaMapper.insert(irUserArriveArea);
            }
        }
    }

    /**
     * 根据学校id,用户id集合，上报时间介于查询
     * @param schoolId
     * @param userIdList
     * @param startTime
     * @param endTime
     * @return
     */
    public List<IrTemperatureReport> findValidBySchoolIdAndUserIdListAndDataTimeBetween(String schoolId, List<String> userIdList, Date startTime, Date endTime) {
        IrTemperatureReportExample example = new IrTemperatureReportExample();
        example.createCriteria().andSchoolIdEqualTo(schoolId)
                .andUserIdIn(userIdList)
                .andCreateAtGreaterThan(startTime)
                .andCreateAtLessThan(endTime)
                .andStatusNotEqualTo(DataStatus.DELETE.getCode());
        List<IrTemperatureReport> list = irTemperatureReportMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            //处理15天到过的区域
            List<String> ids = list.stream().map(IrTemperatureReport::getId).collect(Collectors.toList());
            List<IrUserArriveArea> userArriveAreas = irUserArriveAreaService.findByReportIds(ids);
            if (!CollectionUtils.isEmpty(userArriveAreas)) {
                for (IrTemperatureReport irTemperatureReport : list) {
                    for (IrUserArriveArea userArriveArea : userArriveAreas) {
                        if (irTemperatureReport.getId().equals(userArriveArea.getReportId())) {
                            List<IrUserArriveArea> userArriveAreaList = irTemperatureReport.getUserArriveAreaList();
                            if (CollectionUtils.isEmpty(userArriveAreaList)) {
                                userArriveAreaList = new ArrayList<>();
                                userArriveAreaList.add(userArriveArea);
                                irTemperatureReport.setUserArriveAreaList(userArriveAreaList);
                            }else {
                                userArriveAreaList.add(userArriveArea);
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     *根据学校id,用户id集合，提交日期介于，体温大于等于
     * @param schoolId
     * @param userIdList
     * @param startDate
     * @param endDate
     * @return
     */
    public List<IrTemperatureReportSimplifyVo> findAbnormalSimplifyInfo(String schoolId, List<String> userIdList, Date startDate, Date endDate) {
        return irTemperatureReportMapper.findAbnormalSimplifyInfo(schoolId, userIdList, startDate, endDate, CommonLogicalStatus.YES.getCode());
    }

    /**
     * 因为系统设置中的温度的修改更新所有数据,判断其是否异常
     * @param schoolId
     */
    public void updateDateBySchoolId(String schoolId,IrConfig config) {
        List<IrTemperatureReport> irTemperatureReportList = findBySchoolId(schoolId);
        if (!CollectionUtils.isEmpty(irTemperatureReportList)) {
            for (IrTemperatureReport irTemperatureReport : irTemperatureReportList) {
                //判断是否属于异常情况
                if (irTemperatureReport.getTemperature().compareTo(config.getTemperatureLowLimit()) >= 0
                        || irTemperatureReport.getOtherSymptom() == OtherSymptom.YES.getCode()
                        || (config.getMode() == IrConfigMode.HOLIDAY.getCode() && irTemperatureReport.getContact() != null && irTemperatureReport.getContact() == Contact.YES.getCode())) {
                    irTemperatureReport.setAbnormal(IrTemperatureReportAbnormal.ABNORMAL.getCode());
                } else {
                    irTemperatureReport.setAbnormal(IrTemperatureReportAbnormal.NORMAL.getCode());
                }
                irTemperatureReportMapper.updateByPrimaryKey(irTemperatureReport);
            }
        }
    }

    /**
     * 根据学校id查询所有的数据
     * @param schoolId
     * @return
     */
    public List<IrTemperatureReport> findBySchoolId(String schoolId) {
        IrTemperatureReportExample example = new IrTemperatureReportExample();
        example.createCriteria().andSchoolIdEqualTo(schoolId);
        return irTemperatureReportMapper.selectByExample(example);
    }

    /**
     * 根据学校id和身份信息查询所有异常的数量
     * @param schoolId
     * @param identity
     */
    public long findAbunusualCountByIdentityAndSchoolId(String schoolId, IrTemperatureReport irTemperatureReport) {
        long number = irTemperatureReportMapper.findAbunusualCountByIdentityAndSchoolId(irTemperatureReport.getUserName(),
                irTemperatureReport.getOfficeId(),
                schoolId,
                irTemperatureReport.getTemperature(),
                irTemperatureReport.getOtherSymptom(),
                irTemperatureReport.getCurrentLocation(),
                irTemperatureReport.getContact(),
                irTemperatureReport.getIdentity(),
                irTemperatureReport.getProvince(),
                irTemperatureReport.getCity(),
                irTemperatureReport.getArea(),
                irTemperatureReport.getMobile(),
                irTemperatureReport.getStartTime(),
                irTemperatureReport.getEndTime(),
                irTemperatureReport.getArriveOtherArea(),
                irTemperatureReport.getAbnormal());
        return number;
    }
}
