package cn.hy.infoReport.common.enums;

/**
 * @Author: JiuZhou
 * @Date: 2020/3/6 16:59
 * @Version 1.0
 */

/**
 * 是不是末级机构
 */
public enum SchoolTreeLeaf {
    YES((byte) 1, "是"),
    NO((byte)0, "否"),
    ;

    private byte code;
    private String msg;

    SchoolTreeLeaf(byte code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }
}
