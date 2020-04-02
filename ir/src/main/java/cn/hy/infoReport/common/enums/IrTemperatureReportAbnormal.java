package cn.hy.infoReport.common.enums;

/**
 * @Author: JiuZhou
 * @Date: 2020/3/18 8:47
 * @Version 1.0
 */
public enum IrTemperatureReportAbnormal {
    ABNORMAL((byte)1, "异常"),
    NORMAL((byte)0, "正常"),
    ;

    private byte code;
    private String msg;

    IrTemperatureReportAbnormal(byte code, String msg) {
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
