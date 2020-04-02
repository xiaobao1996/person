package cn.hy.infoReport.common.mapper;

import cn.hy.infoReport.common.entity.PmsStaff;
import cn.hy.infoReport.common.entity.PmsStaffExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PmsStaffMapper {
    long countByExample(PmsStaffExample example);

    int deleteByExample(PmsStaffExample example);

    int deleteByPrimaryKey(String id);

    int insert(PmsStaff record);

    int insertSelective(PmsStaff record);

    List<PmsStaff> selectByExample(PmsStaffExample example);

    PmsStaff selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") PmsStaff record, @Param("example") PmsStaffExample example);

    int updateByExample(@Param("record") PmsStaff record, @Param("example") PmsStaffExample example);

    int updateByPrimaryKeySelective(PmsStaff record);

    int updateByPrimaryKey(PmsStaff record);
}