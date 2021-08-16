package link.thingscloud.spring.boot.common.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final boolean isTraceEnabled;
    private static final String LOGGING_PREFIX = "[logging] [{}ms] {}, {}";
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    public LoggingAspect() {
        this.isTraceEnabled = log.isTraceEnabled();
    }

    @Pointcut("@annotation(link.thingscloud.spring.boot.common.aop.Logging)")
    public void logging() {
    }

    @Around("logging()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        try {
            Object proceed = joinPoint.proceed();
            this.doLogging(joinPoint, proceed, (Throwable)null, System.currentTimeMillis() - start);
            return proceed;
        } catch (Throwable var5) {
            this.doLogging(joinPoint, (Object)null, var5, System.currentTimeMillis() - start);
            throw var5;
        }
    }

    @Before("logging()")
    public void doBefore(JoinPoint joinPoint) {
        if (this.isTraceEnabled) {
            log.trace("logging aspect doBefore joinPoint : {}", joinPoint);
        }

    }

    @After("logging()")
    public void doAfter(JoinPoint joinPoint) {
        if (this.isTraceEnabled) {
            log.trace("logging aspect doAfterReturning joinPoint : {}", joinPoint);
        }

    }

    @AfterThrowing(
            pointcut = "logging()",
            throwing = "cause"
    )
    public void doAfterThrowing(JoinPoint joinPoint, Exception cause) {
        if (this.isTraceEnabled) {
            log.trace("logging aspect doAfterReturning joinPoint : {}, result : {}", joinPoint, cause);
        }

    }

    @AfterReturning(
            pointcut = "logging()",
            returning = "result"
    )
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        if (this.isTraceEnabled) {
            log.trace("logging aspect doAfterReturning joinPoint : {}, result : {}", joinPoint, result);
        }

    }

    private void doLogging(JoinPoint joinPoint, Object proceed, Throwable cause, long costTimeMillis) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        StringBuilder sb = new StringBuilder();
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        Class<?> returnType = methodSignature.getReturnType();

        for(int i = 0; i < parameterNames.length; ++i) {
            sb.append(parameterNames[i]).append(" : ").append(args[i]).append(", ");
        }

        Logging logging = (Logging)methodSignature.getMethod().getAnnotation(Logging.class);
        if (logging.result() && returnType != Void.TYPE) {
            sb.append("return").append(" : ").append(proceed);
        } else if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 2);
        }

        if (cause == null) {
            switch(logging.level()) {
                case TRACE:
                    this.getLogger(joinPoint).trace("[logging] [{}ms] {}, {}", new Object[]{costTimeMillis, joinPoint.getSignature().getName(), sb});
                    break;
                case DEBUG:
                    this.getLogger(joinPoint).debug("[logging] [{}ms] {}, {}", new Object[]{costTimeMillis, joinPoint.getSignature().getName(), sb});
                    break;
                case WARN:
                    this.getLogger(joinPoint).warn("[logging] [{}ms] {}, {}", new Object[]{costTimeMillis, joinPoint.getSignature().getName(), sb});
                    break;
                case ERROR:
                    this.getLogger(joinPoint).error("[logging] [{}ms] {}, {}", new Object[]{costTimeMillis, joinPoint.getSignature().getName(), sb});
                    break;
                default:
                    this.getLogger(joinPoint).info("[logging] [{}ms] {}, {}", new Object[]{costTimeMillis, joinPoint.getSignature().getName(), sb});
            }
        } else {
            this.getLogger(joinPoint).error("[logging] [{}ms] {}, {}, cause : ", new Object[]{costTimeMillis, joinPoint.getSignature().getName(), sb, cause});
        }

    }

    private Logger getLogger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getTarget().getClass());
    }
}

