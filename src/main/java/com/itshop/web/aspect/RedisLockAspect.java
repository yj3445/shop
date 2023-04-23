package com.itshop.web.aspect;

import com.itshop.web.annotation.RedisLock;
import com.itshop.web.constants.RedisConstants;
import com.itshop.web.constants.SnowConstants;
import com.itshop.web.support.RedisSimpleLock;
import com.itshop.web.util.SpELParserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * redis锁-切面
 *
 * @author liufenglong
 * @date 2020/11/11
 */
@Slf4j
@Aspect
@Component
public class RedisLockAspect {

    @Autowired
    private RedisSimpleLock redisSimpleLock;


    @Around("execution(public * *(..)) && @annotation(com.itshop.web.annotation.RedisLock)")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Object[] args = pjp.getArgs();
        RedisLock lock = method.getAnnotation(RedisLock.class);
        String lockKey = getLockKey(pjp);
        if(!StringUtils.isEmpty(lock.key())) {
            lockKey = SpELParserUtils.parse(method, args, lock.key(), String.class);
        }
        String val = UUID.randomUUID().toString();
        log.info("redis-key-info:{}", new Object[]{lockKey, val, lock.expire()});

        boolean success = redisSimpleLock.tryLock(lockKey, val, lock.expire());
        if (success) {
            try {
                return pjp.proceed();
            } finally {
                redisSimpleLock.unlock(lockKey,val);
            }
        }
//        throw new BizException("请稍后重试");
        return success;
    }

    private String getLockKey(ProceedingJoinPoint pjp) {
        //代理对象信息
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method targetMethod = signature.getMethod();
        String targetClass= pjp.getTarget().getClass().getName();
        final Object[] args = pjp.getArgs();

        //原始对象信息
        String sourceClass = AopUtils.getTargetClass(pjp.getTarget()).getSimpleName();
//      Method sourceMethod = AopUtils.getMostSpecificMethod(method, pjp.getTarget().getClass());

        StringBuilder sb = new StringBuilder();
        sb.append(sourceClass);
        sb.append(SnowConstants.COLON);
        sb.append(targetMethod.getName());
        sb.append(SnowConstants.COLON);

        StringBuilder paraAndValue = new StringBuilder();
        for (Object obj : args) {
            paraAndValue.append(SnowConstants.COLON);
            if (null != obj && StringUtils.isNotBlank(obj.toString())) {
                paraAndValue.append(obj.toString());
            }
        }
        String md5Hex = DigestUtils.md5Hex(sb.toString() + paraAndValue.toString());
        return RedisConstants.REDIS_LOCK_KEY_PREFIX + sb.toString() + md5Hex;
    }
}

