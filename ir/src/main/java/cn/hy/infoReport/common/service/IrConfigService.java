package cn.hy.infoReport.common.service;

import cn.hy.infoReport.common.busiConstant.IrDefaultConfig;
import cn.hy.infoReport.common.entity.IrConfig;
import cn.hy.infoReport.common.mapper.IrConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 配置service
 */
@Service
public class IrConfigService extends BaseService {

    @Autowired
    private IrConfigMapper irConfigMapper;
    @Autowired
    private IrHealthMonitorService irHealthMonitorService;
    @Autowired
    private IrTemperatureReportService irTemperatureReportService;

    /**
     * 根据学校id查询，未查到初始化
     * @param schoolId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public IrConfig findWithInitBySchoolId(String schoolId) {
        IrConfig curConfig = irConfigMapper.selectByPrimaryKey(schoolId);
        if (curConfig == null) {
            curConfig = new IrConfig();
            curConfig.setSchoolId(schoolId);
            curConfig.setTemperatureLowLimit(IrDefaultConfig.temperatureLowLimit);
            curConfig.setNoPass(IrDefaultConfig.noPass);
            curConfig.setDeviceAlarm(IrDefaultConfig.deviceAlarm);
            curConfig.setNotifyContinuousNumber(IrDefaultConfig.notifyContinuousNumber);
            curConfig.setMode(IrDefaultConfig.mode);
            irConfigMapper.insert(curConfig);
        }
        return curConfig;
    }

    /**
     * 保存配置
     * @param schoolId
     * @param irConfig
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(String schoolId, IrConfig irConfig) {
        IrConfig curConfig = irConfigMapper.selectByPrimaryKey(schoolId);
        if (curConfig == null) {
            curConfig = new IrConfig();
            curConfig.setSchoolId(schoolId);
            curConfig.setTemperatureLowLimit(Optional.ofNullable(irConfig.getTemperatureLowLimit()).orElse(IrDefaultConfig.temperatureLowLimit));
            curConfig.setNoPass(Optional.ofNullable(irConfig.getNoPass()).orElse(IrDefaultConfig.noPass));
            curConfig.setDeviceAlarm(Optional.ofNullable(irConfig.getDeviceAlarm()).orElse(IrDefaultConfig.deviceAlarm));
            curConfig.setNotifyContinuousNumber(Optional.ofNullable(irConfig.getNotifyContinuousNumber()).orElse(IrDefaultConfig.notifyContinuousNumber));
            curConfig.setMode(Optional.ofNullable(irConfig.getMode()).orElse(IrDefaultConfig.mode));
            irConfigMapper.insert(curConfig);
        } else {
            //更新irTemperatureReport 中的所有数据
            irTemperatureReportService.updateDateBySchoolId(schoolId, irConfig);
            curConfig.setTemperatureLowLimit(Optional.ofNullable(irConfig.getTemperatureLowLimit()).orElse(IrDefaultConfig.temperatureLowLimit));
            curConfig.setNoPass(Optional.ofNullable(irConfig.getNoPass()).orElse(IrDefaultConfig.noPass));
            curConfig.setDeviceAlarm(Optional.ofNullable(irConfig.getDeviceAlarm()).orElse(IrDefaultConfig.deviceAlarm));
            curConfig.setNotifyContinuousNumber(Optional.ofNullable(irConfig.getNotifyContinuousNumber()).orElse(IrDefaultConfig.notifyContinuousNumber));
            curConfig.setMode(Optional.ofNullable(irConfig.getMode()).orElse(IrDefaultConfig.mode));
            //异常体温下限更改，重新更新监测数据的健康状态
            irHealthMonitorService.rebuildHealthStatus(schoolId, irConfig);
            irConfigMapper.updateByPrimaryKey(curConfig);
        }

    }
}
