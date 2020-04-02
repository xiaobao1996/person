package cn.hy.infoReport.common.exception;

import cn.hy.infoReport.common.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理异常业务
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        if (e instanceof MessageException) {
            logger.error("######  业务异常：{}", e.getMessage());
            return Result.error(e.getMessage(), null);
        }

        if (e instanceof AuthException) {
            logger.error("######  权限相关的异常：{}", ((AuthException) e).getResultCode().getMessage());
            return new Result(((AuthException) e).getResultCode().getCode(), ((AuthException) e).getResultCode().getMessage(), null);
        }

        if (e instanceof MissingServletRequestParameterException) {
            return Result.error("缺少必要的参数", null);
        }

        logger.error("######  系统异常：", e);
        return Result.error(null);
    }

}
