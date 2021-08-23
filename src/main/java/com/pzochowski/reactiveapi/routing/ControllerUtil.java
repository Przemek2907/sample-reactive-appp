package com.pzochowski.reactiveapi.routing;

import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

import java.time.Duration;

public interface ControllerUtil {
    static <T> Flux<ServerSentEvent<T>> toServerStream(Flux<T> flux) {
        return Flux.zip(Flux.interval(Duration.ofSeconds(1)), flux)
                .map(zippedData -> ServerSentEvent.<T> builder()
                        .id(String.valueOf(zippedData.getT1()))
                        .event("New T Object emitter")
                        .data(zippedData.getT2())
                        .build()
                );
    }
}
