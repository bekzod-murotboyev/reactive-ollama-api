package uz.keepsolution.reactiveollamaapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.keepsolution.reactiveollamaapi.dto.ai.MathStructuredResponseDTO;
import uz.keepsolution.reactiveollamaapi.service.OllamaChatService;
import uz.keepsolution.reactiveollamaapi.utils.ApiUtils;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiUtils.API + ApiUtils.V1 + ApiUtils.CHAT + ApiUtils.OLLAMA)
public class OllamaController {

    private final OllamaChatService service;


    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> stream(@RequestParam String q) {
        return service.stream(q);
    }

    @GetMapping(value = "/calculation")
    public Mono<MathStructuredResponseDTO> calculate(@RequestParam String q) {
        return service.calculate(q);
    }

}
