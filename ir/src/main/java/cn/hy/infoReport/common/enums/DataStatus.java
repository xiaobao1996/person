package cn.hy.infoReport.common.enums;

/**
 * 数据有效状态
 * '状态（0删除 1正常 2停用）'
 */
public enum DataStatus {

    DELETE((byte)0, "删除"),
    NORMAL((byte)1, "正常"),
    DISABLE((byte)2, "停用"),;

    private byte code;
    private String msg;

    DataStatus(byte code, String msg) {
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
