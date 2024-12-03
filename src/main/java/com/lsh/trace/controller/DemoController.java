package com.lsh.trace.controller;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
@Slf4j
@RestController
public class DemoController {
    private final Tracer tracer = GlobalOpenTelemetry.getTracer("DemoController");

    @GetMapping("/rolldice")
    public String index(@RequestParam("player") Optional<String> player) {
        var span = tracer.spanBuilder("rolldice-span").startSpan();

        int result = this.getRandomNumber(1, 6);
        span.setAttribute("example-attribute", "example-value");

        if (player.isPresent()) {
            log.info("{} is rolling the dice: {}", player.get(), result);
        } else {
            log.info("Anonymous player is rolling the dice: {}", result);
        }
        span.end();
        return Integer.toString(result);
    }

    public int getRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
