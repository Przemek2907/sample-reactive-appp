package com.pzochowski.reactiveapi.service;

import com.pzochowski.reactiveapi.dto.Foo;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
public class SampleDataService {

    public Flux<Foo> fluxStreamOfFoo() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(flowOfData -> new Foo(flowOfData.intValue(), "Foo no: " + flowOfData));
    }
}
