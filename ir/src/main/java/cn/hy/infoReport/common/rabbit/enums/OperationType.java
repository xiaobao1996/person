package cn.hy.infoReport.common.rabbit.enums;

/**
 * 操作类型
 */
public enum OperationType {

    ADD("add"),
    UPDATE("update"),
    DELETE("delete"),
    UPDATE_ALL("updateAll"),

    STUDENT_SUPPLY_CLASS("supplyClass"),
    STAFF_SUPPLY_OFFICE("supplyOffice");

    public String code;

    OperationType(String code) {
        this.code = code;
    }
}
