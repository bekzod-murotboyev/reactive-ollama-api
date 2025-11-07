package uz.keepsolution.reactiveollamaapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import uz.keepsolution.reactiveollamaapi.dto.replicate.ReplicateGenerateImageResponseDTO;
import uz.keepsolution.reactiveollamaapi.utils.RSocketUtils;

@Controller
@RequiredArgsConstructor
public class RSocketController {

    private final Sinks.Many<ReplicateGenerateImageResponseDTO> sink = Sinks.many().multicast().onBackpressureBuffer();


    @MessageMapping(RSocketUtils.ROUTE_REPLICATE_IMAGE_SEND)
    public Mono<Void> send(Mono<ReplicateGenerateImageResponseDTO> data) {
        return data.doOnNext(sink::tryEmitNext).then();
    }

    @MessageMapping(RSocketUtils.ROUTE_REPLICATE_IMAGE_STREAM)
    public Flux<ReplicateGenerateImageResponseDTO> stream() {
        return sink.asFlux().mergeWith(Flux.never());
    }

}
