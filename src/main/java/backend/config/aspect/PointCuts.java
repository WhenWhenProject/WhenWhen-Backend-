package backend.config.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {

    @Pointcut("execution(* backend.api.controller..*(..))")
    public void allController(){}

    @Pointcut("execution(* backend.api.service..*(..))")
    public void allService() {}

    @Pointcut("execution(* backend.api.repository..*(..))")
    public void allRepository(){}

    @Pointcut("allController() || allService() || allRepository()")
    public void all(){}


}
