package uz.keepsolution.reactiveollamaapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.keepsolution.reactiveollamaapi.entity.ChatMessageEntity;
import uz.keepsolution.reactiveollamaapi.repository.ChatMessageRepository;

@Service
@RequiredArgsConstructor
public class OpenaiChatService {

    private final ChatClient chatClient;
    private final ChatMessageRepository messageRepository;

    public Flux<ServerSentEvent<String>> stream(String question) {
        Flux<String> response = chatClient
                .prompt()
                .user(question)
                .stream()
                .content()
                .publish()
                .autoConnect(2);

        Mono<Void> saver = response
                .reduce(new StringBuilder(), StringBuilder::append)
                .map(StringBuilder::toString)
                .flatMap(responseText -> messageRepository.save(new ChatMessageEntity(question, responseText)))
                .then();

        return response
                .mergeWith(saver.then(Mono.empty()))
                .map(chunk -> ServerSentEvent.builder(chunk).build());
    }
}
