package cn.hy.infoReport.common.task;

import cn.hy.infoReport.common.constant.ProjectConstant;
import cn.hy.infoReport.common.service.PmsStudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 学生同步任务
 */
@Slf4j
@Component
public class StudentSyncTask {

    @Autowired
    private PmsStudentService pmsStudentService;


    public static ReentrantLock lock = new ReentrantLock();


    /**
     * 全量同步教职工信息
     */
    @Scheduled(cron = "0 10 3 1/1 * ?")
    public void sync() {
        log.warn("同步学生信息开始");
        if (CollectionUtils.isEmpty(ProjectConstant.pmsSchoolIdSet)) {
            return;
        }
        long timeMillis = System.currentTimeMillis();
        try {
            lock.lock();
            for (String schoolId : ProjectConstant.pmsSchoolIdSet) {
                try {
                    pmsStudentService.syncAllBySchoolId(schoolId, timeMillis);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("同步学校id:{}学生数据失败", schoolId);
                }
            }
        } finally {
            lock.unlock();
        }
        log.warn("同步学生信息结束");
    }

}
