package cn.hy.infoReport.common.mapper;

import cn.hy.infoReport.common.entity.IrHealthMonitor;
import cn.hy.infoReport.common.entity.IrHealthMonitorExample;
import cn.hy.infoReport.module.business.vo.CalcVo;
import cn.hy.infoReport.module.business.vo.IrHealthMonitorSimplifyVo;
import cn.hy.infoReport.module.business.vo.StaffHealthMonitorVo;
import cn.hy.infoReport.module.business.vo.StudentHealthMonitorVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface IrHealthMonitorMapper {
    long countByExample(IrHealthMonitorExample example);

    int deleteByExample(IrHealthMonitorExample example);

    int deleteByPrimaryKey(String id);

    int insert(IrHealthMonitor record);

    int insertSelective(IrHealthMonitor record);

    List<IrHealthMonitor> selectByExample(IrHealthMonitorExample example);

    IrHealthMonitor selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IrHealthMonitor record, @Param("example") IrHealthMonitorExample example);

    int updateByExample(@Param("record") IrHealthMonitor record, @Param("example") IrHealthMonitorExample example);

    int updateByPrimaryKeySelective(IrHealthMonitor record);

    int updateByPrimaryKey(IrHealthMonitor record);

    List<StudentHealthMonitorVo> findStudentHealthMonitorBy(@Param("schoolId") String schoolId, @Param("monitorDate") Date monitorDate, @Param("name") String name,
                                                            @Param("classId") String classId, @Param("healthStatus") Byte healthStatus, @Param("checkStatus") Byte checkStatus);

    /**
     * 根据系统设置的温度，全部更新健康状态
     * @param schoolId
     * @param temperatureLowLimit
     */
    void updateHealthStatus(@Param("schoolId") String schoolId, @Param("lowLimit") BigDecimal temperatureLowLimit);

    /**
     * 获取教职工监测记录
     * @param schoolId
     * @param monitorDate
     * @param name
     * @param mobile
     * @param healthStatus
     * @param checkStatus
     * @return
     */
    List<StaffHealthMonitorVo> findStaffHealthMonitorBy(@Param("schoolId") String schoolId, @Param("monitorDate") Date monitorDate, @Param("name") String name,
                                                            @Param("mobile") String mobile, @Param("healthStatus") Byte healthStatus, @Param("checkStatus") Byte checkStatus);

    /**
     * 获取教职工统计
     * @param schoolId
     * @param monitorDate
     * @return
     */
    CalcVo staffCalc(@Param("schoolId") String schoolId, @Param("monitorDate") Date monitorDate);

    /**
     * 获取学生统计
     * @param schoolId
     * @param monitorDate
     * @return
     */
    CalcVo studentCalc(@Param("schoolId") String schoolId, @Param("monitorDate") Date monitorDate);

    /**
     * 根据学校id,用户id集合，监测日期介于，健康状态查询精简信息
     * @param schoolId
     * @param userIdList
     * @param monitorDateStart
     * @param monitorDateEnd
     * @param healthStatus
     * @return
     */
    List<IrHealthMonitorSimplifyVo> findAbnormalSimplifyInfo(@Param("schoolId") String schoolId, @Param("userIdList") List<String> userIdList, @Param("monitorDateStart") Date monitorDateStart,
                                                             @Param("monitorDateEnd") Date monitorDateEnd, @Param("healthStatus") byte healthStatus);
}
