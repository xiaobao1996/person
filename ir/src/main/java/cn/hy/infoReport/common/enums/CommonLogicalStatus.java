package cn.hy.infoReport.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 默认通用逻辑判断枚举类
 */
public enum CommonLogicalStatus {
    //
    YES((byte) 1, "是"),
    NO((byte) 0, "否"),;

    private byte code;
    private String msg;

    CommonLogicalStatus(byte code, String msg) {
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

    /**
     * 根据code获取
     * @return
     */
    public static CommonLogicalStatus getByCode(Byte code) {
        if (code == null) {
            return null;
        }

        CommonLogicalStatus[] values = CommonLogicalStatus.values();
        for (CommonLogicalStatus value : values) {
            if (value.code == code) {
                return value;
            }
        }

        return null;
    }

    /**
     * 根据msg获取
     * @return
     */
    public static CommonLogicalStatus getByMsg(String msg) {
        if (StringUtils.isBlank(msg)) {
            return null;
        }

        CommonLogicalStatus[] values = CommonLogicalStatus.values();
        for (CommonLogicalStatus value : values) {
            if (value.msg.equals(msg)) {
                return value;
            }
        }

        return null;
    }
}
