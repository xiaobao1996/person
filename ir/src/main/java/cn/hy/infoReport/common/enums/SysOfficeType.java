package cn.hy.infoReport.common.enums;

/**
 * 组织机构表机构类型的枚举
 */
public enum SysOfficeType {

    /**
     * 机构类型 0:学校 1:行政部门 2:在校班级 3:毕业班级
     */
    SCHOOL((byte)0, "学校"),
    DEPARTMENT((byte)1, "部门"),
    SCHOOL_CLASS((byte)2, "在校班级"),
    GRADUATION_DEPARTMENT((byte)3, "毕业班级"),;

    private byte code;
    private String msg;

    SysOfficeType(byte code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 根据code获取
     * @param code
     * @return
     */
    public static SysOfficeType getByCode(Byte code) {
        if (code == null) {
            return null;
        }
        SysOfficeType[] values = SysOfficeType.values();
        for (SysOfficeType value : values) {
            if (value.code == code) {
                return value;
            }
        }
        return null;
    }
}
