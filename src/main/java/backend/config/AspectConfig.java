package backend.config;

import backend.aspect.logtrace.logtracer.LogTracer;
import backend.aspect.logtrace.LogTracerAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AspectConfig {

    @Bean
    public LogTracer logTracer() {
        return new LogTracer();
    }

    @Bean
    LogTracerAspect logTracerAspect(LogTracer logTracer) {
        return new LogTracerAspect(logTracer);
    }

}
