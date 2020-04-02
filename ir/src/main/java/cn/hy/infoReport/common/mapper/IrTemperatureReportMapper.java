package cn.hy.infoReport.common.mapper;

import cn.hy.infoReport.common.entity.IrTemperatureReport;
import cn.hy.infoReport.common.entity.IrTemperatureReportExample;
import cn.hy.infoReport.module.business.vo.IrTemperatureReportSimplifyVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface IrTemperatureReportMapper {
    long countByExample(IrTemperatureReportExample example);

    int deleteByExample(IrTemperatureReportExample example);

    int deleteByPrimaryKey(String id);

    int insert(IrTemperatureReport record);

    int insertSelective(IrTemperatureReport record);

    List<IrTemperatureReport> selectByExample(IrTemperatureReportExample example);

    IrTemperatureReport selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IrTemperatureReport record, @Param("example") IrTemperatureReportExample example);

    int updateByExample(@Param("record") IrTemperatureReport record, @Param("example") IrTemperatureReportExample example);

    int updateByPrimaryKeySelective(IrTemperatureReport record);

    int updateByPrimaryKey(IrTemperatureReport record);

    /**
     * 条件查询
     * @param userName
     * @param officeId
     * @param schoolId
     * @param temperature
     * @param otherSymptom
     * @param currentLocation
     * @param contact
     * @param identity
     * @param province
     * @param city
     * @param area
     * @param mobile
     * @param startTime
     * @param endTime
     * @param arriveOtherArea
     * @return
     */
    List<IrTemperatureReport> list(@Param("userName") String userName,
                                   @Param("officeId") String officeId,
                                   @Param("schoolId") String schoolId,
                                   @Param("temperature") BigDecimal temperature,
                                   @Param("otherSymptom") Byte otherSymptom,
                                   @Param("currentLocation") String currentLocation,
                                   @Param("contact") Byte contact,
                                   @Param("identity") Byte identity,
                                   @Param("province") String province,
                                   @Param("city") String city,
                                   @Param("area") String area,
                                   @Param("mobile") String mobile,
                                   @Param("startTime") Date startTime,
                                   @Param("endTime") Date endTime,
                                   @Param("arriveOtherArea") Byte arriveOtherArea);

    /**
     * 根据学校id,用户id集合，提交日期介于，体温大于等于
     * @param schoolId
     * @param userIdList
     * @param startDate
     * @param endDate
     * @return
     */
    List<IrTemperatureReportSimplifyVo> findAbnormalSimplifyInfo(@Param("schoolId") String schoolId, @Param("userIdList") List<String> userIdList, @Param("startDate") Date startDate,
                                                                 @Param("endDate") Date endDate, @Param("abnormal") Byte abnormal);

    /**
     * 根据条件查询异常的数量
     * @param userName
     * @param officeId
     * @param schoolId
     * @param temperature
     * @param otherSymptom
     * @param currentLocation
     * @param contact
     * @param identity
     * @param province
     * @param city
     * @param area
     * @param mobile
     * @param startTime
     * @param endTime
     * @param arriveOtherArea
     * @return
     */
    Long findAbunusualCountByIdentityAndSchoolId(@Param("userName") String userName,
                                                 @Param("officeId") String officeId,
                                                 @Param("schoolId") String schoolId,
                                                 @Param("temperature") BigDecimal temperature,
                                                 @Param("otherSymptom") Byte otherSymptom,
                                                 @Param("currentLocation") String currentLocation,
                                                 @Param("contact") Byte contact,
                                                 @Param("identity") Byte identity,
                                                 @Param("province") String province,
                                                 @Param("city") String city,
                                                 @Param("area") String area,
                                                 @Param("mobile") String mobile,
                                                 @Param("startTime") Date startTime,
                                                 @Param("endTime") Date endTime,
                                                 @Param("arriveOtherArea") Byte arriveOtherArea,
                                                 @Param("abnormal") Byte abnormal);
}
