package github.ricemonger.logger;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PublicMethodLogger {

    @Around("execution(* github.ricemonger.marketplace.*..*.*(..))")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().getName();
        Object[] methodArgs = joinPoint.getArgs();

        if (methodArgs.length > 0) {
            log.trace("{} | Called: {}:{}", className, methodName, methodArgs);
        } else {
            log.trace("{} | Called: {}", className, methodName);
        }

        long timeBefore = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long timeTaken = System.currentTimeMillis() - timeBefore;

        log.debug("{} | Method: {} took: {}ms", className, methodName, timeTaken);

        if (result != null) {
            if (methodArgs.length > 0) {
                log.trace("{} | Executed: {}:{} with result: {}", className, methodName, methodArgs, result);
            } else {
                log.trace("{} | Executed: {} with result: {}", className, methodName, result);
            }

        } else {
            if (methodArgs.length > 0) {
                log.trace("{} | Executed: {}:{}", className, methodName, methodArgs);
            } else {
                log.trace("{} | Executed: {}", className, methodName);
            }
        }

        return result;
    }
}