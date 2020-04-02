package cn.hy.infoReport.common.enums;

/**
 * 数据来源 0人工监测 1机器监测
 */
public enum IrMonitorHistorySource {
    MACHINE((byte) 1, "机器监测"),
    HUMAN((byte) 0, "人工监测"),
    TB_IMPORT((byte) 3, "天波数据导入"),
    ;

    private byte code;
    private String msg;

    IrMonitorHistorySource(byte code, String msg) {
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
