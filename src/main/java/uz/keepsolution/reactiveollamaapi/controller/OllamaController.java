package uz.keepsolution.reactiveollamaapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.keepsolution.reactiveollamaapi.dto.ai.MathStructuredResponseDTO;
import uz.keepsolution.reactiveollamaapi.dto.ai.VisionResponseDTO;
import uz.keepsolution.reactiveollamaapi.service.OllamaChatService;
import uz.keepsolution.reactiveollamaapi.utils.ApiUtils;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiUtils.API + ApiUtils.V1 + ApiUtils.CHAT + ApiUtils.OLLAMA)
public class OllamaController {

    private final OllamaChatService service;


    @GetMapping(value = "/streaming", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> stream(@RequestParam String q) {
        return service.stream(q);
    }

    @GetMapping(value = "/calculation")
    public Mono<MathStructuredResponseDTO> calculate(@RequestParam String q) {
        return service.calculate(q);
    }

    @GetMapping(value = "/blocking")
    public Mono<String> blocking(@RequestParam String q) {
        return service.blocking(q);
    }

    @PostMapping(
            value = "/visions",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Mono<VisionResponseDTO> describeImage(@RequestPart FilePart file) {
        return service.describeImage(file);
    }

}
