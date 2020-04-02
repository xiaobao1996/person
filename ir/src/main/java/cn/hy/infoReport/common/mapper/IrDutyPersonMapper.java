package cn.hy.infoReport.common.mapper;

import cn.hy.infoReport.common.entity.IrDutyPerson;
import cn.hy.infoReport.common.entity.IrDutyPersonExample;

import java.util.Date;
import java.util.List;

import cn.hy.infoReport.module.business.vo.IrDutyPersonVo;
import org.apache.ibatis.annotations.Param;

public interface IrDutyPersonMapper {
    long countByExample(IrDutyPersonExample example);

    int deleteByExample(IrDutyPersonExample example);

    int deleteByPrimaryKey(String id);

    int insert(IrDutyPerson record);

    int insertSelective(IrDutyPerson record);

    List<IrDutyPerson> selectByExample(IrDutyPersonExample example);

    IrDutyPerson selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IrDutyPerson record, @Param("example") IrDutyPersonExample example);

    int updateByExample(@Param("record") IrDutyPerson record, @Param("example") IrDutyPersonExample example);

    int updateByPrimaryKeySelective(IrDutyPerson record);

    int updateByPrimaryKey(IrDutyPerson record);

    List<IrDutyPersonVo> findPersonPageBy(@Param("schoolId") String schoolId, @Param("startDate") Date startDate, @Param("endDate")Date endDate,@Param("name") String name,@Param("mobile") String mobile);
}
