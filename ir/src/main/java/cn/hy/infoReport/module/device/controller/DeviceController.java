package cn.hy.infoReport.module.device.controller;

import cn.hy.infoReport.common.entity.PmsStaff;
import cn.hy.infoReport.common.entity.PmsStudent;
import cn.hy.infoReport.common.service.IrMonitorHistoryService;
import cn.hy.infoReport.common.service.PmsStaffService;
import cn.hy.infoReport.common.service.PmsStudentService;
import cn.hy.infoReport.common.utils.ValidatorUtils;
import cn.hy.infoReport.common.vo.Result;
import cn.hy.infoReport.module.device.vo.DeviceMonitorDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备controller
 */
@RequestMapping("/pub/device")
@RestController
public class DeviceController {

    @Autowired
    private IrMonitorHistoryService irMonitorHistoryService;
    @Autowired
    private PmsStaffService pmsStaffService;
    @Autowired
    private PmsStudentService pmsStudentService;

    /**
     * 保存设备监控数据
     * @return
     */
    @RequestMapping("/saveMonitorData")
    public Result saveDeviceMonitorData(@RequestParam String schoolId, DeviceMonitorDataVo monitorDataVo) {
        ValidatorUtils.validateEntity(monitorDataVo);
        byte type = 0;
        PmsStaff pmsStaff = pmsStaffService.findBySchoolIdAndUserId(schoolId, monitorDataVo.getUserId());
        PmsStudent student = null;
        if (pmsStaff != null) {
            type = 1;
            monitorDataVo.setUsername(pmsStaff.getName());
        } else {
            student = pmsStudentService.findBySchoolIdAndUserId(schoolId, monitorDataVo.getUserId());
            if (student == null) {
                return Result.error("监测用户非本校人员", null);
            }
            monitorDataVo.setUsername(student.getName());
        }

        irMonitorHistoryService.saveDeviceData(schoolId, type, monitorDataVo, pmsStaff, student);
        return Result.success(null);
    }

}
