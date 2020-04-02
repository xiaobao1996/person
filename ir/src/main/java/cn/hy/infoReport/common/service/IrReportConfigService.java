package cn.hy.infoReport.common.service;

import cn.hy.infoReport.common.entity.IrReportConfig;
import cn.hy.infoReport.common.mapper.IrReportConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 通知配置service
 */
@Service
public class IrReportConfigService extends BaseService {


    @Autowired
    private IrReportConfigMapper irReportConfigMapper;

    /**
     * 保存配置信息
     * @param schoolId
     * @param type
     * @param identifyList
     * @param personList
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(String schoolId, Byte type, List<String> identifyList, List<String> personList) {
        Date now = new Date();
        IrReportConfig curConfig = irReportConfigMapper.selectByPrimaryKey(schoolId);
        if (curConfig == null) {
            curConfig = new IrReportConfig();
            curConfig.setSchoolId(schoolId);
            curConfig.setCreateAt(now);
            curConfig.setUpdateAt(now);
            if (type == 0) {
                if (!CollectionUtils.isEmpty(identifyList)) {
                    curConfig.setStudentReportIdentify(String.join(",", identifyList));
                } else {
                    curConfig.setStudentReportIdentify("");
                }
                if (!CollectionUtils.isEmpty(personList)) {
                    curConfig.setStudentReportUserIds(String.join(",", personList));
                } else {
                    curConfig.setStudentReportUserIds("");
                }
            } else if (type == 1) {
                if (!CollectionUtils.isEmpty(identifyList)) {
                    curConfig.setStaffReportIdentify(String.join(",", identifyList));
                } else {
                    curConfig.setStaffReportIdentify("");
                }
                if (!CollectionUtils.isEmpty(personList)) {
                    curConfig.setStaffReportUserIds(String.join(",", personList));
                } else {
                    curConfig.setStaffReportUserIds("");
                }
            }
            irReportConfigMapper.insert(curConfig);
        } else {
            curConfig.setUpdateAt(now);
            if (type == 0) {
                if (!CollectionUtils.isEmpty(identifyList)) {
                    curConfig.setStudentReportIdentify(String.join(",", identifyList));
                } else {
                    curConfig.setStudentReportIdentify("");
                }
                if (!CollectionUtils.isEmpty(personList)) {
                    curConfig.setStudentReportUserIds(String.join(",", personList));
                } else {
                    curConfig.setStudentReportUserIds("");
                }
            } else if (type == 1) {
                if (!CollectionUtils.isEmpty(identifyList)) {
                    curConfig.setStaffReportIdentify(String.join(",", identifyList));
                } else {
                    curConfig.setStaffReportIdentify("");
                }
                if (!CollectionUtils.isEmpty(personList)) {
                    curConfig.setStaffReportUserIds(String.join(",", personList));
                } else {
                    curConfig.setStaffReportUserIds("");
                }
            }
            irReportConfigMapper.updateByPrimaryKey(curConfig);
        }
    }

    /**
     * 根据学校id查询，若没有数据，进行初始化
     * @param schoolId
     * @return
     */
    @Transactional(rollbackFor =  Exception.class)
    public IrReportConfig findWithInitBySchoolId(String schoolId) {
        IrReportConfig curConfig = irReportConfigMapper.selectByPrimaryKey(schoolId);
        if (curConfig == null) {
            Date now = new Date();
            curConfig = new IrReportConfig();
            curConfig.setSchoolId(schoolId);
            curConfig.setCreateAt(now);
            curConfig.setUpdateAt(now);
            irReportConfigMapper.insert(curConfig);
        }
        return curConfig;
    }
}
