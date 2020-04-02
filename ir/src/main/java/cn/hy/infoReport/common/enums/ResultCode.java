package cn.hy.infoReport.common.enums;

/**
 * 返回错误码enum
 * Created by lyw on 2017/12/8.
 */
public enum ResultCode {

    SUCCESS(200, "请求服务器成功!"),
    SYS_ERROR(500, "服务器异常！"),
    PARAM_ERROR(400, "请求参数异常！"),
    NOT_AUTHENTICATION(401, "未认证！"),
    AUTHENTICATION_EXPIRED(402, "认证信息过期！"),
    LACK_PERMISSION(403, "缺少访问权限！"),


    APP_NOT_EXISTS(4001, "应用不存在！"),
    TICKET_EXPIRED(4002, "ticket授权过期！"),
    APP_LACK_PERMISSION(4003, "当前应用未授权！"),
    SCHOOL_NOT_EXISTS(4004, "学校不存在！"),
    SCHOOL_DISABLED(4005, "学校已被停用！"),
    APP_EXPIRED(4012, "应用过期！"),
    APP_DISABLED(4013, "当前应用已停用！"),
    ;

    private int code;
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
