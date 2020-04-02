package cn.hy.infoReport.common.task;

import cn.hy.infoReport.common.constant.ProjectConstant;
import cn.hy.infoReport.common.service.PmsStaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 教职工同步任务
 */
@Slf4j
@Component
public class StaffSyncTask {

    @Autowired
    private PmsStaffService pmsStaffService;


    public static ReentrantLock lock = new ReentrantLock();


    /**
     * 全量同步教职工信息
     */
    @Scheduled(cron = "0 30 3 1/1 * ?")
    public void sync() {
        log.warn("同步教职工信息开始");
        if (CollectionUtils.isEmpty(ProjectConstant.pmsSchoolIdSet)) {
            return;
        }
        long timeMillis = System.currentTimeMillis();
        try {
            lock.lock();
            for (String schoolId : ProjectConstant.pmsSchoolIdSet) {
                try {
                    pmsStaffService.syncAllBySchoolId(schoolId, timeMillis);
                } catch (Exception e) {
                    log.error("同步学校id:{}教职工数据失败", schoolId);
                }
            }
        } finally {
            lock.unlock();
        }
        log.warn("同步教职工信息结束");
    }

}
