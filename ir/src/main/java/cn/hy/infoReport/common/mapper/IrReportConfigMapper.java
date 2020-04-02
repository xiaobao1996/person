package cn.hy.infoReport.common.mapper;

import cn.hy.infoReport.common.entity.IrReportConfig;
import cn.hy.infoReport.common.entity.IrReportConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IrReportConfigMapper {
    long countByExample(IrReportConfigExample example);

    int deleteByExample(IrReportConfigExample example);

    int deleteByPrimaryKey(String schoolId);

    int insert(IrReportConfig record);

    int insertSelective(IrReportConfig record);

    List<IrReportConfig> selectByExample(IrReportConfigExample example);

    IrReportConfig selectByPrimaryKey(String schoolId);

    int updateByExampleSelective(@Param("record") IrReportConfig record, @Param("example") IrReportConfigExample example);

    int updateByExample(@Param("record") IrReportConfig record, @Param("example") IrReportConfigExample example);

    int updateByPrimaryKeySelective(IrReportConfig record);

    int updateByPrimaryKey(IrReportConfig record);
}