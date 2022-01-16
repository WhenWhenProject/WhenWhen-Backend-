package backend.controller;

import backend.advice.logtrace.LogTracer;
import backend.advice.logtrace.LogTracerAspect;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final LogTracer logTracer;
    private final LogTracerAspect logTracerAspect;

    @GetMapping("/hello")
    public String hello() {
        return "ok";
    }

}
