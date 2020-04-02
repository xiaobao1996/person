package cn.hy.infoReport.common.enums;

/**
 * 班级人员类型 0:班主任，1:副班主任，2:班长，3:副班长
 */
public enum SysClassUserDetailType {

    HEAD_TEACHER((byte) 0, "班主任"),
    DEPUTY_HEAD_TEACHER((byte) 1, "副班主任"),
    CLASS_MONITOR((byte) 2, "班长"),
    DEPUTY_CLASS_MONITOR((byte) 3, "副班长");

    private byte code;
    private String msg;

    SysClassUserDetailType(byte code, String msg) {
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
     * @return
     */
    public static SysClassUserDetailType findByCode(Byte code) {
        if (code == null) {
            return null;
        }

        SysClassUserDetailType[] values = SysClassUserDetailType.values();
        for (SysClassUserDetailType value : values) {
            if (code == value.code) {
                return value;
            }
        }
        return null;
    }
}
