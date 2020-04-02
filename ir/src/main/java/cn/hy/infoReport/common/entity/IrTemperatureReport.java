package cn.hy.infoReport.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author
 */
public class IrTemperatureReport implements Serializable {
    /**
     * id
     */
    private String id;

    /**
     * 填报时间
     */
    private Date dataTime;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 所在部门名称
     */
    private String officeName;

    /**
     * 学生为所在班级/教师为所在部门的id
     */
    private String officeId;

    /**
     * 身份(0 学生 1 教职工)
     */
    private Byte identity;

    /**
     * 体温
     */
    private BigDecimal temperature;

    /**
     * 其他症状
     */
    private Byte otherSymptom;

    /**
     * 当前所在地
     */
    private String currentLocation;

    /**
     * 有无和疫情人员接触( 0:无 ,1: 有)
     */
    private Byte contact;

    /**
     * 15天内有无去过其他区域(0: 无, 1: 有)
     */
    private Byte arriveOtherArea;

    /**
     * 其他信息
     */
    private String otherInfo;

    /**
     * 是否异常(0 否 1是)
     */
    private Byte abnormal;

    /**
     * 学校id
     */
    private String schoolId;

    /**
     * 学校名称
     */
    private String schoolName;

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
    /**********************业务数据**********************************/

    /**
     * 是否异常
     */
    private Boolean unusual;
    /**
     * 查询的开始时间
     */
    private Date startTime;
    /**
     * 查询的结束时间
     */
    private Date endTime;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 到过的区域
     */
    private List<IrUserArriveArea> userArriveAreaList;

    /**
     * 体温是否异常
     */
    private Boolean temperatureUnusual;

    public Boolean getTemperatureUnusual() {
        return temperatureUnusual;
    }

    public void setTemperatureUnusual(Boolean temperatureUnusual) {
        this.temperatureUnusual = temperatureUnusual;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<IrUserArriveArea> getUserArriveAreaList() {
        return userArriveAreaList;
    }

    public void setUserArriveAreaList(List<IrUserArriveArea> userArriveAreaList) {
        this.userArriveAreaList = userArriveAreaList;
    }

    public Byte getArriveOtherArea() {
        return arriveOtherArea;
    }

    public void setArriveOtherArea(Byte arriveOtherArea) {
        this.arriveOtherArea = arriveOtherArea;
    }

    public Boolean getUnusual() {
        return unusual;
    }

    public void setUnusual(Boolean unusual) {
        this.unusual = unusual;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public Byte getIdentity() {
        return identity;
    }

    public void setIdentity(Byte identity) {
        this.identity = identity;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public Byte getOtherSymptom() {
        return otherSymptom;
    }

    public void setOtherSymptom(Byte otherSymptom) {
        this.otherSymptom = otherSymptom;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Byte getContact() {
        return contact;
    }

    public void setContact(Byte contact) {
        this.contact = contact;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
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

    public Byte getAbnormal() {
        return abnormal;
    }

    public void setAbnormal(Byte abnormal) {
        this.abnormal = abnormal;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        IrTemperatureReport other = (IrTemperatureReport) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDataTime() == null ? other.getDataTime() == null : this.getDataTime().equals(other.getDataTime()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getOfficeName() == null ? other.getOfficeName() == null : this.getOfficeName().equals(other.getOfficeName()))
            && (this.getOfficeId() == null ? other.getOfficeId() == null : this.getOfficeId().equals(other.getOfficeId()))
            && (this.getIdentity() == null ? other.getIdentity() == null : this.getIdentity().equals(other.getIdentity()))
            && (this.getTemperature() == null ? other.getTemperature() == null : this.getTemperature().equals(other.getTemperature()))
            && (this.getOtherSymptom() == null ? other.getOtherSymptom() == null : this.getOtherSymptom().equals(other.getOtherSymptom()))
            && (this.getCurrentLocation() == null ? other.getCurrentLocation() == null : this.getCurrentLocation().equals(other.getCurrentLocation()))
            && (this.getContact() == null ? other.getContact() == null : this.getContact().equals(other.getContact()))
            && (this.getOtherInfo() == null ? other.getOtherInfo() == null : this.getOtherInfo().equals(other.getOtherInfo()))
            && (this.getSchoolId() == null ? other.getSchoolId() == null : this.getSchoolId().equals(other.getSchoolId()))
            && (this.getCreateAt() == null ? other.getCreateAt() == null : this.getCreateAt().equals(other.getCreateAt()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getUpdateAt() == null ? other.getUpdateAt() == null : this.getUpdateAt().equals(other.getUpdateAt()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDataTime() == null) ? 0 : getDataTime().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getOfficeName() == null) ? 0 : getOfficeName().hashCode());
        result = prime * result + ((getOfficeId() == null) ? 0 : getOfficeId().hashCode());
        result = prime * result + ((getIdentity() == null) ? 0 : getIdentity().hashCode());
        result = prime * result + ((getTemperature() == null) ? 0 : getTemperature().hashCode());
        result = prime * result + ((getOtherSymptom() == null) ? 0 : getOtherSymptom().hashCode());
        result = prime * result + ((getCurrentLocation() == null) ? 0 : getCurrentLocation().hashCode());
        result = prime * result + ((getContact() == null) ? 0 : getContact().hashCode());
        result = prime * result + ((getOtherInfo() == null) ? 0 : getOtherInfo().hashCode());
        result = prime * result + ((getSchoolId() == null) ? 0 : getSchoolId().hashCode());
        result = prime * result + ((getCreateAt() == null) ? 0 : getCreateAt().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getUpdateAt() == null) ? 0 : getUpdateAt().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", dataTime=").append(dataTime);
        sb.append(", mobile=").append(mobile);
        sb.append(", userName=").append(userName);
        sb.append(", userId=").append(userId);
        sb.append(", officeName=").append(officeName);
        sb.append(", officeId=").append(officeId);
        sb.append(", identity=").append(identity);
        sb.append(", temperature=").append(temperature);
        sb.append(", otherSymptom=").append(otherSymptom);
        sb.append(", currentLocation=").append(currentLocation);
        sb.append(", contact=").append(contact);
        sb.append(", otherInfo=").append(otherInfo);
        sb.append(", schoolId=").append(schoolId);
        sb.append(", createAt=").append(createAt);
        sb.append(", createBy=").append(createBy);
        sb.append(", updateAt=").append(updateAt);
        sb.append(", updataBy=").append(updateBy);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
