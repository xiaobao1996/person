package cn.hy.infoReport.common.enums;

/**
 * @Author: JiuZhou
 * @Date: 2020/3/4 16:48
 * @Version 1.0
 */

/**
 * 有无接触过重点疫情区人员
 */
public enum Contact {
    YES((byte) 1, "有"),
    NO((byte)0, "无"),
    ;

    private byte code;
    private String msg;

    Contact(byte code, String msg) {
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
