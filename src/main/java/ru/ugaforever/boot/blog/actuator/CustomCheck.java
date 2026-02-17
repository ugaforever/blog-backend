package ru.ugaforever.boot.blog.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.random.RandomGenerator;

@Component
public class CustomCheck implements HealthIndicator {

    /*
    * http://localhost:8081/actuator/health/readiness
    * http://localhost:8081/actuator/health/liveness
    */
    @Override
    public Health health() {
        return RandomGenerator.getDefault()
                .nextBoolean() ? Health.up().build() : Health.down().build();
    }
}