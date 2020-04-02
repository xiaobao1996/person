package cn.hy.infoReport.common.exception;


import cn.hy.infoReport.common.enums.ResultCode;

/**
 * 验证相关的exception
 */
public class AuthException extends RuntimeException {

    private ResultCode resultCode;

    public AuthException(ResultCode resultCode) {
        super();
        this.resultCode = resultCode;
    }

    public AuthException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
