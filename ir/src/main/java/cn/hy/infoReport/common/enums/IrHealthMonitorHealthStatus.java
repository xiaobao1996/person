package cn.hy.infoReport.common.enums;

/**
 * 体温状态 2异常 1需确认 0正常
 */
public enum IrHealthMonitorHealthStatus {
    ABNORMAL((byte) 2, "异常"),
    NEED_CONFIRM((byte) 1, "需确认"),
    NORMAL((byte) 0, "正常"),
    ;

    private byte code;
    private String msg;

    IrHealthMonitorHealthStatus(byte code, String msg) {
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
