package example.monitor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ServiceMonitor {

    @Around("execution(* example..*Service.*(..))")
    public Object logServiceAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object retVal = joinPoint.proceed();
        long end = System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("Completed: {}.{} : {}ms", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), end - start);
        }

        return retVal;
    }
}