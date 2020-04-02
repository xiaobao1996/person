package cn.hy.infoReport.common.vo;


import cn.hy.infoReport.common.enums.ResultCode;

/**
 * Created by lyw on 2017/12/8.
 */
public class Result {

    private Integer code;
    private String message;
    private Object result;

    public Result() {
    }

    public Result(Integer code, String message, Object result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    /**
     * 服务器请求成功返回结果集
     * @param data
     * @return
     */
    public static Result success(Object data) {
        return success(ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 服务器请求成功返回结果集
     * @param message
     * @param data
     * @return
     */
    public static Result success(String message, Object data) {
        return new Result(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 服务器请求失败返回结果集
     * @param data
     * @return
     */
    public static Result error(Object data) {
        return error(ResultCode.SYS_ERROR.getMessage(), data);
    }

    /**
     * 服务器请求失败返回结果集
     * @param message
     * @param data
     * @return
     */
    public static Result error(String message, Object data) {
        return new Result(ResultCode.SYS_ERROR.getCode(), message, data);
    }

}
