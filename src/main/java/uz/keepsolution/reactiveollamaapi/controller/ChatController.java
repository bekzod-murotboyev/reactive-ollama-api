package uz.keepsolution.reactiveollamaapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import uz.keepsolution.reactiveollamaapi.service.ChatService;
import uz.keepsolution.reactiveollamaapi.utils.ApiUtils;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiUtils.API + ApiUtils.V1 + ApiUtils.CHAT)
public class ChatController {

    private final ChatService service;

    @GetMapping
    public Flux<String> stream(@RequestParam String q) {
        return service.stream(q);
    }

}
