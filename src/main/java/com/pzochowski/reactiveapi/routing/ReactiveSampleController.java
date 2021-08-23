package com.pzochowski.reactiveapi.routing;

import com.pzochowski.reactiveapi.dto.Foo;
import com.pzochowski.reactiveapi.service.SampleDataService;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ReactiveSampleController {

    private final SampleDataService sampleDataService;

    public ReactiveSampleController(SampleDataService sampleDataService) {
        this.sampleDataService = sampleDataService;
    }

    @GetMapping("/foo-stream")
    public Flux<ServerSentEvent<Foo>> streamFooEvents() {
        return ControllerUtil.toServerStream(sampleDataService.fluxStreamOfFoo());
    }
}
