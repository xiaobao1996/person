package cn.hy.infoReport.module.business.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 监测日统计vo
 */
@Setter
@Getter
public class DailyMonitorCalcVo {

    private Date now;
    private Long totalCount = 0L;
    private Long todayAbnormalCount = 0L;
    private Long yesterdayAbnormalCount = 0L;
    private Long before3DaysAbnormalCount = 0L;

    private DailyMonitorCalcItemVo todayCalcItem;
    private DailyMonitorCalcItemVo yesterdayCalcItem;
    private DailyMonitorCalcItemVo before3DayCalcItem;

    private List<DailyMonitorCalcItemVo> calcItemList;

    /**
     * 初始化14天的item数据
     */
    public void init14DayItemList() {
        this.calcItemList = new ArrayList<>(28);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -13);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 14; i ++) {
            Date date = calendar.getTime();
            DailyMonitorCalcItemVo calcItemVo = new DailyMonitorCalcItemVo(date, dateFormat.format(date));
            this.calcItemList.add(calcItemVo);
            if (i == 11) {
                this.before3DayCalcItem = calcItemVo;
            } else if (i == 12) {
                this.yesterdayCalcItem = calcItemVo;
            } else if (i == 13) {
                this.todayCalcItem = calcItemVo;
            }
            calendar.add(Calendar.DATE, 1);
        }
    }

    /**
     * 重新构造vo
     */
    public void rebuildVo() {
        if (!CollectionUtils.isEmpty(this.calcItemList)) {
            this.calcItemList.forEach(ciTmp -> {
                ciTmp.setAbnormalCount((long) (CollectionUtils.isEmpty(ciTmp.getAbnormalUserIdSet()) ? 0 : ciTmp.getAbnormalUserIdSet().size()));
                ciTmp.setAbnormalUserIdSet(null);
                ciTmp.setMonitorDate(null);
            });
        }
        if (this.todayCalcItem != null) {
            this.todayAbnormalCount = this.todayCalcItem.abnormalCount;
            this.todayCalcItem = null;
        }

        if (this.yesterdayCalcItem != null) {
            this.yesterdayAbnormalCount = this.yesterdayCalcItem.abnormalCount;
            this.yesterdayCalcItem = null;
        }

        if (this.before3DayCalcItem != null) {
            this.before3DaysAbnormalCount = this.before3DayCalcItem.abnormalCount;
            this.before3DayCalcItem = null;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class DailyMonitorCalcItemVo {
        private Date monitorDate;
        private String monitorDateStr;
        private Long abnormalCount = 0L;
        private Set<String> abnormalUserIdSet;

        public DailyMonitorCalcItemVo(Date monitorDate) {
            this.monitorDate = monitorDate;
        }

        public DailyMonitorCalcItemVo(Date monitorDate, String monitorDateStr) {
            this.monitorDate = monitorDate;
            this.monitorDateStr = monitorDateStr;
        }
    }
}
