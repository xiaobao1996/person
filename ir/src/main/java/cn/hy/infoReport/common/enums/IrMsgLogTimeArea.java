package cn.hy.infoReport.common.enums;

/**
 * 时间范围 0上午 1下午
 */
public enum IrMsgLogTimeArea {
    AM((byte) 0, "上午"),
    PM((byte) 1, "下午"),;

    private byte code;
    private String msg;

    IrMsgLogTimeArea(byte code, String msg) {
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
