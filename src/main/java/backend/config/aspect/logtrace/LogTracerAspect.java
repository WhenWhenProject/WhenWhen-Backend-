package backend.config.aspect.logtrace;

import backend.config.aspect.logtrace.logtracer.LogTracer;
import backend.config.aspect.logtrace.logtracer.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@RequiredArgsConstructor
public class LogTracerAspect {

    private final LogTracer logTracer;

    @Around("backend.config.aspect.PointCuts.all()")
    public Object execute(ProceedingJoinPoint joinPoint) throws  Throwable {
        TraceStatus status = null;

        try {
            String message = joinPoint.getSignature().toShortString();
            status = logTracer.begin(message);

            Object result = joinPoint.proceed();

            logTracer.end(status);

            return result;
        } catch (Exception e) {
            logTracer.exception(status, e);
            throw e;
        }
    }

}