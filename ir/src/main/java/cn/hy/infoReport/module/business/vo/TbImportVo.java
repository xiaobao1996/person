package cn.hy.infoReport.module.business.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.hy.infoReport.common.entity.PmsStudent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 天波导入数据vo
 */
@Setter
@Getter
public class TbImportVo {

    @Excel(name = "班级")
    private String className;
    @Excel(name = "日期")
    private String monitorDateLabel;

    private Date monitorDate;

    @Excel(name = "学生名称")
    private String name;
    @Excel(name = "性别")
    private String sex;
    @Excel(name = "是否异常")
    private String isAbnormal;
    @Excel(name = "体温明细")
    private String temperatureDetail;

    private List<Item> temperatureDetailList;

    private PmsStudent pmsStudent;

    private static final String SEPARATOR = "|";
    private static final String TEMPERATURE = "℃";

//    private static final String TEMPERATURE_REGEX = "^\\d{1,3}{0,4}℃$";

//    public static void main(String[] args) {
//        System.out.println("36..℃".matches(TEMPERATURE_REGEX));
//    }

    public void setTemperatureDetail(String temperatureDetail) {
        if (StringUtils.isBlank(this.temperatureDetail)) {
            this.temperatureDetail = temperatureDetail;
        } else {
            this.temperatureDetail += SEPARATOR + temperatureDetail;
        }
    }

    public void setTemperatureDetailInit(String temperatureDetail) {
        this.temperatureDetail = temperatureDetail;
    }

    /**
     * 获取体温明细数据
     */
    public void buildData() {
        if (StringUtils.isBlank(temperatureDetail) || !temperatureDetail.contains("|")) {
            return;
        }

        temperatureDetail = temperatureDetail.substring(0, temperatureDetail.lastIndexOf(TEMPERATURE));
        temperatureDetail = temperatureDetail.replaceAll(TEMPERATURE, "");
        String[] dataList = temperatureDetail.split("\\|");
        if (dataList.length > 1) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            for (int i = 0; i < dataList.length; i+=2) {
                try {
                    Date monitorDateTime = dateFormat.parse(dataList[i]);
                    BigDecimal temperature = new BigDecimal(dataList[i + 1]).stripTrailingZeros().setScale(1, RoundingMode.HALF_UP);
                    if (monitorDateTime != null && temperature != null) {
                        if (temperatureDetailList == null) {
                            temperatureDetailList = new ArrayList<>(10);
                        }
                        temperatureDetailList.add(new Item(dataList[i], monitorDateTime, temperature));
                    }
                } catch (Exception e) {
                }
            }
        }

    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {

        private String monitorDateTimeStr;
        private Date monitorDateTime;
        private BigDecimal temperature;

    }

}
