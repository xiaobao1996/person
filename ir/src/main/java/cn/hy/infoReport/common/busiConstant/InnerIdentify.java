package cn.hy.infoReport.common.busiConstant;

/**
 * 内置身份
 */
public enum InnerIdentify {

    PARENTS("-INNER:PARENTS", "家长"),
    HEAD_TEACHER("-INNER:HEAD_TEACHER", "班主任"),
    ;

    private String code;
    private String msg;

    InnerIdentify(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
