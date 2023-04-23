package com.itshop.web.config;

import com.itshop.web.dto.RetResult;
import com.itshop.web.dto.RetWrapper;
import com.itshop.web.exception.BusinessException;
import com.itshop.web.exception.ForbiddenException;
import com.itshop.web.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller层全局异常配置
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义的业务异常
     * @param e
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public RetResult businessExceptionHandler(BusinessException e,HttpServletRequest request) {
        String message = String.format("接口 [%s] 出现业务异常，异常摘要：%s",
                request.getRequestURI(),
                e.getMessage());
        logger.error("发生业务异常！原因是：{}", e.getMessage());
        return RetWrapper.failure(message);
    }

    /**
     * 登录未认证 异常
     * @param e
     */
    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseBody
    public RetResult unauthorizedExceptionHandler(UnauthorizedException e, HttpServletRequest request) {
        String message = String.format("接口 [%s] 出现登录未认证异常，异常摘要：%s",
                request.getRequestURI(),
                e.getMessage());
        logger.error("发生登录未认证异常！原因是：{}", message);
        return RetWrapper.unauthorized();
    }

    /**
     * 资源未授权 异常
     * @param e
     */
    @ExceptionHandler(value = ForbiddenException.class)
    @ResponseBody
    public RetResult forbiddenExceptionHandler(ForbiddenException e,HttpServletRequest request) {
        String message = String.format("接口 [%s] 出现资源未授权异常，异常摘要：%s",
                request.getRequestURI(),
                e.getMessage());
        logger.error("发生资源未授权异常！原因是：{}", message);
        return RetWrapper.forbidden();
    }

    /**
     * 页面未找到异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    public RetResult error(NoHandlerFoundException e,HttpServletRequest request) {
        logger.error("发生页面未找到异常:", e);
        return RetWrapper.notFound("接口 [" + request.getRequestURI() + "] 不存在");
    }

    /**
     * 处理空指针异常
     *
     * @param e
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public RetResult exceptionHandler(NullPointerException e, HttpServletRequest request) {
        String message = String.format("接口 [%s] 出现空指针异常，异常摘要：%s",
                request.getRequestURI(),
                e.getMessage());
        logger.error(message, e);
        return RetWrapper.internalServerError(message);
    }

    /**
     * 处理其他异常
     *
     * @param e
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public RetResult exceptionHandler(Exception e, HttpServletRequest request) {
        String message = String.format("接口 [%s] 出现异常，异常摘要：%s",
                request.getRequestURI(),
                e.getMessage());
        logger.error(message, e);
        return RetWrapper.internalServerError(message);
    }
}
