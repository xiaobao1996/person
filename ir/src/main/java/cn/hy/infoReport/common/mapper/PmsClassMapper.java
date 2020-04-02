package cn.hy.infoReport.common.mapper;

import cn.hy.infoReport.common.entity.PmsClass;
import cn.hy.infoReport.common.entity.PmsClassExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsClassMapper {
    long countByExample(PmsClassExample example);

    int deleteByExample(PmsClassExample example);

    int deleteByPrimaryKey(String id);

    int insert(PmsClass record);

    int insertSelective(PmsClass record);

    List<PmsClass> selectByExample(PmsClassExample example);

    PmsClass selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") PmsClass record, @Param("example") PmsClassExample example);

    int updateByExample(@Param("record") PmsClass record, @Param("example") PmsClassExample example);

    int updateByPrimaryKeySelective(PmsClass record);

    int updateByPrimaryKey(PmsClass record);

    /**
     * 查找该学校下所有的机构
     * @param schoolId
     * @return
     */
    List<PmsClass> findClassBySchoolId(String schoolId);
}
