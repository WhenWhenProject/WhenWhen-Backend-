package backend.advice;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {

    @Pointcut("execution(* backend.controller..*(..))")
    public void allController(){}

    @Pointcut("execution(* backend.service..*(..))")
    public void allService() {}

    @Pointcut("execution(* backend.repository..*(..))")
    public void allRepository(){}

    @Pointcut("allController() || allService() || allRepository()")
    public void all(){}


}
