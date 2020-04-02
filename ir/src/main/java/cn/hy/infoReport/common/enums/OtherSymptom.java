package cn.hy.infoReport.common.enums;

/**
 * @Author: JiuZhou
 * @Date: 2020/3/6 15:56
 * @Version 1.0
 */
public enum OtherSymptom {

    YES((byte) 1, "有"),
    NO((byte)0, "无"),
    ;

    private byte code;
    private String msg;

    OtherSymptom(byte code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }
}
