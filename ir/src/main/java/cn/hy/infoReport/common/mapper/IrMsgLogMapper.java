package cn.hy.infoReport.common.mapper;

import cn.hy.infoReport.common.entity.IrMsgLog;
import cn.hy.infoReport.common.entity.IrMsgLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IrMsgLogMapper {
    long countByExample(IrMsgLogExample example);

    int deleteByExample(IrMsgLogExample example);

    int deleteByPrimaryKey(String id);

    int insert(IrMsgLog record);

    int insertSelective(IrMsgLog record);

    List<IrMsgLog> selectByExample(IrMsgLogExample example);

    IrMsgLog selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IrMsgLog record, @Param("example") IrMsgLogExample example);

    int updateByExample(@Param("record") IrMsgLog record, @Param("example") IrMsgLogExample example);

    int updateByPrimaryKeySelective(IrMsgLog record);

    int updateByPrimaryKey(IrMsgLog record);
}
