package cn.hy.infoReport.common.enums;

/**
 * 操作类型 0自动发送 1手工发送
 */
public enum IrMsgLogOpeType {
    AUTO((byte) 0, "自动发送"),
    PERSON_OPE((byte) 1, "手工"),;

    private byte code;
    private String msg;

    IrMsgLogOpeType(byte code, String msg) {
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
