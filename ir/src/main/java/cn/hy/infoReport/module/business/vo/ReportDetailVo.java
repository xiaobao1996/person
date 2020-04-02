package cn.hy.infoReport.module.business.vo;

import cn.hy.infoReport.common.entity.PmsStaff;
import cn.hy.infoReport.common.entity.SysRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 通知设置详情vo
 */
@Setter
@Getter
public class ReportDetailVo {

    private List<ReportDetailItemVo> identifyList;
    private List<ReportDetailItemVo> personList;

    /**
     * 来自于身份
     */
    public void fromIdentify(String identifyStr, List<SysRole> jobTypeList) {
        if (StringUtils.isBlank(identifyStr)) {
            return;
        }
        List<String> identifyCodeList = Arrays.stream(identifyStr.split(",")).distinct().collect(Collectors.toList());
        identifyList = new ArrayList<>(identifyCodeList.size() * 2);
        for (String isTmp : identifyCodeList) {
            for (SysRole srTmp : jobTypeList) {
                if (isTmp.equals(srTmp.getId())) {
                    identifyList.add(new ReportDetailItemVo(isTmp, srTmp.getRoleName()));
                    break;
                }
            }
        }
    }

    /**
     * 来自于身份
     */
    public void fromPerson(String personStr, List<PmsStaff> staffList) {
        if (StringUtils.isBlank(personStr)) {
            return;
        }
        if (CollectionUtils.isEmpty(staffList)) {
            return;
        }

        List<String> personIdList = Arrays.stream(personStr.split(",")).distinct().collect(Collectors.toList());
        personList = new ArrayList<>(personIdList.size() * 2);
        for (String piTmp : personIdList) {
            for (PmsStaff psTmp : staffList) {
                if (psTmp.getUserId().equals(piTmp)) {
                    ReportDetailItemVo itemVo = new ReportDetailItemVo(psTmp.getUserId(), psTmp.getName(), psTmp.getJobTypeNameList());
                    personList.add(itemVo);
                    break;
                }
            }
        }
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReportDetailItemVo {
        private String code;
        private String name;
        private List<String> jobTypeList;

        public ReportDetailItemVo(String code, String name) {
            this.code = code;
            this.name = name;
        }
    }
}
