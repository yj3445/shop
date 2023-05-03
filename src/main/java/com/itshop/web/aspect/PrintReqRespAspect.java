package com.itshop.web.aspect;

import com.alibaba.fastjson.JSONObject;
import com.itshop.web.bo.UserInfoVO;
import com.itshop.web.constants.SessionConstants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class PrintReqRespAspect {

    @Pointcut("@annotation( com.itshop.web.annotation.PrintReqResp)")
    void printReqResp(){}

    @Around("printReqResp()")
    public Object printParamAround(ProceedingJoinPoint pjp) throws Throwable {
        String methodSign = pjp.getSignature().toShortString();
        Object[] args = pjp.getArgs();
        List<Object> argList = Arrays.stream(args)
                .filter(f ->  !(f instanceof HttpSession)
                        && (!(f instanceof HttpServletRequest)
                        && !(f instanceof HttpServletResponse)
                        && !(f instanceof MultipartFile)))
                .collect(Collectors.toList());
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String userId = (String) request.getSession().getAttribute(SessionConstants.USER_ID_SESSION);


        Object proceed = null;
        try {
            long stime = System.currentTimeMillis();
            proceed = pjp.proceed();
            long etime = System.currentTimeMillis();
            log.info("method:{} userId:{} request:{} ms:{}", methodSign,userId,JSONObject.toJSONString(argList), (etime -stime));
            return proceed;
        } catch (Throwable e) {
            log.error("method:{} userId:{} request:{} throwable:{}", methodSign,userId,JSONObject.toJSONString(argList), e);
            throw e;
        }
    }

}

