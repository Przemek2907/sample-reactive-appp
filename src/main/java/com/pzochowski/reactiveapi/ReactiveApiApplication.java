package com.pzochowski.reactiveapi;

import com.pzochowski.reactiveapi.dto.Foo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.LocalTime;

@SpringBootApplication
public class ReactiveApiApplication {

    @Value("${server.port}")
    int serverPort;

    public static void main(String[] args) {
        SpringApplication.run(ReactiveApiApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {

        System.out.println("TRIGGERING SELF TEST INSIDE THE APPLICATION");
        WebClient client = WebClient.create("http://localhost:" + serverPort);
        ParameterizedTypeReference<ServerSentEvent<Foo>> type
                = new ParameterizedTypeReference<ServerSentEvent<Foo>>() {};


        Flux<ServerSentEvent<Foo>> eventStream = client.get()
                .uri("/foo-stream")
                .retrieve()
                .bodyToFlux(type);

        eventStream.subscribe(
                content -> System.out.println("MY foo : " + content.data() + ".  Obtained at: " + LocalTime.now()),
                System.out::println,
                () -> System.out.println("DONE!!!"));
    }

}
