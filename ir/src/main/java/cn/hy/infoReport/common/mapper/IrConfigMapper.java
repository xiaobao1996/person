package cn.hy.infoReport.common.mapper;

import cn.hy.infoReport.common.entity.IrConfig;
import cn.hy.infoReport.common.entity.IrConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IrConfigMapper {
    long countByExample(IrConfigExample example);

    int deleteByExample(IrConfigExample example);

    int deleteByPrimaryKey(String schoolId);

    int insert(IrConfig record);

    int insertSelective(IrConfig record);

    List<IrConfig> selectByExample(IrConfigExample example);

    IrConfig selectByPrimaryKey(String schoolId);

    int updateByExampleSelective(@Param("record") IrConfig record, @Param("example") IrConfigExample example);

    int updateByExample(@Param("record") IrConfig record, @Param("example") IrConfigExample example);

    int updateByPrimaryKeySelective(IrConfig record);

    int updateByPrimaryKey(IrConfig record);
}