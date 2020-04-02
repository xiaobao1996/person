package cn.hy.infoReport.common.mapper;

import cn.hy.infoReport.common.entity.IrUserArriveArea;
import cn.hy.infoReport.common.entity.IrUserArriveAreaExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IrUserArriveAreaMapper {
    long countByExample(IrUserArriveAreaExample example);

    int deleteByExample(IrUserArriveAreaExample example);

    int deleteByPrimaryKey(String id);

    int insert(IrUserArriveArea record);

    int insertSelective(IrUserArriveArea record);

    List<IrUserArriveArea> selectByExample(IrUserArriveAreaExample example);

    IrUserArriveArea selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IrUserArriveArea record, @Param("example") IrUserArriveAreaExample example);

    int updateByExample(@Param("record") IrUserArriveArea record, @Param("example") IrUserArriveAreaExample example);

    int updateByPrimaryKeySelective(IrUserArriveArea record);

    int updateByPrimaryKey(IrUserArriveArea record);
}
