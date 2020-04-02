package cn.hy.infoReport.common.entity;


import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author
 */
public class SysUser implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 登录名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 登录密码
     */
    @JSONField(serialize=false)
    private String password;

    /**
     * 用户姓名
     */
    private String realName;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 电子邮箱
     */

    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 家庭电话
     */
    private String familyPhone;

    /**
     * 办公电话
     */
    private String phone;

    /**
     * 用户性别
     */
    private Byte sex;

    /**
     * 头像路径
     */
    private String avatar;

    /**
     * 绑定的微信openId
     */
    private String wxOpenid;

    /**
     * 地址
     */
    private String address;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateAt;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 状态（0删除 1正常 2停用 3冻结）
     */
    private Byte status;

    /**
     * 数据来源（0:用户自己注册，1:后台添加，2:数据导入，3:系统内置）
     */
    private Byte source;


    /**
     * 护照
     */
    private String passport;

    /**
     * 证件类型（0:身份证，1:护照）
     */
    private Byte papersType;

    /**
     * 证件类型名称
     */
    private String papersTypeName;

    /**
     * 证件号码
     */
    private String papersNo;


    /**
     * 最后登陆时间
     */
    private Date lastLoginDate;

    /**
     * 机构id
     */
    private String officeId;
    /**
     * 机构名称
     */
    private String officeName;

    /**
     * 机构层级名称
     */
    private String officeTreeNames;

    /**
     * 角色信息
     */
    private List sysRoleList;

    /**
     * 部门信息
     */
    private List sysOfficeList;

    /**
     * 孩子信息
     */
    private List<SysUser> children;

    /**
     * 角色部门关联id
     */
    private String officeUserDetailId;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色名称字符串
     */
    private String roleNamesStr;
    /**
     * 关系名称
     */
    private String relationTypeName;


    /**
     * 关系列表
     */
    private List<SysUser> relationList;
    /**
     * 部门id集合
     */
    private List<String> officeIds;

    private Boolean reported = false;

    private IrTemperatureReport temperatureReport;

    public List getSysOfficeList() {
        return sysOfficeList;
    }

    public void setSysOfficeList(List sysOfficeList) {
        this.sysOfficeList = sysOfficeList;
    }

    public List<String> getOfficeIds() {
        return officeIds;
    }

    public void setOfficeIds(List<String> officeIds) {
        this.officeIds = officeIds;
    }

    public List getSysRoleList() {
        return sysRoleList;
    }

    public void setSysRoleList(List sysRoleList) {
        this.sysRoleList = sysRoleList;
    }

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getWxOpenid() {
        return wxOpenid;
    }

    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
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

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getRoleNamesStr() {
        return roleNamesStr;
    }

    public void setRoleNamesStr(String roleNamesStr) {
        this.roleNamesStr = roleNamesStr;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getOfficeTreeNames() {
        return officeTreeNames;
    }

    public void setOfficeTreeNames(String officeTreeNames) {
        this.officeTreeNames = officeTreeNames;
    }

//    public List<SysRole> getSysRoleList() {
//        return sysRoleList;
//    }
//
//    public void setSysRoleList(List<SysRole> sysRoleList) {
//        this.sysRoleList = sysRoleList;
//    }

    public String getOfficeUserDetailId() {
        return officeUserDetailId;
    }

    public void setOfficeUserDetailId(String officeUserDetailId) {
        this.officeUserDetailId = officeUserDetailId;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public Byte getPapersType() {
        return papersType;
    }

    public void setPapersType(Byte papersType) {
        this.papersType = papersType;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
    }

//    public List<SysOffice> getSysOfficeList() {
//        return sysOfficeList;
//    }
//
//    public void setSysOfficeList(List<SysOffice> sysOfficeList) {
//        this.sysOfficeList = sysOfficeList;
//    }

    public List<SysUser> getChildren() {
        return children;
    }

    public void setChildren(List<SysUser> children) {
        this.children = children;
    }

    public String getPapersNo() {
        return papersNo;
    }

    public void setPapersNo(String papersNo) {
        this.papersNo = papersNo;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPapersTypeName() {
        return papersTypeName;
    }

    public void setPapersTypeName(String papersTypeName) {
        this.papersTypeName = papersTypeName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFamilyPhone() {
        return familyPhone;
    }

    public void setFamilyPhone(String familyPhone) {
        this.familyPhone = familyPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public String getRelationTypeName() {
        return relationTypeName;
    }

    public void setRelationTypeName(String relationTypeName) {
        this.relationTypeName = relationTypeName;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Boolean getReported() {
        return reported;
    }

    public void setReported(Boolean reported) {
        this.reported = reported;
    }

    public IrTemperatureReport getTemperatureReport() {
        return temperatureReport;
    }

    public void setTemperatureReport(IrTemperatureReport temperatureReport) {
        this.temperatureReport = temperatureReport;
    }
}
