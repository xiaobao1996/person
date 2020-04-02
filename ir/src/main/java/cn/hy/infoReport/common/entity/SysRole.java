package cn.hy.infoReport.common.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author
 */
@Setter
@Getter
public class SysRole implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色排序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 系统内置（1是 0否）
     */
    private Byte sysStatus;

    /**
     * 注册审核状态（0:不需要审核，1:人工审核，2:自动审核）
     */
    private Byte auditStatus;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 数据范围权限类型 0:自定义范围权限，1:全部数据范围权限
     */
    private Byte dataScope;

    /**
     * 角色组类型 （0:其他，1:教师，2:家长，3:学生，4:行政人员）
     */
    private Byte groupType;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateAt;

    /**
     * 学校id
     */
    private String schoolId;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 状态（0删除 1正常  2停用）
     */
    private Byte status;


    /**
     * 是否对外开放选择（0:否，1:是）
     */

    private Byte openStatus;


    private static final long serialVersionUID = 1L;



}
