package cn.hy.infoReport.common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author
 */
public class SysOffice implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 父级id
     */
    private String parentId;

    /**
     * 所有父级id","分割
     */
    private String parentIds;

    /**
     * 本级排序号（升序）
     */
    private Integer treeSort;

    /**
     * 所有级别排序号
     */
    private String treeSorts;

    /**
     * 是否最末级
     */
    private Byte treeLeaf;

    /**
     * 层次级别
     */
    private Integer treeLevel;

    /**
     * 全节点名
     */
    private String treeNames;

    /**
     * 机构代码
     */
    private String officeCode;

    /**
     * 机构名称
     */
    private String officeName;

    /**
     * 机构类型 0:学校 1:部门
     */
    private Byte officeType;

    /**
     * 是否是连锁学校 0:否，1:是
     */
    private Byte isChain;

    /**
     * 机构学校id
     */
    private String schoolId;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 更新时间
     */
    private Date updateAt;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 状态（0删除 1正常 2停用）
     */
    private Byte status;

    /**
     * 父级机构信息
     */
    private SysOffice parentOffice;

    /**
     * 父级机构名称
     */
    private String parentOfficeName;


    /**
     * 是否可以访问
     */
    private Boolean access = true;
    /**
     * 学生用户id
     */
    private String studentId;

    private Set<String> roleIdSet;



    /*************业务数据*************/
    private String userId;

    /**
     * 班级信息
     */
    private BusiClass busiClass;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public Integer getTreeSort() {
        return treeSort;
    }

    public void setTreeSort(Integer treeSort) {
        this.treeSort = treeSort;
    }

    public String getTreeSorts() {
        return treeSorts;
    }

    public void setTreeSorts(String treeSorts) {
        this.treeSorts = treeSorts;
    }

    public Byte getTreeLeaf() {
        return treeLeaf;
    }

    public void setTreeLeaf(Byte treeLeaf) {
        this.treeLeaf = treeLeaf;
    }

    public Integer getTreeLevel() {
        return treeLevel;
    }

    public void setTreeLevel(Integer treeLevel) {
        this.treeLevel = treeLevel;
    }

    public String getTreeNames() {
        return treeNames;
    }

    public void setTreeNames(String treeNames) {
        this.treeNames = treeNames;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public Byte getOfficeType() {
        return officeType;
    }

    public void setOfficeType(Byte officeType) {
        this.officeType = officeType;
    }

    public Byte getIsChain() {
        return isChain;
    }

    public void setIsChain(Byte isChain) {
        this.isChain = isChain;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public Boolean getAccess() {
        return access;
    }

    public void setAccess(Boolean access) {
        this.access = access;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public SysOffice getParentOffice() {
        return parentOffice;
    }

    public void setParentOffice(SysOffice parentOffice) {
        this.parentOffice = parentOffice;
    }

    public String getParentOfficeName() {
        return parentOfficeName;
    }

    public void setParentOfficeName(String parentOfficeName) {
        this.parentOfficeName = parentOfficeName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Set<String> getRoleIdSet() {
        return roleIdSet;
    }

    public void setRoleIdSet(Set<String> roleIdSet) {
        this.roleIdSet = roleIdSet;
    }

    public BusiClass getBusiClass() {
        return busiClass;
    }

    public void setBusiClass(BusiClass busiClass) {
        this.busiClass = busiClass;
    }
}
