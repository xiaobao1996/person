package cn.hy.infoReport.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author
 */
public class BusiPlace implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 场所名称
     */
    private String name;

    /**
     * 类型 （数据字典：普通班级为1）
     */
    private String type;

    private String typeLabel;

    /**
     * 对应班级id
     */
    private String classId;

    /**
     * 对应班级所属届差值
     */
    private Integer classDistanceYear;

    /**
     * 班号
     */
    private String classCode;

    /**
     * 具体地点
     */
    private String place;

    /**
     * 学校id
     */
    private String schoolId;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    private Date updateAt;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 状态（0删除 1正常  2停用）
     */
    private Byte status;

    /***  业务数据  ***/

    /**
     * 对应班级名称
     */
    private String className;

    /**
     * 年级
     */
    private Integer grade;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Integer getClassDistanceYear() {
        return classDistanceYear;
    }

    public void setClassDistanceYear(Integer classDistanceYear) {
        this.classDistanceYear = classDistanceYear;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(String typeLabel) {
        this.typeLabel = typeLabel;
    }
}
