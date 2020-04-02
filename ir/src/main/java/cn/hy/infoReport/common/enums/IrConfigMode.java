package cn.hy.infoReport.common.enums;

/**
 * 模式 0假期 1开学
 */
public enum IrConfigMode {
    HOLIDAY((byte) 0, "假期"),
    OPEN_SCHOOL((byte) 1, "开学"),
    ;

    private byte code;
    private String msg;

    IrConfigMode(byte code, String msg) {
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
