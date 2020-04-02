package cn.hy.infoReport.common.enums;

/**
 * 体温状态 2异常 1需确认 0正常
 */
public enum IrHealthMonitorCheckStatus {
    FINISHED((byte) 3, "测试完成"),
    PM_NOT((byte) 2, "下午未测"),
    AM_NOT((byte) 1, "上午未测"),
    ALL_NOT((byte) 0, "全天未测"),
    ;

    private byte code;
    private String msg;

    IrHealthMonitorCheckStatus(byte code, String msg) {
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
