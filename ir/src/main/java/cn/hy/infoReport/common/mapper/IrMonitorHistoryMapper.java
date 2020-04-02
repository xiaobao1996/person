package cn.hy.infoReport.common.mapper;

import cn.hy.infoReport.common.entity.IrMonitorHistory;
import cn.hy.infoReport.common.entity.IrMonitorHistoryExample;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface IrMonitorHistoryMapper {
    long countByExample(IrMonitorHistoryExample example);

    int deleteByExample(IrMonitorHistoryExample example);

    int deleteByPrimaryKey(String id);

    int insert(IrMonitorHistory record);

    int insertSelective(IrMonitorHistory record);

    List<IrMonitorHistory> selectByExample(IrMonitorHistoryExample example);

    IrMonitorHistory selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IrMonitorHistory record, @Param("example") IrMonitorHistoryExample example);

    int updateByExample(@Param("record") IrMonitorHistory record, @Param("example") IrMonitorHistoryExample example);

    int updateByPrimaryKeySelective(IrMonitorHistory record);

    int updateByPrimaryKey(IrMonitorHistory record);

    /**
     * 获取体温枪数据
     * @param schoolId
     * @param startDate
     * @param endDate
     * @param name
     * @param classId
     * @return
     */
    List<IrMonitorHistory> findTbListBy(@Param("schoolId") String schoolId, @Param("startDate") Date startDate, @Param("endDate") Date endDate,
                                        @Param("name") String name, @Param("classId") String classId);
}
