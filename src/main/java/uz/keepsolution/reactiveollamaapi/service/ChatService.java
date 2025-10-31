package uz.keepsolution.reactiveollamaapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient chatClient;

    public Flux<String> stream(String q) {
        return chatClient.prompt().user(q).stream().content();
    }
}
