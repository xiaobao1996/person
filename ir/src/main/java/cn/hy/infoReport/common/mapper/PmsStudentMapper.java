package cn.hy.infoReport.common.mapper;

import cn.hy.infoReport.common.entity.PmsStudent;
import cn.hy.infoReport.common.entity.PmsStudentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PmsStudentMapper {
    long countByExample(PmsStudentExample example);

    int deleteByExample(PmsStudentExample example);

    int deleteByPrimaryKey(String id);

    int insert(PmsStudent record);

    int insertSelective(PmsStudent record);

    List<PmsStudent> selectByExample(PmsStudentExample example);

    PmsStudent selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") PmsStudent record, @Param("example") PmsStudentExample example);

    int updateByExample(@Param("record") PmsStudent record, @Param("example") PmsStudentExample example);

    int updateByPrimaryKeySelective(PmsStudent record);

    int updateByPrimaryKey(PmsStudent record);

    /**
     * 根据学校id查询学生信息，同时进行排序
     * @param schoolId
     * @return
     */
    List<PmsStudent> findWithSortBySchoolId(@Param("schoolId") String schoolId);
}
