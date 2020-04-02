package cn.hy.infoReport.common.enums;

/**
 * 角色组类型 （0:其他，1:教师，2:家长，3:学生，4:行政人员）
 */
public enum SysRoleGroupType {
    OTHER((byte) 0, "其他"),
    TEACHER((byte) 1, "教师"),
    PARENTS((byte) 2, "家长"),
    STUDENT((byte) 3, "学生"),
    ADMINISTRATIVE((byte) 4, "行政人员"),;

    private byte code;
    private String msg;

    SysRoleGroupType(byte code, String msg) {
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
}
