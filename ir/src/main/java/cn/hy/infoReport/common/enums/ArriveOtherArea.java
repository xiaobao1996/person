package cn.hy.infoReport.common.enums;

/**
 * @Author: JiuZhou
 * @Date: 2020/3/4 16:06
 * @Version 1.0
 */
public enum ArriveOtherArea {
    YES((byte) 1, "有"),
    NO((byte)0, "无"),
    ;

    private byte code;
    private String msg;

    ArriveOtherArea(byte code, String msg) {
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
