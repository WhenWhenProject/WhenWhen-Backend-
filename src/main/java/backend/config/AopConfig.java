package backend.config;

import backend.advice.logtrace.LogTracer;
import backend.advice.logtrace.LogTracerAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AopConfig {

    @Bean
    public LogTracer logTracer() {
        return new LogTracer();
    }

    @Bean
    LogTracerAspect logTracerAspect(LogTracer logTracer) {
        return new LogTracerAspect(logTracer);
    }

}
