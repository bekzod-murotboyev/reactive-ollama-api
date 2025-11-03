package uz.keepsolution.reactiveollamaapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import uz.keepsolution.reactiveollamaapi.service.OpenaiChatService;
import uz.keepsolution.reactiveollamaapi.utils.ApiUtils;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiUtils.API + ApiUtils.V1 + ApiUtils.CHAT + ApiUtils.OPENAI)
public class OpenaiController {

    private final OpenaiChatService service;

    @GetMapping(value = "/streaming",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> stream(@RequestParam String q) {
        return service.stream(q);
    }
}
